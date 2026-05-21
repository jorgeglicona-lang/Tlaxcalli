package com.ometeotl.tlaxcalli.LOGICA;

import com.ometeotl.tlaxcalli.IGU.Inicio;
import com.ometeotl.tlaxcalli.PERSISTENCIA.Interfaces.ILoginDAO;
import com.ometeotl.tlaxcalli.PERSISTENCIA.Interfaces.DAOFactory;

public class C_Login {
    private String Usuario;
    private String password;
  
    public void logeo(String User, String Passw) {
        this.Usuario = User;
        this.password = Passw;
    }
    
    public boolean ValUsuario() {
        // En lugar de escribir el Query aquí, llamamos al contrato a través de la fábrica
        ILoginDAO loginDAO = DAOFactory.getLoginDAO();
        
        // Le pedimos al DAO seleccionado que valide las credenciales
        boolean accesoCorrecto = loginDAO.validarAcceso(Usuario, password);
        
        if (accesoCorrecto) {
            String puestoEncontrado = C_Sesion_login.puesto;
            
            if (puestoEncontrado.equalsIgnoreCase("Gerente") || 
                puestoEncontrado.equalsIgnoreCase("Administrador")) {
                
                // Abrir pantalla de inicio
                Inicio ini = new Inicio();
                ini.setVisible(true);
                ini.setLocationRelativeTo(null);
                ini.settext(C_Sesion_login.nombre);
                
                return true; 
            } else {
                javax.swing.JOptionPane.showMessageDialog(null, "⛔ Acceso Denegado.\nTu puesto (" + puestoEncontrado + ") no tiene permisos de Administrador.");
                return false;
            }
        } else {
            javax.swing.JOptionPane.showMessageDialog(null, "❌ Credenciales incorrectas o usuario Inactivo.");
            return false;
        }
    }
}