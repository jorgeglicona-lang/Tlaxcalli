package com.ometeotl.tlaxcalli.IGU;

import com.ometeotl.tlaxcalli.HerramientasVisuales;
import static com.ometeotl.tlaxcalli.HerramientasVisuales.configurarBotonCerrar;
import com.ometeotl.tlaxcalli.LOGICA.C_GastosG;
import java.awt.Color;
import static java.awt.Color.RED;
import static java.awt.Color.WHITE;
import javax.swing.JOptionPane;

public class Gastos_Generales extends javax.swing.JFrame {
    
    private final C_GastosG controlador = new C_GastosG();
    
    public Gastos_Generales() {
        initComponents();
        
        controlador.cargarSemanaActual(T_GAd, TotalGAd);
        datePicker1.setDateToToday();
        datePicker2.setDateToToday();
        
        HerramientasVisuales.configurarBarraArrastre(this, jPanel3);
        configurarBotonCerrar(this, jBext, ext, WHITE,new Color(204, 204, 204), RED, WHITE,false,true);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jBext = new javax.swing.JPanel();
        ext = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        T_GAd = new javax.swing.JTable();
        jLabel2 = new javax.swing.JLabel();
        B_GyS = new javax.swing.JButton();
        B_SalirGAd = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        TotalGAd = new javax.swing.JTextField();
        B_AgregarGAd = new javax.swing.JButton();
        B_EliminarGAd = new javax.swing.JButton();
        B_BuscarG = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        datePicker1 = new com.github.lgooddatepicker.components.DatePicker();
        datePicker2 = new com.github.lgooddatepicker.components.DatePicker();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Gastos generales");
        setUndecorated(true);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel6.setText("Gastos Generales");
        jLabel6.setFont(new java.awt.Font("Copperplate Gothic Bold", 0, 24)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(0, 0, 0));
        jPanel1.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 30, 250, 30));

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));

        jBext.setBackground(new java.awt.Color(255, 255, 255));

        ext.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        ext.setText("X");
        ext.setFont(new java.awt.Font("Roboto Light", 0, 24)); // NOI18N

        javax.swing.GroupLayout jBextLayout = new javax.swing.GroupLayout(jBext);
        jBext.setLayout(jBextLayout);
        jBextLayout.setHorizontalGroup(
            jBextLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(ext, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 46, Short.MAX_VALUE)
        );
        jBextLayout.setVerticalGroup(
            jBextLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jBextLayout.createSequentialGroup()
                .addComponent(ext, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addGap(0, 564, Short.MAX_VALUE)
                .addComponent(jBext, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addComponent(jBext, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel1.add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 610, 40));

        T_GAd.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        jScrollPane1.setViewportView(T_GAd);

        jPanel1.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(37, 89, 300, 280));

        jLabel2.setText("Fecha :");
        jLabel2.setForeground(new java.awt.Color(0, 0, 0));
        jPanel1.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 80, -1, -1));

        B_GyS.setText("Guardar y Salir");
        B_GyS.addActionListener(this::B_GySActionPerformed);
        jPanel1.add(B_GyS, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 430, -1, -1));

        B_SalirGAd.setText("Salir");
        B_SalirGAd.addActionListener(this::B_SalirGAdActionPerformed);
        jPanel1.add(B_SalirGAd, new org.netbeans.lib.awtextra.AbsoluteConstraints(37, 441, -1, -1));

        jLabel4.setText("Total de gastos operativos");
        jLabel4.setForeground(new java.awt.Color(0, 0, 0));
        jPanel1.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 380, -1, -1));

        TotalGAd.setEditable(false);
        jPanel1.add(TotalGAd, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 380, 70, -1));

        B_AgregarGAd.setText("Agregar");
        B_AgregarGAd.addActionListener(this::B_AgregarGAdActionPerformed);
        jPanel1.add(B_AgregarGAd, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 300, -1, -1));

        B_EliminarGAd.setText("Eliminar");
        B_EliminarGAd.addActionListener(this::B_EliminarGAdActionPerformed);
        jPanel1.add(B_EliminarGAd, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 340, -1, -1));

        B_BuscarG.setText("Buscar");
        B_BuscarG.addActionListener(this::B_BuscarGActionPerformed);
        jPanel1.add(B_BuscarG, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 190, -1, -1));

        jLabel5.setForeground(new java.awt.Color(0, 0, 0));
        jLabel5.setText("A:");
        jPanel1.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 130, 20, -1));
        jPanel1.add(datePicker1, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 100, -1, -1));
        jPanel1.add(datePicker2, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 150, -1, -1));

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 610, 500));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void B_AgregarGAdActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_B_AgregarGAdActionPerformed
        controlador.agregarGasto(this, T_GAd, TotalGAd);
    }//GEN-LAST:event_B_AgregarGAdActionPerformed

    private void B_EliminarGAdActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_B_EliminarGAdActionPerformed
        controlador.eliminarGasto(this, T_GAd, TotalGAd);
    }//GEN-LAST:event_B_EliminarGAdActionPerformed

    private void B_GySActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_B_GySActionPerformed
        JOptionPane.showMessageDialog(this, "Cambios realizados con exito.");
        this.dispose();
    }//GEN-LAST:event_B_GySActionPerformed

    private void B_BuscarGActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_B_BuscarGActionPerformed
        controlador.buscarPorFechas(datePicker1.getDate().toString(), datePicker2.getDate().toString(), T_GAd, TotalGAd);
    }//GEN-LAST:event_B_BuscarGActionPerformed

    private void B_SalirGAdActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_B_SalirGAdActionPerformed
        this.dispose();
    }//GEN-LAST:event_B_SalirGAdActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton B_AgregarGAd;
    private javax.swing.JButton B_BuscarG;
    private javax.swing.JButton B_EliminarGAd;
    private javax.swing.JButton B_GyS;
    private javax.swing.JButton B_SalirGAd;
    private javax.swing.JTable T_GAd;
    private javax.swing.JTextField TotalGAd;
    private com.github.lgooddatepicker.components.DatePicker datePicker1;
    private com.github.lgooddatepicker.components.DatePicker datePicker2;
    private javax.swing.JLabel ext;
    private javax.swing.JPanel jBext;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    // End of variables declaration//GEN-END:variables
}

