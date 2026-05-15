
package com.ometeotl.tlaxcalli.IGU;

public class Emplados extends javax.swing.JFrame {

    public Emplados() {
        initComponents();
        setIconImage(new javax.swing.ImageIcon(getClass().getResource("/imagen/transparencia.png")).getImage());
        cargarTabla();
        this.setLocationRelativeTo(null);
        
        // --- APLICAR SEGURIDAD ---
        aplicarPermisos();
    }
    
    private void aplicarPermisos() {
        // Leemos el gafete de la sesión
        String rol = com.ometeotl.tlaxcalli.LOGICA.C_Sesion_login.puesto;
        
        // Si NO es Administrador, bloqueamos los controles
        if (rol == null || !rol.equalsIgnoreCase("Administrador")) {
            b_agregar.setEnabled(false);
            b_eliminar.setEnabled(false);
            b_modificar.setEnabled(false);
            
            // Opcional: Mostrar mensaje o cambiar título
            this.setTitle("Empleados (Modo Lectura - Solo Administrador puede editar)");
        }
    }
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        l_empleados = new javax.swing.JTable();
        b_agregar = new javax.swing.JButton();
        b_eliminar = new javax.swing.JButton();
        b_modificar = new javax.swing.JButton();
        b_atras = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        l_empleados.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane1.setViewportView(l_empleados);

        b_agregar.setText("Agregar");
        b_agregar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                b_agregarActionPerformed(evt);
            }
        });

        b_eliminar.setText("Eliminar");
        b_eliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                b_eliminarActionPerformed(evt);
            }
        });

        b_modificar.setText("Modificar");
        b_modificar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                b_modificarActionPerformed(evt);
            }
        });

        b_atras.setText("Salir");
        b_atras.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                b_atrasActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(48, 48, 48)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(b_agregar, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(b_modificar, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(b_eliminar, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(b_atras, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(60, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(b_agregar, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(b_eliminar, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(b_modificar, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(b_atras, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(32, Short.MAX_VALUE))
        );

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 700, 470));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void b_atrasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_b_atrasActionPerformed

        this.setVisible(false);
    }//GEN-LAST:event_b_atrasActionPerformed

    private void b_agregarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_b_agregarActionPerformed
        javax.swing.JTextField txtNombre = new javax.swing.JTextField();
        javax.swing.JTextField txtApp = new javax.swing.JTextField();
        javax.swing.JTextField txtApm = new javax.swing.JTextField();
        
        // Campos nuevos para Login
        javax.swing.JTextField txtUser = new javax.swing.JTextField();
        javax.swing.JPasswordField txtPass = new javax.swing.JPasswordField();

        String[] puestos = {"Repartidor", "Molinero", "Mostrador", "Administrador", "Gerente"};
        javax.swing.JComboBox<String> cmbPuesto = new javax.swing.JComboBox<>(puestos);

        // 2. DISEÑO DEL PANEL
        javax.swing.JPanel panel = new javax.swing.JPanel(new java.awt.GridLayout(0, 1));
        panel.add(new javax.swing.JLabel("--- Datos Personales ---"));
        panel.add(new javax.swing.JLabel("Nombre:"));
        panel.add(txtNombre);
        panel.add(new javax.swing.JLabel("Apellido Paterno:"));
        panel.add(txtApp);
        panel.add(new javax.swing.JLabel("Apellido Materno:"));
        panel.add(txtApm);
        panel.add(new javax.swing.JLabel("Puesto:"));
        panel.add(cmbPuesto);
        
        panel.add(new javax.swing.JLabel(" ")); // Espacio vacío
        panel.add(new javax.swing.JLabel("--- Datos de Acceso (Opcional) ---"));
        panel.add(new javax.swing.JLabel("Usuario:"));
        panel.add(txtUser);
        panel.add(new javax.swing.JLabel("Contraseña:"));
        panel.add(txtPass);

        // 3. MOSTRAR ALERTA
        int resultado = javax.swing.JOptionPane.showConfirmDialog(null, panel, 
                "Nuevo Empleado", javax.swing.JOptionPane.OK_CANCEL_OPTION, javax.swing.JOptionPane.PLAIN_MESSAGE);

        if (resultado == javax.swing.JOptionPane.OK_OPTION) {
            // Recolectar datos
            String nom = txtNombre.getText().trim();
            String app = txtApp.getText().trim();
            String apm = txtApm.getText().trim();
            String puesto = cmbPuesto.getSelectedItem().toString();
            
            String usuario = txtUser.getText().trim();
            String pass = new String(txtPass.getPassword()).trim();

            if (nom.isEmpty() || app.isEmpty()) {
                javax.swing.JOptionPane.showMessageDialog(this, "Nombre y Apellido son obligatorios.");
                return;
            }
            
            // Validar que si puso usuario, ponga contraseña
            if (!usuario.isEmpty() && pass.isEmpty()) {
                javax.swing.JOptionPane.showMessageDialog(this, "Si asignas usuario, debes poner contraseña.");
                return;
            }
            
            if (puesto.equalsIgnoreCase("Administrador") && (usuario.isEmpty() || pass.isEmpty())) {
                javax.swing.JOptionPane.showMessageDialog(this, "Si asignas un Administrador, debes poner asignar usuario y contraseña.");
                return;
            }

            // Llamar al DAO actualizado
            com.ometeotl.tlaxcalli.PERSISTENCIA.EmpleadosDAO dao = new com.ometeotl.tlaxcalli.PERSISTENCIA.EmpleadosDAO();
            
            // Enviamos los 6 parámetros
            if (dao.registrarEmpleado(nom, app, apm, puesto, usuario, pass)) {
                javax.swing.JOptionPane.showMessageDialog(this, "✅ Empleado registrado con éxito.");
                cargarTabla(); 
            } else {
                javax.swing.JOptionPane.showMessageDialog(this, "❌ Error al guardar.");
            }
        }
    
    }//GEN-LAST:event_b_agregarActionPerformed

    private void b_eliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_b_eliminarActionPerformed
        int filaSeleccionada = l_empleados.getSelectedRow();
        
        if (filaSeleccionada == -1) {
            javax.swing.JOptionPane.showMessageDialog(this, "Primero selecciona un empleado de la lista.");
            return;
        }

        // 2. OBTENER EL ID (Está en la columna 0)
        // Convertimos el objeto a String y luego a entero por seguridad
        String idTexto = l_empleados.getValueAt(filaSeleccionada, 0).toString();
        int idEmpleado = Integer.parseInt(idTexto);
        String nombre = l_empleados.getValueAt(filaSeleccionada, 1).toString();

        // 3. PREGUNTAR CONFIRMACIÓN (Importante para no borrar por error)
        int confirmacion = javax.swing.JOptionPane.showConfirmDialog(this, 
                "¿Estás seguro de eliminar a " + nombre + "?\nEsta acción no se puede deshacer.",
                "Confirmar Eliminación",
                javax.swing.JOptionPane.YES_NO_OPTION);

        if (confirmacion == javax.swing.JOptionPane.YES_OPTION) {
            // 4. LLAMAR AL DAO
            com.ometeotl.tlaxcalli.PERSISTENCIA.EmpleadosDAO dao = new com.ometeotl.tlaxcalli.PERSISTENCIA.EmpleadosDAO();
            
            if (dao.eliminarEmpleado(idEmpleado)) {
                javax.swing.JOptionPane.showMessageDialog(this, "✅ Empleado eliminado.");
                cargarTabla(); // Actualizamos la lista visualmente
            } else {
                javax.swing.JOptionPane.showMessageDialog(this, "❌ Error al eliminar. Verifique si tiene ventas registradas.");
            }
        }
    }//GEN-LAST:event_b_eliminarActionPerformed

    private void b_modificarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_b_modificarActionPerformed
        int fila = l_empleados.getSelectedRow();
        if (fila == -1) {
            javax.swing.JOptionPane.showMessageDialog(this, "Selecciona un empleado para modificar.");
            return;
        }

        // 2. RECUPERAR DATOS ACTUALES DE LA TABLA
        // (Recordando que la columna 0 es el ID oculto)
        int idEmpleado = Integer.parseInt(l_empleados.getValueAt(fila, 0).toString());
        String nombreActual = l_empleados.getValueAt(fila, 1).toString();
        String appActual = l_empleados.getValueAt(fila, 2).toString();
        String apmActual = l_empleados.getValueAt(fila, 3).toString();
        String puestoActual = l_empleados.getValueAt(fila, 4).toString();

        // Recuperar el usuario actual desde la BD (si tiene)
        com.ometeotl.tlaxcalli.PERSISTENCIA.EmpleadosDAO dao = new com.ometeotl.tlaxcalli.PERSISTENCIA.EmpleadosDAO();
        String usuarioActual = dao.obtenerNombreUsuario(idEmpleado);

        // 3. CREAR FORMULARIO PRE-LLENADO
        javax.swing.JTextField txtNombre = new javax.swing.JTextField(nombreActual);
        javax.swing.JTextField txtApp = new javax.swing.JTextField(appActual);
        javax.swing.JTextField txtApm = new javax.swing.JTextField(apmActual);
        
        javax.swing.JTextField txtUser = new javax.swing.JTextField(usuarioActual);
        javax.swing.JPasswordField txtPass = new javax.swing.JPasswordField(); // Contraseña siempre vacía por seguridad

        String[] puestos = {"Repartidor", "Molinero", "Mostrador", "Gerente", "Administrador"};
        javax.swing.JComboBox<String> cmbPuesto = new javax.swing.JComboBox<>(puestos);
        cmbPuesto.setSelectedItem(puestoActual);

        javax.swing.JPanel panel = new javax.swing.JPanel(new java.awt.GridLayout(0, 1));
        panel.add(new javax.swing.JLabel("--- Datos Personales ---"));
        panel.add(new javax.swing.JLabel("Nombre:"));
        panel.add(txtNombre);
        panel.add(new javax.swing.JLabel("Apellido Paterno:"));
        panel.add(txtApp);
        panel.add(new javax.swing.JLabel("Apellido Materno:"));
        panel.add(txtApm);
        panel.add(new javax.swing.JLabel("Puesto:"));
        panel.add(cmbPuesto);
        panel.add(new javax.swing.JLabel(" ")); 
        panel.add(new javax.swing.JLabel("--- Acceso (Solo Admin) ---"));
        panel.add(new javax.swing.JLabel("Usuario:"));
        panel.add(txtUser);
        panel.add(new javax.swing.JLabel("Nueva Contraseña (Dejar vacía para mantener actual):"));
        panel.add(txtPass);

        // 4. MOSTRAR ALERTA
        int resultado = javax.swing.JOptionPane.showConfirmDialog(null, panel, 
                "Modificar Empleado", javax.swing.JOptionPane.OK_CANCEL_OPTION, javax.swing.JOptionPane.PLAIN_MESSAGE);

        if (resultado == javax.swing.JOptionPane.OK_OPTION) {
            // Recolectar datos nuevos
            String newNom = txtNombre.getText().trim();
            String newApp = txtApp.getText().trim();
            String newApm = txtApm.getText().trim();
            String newPuesto = cmbPuesto.getSelectedItem().toString();
            String newUser = txtUser.getText().trim();
            String newPass = new String(txtPass.getPassword()).trim();

            // VALIDACIONES
            if (newNom.isEmpty() || newApp.isEmpty()) {
                javax.swing.JOptionPane.showMessageDialog(this, "El nombre no puede estar vacío.");
                return;
            }

            // --- LÓGICA DE SEGURIDAD EXIGIDA ---
            boolean esAdmin = newPuesto.equalsIgnoreCase("Administrador");
            
            // Regla: Si es Admin, DEBE tener usuario.
            if (esAdmin && newUser.isEmpty()) {
                javax.swing.JOptionPane.showMessageDialog(this, "Un Administrador debe tener un Usuario asignado.");
                return;
            }
            
            // Regla: Si lo estamos ASCENDIENDO a Admin (no tenía usuario antes), DEBE poner contraseña.
            if (esAdmin && usuarioActual.isEmpty() && newPass.isEmpty()) {
                 javax.swing.JOptionPane.showMessageDialog(this, "Al crear un nuevo Administrador, la contraseña es obligatoria.");
                 return;
            }

            // 5. GUARDAR CAMBIOS
            if (dao.modificarEmpleado(idEmpleado, newNom, newApp, newApm, newPuesto, newUser, newPass)) {
                javax.swing.JOptionPane.showMessageDialog(this, "✅ Modificación exitosa.");
                cargarTabla(); 
            } else {
                javax.swing.JOptionPane.showMessageDialog(this, "❌ Error al modificar.");
            }
        }
    }//GEN-LAST:event_b_modificarActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton b_agregar;
    private javax.swing.JButton b_atras;
    private javax.swing.JButton b_eliminar;
    private javax.swing.JButton b_modificar;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable l_empleados;
    // End of variables declaration//GEN-END:variables
// MÉTODO PARA LLENAR LA TABLA
    private void cargarTabla() {
        // Llamamos a nuestro experto DAO
        com.ometeotl.tlaxcalli.PERSISTENCIA.EmpleadosDAO dao = new com.ometeotl.tlaxcalli.PERSISTENCIA.EmpleadosDAO();
        
        // Obtenemos el modelo lleno
        javax.swing.table.DefaultTableModel modelo = dao.consultarEmpleadosVisibles();
        
        // Se lo ponemos a la tabla visual
        l_empleados.setModel(modelo);
        
        l_empleados.getColumnModel().getColumn(0).setMinWidth(0);
        l_empleados.getColumnModel().getColumn(0).setMaxWidth(0);
        l_empleados.getColumnModel().getColumn(0).setWidth(0);
        l_empleados.getColumnModel().getColumn(0).setPreferredWidth(0);
    }
}
