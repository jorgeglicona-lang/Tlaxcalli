package com.ometeotl.tlaxcalli.PERSISTENCIA.Interfaces;

import com.ometeotl.tlaxcalli.LOGICA.C_Login;

public interface ILoginDAO {
    /**
     * El contrato exige que cualquier motor de base de datos implemente este método.
     * @param usuario El nombre de usuario ingresado en la IGU.
     * @param contrasenia La contraseña en texto plano (el DAO se encargará de encriptarla si es necesario).
     * @return true si las credenciales son correctas y el empleado está activo, false en caso contrario.
     */
    boolean validarAcceso(String usuario, String contrasenia);
}