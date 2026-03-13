# arquitectura-capas

![Java](https://img.shields.io/badge/Java-22-orange?logo=openjdk)
![Maven](https://img.shields.io/badge/Maven-3.x-red?logo=apachemaven)
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-16-blue?logo=postgresql)

Subproyecto de [`acceso-datos-jdbc`](../README.md).

Evolución de `jdbc-basico` aplicando una **arquitectura en capas** real. El mismo CRUD sobre la tabla `libro`, pero estructurado con responsabilidades claras y separadas: modelo, repositorio, servicio y aplicación.

---

## 📁 Estructura

```
arquitectura-capas/
├── src/main/java/com/example/
│   ├── app/
│   │   └── CrudDemo.java                  → Clase main, ejecuta las operaciones
│   ├── db/
│   │   └── Db.java                        → Proveedor de conexiones
│   ├── model/
│   │   └── Libro.java                     → Entidad de dominio
│   ├── repository/
│   │   ├── LibroRepository.java           → Interfaz del repositorio
│   │   └── jdbc/
│   │       └── JdbcLibroRepository.java   → Implementación concreta con JDBC
│   └── service/
│       └── LibroService.java              → Lógica de negocio y validaciones
└── src/main/resources/
    └── db.properties.example              → Plantilla de configuración (sin credenciales reales)
```

---

## 🏗️ Arquitectura

```
[ CrudDemo.java / main ]
          ↓
   [ LibroService ]          → lógica de negocio, validaciones
          ↓
  [ LibroRepository ]        → interfaz (contrato)
          ↓
[ JdbcLibroRepository ]      → implementación concreta con JDBC
          ↓
      [ PostgreSQL ]
```

La clave del diseño es que `LibroService` depende de la **interfaz** `LibroRepository`, no de la implementación concreta. Esto permite cambiar la implementación (por ejemplo, por una versión en memoria para tests) sin tocar la lógica de negocio.

---

## 🚀 Cómo ejecutarlo

### Requisitos
- Java 22
- Maven
- PostgreSQL en local

### Pasos

**1. Configura la base de datos**

Copia el archivo de ejemplo y añade tus credenciales:
```sh
cp src/main/resources/db.properties.example src/main/resources/db.properties
```

Edita `db.properties`:
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

## 💡 ¿Qué aporta frente a jdbc-basico?

| | jdbc-basico | arquitectura-capas |
|---|---|---|
| Capas | 3 (dao, db, model) | 4 (repository, service, db, model) |
| Interfaz de repositorio | ❌ | ✅ |
| Capa de servicio con lógica de negocio | ❌ | ✅ |
| Facilidad para hacer tests | Media | Alta |
| Escalabilidad | Media | Alta |

---

## 📌 Nota

Este subproyecto es el paso natural después de entender `jdbc-basico`. Refleja un diseño más cercano a lo que se usa en proyectos reales con frameworks como Spring Boot.