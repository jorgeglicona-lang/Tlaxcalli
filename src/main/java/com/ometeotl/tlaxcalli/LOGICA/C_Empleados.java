package com.ometeotl.tlaxcalli.LOGICA;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import java.awt.GridLayout;

import com.ometeotl.tlaxcalli.PERSISTENCIA.Interfaces.DAOFactory;
import com.ometeotl.tlaxcalli.PERSISTENCIA.Interfaces.IEmpleadosDAO;

public class C_Empleados {

    public void aplicarPermisos(JFrame ventana, JButton bAgregar, JButton bEliminar, JButton bModificar) {
        String rol = C_Sesion_login.puesto;
        
        if (rol == null || !rol.equalsIgnoreCase("Administrador")) {
            bAgregar.setEnabled(false);
            bEliminar.setEnabled(false);
            bModificar.setEnabled(false);
            ventana.setTitle("Empleados (Modo Lectura - Solo Administrador puede editar)");
        }
    }

    public void cargarTabla(JTable tabla) {
        IEmpleadosDAO dao = DAOFactory.getEmpleadosDAO();
        DefaultTableModel modelo = dao.consultarEmpleadosVisibles();
        tabla.setModel(modelo);
        
        // Ocultar la columna 0 (ID)
        if (tabla.getColumnCount() > 0) {
            tabla.getColumnModel().getColumn(0).setMinWidth(0);
            tabla.getColumnModel().getColumn(0).setMaxWidth(0);
            tabla.getColumnModel().getColumn(0).setWidth(0);
            tabla.getColumnModel().getColumn(0).setPreferredWidth(0);
        }
    }

    public void agregarEmpleado(JFrame parent, JTable tabla) {
        JTextField txtNombre = new JTextField();
        JTextField txtApp = new JTextField();
        JTextField txtApm = new JTextField();
        JTextField txtUser = new JTextField();
        JPasswordField txtPass = new JPasswordField();

        // 1. Bloqueamos los campos de acceso por defecto (ya que empieza en "Repartidor")
        txtUser.setEnabled(false);
        txtPass.setEnabled(false);

        String[] puestos = {"Repartidor", "Molinero", "Mostrador", "Administrador", "Gerente"};
        JComboBox<String> cmbPuesto = new JComboBox<>(puestos);

        // 2. Le ponemos el "escuchador" al JComboBox para que reaccione al instante
        cmbPuesto.addActionListener(e -> {
            String puestoSeleccionado = cmbPuesto.getSelectedItem().toString();
            
            // Si elige a los jefes, encendemos las cajas
            if (puestoSeleccionado.equalsIgnoreCase("Administrador") || 
                puestoSeleccionado.equalsIgnoreCase("Gerente")) {
                txtUser.setEnabled(true);
                txtPass.setEnabled(true);
            } else {
                // Si elige otro puesto, apagamos y limpiamos la basura por si acaso
                txtUser.setEnabled(false);
                txtPass.setEnabled(false);
                txtUser.setText("");
                txtPass.setText("");
            }
        });

        JPanel panel = new JPanel(new java.awt.GridLayout(0, 1));
        panel.add(new JLabel("--- Datos Personales ---"));
        panel.add(new JLabel("Nombre:"));
        panel.add(txtNombre);
        panel.add(new JLabel("Apellido Paterno:"));
        panel.add(txtApp);
        panel.add(new JLabel("Apellido Materno:"));
        panel.add(txtApm);
        panel.add(new JLabel("Puesto:"));
        panel.add(cmbPuesto);
        panel.add(new JLabel(" "));
        panel.add(new JLabel("--- Datos de Acceso (Opcional) ---"));
        panel.add(new JLabel("Usuario:"));
        panel.add(txtUser);
        panel.add(new JLabel("Contraseña:"));
        panel.add(txtPass);

        int resultado = JOptionPane.showConfirmDialog(parent, panel, 
                "Nuevo Empleado", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (resultado == JOptionPane.OK_OPTION) {
            String nom = txtNombre.getText().trim();
            String app = txtApp.getText().trim();
            String apm = txtApm.getText().trim();
            String puesto = cmbPuesto.getSelectedItem().toString();
            String usuario = txtUser.getText().trim();
            String pass = new String(txtPass.getPassword()).trim();

            if (nom.isEmpty() || app.isEmpty()) {
                JOptionPane.showMessageDialog(parent, "Nombre y Apellido son obligatorios.");
                return;
            }
            if (!usuario.isEmpty() && pass.isEmpty()) {
                JOptionPane.showMessageDialog(parent, "Si asignas usuario, debes poner contraseña.");
                return;
            }
            if ((puesto.equalsIgnoreCase("Administrador") || puesto.equalsIgnoreCase("Gerente")) 
                && (usuario.isEmpty() || pass.isEmpty())) {
                JOptionPane.showMessageDialog(parent, "Los perfiles de Administrador y Gerente deben tener usuario y contraseña.");
                return;
            }

            // Llamamos a nuestro DAO para hacer el registro
            IEmpleadosDAO dao = DAOFactory.getEmpleadosDAO();
            if (dao.registrarEmpleado(nom, app, apm, puesto, usuario, pass)) {
                JOptionPane.showMessageDialog(parent, "✅ Empleado registrado con éxito.");
                cargarTabla(tabla); 
            } else {
                JOptionPane.showMessageDialog(parent, "❌ Error al guardar.");
            }
        }
    }

    public void eliminarEmpleado(JFrame parent, JTable tabla) {
        int filaSeleccionada = tabla.getSelectedRow();
        if (filaSeleccionada == -1) {
            JOptionPane.showMessageDialog(parent, "Primero selecciona un empleado de la lista.");
            return;
        }

        String idTexto = tabla.getValueAt(filaSeleccionada, 0).toString();
        int idEmpleado = Integer.parseInt(idTexto);
        String nombre = tabla.getValueAt(filaSeleccionada, 1).toString();

        int confirmacion = JOptionPane.showConfirmDialog(parent, 
                "¿Estás seguro de eliminar a " + nombre + "?\nEsta acción no se puede deshacer.",
                "Confirmar Eliminación", JOptionPane.YES_NO_OPTION);

        if (confirmacion == JOptionPane.YES_OPTION) {
            IEmpleadosDAO dao = DAOFactory.getEmpleadosDAO();
            if (dao.eliminarEmpleado(idEmpleado)) {
                JOptionPane.showMessageDialog(parent, "✅ Empleado eliminado.");
                cargarTabla(tabla);
            } else {
                JOptionPane.showMessageDialog(parent, "❌ Error al eliminar. Verifique si tiene ventas registradas.");
            }
        }
    }

    public void modificarEmpleado(JFrame parent, JTable tabla) {
        int fila = tabla.getSelectedRow();
        if (fila == -1) {
            JOptionPane.showMessageDialog(parent, "Selecciona un empleado para modificar.");
            return;
        }

        int idEmpleado = Integer.parseInt(tabla.getValueAt(fila, 0).toString());
        String nombreActual = tabla.getValueAt(fila, 1).toString();
        String appActual = tabla.getValueAt(fila, 2).toString();
        String apmActual = tabla.getValueAt(fila, 3).toString();
        String puestoActual = tabla.getValueAt(fila, 4).toString();

        IEmpleadosDAO dao = DAOFactory.getEmpleadosDAO();
        String usuarioRecuperado = dao.obtenerNombreUsuario(idEmpleado); 
        // Declaramos usuarioActual como 'final' en una sola línea usando un operador ternario
        final String usuarioActual = (usuarioRecuperado == null) ? "" : usuarioRecuperado;

        JTextField txtNombre = new JTextField(nombreActual);
        JTextField txtApp = new JTextField(appActual);
        JTextField txtApm = new JTextField(apmActual);
        JTextField txtUser = new JTextField(usuarioActual);
        JPasswordField txtPass = new JPasswordField();

        String[] puestos = {"Repartidor", "Molinero", "Mostrador", "Gerente", "Administrador"};
        JComboBox<String> cmbPuesto = new JComboBox<>(puestos);
        cmbPuesto.setSelectedItem(puestoActual);

        // --- 1. CONFIGURACIÓN INICIAL DE SEGURIDAD ---
        // Verificamos si el empleado ya era jefe para encenderle las cajas desde el principio
        boolean esJefeInicial = puestoActual.equalsIgnoreCase("Administrador") || puestoActual.equalsIgnoreCase("Gerente");
        txtUser.setEnabled(esJefeInicial);
        txtPass.setEnabled(esJefeInicial);

        // --- 2. ESCUCHADOR DE CAMBIOS EN TIEMPO REAL ---
        cmbPuesto.addActionListener(e -> {
            String puestoSeleccionado = cmbPuesto.getSelectedItem().toString();
            
            if (puestoSeleccionado.equalsIgnoreCase("Administrador") || 
                puestoSeleccionado.equalsIgnoreCase("Gerente")) {
                txtUser.setEnabled(true);
                txtPass.setEnabled(true);
                
                // Si lo estamos ascendiendo y antes tenía usuario, se lo volvemos a poner visible
                txtUser.setText(usuarioActual); 
            } else {
                // Si lo degradan a Mostrador o Repartidor, apagamos y borramos los textos
                // para que no se guarde basura oculta
                txtUser.setEnabled(false);
                txtPass.setEnabled(false);
                txtUser.setText("");
                txtPass.setText("");
            }
        });

        JPanel panel = new JPanel(new GridLayout(0, 1));
        panel.add(new JLabel("--- Datos Personales ---"));
        panel.add(new JLabel("Nombre:"));
        panel.add(txtNombre);
        panel.add(new JLabel("Apellido Paterno:"));
        panel.add(txtApp);
        panel.add(new JLabel("Apellido Materno:"));
        panel.add(txtApm);
        panel.add(new JLabel("Puesto:"));
        panel.add(cmbPuesto);
        panel.add(new JLabel(" ")); 
        panel.add(new JLabel("--- Acceso (Gerente y Admin) ---"));
        panel.add(new JLabel("Usuario:"));
        panel.add(txtUser);
        panel.add(new JLabel("Nueva Contraseña (Dejar vacía para mantener actual):"));
        panel.add(txtPass);

        int resultado = JOptionPane.showConfirmDialog(parent, panel, 
                "Modificar Empleado", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (resultado == JOptionPane.OK_OPTION) {
            String newNom = txtNombre.getText().trim();
            String newApp = txtApp.getText().trim();
            String newApm = txtApm.getText().trim();
            String newPuesto = cmbPuesto.getSelectedItem().toString();
            String newUser = txtUser.getText().trim();
            String newPass = new String(txtPass.getPassword()).trim();

            if (newNom.isEmpty() || newApp.isEmpty()) {
                JOptionPane.showMessageDialog(parent, "El nombre no puede estar vacío.");
                return;
            }

            // --- 3. VALIDACIÓN DE REGLAS DE NEGOCIO AL GUARDAR ---
            boolean esJefe = newPuesto.equalsIgnoreCase("Administrador") || newPuesto.equalsIgnoreCase("Gerente");
            
            if (esJefe && newUser.isEmpty()) {
                JOptionPane.showMessageDialog(parent, "Un " + newPuesto + " debe tener un Usuario asignado.");
                return;
            }
            
            // Si es Jefe, no tenía usuario antes (fue ascendido), y no puso contraseña nueva -> Error
            if (esJefe && usuarioActual.isEmpty() && newPass.isEmpty()) {
                 JOptionPane.showMessageDialog(parent, "Al ascender a " + newPuesto + ", la contraseña es obligatoria.");
                 return;
            }

            if (dao.modificarEmpleado(idEmpleado, newNom, newApp, newApm, newPuesto, newUser, newPass)) {
                JOptionPane.showMessageDialog(parent, "✅ Modificación exitosa.");
                cargarTabla(tabla); 
            } else {
                JOptionPane.showMessageDialog(parent, "❌ Error al modificar.");
            }
        }
    }
}