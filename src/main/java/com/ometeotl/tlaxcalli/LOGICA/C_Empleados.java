package com.ometeotl.tlaxcalli.LOGICA;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import java.awt.GridLayout;

import com.ometeotl.tlaxcalli.PERSISTENCIA.Interfaces.DAOFactory;
import com.ometeotl.tlaxcalli.PERSISTENCIA.Interfaces.IEmpleadosDAO;
import static com.ometeotl.tlaxcalli.HerramientasVisuales.ocultarColumna;
import static javax.swing.JOptionPane.OK_CANCEL_OPTION;
import static javax.swing.JOptionPane.OK_OPTION;
import static javax.swing.JOptionPane.PLAIN_MESSAGE;
import static javax.swing.JOptionPane.YES_NO_OPTION;
import static javax.swing.JOptionPane.YES_OPTION;
import static javax.swing.JOptionPane.showConfirmDialog;
import static javax.swing.JOptionPane.showMessageDialog;

public class C_Empleados {
    private final IEmpleadosDAO dao = DAOFactory.getEmpleadosDAO();
    public void aplicarPermisos(JFrame parent, JButton bAgregar, JButton bEliminar, JButton bModificar) {
        String rol = C_Sesion_login.puesto;
        
        if (rol == null || !rol.equalsIgnoreCase("Administrador")) {
            bAgregar.setEnabled(false);
            bEliminar.setEnabled(false);
            bModificar.setEnabled(false);
            parent.setTitle("Empleados (Modo Lectura - Solo Administrador puede editar)");
        }
    }

    public void cargarTabla(JTable tabla) {
        DefaultTableModel modelo = dao.consultarEmpleadosVisibles();
        tabla.setModel(modelo);
        
        ocultarColumna(tabla,0);
    }

    public void eliminarEmpleado(JFrame parent, JTable tabla) {
        int filaSeleccionada = tabla.getSelectedRow();
        if (filaSeleccionada == -1) {
            showMessageDialog(parent, "Primero selecciona un empleado de la lista.");
            return;
        }

        String idTexto = tabla.getValueAt(filaSeleccionada, 0).toString();
        int idEmpleado = Integer.parseInt(idTexto);
        String nombre = tabla.getValueAt(filaSeleccionada, 1).toString();

        int confirmacion = showConfirmDialog(parent, 
                "¿Estás seguro de eliminar a " + nombre + "?\nEsta acción no se puede deshacer.",
                "Confirmar Eliminación", YES_NO_OPTION);

        if (confirmacion == YES_OPTION) {
            if (tabla.getColumnCount() > 1) {
                // 🕵️‍♂️ 1. Recuperamos el puesto del empleado que quieren borrar desde la tabla
                int fila = tabla.getSelectedRow();
                String puestoAEliminar = tabla.getValueAt(fila, 4).toString(); // Ajuste el índice si su columna de puesto es otra
                
                // 🔒 2. EL CANDADO DE SEGURIDAD MÁXIMA
                if (puestoAEliminar.equalsIgnoreCase("Administrador") || puestoAEliminar.equalsIgnoreCase("Gerente")) {
                    
                    // Le preguntamos a la BD cuántos jefes quedan
                    int jefesDisponibles = dao.contarAdministradoresActivos();
                    
                    if (jefesDisponibles <= 1) {
                        showMessageDialog(parent, 
                            "❌ Operación denegada: Este es el único usuario con acceso administrativo en el sistema.\n" +
                            "No puede eliminarlo o se quedará sin acceso a Tlaxcalli.", 
                            "Alerta de Seguridad", javax.swing.JOptionPane.WARNING_MESSAGE);
                        return; // Rompemos el flujo aquí mismo, salvando el día
                    }
                }

                // 3. Si pasa el candado, procedemos a borrar normalmente
                if (dao.eliminarEmpleado(idEmpleado)) {
                    showMessageDialog(parent, "✅ Empleado eliminado.");
                    cargarTabla(tabla);
                } else {
                    showMessageDialog(parent, "❌ Error al eliminar. Verifique si tiene ventas registradas.");
                }
            }
        }
    }
    
    public void agregarEmpleado(JFrame parent, JTable tabla) {
        ejecutarFormularioEmpleado(parent, tabla, new JTextField(), new JTextField(),
                            new JTextField(), new JTextField(), "Repartidor", true);
    }

    public void modificarEmpleado(JFrame parent, JTable tabla) {
        int fila = tabla.getSelectedRow();
        if (fila == -1) {
            showMessageDialog(parent, "Selecciona un empleado para modificar.");
            return;
        }

        // Recuperamos datos de la tabla
        String nombreActual = tabla.getValueAt(fila, 1).toString();
        String appActual = tabla.getValueAt(fila, 2).toString();
        String apmActual = tabla.getValueAt(fila, 3).toString();
        String puestoActual = tabla.getValueAt(fila, 4).toString();

        int idEmpleado = Integer.parseInt(tabla.getValueAt(fila, 0).toString());
        String usuarioRecuperado = dao.obtenerNombreUsuario(idEmpleado); 
        String userFinal = (usuarioRecuperado == null) ? "" : usuarioRecuperado;

        // Mandamos los campos precargados y el indicador false (Modificar)
        ejecutarFormularioEmpleado(parent, tabla, new JTextField(nombreActual), new JTextField(appActual), 
                                new JTextField(apmActual), new JTextField(userFinal), puestoActual, false);
    }
    
    // El método unificado (Cambie el nombre a uno más descriptivo del negocio)
    private void ejecutarFormularioEmpleado(JFrame parent, JTable tabla, 
            JTextField txtNombre, JTextField txtApp, JTextField txtApm, JTextField txtUser, 
            String puestoActual, boolean esRegistroNuevo) {
        
        JPasswordField txtPass = new JPasswordField();
        String[] puestos = {"Repartidor", "Molinero", "Mostrador", "Gerente", "Administrador"};
        JComboBox<String> cmbPuesto = new JComboBox<>(puestos);
        cmbPuesto.setSelectedItem(puestoActual);

        // Guardamos el usuario de respaldo de forma segura como texto estricto, no como componente
        final String copiaUsuarioRespaldo = txtUser.getText();

        // 1. CONFIGURACIÓN INICIAL DE SEGURIDAD
        boolean esJefeInicial = puestoActual.equalsIgnoreCase("Administrador") || puestoActual.equalsIgnoreCase("Gerente");
        txtUser.setEnabled(esJefeInicial);
        txtPass.setEnabled(esJefeInicial);

        // 2. ESCUCHADOR EN TIEMPO REAL
        cmbPuesto.addActionListener(e -> {
            String puestoSeleccionado = cmbPuesto.getSelectedItem().toString();
            boolean esJefeSeleccionado = puestoSeleccionado.equalsIgnoreCase("Administrador") || puestoSeleccionado.equalsIgnoreCase("Gerente");
            
            txtUser.setEnabled(esJefeSeleccionado);
            txtPass.setEnabled(esJefeSeleccionado);
            
            if (esJefeSeleccionado) {
                txtUser.setText(copiaUsuarioRespaldo); // Usamos el texto de respaldo seguro
            } else {
                txtUser.setText("");
                txtPass.setText("");
            }
        });

        // 3. CONSTRUCCIÓN DEL PANEL VISUAL
        JPanel panel = new JPanel(new GridLayout(0, 1));
        panel.add(new JLabel("--- Datos Personales ---"));
        panel.add(new JLabel("Nombre:")); panel.add(txtNombre);
        panel.add(new JLabel("Apellido Paterno:")); panel.add(txtApp);
        panel.add(new JLabel("Apellido Materno:")); panel.add(txtApm);
        panel.add(new JLabel("Puesto:")); panel.add(cmbPuesto);
        panel.add(new JLabel(" ")); 
        panel.add(new JLabel("--- Acceso (Gerente y Admin) ---"));
        panel.add(new JLabel("Usuario:")); panel.add(txtUser);
        panel.add(new JLabel(esRegistroNuevo ? "Contraseña:" : "Nueva Contraseña (Vacía para mantener actual):")); 
        panel.add(txtPass);

        String tituloVentana = (esRegistroNuevo ? "Agregar" : "Modificar") + " Empleado";
        int result = showConfirmDialog(parent, panel, tituloVentana, OK_CANCEL_OPTION, PLAIN_MESSAGE);
        
        // 4. PROCESAMIENTO DE REGLAS DE NEGOCIO AL GUARDAR
        if (result == OK_OPTION) {
            String newNom = txtNombre.getText().trim();
            String newApp = txtApp.getText().trim();
            String newApm = txtApm.getText().trim();
            String newPuesto = cmbPuesto.getSelectedItem().toString();
            String newUser = txtUser.getText().trim();
            String newPass = new String(txtPass.getPassword()).trim();

            if (newNom.isEmpty() || newApp.isEmpty()) {
                showMessageDialog(parent, "Nombre y Apellido Paterno son obligatorios.");
                return;
            }

            boolean esJefeFinal = newPuesto.equalsIgnoreCase("Administrador") || newPuesto.equalsIgnoreCase("Gerente");

            // Validaciones estructurales para Jefes
            if (esJefeFinal && newUser.isEmpty()) {
                showMessageDialog(parent, "Un " + newPuesto + " debe tener un Usuario asignado.");
                return;
            }
            if (esJefeFinal && esRegistroNuevo && newPass.isEmpty()) {
                showMessageDialog(parent, "La contraseña es obligatoria para nuevos perfiles administrativos.");
                return;
            }
            if (esJefeFinal && !esRegistroNuevo && copiaUsuarioRespaldo.isEmpty() && newPass.isEmpty()) {
                 showMessageDialog(parent, "Al ascender a " + newPuesto + ", la contraseña es obligatoria.");
                 return;
            }
            
            boolean exito;

            if (esRegistroNuevo) {
                if (!newUser.isEmpty() && newPass.isEmpty()) {
                    showMessageDialog(parent, "Si asignas usuario, debes poner contraseña.");
                    return;
                }
                exito = dao.registrarEmpleado(newNom, newApp, newApm, newPuesto, newUser, newPass);
            } else {
                int filaSeleccionada = tabla.getSelectedRow();
                if (filaSeleccionada == -1) return; // Validación de salida rápida
                int idEmpleado = Integer.parseInt(tabla.getValueAt(filaSeleccionada, 0).toString());
                exito = dao.modificarEmpleado(idEmpleado, newNom, newApp, newApm, newPuesto, newUser, newPass);
            }

            // 5. RESPUESTA DEL SISTEMA
            if (exito) {
                showMessageDialog(parent, "✅ Operación realizada con éxito.");
                cargarTabla(tabla); 
            } else {
                showMessageDialog(parent, "❌ Error al procesar en la base de datos.");
            }
        }
    }
}