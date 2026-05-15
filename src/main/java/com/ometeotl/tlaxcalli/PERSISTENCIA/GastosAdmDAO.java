package com.ometeotl.tlaxcalli.PERSISTENCIA;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class GastosAdmDAO {

    // 1. MÉTODO PARA REGISTRAR EL GASTO (Con fecha automática)
    public boolean registrarGastoAdm(String descripcion, double monto) {
        Cconection conexion = new Cconection();
        // Usamos GETDATE() directo en SQL para que ponga la fecha y hora actual
        String sql = "INSERT INTO Gastos_Administrativos (Descripcion, Monto, Fecha) VALUES (?, ?, GETDATE())";
        
        try (Connection con = conexion.establecerConexion();
             PreparedStatement pst = con.prepareStatement(sql)) {
            
            pst.setString(1, descripcion);
            pst.setDouble(2, monto);
            
            int filasAfectadas = pst.executeUpdate();
            return filasAfectadas > 0;
            
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al guardar el gasto administrativo: " + e.toString());
            return false;
        }
    }
    
    // 2. MÉTODO PARA OBTENER LOS GASTOS DE ESTA SEMANA (Lunes a Domingo)
    public DefaultTableModel obtenerGastosSemana() {
        DefaultTableModel modelo = new DefaultTableModel();
        modelo.addColumn("ID");
        modelo.addColumn("Descripción");
        modelo.addColumn("Monto ($)");
        modelo.addColumn("Fecha");
        
        Cconection conexion = new Cconection();
        
        // Esta consulta mágica trae la semana desde el Lunes sin importar el idioma del SQL Server
        String sql = "SELECT Id_gasto_adm, Descripcion, Monto, Fecha " +
                     "FROM Gastos_Administrativos " +
                     "WHERE Fecha >= DATEADD(wk, DATEDIFF(wk, 0, GETDATE()), 0) " +
                     "AND Fecha <= DATEADD(wk, DATEDIFF(wk, 0, GETDATE()), 6) " +
                     "ORDER BY Fecha DESC";
                     
        try (Connection con = conexion.establecerConexion();
             PreparedStatement pst = con.prepareStatement(sql);
             ResultSet rs = pst.executeQuery()) {
            
            while (rs.next()) {
                Object[] fila = new Object[4];
                fila[0] = rs.getInt("Id_gasto_adm");
                fila[1] = rs.getString("Descripcion");
                fila[2] = rs.getDouble("Monto");
                fila[3] = rs.getDate("Fecha");
                modelo.addRow(fila);
            }
            
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al cargar la tabla: " + e.toString());
        }
        return modelo;
    }
    
    // 3. MÉTODO PARA ELIMINAR POR SI NOS EQUIVOCAMOS DE GASTO
    public boolean eliminarGastoAdm(int idGasto) {
        Cconection conexion = new Cconection();
        String sql = "DELETE FROM Gastos_Administrativos WHERE Id_gasto_adm = ?";
        
        try (Connection con = conexion.establecerConexion();
             PreparedStatement pst = con.prepareStatement(sql)) {
            
            pst.setInt(1, idGasto);
            int filasAfectadas = pst.executeUpdate();
            return filasAfectadas > 0;
            
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al eliminar: " + e.toString());
            return false;
        }
    }
}