package com.ometeotl.tlaxcalli.PERSISTENCIA;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import java.sql.*;

public class SQLiteInicioDAOTest {

    @Test
    public void testInicializarEstructuraSQLite() throws SQLException {
        // 2. Creamos una instancia de su clase DAO
        try ( // 1. Abrimos conexión en memoria (se crea y destruye en segundos)
                Connection con = DriverManager.getConnection("jdbc:sqlite::memory:")) {
            // 2. Creamos una instancia de su clase DAO
            CSQLiteConnection dao = new CSQLiteConnection();
            // 3. Ejecutamos el método que queremos probar
            dao.inicializarEstructuraSQLite(con);
            // 4. VERIFICACIÓN (Asserts)
            // Probamos que la tabla Empleados realmente se creó
            DatabaseMetaData meta = con.getMetaData();
            try (ResultSet rs = meta.getTables(null, null, "Empleados", null)) {
                assertTrue(rs.next(), "La tabla Empleados debería existir");
            }
            // Probamos que los datos semilla (Productos) se insertaron
            try (Statement stmt = con.createStatement();
                    ResultSet rs = stmt.executeQuery("SELECT count(*) FROM Productos")) {
                assertTrue(rs.next());
                assertEquals(3, rs.getInt(1), "Debería haber 3 productos semilla");
            }
        }
    }
}