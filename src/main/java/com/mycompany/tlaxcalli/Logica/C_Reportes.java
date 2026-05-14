package com.mycompany.tlaxcalli.Logica;

import com.mycompany.tlaxcalli.Persistencia.ReportesDAO;
import java.util.List;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class C_Reportes {
    
    private ReportesDAO dao = new ReportesDAO();

    // Lógica para estructurar el ComboBox
    public void cargarEmpleados(JComboBox<String> combo) {
        combo.removeAllItems();
        combo.addItem("Todos"); // Añadimos la opción general primero
        
        List<String> empleados = dao.obtenerNombresEmpleadosFiltro();
        for (String emp : empleados) {
            combo.addItem(emp);
        }
    }

    // Lógica para coordinar las tablas, hacer las restas y actualizar textos
    public void procesarReporte(String empleado, String fInicio, String fFin, JTable tablaVentas, JTable tablaGastos, JLabel lblTotalV, JLabel lblTotalG, JLabel lblNeto) {
    DefaultTableModel modVentas = (DefaultTableModel) tablaVentas.getModel();
    DefaultTableModel modGastos = (DefaultTableModel) tablaGastos.getModel();
    
    modVentas.setRowCount(0);
    modGastos.setRowCount(0);
    
    // El DAO ahora pide las fechas también
    double totalVentas = dao.llenarVentas(empleado, fInicio, fFin, modVentas);
    double totalGastos = dao.llenarGastos(empleado, fInicio, fFin, modGastos);
    
    double ingresosNetos = totalVentas - totalGastos;
    
    lblTotalV.setText(String.format("$%.2f", totalVentas));
    lblTotalG.setText(String.format("$%.2f", totalGastos));
    lblNeto.setText(String.format("$%.2f", ingresosNetos));
}
}