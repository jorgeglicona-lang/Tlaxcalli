package com.ometeotl.tlaxcalli.PERSISTENCIA.Servicios;

import com.ometeotl.tlaxcalli.PERSISTENCIA.CSQLiteConnection;
import com.ometeotl.tlaxcalli.PERSISTENCIA.Interfaces.DAOFactory;
import com.ometeotl.tlaxcalli.PERSISTENCIA.Interfaces.IMolinoDAO;
import com.ometeotl.tlaxcalli.PERSISTENCIA.Interfaces.IVentasDAO;
import java.sql.Connection;
import java.sql.SQLException;
import javax.swing.table.DefaultTableModel;

public class CorteDiaService {
    
    private final CSQLiteConnection conMngr = new CSQLiteConnection();
    private final IVentasDAO ventasDAO = DAOFactory.getVentasDAO();
    private final IMolinoDAO molinoDAO = DAOFactory.getMolinoDAO();

    public boolean procesarVentaYCascada(int idEmpleado, double kTortillaRep, double kTortillaPub, 
                                         double kMasa, DefaultTableModel modeloProductos, 
                                         DefaultTableModel modeloGastos) {
        Connection con = null;
        try {
            con = conMngr.establecerConexionPortatil();
            con.setAutoCommit(false); 
            boolean exitoVenta = ventasDAO.guardarCorteCompleto(con, idEmpleado, kTortillaRep, kTortillaPub, 
                                                                kMasa, modeloProductos, modeloGastos);
            if (!exitoVenta) {
                throw new SQLException("Fallo interno al guardar los datos del corte.");
            }

            int idMostrador = ventasDAO.obtenerIdMostrador(con);

            if (idEmpleado != idMostrador) {
                // Solo si Mostrador ya cerró su corte se actualiza en la BD; 
                // mientras tanto, los kilos de Mostrador se mantienen dinámicos en cascada
                boolean mostradorYaGuardo = ventasDAO.existeCorteMostradorHoy(con);
                
                if (mostradorYaGuardo) {
                    double produccionBrutaTortilla = molinoDAO.obtenerTotalTortillaHoy(con);
                    double totalTortillaVendidaRepartidores = ventasDAO.obtenerTotalRepartoHoy(con);
                    double tortillaQueNoSeHizoPorVenderMasa = ventasDAO.obtenerTotalMasaHoy(con);
                    
                    double sobranteMostrador = produccionBrutaTortilla - totalTortillaVendidaRepartidores - tortillaQueNoSeHizoPorVenderMasa;
                    if (sobranteMostrador < 0) sobranteMostrador = 0; 
                    
                    boolean exitoCascada = ventasDAO.actualizarKilosMostrador(con, idMostrador, sobranteMostrador);
                    if (!exitoCascada) {
                        throw new SQLException("Fallo al aplicar el efecto cascada en el Mostrador.");
                    }
                }
            }

            con.commit();
            return true;

        } catch (Exception e) {
            System.err.println("Error en transacción. Aplicando Rollback: " + e.getMessage());
            if (con != null) {
                try { con.rollback(); } catch (SQLException ex) { }
            }
            return false;
        } finally {
            if (con != null) {
                try {
                    con.setAutoCommit(true); 
                    con.close();
                } catch (SQLException ex) { }
            }
        }
    }
    
    public boolean sincronizarMostradorPasivo() {
        Connection con = null;
        try {
            con = conMngr.establecerConexionPortatil();
            con.setAutoCommit(false); 

            int idMostrador = ventasDAO.obtenerIdMostrador(con);
            boolean mostradorYaGuardo = ventasDAO.existeCorteMostradorHoy(con);

            if (mostradorYaGuardo) {
                double produccionBrutaTortilla = molinoDAO.obtenerTotalTortillaHoy(con);
                double totalTortillaVendidaRepartidores = ventasDAO.obtenerTotalRepartoHoy(con);
                double tortillaQueNoSeHizoPorVenderMasa = ventasDAO.obtenerTotalMasaHoy(con);
                
                double sobranteMostrador = produccionBrutaTortilla - totalTortillaVendidaRepartidores - tortillaQueNoSeHizoPorVenderMasa;
                if (sobranteMostrador < 0) sobranteMostrador = 0;

                ventasDAO.actualizarKilosMostrador(con, idMostrador, sobranteMostrador);
            }

            con.commit();
            return true;

        } catch (Exception e) {
            System.err.println("Error en sincronización pasiva de mostrador: " + e.getMessage());
            if (con != null) {
                try { con.rollback(); } catch (SQLException ex) { }
            }
            return false;
        } finally {
            if (con != null) {
                try {
                    con.setAutoCommit(true);
                    con.close();
                } catch (SQLException ex) { }
            }
        }
    }
}