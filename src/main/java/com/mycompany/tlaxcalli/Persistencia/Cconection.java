
package com.mycompany.tlaxcalli.Persistencia;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.swing.JOptionPane;

public class Cconection { // Te perdono el nombre por ahora, luego lo cambiamos

    // 1. CONFIGURACIÓN "TODO TERRENO"
    // "localhost" significa: "búscame en esta misma computadora"
    private static final String IP = "localhost"; 
    private static final String PUERTO = "1433"; 
    private static final String BASE_DATOS = "Tlaxcalli"; // Tu base del script
    
    // Credenciales del script SQL que me pasaste
    private static final String USUARIO = "Admin";
    private static final String CONTRASENA = "Titolalo_099"; // <--- PON TU CONTRASEÑA REAL

    public Connection establecerConexion() {
        Connection conectar = null;
        
        try {
            // 2. LA CADENA DE CONEXIÓN ROBUSTA
            // encrypt=true;trustServerCertificate=true; es OBLIGATORIO hoy en día para desarrollo local
            String cadena = "jdbc:sqlserver://" + IP + ":" + PUERTO + ";"
                          + "databaseName=" + BASE_DATOS + ";"
                          + "encrypt=true;trustServerCertificate=true;";

            conectar = DriverManager.getConnection(cadena, USUARIO, CONTRASENA);
            
            // Solo para probar (borraremos esto después)
            System.out.println("✅ Conexión Exitosa a la Base de Datos");

        } catch (SQLException e) {
            // 3. MENSAJE DE ERROR ÚTIL
            // Esto te dirá EXACTAMENTE por qué falla (puerto cerrado, mala contraseña, etc.)
            System.err.println("❌ Error conectando: " + e.toString());
            JOptionPane.showMessageDialog(null, "Error de conexión: " + e.toString());
        }
        
        return conectar;
    }
}