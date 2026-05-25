package com.ometeotl.tlaxcalli.LOGICA;

import com.ometeotl.tlaxcalli.PERSISTENCIA.Interfaces.DAOFactory;
import com.ometeotl.tlaxcalli.PERSISTENCIA.Interfaces.IReportesDAO;
import com.ometeotl.tlaxcalli.PERSISTENCIA.Interfaces.IGastosGeneralesDAO;
import javax.swing.table.DefaultTableModel;

public class C_GenerarReporte {
    
    // 🛠️ ¡REPARADO! Enchufamos las interfaces limpias a través de la fábrica genérica
    private IReportesDAO reportesDao = DAOFactory.getReportesDAO();
    private IGastosGeneralesDAO gastosGeneralesDao = DAOFactory.getGastosGeneralesDAO();

    public void prepararPDF(String fechaInicio, String fechaFin, String empleadoFiltro) {
        // 1. Creamos modelos temporales para extraer la info de los DAOs
        DefaultTableModel modeloVentas = new DefaultTableModel();
        DefaultTableModel modeloGastosOp = new DefaultTableModel();
        
        // 🛠️ ¡REPARADO! Usamos el DAO unificado de Gastos Generales mediante la fábrica
        DefaultTableModel modeloGastosAdm = gastosGeneralesDao.obtenerGastosPorRango(fechaInicio, fechaFin);
        
        modeloVentas.addColumn("Empleado");
        modeloVentas.addColumn("Producto");
        modeloVentas.addColumn("Monto ($)");

        modeloGastosOp.addColumn("Empleado");
        modeloGastosOp.addColumn("Descripción");
        modeloGastosOp.addColumn("Monto ($)");

        // 2. Ejecutamos las consultas portátiles desde SQLite
        double totalV = reportesDao.llenarVentas(empleadoFiltro, fechaInicio, fechaFin, modeloVentas);
        double totalGOp = reportesDao.llenarGastos(empleadoFiltro, fechaInicio, fechaFin, modeloGastosOp);
        
        // 3. Calculamos totales generales de la tabla administrativa (La columna 2 es el Monto)
        double totalGAdm = 0;
        for (int i = 0; i < modeloGastosAdm.getRowCount(); i++) {
            totalGAdm += Double.parseDouble(modeloGastosAdm.getValueAt(i, 2).toString());
        }

        // 4. Llamamos al generador de PDF enviando toda la información recolectada
        GeneradorReportes pdf = new GeneradorReportes();
        pdf.crearDocumento(fechaInicio, fechaFin, empleadoFiltro, modeloVentas, modeloGastosOp, modeloGastosAdm, totalV, totalGOp, totalGAdm);
    }
}