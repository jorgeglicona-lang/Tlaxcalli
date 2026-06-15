package com.ometeotl.tlaxcalli.PERSISTENCIA.Interfaces;

import com.ometeotl.tlaxcalli.LOGICA.GastoItem;
import com.ometeotl.tlaxcalli.LOGICA.ProductoItem;
import java.sql.Connection;
import javax.swing.table.DefaultTableModel;
import java.util.List;

public interface IVentasDAO {
    boolean guardarCorteCompleto(Connection con, int idEmpleado, double kTortillaRep, double kTortillaPub, double kMasa, 
                             DefaultTableModel modeloProductos, DefaultTableModel modeloGastos);
    double obtenerTotalRepartoHoy();
    double obtenerTotalRepartoHoy(Connection con);
    boolean actualizarKilosMostrador(Connection con, int idMostrador, double cantidad);
    double obtenerTotalMasaHoy(Connection con);
    double obtenerTotalMasaHoy();
    List<ProductoItem> obtenerProductosParaVenta();
    List<GastoItem> obtenerGastosParaVenta();
    double[] obtenerCorteEmpleadoHoy(int idEmpleado);
    double obtenerPrecioProducto(int idProducto);
    
    List<Object[]> obtenerProductosCorteHoy(int idEmpleado);
    List<Object[]> obtenerGastosCorteHoy(int idEmpleado);
}