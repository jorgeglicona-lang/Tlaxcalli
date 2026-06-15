package com.ometeotl.tlaxcalli.PERSISTENCIA;

import com.ometeotl.tlaxcalli.PERSISTENCIA.Interfaces.IGastosGeneralesDAO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.table.DefaultTableModel;

public class GastosGeneralesSQLiteDAO implements IGastosGeneralesDAO {
    private final CSQLiteConnection conMngr = new CSQLiteConnection();
    
    @Override
    public DefaultTableModel obtenerGastosSemana() {
        DefaultTableModel modelo = new DefaultTableModel();
        modelo.addColumn("ID");
        modelo.addColumn("Descripción");
        modelo.addColumn("Monto ($)");
        modelo.addColumn("Fecha");
        
        //Comparamos que el número de semana y año de la fecha sean iguales al número de semana y año de HOY.
        String sql = "SELECT Id_gasto_adm, Descripcion, Monto, Fecha " +
                     "FROM Gastos_Administrativos " +
                     "WHERE strftime('%W', Fecha) = strftime('%W', 'now', 'localtime') " +
                     "AND strftime('%Y', Fecha) = strftime('%Y', 'now', 'localtime') " +
                     "ORDER BY Fecha DESC";
                     
        try (Connection con = conMngr.establecerConexionPortatil();
             PreparedStatement pst = con.prepareStatement(sql);
             ResultSet rs = pst.executeQuery()) {
            
            while (rs.next()) {
                modelo.addRow(new Object[]{
                    rs.getInt("Id_gasto_adm"), 
                    rs.getString("Descripcion"), 
                    rs.getDouble("Monto"), 
                    rs.getString("Fecha")
                });
            }
        } catch (SQLException e) {
            System.err.println("Error cargando gastos semanales SQLite: " + e.getMessage());
        }
        return modelo;
    }

    @Override
    public DefaultTableModel obtenerGastosPorRango(String inicio, String fin) {
        DefaultTableModel modelo = new DefaultTableModel();
        modelo.addColumn("ID"); 
        modelo.addColumn("Descripción"); 
        modelo.addColumn("Monto ($)"); 
        modelo.addColumn("Fecha");
        
        // Usamos date() para asegurarnos de que compare correctamente los strings YYYY-MM-DD
        String sql = "SELECT Id_gasto_adm, Descripcion, Monto, Fecha " +
                     "FROM Gastos_Administrativos " +
                     "WHERE date(Fecha) BETWEEN date(?) AND date(?) ORDER BY Fecha DESC";
                     
        try (Connection con = conMngr.establecerConexionPortatil();
             PreparedStatement pst = con.prepareStatement(sql)) {
            pst.setString(1, inicio);
            pst.setString(2, fin);
            try (ResultSet rs = pst.executeQuery()) {
                while (rs.next()) {
                    modelo.addRow(new Object[]{
                        rs.getInt("Id_gasto_adm"), 
                        rs.getString("Descripcion"), 
                        rs.getDouble("Monto"), 
                        rs.getString("Fecha")
                    });
                }
            }
        } catch (SQLException e) {
            System.err.println("Error buscando gastos por rango SQLite: " + e.getMessage());
        }
        return modelo;
    }

    @Override
    public boolean registrarGasto(String descripcion, double monto, String fecha) {
        String sql = "INSERT INTO Gastos_Administrativos (Descripcion, Monto, Fecha) VALUES (?, ?, ?)";
        
        try (Connection con = conMngr.establecerConexionPortatil();
             PreparedStatement pst = con.prepareStatement(sql)) {
            pst.setString(1, descripcion);
            pst.setDouble(2, monto);
            pst.setString(3, fecha); 
            return pst.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error registrando gasto SQLite: " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean eliminarGasto(int idGasto) {
        String sql = "DELETE FROM Gastos_Administrativos WHERE Id_gasto_adm = ?";
        
        try (Connection con = conMngr.establecerConexionPortatil();
             PreparedStatement pst = con.prepareStatement(sql)) {
            pst.setInt(1, idGasto);
            return pst.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error eliminando gasto SQLite: " + e.getMessage());
            return false;
        }
    }
}