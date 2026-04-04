# 📖 prestamos

[![Java](https://img.shields.io/badge/Java-22-orange?logo=openjdk)](https://openjdk.org/)
[![Maven](https://img.shields.io/badge/Maven-3.x-red?logo=apachemaven)](https://maven.apache.org/)
[![PostgreSQL](https://img.shields.io/badge/PostgreSQL-16-blue?logo=postgresql)](https://www.postgresql.org/)
[![JDBC](https://img.shields.io/badge/JDBC-API-green)](https://docs.oracle.com/javase/tutorial/jdbc/)

Subproyecto del módulo **Acceso a Datos** (2º DAM) que implementa un sistema de **gestión de préstamos de libros** usando JDBC con transacciones, arquitectura en capas y dos tablas relacionadas (`libro` y `prestamo`).

---

## 🎯 Objetivo

Desarrollar un caso de uso real que combina transacciones JDBC con una relación entre tablas: al prestar un libro se crea un registro en `prestamo` y se actualiza el estado del `libro` en la misma transacción atómica. Si algo falla, ambas operaciones se deshacen con rollback.

---

## 📁 Estructura del proyecto

```
prestamos/
└── src/
    └── main/
        ├── java/com/example/
        │   ├── app/
        │   │   └── PrestamoDemo.java              → Punto de entrada, lanza la demo
        │   ├── db/
        │   │   └── Db.java                        → Gestión de la conexión (db.properties)
        │   ├── model/
        │   │   ├── Libro.java                     → Entidad Libro
        │   │   ├── Prestamo.java                  → Entidad Préstamo
        │   │   └── Usuario.java                   → Entidad Usuario
        │   ├── repository/
        │   │   ├── LibroRepository.java           → Interfaz CRUD de libros
        │   │   └── jdbc/
        │   │       ├── JdbcLibroRepository.java   → Implementación JDBC (con métodos transaccionales)
        │   │       └── JdbcPrestamoRepository.java → Repositorio de préstamos (insert y devolución)
        │   └── service/
        │       ├── PrestamoService.java           → Lógica de negocio: prestar y devolver
        │       └── TxLibroService.java            → Servicio auxiliar de transacciones sobre libros
        └── resources/
            ├── db/
            │   └── ddl_practica6_prestamo.sql     → Script DDL con tablas y datos de prueba
            └── db.properties.example              → Plantilla de configuración de la conexión
```

---

## 🗄️ Modelo de datos

```sql
usuario (id, nombre, email)
    │
    └──< prestamo (id, usuario_id, libro_id, fecha_inicio, fecha_fin, fecha_devolucion)
                                    │
                                    └──> libro (id, titulo, isbn, anio, disponible)
```

Un préstamo activo es aquel con `fecha_devolucion IS NULL`. Un índice único sobre `(libro_id) WHERE fecha_devolucion IS NULL` garantiza que no puede haber dos préstamos activos del mismo libro a nivel de base de datos.

---

## ⚙️ Cómo funciona

### Prestar un libro — `PrestamoService.prestarLibro()`

```
1. setAutoCommit(false)
2. isDisponible(con, libroId)        → comprueba que el libro esté libre
3. prestamoRepo.insert(con, p)       → inserta fila en tabla prestamo
4. libroRepo.updateDisponible(...)   → marca libro como disponible=false
5. commit() ✅  /  rollback() ❌
6. setAutoCommit(true)
```

### Devolver un libro — `PrestamoService.devolverLibro()`

```
1. setAutoCommit(false)
2. prestamoRepo.marcarDevuelto(con, libroId)   → rellena fecha_devolucion = CURRENT_DATE
3. libroRepo.updateDisponible(con, libroId, true) → marca libro como disponible=true
4. commit() ✅  /  rollback() ❌
5. setAutoCommit(true)
```

Ambas operaciones comparten la misma `Connection`, garantizando atomicidad.

---

## 🧪 Demo incluida

`PrestamoDemo` ejecuta tres casos en secuencia:

| Caso | Resultado esperado |
|------|--------------------|
| Prestar libro `id=1` al usuario `id=1` | ✅ Préstamo creado, libro marcado no disponible |
| Prestar el mismo libro `id=1` a otro usuario | ❌ Excepción: libro no disponible |
| Devolver el libro `id=1` | ✅ `fecha_devolucion` rellenada, libro disponible de nuevo |

---

## 🛠️ Configuración

### 1. Crear la base de datos

Ejecuta el script DDL incluido en `src/main/resources/db/`:

```bash
psql -U tu_usuario -d tu_bd -f ddl_practica6_prestamo.sql
```

El script crea las tablas `usuario` y `prestamo`, el índice de préstamo activo único, e inserta datos de prueba (27 usuarios y 28 libros de fantasía y ciencia ficción).

### 2. Configurar la conexión

Copia `db.properties.example` como `db.properties` en `src/main/resources/` y rellena tus datos:

```properties
db.url=jdbc:postgresql://localhost:5432/nombre_bd
db.user=tu_usuario
db.password=tu_contraseña
```

> `db.properties` está en `.gitignore` para no exponer credenciales.

---

## 🛠️ Tecnologías

| Tecnología | Uso |
|------------|-----|
| Java 22 | Lenguaje principal |
| JDBC API | Transacciones y acceso a base de datos |
| PostgreSQL | Base de datos relacional (dos tablas relacionadas) |
| Maven | Gestión de dependencias y compilación |

---

## 👩‍💻 Autora

**Sara García Albandea**  
Estudiante de 2º DAM — Centro Formativo Digitech  
📧 [sg.albandea@gmail.com](mailto:sg.albandea@gmail.com)  
🔗 [GitHub](https://github.com/SaraGAlb)
