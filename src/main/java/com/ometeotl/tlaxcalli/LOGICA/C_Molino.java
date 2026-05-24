package com.ometeotl.tlaxcalli.LOGICA;

import com.ometeotl.tlaxcalli.PERSISTENCIA.Interfaces.DAOFactory;
import com.ometeotl.tlaxcalli.PERSISTENCIA.Interfaces.IMolinoDAO;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

public class C_Molino {

    public void inicializarFormulario(JTextField tBotes, JTextField tHarina, JTextField tDesperdicio) {
        IMolinoDAO dao = DAOFactory.getMolinoDAO();
        double[] datosHoy = dao.consultarReporteHoy();
        
        if (datosHoy != null) {
            tBotes.setText(String.valueOf(datosHoy[0]));
            tHarina.setText(String.valueOf(datosHoy[1]));
            tDesperdicio.setText(String.valueOf(datosHoy[2]));
            
            java.awt.Color amarilloClarito = new java.awt.Color(255, 255, 200);
            tBotes.setBackground(amarilloClarito);
            tHarina.setBackground(amarilloClarito);
            tDesperdicio.setBackground(amarilloClarito);
        }
    }

    public void procesarGuardado(JFrame ventana, String botesStr, String harinaStr, String despStr) {
        try {
            double botesNew = Double.parseDouble(botesStr);
            double harinaNew = Double.parseDouble(harinaStr);
            double despNew = Double.parseDouble(despStr);
            
            // --- REGLAS DE NEGOCIO MATEMÁTICAS (Encapsuladas aquí) ---
            double KILOS_MASA_POR_BOTE = 19.0; 
            double masaNatural = botesNew * KILOS_MASA_POR_BOTE;
            double masaTotal = masaNatural + (harinaNew * 2.5); 
            double ventaMasa = 0.0; 
            double masaParaTortilla = masaTotal - ventaMasa - despNew;
            if (masaParaTortilla < 0) masaParaTortilla = 0;
            double RENDIMIENTO_TORTILLA = masaParaTortilla / 18; 
            double tortillaElaborada = 16 * RENDIMIENTO_TORTILLA;

            IMolinoDAO dao = DAOFactory.getMolinoDAO();
            double[] datosViejos = dao.consultarReporteHoy();
            
            if (datosViejos == null) {
                // REGISTRO NUEVO
                if (dao.guardarReporteDiario(1, botesNew, harinaNew, despNew, 
                        masaNatural, masaTotal, ventaMasa, masaParaTortilla, tortillaElaborada)) {
                    JOptionPane.showMessageDialog(ventana, "✅ Producción del día registrada con éxito.");
                    ventana.setVisible(false);
                } else {
                    JOptionPane.showMessageDialog(ventana, "❌ Error al guardar la producción.");
                }
            } else {
                // ACTUALIZACIÓN COMPARATIVA
                String mensaje = "⚠️ YA EXISTE UN REGISTRO DEL DÍA DE HOY.\n\n" +
                                 "Datos Anteriores vs Nuevos:\n" +
                                 "Botes:       " + datosViejos[0] + "  ->  " + botesNew + "\n" +
                                 "Harina:      " + datosViejos[1] + "  ->  " + harinaNew + "\n" +
                                 "Desperdicio: " + datosViejos[2] + "  ->  " + despNew + "\n\n" +
                                 "¿Desea SOBRESCRIBIR la información con los nuevos datos?";

                int confirmacion = JOptionPane.showConfirmDialog(ventana, mensaje, 
                        "Confirmar Actualización", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
                
                if (confirmacion == JOptionPane.YES_OPTION) {
                    if (dao.actualizarReporteDiario(botesNew, harinaNew, despNew, 
                            masaNatural, masaTotal, ventaMasa, masaParaTortilla, tortillaElaborada)) {
                        JOptionPane.showMessageDialog(ventana, "✅ Datos actualizados correctamente.");
                        ventana.setVisible(false);
                    } else {
                        JOptionPane.showMessageDialog(ventana, "❌ Error al actualizar.");
                    }
                }
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(ventana, "Por favor, escribe solo números válidos en los campos.");
        }
    }
}