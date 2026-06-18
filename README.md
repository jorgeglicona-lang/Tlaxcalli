******Tlaxcalli: Sistema de Gestión de Producción y Ventas******


📋 **Descripción**

Tlaxcalli es una solución de software de escritorio diseñada para la administración integral de procesos productivos y
comerciales en negocios de elaboración y venta de productos derivados del maíz. El sistema permite gestionar desde la 
producción física en el molino hasta el control estricto de ventas diarias por repartidores y mostrador, asegurando la
consistencia de los datos mediante una arquitectura transaccional robusta (ACID) y un modelo de persistencia desacoplado.


🚀 **Características Principales**

**Gestión de Producción:** Registro y control de insumos (botes de maíz, sacos de harina) y desperdicios del molino con cálculo
automático de rendimiento y conversión masa/tortilla.

**Control de Ventas:** Registro detallado de ventas por repartidor y mostrador con recálculo dinámico en tiempo real en la interfaz.

**Integridad de Datos:** Arquitectura basada en transacciones controladas con commit y rollback manuales para evitar inconsistencias
y proteger la información en la base de datos ante cierres inesperados.

**Corte Diario:** Balance automático entre la producción total calculada y las ventas reales inyectadas.

**Seguridad:** Módulo de autenticación con encriptación de contraseñas utilizando hashing SHA-512.


🛠️ **Stack Tecnológico & Arquitectura**

Gestor de Proyectos: Apache Maven (Gestión de dependencias y ciclo de vida de compilación).

Lenguaje: Java

Base de Datos: SQLite (Modo portátil e integrado).

Interfaz Gráfica: Swing (Diseñado con el editor visual de NetBeans).

Patrón de Diseño: DAO (Data Access Object) asistido por una Fábrica Abstracta (DAOFactory) para el intercambio dinámico de entornos
de persistencia.



⚙️ **Estructura del Proyecto**

/com.ometeotl.tlaxcalli

├── src/

│   ├── main/

│   │   ├── java/

│   │   │   └── com/ometeotl/tlaxcalli/

│   │   │       ├── IGU/          # Interfaces Gráficas de Usuario (.java y .form)

│   │   │       ├── LOGICA/       # Controladores, modelos y lógica de negocio

│   │   │       └── PERSISTENCIA/ # Capa de datos, DAOs y servicios transaccionales

│   │   └── resources/            # Recursos visuales (imágenes, iconos y configuración)

└── pom.xml                       # Descriptor de proyecto Maven



🏗️ **Instalación y Compilación**

Al ser un proyecto desarrollado bajo la arquitectura Maven, la gestión de dependencias (como los conectores JDBC de SQLite)
está completamente automatizada. Para compilar, empaquetar y generar el archivo ejecutable .jar que incluya todas las
dependencias embebidas, ejecute el siguiente comando en la terminal desde la raíz del proyecto:

mvn clean package assembly:single


*(O en su defecto, asegúrese de ejecutar la meta de construcción que genere el archivo empaquetado bajo el sufijo
-jar-with-dependencies.jar para garantizar su portabilidad).*


**Ejecución:**

Una vez empaquetado, puede iniciar la aplicación localmente mediante:

java -jar target/tlaxcalli-1.0-SNAPSHOT-jar-with-dependencies.jar


*Nota: El sistema generará automáticamente el archivo de base de datos local tlaxcalli.db en la primera ejecución si este
no es detectado en la ruta raíz.*
