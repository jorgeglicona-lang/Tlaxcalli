package com.ometeotl.tlaxcalli.LOGICA;

import com.ometeotl.tlaxcalli.PERSISTENCIA.Interfaces.DAOFactory;
import com.ometeotl.tlaxcalli.PERSISTENCIA.Interfaces.IMolinoDAO;
import java.awt.Color;
import javax.swing.JFrame;
import static javax.swing.JOptionPane.WARNING_MESSAGE;
import static javax.swing.JOptionPane.YES_NO_OPTION;
import static javax.swing.JOptionPane.YES_OPTION;
import static javax.swing.JOptionPane.showConfirmDialog;
import static javax.swing.JOptionPane.showMessageDialog;
import javax.swing.JTextField;

public class C_Molino {
    private final IMolinoDAO dao = DAOFactory.getMolinoDAO();
    
    public void inicializarFormulario(JTextField tBotes, JTextField tHarina, JTextField tDesperdicio) {
        double[] datosHoy = dao.consultarReporteHoy();
        
        if (datosHoy==null) {
            return;
        }
        
        tBotes.setText(String.valueOf(datosHoy[0]));
        tHarina.setText(String.valueOf(datosHoy[1]));
        tDesperdicio.setText(String.valueOf(datosHoy[2]));
            
        Color amarilloClarito = new Color(255, 255, 200);
        
        tBotes.setBackground(amarilloClarito);
        tHarina.setBackground(amarilloClarito);
        tDesperdicio.setBackground(amarilloClarito);
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
            double masaParaTortilla = masaTotal - despNew;
            
            if (masaParaTortilla < 0) masaParaTortilla = 0;
            
            double RENDIMIENTO_TORTILLA = masaParaTortilla / 18; 
            double tortillaElaborada = 16 * RENDIMIENTO_TORTILLA;
            double[] datosViejos = dao.consultarReporteHoy();
            
            if (datosViejos == null) {
                // REGISTRO NUEVO
                if (dao.guardarReporteDiario(1, botesNew, harinaNew, despNew, 
                        masaNatural, masaTotal, 0.0, masaParaTortilla, tortillaElaborada)) {
                    showMessageDialog(ventana, "✅ Producción del día registrada con éxito.");
                    ventana.dispose();
                } else {
                    showMessageDialog(ventana, "❌ Error al guardar la producción.");
                }
            } else {
                // ACTUALIZACIÓN COMPARATIVA
                if(datosViejos[0]==botesNew && datosViejos[1]==harinaNew
                        && datosViejos[2]==despNew){
                    ventana.dispose();
                    return;
                }
                
                String mensaje = "⚠️ YA EXISTE UN REGISTRO DEL DÍA DE HOY.\n\n" +
                                 "Datos Anteriores vs Nuevos:\n" +
                                 "Botes:       " + datosViejos[0] + "  ->  " + botesNew + "\n" +
                                 "Harina:      " + datosViejos[1] + "  ->  " + harinaNew + "\n" +
                                 "Desperdicio: " + datosViejos[2] + "  ->  " + despNew + "\n\n" +
                                 "¿Desea SOBRESCRIBIR la información con los nuevos datos?";

                int confirmacion = showConfirmDialog(ventana, mensaje, 
                        "Confirmar Actualización", YES_NO_OPTION, WARNING_MESSAGE);
                
                if (confirmacion != YES_OPTION) {
                    return;
                }
                if (!dao.actualizarReporteDiario(botesNew, harinaNew, despNew, 
                        masaNatural, masaTotal, 0.0, masaParaTortilla, tortillaElaborada)) {
                    showMessageDialog(ventana, "❌ Error al actualizar.");
                    return;
                }
                
                showMessageDialog(ventana, "✅ Datos actualizados correctamente.");
                ventana.dispose();
            }
        } catch (NumberFormatException e) {
            showMessageDialog(ventana, "Por favor, escribe solo números válidos en los campos.");
        }
    }
}