package com.ometeotl.tlaxcalli.LOGICA;

import com.ometeotl.tlaxcalli.PERSISTENCIA.Interfaces.DAOFactory;
import com.ometeotl.tlaxcalli.PERSISTENCIA.Interfaces.I_InicioDAO;
import java.awt.Color;
import java.awt.GridLayout;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import static javax.swing.JOptionPane.ERROR_MESSAGE;
import static javax.swing.JOptionPane.INFORMATION_MESSAGE;
import static javax.swing.JOptionPane.OK_CANCEL_OPTION;
import static javax.swing.JOptionPane.OK_OPTION;
import static javax.swing.JOptionPane.PLAIN_MESSAGE;
import static javax.swing.JOptionPane.WARNING_MESSAGE;
import static javax.swing.JOptionPane.YES_NO_OPTION;
import static javax.swing.JOptionPane.YES_OPTION;
import static javax.swing.JOptionPane.showConfirmDialog;
import static javax.swing.JOptionPane.showMessageDialog;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;

public class C_Inicio {
    private final I_InicioDAO dao = DAOFactory.getInicioDAO();
    
    public void recargarTablaProductos(JTable tablaProd) {
        tablaProd.setModel(dao.obtenerProductosTabla());
    }
    
    public void recargarTablaCatGastos(JTable tablaGastos) {
        tablaGastos.setModel(dao.obtenerCatGastosTabla());
    }
    
    // El policía de tránsito para el botón AGREGAR
    public void agregarInteligente(JFrame parent, JTabbedPane pestanias, JTable tablaProd, JTable tablaGastos) {
        
        int pestaniaActiva = pestanias.getSelectedIndex();
        
        if (pestaniaActiva == 0) {
            // --- FORMULARIO DE PRODUCTOS ---
            JTextField txtNombre = new JTextField();
            JTextField txtPrecio = new JTextField();
            JCheckBox chkComodin = new JCheckBox("Es producto Comodín");
            
            chkComodin.addActionListener(e -> {
                if (chkComodin.isSelected()) {
                    txtPrecio.setText("0.0"); // Le ponemos cero por defecto
                    txtPrecio.setEnabled(false); // Bloqueamos la escritura
                    txtPrecio.setBackground(new Color(220, 220, 220)); // Lo pintamos de gris
                } else {
                    txtPrecio.setText(""); // Lo vaciamos
                    txtPrecio.setEnabled(true); // Desbloqueamos
                    txtPrecio.setBackground(Color.WHITE); // Regresa a blanco
                }
            });

            JPanel panel = new JPanel(new GridLayout(0, 1));
            panel.add(new JLabel("Nombre del Producto:"));
            panel.add(txtNombre);
            panel.add(new JLabel("Precio ($):"));
            panel.add(txtPrecio);
            panel.add(chkComodin);

            int resultado = showConfirmDialog(parent, panel, 
                    "Nuevo Producto", OK_CANCEL_OPTION, PLAIN_MESSAGE);

            if (resultado == OK_OPTION) {
                String nombre = txtNombre.getText().trim();
                String precioStr = txtPrecio.getText().trim();
                int esComodin = chkComodin.isSelected() ? 1 : 0;

                if (nombre.isEmpty() || precioStr.isEmpty()) {
                    showMessageDialog(parent, "Todos los campos son obligatorios.");
                    return; 
                }

                try {
                    // 2. PARSEO LIMPIO: Convertimos el texto directo a número
                    double precio = Double.parseDouble(precioStr);

                    // 3. GUARDADO
                    if (dao.registrarProducto(nombre, precio, esComodin)) {
                        showMessageDialog(parent, "✅ Producto guardado.");
                        recargarTablaProductos(tablaProd);
                    } else {
                        showMessageDialog(parent, "❌ Error al guardar en base de datos.");
                    }

                } catch (NumberFormatException e) {
                    showMessageDialog(parent, "El precio debe ser un número válido (ej. 20.50).");
                }
            }
        } else if (pestaniaActiva == 1) {
            JTextField txtNombre = new JTextField();
            JCheckBox chkRequiere = new JCheckBox("Requiere Descripción detallada");

            JPanel panel = new JPanel(new GridLayout(0, 1));
            panel.add(new JLabel("Nombre del Gasto (Ej. Gasolina, Luz):"));
            panel.add(txtNombre);
            panel.add(chkRequiere);

            int resultado = showConfirmDialog(parent, panel, 
                    "Nuevo Tipo de Gasto", OK_CANCEL_OPTION, PLAIN_MESSAGE);

            if (resultado == OK_OPTION) {
                String nombre = txtNombre.getText().trim();
                
                if (nombre.isEmpty()) {
                    showMessageDialog(parent, "El nombre del gasto es obligatorio.");
                    return;
                }
                
                int requiereDesc = chkRequiere.isSelected() ? 1 : 0;
                if (dao.registrarCatGasto(nombre, requiereDesc)) {
                    showMessageDialog(parent, "✅ Gasto registrado.");
                    recargarTablaCatGastos(tablaGastos);
                } else {
                    showMessageDialog(parent, "❌ Error al guardar.");
                }
            }
        }
    }

    // El policía de tránsito para el botón ELIMINAR
    public void eliminarInteligente(JFrame parent, JTabbedPane pestanias, JTable tablaProd, JTable tablaGastos) {
        
        int pestaniaActiva = pestanias.getSelectedIndex();
        
        if (pestaniaActiva == 0) {
            int fila = tablaProd.getSelectedRow();
            if (fila == -1) {
                showMessageDialog(parent, "Seleccione un Producto para eliminar.", "Aviso", WARNING_MESSAGE);
                return;
            }
            
            int idProd = Integer.parseInt(tablaProd.getValueAt(fila, 0).toString());
            String nombre = tablaProd.getValueAt(fila, 1).toString();
            
            if (idProd > 0 && idProd < 4) {
                showMessageDialog(parent, "⚠️ ACCIÓN DENEGADA:\nEl producto '" + nombre + 
                        "' es vital para los cálculos del sistema y no puede ser eliminado.",
                            "Seguridad de Tlaxcalli", ERROR_MESSAGE);
                return;
            }
            
            int conf = showConfirmDialog(parent, "¿Eliminar definitivamente el producto " + nombre + "?", 
                    "Confirmar", YES_NO_OPTION);
                    
            if (conf == YES_OPTION) {
                if (dao.eliminarProducto(idProd)) {
                    showMessageDialog(parent, "✅ Producto eliminado.");
                    recargarTablaProductos(tablaProd);
                } else {
                    showMessageDialog(parent, "❌ No se pudo eliminar. Quizá ya tiene ventas registradas.");
                }
            }
        } else if (pestaniaActiva == 1) {
            int fila = tablaGastos.getSelectedRow();
            if (fila == -1) {
                showMessageDialog(parent, "Seleccione un Gasto para eliminar.", "Aviso",WARNING_MESSAGE);
                return;
            }
            
            int idTipo = Integer.parseInt(tablaGastos.getValueAt(fila, 0).toString());
            String nombre = tablaGastos.getValueAt(fila, 1).toString();
            
            int conf = showConfirmDialog(parent, "¿Eliminar definitivamente el gasto " + nombre + "?", 
                    "Confirmar", YES_NO_OPTION);
                    
            if (conf == YES_OPTION) {
                if (dao.eliminarCatGasto(idTipo)) {
                    showMessageDialog(parent, "✅ Gasto eliminado.");
                    recargarTablaCatGastos(tablaGastos);
                } else {
                    showMessageDialog(parent, "❌ No se pudo eliminar.");
                }
            }
        }
    }
    
    public void modificarInteligente(JFrame parent, JTabbedPane pestanias, JTable tablaProd, JTable tablaGastos) {
        int pestaniaActiva = pestanias.getSelectedIndex();
        
        if (pestaniaActiva == 0) {
            // --- ESTAMOS EN LA PESTAÑA PRODUCTOS ---
            int fila = tablaProd.getSelectedRow();
            if (fila == -1) {
                showMessageDialog(parent, "Por favor, seleccione un Producto de la tabla para modificarlo.",
                        "Aviso",WARNING_MESSAGE);
                return;
            }
            int idProducto = Integer.parseInt(tablaProd.getValueAt(fila, 0).toString());
            String nombreActual = tablaProd.getValueAt(fila, 1).toString();
            double precioActual = Double.parseDouble(tablaProd.getValueAt(fila, 2).toString());
            String comodinActual = tablaProd.getValueAt(fila, 3).toString(); // Recibe "Sí" o "No"
            
            // Pasamos los datos extraídos como valores iniciales a los campos del formulario
            JTextField txtNombre = new JTextField(nombreActual);
            JTextField txtPrecio = new JTextField(String.valueOf(precioActual));
            JCheckBox chkComodin = new JCheckBox("Es producto Comodín");
            
            chkComodin.addActionListener(e -> {
                if (chkComodin.isSelected()) {
                    txtPrecio.setText("0.0"); // Le ponemos cero por defecto
                    txtPrecio.setEnabled(false); // Bloqueamos la escritura
                    txtPrecio.setBackground(new Color(220, 220, 220)); // Lo pintamos de gris
                } else {
                    txtPrecio.setText(""); // Lo vaciamos
                    txtPrecio.setEnabled(true); // Desbloqueamos
                    txtPrecio.setBackground(Color.WHITE); // Regresa a blanco
                }
            });
            
            // Si la celda extraída decía "Sí", encendemos la palomita automáticamente
            chkComodin.setSelected(comodinActual.equalsIgnoreCase("Sí"));
            
            if (idProducto > 0 && idProducto <4) {
                txtNombre.setEditable(false); // No pueden borrar ni cambiar el texto
                txtNombre.setBackground(new Color(220, 220, 220)); // Lo pintamos de gris
                chkComodin.setEnabled(false); // No pueden alterar su estado lógico
                
                showMessageDialog(parent,"ℹ️ MODO SEGURO ACTIVO:\nPara los productos base, solo está "
                        + "autorizado actualizar el Precio.", "Aviso", INFORMATION_MESSAGE);
            }

            JPanel panel = new JPanel(new GridLayout(0, 1));
            panel.add(new JLabel("Nombre del Producto:"));
            panel.add(txtNombre);
            panel.add(new JLabel("Precio ($):"));
            panel.add(txtPrecio);
            panel.add(chkComodin);

            int resultado = showConfirmDialog(parent, panel, "Modificar Producto", OK_CANCEL_OPTION, PLAIN_MESSAGE);

            if (resultado == JOptionPane.OK_OPTION) {
                String nombreNuevo = txtNombre.getText().trim();
                String precioStr = txtPrecio.getText().trim();
                
                if (nombreNuevo.isEmpty() || precioStr.isEmpty()) {
                    showMessageDialog(parent, "Todos los campos son obligatorios.");
                    return;
                }
                
                try {
                    double precioNuevo = Double.parseDouble(precioStr);
                    int esComodinNuevo = chkComodin.isSelected() ? 1 : 0;
                    if (dao.modificarProducto(idProducto, nombreNuevo, precioNuevo, esComodinNuevo)) {
                        showMessageDialog(parent, "✅ Producto modificado correctamente.");
                        recargarTablaProductos(tablaProd); // Refrescamos la sala al instante
                    } else {
                        showMessageDialog(parent, "❌ Error al actualizar en la base de datos.");
                    }
                } catch (NumberFormatException e) {
                    showMessageDialog(parent, "El precio debe ser un número válido.");
                }
            }
        } else if (pestaniaActiva == 1) {
            int fila = tablaGastos.getSelectedRow();
            if (fila == -1) {
                showMessageDialog(parent, "Por favor, seleccione un Gasto del catálogo para modificarlo.", "Aviso", javax.swing.JOptionPane.WARNING_MESSAGE);
                return;
            }
            
            int idTipo = Integer.parseInt(tablaGastos.getValueAt(fila, 0).toString());
            String nombreActual = tablaGastos.getValueAt(fila, 1).toString();
            String requiereActual = tablaGastos.getValueAt(fila, 2).toString(); // "Sí" o "No"
            
            JTextField txtNombre = new JTextField(nombreActual);
            JCheckBox chkRequiere = new JCheckBox("Requiere Descripción detallada");
            chkRequiere.setSelected(requiereActual.equalsIgnoreCase("Sí"));

            JPanel panel = new JPanel(new GridLayout(0, 1));
            panel.add(new javax.swing.JLabel("Nombre del Gasto:"));
            panel.add(txtNombre);
            panel.add(chkRequiere);

            int resultado = showConfirmDialog(parent, panel, "Modificar Tipo de Gasto",OK_CANCEL_OPTION, PLAIN_MESSAGE);

            if (resultado == OK_OPTION) {
                String nombreNuevo = txtNombre.getText().trim();
                
                if (nombreNuevo.isEmpty()) {
                    showMessageDialog(parent, "El nombre no puede estar vacío.");
                    return;
                }
                
                int requiereNuevo = chkRequiere.isSelected() ? 1 : 0;
                if (dao.modificarCatGasto(idTipo, nombreNuevo, requiereNuevo)) {
                    javax.swing.JOptionPane.showMessageDialog(parent, "✅ Gasto modificado.");
                    recargarTablaCatGastos(tablaGastos);
                } else {
                    javax.swing.JOptionPane.showMessageDialog(parent, "❌ Error al modificar.");
                }
            }
        }
    }
}