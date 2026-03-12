# jdbc-basico

Proyecto de ejemplo que muestra el uso básico de JDBC para
realizar operaciones CRUD sobre una tabla `libro`.

Estructura:

- `com.example.dao.LibroDao`: DAO con métodos `findAll`,
  `findById`, `searchByTitulo`, `insert`, `update`, `deleteById`.
- `com.example.app.CrudDemo`: demo en `main` que utiliza el DAO
y ejecuta cada operación.
- `com.example.db.Db`: clase utilitaria para obtener la conexión
a la base de datos leyendo `db.properties`.
- `com.example.model.Libro`: modelo simple.

## Ejecución

1. Configurar `src/main/resources/db.properties` con la URL,
   usuario y contraseña de la base de datos (PostgreSQL, MySQL, ...).
2. Compilar y ejecutar con Maven:
   ```sh
   mvn compile exec:java -Dexec.mainClass=com.example.app.CrudDemo
   ```

Alternativamente ejecutar los tests con `mvn test`.

> Este proyecto es autónomo y no depende de ninguna otra capa.
