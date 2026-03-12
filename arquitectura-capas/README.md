# arquitectura-capas

Ejemplo de aplicación con arquitectura en capas usando JDBC.
Cada capa está separada en un paquete distinto para demostrar
desacoplamiento y facilidad de mantenimiento.

Estructura principal:

- **modelo** (`com.example.model.Libro`): entidad de dominio.
- **repositorio** (`com.example.repository`): interfaz `LibroRepository`
y su implementación JDBC `JdbcLibroRepository`.
- **servicio** (`com.example.service.LibroService`): lógica de negocio
  y validaciones.
- **aplicación** (`com.example.app.CrudDemo`): clase `main` que
  construye el repositorio, lo inyecta en el servicio y ejecuta
  operaciones de ejemplo.
- **db** (`com.example.db.Db`): proveedor de conexiones.

## Cómo usar

1. Rellena `src/main/resources/db.properties` con los datos de tu
   base de datos.
2. Compila y ejecuta:
   ```sh
   mvn compile exec:java -Dexec.mainClass=com.example.app.CrudDemo
   ```
3. Los tests (si los hay) se pueden ejecutar con `mvn test`.

> Este proyecto ilustra cómo se puede cambiar la implementación del
repositorio (por ejemplo por una versión en memoria para pruebas)
sin modificar la lógica de servicio ni la aplicación.
