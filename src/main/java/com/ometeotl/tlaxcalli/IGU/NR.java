package com.ometeotl.tlaxcalli.IGU;

import com.ometeotl.tlaxcalli.LOGICA.C_NR;
import com.ometeotl.tlaxcalli.LOGICA.C_Inicio; // Para usar pintarImagen()

public class NR extends javax.swing.JFrame {

    // Instanciamos el cerebro
    private C_NR controlador = new C_NR();

    public NR() {
        initComponents();
        setIconImage(new javax.swing.ImageIcon(getClass().getResource("/imagen/transparencia.png")).getImage());
        
        // Ocultar ID en la tabla
        tabla_detalles.getColumnModel().getColumn(0).setMinWidth(0);
        tabla_detalles.getColumnModel().getColumn(0).setMaxWidth(0);
        tabla_detalles.getColumnModel().getColumn(0).setWidth(0);
        
        // Pintar logo usando el utilitario que ya teníamos en C_Inicio
        new C_Inicio().pintarImagen(Logolb, "/imagen/transparencia.png");
        
        // Llenar combos delegando la tarea al controlador
        controlador.inicializarCombos(BoxRepartidor, cb_producto, cb_gastos);
        controlador.cargarPreciosBase();
        configurarSeccionMasa(); // Lógica puramente visual, se queda aquí
    }
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        BoxRepartidor = new javax.swing.JComboBox<>();
        jLabel1 = new javax.swing.JLabel();
        Name = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        Logolb = new javax.swing.JLabel();
        t_reparto = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        t_venta = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        t_masa = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        s_masaNo = new javax.swing.JRadioButton();
        s_masaSi = new javax.swing.JRadioButton();
        c_entregar = new javax.swing.JLabel();
        b_guardarV = new javax.swing.JButton();
        b_salir = new javax.swing.JButton();
        b_molino = new javax.swing.JButton();
        cb_producto = new javax.swing.JComboBox<>();
        t_cantidad = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        t_precio = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        b_agregarProd = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tabla_detalles = new javax.swing.JTable();
        t_montoGasto = new javax.swing.JTextField();
        b_agregarGasto = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        tabla_gastos = new javax.swing.JTable();
        jLabel11 = new javax.swing.JLabel();
        cb_gastos = new javax.swing.JComboBox<>();
        t_detalleGasto = new javax.swing.JTextField();
        s_PAdicionales = new javax.swing.JCheckBox();
        c_Gastos = new javax.swing.JCheckBox();
        b_EliminarGasto = new javax.swing.JButton();
        b_EliminarProd = new javax.swing.JButton();
        jLabel10 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        t_detalle = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Nuevo Registro");
        setLocationByPlatform(true);
        setResizable(false);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setMinimumSize(new java.awt.Dimension(740, 550));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        BoxRepartidor.setName(""); // NOI18N
        BoxRepartidor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BoxRepartidorActionPerformed(evt);
            }
        });
        jPanel1.add(BoxRepartidor, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 110, 170, -1));

        jLabel1.setText("Repartidor");
        jPanel1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 80, -1, -1));

        Name.setFont(new java.awt.Font("Roboto Black", 0, 26)); // NOI18N
        Name.setText("Tlaxcalli");
        jPanel1.add(Name, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 20, -1, -1));

        jLabel2.setText("Tortilla de reparto");
        jPanel1.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 190, -1, -1));
        jPanel1.add(Logolb, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 20, 40, 40));

        t_reparto.setEditable(false);
        t_reparto.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                t_repartoKeyReleased(evt);
            }
        });
        jPanel1.add(t_reparto, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 190, 80, -1));

        jLabel3.setText("Venta de tortilla");
        jPanel1.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 220, -1, -1));

        t_venta.setEditable(false);
        t_venta.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                t_ventaKeyReleased(evt);
            }
        });
        jPanel1.add(t_venta, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 220, 80, -1));

        jLabel4.setText("Venta de masa");
        jPanel1.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 260, -1, -1));

        t_masa.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                t_masaKeyReleased(evt);
            }
        });
        jPanel1.add(t_masa, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 260, 60, -1));

        jLabel5.setText("Kg");
        jPanel1.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 190, -1, -1));

        jLabel6.setText("Kg");
        jPanel1.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 260, -1, -1));

        jLabel7.setText("Kg");
        jPanel1.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 220, -1, -1));

        s_masaNo.setText("No");
        jPanel1.add(s_masaNo, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 260, -1, -1));

        s_masaSi.setText("Si");
        jPanel1.add(s_masaSi, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 260, -1, -1));

        c_entregar.setText("Total a Entregar: $");
        jPanel1.add(c_entregar, new org.netbeans.lib.awtextra.AbsoluteConstraints(510, 110, -1, -1));

        b_guardarV.setText("Guardar");
        b_guardarV.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                b_guardarVActionPerformed(evt);
            }
        });
        jPanel1.add(b_guardarV, new org.netbeans.lib.awtextra.AbsoluteConstraints(590, 670, 120, 50));

        b_salir.setText("Salir");
        b_salir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                b_salirActionPerformed(evt);
            }
        });
        jPanel1.add(b_salir, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 700, -1, -1));

        b_molino.setText("Molino");
        b_molino.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                b_molinoActionPerformed(evt);
            }
        });
        jPanel1.add(b_molino, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 700, -1, -1));

        cb_producto.setEnabled(false);
        cb_producto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cb_productoActionPerformed(evt);
            }
        });
        jPanel1.add(cb_producto, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 340, 150, -1));

        t_cantidad.setEditable(false);
        jPanel1.add(t_cantidad, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 340, 50, -1));

        jLabel8.setText("Unidades");
        jPanel1.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 320, 60, 20));

        t_precio.setEditable(false);
        jPanel1.add(t_precio, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 340, 50, -1));

        jLabel9.setText("Precio");
        jPanel1.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 320, -1, -1));

        b_agregarProd.setText("Agregar");
        b_agregarProd.setEnabled(false);
        b_agregarProd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                b_agregarProdActionPerformed(evt);
            }
        });
        jPanel1.add(b_agregarProd, new org.netbeans.lib.awtextra.AbsoluteConstraints(510, 340, -1, -1));

        tabla_detalles.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Producto", "Cantidad", "Costo", "Total"
            }
        ));
        tabla_detalles.setEnabled(false);
        tabla_detalles.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tabla_detallesMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tabla_detalles);

        jPanel1.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 390, 620, 90));

        t_montoGasto.setEditable(false);
        jPanel1.add(t_montoGasto, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 520, 50, -1));

        b_agregarGasto.setText("Agregar");
        b_agregarGasto.setEnabled(false);
        b_agregarGasto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                b_agregarGastoActionPerformed(evt);
            }
        });
        jPanel1.add(b_agregarGasto, new org.netbeans.lib.awtextra.AbsoluteConstraints(460, 520, -1, -1));

        jScrollPane2.setEnabled(false);

        tabla_gastos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Gasto", "Monto"
            }
        ));
        tabla_gastos.setEnabled(false);
        tabla_gastos.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tabla_gastosMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tabla_gastos);

        jPanel1.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 560, 340, 90));

        jLabel11.setText("$");
        jPanel1.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 520, 20, -1));

        cb_gastos.setEnabled(false);
        cb_gastos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cb_gastosActionPerformed(evt);
            }
        });
        jPanel1.add(cb_gastos, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 520, 160, -1));

        t_detalleGasto.setEditable(false);
        jPanel1.add(t_detalleGasto, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 520, 120, -1));

        s_PAdicionales.setText("Productos Adicionales");
        s_PAdicionales.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                s_PAdicionalesActionPerformed(evt);
            }
        });
        jPanel1.add(s_PAdicionales, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 300, -1, -1));

        c_Gastos.setText("Gastos");
        c_Gastos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                c_GastosActionPerformed(evt);
            }
        });
        jPanel1.add(c_Gastos, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 490, -1, -1));

        b_EliminarGasto.setText("Eliminar");
        b_EliminarGasto.setEnabled(false);
        b_EliminarGasto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                b_EliminarGastoActionPerformed(evt);
            }
        });
        jPanel1.add(b_EliminarGasto, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 520, -1, -1));

        b_EliminarProd.setText("Eliminar");
        b_EliminarProd.setEnabled(false);
        b_EliminarProd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                eliminarProd(evt);
            }
        });
        jPanel1.add(b_EliminarProd, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 340, -1, -1));

        jLabel10.setText("$");
        jPanel1.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 340, -1, -1));

        jLabel12.setText("Detalles");
        jPanel1.add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 500, -1, -1));

        jLabel13.setText("Detalle");
        jPanel1.add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 320, -1, -1));

        t_detalle.setEditable(false);
        jPanel1.add(t_detalle, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 340, 100, -1));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 740, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        jPanel1.getAccessibleContext().setAccessibleName("");

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void b_guardarVActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_b_guardarVActionPerformed
        controlador.guardarCorte(this, BoxRepartidor, t_reparto, t_venta, t_masa, 
                                 tabla_detalles, tabla_gastos, s_masaNo, s_PAdicionales, c_Gastos);
    }//GEN-LAST:event_b_guardarVActionPerformed

    private void b_salirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_b_salirActionPerformed
        this.setVisible(false);
    }//GEN-LAST:event_b_salirActionPerformed

    private void BoxRepartidorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BoxRepartidorActionPerformed
        c_entregar.setText("$0.00");
        controlador.procesarSeleccionRepartidor(this, BoxRepartidor, t_reparto, t_venta, t_masa, s_masaSi,
                                                s_PAdicionales, c_Gastos,tabla_detalles, tabla_gastos, c_entregar,
                                                cb_gastos, cb_producto);
    }//GEN-LAST:event_BoxRepartidorActionPerformed

    private void b_molinoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_b_molinoActionPerformed
        Molino mol=new Molino();
        mol.setVisible(true);
        mol.setLocationRelativeTo(null);
    }//GEN-LAST:event_b_molinoActionPerformed

    private void cb_productoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cb_productoActionPerformed
        if (cb_producto.getSelectedItem() == null) return;
        com.ometeotl.tlaxcalli.LOGICA.ProductoItem item = 
                (com.ometeotl.tlaxcalli.LOGICA.ProductoItem) cb_producto.getSelectedItem();
        t_cantidad.setText("");
        t_precio.setText("");
        t_detalle.setText("");
            
        if (item.getId() == 0) {
            t_cantidad.setEnabled(false);
            t_precio.setEnabled(false);
            t_detalle.setEnabled(false);
            b_agregarProd.setEnabled(false); 
            return;
        }
        
        b_agregarProd.setEnabled(true);
        t_cantidad.setEnabled(true);
        t_cantidad.setEditable(true);
        t_cantidad.requestFocus();
        
        if (item.isComodin()) {
            t_precio.setEnabled(true);
            t_precio.setEditable(true);
            t_detalle.setEnabled(true);
            t_detalle.setEditable(true);
            t_detalle.requestFocus();
        } else {
            t_detalle.setEnabled(false);
            t_detalle.setText(item.toString());
            t_precio.setEnabled(false);
            t_precio.setText(String.valueOf(item.getPrecio()));
        }
    }//GEN-LAST:event_cb_productoActionPerformed

    private void b_agregarProdActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_b_agregarProdActionPerformed
        try {
            com.ometeotl.tlaxcalli.LOGICA.ProductoItem item = (com.ometeotl.tlaxcalli.LOGICA.ProductoItem) cb_producto.getSelectedItem();
            int cantidad = Integer.parseInt(t_cantidad.getText());
            double precioUnitario = item.isComodin() ? Double.parseDouble(t_precio.getText()) : item.getPrecio();
            String observacion = item.isComodin() ? t_detalle.getText() : item.toString();
            double subtotal = cantidad * precioUnitario;

            javax.swing.table.DefaultTableModel modelo = (javax.swing.table.DefaultTableModel) tabla_detalles.getModel();
            modelo.addRow(new Object[] { item.getId(), observacion, cantidad, precioUnitario, subtotal });
            
            t_cantidad.setText("");
            t_precio.setText("");
            t_detalle.setText("");
        } catch (NumberFormatException e) {
            javax.swing.JOptionPane.showMessageDialog(this, "Verifica la cantidad y precio.");
        }
        
        controlador.calcularTotalAPagar(t_reparto, t_venta, t_masa, tabla_detalles, tabla_gastos, c_entregar);
    }//GEN-LAST:event_b_agregarProdActionPerformed

    private void cb_gastosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cb_gastosActionPerformed
        if (cb_gastos.getSelectedItem() == null) return;
        
        com.ometeotl.tlaxcalli.LOGICA.GastoItem item = (com.ometeotl.tlaxcalli.LOGICA.GastoItem) cb_gastos.getSelectedItem();
        
        t_montoGasto.setText("");
        t_detalleGasto.setText("");
        
        if (item.getId() == 0) { 
            t_detalleGasto.setEnabled(false);
            b_agregarGasto.setEnabled(false);
            return;
        }
        
        b_agregarGasto.setEnabled(true); 
        t_montoGasto.setEnabled(true); 
        t_montoGasto.setEditable(true); 
        t_montoGasto.requestFocus();
        
        if (item.isRequiereDescripcion()) {
            t_detalleGasto.setEnabled(true); 
            t_detalleGasto.setEditable(true);
        } else {
            t_detalleGasto.setEnabled(false); 
            t_detalleGasto.setText(item.toString()); 
        }
    }//GEN-LAST:event_cb_gastosActionPerformed

    private void b_agregarGastoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_b_agregarGastoActionPerformed
        try {
            com.ometeotl.tlaxcalli.LOGICA.GastoItem item = (com.ometeotl.tlaxcalli.LOGICA.GastoItem) cb_gastos.getSelectedItem();
            
            double monto = Double.parseDouble(t_montoGasto.getText());
            String descripcion = item.isRequiereDescripcion() ? t_detalleGasto.getText() : item.toString();

            if (item.isRequiereDescripcion() && descripcion.isEmpty()) {
                javax.swing.JOptionPane.showMessageDialog(this, "Por favor especifica el detalle del gasto."); return;
            }

            javax.swing.table.DefaultTableModel modelo = (javax.swing.table.DefaultTableModel) tabla_gastos.getModel();
            modelo.addRow(new Object[] { descripcion, monto });
            
            t_montoGasto.setText(""); 
            t_detalleGasto.setText("");
        } catch (NumberFormatException e) { 
            javax.swing.JOptionPane.showMessageDialog(this, "El monto debe ser numérico."); 
        }
        
        controlador.calcularTotalAPagar(t_reparto, t_venta, t_masa, tabla_detalles, tabla_gastos, c_entregar);
    }//GEN-LAST:event_b_agregarGastoActionPerformed

    private void s_PAdicionalesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_s_PAdicionalesActionPerformed
        boolean activo = s_PAdicionales.isSelected();
        cb_producto.setEnabled(activo);
        tabla_detalles.setEnabled(activo);
        if (!activo) {
            cb_producto.setSelectedIndex(0); 
            t_detalle.setText("");
            t_cantidad.setText("");
            t_precio.setText("");
            tabla_detalles.clearSelection(); 
            b_EliminarProd.setEnabled(false);
            t_detalle.setEnabled(false);
            t_cantidad.setEnabled(false);  
            t_precio.setEnabled(false);    
            b_agregarProd.setEnabled(false); 
        }
    }//GEN-LAST:event_s_PAdicionalesActionPerformed

    private void c_GastosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_c_GastosActionPerformed
        boolean activo = c_Gastos.isSelected();
        cb_gastos.setEnabled(activo);
        tabla_gastos.setEnabled(activo);
        if(!activo){
            cb_gastos.setSelectedIndex(0);
            t_montoGasto.setText("");
            t_detalleGasto.setText("");
            tabla_gastos.clearSelection();
            b_EliminarGasto.setEnabled(false);
            t_montoGasto.setEnabled(false);
            t_detalleGasto.setEnabled(false);
            b_agregarGasto.setEnabled(false);
        }
    }//GEN-LAST:event_c_GastosActionPerformed

    private void t_repartoKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_t_repartoKeyReleased
        controlador.calcularTotalAPagar(t_reparto, t_venta, t_masa, tabla_detalles, tabla_gastos, c_entregar);
    }//GEN-LAST:event_t_repartoKeyReleased

    private void t_ventaKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_t_ventaKeyReleased
        controlador.calcularTotalAPagar(t_reparto, t_venta, t_masa, tabla_detalles, tabla_gastos, c_entregar);
    }//GEN-LAST:event_t_ventaKeyReleased

    private void t_masaKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_t_masaKeyReleased
        controlador.calcularTotalAPagar(t_reparto, t_venta, t_masa, tabla_detalles, tabla_gastos, c_entregar);
    }//GEN-LAST:event_t_masaKeyReleased

    private void eliminarProd(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_eliminarProd
        javax.swing.table.DefaultTableModel modelo = (javax.swing.table.DefaultTableModel) tabla_detalles.getModel();
        int fila = tabla_detalles.getSelectedRow();
        if (fila >= 0) {
            modelo.removeRow(fila);
            controlador.calcularTotalAPagar(t_reparto, t_venta, t_masa, tabla_detalles, tabla_gastos, c_entregar); 
            if (modelo.getRowCount() == 0) b_EliminarProd.setEnabled(false);
        }
    }//GEN-LAST:event_eliminarProd

    private void b_EliminarGastoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_b_EliminarGastoActionPerformed
        javax.swing.table.DefaultTableModel modelo = (javax.swing.table.DefaultTableModel) tabla_gastos.getModel();
        int fila = tabla_gastos.getSelectedRow();
        if (fila >= 0) {
            modelo.removeRow(fila);
            controlador.calcularTotalAPagar(t_reparto, t_venta, t_masa, tabla_detalles, tabla_gastos, c_entregar); 
            if (modelo.getRowCount() == 0) b_EliminarGasto.setEnabled(false);
        }
    }//GEN-LAST:event_b_EliminarGastoActionPerformed

    private void tabla_detallesMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabla_detallesMouseClicked
        if (tabla_detalles.getSelectedRow() != -1) b_EliminarProd.setEnabled(true);
    }//GEN-LAST:event_tabla_detallesMouseClicked

    private void tabla_gastosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabla_gastosMouseClicked
        if (tabla_gastos.getSelectedRow() != -1) b_EliminarGasto.setEnabled(true);
    }//GEN-LAST:event_tabla_gastosMouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<Object> BoxRepartidor;
    private javax.swing.JLabel Logolb;
    private javax.swing.JLabel Name;
    private javax.swing.JButton b_EliminarGasto;
    private javax.swing.JButton b_EliminarProd;
    private javax.swing.JButton b_agregarGasto;
    private javax.swing.JButton b_agregarProd;
    private javax.swing.JButton b_guardarV;
    private javax.swing.JButton b_molino;
    private javax.swing.JButton b_salir;
    private javax.swing.JCheckBox c_Gastos;
    private javax.swing.JLabel c_entregar;
    private javax.swing.JComboBox<Object> cb_gastos;
    private javax.swing.JComboBox<Object> cb_producto;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
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
    private javax.swing.JCheckBox s_PAdicionales;
    private javax.swing.JRadioButton s_masaNo;
    private javax.swing.JRadioButton s_masaSi;
    private javax.swing.JTextField t_cantidad;
    private javax.swing.JTextField t_detalle;
    private javax.swing.JTextField t_detalleGasto;
    private javax.swing.JTextField t_masa;
    private javax.swing.JTextField t_montoGasto;
    private javax.swing.JTextField t_precio;
    private javax.swing.JTextField t_reparto;
    private javax.swing.JTextField t_venta;
    private javax.swing.JTable tabla_detalles;
    private javax.swing.JTable tabla_gastos;
    // End of variables declaration//GEN-END:variables
    
    private void configurarSeccionMasa() {
        javax.swing.ButtonGroup grupoMasa = new javax.swing.ButtonGroup();
        grupoMasa.add(s_masaSi);
        grupoMasa.add(s_masaNo);

        s_masaNo.setSelected(true);   
        t_masa.setEnabled(false);     
        t_masa.setText("");           
        t_masa.setBackground(new java.awt.Color(220, 220, 220)); 

        s_masaSi.addActionListener(e -> {
            t_masa.setEnabled(true);  
            t_masa.setBackground(java.awt.Color.WHITE); 
            t_masa.requestFocus();    
        });

        s_masaNo.addActionListener(e -> {
            t_masa.setEnabled(false); 
            t_masa.setText("");       
            t_masa.setBackground(new java.awt.Color(220, 220, 220)); 
            controlador.calcularTotalAPagar(t_reparto, t_venta, t_masa, tabla_detalles, tabla_gastos, c_entregar);
        });
    }    
}
