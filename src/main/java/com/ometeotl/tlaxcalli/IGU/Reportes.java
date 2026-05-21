package com.ometeotl.tlaxcalli.IGU;
import com.ometeotl.tlaxcalli.LOGICA.C_GenerarReporte;
import javax.swing.JOptionPane;

public class Reportes extends javax.swing.JFrame {

    public Reportes() {
        initComponents();
        controlador.cargarEmpleados(CB_Empleados);
        
        // Poner la fecha de hoy por defecto para que no aparezcan vacíos
        datePicker1.setDateToToday();
        datePicker4.setDateToToday();
    
        // Opcional: hacer el texto un poco más pequeño si se sigue cortando
        datePicker1.getComponentDateTextField().setFont(new java.awt.Font("Segoe UI", 0, 11));
        datePicker4.getComponentDateTextField().setFont(new java.awt.Font("Segoe UI", 0, 11));
    
        TotalV.setText("$0.00");
        TotalG.setText("$0.00");
        IngresosN.setText("$0.00");
    }
     private com.ometeotl.tlaxcalli.LOGICA.C_Reportes controlador = new com.ometeotl.tlaxcalli.LOGICA.C_Reportes();

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        T_Ventas = new javax.swing.JTable();
        jScrollPane2 = new javax.swing.JScrollPane();
        T_Gastos = new javax.swing.JTable();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        BBuscarVG = new javax.swing.JButton();
        CB_Empleados = new javax.swing.JComboBox<>();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        TotalV = new javax.swing.JLabel();
        TotalG = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        IngresosN = new javax.swing.JLabel();
        B_GAd = new javax.swing.JButton();
        B_GenPDF = new javax.swing.JButton();
        B_SalirInicio = new javax.swing.JButton();
        datePicker4 = new com.github.lgooddatepicker.components.DatePicker();
        datePicker1 = new com.github.lgooddatepicker.components.DatePicker();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Reportes");
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setForeground(new java.awt.Color(0, 0, 0));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setFont(new java.awt.Font("Copperplate Gothic Bold", 0, 24)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(0, 0, 0));
        jLabel1.setText("Reportes");
        jPanel1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(324, 31, -1, -1));

        T_Ventas.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        jScrollPane1.setViewportView(T_Ventas);

        jPanel1.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 240, 320, 120));

        T_Gastos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        jScrollPane2.setViewportView(T_Gastos);

        jPanel1.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(420, 240, 310, 120));

        jLabel2.setForeground(new java.awt.Color(0, 0, 0));
        jLabel2.setText("De:");
        jPanel1.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(420, 110, -1, -1));

        jLabel3.setForeground(new java.awt.Color(0, 0, 0));
        jLabel3.setText("A:");
        jPanel1.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(420, 150, -1, -1));

        BBuscarVG.setText("Aplicar");
        BBuscarVG.addActionListener(this::BBuscarVGActionPerformed);
        jPanel1.add(BBuscarVG, new org.netbeans.lib.awtextra.AbsoluteConstraints(650, 150, -1, -1));

        jPanel1.add(CB_Empleados, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 150, 120, -1));

        jLabel4.setText("Empleado:");
        jLabel4.setForeground(new java.awt.Color(0, 0, 0));
        jPanel1.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 150, -1, -1));

        jLabel5.setFont(new java.awt.Font("Segoe UI Black", 0, 18)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(0, 0, 0));
        jLabel5.setText("Ventas");
        jPanel1.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 200, -1, -1));

        jLabel6.setFont(new java.awt.Font("Segoe UI Black", 0, 18)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(0, 0, 0));
        jLabel6.setText("Gastos");
        jPanel1.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(420, 200, -1, -1));

        jLabel7.setForeground(new java.awt.Color(0, 0, 0));
        jLabel7.setText("Total de ventas");
        jPanel1.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 420, -1, -1));

        jLabel8.setForeground(new java.awt.Color(0, 0, 0));
        jLabel8.setText("Total de gastos");
        jPanel1.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 460, -1, -1));

        TotalV.setText("jLabel9");
        jPanel1.add(TotalV, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 420, -1, -1));

        TotalG.setText("jLabel10");
        jPanel1.add(TotalG, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 460, -1, -1));

        jLabel9.setForeground(new java.awt.Color(0, 0, 0));
        jLabel9.setText("Ingresos Netos");
        jPanel1.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 500, -1, -1));

        IngresosN.setText("jLabel10");
        jPanel1.add(IngresosN, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 500, -1, -1));

        B_GAd.setText("Gastos Generales");
        B_GAd.addActionListener(this::B_GAdActionPerformed);
        jPanel1.add(B_GAd, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 600, -1, -1));

        B_GenPDF.setText("Generar Reporte");
        B_GenPDF.addActionListener(this::B_GenPDFActionPerformed);
        jPanel1.add(B_GenPDF, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 600, -1, -1));

        B_SalirInicio.setText("Salir");
        B_SalirInicio.addActionListener(this::B_SalirInicioActionPerformed);
        jPanel1.add(B_SalirInicio, new org.netbeans.lib.awtextra.AbsoluteConstraints(670, 600, -1, -1));
        jPanel1.add(datePicker4, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 150, -1, -1));
        jPanel1.add(datePicker1, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 110, -1, -1));

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 779, 660));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void B_GAdActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_B_GAdActionPerformed
        Gastos_Generales ventanaGastos = new Gastos_Generales();
        ventanaGastos.setVisible(true);
        ventanaGastos.setLocationRelativeTo(null); // Para que salga al centro

    }//GEN-LAST:event_B_GAdActionPerformed

    private void B_SalirInicioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_B_SalirInicioActionPerformed
        Inicio ventanaInicio = new Inicio();
        ventanaInicio.setVisible(true);
        ventanaInicio.setLocationRelativeTo(null);
        // Cerramos la ventana actual de Reportes
        this.dispose();
    }//GEN-LAST:event_B_SalirInicioActionPerformed

    private void BBuscarVGActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BBuscarVGActionPerformed
    String empleadoSel = CB_Empleados.getSelectedItem().toString();
    
    // Capturamos las fechas de los DatePicker
    java.time.LocalDate date1 = datePicker1.getDate(); // El de "De:"
    java.time.LocalDate date2 = datePicker4.getDate(); // El de "A:"
    
    // Validación de seguridad: que no busquen sin fechas
    if (date1 == null || date2 == null) {
        JOptionPane.showMessageDialog(this, "Jefe, debe seleccionar ambas fechas para filtrar.");
        return;
    }
    
    // Convertimos a String (LGoodDatePicker ya nos da el formato YYYY-MM-DD listo)
    String fInicio = date1.toString();
    String fFin = date2.toString();
    
    // Le pasamos todo al controlador
    controlador.procesarReporte(empleadoSel, fInicio, fFin, T_Ventas, T_Gastos, TotalV, TotalG, IngresosN);
    }//GEN-LAST:event_BBuscarVGActionPerformed

    private void B_GenPDFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_B_GenPDFActionPerformed
        String empleado = CB_Empleados.getSelectedItem().toString();
        String inicio = datePicker1.getDate().toString();
        String fin = datePicker4.getDate().toString();

        C_GenerarReporte orquestador = new C_GenerarReporte();
        orquestador.prepararPDF(inicio, fin, empleado);
    }//GEN-LAST:event_B_GenPDFActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton BBuscarVG;
    private javax.swing.JButton B_GAd;
    private javax.swing.JButton B_GenPDF;
    private javax.swing.JButton B_SalirInicio;
    private javax.swing.JComboBox<String> CB_Empleados;
    private javax.swing.JLabel IngresosN;
    private javax.swing.JTable T_Gastos;
    private javax.swing.JTable T_Ventas;
    private javax.swing.JLabel TotalG;
    private javax.swing.JLabel TotalV;
    private com.github.lgooddatepicker.components.DatePicker datePicker1;
    private com.github.lgooddatepicker.components.DatePicker datePicker4;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    // End of variables declaration//GEN-END:variables




}
