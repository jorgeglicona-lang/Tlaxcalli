package com.ometeotl.tlaxcalli.LOGICA;

import com.ometeotl.tlaxcalli.PERSISTENCIA.ReportesDAO;
import com.ometeotl.tlaxcalli.PERSISTENCIA.GGAdministrativosDAO;
import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;
import java.util.List;
import com.ometeotl.tlaxcalli.IGU.Reportes;



public class C_GenerarReporte {
    
    private ReportesDAO reportesDao = new ReportesDAO();
    private GGAdministrativosDAO gastosAdmDao = new GGAdministrativosDAO();

    public void prepararPDF(String fechaInicio, String fechaFin, String empleadoFiltro) {
        // 1. Creamos modelos temporales para extraer la info de los DAOs
        DefaultTableModel modeloVentas = new DefaultTableModel();
        DefaultTableModel modeloGastosOp = new DefaultTableModel();
        DefaultTableModel modeloGastosAdm = gastosAdmDao.obtenerGastosPorRango(fechaInicio, fechaFin);
        
        modeloVentas.addColumn("Empleado");
        modeloVentas.addColumn("Producto");
        modeloVentas.addColumn("Monto ($)");

        modeloGastosOp.addColumn("Empleado");
        modeloGastosOp.addColumn("Descripción");
        modeloGastosOp.addColumn("Monto ($)");

        // 2. Ejecutamos las consultas que ya tenemos (usando sus DAOs actuales)
        double totalV = reportesDao.llenarVentas(empleadoFiltro, fechaInicio, fechaFin, modeloVentas);
        double totalGOp = reportesDao.llenarGastos(empleadoFiltro, fechaInicio, fechaFin, modeloGastosOp);
        
        // 3. Calculamos totales generales
        double totalGAdm = 0;
        for (int i = 0; i < modeloGastosAdm.getRowCount(); i++) {
            totalGAdm += Double.parseDouble(modeloGastosAdm.getValueAt(i, 2).toString());
        }

        // 4. Llamamos al generador visual enviando todos los datos recolectados
        GeneradorReportes pdf = new GeneradorReportes();
        pdf.crearDocumento(fechaInicio, fechaFin, empleadoFiltro, modeloVentas, modeloGastosOp, modeloGastosAdm, totalV, totalGOp, totalGAdm);
    }
}