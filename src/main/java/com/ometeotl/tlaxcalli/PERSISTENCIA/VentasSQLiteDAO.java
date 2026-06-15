package com.ometeotl.tlaxcalli.PERSISTENCIA;

import com.ometeotl.tlaxcalli.LOGICA.GastoItem;
import com.ometeotl.tlaxcalli.LOGICA.ProductoItem;
import com.ometeotl.tlaxcalli.PERSISTENCIA.Interfaces.IVentasDAO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.table.DefaultTableModel;

public class VentasSQLiteDAO implements IVentasDAO {

    @Override
    public boolean guardarCorteCompleto(Connection con, int idEmpleado, double kTortillaRep, double kTortillaPub, double kMasa, 
                                        DefaultTableModel modeloProductos, DefaultTableModel modeloGastos) {
        try {
            // 🛡️ REPARADO: Ya no creamos conexión local, usamos la 'con' que viene del servicio
            
            // Limpiamos los registros viejos de HOY para este empleado antes de inyectar el nuevo estado limpio.
            String sqlDelVentas = "DELETE FROM Ventas_Diarias WHERE Id_empleado = ? AND date(Fecha) = date('now', 'localtime')";
            String sqlDelGastos = "DELETE FROM Gastos WHERE Id_empleado = ? AND date(Fecha) = date('now', 'localtime')";
            
            try (PreparedStatement psDelV = con.prepareStatement(sqlDelVentas);
                 PreparedStatement psDelG = con.prepareStatement(sqlDelGastos)) {
                psDelV.setInt(1, idEmpleado);
                psDelG.setInt(1, idEmpleado);
                psDelV.executeUpdate();
                psDelG.executeUpdate();
            }

            // GUARDAR VENTAS FIJAS
            String sqlVenta = "INSERT INTO Ventas_Diarias (Id_empleado, Id_producto, Fecha, Cantidad, Total_Dinero, Observaciones) " +
                              "VALUES (?, ?, datetime('now', 'localtime'), ?, ?, ?)";
            
            double pReparto = obtenerPrecioProducto(1);
            double pMostrador = obtenerPrecioProducto(2);
            double pMasa = obtenerPrecioProducto(3);

            try (PreparedStatement psV = con.prepareStatement(sqlVenta)) {
                if (kTortillaRep > 0) registrarItem(psV, idEmpleado, 1, kTortillaRep, kTortillaRep * pReparto, "Reparto");
                if (kTortillaPub > 0) registrarItem(psV, idEmpleado, 2, kTortillaPub, kTortillaPub * pMostrador, "Mostrador");
                if (kMasa > 0) registrarItem(psV, idEmpleado, 3, kMasa, kMasa * pMasa, "Mostrador");

                // GUARDAR TABLA DE PRODUCTOS ADICIONALES
                for (int i = 0; i < modeloProductos.getRowCount(); i++) {
                    int idProd = Integer.parseInt(modeloProductos.getValueAt(i, 0).toString());
                    double cant = Double.parseDouble(modeloProductos.getValueAt(i, 2).toString());
                    double total = Double.parseDouble(modeloProductos.getValueAt(i, 4).toString());
                    String obs = modeloProductos.getValueAt(i, 1).toString(); 
                    
                    registrarItem(psV, idEmpleado, idProd, cant, total, obs);
                }
            }

            // GUARDAR TABLA DE GASTOS
            String sqlGasto = "INSERT INTO Gastos (Id_empleado, Descripcion, Monto, Fecha) VALUES (?, ?, ?, datetime('now', 'localtime'))";
            try (PreparedStatement psG = con.prepareStatement(sqlGasto)) {
                for (int i = 0; i < modeloGastos.getRowCount(); i++) {
                    String desc = modeloGastos.getValueAt(i, 0).toString();
                    double monto = Double.parseDouble(modeloGastos.getValueAt(i, 1).toString());

                    psG.setInt(1, idEmpleado);
                    psG.setString(2, desc);
                    psG.setDouble(3, monto);
                    psG.executeUpdate();
                }
            }

            return true; // 📦 Éxito parcial, el commit final lo decide el servicio

        } catch (Exception e) {
            System.err.println("Error guardando corte parcial en DAO: " + e.getMessage());
            return false;
        }
        // 🧼 ELIMINADO: Ya no hay bloque finally con con.close() aquí, asegurando que la transacción siga viva
    }

    private void registrarItem(PreparedStatement ps, int emp, int prod, double cant, double total, String obs) throws SQLException {
        ps.setInt(1, emp);
        ps.setInt(2, prod);
        ps.setDouble(3, cant);
        ps.setDouble(4, total);
        ps.setString(5, obs);
        ps.executeUpdate();
    }
    
    @Override
    public double obtenerTotalRepartoHoy(Connection con) {
        double total = 0.0;
        String sql = "SELECT SUM(Cantidad) FROM Ventas_Diarias " +
                 "WHERE date(Fecha) = date('now', 'localtime') " +
                 "AND Id_producto IN (1, 2) " + 
                 "AND Id_empleado != 2";
        
        try (PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                total = rs.getDouble(1);
            }
        } catch (Exception e) {
            System.err.println("Error obteniendo ventas reparto en transacción: " + e.getMessage());
        }
        return total;
    }
    
    @Override
    public double obtenerTotalRepartoHoy() {
        CSQLiteConnection conMngr = new CSQLiteConnection();
        double total = 0.0;
        String sql = "SELECT SUM(Cantidad) FROM Ventas_Diarias " +
                 "WHERE date(Fecha) = date('now', 'localtime') " +
                 "AND Id_producto IN (1, 2) " + 
                 "AND Id_empleado != 2";
        
        try (java.sql.Connection con = conMngr.establecerConexionPortatil();
             java.sql.PreparedStatement ps = con.prepareStatement(sql);
             java.sql.ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                total = rs.getDouble(1);
            }
        } catch (Exception e) {
            System.err.println("Error obteniendo ventas reparto SQLite: " + e);
        }
        return total;
    }
    
    @Override
    public double obtenerTotalMasaHoy(Connection con) {
        double total = 0.0;
        // Suma el producto 3 (Masa) vendido por los repartidores hoy
        String sql = "SELECT SUM(Cantidad) FROM Ventas_Diarias " +
                     "WHERE date(Fecha) = date('now', 'localtime') " +
                     "AND Id_producto = 3 " + 
                     "AND Id_empleado != 2"; 
        try (PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            if (rs.next()) total = rs.getDouble(1);
        } catch (Exception e) { System.err.println("Error: " + e.getMessage()); }
        return total;
    }
    
    @Override
    public double obtenerTotalMasaHoy() {
        CSQLiteConnection conMngr = new CSQLiteConnection();
        double total = 0.0;
        
        // Suma el producto 3 (Masa) vendido por los repartidores hoy
        String sql = "SELECT SUM(Cantidad) FROM Ventas_Diarias " +
                     "WHERE date(Fecha) = date('now', 'localtime') " +
                     "AND Id_producto = 3 " + 
                     "AND Id_empleado != 2"; 
                     
        // 🛠️ CORREGIDO: Abrimos la conexión propia dentro del try
        try (Connection con = conMngr.establecerConexionPortatil();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
             
            if (rs.next()) {
                total = rs.getDouble(1);
            }
        } catch (Exception e) { 
            System.err.println("Error obteniendo total masa de la BD: " + e.getMessage()); 
        }
        return total;
    }
    
    @Override
    public List<ProductoItem> obtenerProductosParaVenta() {
        List<com.ometeotl.tlaxcalli.LOGICA.ProductoItem> lista = new ArrayList<>();
        CSQLiteConnection conMngr = new CSQLiteConnection();
        String sql = "SELECT * FROM Productos WHERE id_Producto > 3"; 
        
        try (Connection con = conMngr.establecerConexionPortatil();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
             
            while(rs.next()) {
                lista.add(new ProductoItem(
                    rs.getInt("Id_producto"),
                    rs.getString("Nom_producto"),
                    rs.getDouble("Precio"),
                    rs.getInt("Es_Comodin") == 1
                ));
            }
        } catch (Exception e) { System.err.println("Error productos SQLite: " + e.getMessage()); }
        return lista;
    }

    @Override
    public List<GastoItem> obtenerGastosParaVenta() {
        List<com.ometeotl.tlaxcalli.LOGICA.GastoItem> lista = new ArrayList<>();
        CSQLiteConnection conMngr = new CSQLiteConnection();
        String sql = "SELECT * FROM Cat_Gastos"; 
        
        try (Connection con = conMngr.establecerConexionPortatil();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
             
            while(rs.next()) {
                lista.add(new GastoItem(
                    rs.getInt("Id_tipo"),
                    rs.getString("Nombre"),
                    rs.getInt("Requiere_Descripcion") == 1
                ));
            }
        } catch (Exception e) { System.err.println("Error gastos SQLite: " + e.getMessage()); }
        return lista;
    }
    
    @Override
    public double obtenerPrecioProducto(int idProducto) {
        CSQLiteConnection conMngr = new CSQLiteConnection();
        String sql = "SELECT Precio FROM Productos WHERE Id_producto = ?";
        try (Connection con = conMngr.establecerConexionPortatil();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, idProducto);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return rs.getDouble("Precio");
            }
        } catch (Exception e) {
            System.err.println("Error obteniendo precio: " + e.getMessage());
        }
        return 0.0; // En caso de error catastrófico
    }
    
    @Override
    public double[] obtenerCorteEmpleadoHoy(int idEmpleado) {
        CSQLiteConnection conMngr = new CSQLiteConnection();
        String sql = "SELECT Id_producto, Cantidad FROM Ventas_Diarias " +
                     "WHERE Id_empleado = ? AND date(Fecha) = date('now', 'localtime') " +
                     "AND Id_producto IN (1, 2, 3)";
        
        try (Connection con = conMngr.establecerConexionPortatil();
             PreparedStatement ps = con.prepareStatement(sql)) {
            
            ps.setInt(1, idEmpleado);
            try (ResultSet rs = ps.executeQuery()) {
                double[] datos = new double[3];
                boolean tieneRegistros = false;
                
                while (rs.next()) {
                    tieneRegistros = true;
                    int idProd = rs.getInt("Id_producto");
                    double cantidad = rs.getDouble("Cantidad");
                    
                    // Mapeamos: ID 1 -> posición 0, ID 2 -> posición 1, ID 3 -> posición 2
                    if (idProd >= 1 && idProd <= 3) {
                        datos[idProd - 1] = cantidad;
                    }
                }
                // Si encontramos al menos una fila, regresamos el array. Si no, null.
                return tieneRegistros ? datos : null;
            }
        } catch (Exception e) {
            System.err.println("Error buscando corte previo en SQLite: " + e.getMessage());
        }
        return null;
    }
    
    @Override
    public List<Object[]> obtenerProductosCorteHoy(int idEmpleado) {
        List<Object[]> lista = new ArrayList<>();
        CSQLiteConnection conMngr = new CSQLiteConnection();
        String sql = "SELECT v.Id_producto, p.Nom_producto, v.Cantidad, p.Precio, v.Total_Dinero " +
                     "FROM Ventas_Diarias v " +
                     "JOIN Productos p ON v.Id_producto = p.Id_producto " +
                     "WHERE v.Id_empleado = ? AND date(v.Fecha) = date('now', 'localtime') AND v.Id_producto > 3";
        
        try (Connection con = conMngr.establecerConexionPortatil();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, idEmpleado);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Object[] fila = new Object[5];
                    fila[0] = rs.getInt("Id_producto");
                    fila[1] = rs.getString("Nom_producto");
                    fila[2] = rs.getDouble("Cantidad");
                    fila[3] = rs.getDouble("Precio");
                    fila[4] = rs.getDouble("Total_Dinero");
                    lista.add(fila);
                }
            }
        } catch (Exception e) { System.err.println("Error cargando tabla productos hoy: " + e.getMessage()); }
        return lista;
    }

    @Override
    public List<Object[]> obtenerGastosCorteHoy(int idEmpleado) {
        List<Object[]> lista = new ArrayList<>();
        CSQLiteConnection conMngr = new CSQLiteConnection();
        String sql = "SELECT Descripcion, Monto FROM Gastos WHERE Id_empleado = ? AND date(Fecha) = date('now', 'localtime')";
        
        try (Connection con = conMngr.establecerConexionPortatil();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, idEmpleado);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Object[] fila = new Object[2];
                    fila[0] = rs.getString("Descripcion");
                    fila[1] = rs.getDouble("Monto");
                    lista.add(fila);
                }
            }
        } catch (Exception e) { System.err.println("Error cargando tabla gastos hoy: " + e.getMessage()); }
        return lista;
    }
    
    @Override
    public boolean actualizarKilosMostrador(Connection con, int idMostrador, double cantidad) {
        double pMostrador = obtenerPrecioProducto(2);
        if (pMostrador == 0) pMostrador = 22.0;
        double totalDinero = cantidad * pMostrador;

        // Intentamos actualizar la tortilla del mostrador si ya existía el registro hoy
        String sqlUpdate = "UPDATE Ventas_Diarias SET Cantidad = ?, Total_Dinero = ? " +
                           "WHERE Id_empleado = ? AND Id_producto = 2 AND date(Fecha) = date('now', 'localtime')";
        
        try (PreparedStatement ps = con.prepareStatement(sqlUpdate)) {
            ps.setDouble(1, cantidad);
            ps.setDouble(2, totalDinero);
            ps.setInt(3, idMostrador);
            
            int filasModificadas = ps.executeUpdate();
            
            // Si el dueño no ha abierto la ventana del mostrador hoy, no habrá filas que actualizar.
            // En ese caso, le creamos su registro base autocalculado de una vez:
            if (filasModificadas == 0) {
                String sqlInsert = "INSERT INTO Ventas_Diarias (Id_empleado, Id_producto, Fecha, Cantidad, Total_Dinero, Observaciones) " +
                                   "VALUES (?, 2, datetime('now', 'localtime'), ?, ?, 'Mostrador Autocalculado')";
                try (PreparedStatement psIns = con.prepareStatement(sqlInsert)) {
                    psIns.setInt(1, idMostrador);
                    psIns.setDouble(2, cantidad);
                    psIns.setDouble(3, totalDinero);
                    psIns.executeUpdate();
                }
            }
            return true;
        } catch (SQLException e) {
            System.err.println("Error en cascada Mostrador SQLite: " + e.getMessage());
            return false;
        }
    }
}