package com.ometeotl.tlaxcalli.PERSISTENCIA;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;

public class VentasDAO {

    // NUEVO MÉTODO QUE RECIBE LAS TABLAS LLENAS
    public boolean guardarCorteCompleto(int idEmpleado, 
                                        double kTortillaRep, double kTortillaPub, double kMasa, 
                                        javax.swing.table.DefaultTableModel modeloProductos,
                                        javax.swing.table.DefaultTableModel modeloGastos) {
        
        Connection con = null;
        try {
            con = new Cconection().establecerConexion();
            con.setAutoCommit(false); // MODO TRANSACCIÓN

            // 1. GUARDAR VENTAS FIJAS (Tortilla y Masa)
            String sqlVenta = "INSERT INTO Ventas_Diarias (Id_empleado, Id_producto, Fecha, Cantidad, Total_Dinero, Observaciones) VALUES (?, ?, GETDATE(), ?, ?, ?)";
            PreparedStatement psV = con.prepareStatement(sqlVenta);

            // Tortilla Reparto (ID 1)
            if (kTortillaRep > 0) registrarItem(psV, idEmpleado, 1, kTortillaRep, kTortillaRep * 19.0, "Reparto");
            // Tortilla Público (ID 2)
            if (kTortillaPub > 0) registrarItem(psV, idEmpleado, 2, kTortillaPub, kTortillaPub * 22.0, "Mostrador");
            // Masa (ID 3)
            if (kMasa > 0) registrarItem(psV, idEmpleado, 3, kMasa, kMasa * 20.0, "Mostrador");

            // 2. GUARDAR TABLA DE PRODUCTOS ADICIONALES
            // Asumimos columnas: [0:ID, 1:Nombre, 2:Cant, 3:PrecioU, 4:Total]
            for (int i = 0; i < modeloProductos.getRowCount(); i++) {
                int idProd = Integer.parseInt(modeloProductos.getValueAt(i, 0).toString());
                double cant = Double.parseDouble(modeloProductos.getValueAt(i, 2).toString());
                double total = Double.parseDouble(modeloProductos.getValueAt(i, 4).toString());
                // Nota: Si es comodín, guardamos la descripción en Observaciones
                String obs = modeloProductos.getValueAt(i, 1).toString(); 
                
                registrarItem(psV, idEmpleado, idProd, cant, total, obs);
            }

            // 3. GUARDAR TABLA DE GASTOS
            // Asumimos columnas: [0:Descripcion, 1:Monto]
            String sqlGasto = "INSERT INTO Gastos (Id_empleado, Descripcion, Monto, Fecha) VALUES (?, ?, ?, GETDATE())";
            PreparedStatement psG = con.prepareStatement(sqlGasto);

            for (int i = 0; i < modeloGastos.getRowCount(); i++) {
                String desc = modeloGastos.getValueAt(i, 0).toString();
                double monto = Double.parseDouble(modeloGastos.getValueAt(i, 1).toString());

                psG.setInt(1, idEmpleado);
                psG.setString(2, desc);
                psG.setDouble(3, monto);
                psG.executeUpdate();
            }

            con.commit(); // CONFIRMAR TODO
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            javax.swing.JOptionPane.showMessageDialog(null, "Error SQL Crítico:\n" + e.getMessage());
            
            try { if (con != null) con.rollback(); } catch (SQLException ex) { }
            return false;
        } finally {
            try { if (con != null) con.close(); } catch (SQLException ex) { }
        }
    }

    // Auxiliar para limpiar código
    private void registrarItem(PreparedStatement ps, int emp, int prod, double cant, double total, String obs) throws SQLException {
        ps.setInt(1, emp);
        ps.setInt(2, prod);
        ps.setDouble(3, cant);
        ps.setDouble(4, total);
        ps.setString(5, obs);
        ps.executeUpdate();
    }

    // Métodos auxiliares para no repetir código
    private void registrarVenta(Connection con, String sql, int emp, int prod, double cant, double total, String obs) throws SQLException {
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, emp);
            ps.setInt(2, prod);
            ps.setDouble(3, cant);
            ps.setDouble(4, total);
            ps.setString(5, obs);
            ps.executeUpdate();
        }
    }

    private void registrarGasto(Connection con, String sql, int emp, String desc, double monto) throws SQLException {
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, emp);
            ps.setString(2, desc);
            ps.setDouble(3, monto);
            ps.executeUpdate();
        }
    }
    
    // MÉTODO PARA SUMAR LO QUE SE LLEVARON LOS REPARTIDORES HOY
    public double obtenerTotalRepartoHoy() {
        Connection con = null;
        double total = 0.0;
        try {
            con = new Cconection().establecerConexion();
            // Asumiendo que el ID del producto "Tortilla Reparto" es 1 (según tu código anterior)
            String sql = "SELECT SUM(Cantidad) FROM Ventas_Diarias WHERE Fecha = CAST(GETDATE() AS DATE) AND Id_producto = 1";
            
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            
            if (rs.next()) {
                total = rs.getDouble(1);
            }
        } catch (Exception e) {
            System.err.println("Error obteniendo ventas reparto: " + e);
        } finally {
            try { if (con != null) con.close(); } catch (Exception ex) {}
        }
        return total;
    }
}