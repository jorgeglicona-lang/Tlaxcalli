package com.mycompany.tlaxcalli.Logica;

import com.mycompany.tlaxcalli.Igu.Inicio;
import com.mycompany.tlaxcalli.Igu.Login;
import com.mycompany.tlaxcalli.Persistencia.Cconection;
import java.sql.*;
import javax.swing.JOptionPane;

public class C_Login {
    private String Usuario;
    private String password;
  
    public void logeo(String User,String Passw){
        this.Usuario=User;
        this.password=Passw;
    }
    
    // Cambiamos void por boolean
    public boolean ValUsuario() {
        Cconection Clog = new Cconection();
        
        // SQL BLINDADO: Forzamos (CAST) a que el parámetro sea NVARCHAR
        // Así coincide 100% con lo que guardamos en la base de datos.
        String sql = "SELECT cast(case when L.Contrasena = HASHBYTES('SHA2_512', CAST(? AS NVARCHAR(4000))) then 1 else 0 end as int) as Bandera, " +
                     "E.Id_empleado, E.Nombre, E.Puesto " +
                     "FROM Logeo L " +
                     "INNER JOIN Empleados E ON L.Id_empleado = E.Id_empleado " + 
                     "WHERE L.Nombre = ? AND E.Estatus = 'Activo'";
        
        try {
            java.sql.PreparedStatement ps = Clog.establecerConexion().prepareStatement(sql);
            ps.setString(1, password); // Contraseña
            ps.setString(2, Usuario);  // Usuario
            
            java.sql.ResultSet rs = ps.executeQuery();
            
            if (rs.next()) {
                int esValido = rs.getInt("Bandera");
                
                if (esValido == 1) {
                    // --- CONTRASEÑA CORRECTA, AHORA REVISAMOS PUESTO ---
                    String puestoEncontrado = rs.getString("Puesto");
                    
                    if (puestoEncontrado.equalsIgnoreCase("Gerente") || 
                        puestoEncontrado.equalsIgnoreCase("Administrador")){
                        
                        // Guardar Sesión
                        C_Sesion_login.idUsuario = rs.getInt("Id_empleado");
                        C_Sesion_login.nombre = rs.getString("Nombre");
                        C_Sesion_login.puesto = puestoEncontrado;
                        
                        // Abrir Inicio
                        Inicio ini = new Inicio();
                        ini.setVisible(true);
                        ini.setLocationRelativeTo(null);
                        ini.settext(C_Sesion_login.nombre);
                        
                        return true; // Éxito
                        
                    } else {
                        javax.swing.JOptionPane.showMessageDialog(null, "⛔ Acceso Denegado.\nTu puesto (" + puestoEncontrado + ") no tiene permisos de Administrador.");
                        return false;
                    }
                    
                } else {
                    javax.swing.JOptionPane.showMessageDialog(null, "❌ Contraseña incorrecta.");
                    return false;
                }
            } else {
                javax.swing.JOptionPane.showMessageDialog(null, "⚠️ Usuario no encontrado.");
                return false;
            }
            
        } catch (Exception e) {
            javax.swing.JOptionPane.showMessageDialog(null, "Error crítico: " + e.getMessage());
            return false;
        }
    }
}
