package com.ometeotl.tlaxcalli.PERSISTENCIA;

import com.ometeotl.tlaxcalli.PERSISTENCIA.Interfaces.ILoginDAO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class LoginSQLServerDAO implements ILoginDAO {

    @Override
    public boolean validarAcceso(String usuario, String contrasenia) {
        Cconection Clog = new Cconection();
        
        String sql = "SELECT cast(case when L.Contrasena = HASHBYTES('SHA2_512', CAST(? AS NVARCHAR(4000))) then 1 else 0 end as int) as Bandera, " +
                     "E.Id_empleado, E.Nombre, E.Puesto " + // Traemos el puesto en la consulta
                     "FROM Logeo L " +
                     "INNER JOIN Empleados E ON L.Id_empleado = E.Id_empleado " + 
                     "WHERE L.Nombre = ? AND E.Estatus = 'Activo'";
        
        try (Connection con = Clog.establecerConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            
            ps.setString(1, contrasenia);
            ps.setString(2, usuario);
            
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    int esValido = rs.getInt("Bandera");
                    if (esValido == 1) {
                        // ¡CORREGIDO, JEFE! El DAO extrae y llena la sesión completa para que el controlador pueda evaluar
                        com.ometeotl.tlaxcalli.LOGICA.C_Sesion_login.idUsuario = rs.getInt("Id_empleado");
                        com.ometeotl.tlaxcalli.LOGICA.C_Sesion_login.nombre = rs.getString("Nombre");
                        com.ometeotl.tlaxcalli.LOGICA.C_Sesion_login.puesto = rs.getString("Puesto"); // <--- Aquí pasamos el puesto
                        return true;
                    }
                }
            }
        } catch (Exception e) {
            System.err.println("Error en LoginSQLServerDAO: " + e.getMessage());
        }
        return false;
    }
}