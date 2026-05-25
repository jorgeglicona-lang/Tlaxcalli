package com.ometeotl.tlaxcalli.PERSISTENCIA.Interfaces;

import java.util.List;
import javax.swing.table.DefaultTableModel;

public interface IReportesDAO {
    List<String> obtenerNombresEmpleadosFiltro();
    double llenarVentas(String empleado, String fechaInicio, String fechaFin, DefaultTableModel modelo);
    double llenarGastos(String empleado, String fechaInicio, String fechaFin, DefaultTableModel modelo);
}