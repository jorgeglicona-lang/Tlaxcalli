package com.ometeotl.tlaxcalli.LOGICA;

import java.util.List;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import com.ometeotl.tlaxcalli.PERSISTENCIA.Interfaces.DAOFactory;
import com.ometeotl.tlaxcalli.PERSISTENCIA.Interfaces.IReportesDAO;
import javax.swing.JFrame;
import com.github.lgooddatepicker.components.DatePicker;

public class C_Reportes {
    
    private final IReportesDAO dao = DAOFactory.getReportesDAO();
    
    // Lógica para estructurar el ComboBox
    public void cargarEmpleados(JComboBox<String> combo) {
        combo.removeAllItems();
        combo.addItem("Todos");
        
        List<String> empleados = dao.obtenerNombresEmpleadosFiltro();
        for (String emp : empleados) {
            combo.addItem(emp);
        }
    }

    // Lógica para coordinar las tablas, hacer las restas y actualizar textos
    public void procesarReporte(JFrame parent, JComboBox CB_Empleados,
            JTable tablaVentas, JTable tablaGastos, JLabel lblTotalV, 
            JLabel lblTotalG, JLabel lblNeto,DatePicker ini,DatePicker fin) {
        
        String empleado = CB_Empleados.getSelectedItem().toString();
        String fInicio= ini.getDate().toString();
        String fFin = fin.getDate().toString();
        DefaultTableModel modVentas = (DefaultTableModel) tablaVentas.getModel();
        DefaultTableModel modGastos = (DefaultTableModel) tablaGastos.getModel();
        
        if (modVentas.getColumnCount() == 0) {
            modVentas.addColumn("Empleado"); 
            modVentas.addColumn("Producto"); 
            modVentas.addColumn("Monto ($)");
        }
        
        if (modGastos.getColumnCount() == 0) {
            modGastos.addColumn("Empleado"); 
            modGastos.addColumn("Descripción"); 
            modGastos.addColumn("Monto ($)");
        }
        // Limpiamos los datos viejos
        modVentas.setRowCount(0);
        modGastos.setRowCount(0);
        
        // El DAO ahora pide las fechas también y nos devuelve los totales
        double totalVentas = dao.llenarVentas(empleado, fInicio, fFin, modVentas);
        double totalGastos = dao.llenarGastos(empleado, fInicio, fFin, modGastos);
        
        // Matemáticas para la ganancia
        double ingresosNetos = totalVentas - totalGastos;
        
        // Actualizamos las etiquetas visuales
        lblTotalV.setText(String.format("$%.2f", totalVentas));
        lblTotalG.setText(String.format("$%.2f", totalGastos));
        lblNeto.setText(String.format("$%.2f", ingresosNetos));
    }
}