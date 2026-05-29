package com.ometeotl.tlaxcalli.LOGICA;

import com.github.lgooddatepicker.components.DatePicker;
import static com.ometeotl.tlaxcalli.HerramientasVisuales.ocultarColumna;
import com.ometeotl.tlaxcalli.PERSISTENCIA.Interfaces.DAOFactory;
import com.ometeotl.tlaxcalli.PERSISTENCIA.Interfaces.IGastosGeneralesDAO;
import javax.swing.JFrame;
import static javax.swing.JOptionPane.OK_CANCEL_OPTION;
import static javax.swing.JOptionPane.OK_OPTION;
import static javax.swing.JOptionPane.YES_OPTION;
import static javax.swing.JOptionPane.showConfirmDialog;
import static javax.swing.JOptionPane.showMessageDialog;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

public class C_GastosG {
    
    private final IGastosGeneralesDAO dao = DAOFactory.getGastosGeneralesDAO();

    // Llena la tabla y calcula el total automáticamente
    public void cargarSemanaActual(JTable tabla, JTextField txtTotal) {
        DefaultTableModel modelo = dao.obtenerGastosSemana();
        tabla.setModel(modelo);
        ocultarColumna(tabla,0);
        calcularTotal(tabla, txtTotal);
    }

    public void buscarPorFechas(String inicio, String fin, JTable tabla, JTextField txtTotal) {
        DefaultTableModel modelo = dao.obtenerGastosPorRango(inicio, fin);
        tabla.setModel(modelo);
        ocultarColumna(tabla,0);
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

        int option = showConfirmDialog(parent, message, "Nuevo Gasto Administrativo", 
                OK_CANCEL_OPTION);

        if (option == OK_OPTION) {
            try {
                String descripcion = txtDesc.getText().trim();
                double monto = Double.parseDouble(txtMonto.getText().trim());
                String fecha = dpFecha.getDate().toString();

                if (monto > 0 && !descripcion.isEmpty()) {
                    if(dao.registrarGasto(descripcion, monto, fecha)){
                        showMessageDialog(parent, "Gasto registrado.");
                        cargarSemanaActual(tabla,txTotal);
                    } 
                }else {
                    showMessageDialog(parent, "Revise los datos. El monto debe ser mayor a 0.");
                }
            } catch (Exception e) {
                showMessageDialog(parent, "Error en los datos ingresados.");
            }
        }
    }

    public void eliminarGasto(JFrame parent, JTable tabla, JTextField txTotal) {
        int fila = tabla.getSelectedRow();
        if (fila == -1) {
            showMessageDialog(parent, "Debe seleccionar un gasto de la tabla.");
            return;
        }

        int id = (int) tabla.getValueAt(fila, 0); 
        int confirmar = showConfirmDialog(parent, "¿Seguro que quiere eliminar este gasto?");

        if (confirmar == YES_OPTION) {
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