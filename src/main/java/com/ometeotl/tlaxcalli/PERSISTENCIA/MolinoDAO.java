package com.ometeotl.tlaxcalli.PERSISTENCIA;

import java.sql.Connection;
import java.sql.PreparedStatement;
import javax.swing.JOptionPane;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MolinoDAO {

    public boolean guardarReporteDiario(int idEmp, double botes, double harina, double desp,
                                    double mNatural, double mTotal, double vMasa,
                                    double mTortilla, double tElaborada) {
    
    Connection con = new Cconection().establecerConexion();
    String sql = "INSERT INTO Produccion_Diaria " +
                 "(Id_empleado, Botes_Maiz, Harina_Kg, Desperdicio_Kg, " +
                 "Masa_Natural_Kg, Masa_Total_Kg, Venta_Masa_Kg, " +
                 "Masa_Para_Tortilla_Kg, Tortilla_Elaborada_Kg, Fecha) " +
                 "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, GETDATE())";
    
    try {
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setInt(1, idEmp);
        ps.setDouble(2, botes);
        ps.setDouble(3, harina);
        ps.setDouble(4, desp);
        // Los calculados:
        ps.setDouble(5, mNatural);
        ps.setDouble(6, mTotal);
        ps.setDouble(7, vMasa);
        ps.setDouble(8, mTortilla);
        ps.setDouble(9, tElaborada);
        
        ps.executeUpdate();
        con.close();
        return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
    // MÉTODO PARA SABER CUÁNTA TORTILLA SE HIZO HOY
    public double obtenerTotalTortillaHoy() {
        Connection con = null;
        double total = 0.0;
        try {
            con = new Cconection().establecerConexion();
            // Sumamos toda la tortilla elaborada con fecha de HOY
            String sql = "SELECT SUM(Tortilla_Elaborada_Kg) FROM Produccion_Diaria WHERE Fecha = CAST(GETDATE() AS DATE)";
            
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            
            if (rs.next()) {
                total = rs.getDouble(1);
            }
        } catch (Exception e) {
            System.err.println("Error obteniendo producción: " + e);
        } finally {
            try { if (con != null) con.close(); } catch (Exception ex) {}
        }
        return total;
    }
    // 1. MÉTODO PARA CONSULTAR SI YA HAY REGISTRO HOY
    // Retorna un array de doubles [botes, harina, desperdicio] o null si no hay nada
    public double[] consultarReporteHoy() {
        Connection con = null;
        double[] datos = null;
        
        try {
            con = new Cconection().establecerConexion();
            String sql = "SELECT Botes_Maiz, Harina_Kg, Desperdicio_Kg FROM Produccion_Diaria WHERE Fecha = CAST(GETDATE() AS DATE)";
            
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            
            if (rs.next()) {
                datos = new double[3];
                datos[0] = rs.getDouble("Botes_Maiz");
                datos[1] = rs.getDouble("Harina_Kg");
                datos[2] = rs.getDouble("Desperdicio_Kg");
            }
            
        } catch (Exception e) {
            System.err.println("Error consultando molino hoy: " + e);
        } finally {
            try { if (con != null) con.close(); } catch (SQLException ex) {}
        }
        return datos;
    }

    // 2. MÉTODO PARA ACTUALIZAR EL REGISTRO DE HOY
    public boolean actualizarReporteDiario(double botes, double harina, double desp,
                                    double mNatural, double mTotal, double vMasa,
                                    double mTortilla, double tElaborada) {
        
        Connection con = null;
        try {
            con = new Cconection().establecerConexion();
            // Actualizamos SOLO el registro de hoy
            String sql = "UPDATE Produccion_Diaria SET " +
                         "Botes_Maiz=?, Harina_Kg=?, Desperdicio_Kg=?, " +
                         "Masa_Natural_Kg=?, Masa_Total_Kg=?, Venta_Masa_Kg=?, " +
                         "Masa_Para_Tortilla_Kg=?, Tortilla_Elaborada_Kg=? " +
                         "WHERE Fecha = CAST(GETDATE() AS DATE)";
            
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setDouble(1, botes);
            ps.setDouble(2, harina);
            ps.setDouble(3, desp);
            ps.setDouble(4, mNatural);
            ps.setDouble(5, mTotal);
            ps.setDouble(6, vMasa);
            ps.setDouble(7, mTortilla);
            ps.setDouble(8, tElaborada);
            
            int filas = ps.executeUpdate();
            return filas > 0;
            
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            try { if (con != null) con.close(); } catch (SQLException ex) {}
        }
    }
}
