package com.ometeotl.tlaxcalli;

import com.ometeotl.tlaxcalli.IGU.Login;
import com.ometeotl.tlaxcalli.PERSISTENCIA.CSQLiteConnection;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Properties;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

public class Tlaxcalli {
    
    private final String ARCHIVO_CONFIG = "config.properties";
    private final String CLAVE_NEGOCIO = "negocio.nombre";

    public void inicializarNombreNegocio(JFrame parent, JLabel lblNegocio) {
        Properties propiedades = new Properties();
        File archivo = new File(ARCHIVO_CONFIG);
        String nombreNegocio = "";

        try {
            // 1. Intentamos leer el archivo si ya existe en la carpeta del programa
            if (archivo.exists()) {
                try (FileInputStream fis = new FileInputStream(archivo)) {
                    propiedades.load(fis);
                    nombreNegocio = propiedades.getProperty(CLAVE_NEGOCIO, "");
                }
            }

            // 2. ¡DETECTADO! Si está vacío, significa que es la PRIMERA VEZ que abren el programa
            if (nombreNegocio.isEmpty()) {
                
                // Lanza la pantalla/modal de bienvenida solicitando el registro único
                nombreNegocio = JOptionPane.showInputDialog(parent, 
                        "¡Bienvenido a Tlaxcalli!\n\nDetectamos que es la primera vez que inicia el sistema.\n" +
                        "Por favor, ingrese el nombre oficial de su negocio:", 
                        "Asistente de Configuración Inicial", 
                        JOptionPane.INFORMATION_MESSAGE);

                // Validación de cortesía por si el usuario cancela o lo deja en blanco
                if (nombreNegocio == null || nombreNegocio.trim().isEmpty()) {
                    nombreNegocio = "Tlaxcalli Sistema"; // Nombre genérico de respaldo
                } else {
                    nombreNegocio = nombreNegocio.trim();
                }

                // 3. GUARDADO PERMANENTE: Escribimos el dato en el disco duro
                propiedades.setProperty(CLAVE_NEGOCIO, nombreNegocio);
                try (FileOutputStream fos = new FileOutputStream(archivo)) {
                    propiedades.store(fos, "Configuracion de Identidad de Tlaxcalli");
                }
                
                JOptionPane.showMessageDialog(parent, "✅ Nombre del negocio registrado y configurado con éxito.");
            }

            // 4. Mandamos el nombre recuperado (o el recién creado) directamente al JLabel de su sala
            lblNegocio.setText(nombreNegocio.toUpperCase());

        } catch (Exception e) {
            System.err.println("Error al gestionar el nombre del negocio: " + e.getMessage());
            lblNegocio.setText("TLAXCALLI POS"); // Respaldo visual si algo falla catastróficamente
        }
    }

    public static void main(String[] args) {
        // Al llamar a la clase separada, aseguramos la BD portátil sin alterar SQL Server
        CSQLiteConnection conexionPortatil = new CSQLiteConnection();
        conexionPortatil.establecerConexionPortatil();
        Login log = new Login();
        log.setVisible(true);
        log.setLocationRelativeTo(null);
        
    }
}
