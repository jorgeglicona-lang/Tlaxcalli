package com.ometeotl.tlaxcalli.PERSISTENCIA.Interfaces;

import java.sql.Connection;

public interface IMolinoDAO {
    boolean guardarReporteDiario(int idEmp, double botes, double harina, double desp,
                                double mNatural, double mTotal, double vMasa,
                                double mTortilla, double tElaborada);
    double obtenerTotalTortillaHoy(); 
    double obtenerTotalTortillaHoy(Connection con);
    double[] consultarReporteHoy();
    boolean actualizarReporteDiario(double botes, double harina, double desp,
                                   double mNatural, double mTotal, double vMasa,
                                   double mTortilla, double tElaborada);
}