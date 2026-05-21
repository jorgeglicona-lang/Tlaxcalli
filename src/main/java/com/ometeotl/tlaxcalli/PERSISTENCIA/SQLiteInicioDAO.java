package com.ometeotl.tlaxcalli.PERSISTENCIA;

import com.ometeotl.tlaxcalli.PERSISTENCIA.Interfaces.I_InicioDAO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.table.DefaultTableModel;

public class SQLiteInicioDAO implements I_InicioDAO {

    @Override
    public DefaultTableModel obtenerProductosTabla() {
        // Respetamos las columnas exactas de su script oficial de SQL Server
        String[] titulos = {"ID Producto", "Producto", "Precio", "Es Comodín"};
        DefaultTableModel modelo = new DefaultTableModel(null, titulos);
        String sql = "SELECT Id_producto, Nom_producto, Precio, Es_Comodin FROM Productos";
        
        CSQLiteConnection conMngr = new CSQLiteConnection();
        
        try (Connection con = conMngr.establecerConexionPortatil();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            
            while (rs.next()) {
                Object[] fila = new Object[4];
                fila[0] = rs.getInt("Id_producto");
                fila[1] = rs.getString("Nom_producto");
                fila[2] = rs.getDouble("Precio");
                // Traducimos el bit/int a algo visual para la tabla
                fila[3] = rs.getInt("Es_Comodin") == 1 ? "Sí" : "No";
                
                modelo.addRow(fila);
            }
        } catch (Exception e) {
            System.err.println("Error al cargar productos en la sala SQLite: " + e.getMessage());
        }
        return modelo;
    }
    
    @Override
    public DefaultTableModel obtenerCatGastosTabla() {
        // Respetamos las columnas exactas de su tabla Cat_Gastos
        String[] titulos = {"ID Tipo", "Nombre Gasto", "Requiere Descripción"};
        DefaultTableModel modelo = new DefaultTableModel(null, titulos);
        String sql = "SELECT Id_tipo, Nombre, Requiere_Descripcion FROM Cat_Gastos";
        
        CSQLiteConnection conMngr = new CSQLiteConnection();
        
        try (Connection con = conMngr.establecerConexionPortatil();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            
            while (rs.next()) {
                Object[] fila = new Object[3];
                fila[0] = rs.getInt("Id_tipo");
                fila[1] = rs.getString("Nombre");
                fila[2] = rs.getInt("Requiere_Descripcion") == 1 ? "Sí" : "No";
                
                modelo.addRow(fila);
            }
        } catch (Exception e) {
            System.err.println("Error al cargar catálogo de gastos en la sala SQLite: " + e.getMessage());
        }
        return modelo;
    }
}