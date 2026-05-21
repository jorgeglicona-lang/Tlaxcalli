package com.ometeotl.tlaxcalli.PERSISTENCIA;

import java.io.File;
import java.security.MessageDigest;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.SQLException;

public class CSQLiteConnection {
    
    private final String DB_NAME = "tlaxcalli.db";
    
    public Connection establecerConexionPortatil() {
        Connection con = null;
        try {
            String url = "jdbc:sqlite:" + DB_NAME;
            File archivoDb = new File(DB_NAME);
            
            boolean esNueva = !archivoDb.exists();
            
            // SQLite necesita que las llaves foráneas se activen por conexión
            con = DriverManager.getConnection(url);
            try (Statement pragma = con.createStatement()) {
                pragma.execute("PRAGMA foreign_keys = ON;");
            }
            
            if (esNueva) {
                System.out.println("Ometeotl Portátil: Traducción estricta de tablas.sql iniciada...");
                inicializarEstructuraSQLite(con);
            }
            
        } catch (SQLException e) {
            System.err.println("Error crítico en conexión portátil SQLite: " + e.getMessage());
        }
        return con;
    }

    private String generarSHA512(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-512");
            byte[] hash = md.digest(password.getBytes("UTF-8"));
            StringBuilder hexString = new StringBuilder();
            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }
            return hexString.toString().toUpperCase();
        } catch (Exception ex) { 
            return password; 
        }
    }

    private void inicializarEstructuraSQLite(Connection con) {
        try (Statement stmt = con.createStatement()) {
            
            // 1. Cat_Gastos
            stmt.executeUpdate("CREATE TABLE IF NOT EXISTS Cat_Gastos ("
                    + "Id_tipo INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + "Nombre TEXT NOT NULL,"
                    + "Requiere_Descripcion INTEGER DEFAULT 0)");
            
            // 2. Empleados
            stmt.executeUpdate("CREATE TABLE IF NOT EXISTS Empleados ("
                    + "Id_empleado INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + "Nombre TEXT,"
                    + "ApellidoP TEXT,"
                    + "ApellidoM TEXT,"
                    + "Puesto TEXT,"
                    + "Estatus TEXT DEFAULT 'Activo')");

            // 3. Gastos
            stmt.executeUpdate("CREATE TABLE IF NOT EXISTS Gastos ("
                    + "Id_gasto INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + "Id_empleado INTEGER NOT NULL,"
                    + "Descripcion TEXT NOT NULL,"
                    + "Monto REAL NOT NULL,"
                    + "Fecha TEXT DEFAULT (date('now')),"
                    + "FOREIGN KEY(Id_empleado) REFERENCES Empleados(Id_empleado))");

            // 4. Gastos_Administrativos
            stmt.executeUpdate("CREATE TABLE IF NOT EXISTS Gastos_Administrativos ("
                    + "Id_gasto_adm INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + "Descripcion TEXT NOT NULL,"
                    + "Monto REAL NOT NULL,"
                    + "Fecha TEXT NOT NULL DEFAULT (date('now')))");

            // 5. Logeo (Note los UNIQUE constraints traducidos de su SQL)
            stmt.executeUpdate("CREATE TABLE IF NOT EXISTS Logeo ("
                    + "Id_login INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + "Id_empleado INTEGER NOT NULL UNIQUE,"
                    + "Nombre TEXT NOT NULL UNIQUE,"
                    + "Contrasena TEXT NOT NULL,"
                    + "FOREIGN KEY(Id_empleado) REFERENCES Empleados(Id_empleado))");

            // 6. Produccion_Diaria
            stmt.executeUpdate("CREATE TABLE IF NOT EXISTS Produccion_Diaria ("
                    + "Id_produccion INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + "Fecha TEXT DEFAULT (date('now')),"
                    + "Id_empleado INTEGER NOT NULL,"
                    + "Botes_Maiz REAL NOT NULL,"
                    + "Harina_Kg REAL NOT NULL,"
                    + "Desperdicio_Kg REAL NOT NULL,"
                    + "Masa_Natural_Kg REAL NOT NULL,"
                    + "Masa_Total_Kg REAL NOT NULL,"
                    + "Venta_Masa_Kg REAL DEFAULT 0,"
                    + "Masa_Para_Tortilla_Kg REAL NOT NULL,"
                    + "Tortilla_Elaborada_Kg REAL NOT NULL,"
                    + "FOREIGN KEY(Id_empleado) REFERENCES Empleados(Id_empleado))");

            // 7. Productividad (Sin PK en el script original)
            stmt.executeUpdate("CREATE TABLE IF NOT EXISTS Productividad ("
                    + "Fecha TEXT NOT NULL,"
                    + "Botes REAL NOT NULL,"
                    + "Harina REAL NOT NULL,"
                    + "Desperdicio REAL NOT NULL,"
                    + "TotalTortilla REAL NOT NULL)");

            // 8. Productos
            stmt.executeUpdate("CREATE TABLE IF NOT EXISTS Productos ("
                    + "Id_producto INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + "Nom_producto TEXT,"
                    + "Precio REAL,"
                    + "Es_Comodin INTEGER DEFAULT 0)");

            // 9. Usuarios
            stmt.executeUpdate("CREATE TABLE IF NOT EXISTS Usuarios ("
                    + "Id_usuario INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + "Id_empleado INTEGER,"
                    + "Contrasena TEXT NOT NULL,"
                    + "FOREIGN KEY(Id_empleado) REFERENCES Empleados(Id_empleado))");

            // 10. Ventas_Diarias
            stmt.executeUpdate("CREATE TABLE IF NOT EXISTS Ventas_Diarias ("
                    + "Id_venta INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + "Id_empleado INTEGER NOT NULL,"
                    + "Id_producto INTEGER NOT NULL,"
                    + "Fecha TEXT NOT NULL,"
                    + "Cantidad REAL NOT NULL,"
                    + "Total_Dinero REAL NOT NULL,"
                    + "Observaciones TEXT,"
                    + "FOREIGN KEY(Id_empleado) REFERENCES Empleados(Id_empleado),"
                    + "FOREIGN KEY(Id_producto) REFERENCES Productos(Id_producto))");

            // ==========================================
            // INYECCIÓN DE USUARIOS SEMILLA REQUERIDOS
            // ==========================================
            String contraseniaCifrada = generarSHA512("Admin123");
            
            // A. Insertar al Super Usuario Master en Empleados (Id_empleado = 1)
            stmt.executeUpdate("INSERT INTO Empleados (Nombre, ApellidoP, ApellidoM, Puesto) "
                    + "VALUES ('Super', 'Usuario', 'Master', 'Administrador')"); // Estatus toma el DEFAULT 'Activo'
            
            // B. Crear su login amarrado al Id_empleado 1 (Admin - Admin123)
            stmt.executeUpdate("INSERT INTO Logeo (Id_empleado, Nombre, Contrasena) "
                    + "VALUES (1, 'Admin', '" + contraseniaCifrada + "')");
            
            // C. Insertar al trabajador "Mostrador" vacío en apellidos con puesto Mostrador (Id_empleado = 2)
            stmt.executeUpdate("INSERT INTO Empleados (Nombre, ApellidoP, ApellidoM, Puesto) "
                    + "VALUES ('Mostrador', '', '', 'Mostrador')");
            
            System.out.println("¡Base de datos portátil creada de forma idéntica a SQL Server!");
            
        } catch (SQLException e) {
            System.err.println("Error al construir la estructura inicial en SQLite: " + e.getMessage());
        }
    }
}