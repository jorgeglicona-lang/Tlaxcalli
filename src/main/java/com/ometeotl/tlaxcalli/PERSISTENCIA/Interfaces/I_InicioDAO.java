package com.ometeotl.tlaxcalli.PERSISTENCIA.Interfaces;

import javax.swing.table.DefaultTableModel;

public interface I_InicioDAO {
    DefaultTableModel obtenerProductosTabla();
    DefaultTableModel obtenerCatGastosTabla();
    
    boolean registrarProducto(String nombre, double precio, int esComodin);
    boolean modificarProducto(int idProducto, String nombre, double precio, int esComodin);
    boolean eliminarProducto(int idProducto);
    
    boolean registrarCatGasto(String nombre, int requiereDesc);
    boolean modificarCatGasto(int idTipo, String nombre, int requiereDesc);
    boolean eliminarCatGasto(int idTipo);
}