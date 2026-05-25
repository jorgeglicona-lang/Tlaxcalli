package com.ometeotl.tlaxcalli.LOGICA;

import com.ometeotl.tlaxcalli.PERSISTENCIA.Interfaces.DAOFactory;
import com.ometeotl.tlaxcalli.PERSISTENCIA.Interfaces.IGastosGeneralesDAO;
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
public boolean agregarGasto(String descripcion, double monto, String fecha) {
    return dao.registrarGasto(descripcion, monto, fecha);
}

    public boolean eliminarGasto(int idGasto) {
        return dao.eliminarGasto(idGasto);
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