package com.ometeotl.tlaxcalli.PERSISTENCIA;

import com.ometeotl.tlaxcalli.PERSISTENCIA.Interfaces.ILoginDAO;
import java.security.MessageDigest;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class LoginSQLiteDAO implements ILoginDAO {

    // Encriptador local para no depender de funciones exclusivas de SQL Server
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
    public boolean validarAcceso(String usuario, String contrasenia) {
        // Usamos EXCLUSIVAMENTE la conexión portátil
        CSQLiteConnection conexionPortatil = new CSQLiteConnection();
        
        // Consulta limpia y directa para SQLite respetando sus nombres de columna exactos
        String sql = "SELECT E.Id_empleado, E.Nombre, E.Puesto " +
                     "FROM Logeo L " +
                     "INNER JOIN Empleados E ON L.Id_empleado = E.Id_empleado " +
                     "WHERE L.Nombre = ? AND L.Contrasena = ? AND E.Estatus = 'Activo'";
        
        try (Connection con = conexionPortatil.establecerConexionPortatil();
             PreparedStatement ps = con.prepareStatement(sql)) {
            
            ps.setString(1, usuario);
            ps.setString(2, generarSHA512(contrasenia)); // Encriptamos antes de mandar a SQLite
            
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    // Si hay coincidencia, llenamos la sesión para que el controlador C_Login evalúe los permisos
                    com.ometeotl.tlaxcalli.LOGICA.C_Sesion_login.idUsuario = rs.getInt("Id_empleado");
                    com.ometeotl.tlaxcalli.LOGICA.C_Sesion_login.nombre = rs.getString("Nombre");
                    com.ometeotl.tlaxcalli.LOGICA.C_Sesion_login.puesto = rs.getString("Puesto");
                    return true;
                }
            }
        } catch (Exception e) {
            System.err.println("Error en LoginSQLiteDAO: " + e.getMessage());
        }
        return false;
    }
}