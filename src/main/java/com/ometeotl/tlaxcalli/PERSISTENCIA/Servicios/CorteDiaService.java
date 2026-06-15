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
            con.setAutoCommit(false); // 🛡️ Escudo activado

            // =====================================================================
            // PASO A: Guardar el registro del Empleado actual (Repartidor o Mostrador)
            // =====================================================================
            boolean exitoVenta = ventasDAO.guardarCorteCompleto(con, idEmpleado, kTortillaRep, kTortillaPub, 
                                                                kMasa, modeloProductos, modeloGastos);
            if (!exitoVenta) {
                throw new SQLException("Fallo interno al guardar los datos del corte.");
            }

            // =====================================================================
            // PASO B: ¡EL EFECTO CASCADA CORREGIDO!
            // =====================================================================
            int ID_MOSTRADOR = 2; 
            
            // Si estamos guardando a un Repartidor, recalculamos el Mostrador en automático
            if (idEmpleado != ID_MOSTRADOR) {
                
                // 1. Traemos la producción bruta de tortilla que calculó el molino en la mañana
                double produccionBrutaTortilla = molinoDAO.obtenerTotalTortillaHoy(con);
                
                // 2. Traemos TODA la tortilla vendida por los repartidores hoy (Reparto + Venta Directa)
                double totalTortillaVendidaRepartidores = ventasDAO.obtenerTotalRepartoHoy(con);
                
                // 3. Traemos TODA la masa cruda que vendieron los repartidores hoy
                // (Cada kilo de masa vendido es masa que NO se convirtió en tortilla)
                double totalMasaVendidaRepartidores = ventasDAO.obtenerTotalMasaHoy(con);
                
                // Convertimos esos kilos de masa a su equivalente en tortilla usando su factor (16/18)
                double tortillaQueNoSeHizoPorVenderMasa = totalMasaVendidaRepartidores * (16.0 / 18.0);
                
                // 4. LA FÓRMULA MAESTRA:
                // Lo que le queda al Mostrador es: Lo que hizo el molino - Las tortillas que se llevaron - La masa que vendieron
                double sobranteMostrador = produccionBrutaTortilla - totalTortillaVendidaRepartidores - tortillaQueNoSeHizoPorVenderMasa;
                
                if (sobranteMostrador < 0) sobranteMostrador = 0; 
                
                // 5. Inyectamos de forma quirúrgica el nuevo valor al Mostrador
                boolean exitoCascada = ventasDAO.actualizarKilosMostrador(con, ID_MOSTRADOR, sobranteMostrador);
                
                if (!exitoCascada) {
                    throw new SQLException("Fallo al aplicar el efecto cascada en el Mostrador.");
                }
            }

            con.commit(); // ✅ Todo cuadró matemáticamente -> Se graba en piedra
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
}