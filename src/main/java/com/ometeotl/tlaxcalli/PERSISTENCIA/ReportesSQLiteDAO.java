package com.ometeotl.tlaxcalli.PERSISTENCIA;

import com.ometeotl.tlaxcalli.PERSISTENCIA.Interfaces.IReportesDAO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.table.DefaultTableModel;

public class ReportesSQLiteDAO implements IReportesDAO {

    @Override
    public List<String> obtenerNombresEmpleadosFiltro() {
        List<String> empleados = new ArrayList<>();
        CSQLiteConnection conMngr = new CSQLiteConnection();
        // Ordenamos alfabéticamente para que el ComboBox se vea más profesional
        String sql = "SELECT Nombre FROM Empleados WHERE Puesto IN ('Repartidor', 'Mostrador') ORDER BY Nombre ASC";
        
        try (Connection con = conMngr.establecerConexionPortatil();
             PreparedStatement pst = con.prepareStatement(sql);
             ResultSet rs = pst.executeQuery()) {
            
            while (rs.next()) {
                empleados.add(rs.getString("Nombre"));
            }
        } catch (SQLException e) {
            System.err.println("Error SQLite Empleados Reporte: " + e.getMessage());
        }
        return empleados;
    }

    @Override
    public double llenarVentas(String empleado, String fechaInicio, String fechaFin, DefaultTableModel modelo) {
        double suma = 0;
        CSQLiteConnection conMngr = new CSQLiteConnection();
        
        // 🛠️ SQLite: Usamos date(V.Fecha) para asegurar una comparación estricta de días
        String sql = "SELECT E.Nombre, P.Nom_producto, V.Total_Dinero " +
                     "FROM Ventas_Diarias V " +
                     "JOIN Empleados E ON V.Id_empleado = E.Id_empleado " +
                     "JOIN Productos P ON V.Id_producto = P.Id_producto " +
                     "WHERE date(V.Fecha) BETWEEN date(?) AND date(?) ";
                     
        if (!empleado.equals("Todos")) {
            sql += "AND E.Nombre = ? ";
        }

        try (Connection con = conMngr.establecerConexionPortatil();
             PreparedStatement pst = con.prepareStatement(sql)) {
             
            pst.setString(1, fechaInicio);
            pst.setString(2, fechaFin);
            
            if (!empleado.equals("Todos")) {
                pst.setString(3, empleado);
            }

            try (ResultSet rs = pst.executeQuery()) {
                while (rs.next()) {
                    double monto = rs.getDouble("Total_Dinero");
                    modelo.addRow(new Object[]{
                        rs.getString("Nombre"), 
                        rs.getString("Nom_producto"), 
                        monto
                    });
                    suma += monto;
                }
            }
        } catch (SQLException e) {
            System.err.println("Error SQLite Ventas Reporte: " + e.getMessage());
        }
        return suma;
    }

    @Override
    public double llenarGastos(String empleado, String fechaInicio, String fechaFin, DefaultTableModel modelo) {
        double suma = 0;
        CSQLiteConnection conMngr = new CSQLiteConnection();
        
        String sql = "SELECT E.Nombre, G.Descripcion, G.Monto " +
                     "FROM Gastos G " +
                     "JOIN Empleados E ON G.Id_empleado = E.Id_empleado " +
                     "WHERE date(G.Fecha) BETWEEN date(?) AND date(?) ";
                     
        if (!empleado.equals("Todos")) {
            sql += "AND E.Nombre = ? ";
        }
        
        try (Connection con = conMngr.establecerConexionPortatil();
             PreparedStatement pst = con.prepareStatement(sql)) {
             
            pst.setString(1, fechaInicio);
            pst.setString(2, fechaFin);
            
            if (!empleado.equals("Todos")) {
                pst.setString(3, empleado);
            }

            try (ResultSet rs = pst.executeQuery()) {
                while (rs.next()) {
                    double monto = rs.getDouble("Monto");
                    modelo.addRow(new Object[]{rs.getString("Nombre"), rs.getString("Descripcion"), monto});
                    suma += monto;
                }
            }
        } catch (SQLException e) {
            System.err.println("Error SQLite Gastos Reporte: " + e.getMessage());
        }
        return suma;
    }
}