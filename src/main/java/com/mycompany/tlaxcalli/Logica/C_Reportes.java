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
    public void procesarReporte(String empleado, JTable tablaVentas, JTable tablaGastos, JLabel lblTotalV, JLabel lblTotalG, JLabel lblNeto) {
        DefaultTableModel modVentas = (DefaultTableModel) tablaVentas.getModel();
        DefaultTableModel modGastos = (DefaultTableModel) tablaGastos.getModel();
        
        // Configuramos las columnas solo si no existen
        if (modVentas.getColumnCount() == 0) {
            modVentas.addColumn("Empleado"); modVentas.addColumn("Producto"); modVentas.addColumn("Monto ($)");
            modGastos.addColumn("Empleado"); modGastos.addColumn("Descripción"); modGastos.addColumn("Monto ($)");
        }
        
        modVentas.setRowCount(0); // Limpiamos datos viejos
        modGastos.setRowCount(0);
        
        // El DAO llena los modelos y nos devuelve las sumas mágicamente
        double totalVentas = dao.llenarVentas(empleado, modVentas);
        double totalGastos = dao.llenarGastos(empleado, modGastos);
        
        // Operación matemática del negocio
        double ingresosNetos = totalVentas - totalGastos;
        
        // Actualizamos las etiquetas visuales con formato de moneda
        lblTotalV.setText(String.format("$%.2f", totalVentas));
        lblTotalG.setText(String.format("$%.2f", totalGastos));
        lblNeto.setText(String.format("$%.2f", ingresosNetos));
    }
}