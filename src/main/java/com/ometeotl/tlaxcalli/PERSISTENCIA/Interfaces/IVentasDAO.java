package com.ometeotl.tlaxcalli.PERSISTENCIA.Interfaces;

import javax.swing.table.DefaultTableModel;
import java.util.List;

public interface IVentasDAO {
    // El guardado maestro
    boolean guardarCorteCompleto(int idEmpleado, double kTortillaRep, double kTortillaPub, double kMasa, 
                                 DefaultTableModel modeloProductos, DefaultTableModel modeloGastos);
    
    // Consultas de validación
    double obtenerTotalRepartoHoy();
    // Nueva herramienta para no hardcodear precios
    double obtenerPrecioProducto(int idProducto);
    // Listas para llenar los ComboBox (¡Esto NO debe estar en la ventana gráfica!)
    List<com.ometeotl.tlaxcalli.LOGICA.ProductoItem> obtenerProductosParaVenta();
    List<com.ometeotl.tlaxcalli.LOGICA.GastoItem> obtenerGastosParaVenta();
}