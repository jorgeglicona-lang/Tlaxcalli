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

    // ... sus métodos anteriores de obtenerProductosTabla y obtenerCatGastosTabla ...

    @Override
    public boolean registrarProducto(String nombre, double precio, int esComodin) {
        CSQLiteConnection conMngr = new CSQLiteConnection();
        String sql = "INSERT INTO Productos (Nom_producto, Precio, Es_Comodin) VALUES (?, ?, ?)";
        
        try (java.sql.Connection con = conMngr.establecerConexionPortatil();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, nombre);
            ps.setDouble(2, precio);
            ps.setInt(3, esComodin);
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            System.err.println("Error al registrar producto: " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean modificarProducto(int idProducto, String nombre, double precio, int esComodin) {
        CSQLiteConnection conMngr = new CSQLiteConnection();
        String sql = "UPDATE Productos SET Nom_producto = ?, Precio = ?, Es_Comodin = ? WHERE Id_producto = ?";
        
        try (java.sql.Connection con = conMngr.establecerConexionPortatil();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, nombre);
            ps.setDouble(2, precio);
            ps.setInt(3, esComodin);
            ps.setInt(4, idProducto);
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            System.err.println("Error al modificar producto: " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean eliminarProducto(int idProducto) {
        CSQLiteConnection conMngr = new CSQLiteConnection();
        // Borrado físico directo (si no hay ventas vinculadas)
        String sql = "DELETE FROM Productos WHERE Id_producto = ?";
        
        try (java.sql.Connection con = conMngr.establecerConexionPortatil();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, idProducto);
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            System.err.println("Error al eliminar producto (posible llave foránea): " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean registrarCatGasto(String nombre, int requiereDesc) {
        CSQLiteConnection conMngr = new CSQLiteConnection();
        String sql = "INSERT INTO Cat_Gastos (Nombre, Requiere_Descripcion) VALUES (?, ?)";
        
        try (java.sql.Connection con = conMngr.establecerConexionPortatil();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, nombre);
            ps.setInt(2, requiereDesc);
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            System.err.println("Error al registrar catálogo de gasto: " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean modificarCatGasto(int idTipo, String nombre, int requiereDesc) {
        CSQLiteConnection conMngr = new CSQLiteConnection();
        String sql = "UPDATE Cat_Gastos SET Nombre = ?, Requiere_Descripcion = ? WHERE Id_tipo = ?";
        
        try (java.sql.Connection con = conMngr.establecerConexionPortatil();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, nombre);
            ps.setInt(2, requiereDesc);
            ps.setInt(3, idTipo);
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            System.err.println("Error al modificar catálogo de gasto: " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean eliminarCatGasto(int idTipo) {
        CSQLiteConnection conMngr = new CSQLiteConnection();
        String sql = "DELETE FROM Cat_Gastos WHERE Id_tipo = ?";
        
        try (java.sql.Connection con = conMngr.establecerConexionPortatil();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, idTipo);
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            System.err.println("Error al eliminar gasto (posible llave foránea): " + e.getMessage());
            return false;
        }
    }
}