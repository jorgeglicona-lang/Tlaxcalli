package com.ometeotl.tlaxcalli.PERSISTENCIA;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class GGAdministrativosDAO {

    public DefaultTableModel obtenerGastosSemana() {
        DefaultTableModel modelo = new DefaultTableModel();
        modelo.addColumn("ID");
        modelo.addColumn("Descripción");
        modelo.addColumn("Monto ($)");
        modelo.addColumn("Fecha");
        
        Cconection conexion = new Cconection();
        String sql = "SELECT Id_gasto_adm, Descripcion, Monto, Fecha " +
                     "FROM Gastos_Administrativos " +
                     "WHERE Fecha >= DATEADD(wk, DATEDIFF(wk, 0, GETDATE()), 0) " +
                     "AND Fecha <= DATEADD(wk, DATEDIFF(wk, 0, GETDATE()), 6) " +
                     "ORDER BY Fecha DESC";
                     
        try (Connection con = conexion.establecerConexion();
             PreparedStatement pst = con.prepareStatement(sql);
             ResultSet rs = pst.executeQuery()) {
            
            while (rs.next()) {
                modelo.addRow(new Object[]{
                    rs.getInt("Id_gasto_adm"), 
                    rs.getString("Descripcion"), 
                    rs.getDouble("Monto"), 
                    rs.getDate("Fecha")
                });
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al cargar gastos semanales: " + e.toString());
        }
        return modelo;
    }

    // 1. Modificar el registro para que acepte fecha
public boolean registrarGasto(String descripcion, double monto, String fecha) {
    Cconection conexion = new Cconection();
    String sql = "INSERT INTO Gastos_Administrativos (Descripcion, Monto, Fecha) VALUES (?, ?, ?)";
    try (Connection con = conexion.establecerConexion();
         PreparedStatement pst = con.prepareStatement(sql)) {
        pst.setString(1, descripcion);
        pst.setDouble(2, monto);
        pst.setString(3, fecha); // Recibe "YYYY-MM-DD"
        return pst.executeUpdate() > 0;
    } catch (SQLException e) {
        JOptionPane.showMessageDialog(null, "Error al guardar: " + e.toString());
        return false;
    }
}

// 2. Nuevo método para buscar por rango de fechas
public DefaultTableModel obtenerGastosPorRango(String inicio, String fin) {
    DefaultTableModel modelo = new DefaultTableModel();
    modelo.addColumn("ID"); modelo.addColumn("Descripción"); modelo.addColumn("Monto ($)"); modelo.addColumn("Fecha");
    
    Cconection conexion = new Cconection();
    String sql = "SELECT Id_gasto_adm, Descripcion, Monto, Fecha " +
                 "FROM Gastos_Administrativos " +
                 "WHERE Fecha BETWEEN ? AND ? ORDER BY Fecha DESC";
                 
    try (Connection con = conexion.establecerConexion();
         PreparedStatement pst = con.prepareStatement(sql)) {
        pst.setString(1, inicio);
        pst.setString(2, fin);
        ResultSet rs = pst.executeQuery();
        while (rs.next()) {
            modelo.addRow(new Object[]{rs.getInt(1), rs.getString(2), rs.getDouble(3), rs.getDate(4)});
        }
    } catch (SQLException e) {
        JOptionPane.showMessageDialog(null, "Error al buscar: " + e.toString());
    }
    return modelo;
}

    public boolean eliminarGasto(int idGasto) {
        Cconection conexion = new Cconection();
        String sql = "DELETE FROM Gastos_Administrativos WHERE Id_gasto_adm = ?";
        try (Connection con = conexion.establecerConexion();
             PreparedStatement pst = con.prepareStatement(sql)) {
            pst.setInt(1, idGasto);
            return pst.executeUpdate() > 0;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al eliminar: " + e.toString());
            return false;
        }
    }
}