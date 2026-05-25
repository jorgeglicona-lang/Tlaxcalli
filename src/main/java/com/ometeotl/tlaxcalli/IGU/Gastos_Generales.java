package com.ometeotl.tlaxcalli.IGU;

import com.ometeotl.tlaxcalli.PERSISTENCIA.GastosAdmDAO;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

public class Gastos_Generales extends javax.swing.JFrame {
    
    private com.ometeotl.tlaxcalli.LOGICA.C_GastosG controlador = new com.ometeotl.tlaxcalli.LOGICA.C_GastosG();

    public Gastos_Generales() {
        initComponents();
        llenarTablaSemanal(); 
        datePicker1.setDateToToday();
        datePicker2.setDateToToday();
    }

    public void llenarTablaSemanal() {
        // Le pasamos la tabla y el campo de texto para que el controlador haga todo
        controlador.cargarSemanaActual(T_GAd, TotalGAd);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
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
        jLabel6 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Gastos generales");
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

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

        jLabel2.setForeground(new java.awt.Color(0, 0, 0));
        jLabel2.setText("Fecha :");
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

        jLabel6.setFont(new java.awt.Font("Copperplate Gothic Light", 0, 24)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(0, 0, 0));
        jLabel6.setText("Gastos Generales");
        jPanel1.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 30, 240, 30));

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 610, 500));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void B_AgregarGAdActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_B_AgregarGAdActionPerformed
        JTextField txtDesc = new JTextField();
    JTextField txtMonto = new JTextField(); 
    com.github.lgooddatepicker.components.DatePicker dpFecha = new com.github.lgooddatepicker.components.DatePicker();
    dpFecha.setDateToToday(); // Por defecto hoy
    
    Object[] message = { 
        "Descripción del Gasto:", txtDesc, 
        "Monto ($):", txtMonto,
        "Fecha del gasto:", dpFecha 
    };
    
    int option = JOptionPane.showConfirmDialog(this, message, "Nuevo Gasto Administrativo", JOptionPane.OK_CANCEL_OPTION);
    
    if (option == JOptionPane.OK_OPTION) {
        try {
            String descripcion = txtDesc.getText().trim();
            double monto = Double.parseDouble(txtMonto.getText().trim());
            String fecha = dpFecha.getDate().toString();
            
            if (monto > 0 && !descripcion.isEmpty()) {
                if (controlador.agregarGasto(descripcion, monto, fecha)) {
                    JOptionPane.showMessageDialog(this, "Gasto registrado.");
                    llenarTablaSemanal(); // Refresca a la semana actual o puede dejar la búsqueda activa
                }
            } else {
                JOptionPane.showMessageDialog(this, "Revise los datos. El monto debe ser mayor a 0.");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error en los datos ingresados.");
        }
    }
    }//GEN-LAST:event_B_AgregarGAdActionPerformed

    private void B_EliminarGAdActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_B_EliminarGAdActionPerformed
        int fila = T_GAd.getSelectedRow();
    if (fila == -1) {
        JOptionPane.showMessageDialog(this, "Debe seleccionar un gasto de la tabla.");
        return;
    }
    
    int id = (int) T_GAd.getValueAt(fila, 0); 
    int confirmar = JOptionPane.showConfirmDialog(this, "¿Seguro que quiere eliminar este gasto?");
    
    if (confirmar == JOptionPane.YES_OPTION) {
        if (controlador.eliminarGasto(id)) {
            llenarTablaSemanal(); // Actualiza la tabla y el total al instante
        }
    }
    }//GEN-LAST:event_B_EliminarGAdActionPerformed

    private void B_GySActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_B_GySActionPerformed
        JOptionPane.showMessageDialog(this, "Cambios realizados con exito.");
        this.dispose();
    }//GEN-LAST:event_B_GySActionPerformed

    private void B_BuscarGActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_B_BuscarGActionPerformed
    if (datePicker1.getDate() == null || datePicker2.getDate() == null) {
        JOptionPane.showMessageDialog(this, "Seleccione ambas fechas, Jefe.");
        return;
    }
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
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    // End of variables declaration//GEN-END:variables
}

