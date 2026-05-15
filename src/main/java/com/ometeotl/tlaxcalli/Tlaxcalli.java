
package com.ometeotl.tlaxcalli;

import com.ometeotl.tlaxcalli.IGU.Login;
import com.ometeotl.tlaxcalli.PERSISTENCIA.Cconection;

public class Tlaxcalli {

    public static void main(String[] args) {
        Cconection conexion=new Cconection();
        conexion.establecerConexion();
        Login log = new Login();
        log.setVisible(true);
        log.setLocationRelativeTo(null);
        
    }
}
