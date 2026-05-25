package com.ometeotl.tlaxcalli.PERSISTENCIA.Interfaces;

import javax.swing.table.DefaultTableModel;
import java.util.List;

public interface IVentasDAO {
    boolean guardarCorteCompleto(int idEmpleado, double kTortillaRep, double kTortillaPub, double kMasa, 
                                 DefaultTableModel modeloProductos, DefaultTableModel modeloGastos);
    double obtenerTotalRepartoHoy();
    List<com.ometeotl.tlaxcalli.LOGICA.ProductoItem> obtenerProductosParaVenta();
    List<com.ometeotl.tlaxcalli.LOGICA.GastoItem> obtenerGastosParaVenta();
    double[] obtenerCorteEmpleadoHoy(int idEmpleado);
    double obtenerPrecioProducto(int idProducto);
    
    // 🚀 NUEVAS ARMAS: Para leer las tablas del registro previo
    List<Object[]> obtenerProductosCorteHoy(int idEmpleado);
    List<Object[]> obtenerGastosCorteHoy(int idEmpleado);
}