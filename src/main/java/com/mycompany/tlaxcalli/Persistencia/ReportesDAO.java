package com.mycompany.tlaxcalli.Persistencia;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class ReportesDAO {

    // Método para extraer nombres de la base de datos
    public List<String> obtenerNombresEmpleadosFiltro() {
        List<String> empleados = new ArrayList<>();
        Cconection conexion = new Cconection();
        String sql = "SELECT Nombre FROM Empleados WHERE Puesto IN ('Repartidor', 'Mostrador')";
        
        try (Connection con = conexion.establecerConexion();
             PreparedStatement pst = con.prepareStatement(sql);
             ResultSet rs = pst.executeQuery()) {
            
            while (rs.next()) {
                empleados.add(rs.getString("Nombre"));
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error en DAO Empleados: " + e.toString());
        }
        return empleados;
    }

    // Método para consultar y llenar la tabla de ventas (devuelve la suma)
    public double llenarVentas(String empleado, DefaultTableModel modelo) {
        double suma = 0;
        Cconection conexion = new Cconection();
        String sql = "SELECT E.Nombre, P.Nom_producto, V.Total_Dinero " +
                     "FROM Ventas_Diarias V " +
                     "JOIN Empleados E ON V.Id_empleado = E.Id_empleado " +
                     "JOIN Productos P ON V.Id_producto = P.Id_producto ";
                     
        if (!empleado.equals("Todos")) {
            sql += "WHERE E.Nombre = '" + empleado + "'";
        }

        try (Connection con = conexion.establecerConexion();
             PreparedStatement pst = con.prepareStatement(sql);
             ResultSet rs = pst.executeQuery()) {
             
            while (rs.next()) {
                double monto = rs.getDouble("Total_Dinero");
                modelo.addRow(new Object[]{rs.getString("Nombre"), rs.getString("Nom_producto"), monto});
                suma += monto;
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error en DAO Ventas: " + e.toString());
        }
        return suma;
    }

    // Método para consultar y llenar la tabla de gastos (devuelve la suma)
    public double llenarGastos(String empleado, DefaultTableModel modelo) {
        double suma = 0;
        Cconection conexion = new Cconection();
        String sql = "SELECT E.Nombre, G.Descripcion, G.Monto " +
                     "FROM Gastos G " +
                     "JOIN Empleados E ON G.Id_empleado = E.Id_empleado ";
                     
        if (!empleado.equals("Todos")) {
            sql += "WHERE E.Nombre = '" + empleado + "'";
        }
        
        try (Connection con = conexion.establecerConexion();
             PreparedStatement pst = con.prepareStatement(sql);
             ResultSet rs = pst.executeQuery()) {
             
            while (rs.next()) {
                double monto = rs.getDouble("Monto");
                modelo.addRow(new Object[]{rs.getString("Nombre"), rs.getString("Descripcion"), monto});
                suma += monto;
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error en DAO Gastos: " + e.toString());
        }
        return suma;
    }
}