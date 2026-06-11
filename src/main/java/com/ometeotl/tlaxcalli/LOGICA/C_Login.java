package com.ometeotl.tlaxcalli.LOGICA;

import static com.ometeotl.tlaxcalli.HerramientasVisuales.GenV;
import com.ometeotl.tlaxcalli.IGU.Inicio;
import com.ometeotl.tlaxcalli.PERSISTENCIA.Interfaces.ILoginDAO;
import com.ometeotl.tlaxcalli.PERSISTENCIA.Interfaces.DAOFactory;
import javax.swing.JFrame;
import static javax.swing.JOptionPane.showMessageDialog;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class C_Login {
    
    public void ValUsuario(JFrame parent, JTextField User, JPasswordField Passw) {
        // En lugar de escribir el Query aquí, llamamos al contrato a través de la fábrica
        String valorPass = new String (Passw.getPassword());
        String Usuario=User.getText();
        String password=valorPass;
        
        
        if(password.equals("**********") || Usuario.equals("Ingrese su usuario")) {
            showMessageDialog(null, "❌ Credenciales incorrectas o usuario Inactivo.");
            return;
        }
        
        ILoginDAO Val = DAOFactory.getLoginDAO();
        boolean accesoCorrecto = Val.validarAcceso(Usuario, password);
        
        if (!accesoCorrecto) {
            showMessageDialog(null, "❌ Credenciales incorrectas o usuario Inactivo.");
            return;
        }
        
        String puestoEncontrado = C_Sesion_login.puesto;
            
        if (!puestoEncontrado.equalsIgnoreCase("Gerente") && 
                !puestoEncontrado.equalsIgnoreCase("Administrador")) {
            showMessageDialog(null, "⛔ Acceso Denegado.\nTu puesto (" 
                                + puestoEncontrado + ") no tiene permisos de Administrador.");
            return;
        }
        
        Inicio ini = new Inicio();
        GenV(ini);
        parent.dispose();
    }
}