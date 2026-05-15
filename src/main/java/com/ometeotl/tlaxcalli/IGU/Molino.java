
package com.ometeotl.tlaxcalli.IGU;

public class Molino extends javax.swing.JFrame {

    // Constructor
    public Molino() {
        initComponents();
        setIconImage(new javax.swing.ImageIcon(getClass().getResource("/imagen/transparencia.png")).getImage());
        cargarDatosDelDia(); // <--- AGREGAR ESTO
    }

    // Método nuevo para buscar y llenar los campos
    private void cargarDatosDelDia() {
        com.ometeotl.tlaxcalli.PERSISTENCIA.MolinoDAO dao = new com.ometeotl.tlaxcalli.PERSISTENCIA.MolinoDAO();
        
        // Consultamos
        double[] datosHoy = dao.consultarReporteHoy();
        
        if (datosHoy != null) {
            // Si hay datos, los ponemos en los cuadros de texto
            t_botes.setText(String.valueOf(datosHoy[0]));
            t_harina.setText(String.valueOf(datosHoy[1]));
            t_desperdicio.setText(String.valueOf(datosHoy[2]));
            
            // Opcional: Cambiar color para indicar que ya existe info
            t_botes.setBackground(new java.awt.Color(255, 255, 200)); // Amarillo clarito
            t_harina.setBackground(new java.awt.Color(255, 255, 200));
            t_desperdicio.setBackground(new java.awt.Color(255, 255, 200));
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        t_botes = new javax.swing.JTextField();
        t_harina = new javax.swing.JTextField();
        t_desperdicio = new javax.swing.JTextField();
        b_guardarR = new javax.swing.JButton();
        b_salir = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Molino");
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        jLabel1.setText("DATOS DE MOLINO");

        jLabel2.setText("Botes elaborados:");

        jLabel3.setText("Harina utilizada:");

        jLabel4.setText("Desperdicio o sobrante:");

        t_botes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                t_botesActionPerformed(evt);
            }
        });

        t_harina.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                t_harinaActionPerformed(evt);
            }
        });

        t_desperdicio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                t_desperdicioActionPerformed(evt);
            }
        });

        b_guardarR.setText("Guardar");
        b_guardarR.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                b_guardarRActionPerformed(evt);
            }
        });

        b_salir.setText("Salir");
        b_salir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                b_salirActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(44, 44, 44)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2)
                            .addComponent(jLabel4)
                            .addComponent(jLabel3))
                        .addGap(37, 37, 37)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(t_harina, javax.swing.GroupLayout.PREFERRED_SIZE, 138, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(t_desperdicio, javax.swing.GroupLayout.PREFERRED_SIZE, 138, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(t_botes, javax.swing.GroupLayout.PREFERRED_SIZE, 138, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(153, 153, 153)
                        .addComponent(jLabel1)))
                .addContainerGap(57, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(b_guardarR, javax.swing.GroupLayout.DEFAULT_SIZE, 86, Short.MAX_VALUE)
                    .addComponent(b_salir, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(143, 143, 143))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addGap(35, 35, 35)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(t_botes, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(t_harina, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(t_desperdicio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(b_guardarR, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 36, Short.MAX_VALUE)
                .addComponent(b_salir)
                .addGap(28, 28, 28))
        );

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 400, 300));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void t_botesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_t_botesActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_t_botesActionPerformed

    private void t_harinaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_t_harinaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_t_harinaActionPerformed

    private void t_desperdicioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_t_desperdicioActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_t_desperdicioActionPerformed

    private void b_salirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_b_salirActionPerformed
        this.setVisible(false);
    }//GEN-LAST:event_b_salirActionPerformed

    private void b_guardarRActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_b_guardarRActionPerformed
                                          
        try {
            // 1. OBTENER DATOS DE LA INTERFAZ (Nuevos valores)
            double botesNew = Double.parseDouble(t_botes.getText());
            double harinaNew = Double.parseDouble(t_harina.getText());
            double despNew = Double.parseDouble(t_desperdicio.getText());
            
            // 2. FÓRMULAS DE PRODUCCIÓN
            double KILOS_MASA_POR_BOTE = 19.0; 
            double masaNatural = botesNew * KILOS_MASA_POR_BOTE;
            double masaTotal = masaNatural + (harinaNew * 2.5); 
            double ventaMasa = 0.0; // Ajustar si tienes este dato
            double masaParaTortilla = masaTotal - ventaMasa - despNew;
            if (masaParaTortilla < 0) masaParaTortilla = 0;
            double RENDIMIENTO_TORTILLA = masaParaTortilla / 18; 
            double tortillaElaborada = 16 * RENDIMIENTO_TORTILLA;

            // 3. CONSULTAR SI YA EXISTE REGISTRO
            com.ometeotl.tlaxcalli.PERSISTENCIA.MolinoDAO dao = new com.ometeotl.tlaxcalli.PERSISTENCIA.MolinoDAO();
            double[] datosViejos = dao.consultarReporteHoy();
            
            if (datosViejos == null) {
                // --- ESCENARIO A: NO HAY DATOS (REGISTRO NUEVO) ---
                boolean exito = dao.guardarReporteDiario(1, botesNew, harinaNew, despNew, 
                                        masaNatural, masaTotal, ventaMasa, 
                                        masaParaTortilla, tortillaElaborada);
                if (exito) {
                    javax.swing.JOptionPane.showMessageDialog(this, "✅ Producción del día registrada.");
                    this.setVisible(false); // Cerramos ventana
                } else {
                    javax.swing.JOptionPane.showMessageDialog(this, "❌ Error al guardar.");
                }

            } else {
                // --- ESCENARIO B: YA HAY DATOS (ACTUALIZACIÓN) ---
                
                // Preparamos el mensaje de comparación
                String mensaje = "⚠️ YA EXISTE UN REGISTRO DEL DÍA DE HOY.\n\n" +
                                 "Datos Anteriores vs Nuevos:\n" +
                                 "Botes:       " + datosViejos[0] + "  ->  " + botesNew + "\n" +
                                 "Harina:      " + datosViejos[1] + "  ->  " + harinaNew + "\n" +
                                 "Desperdicio: " + datosViejos[2] + "  ->  " + despNew + "\n\n" +
                                 "¿Desea SOBRESCRIBIR la información con los nuevos datos?";

                int confirmacion = javax.swing.JOptionPane.showConfirmDialog(this, mensaje, 
                        "Confirmar Actualización", javax.swing.JOptionPane.YES_NO_OPTION, javax.swing.JOptionPane.WARNING_MESSAGE);
                
                if (confirmacion == javax.swing.JOptionPane.YES_OPTION) {
                    boolean actualizado = dao.actualizarReporteDiario(botesNew, harinaNew, despNew, 
                                            masaNatural, masaTotal, ventaMasa, 
                                            masaParaTortilla, tortillaElaborada);
                    
                    if (actualizado) {
                         javax.swing.JOptionPane.showMessageDialog(this, "✅ Datos actualizados correctamente.");
                         this.setVisible(false);
                    } else {
                         javax.swing.JOptionPane.showMessageDialog(this, "❌ Error al actualizar.");
                    }
                }
            }

        } catch (NumberFormatException e) {
            javax.swing.JOptionPane.showMessageDialog(this, "Escribe solo números válidos.");
        }

    }//GEN-LAST:event_b_guardarRActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton b_guardarR;
    private javax.swing.JButton b_salir;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JTextField t_botes;
    private javax.swing.JTextField t_desperdicio;
    private javax.swing.JTextField t_harina;
    // End of variables declaration//GEN-END:variables

    // MÉTODO PARA LIMPIAR EL FORMULARIO DE MOLINO
    private void limpiarCampos() {
        t_botes.setText("");
        t_harina.setText("");
        t_desperdicio.setText("");
        // Colocamos el foco en el primer campo para seguir capturando rápido
        t_botes.requestFocus(); 
    }

}
