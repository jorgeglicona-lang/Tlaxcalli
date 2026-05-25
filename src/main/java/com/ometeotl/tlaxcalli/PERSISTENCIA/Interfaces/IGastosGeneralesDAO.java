package com.ometeotl.tlaxcalli.PERSISTENCIA.Interfaces;

import javax.swing.table.DefaultTableModel;

public interface IGastosGeneralesDAO {
    DefaultTableModel obtenerGastosSemana();
    DefaultTableModel obtenerGastosPorRango(String inicio, String fin);
    boolean registrarGasto(String descripcion, double monto, String fecha);
    boolean eliminarGasto(int idGasto);
}