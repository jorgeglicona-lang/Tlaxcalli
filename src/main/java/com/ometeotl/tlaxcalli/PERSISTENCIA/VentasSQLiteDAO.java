package com.ometeotl.tlaxcalli.PERSISTENCIA;

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
    public boolean guardarCorteCompleto(int idEmpleado, double kTortillaRep, double kTortillaPub, double kMasa, 
                                        DefaultTableModel modeloProductos, DefaultTableModel modeloGastos) {
        CSQLiteConnection conMngr = new CSQLiteConnection();
        Connection con = null;
        try {
            con = conMngr.establecerConexionPortatil();
            con.setAutoCommit(false); // MODO TRANSACCIÓN

            // 1. GUARDAR VENTAS FIJAS
            // En SQLite usamos datetime('now', 'localtime') para la estampa de tiempo
            String sqlVenta = "INSERT INTO Ventas_Diarias (Id_empleado, Id_producto, Fecha, Cantidad, Total_Dinero, Observaciones) " +
                              "VALUES (?, ?, datetime('now', 'localtime'), ?, ?, ?)";
            
            // Obtenemos los precios frescos de la base de datos justo antes de guardar
            double pReparto = obtenerPrecioProducto(1);
            double pMostrador = obtenerPrecioProducto(2);
            double pMasa = obtenerPrecioProducto(3);

            try (PreparedStatement psV = con.prepareStatement(sqlVenta)) {
                // 🛠️ ¡REPARADO! Multiplicamos por la variable, no por un número fijo
                if (kTortillaRep > 0) registrarItem(psV, idEmpleado, 1, kTortillaRep, kTortillaRep * pReparto, "Reparto");
                if (kTortillaPub > 0) registrarItem(psV, idEmpleado, 2, kTortillaPub, kTortillaPub * pMostrador, "Mostrador");
                if (kMasa > 0) registrarItem(psV, idEmpleado, 3, kMasa, kMasa * pMasa, "Mostrador");

                // 2. GUARDAR TABLA DE PRODUCTOS ADICIONALES
                for (int i = 0; i < modeloProductos.getRowCount(); i++) {
                    int idProd = Integer.parseInt(modeloProductos.getValueAt(i, 0).toString());
                    double cant = Double.parseDouble(modeloProductos.getValueAt(i, 2).toString());
                    double total = Double.parseDouble(modeloProductos.getValueAt(i, 4).toString());
                    String obs = modeloProductos.getValueAt(i, 1).toString(); 
                    
                    registrarItem(psV, idEmpleado, idProd, cant, total, obs);
                }
            }

            // 3. GUARDAR TABLA DE GASTOS
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

            con.commit(); 
            return true;

        } catch (Exception e) {
            System.err.println("Error guardando corte en SQLite: " + e.getMessage());
            try { if (con != null) con.rollback(); } catch (SQLException ex) { }
            return false;
        } finally {
            try { if (con != null) { con.setAutoCommit(true); con.close(); } } catch (SQLException ex) { }
        }
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
    public double obtenerTotalRepartoHoy() {
        CSQLiteConnection conMngr = new CSQLiteConnection();
        double total = 0.0;
        // En SQLite, usamos date(Fecha) para sacar el día de un Timestamp
        String sql = "SELECT SUM(Cantidad) FROM Ventas_Diarias WHERE date(Fecha) = date('now', 'localtime') AND Id_producto = 1";
        
        try (Connection con = conMngr.establecerConexionPortatil();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                total = rs.getDouble(1);
            }
        } catch (Exception e) {
            System.err.println("Error obteniendo ventas reparto SQLite: " + e);
        }
        return total;
    }

    @Override
    public List<com.ometeotl.tlaxcalli.LOGICA.ProductoItem> obtenerProductosParaVenta() {
        List<com.ometeotl.tlaxcalli.LOGICA.ProductoItem> lista = new ArrayList<>();
        CSQLiteConnection conMngr = new CSQLiteConnection();
        String sql = "SELECT * FROM Productos WHERE id_Producto > 3"; 
        
        try (Connection con = conMngr.establecerConexionPortatil();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
             
            while(rs.next()) {
                lista.add(new com.ometeotl.tlaxcalli.LOGICA.ProductoItem(
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
    public List<com.ometeotl.tlaxcalli.LOGICA.GastoItem> obtenerGastosParaVenta() {
        List<com.ometeotl.tlaxcalli.LOGICA.GastoItem> lista = new ArrayList<>();
        CSQLiteConnection conMngr = new CSQLiteConnection();
        String sql = "SELECT * FROM Cat_Gastos"; 
        
        try (Connection con = conMngr.establecerConexionPortatil();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
             
            while(rs.next()) {
                lista.add(new com.ometeotl.tlaxcalli.LOGICA.GastoItem(
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
}