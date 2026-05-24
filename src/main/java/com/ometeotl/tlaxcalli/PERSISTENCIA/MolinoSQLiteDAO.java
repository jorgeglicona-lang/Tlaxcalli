package com.ometeotl.tlaxcalli.PERSISTENCIA;

import com.ometeotl.tlaxcalli.PERSISTENCIA.Interfaces.IMolinoDAO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class MolinoSQLiteDAO implements IMolinoDAO {

    @Override
    public boolean guardarReporteDiario(int idEmp, double botes, double harina, double desp,
                                     double mNatural, double mTotal, double vMasa,
                                     double mTortilla, double tElaborada) {
        CSQLiteConnection conMngr = new CSQLiteConnection();
        // Usamos date('now', 'localtime') para estampar la fecha actual en texto YYYY-MM-DD
        String sql = "INSERT INTO Produccion_Diaria " +
                     "(Id_empleado, Botes_Maiz, Harina_Kg, Desperdicio_Kg, " +
                     "Masa_Natural_Kg, Masa_Total_Kg, Venta_Masa_Kg, " +
                     "Masa_Para_Tortilla_Kg, Tortilla_Elaborada_Kg, Fecha) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, date('now', 'localtime'))";
        
        try (Connection con = conMngr.establecerConexionPortatil();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, idEmp);
            ps.setDouble(2, botes);
            ps.setDouble(3, harina);
            ps.setDouble(4, desp);
            ps.setDouble(5, mNatural);
            ps.setDouble(6, mTotal);
            ps.setDouble(7, vMasa);
            ps.setDouble(8, mTortilla);
            ps.setDouble(9, tElaborada);
            
            ps.executeUpdate();
            return true;
        } catch (Exception e) {
            System.err.println("Error guardando producción SQLite: " + e.getMessage());
            return false;
        }
    }

    @Override
    public double obtenerTotalTortillaHoy() {
        CSQLiteConnection conMngr = new CSQLiteConnection();
        double total = 0.0;
        String sql = "SELECT SUM(Tortilla_Elaborada_Kg) FROM Produccion_Diaria WHERE Fecha = date('now', 'localtime')";
        
        try (Connection con = conMngr.establecerConexionPortatil();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                total = rs.getDouble(1);
            }
        } catch (Exception e) {
            System.err.println("Error obteniendo total tortilla SQLite: " + e.getMessage());
        }
        return total;
    }

    @Override
    public double[] consultarReporteHoy() {
        CSQLiteConnection conMngr = new CSQLiteConnection();
        double[] datos = null;
        String sql = "SELECT Botes_Maiz, Harina_Kg, Desperdicio_Kg FROM Produccion_Diaria WHERE Fecha = date('now', 'localtime')";
        
        try (Connection con = conMngr.establecerConexionPortatil();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                datos = new double[3];
                datos[0] = rs.getDouble("Botes_Maiz");
                datos[1] = rs.getDouble("Harina_Kg");
                datos[2] = rs.getDouble("Desperdicio_Kg");
            }
        } catch (Exception e) {
            System.err.println("Error consultando producción de hoy SQLite: " + e.getMessage());
        }
        return datos;
    }

    @Override
    public boolean actualizarReporteDiario(double botes, double harina, double desp,
                                        double mNatural, double mTotal, double vMasa,
                                        double mTortilla, double tElaborada) {
        CSQLiteConnection conMngr = new CSQLiteConnection();
        String sql = "UPDATE Produccion_Diaria SET " +
                     "Botes_Maiz=?, Harina_Kg=?, Desperdicio_Kg=?, " +
                     "Masa_Natural_Kg=?, Masa_Total_Kg=?, Venta_Masa_Kg=?, " +
                     "Masa_Para_Tortilla_Kg=?, Tortilla_Elaborada_Kg=? " +
                     "WHERE Fecha = date('now', 'localtime')";
        
        try (Connection con = conMngr.establecerConexionPortatil();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setDouble(1, botes);
            ps.setDouble(2, harina);
            ps.setDouble(3, desp);
            ps.setDouble(4, mNatural);
            ps.setDouble(5, mTotal);
            ps.setDouble(6, vMasa);
            ps.setDouble(7, mTortilla);
            ps.setDouble(8, tElaborada);
            
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            System.err.println("Error actualizando producción SQLite: " + e.getMessage());
            return false;
        }
    }
}