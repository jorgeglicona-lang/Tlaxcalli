package com.ometeotl.tlaxcalli.PERSISTENCIA.Interfaces;

import javax.swing.table.DefaultTableModel;

public interface I_InicioDAO {
    DefaultTableModel obtenerProductosTabla();
    DefaultTableModel obtenerCatGastosTabla();
}