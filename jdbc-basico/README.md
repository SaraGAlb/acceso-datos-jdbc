# jdbc-basico

![Java](https://img.shields.io/badge/Java-22-orange?logo=openjdk)
![Maven](https://img.shields.io/badge/Maven-3.x-red?logo=apachemaven)
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-16-blue?logo=postgresql)

Subproyecto de [`acceso-datos-jdbc`](../README.md).

Implementación básica de operaciones **CRUD** sobre una tabla `libro` usando JDBC directamente, sin capas intermedias. El objetivo es mostrar cómo funciona la conexión a base de datos y la ejecución de consultas SQL desde Java.

---

## 📁 Estructura

```
jdbc-basico/
├── src/main/java/com/example/
│   ├── app/
│   │   └── CrudDemo.java        → Clase main, ejecuta todas las operaciones CRUD
│   ├── dao/
│   │   └── LibroDao.java        → DAO con métodos CRUD
│   ├── db/
│   │   └── Db.java              → Gestión de la conexión (lee db.properties)
│   └── model/
│       └── Libro.java           → Entidad de dominio
└── src/main/resources/
    └── db.properties            → Configuración de la base de datos (credenciales enmascaradas)
```

---

## ⚙️ Operaciones implementadas

| Método | Descripción |
|---|---|
| `findAll()` | Obtiene todos los libros |
| `findById(id)` | Busca un libro por ID |
| `searchByTitulo(titulo)` | Búsqueda por título |
| `insert(libro)` | Inserta un nuevo libro |
| `update(libro)` | Actualiza un libro existente |
| `deleteById(id)` | Elimina un libro por ID |

---

## 🚀 Cómo ejecutarlo

### Requisitos
- Java 22
- Maven
- PostgreSQL en local

### Pasos

**1. Configura la base de datos**

Edita `src/main/resources/db.properties` con tus credenciales:
```properties
db.url=jdbc:postgresql://localhost:5432/biblioteca_ad
db.user=postgres
db.password=tu_contraseña
```

**2. Crea la tabla en PostgreSQL**
```sql
CREATE TABLE libro (
    id SERIAL PRIMARY KEY,
    titulo VARCHAR(255),
    autor VARCHAR(255),
    anio_publicacion INT,
    disponible BOOLEAN
);
```

**3. Compila y ejecuta**
```sh
mvn compile exec:java -Dexec.mainClass=com.example.app.CrudDemo
```

**4. Ejecuta los tests**
```sh
mvn test
```

---

## 📌 Nota

Este subproyecto es autónomo e independiente de `arquitectura-capas`. Está pensado como punto de partida para entender JDBC antes de introducir capas de abstracción.