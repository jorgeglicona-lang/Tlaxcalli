package com.mycompany.tlaxcalli.Igu;

import com.mycompany.tlaxcalli.Persistencia.GastosAdmDAO;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

public class Gastos_Generales extends javax.swing.JFrame {
    
    private com.mycompany.tlaxcalli.Logica.C_GastosG controlador = new com.mycompany.tlaxcalli.Logica.C_GastosG();

    public Gastos_Generales() {
        initComponents();
        llenarTablaSemanal(); 
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
        jLabel1 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
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

        jPanel1.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(37, 70, 529, 219));

        jLabel2.setForeground(new java.awt.Color(0, 0, 0));
        jLabel2.setText("Fecha :");
        jPanel1.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 30, -1, -1));

        B_GyS.setText("Guardar y Salir");
        B_GyS.addActionListener(this::B_GySActionPerformed);
        jPanel1.add(B_GyS, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 440, -1, -1));

        B_SalirGAd.setText("Salir");
        jPanel1.add(B_SalirGAd, new org.netbeans.lib.awtextra.AbsoluteConstraints(37, 441, -1, -1));

        jLabel4.setForeground(new java.awt.Color(0, 0, 0));
        jLabel4.setText("Total de gastos operativos");
        jPanel1.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 340, -1, -1));

        TotalGAd.setEditable(false);
        jPanel1.add(TotalGAd, new org.netbeans.lib.awtextra.AbsoluteConstraints(502, 335, 70, -1));

        B_AgregarGAd.setText("Agregar");
        B_AgregarGAd.addActionListener(this::B_AgregarGAdActionPerformed);
        jPanel1.add(B_AgregarGAd, new org.netbeans.lib.awtextra.AbsoluteConstraints(590, 90, -1, -1));

        B_EliminarGAd.setText("Eliminar");
        B_EliminarGAd.addActionListener(this::B_EliminarGAdActionPerformed);
        jPanel1.add(B_EliminarGAd, new org.netbeans.lib.awtextra.AbsoluteConstraints(590, 130, -1, -1));

        B_BuscarG.setText("Buscar");
        jPanel1.add(B_BuscarG, new org.netbeans.lib.awtextra.AbsoluteConstraints(590, 30, -1, -1));

        jLabel5.setForeground(new java.awt.Color(0, 0, 0));
        jLabel5.setText("A:");
        jPanel1.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 30, -1, -1));

        jLabel1.setForeground(new java.awt.Color(0, 0, 0));
        jLabel1.setText("calendario");
        jPanel1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 30, -1, -1));

        jLabel3.setForeground(new java.awt.Color(0, 0, 0));
        jLabel3.setText("calendario");
        jPanel1.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 30, -1, -1));

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 730, 500));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void B_AgregarGAdActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_B_AgregarGAdActionPerformed
        JTextField txtDesc = new JTextField();
    JTextField txtMonto = new JTextField(); 
    
    Object[] message = { "Descripción del Gasto:", txtDesc, "Monto ($):", txtMonto };
    int option = JOptionPane.showConfirmDialog(this, message, "Nuevo Gasto Administrativo", JOptionPane.OK_CANCEL_OPTION);
    
    if (option == JOptionPane.OK_OPTION) {
        try {
            // El trim() quita los espacios en blanco accidentales al inicio y al final
            String descripcion = txtDesc.getText().trim();
            double monto = Double.parseDouble(txtMonto.getText().trim());
            
            // --- NUEVAS VALIDACIONES DE SEGURIDAD ---
            if (monto <= 0) {
                JOptionPane.showMessageDialog(this, "Jefe, el monto tiene que ser mayor a cero. No hay gastos gratis.");
                return; // Cortamos la ejecución aquí
            }
            if (descripcion.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Jefe, no puede dejar la descripción vacía. Necesitamos saber en qué se gastó.");
                return; // Cortamos la ejecución aquí
            }
            // ----------------------------------------
            
            if (controlador.agregarGasto(descripcion, monto)) {
                JOptionPane.showMessageDialog(this, "Gasto registrado correctamente.");
                llenarTablaSemanal(); // Actualiza la tabla y el total al instante
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Jefe, el monto debe ser un número válido (ej. 150.50).");
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

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton B_AgregarGAd;
    private javax.swing.JButton B_BuscarG;
    private javax.swing.JButton B_EliminarGAd;
    private javax.swing.JButton B_GyS;
    private javax.swing.JButton B_SalirGAd;
    private javax.swing.JTable T_GAd;
    private javax.swing.JTextField TotalGAd;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    // End of variables declaration//GEN-END:variables
}

