package com.ometeotl.tlaxcalli.PERSISTENCIA.Interfaces;

import javax.swing.table.DefaultTableModel;

public interface IEmpleadosDAO {
    DefaultTableModel consultarEmpleados();
    boolean registrarEmpleado(String nombre, String app, String apm, String puesto, String usuario, String password);
    boolean eliminarEmpleado(int idEmpleado);
    boolean modificarEmpleado(int idEmpleado, String nombre, String app, String apm, String puesto, String usuario, String password);
    String obtenerNombreUsuario(int idEmpleado);
    DefaultTableModel consultarEmpleadosVisibles();
}