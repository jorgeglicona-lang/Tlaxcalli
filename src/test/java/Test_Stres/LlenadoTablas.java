/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Test_Stres;

import java.sql.Connection;
import java.sql.Statement;

/**
 *
 * @author Anyelo
 */
public class LlenadoTablas {
    public void inyectarRepartidoresDePrueba() {
    String sqlRepartidores = "INSERT INTO Empleados (Nombre, ApellidoP, ApellidoM, Puesto) VALUES "
                           + "('Carlos', 'Ramirez', 'Lopez', 'Repartidor'), "
                           + "('Luis', 'Fernandez', 'Gomez', 'Repartidor'), "
                           + "('Miguel', 'Angel', 'Torres', 'Repartidor')";
                           
    try (Connection con = conMngr.establecerConexionPortatil();
         Statement stmt = con.createStatement()) {
         
        int insertados = stmt.executeUpdate(sqlRepartidores);
        System.out.println("✅ Se agregaron " + insertados + " repartidores a la plantilla.");
        
    } catch (Exception e) {
        System.err.println("Error al inyectar repartidores: " + e.getMessage());
    }
}
    
}
