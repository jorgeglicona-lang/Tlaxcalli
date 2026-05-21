package com.ometeotl.tlaxcalli.PERSISTENCIA.Interfaces;

import com.ometeotl.tlaxcalli.PERSISTENCIA.EmpleadosSQLServerDAO;
import com.ometeotl.tlaxcalli.PERSISTENCIA.EmpleadosSQLiteDAO;
import com.ometeotl.tlaxcalli.PERSISTENCIA.LoginSQLServerDAO;
import com.ometeotl.tlaxcalli.PERSISTENCIA.LoginSQLiteDAO;
import com.ometeotl.tlaxcalli.PERSISTENCIA.SQLiteInicioDAO;

public class DAOFactory {
    
    public static final boolean MODO_PORTATIL = true; 

    public static ILoginDAO getLoginDAO() {
        if (MODO_PORTATIL) {
            return new LoginSQLiteDAO();
        } else {
            return new LoginSQLServerDAO();
        }
    }

    // Nuevo interruptor para catálogo de productos
    public static SQLiteInicioDAO getInicioDAO() {
        if (MODO_PORTATIL) {
            return new SQLiteInicioDAO();
        }
        // Aquí retornaremos el de SQL Server cuando se mapee en la otra rama
        return null; 
    }
    
    // Nuevo interruptor para la gestión de Empleados
    public static IEmpleadosDAO getEmpleadosDAO() {
        if (MODO_PORTATIL) {
            return new EmpleadosSQLiteDAO();
        }
        return new EmpleadosSQLServerDAO(); //<-- Cuando renombre el viejo
    }
}