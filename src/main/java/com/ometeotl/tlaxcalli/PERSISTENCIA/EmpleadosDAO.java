package com.ometeotl.tlaxcalli.PERSISTENCIA;

import java.sql.Connection;
import java.sql.PreparedStatement; // <--- Faltaba este
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.table.DefaultTableModel;

public class EmpleadosDAO {

    // Método que devuelve la tabla ya lista para mostrarse
    public DefaultTableModel consultarEmpleados(){
        String titulos [] = {"id","nombre"};
        javax.swing.table.DefaultTableModel modelo = new javax.swing.table.DefaultTableModel(null,titulos);
        Connection con = null;
        Statement st = null;
        ResultSet rs = null;

        try {
            con = new Cconection().establecerConexion();
            st = con.createStatement();
            
            // FILTRAMOS PARA VER SOLO LOS ACTIVOS
            String sql = "SELECT Id_empleado, Nombre " +
                         "FROM Empleados " +
                         "WHERE Estatus = 'Activo' " +
                         "AND NOT (Nombre = 'Super' AND ApellidoP = 'User')";
            rs = st.executeQuery(sql);

            while (rs.next()) {
                Object[] fila = new Object[2];
                fila[0] = rs.getInt("Id_empleado");
                fila[1] = rs.getString("Nombre");
                
                modelo.addRow(fila);
            }

        } catch (Exception e) {
            System.err.println("Error al listar empleados: " + e.getMessage());
        } finally {
            try {
                if (rs != null) rs.close();
                if (st != null) st.close();
                if (con != null) con.close();
            } catch (Exception ex) { }
        }

        return modelo;
    }
    
    // MÉTODO PARA AGREGAR UN NUEVO EMPLEADO
    // MÉTODO NUEVO: REGISTRA EMPLEADO + USUARIO + CONTRASEÑA
    public boolean registrarEmpleado(String nombre, String app, String apm, String puesto, String usuario, String password) {
        Connection con = null;
        PreparedStatement psEmp = null;
        PreparedStatement psLog = null;
        ResultSet rs = null;
        
        try {
            con = new Cconection().establecerConexion();
            con.setAutoCommit(false); // MODO TRANSACCIÓN: Todo o nada

            // 1. INSERTAR EMPLEADO Y PEDIR EL ID GENERADO
            String sqlEmp = "INSERT INTO Empleados (Nombre, ApellidoP, ApellidoM, Puesto) VALUES (?, ?, ?, ?)";
            
            // RETURN_GENERATED_KEYS es para recuperar el ID autonumérico
            psEmp = con.prepareStatement(sqlEmp, java.sql.Statement.RETURN_GENERATED_KEYS);
            psEmp.setString(1, nombre);
            psEmp.setString(2, app);
            psEmp.setString(3, apm);
            psEmp.setString(4, puesto);
            
            int filas = psEmp.executeUpdate();
            
            if (filas == 0) {
                throw new Exception("No se pudo guardar el empleado.");
            }

            // 2. RECUPERAR EL ID DEL NUEVO EMPLEADO
            rs = psEmp.getGeneratedKeys();
            int idNuevoEmpleado = 0;
            if (rs.next()) {
                idNuevoEmpleado = rs.getInt(1); // Aquí tenemos el ID nuevo (ej: 50)
            } else {
                throw new Exception("No se obtuvo el ID del empleado.");
            }

            // 3. CREAR EL LOGIN (SI SE ESCRIBIÓ USUARIO Y CONTRASEÑA)
            if (!usuario.isEmpty() && !password.isEmpty()) {
                // Usamos la misma encriptación BLINDADA que en el Login (CAST AS NVARCHAR)
                String sqlLog = "INSERT INTO Logeo (Id_empleado, Nombre, Contrasena) " +
                                "VALUES (?, ?, HASHBYTES('SHA2_512', CAST(? AS NVARCHAR(4000))))";
                
                psLog = con.prepareStatement(sqlLog);
                psLog.setInt(1, idNuevoEmpleado);
                psLog.setString(2, usuario);
                psLog.setString(3, password);
                psLog.executeUpdate();
            }

            // 4. CONFIRMAR CAMBIOS
            con.commit();
            return true;

        } catch (Exception e) {
            System.err.println("Error registrando empleado completo: " + e.getMessage());
            try { if (con != null) con.rollback(); } catch (Exception ex) {} // Deshacer si falla
            return false;
        } finally {
            try { 
                if (rs != null) rs.close();
                if (psEmp != null) psEmp.close();
                if (psLog != null) psLog.close();
                if (con != null) con.close(); 
            } catch (Exception ex) {}
        }
    }
    
    // MÉTODO PARA ELIMINAR EMPLEADO (Y SU LOGIN SI TIENE)
    // AHORA ES UN "BORRADO LÓGICO"
    public boolean eliminarEmpleado(int idEmpleado) {
        Connection con = null;
        PreparedStatement ps = null;
        
        try {
            con = new Cconection().establecerConexion();
            // En vez de DELETE, solo cambiamos su estatus
            String sql = "UPDATE Empleados SET Estatus = 'Inactivo' WHERE Id_empleado = ?";
            
            ps = con.prepareStatement(sql);
            ps.setInt(1, idEmpleado);
            
            int filas = ps.executeUpdate();
            return filas > 0;

        } catch (Exception e) {
            System.err.println("Error desactivando empleado: " + e.getMessage());
            return false;
        } finally {
            try { if (con != null) con.close(); } catch (Exception ex) {}
        }
    }
    
    // MÉTODO PARA MODIFICAR EMPLEADO (Gestionando sus credenciales)
    public boolean modificarEmpleado(int idEmpleado, String nombre, String app, String apm, String puesto, String usuario, String password) {
        Connection con = null;
        PreparedStatement psEmp = null;
        PreparedStatement psLog = null;
        PreparedStatement psCheck = null;
        ResultSet rs = null;
        
        try {
            con = new Cconection().establecerConexion();
            con.setAutoCommit(false); // INICIAMOS TRANSACCIÓN

            // 1. ACTUALIZAR DATOS PERSONALES (Siempre se hace)
            String sqlEmp = "UPDATE Empleados SET Nombre=?, ApellidoP=?, ApellidoM=?, Puesto=? WHERE Id_empleado=?";
            psEmp = con.prepareStatement(sqlEmp);
            psEmp.setString(1, nombre);
            psEmp.setString(2, app);
            psEmp.setString(3, apm);
            psEmp.setString(4, puesto);
            psEmp.setInt(5, idEmpleado);
            psEmp.executeUpdate();

            // 2. LÓGICA DE CREDENCIALES (El cerebro de la operación)
            
            if (!puesto.equalsIgnoreCase("Administrador")) {
                // CASO A: NO ES ADMINISTRADOR -> BORRAR LOGIN SI EXISTE (Bloquear acceso)
                String sqlDelete = "DELETE FROM Logeo WHERE Id_empleado = ?";
                psLog = con.prepareStatement(sqlDelete);
                psLog.setInt(1, idEmpleado);
                psLog.executeUpdate();
                
            } else {
                // CASO B: ES ADMINISTRADOR -> ASEGURAR LOGIN
                
                // Primero revisamos si YA tiene login
                String sqlCheck = "SELECT Id_login FROM Logeo WHERE Id_empleado = ?";
                psCheck = con.prepareStatement(sqlCheck);
                psCheck.setInt(1, idEmpleado);
                rs = psCheck.executeQuery();
                
                boolean tieneLogin = rs.next();
                
                if (tieneLogin) {
                    // Si ya tiene, ACTUALIZAMOS usuario y contraseña
                    // (Solo si escribió algo en contraseña, si la dejó vacía, mantenemos la vieja)
                    if (!password.isEmpty()) {
                        String sqlUpdate = "UPDATE Logeo SET Nombre=?, Contrasena=HASHBYTES('SHA2_512', CAST(? AS NVARCHAR(4000))) WHERE Id_empleado=?";
                        psLog = con.prepareStatement(sqlUpdate);
                        psLog.setString(1, usuario);
                        psLog.setString(2, password);
                        psLog.setInt(3, idEmpleado);
                        psLog.executeUpdate();
                    } else {
                        // Si no puso contraseña, solo actualizamos el nombre de usuario
                        String sqlUpdateUser = "UPDATE Logeo SET Nombre=? WHERE Id_empleado=?";
                        psLog = con.prepareStatement(sqlUpdateUser);
                        psLog.setString(1, usuario);
                        psLog.setInt(2, idEmpleado);
                        psLog.executeUpdate();
                    }
                } else {
                    // Si NO tiene (era Repartidor y lo ascendieron), INSERTAMOS nuevo login
                    String sqlInsert = "INSERT INTO Logeo (Id_empleado, Nombre, Contrasena) VALUES (?, ?, HASHBYTES('SHA2_512', CAST(? AS NVARCHAR(4000))))";
                    psLog = con.prepareStatement(sqlInsert);
                    psLog.setInt(1, idEmpleado);
                    psLog.setString(2, usuario);
                    psLog.setString(3, password);
                    psLog.executeUpdate();
                }
            }

            con.commit(); // CONFIRMAR TODO
            return true;

        } catch (Exception e) {
            System.err.println("Error modificando: " + e.getMessage());
            try { if (con != null) con.rollback(); } catch (Exception ex) {}
            return false;
        } finally {
            try { 
                if (rs != null) rs.close();
                if (psEmp != null) psEmp.close();
                if (psLog != null) psLog.close();
                if (psCheck != null) psCheck.close();
                if (con != null) con.close(); 
            } catch (Exception ex) {}
        }
    }
    
    // MÉTODO AUXILIAR: OBTENER EL USUARIO ACTUAL (Para mostrarlo en la ventana)
    public String obtenerNombreUsuario(int idEmpleado) {
        String usuario = "";
        try {
            Connection con = new Cconection().establecerConexion();
            String sql = "SELECT Nombre FROM Logeo WHERE Id_empleado = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, idEmpleado);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                usuario = rs.getString("Nombre");
            }
            con.close();
        } catch (Exception e) {}
        return usuario;
    }

    // Método exclusivo para la TABLA DE GESTIÓN (Oculta Mostrador y Admin)
    public javax.swing.table.DefaultTableModel consultarEmpleadosVisibles() {
        String[] titulos = {"ID", "Nombre", "Ap. Paterno", "Ap. Materno", "Puesto"};
        javax.swing.table.DefaultTableModel modelo = new javax.swing.table.DefaultTableModel(null, titulos);
        
        Connection con = null;
        try {
            con = new Cconection().establecerConexion();
            // AQUÍ ESTÁ EL TRUCO: Ocultamos Mostrador y Admin
            String sql = "SELECT Id_empleado, Nombre, ApellidoP, ApellidoM, Puesto " +
                    "FROM Empleados WHERE Estatus = 'Activo' " +
                    "AND Nombre NOT IN ('Mostrador', 'Super')" +
                    " AND NOT (ApellidoP = 'Super')";
            
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            
            while (rs.next()) {
                Object[] fila = new Object[6];
                fila[0] = rs.getInt("Id_empleado");
                fila[1] = rs.getString("Nombre");
                fila[2] = rs.getString("ApellidoP");
                fila[3] = rs.getString("ApellidoM");
                fila[4] = rs.getString("Puesto");
                modelo.addRow(fila);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try { if (con != null) con.close(); } catch (SQLException ex) { }
        }
        return modelo;
    }
}