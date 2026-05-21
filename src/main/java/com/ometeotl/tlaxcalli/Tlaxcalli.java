
package com.ometeotl.tlaxcalli;

import com.ometeotl.tlaxcalli.IGU.Login;
import com.ometeotl.tlaxcalli.PERSISTENCIA.CSQLiteConnection;

public class Tlaxcalli {

    public static void main(String[] args) {
        // Al llamar a la clase separada, aseguramos la BD portátil sin alterar SQL Server
        CSQLiteConnection conexionPortatil = new CSQLiteConnection();
        conexionPortatil.establecerConexionPortatil();
        Login log = new Login();
        log.setVisible(true);
        log.setLocationRelativeTo(null);
        
    }
}
