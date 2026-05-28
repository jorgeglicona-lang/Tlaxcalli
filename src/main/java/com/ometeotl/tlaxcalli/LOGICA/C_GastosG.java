package com.ometeotl.tlaxcalli.LOGICA;

import com.github.lgooddatepicker.components.DatePicker;
import com.ometeotl.tlaxcalli.PERSISTENCIA.Interfaces.DAOFactory;
import com.ometeotl.tlaxcalli.PERSISTENCIA.Interfaces.IGastosGeneralesDAO;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

public class C_GastosG {
    
    private IGastosGeneralesDAO dao = DAOFactory.getGastosGeneralesDAO();

    // Llena la tabla y calcula el total automáticamente
    public void cargarSemanaActual(JTable tabla, JTextField txtTotal) {
        DefaultTableModel modelo = dao.obtenerGastosSemana();
        tabla.setModel(modelo);
        
        // Ocultar la columna ID para que el usuario no la vea, pero podamos usarla
        if (tabla.getColumnCount() > 0) {
            tabla.getColumnModel().getColumn(0).setMinWidth(0);
            tabla.getColumnModel().getColumn(0).setMaxWidth(0);
            tabla.getColumnModel().getColumn(0).setWidth(0);
        }
        
        calcularTotal(tabla, txtTotal);
    }

    public void buscarPorFechas(String inicio, String fin, JTable tabla, JTextField txtTotal) {
        DefaultTableModel modelo = dao.obtenerGastosPorRango(inicio, fin);
        tabla.setModel(modelo);

        if (tabla.getColumnCount() > 0) {
            tabla.getColumnModel().getColumn(0).setMinWidth(0);
            tabla.getColumnModel().getColumn(0).setMaxWidth(0);
            tabla.getColumnModel().getColumn(0).setWidth(0);
        }
        calcularTotal(tabla, txtTotal);
    }
    
    // Actualizamos el agregar para pasar la fecha
    public void agregarGasto(JFrame parent, JTable tabla, JTextField txTotal) {
        JTextField txtDesc = new JTextField();
        JTextField txtMonto = new JTextField(); 
        DatePicker dpFecha = new DatePicker();
        dpFecha.setDateToToday(); // Por defecto hoy

        Object[] message = { 
            "Descripción del Gasto:", txtDesc, 
            "Monto ($):", txtMonto,
            "Fecha del gasto:", dpFecha 
        };

        int option = JOptionPane.showConfirmDialog(parent, message, "Nuevo Gasto Administrativo", 
                JOptionPane.OK_CANCEL_OPTION);

        if (option == JOptionPane.OK_OPTION) {
            try {
                String descripcion = txtDesc.getText().trim();
                double monto = Double.parseDouble(txtMonto.getText().trim());
                String fecha = dpFecha.getDate().toString();

                if (monto > 0 && !descripcion.isEmpty()) {
                    if(dao.registrarGasto(descripcion, monto, fecha)){
                        JOptionPane.showMessageDialog(parent, "Gasto registrado.");
                        cargarSemanaActual(tabla,txTotal);
                    } 
                }else {
                    JOptionPane.showMessageDialog(parent, "Revise los datos. El monto debe ser mayor a 0.");
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(parent, "Error en los datos ingresados.");
            }
        }
    }

    public void eliminarGasto(JFrame parent, JTable tabla, JTextField txTotal) {
        int fila = tabla.getSelectedRow();
        if (fila == -1) {
            JOptionPane.showMessageDialog(parent, "Debe seleccionar un gasto de la tabla.");
            return;
        }

        int id = (int) tabla.getValueAt(fila, 0); 
        int confirmar = JOptionPane.showConfirmDialog(parent, "¿Seguro que quiere eliminar este gasto?");

        if (confirmar == JOptionPane.YES_OPTION) {
            if (dao.eliminarGasto(id)) {
                cargarSemanaActual(tabla, txTotal);
            }
        }
    }

    // Método para sumar toda la columna de montos
    private void calcularTotal(JTable tabla, JTextField txtTotal) {
        double total = 0;
        for (int i = 0; i < tabla.getRowCount(); i++) {
            total += Double.parseDouble(tabla.getValueAt(i, 2).toString());
        }
        txtTotal.setText(String.format("%.2f", total));
    }
}