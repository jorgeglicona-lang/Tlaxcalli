package com.ometeotl.tlaxcalli.PERSISTENCIA.Interfaces;

import com.ometeotl.tlaxcalli.LOGICA.GastoItem;
import com.ometeotl.tlaxcalli.LOGICA.ProductoItem;
import javax.swing.table.DefaultTableModel;
import java.util.List;

public interface IVentasDAO {
    boolean guardarCorteCompleto(int idEmpleado, double kTortillaRep, double kTortillaPub, double kMasa, 
                                 DefaultTableModel modeloProductos, DefaultTableModel modeloGastos);
    double obtenerTotalRepartoHoy();
    List<ProductoItem> obtenerProductosParaVenta();
    List<GastoItem> obtenerGastosParaVenta();
    double[] obtenerCorteEmpleadoHoy(int idEmpleado);
    double obtenerPrecioProducto(int idProducto);
    
    List<Object[]> obtenerProductosCorteHoy(int idEmpleado);
    List<Object[]> obtenerGastosCorteHoy(int idEmpleado);
}