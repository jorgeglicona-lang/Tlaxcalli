package com.ometeotl.tlaxcalli.PERSISTENCIA;

import static java.io.File.separator;
import static java.lang.System.Logger.Level.ERROR;
import java.security.MessageDigest;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.SQLException;
import static javax.swing.JOptionPane.ERROR_MESSAGE;
import static javax.swing.JOptionPane.INFORMATION_MESSAGE;
import static javax.swing.JOptionPane.showMessageDialog;

public class CSQLiteConnection {
    
    private final String DB_NAME = "tlaxcalli.db";
    
    public Connection establecerConexionPortatil() {
        Connection con = null;
        try {
            Class.forName("org.sqlite.JDBC");
            String url = "jdbc:sqlite:" + System.getProperty("user.dir") + separator + DB_NAME;
            con = DriverManager.getConnection(url);
            try (Statement pragma = con.createStatement()) {
                pragma.execute("PRAGMA foreign_keys = ON;");
            }
        } catch (SQLException e) {
            showMessageDialog(null, "❌ Error crítico al conectar la BD:\n" + e.getMessage(), 
                "Error de Sistema", ERROR_MESSAGE);
        } catch (ClassNotFoundException ex) {
            System.getLogger(CSQLiteConnection.class.getName()).log(ERROR, (String) null, ex);
        }
        return con;
    }
    
    public Connection CrearBDPortatil() {
        Connection con = null;
        try {
            // 1. Obligamos a Java a decirnos la ruta exacta donde estamos parados
            String rutaBase = System.getProperty("user.dir");
            String rutaCompletaDb = rutaBase + separator + DB_NAME;
            
            // 2. Usamos la ruta absoluta y exacta para evitar que Windows esconda el archivo
            String url = "jdbc:sqlite:" + rutaCompletaDb;
            
            // 3. Obligamos a Java a cargar el traductor de SQLite en memoria
            try {
                Class.forName("org.sqlite.JDBC");
            } catch (ClassNotFoundException ex) {
                showMessageDialog(null, "❌ ¡Falta la librería sqlite-jdbc en el proyecto!\n"
                        + "Maven no empacó el driver.", "Error de Librería", ERROR_MESSAGE);
                return null;
            }
            
            con = DriverManager.getConnection(url);
            try (Statement pragma = con.createStatement()) {
                pragma.execute("PRAGMA foreign_keys = ON;");
            }
            
            
            System.out.println("Creando nueva estructura...");
            inicializarEstructuraSQLite(con);

            // Nos avisa exactamente dónde dejó el archivo
            showMessageDialog(null, "✅ Base de datos inicializada correctamente en:\n" + rutaCompletaDb, 
                "Ometeotl Portátil",INFORMATION_MESSAGE);

            
        } catch (SQLException e) {
            // Si algo falla, lo vemos en pantalla sí o sí
            showMessageDialog(null, "❌ Error crítico al conectar la BD:\n" + e.getMessage(), 
                "Error de Sistema", ERROR_MESSAGE);
        }
        return con;
    }

    public static String generarSHA512(String password) {
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

    public void inicializarEstructuraSQLite(Connection con) {
        try (Statement stmt = con.createStatement()) {
            int[] idTabla = new int[8];
            String[] Tabla = {"Cat_Gastos", "Empleados", "Gastos","Gastos_Administrativos","Logeo",
                              "Produccion_Diaria","Productos","Ventas_Diarias"};

            // 1. Cat_Gastos
            idTabla[0]=stmt.executeUpdate("CREATE TABLE IF NOT EXISTS Cat_Gastos ("
                    + "Id_tipo INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + "Nombre TEXT NOT NULL,"
                    + "Requiere_Descripcion INTEGER DEFAULT 0)");
            
            // 2. Empleados
            idTabla[1]=stmt.executeUpdate("CREATE TABLE IF NOT EXISTS Empleados ("
                    + "Id_empleado INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + "Nombre TEXT,"
                    + "ApellidoP TEXT,"
                    + "ApellidoM TEXT,"
                    + "Puesto TEXT,"
                    + "Estatus TEXT DEFAULT 'Activo')");

            // 3. Gastos
            idTabla[2]=stmt.executeUpdate("CREATE TABLE IF NOT EXISTS Gastos ("
                    + "Id_gasto INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + "Id_empleado INTEGER NOT NULL,"
                    + "Descripcion TEXT NOT NULL,"
                    + "Monto REAL NOT NULL,"
                    + "Fecha TEXT DEFAULT (date('now')),"
                    + "FOREIGN KEY(Id_empleado) REFERENCES Empleados(Id_empleado))");

            // 4. Gastos_Administrativos
            idTabla[3]=stmt.executeUpdate("CREATE TABLE IF NOT EXISTS Gastos_Administrativos ("
                    + "Id_gasto_adm INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + "Descripcion TEXT NOT NULL,"
                    + "Monto REAL NOT NULL,"
                    + "Fecha TEXT NOT NULL DEFAULT (date('now')))");

            // 5. Logeo (Note los UNIQUE constraints traducidos de su SQL)
            idTabla[4]=stmt.executeUpdate("CREATE TABLE IF NOT EXISTS Logeo ("
                    + "Id_login INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + "Id_empleado INTEGER NOT NULL UNIQUE,"
                    + "Nombre TEXT NOT NULL UNIQUE,"
                    + "Contrasena TEXT NOT NULL,"
                    + "FOREIGN KEY(Id_empleado) REFERENCES Empleados(Id_empleado))");

            // 6. Produccion_Diaria
            idTabla[5]=stmt.executeUpdate("CREATE TABLE IF NOT EXISTS Produccion_Diaria ("
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

            // 8. Productos
            idTabla[6]=stmt.executeUpdate("CREATE TABLE IF NOT EXISTS Productos ("
                    + "Id_producto INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + "Nom_producto TEXT,"
                    + "Precio REAL,"
                    + "Es_Comodin INTEGER DEFAULT 0)");

            // 10. Ventas_Diarias
            idTabla[7]=stmt.executeUpdate("CREATE TABLE IF NOT EXISTS Ventas_Diarias ("
                    + "Id_venta INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + "Id_empleado INTEGER NOT NULL,"
                    + "Id_producto INTEGER NOT NULL,"
                    + "Fecha TEXT NOT NULL,"
                    + "Cantidad REAL NOT NULL,"
                    + "Total_Dinero REAL NOT NULL,"
                    + "Observaciones TEXT,"
                    + "FOREIGN KEY(Id_empleado) REFERENCES Empleados(Id_empleado),"
                    + "FOREIGN KEY(Id_producto) REFERENCES Productos(Id_producto))");
            
            for(int i=0;i<8;i++){
                if(idTabla[i]==0) System.out.println("¡Tabla de "+Tabla[i]+" creada de forma exitosa!");
            }

            // ==========================================
            // INYECCIÓN DE USUARIOS SEMILLA REQUERIDOS
            // ==========================================
            int stmtchk;
            String contraseniaCifrada = generarSHA512("Root");
            
            // A. Insertar al Super Usuario Master en Empleados (Id_empleado = 1)
            stmt.executeUpdate("INSERT INTO Empleados (Nombre, ApellidoP, ApellidoM, Puesto) "
                    + "VALUES ('Super', 'Usuario', 'Master', 'Administrador')"); // Estatus toma el DEFAULT 'Activo'
            
            // B. Crear su login amarrado al Id_empleado 1 (Admin - Admin123)
            stmtchk=stmt.executeUpdate("INSERT INTO Logeo (Id_empleado, Nombre, Contrasena) "
                    + "VALUES (1, 'Root', '" + contraseniaCifrada + "')");
            
            if(stmtchk==1) System.out.println("¡Super usuario creado de forma exitosa!\n");
            
            contraseniaCifrada = generarSHA512("Admin123");
            
            // A. Insertar al Super Usuario Master en Empleados (Id_empleado = 1)
            stmt.executeUpdate("INSERT INTO Empleados (Nombre, ApellidoP, ApellidoM, Puesto) "
                    + "VALUES ('Aministrador', 'Del', 'Negocio', 'Administrador')");
            
            stmtchk=stmt.executeUpdate("INSERT INTO Logeo (Id_empleado, Nombre, Contrasena) "
                    + "VALUES (2, 'Admin', '" + contraseniaCifrada + "')");
            
            if(stmtchk==1) System.out.println("¡Usuario Admin creado de forma exitosa!\n");
            
            // C. Insertar al trabajador "Mostrador" vacío en apellidos con puesto Mostrador (Id_empleado = 2)
            stmtchk=stmt.executeUpdate("INSERT INTO Empleados (Nombre, ApellidoP, ApellidoM, Puesto) "
                    + "VALUES ('Mostrador', '', '', 'Mostrador')");
            
            if(stmtchk==1) System.out.println("¡Elememto Mostrador creado de forma exitosa!\n");
            
            stmtchk=stmt.executeUpdate("INSERT INTO Productos (Id_producto, Nom_producto, Precio, Es_Comodin) VALUES (1, 'Tortilla de Reparto', 19.0, 0)");
            if(stmtchk==1) System.out.println("¡Producto 1 creado de forma exitosa!\n");
            
            stmtchk=stmt.executeUpdate("INSERT INTO Productos (Id_producto, Nom_producto, Precio, Es_Comodin) VALUES (2, 'Tortilla Mostrador', 22.0, 0)");
            if(stmtchk==1) System.out.println("¡Producto 2 creado de forma exitosa!\n");
            
            stmtchk=stmt.executeUpdate("INSERT INTO Productos (Id_producto, Nom_producto, Precio, Es_Comodin) VALUES (3, 'Masa', 20.0, 0)");
            if(stmtchk==1) System.out.println("¡Producto 3 creado de forma exitosa!\n");
            
            System.out.println("¡Base de datos portátil creada de forma exitosa!");
            
        } catch (SQLException e) {
            System.err.println("Error al construir la estructura inicial en SQLite: " + e.getMessage());
        }
    }
}
