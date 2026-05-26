package com.ometeotl.tlaxcalli.PERSISTENCIA.Interfaces;

import com.ometeotl.tlaxcalli.PERSISTENCIA.EmpleadosSQLiteDAO;
import com.ometeotl.tlaxcalli.PERSISTENCIA.GastosGeneralesSQLiteDAO;
import com.ometeotl.tlaxcalli.PERSISTENCIA.LoginSQLiteDAO;
import com.ometeotl.tlaxcalli.PERSISTENCIA.SQLiteInicioDAO;
import com.ometeotl.tlaxcalli.PERSISTENCIA.MolinoSQLiteDAO;
import com.ometeotl.tlaxcalli.PERSISTENCIA.ReportesSQLiteDAO;
import com.ometeotl.tlaxcalli.PERSISTENCIA.VentasSQLiteDAO;

public class DAOFactory {
    
    public static final boolean MODO_PORTATIL = true; 

    public static ILoginDAO getLoginDAO() {
        if (MODO_PORTATIL) {
            return new LoginSQLiteDAO();
        } else {
            return null;
        }
    }

    // Nuevo interruptor para catálogo de productos
    public static I_InicioDAO getInicioDAO() {
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
        return null;
    }
    
    public static IMolinoDAO getMolinoDAO() {
        if (MODO_PORTATIL) {
            return new MolinoSQLiteDAO();
        }
        return null; //<-- Cuando renombre el viejo
    }
    
    public static IVentasDAO getVentasDAO(){
        if (MODO_PORTATIL){
            return new VentasSQLiteDAO();
        }
        return null;
    }
    
    public static IGastosGeneralesDAO getGastosGeneralesDAO() {
        if (MODO_PORTATIL) {
            return new GastosGeneralesSQLiteDAO();
        }
        return null;
    }
    
    public static IReportesDAO getReportesDAO() {
        if (MODO_PORTATIL) {
            return new ReportesSQLiteDAO();
        }
        return null;
    }
}