package com.ometeotl.tlaxcalli.PERSISTENCIA;

import com.ometeotl.tlaxcalli.PERSISTENCIA.Interfaces.IEmpleadosDAO;
import java.security.MessageDigest;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.table.DefaultTableModel;

public class EmpleadosSQLiteDAO implements IEmpleadosDAO {

    // Encriptador en Java para sustituir el HASHBYTES de SQL Server
    private String generarSHA512(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-512");
            byte[] hash = md.digest(password.getBytes("UTF-8"));
            StringBuilder hexString = new StringBuilder();
            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }
            return hexString.toString().toUpperCase();
        } catch (Exception ex) { 
            return password; 
        }
    }

    @Override
    public DefaultTableModel consultarEmpleados() {
        String titulos[] = {"id", "nombre"};
        DefaultTableModel modelo = new DefaultTableModel(null, titulos);
        String sql = "SELECT Id_empleado, Nombre FROM Empleados WHERE Estatus = 'Activo' AND NOT (Nombre = 'Super' AND ApellidoP = 'User')";
        
        CSQLiteConnection conMngr = new CSQLiteConnection();
        try (Connection con = conMngr.establecerConexionPortatil();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                Object[] fila = new Object[2];
                fila[0] = rs.getInt("Id_empleado");
                fila[1] = rs.getString("Nombre");
                modelo.addRow(fila);
            }
        } catch (Exception e) {
            System.err.println("Error al listar empleados SQLite: " + e.getMessage());
        }
        return modelo;
    }

    @Override
    public boolean registrarEmpleado(String nombre, String app, String apm, String puesto, String usuario, String password) {
        CSQLiteConnection conMngr = new CSQLiteConnection();
        Connection con = null;
        try {
            con = conMngr.establecerConexionPortatil();
            con.setAutoCommit(false); // Transacción: Todo o nada

            String sqlEmp = "INSERT INTO Empleados (Nombre, ApellidoP, ApellidoM, Puesto) VALUES (?, ?, ?, ?)";
            try (PreparedStatement psEmp = con.prepareStatement(sqlEmp, Statement.RETURN_GENERATED_KEYS)) {
                psEmp.setString(1, nombre);
                psEmp.setString(2, app);
                psEmp.setString(3, apm);
                psEmp.setString(4, puesto);
                psEmp.executeUpdate();

                int idNuevoEmpleado = 0;
                try (ResultSet rs = psEmp.getGeneratedKeys()) {
                    if (rs.next()) {
                        idNuevoEmpleado = rs.getInt(1);
                    } else {
                        throw new Exception("No se obtuvo el ID autonumérico de SQLite.");
                    }
                }

                if (!usuario.isEmpty() && !password.isEmpty()) {
                    String sqlLog = "INSERT INTO Logeo (Id_empleado, Nombre, Contrasena) VALUES (?, ?, ?)";
                    try (PreparedStatement psLog = con.prepareStatement(sqlLog)) {
                        psLog.setInt(1, idNuevoEmpleado);
                        psLog.setString(2, usuario);
                        psLog.setString(3, generarSHA512(password)); // Encriptamos aquí
                        psLog.executeUpdate();
                    }
                }
            }
            con.commit();
            return true;
        } catch (Exception e) {
            System.err.println("Error registrando empleado en SQLite: " + e.getMessage());
            try { if (con != null) con.rollback(); } catch (Exception ex) {}
            return false;
        } finally {
            try { if (con != null) con.setAutoCommit(true); con.close(); } catch (Exception ex) {}
        }
    }

    @Override
    public boolean eliminarEmpleado(int idEmpleado) {
        CSQLiteConnection conMngr = new CSQLiteConnection();
        String sql = "UPDATE Empleados SET Estatus = 'Inactivo' WHERE Id_empleado = ?";
        try (Connection con = conMngr.establecerConexionPortatil();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, idEmpleado);
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            System.err.println("Error desactivando empleado SQLite: " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean modificarEmpleado(int idEmpleado, String nombre, String app, String apm, String puesto, String usuario, String password) {
        CSQLiteConnection conMngr = new CSQLiteConnection();
        Connection con = null;
        try {
            con = conMngr.establecerConexionPortatil();
            con.setAutoCommit(false);

            String sqlEmp = "UPDATE Empleados SET Nombre=?, ApellidoP=?, ApellidoM=?, Puesto=? WHERE Id_empleado=?";
            try (PreparedStatement psEmp = con.prepareStatement(sqlEmp)) {
                psEmp.setString(1, nombre);
                psEmp.setString(2, app);
                psEmp.setString(3, apm);
                psEmp.setString(4, puesto);
                psEmp.setInt(5, idEmpleado);
                psEmp.executeUpdate();
            }

            if (!puesto.equalsIgnoreCase("Administrador") && !puesto.equalsIgnoreCase("Gerente")) {
                String sqlDelete = "DELETE FROM Logeo WHERE Id_empleado = ?";
                try (PreparedStatement psLog = con.prepareStatement(sqlDelete)) {
                    psLog.setInt(1, idEmpleado);
                    psLog.executeUpdate();
                }
            } else {
                boolean tieneLogin = false;
                String sqlCheck = "SELECT Id_login FROM Logeo WHERE Id_empleado = ?";
                try (PreparedStatement psCheck = con.prepareStatement(sqlCheck)) {
                    psCheck.setInt(1, idEmpleado);
                    try (ResultSet rs = psCheck.executeQuery()) {
                        tieneLogin = rs.next();
                    }
                }

                if (tieneLogin) {
                    if (!password.isEmpty()) {
                        String sqlUpdate = "UPDATE Logeo SET Nombre=?, Contrasena=? WHERE Id_empleado=?";
                        try (PreparedStatement psLog = con.prepareStatement(sqlUpdate)) {
                            psLog.setString(1, usuario);
                            psLog.setString(2, generarSHA512(password));
                            psLog.setInt(3, idEmpleado);
                            psLog.executeUpdate();
                        }
                    } else {
                        String sqlUpdateUser = "UPDATE Logeo SET Nombre=? WHERE Id_empleado=?";
                        try (PreparedStatement psLog = con.prepareStatement(sqlUpdateUser)) {
                            psLog.setString(1, usuario);
                            psLog.setInt(2, idEmpleado);
                            psLog.executeUpdate();
                        }
                    }
                } else {
                    String sqlInsert = "INSERT INTO Logeo (Id_empleado, Nombre, Contrasena) VALUES (?, ?, ?)";
                    try (PreparedStatement psLog = con.prepareStatement(sqlInsert)) {
                        psLog.setInt(1, idEmpleado);
                        psLog.setString(2, usuario);
                        psLog.setString(3, generarSHA512(password));
                        psLog.executeUpdate();
                    }
                }
            }
            con.commit();
            return true;
        } catch (Exception e) {
            System.err.println("Error modificando empleado SQLite: " + e.getMessage());
            try { if (con != null) con.rollback(); } catch (Exception ex) {}
            return false;
        } finally {
            try { if (con != null) { con.setAutoCommit(true); con.close(); } } catch (Exception ex) {}
        }
    }

    @Override
    public String obtenerNombreUsuario(int idEmpleado) {
        String usuario = "";
        CSQLiteConnection conMngr = new CSQLiteConnection();
        String sql = "SELECT Nombre FROM Logeo WHERE Id_empleado = ?";
        try (Connection con = conMngr.establecerConexionPortatil();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, idEmpleado);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    usuario = rs.getString("Nombre");
                }
            }
        } catch (Exception e) {}
        return usuario;
    }

    @Override
    public DefaultTableModel consultarEmpleadosVisibles() {
        String[] titulos = {"ID", "Nombre", "Ap. Paterno", "Ap. Materno", "Puesto"};
        DefaultTableModel modelo = new DefaultTableModel(null, titulos);
        CSQLiteConnection conMngr = new CSQLiteConnection();
        String sql = "SELECT Id_empleado, Nombre, ApellidoP, ApellidoM, Puesto " +
                     "FROM Empleados WHERE Estatus = 'Activo' " +
                     "AND Nombre NOT IN ('Mostrador', 'Super')";
        
        try (Connection con = conMngr.establecerConexionPortatil();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Object[] fila = new Object[5];
                fila[0] = rs.getInt("Id_empleado");
                fila[1] = rs.getString("Nombre");
                fila[2] = rs.getString("ApellidoP");
                fila[3] = rs.getString("ApellidoM");
                fila[4] = rs.getString("Puesto");
                modelo.addRow(fila);
            }
        } catch (Exception e) {
            System.err.println("Error listando visibles SQLite: " + e.getMessage());
        }
        return modelo;
    }
    
    @Override
    public DefaultTableModel consultarVendedores() {
        String[] titulos = {"ID", "Nombre"};
        DefaultTableModel modelo = new DefaultTableModel(null, titulos);
        
        // Filtramos para que solo traiga al personal de reparto y mostrador
        String sql = "SELECT Id_empleado, Nombre FROM Empleados " +
                     "WHERE Puesto = 'Repartidor' OR Puesto = 'Mostrador' " +
                     "ORDER BY Nombre ASC";
        
        CSQLiteConnection conMngr = new CSQLiteConnection();
        
        try (java.sql.Connection con = conMngr.establecerConexionPortatil();
             java.sql.PreparedStatement ps = con.prepareStatement(sql);
             java.sql.ResultSet rs = ps.executeQuery()) {
            
            while (rs.next()) {
                Object[] fila = new Object[2];
                fila[0] = rs.getInt("Id_empleado");
                fila[1] = rs.getString("Nombre");
                modelo.addRow(fila);
            }
        } catch (Exception e) {
            System.err.println("Error al consultar vendedores en SQLite: " + e.getMessage());
        }
        return modelo;
    }
    
    @Override
    public int contarAdministradoresActivos() {
        // Contamos cuántos empleados con puesto de Admin o Gerente tienen un login amarrado
        String sql = "SELECT COUNT(*) FROM Logeo l "
                   + "INNER JOIN Empleados e ON l.Id_empleado = e.Id_empleado "
                   + "WHERE e.Puesto IN ('Administrador', 'Gerente') AND e.Estatus = 'Activo'";
        try (Connection con = new CSQLiteConnection().establecerConexionPortatil();
             Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            if (rs.next()) {
                return rs.getInt(1); // Devuelve el número de jefes vivos
            }
        } catch (SQLException e) {
            System.err.println("Error al contar jefes: " + e.getMessage());
        }
        return 0;
    }
}