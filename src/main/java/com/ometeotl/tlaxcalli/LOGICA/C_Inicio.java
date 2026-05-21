package com.ometeotl.tlaxcalli.LOGICA;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;

public class C_Inicio {
    
    // MÉTODO NATIVO PARA AJUSTAR IMAGEN A JLABEL
    public void pintarImagen(javax.swing.JLabel lbl, String ruta) {
        try {
            // 1. Cargar la imagen desde los recursos del proyecto (funciona dentro del JAR)
            java.net.URL url = getClass().getResource(ruta);
            
            if (url != null) {
                javax.swing.ImageIcon imagen = new javax.swing.ImageIcon(url);
                
                // 2. Obtener dimensiones. Si el layout aún no carga, usamos el tamaño preferido
                int w = lbl.getWidth();
                int h = lbl.getHeight();
                if (w == 0 || h == 0) {
                    w = lbl.getPreferredSize().width;
                    h = lbl.getPreferredSize().height;
                }
                
                // 3. Escalar la imagen (SCALE_SMOOTH da mejor calidad que la librería externa)
                javax.swing.Icon icono = new javax.swing.ImageIcon(
                    imagen.getImage().getScaledInstance(w, h, java.awt.Image.SCALE_SMOOTH)
                );
                
                // 4. Asignar al label
                lbl.setIcon(icono);
                lbl.repaint();
            } else {
                System.err.println("No se encontró la imagen en: " + ruta);
            }
        } catch (Exception e) {
            System.err.println("Error cargando imagen: " + e.getMessage());
        }
    }
    
    public void recargarTablaProductos(JTable tablaProd) {
        com.ometeotl.tlaxcalli.PERSISTENCIA.Interfaces.I_InicioDAO dao = 
            com.ometeotl.tlaxcalli.PERSISTENCIA.Interfaces.DAOFactory.getInicioDAO();
        tablaProd.setModel(dao.obtenerProductosTabla());
    }
    
    public void recargarTablaCatGastos(JTable tablaGastos) {
        com.ometeotl.tlaxcalli.PERSISTENCIA.Interfaces.I_InicioDAO dao = 
            com.ometeotl.tlaxcalli.PERSISTENCIA.Interfaces.DAOFactory.getInicioDAO();
        tablaGastos.setModel(dao.obtenerCatGastosTabla());
    }
    
    // El policía de tránsito para el botón AGREGAR
    public void agregarInteligente(JFrame parent, JTabbedPane pestanias, JTable tablaProd, JTable tablaGastos) {
        
        int pestaniaActiva = pestanias.getSelectedIndex();
        
        if (pestaniaActiva == 0) {
            // --- FORMULARIO DE PRODUCTOS ---
            javax.swing.JTextField txtNombre = new javax.swing.JTextField();
            javax.swing.JTextField txtPrecio = new javax.swing.JTextField();
            javax.swing.JCheckBox chkComodin = new javax.swing.JCheckBox("Es producto Comodín");

            javax.swing.JPanel panel = new javax.swing.JPanel(new java.awt.GridLayout(0, 1));
            panel.add(new javax.swing.JLabel("Nombre del Producto:"));
            panel.add(txtNombre);
            panel.add(new javax.swing.JLabel("Precio ($):"));
            panel.add(txtPrecio);
            panel.add(chkComodin);

            int resultado = JOptionPane.showConfirmDialog(parent, panel, 
                    "Nuevo Producto", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

            if (resultado == JOptionPane.OK_OPTION) {
                String nombre = txtNombre.getText().trim();
                String precioStr = txtPrecio.getText().trim();
                
                if (nombre.isEmpty() || precioStr.isEmpty()) {
                    JOptionPane.showMessageDialog(parent, "Todos los campos son obligatorios.");
                    return;
                }
                
                try {
                    double precio = Double.parseDouble(precioStr);
                    int esComodin = chkComodin.isSelected() ? 1 : 0;
                    
                    com.ometeotl.tlaxcalli.PERSISTENCIA.Interfaces.I_InicioDAO dao = 
                        com.ometeotl.tlaxcalli.PERSISTENCIA.Interfaces.DAOFactory.getInicioDAO();
                    
                    if (dao.registrarProducto(nombre, precio, esComodin)) {
                        JOptionPane.showMessageDialog(parent, "✅ Producto guardado.");
                        recargarTablaProductos(tablaProd);
                    } else {
                        JOptionPane.showMessageDialog(parent, "❌ Error al guardar en base de datos.");
                    }
                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(parent, "El precio debe ser un número válido.");
                }
            }
        } else if (pestaniaActiva == 1) {
            javax.swing.JTextField txtNombre = new javax.swing.JTextField();
            javax.swing.JCheckBox chkRequiere = new javax.swing.JCheckBox("Requiere Descripción detallada");

            javax.swing.JPanel panel = new javax.swing.JPanel(new java.awt.GridLayout(0, 1));
            panel.add(new javax.swing.JLabel("Nombre del Gasto (Ej. Gasolina, Luz):"));
            panel.add(txtNombre);
            panel.add(chkRequiere);

            int resultado = javax.swing.JOptionPane.showConfirmDialog(parent, panel, 
                    "Nuevo Tipo de Gasto", javax.swing.JOptionPane.OK_CANCEL_OPTION, javax.swing.JOptionPane.PLAIN_MESSAGE);

            if (resultado == javax.swing.JOptionPane.OK_OPTION) {
                String nombre = txtNombre.getText().trim();
                
                if (nombre.isEmpty()) {
                    javax.swing.JOptionPane.showMessageDialog(parent, "El nombre del gasto es obligatorio.");
                    return;
                }
                
                int requiereDesc = chkRequiere.isSelected() ? 1 : 0;
                
                com.ometeotl.tlaxcalli.PERSISTENCIA.Interfaces.I_InicioDAO dao = 
                    com.ometeotl.tlaxcalli.PERSISTENCIA.Interfaces.DAOFactory.getInicioDAO();
                
                if (dao.registrarCatGasto(nombre, requiereDesc)) {
                    javax.swing.JOptionPane.showMessageDialog(parent, "✅ Gasto registrado.");
                    recargarTablaCatGastos(tablaGastos);
                } else {
                    javax.swing.JOptionPane.showMessageDialog(parent, "❌ Error al guardar.");
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
                JOptionPane.showMessageDialog(parent, "Seleccione un Producto para eliminar.", "Aviso", JOptionPane.WARNING_MESSAGE);
                return;
            }
            
            int idProd = Integer.parseInt(tablaProd.getValueAt(fila, 0).toString());
            String nombre = tablaProd.getValueAt(fila, 1).toString();
            
            int conf = JOptionPane.showConfirmDialog(parent, "¿Eliminar definitivamente el producto " + nombre + "?", 
                    "Confirmar", JOptionPane.YES_NO_OPTION);
                    
            if (conf == JOptionPane.YES_OPTION) {
                com.ometeotl.tlaxcalli.PERSISTENCIA.Interfaces.I_InicioDAO dao = 
                    com.ometeotl.tlaxcalli.PERSISTENCIA.Interfaces.DAOFactory.getInicioDAO();
                
                if (dao.eliminarProducto(idProd)) {
                    JOptionPane.showMessageDialog(parent, "✅ Producto eliminado.");
                    recargarTablaProductos(tablaProd);
                } else {
                    JOptionPane.showMessageDialog(parent, "❌ No se pudo eliminar. Quizá ya tiene ventas registradas.");
                }
            }
        } else if (pestaniaActiva == 1) {
            int fila = tablaGastos.getSelectedRow();
            if (fila == -1) {
                javax.swing.JOptionPane.showMessageDialog(parent, "Seleccione un Gasto para eliminar.", "Aviso", javax.swing.JOptionPane.WARNING_MESSAGE);
                return;
            }
            
            int idTipo = Integer.parseInt(tablaGastos.getValueAt(fila, 0).toString());
            String nombre = tablaGastos.getValueAt(fila, 1).toString();
            
            int conf = javax.swing.JOptionPane.showConfirmDialog(parent, "¿Eliminar definitivamente el gasto " + nombre + "?", 
                    "Confirmar", javax.swing.JOptionPane.YES_NO_OPTION);
                    
            if (conf == javax.swing.JOptionPane.YES_OPTION) {
                com.ometeotl.tlaxcalli.PERSISTENCIA.Interfaces.I_InicioDAO dao = 
                    com.ometeotl.tlaxcalli.PERSISTENCIA.Interfaces.DAOFactory.getInicioDAO();
                
                if (dao.eliminarCatGasto(idTipo)) {
                    javax.swing.JOptionPane.showMessageDialog(parent, "✅ Gasto eliminado.");
                    recargarTablaCatGastos(tablaGastos);
                } else {
                    javax.swing.JOptionPane.showMessageDialog(parent, "❌ No se pudo eliminar.");
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
                JOptionPane.showMessageDialog(parent, "Por favor, seleccione un Producto de la tabla para modificarlo.", "Aviso", JOptionPane.WARNING_MESSAGE);
                return;
            }
            int idProducto = Integer.parseInt(tablaProd.getValueAt(fila, 0).toString());
            String nombreActual = tablaProd.getValueAt(fila, 1).toString();
            double precioActual = Double.parseDouble(tablaProd.getValueAt(fila, 2).toString());
            String comodinActual = tablaProd.getValueAt(fila, 3).toString(); // Recibe "Sí" o "No"
            
            // Pasamos los datos extraídos como valores iniciales a los campos del formulario
            javax.swing.JTextField txtNombre = new javax.swing.JTextField(nombreActual);
            javax.swing.JTextField txtPrecio = new javax.swing.JTextField(String.valueOf(precioActual));
            javax.swing.JCheckBox chkComodin = new javax.swing.JCheckBox("Es producto Comodín");
            
            // Si la celda extraída decía "Sí", encendemos la palomita automáticamente
            chkComodin.setSelected(comodinActual.equalsIgnoreCase("Sí"));

            javax.swing.JPanel panel = new javax.swing.JPanel(new java.awt.GridLayout(0, 1));
            panel.add(new javax.swing.JLabel("Nombre del Producto:"));
            panel.add(txtNombre);
            panel.add(new javax.swing.JLabel("Precio ($):"));
            panel.add(txtPrecio);
            panel.add(chkComodin);

            int resultado = JOptionPane.showConfirmDialog(parent, panel, 
                    "Modificar Producto", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

            if (resultado == JOptionPane.OK_OPTION) {
                String nombreNuevo = txtNombre.getText().trim();
                String precioStr = txtPrecio.getText().trim();
                
                if (nombreNuevo.isEmpty() || precioStr.isEmpty()) {
                    JOptionPane.showMessageDialog(parent, "Todos los campos son obligatorios.");
                    return;
                }
                
                try {
                    double precioNuevo = Double.parseDouble(precioStr);
                    int esComodinNuevo = chkComodin.isSelected() ? 1 : 0;
                    
                    // Mandamos los cables directos a la base de datos portátil
                    com.ometeotl.tlaxcalli.PERSISTENCIA.Interfaces.I_InicioDAO dao = 
                        com.ometeotl.tlaxcalli.PERSISTENCIA.Interfaces.DAOFactory.getInicioDAO();
                    
                    if (dao.modificarProducto(idProducto, nombreNuevo, precioNuevo, esComodinNuevo)) {
                        JOptionPane.showMessageDialog(parent, "✅ Producto modificado correctamente.");
                        recargarTablaProductos(tablaProd); // Refrescamos la sala al instante
                    } else {
                        JOptionPane.showMessageDialog(parent, "❌ Error al actualizar en la base de datos.");
                    }
                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(parent, "El precio debe ser un número válido.");
                }
            }
        } else if (pestaniaActiva == 1) {
            int fila = tablaGastos.getSelectedRow();
            if (fila == -1) {
                javax.swing.JOptionPane.showMessageDialog(parent, "Por favor, seleccione un Gasto del catálogo para modificarlo.", "Aviso", javax.swing.JOptionPane.WARNING_MESSAGE);
                return;
            }
            
            int idTipo = Integer.parseInt(tablaGastos.getValueAt(fila, 0).toString());
            String nombreActual = tablaGastos.getValueAt(fila, 1).toString();
            String requiereActual = tablaGastos.getValueAt(fila, 2).toString(); // "Sí" o "No"
            
            javax.swing.JTextField txtNombre = new javax.swing.JTextField(nombreActual);
            javax.swing.JCheckBox chkRequiere = new javax.swing.JCheckBox("Requiere Descripción detallada");
            chkRequiere.setSelected(requiereActual.equalsIgnoreCase("Sí"));

            javax.swing.JPanel panel = new javax.swing.JPanel(new java.awt.GridLayout(0, 1));
            panel.add(new javax.swing.JLabel("Nombre del Gasto:"));
            panel.add(txtNombre);
            panel.add(chkRequiere);

            int resultado = javax.swing.JOptionPane.showConfirmDialog(parent, panel, 
                    "Modificar Tipo de Gasto", javax.swing.JOptionPane.OK_CANCEL_OPTION, javax.swing.JOptionPane.PLAIN_MESSAGE);

            if (resultado == javax.swing.JOptionPane.OK_OPTION) {
                String nombreNuevo = txtNombre.getText().trim();
                
                if (nombreNuevo.isEmpty()) {
                    javax.swing.JOptionPane.showMessageDialog(parent, "El nombre no puede estar vacío.");
                    return;
                }
                
                int requiereNuevo = chkRequiere.isSelected() ? 1 : 0;
                
                com.ometeotl.tlaxcalli.PERSISTENCIA.Interfaces.I_InicioDAO dao = 
                    com.ometeotl.tlaxcalli.PERSISTENCIA.Interfaces.DAOFactory.getInicioDAO();
                
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