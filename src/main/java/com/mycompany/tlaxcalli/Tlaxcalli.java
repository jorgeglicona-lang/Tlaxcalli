
package com.mycompany.tlaxcalli;

import com.mycompany.tlaxcalli.Igu.Login;
import com.mycompany.tlaxcalli.Persistencia.Cconection;

public class Tlaxcalli {

    public static void main(String[] args) {
        Cconection conexion=new Cconection();
        conexion.establecerConexion();
        Login log = new Login();
        log.setVisible(true);
        log.setLocationRelativeTo(null);
        
    }
}
