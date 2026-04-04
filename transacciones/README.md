# 🔄 transacciones

[![Java](https://img.shields.io/badge/Java-22-orange?logo=openjdk)](https://openjdk.org/)
[![Maven](https://img.shields.io/badge/Maven-3.x-red?logo=apachemaven)](https://maven.apache.org/)
[![PostgreSQL](https://img.shields.io/badge/PostgreSQL-16-blue?logo=postgresql)](https://www.postgresql.org/)
[![JDBC](https://img.shields.io/badge/JDBC-API-green)](https://docs.oracle.com/javase/tutorial/jdbc/)

Subproyecto del módulo **Acceso a Datos** (2º DAM) que demuestra el uso de **transacciones JDBC** en Java con PostgreSQL.

---

## 🎯 Objetivo

Mostrar cómo controlar manualmente una transacción con JDBC: deshabilitar el autocommit, ejecutar operaciones encadenadas y aplicar `commit` o `rollback` según el resultado.

---

## 📁 Estructura del proyecto

```
transacciones/
└── src/
    └── main/
        ├── java/com/example/
        │   ├── app/
        │   │   └── TxDemo.java               → Punto de entrada, lanza la demo
        │   ├── db/
        │   │   └── Db.java                   → Gestión de la conexión (db.properties)
        │   ├── model/
        │   │   └── Libro.java                → Entidad Libro
        │   ├── repository/
        │   │   ├── LibroRepository.java      → Interfaz CRUD
        │   │   └── jdbc/
        │   │       └── JdbcLibroRepository.java → Implementación JDBC (con sobrecarga transaccional)
        │   └── service/
        │       └── TxLibroService.java       → Lógica de negocio con control de transacción
        └── resources/
            └── db.properties                 → Configuración de la conexión (no incluido en el repo)
```

---

## ⚙️ Cómo funciona

### Flujo de la transacción

```
TxDemo
  └── TxLibroService.marcarNoDisponiblePeroFallar(id)
        ├── con.setAutoCommit(false)       ← inicio de transacción
        ├── findByIdOrNull(con, id)        ← lectura con la misma conexión
        ├── libro.setDisponible(false)
        ├── update(con, libro)             ← escritura con la misma conexión
        ├── [error opcional]               ← simula un fallo
        ├── con.commit()  ✅               ← si todo va bien
        └── con.rollback() ❌              ← si algo lanza excepción
```

### Puntos clave del código

- **`setAutoCommit(false)`**: desactiva el guardado automático de cada operación, permitiendo agruparlas en una unidad.
- **Conexión compartida**: tanto la lectura (`findByIdOrNull`) como la escritura (`update`) reciben la misma `Connection`, condición indispensable para que formen parte de la misma transacción.
- **`rollback()`** en el bloque `catch`: deshace todos los cambios si ocurre cualquier error.
- **`setAutoCommit(true)`** en `finally`: restaura el comportamiento normal de la conexión antes de cerrarla.

---

## 🧪 Demo incluida

`TxDemo` llama a `marcarNoDisponiblePeroFallar(1)`, que:

1. Busca el libro con `id=1`
2. Cambia su campo `disponible` a `false`
3. Guarda el cambio en la BD
4. (Opcionalmente) lanza una excepción forzada para activar el rollback

Para activar el rollback, descomenta esta línea en `TxLibroService`:

```java
throw new RuntimeException("Fallo forzado para demostrar rollback");
```

Tras ejecutar, puedes comprobar en PostgreSQL que el campo **no cambió**, confirmando que el rollback funcionó correctamente.

---

## 🛠️ Configuración

Crea el fichero `src/main/resources/db.properties` con tus datos:

```properties
db.url=jdbc:postgresql://localhost:5432/nombre_bd
db.user=tu_usuario
db.password=tu_contraseña
```

---

## 🛠️ Tecnologías

| Tecnología   | Uso                                      |
|--------------|------------------------------------------|
| Java 22      | Lenguaje principal                       |
| JDBC API     | Control manual de transacciones          |
| PostgreSQL   | Base de datos relacional                 |
| Maven        | Gestión de dependencias y compilación    |

---

## 👩‍💻 Autora

**Sara García Albandea**  
Estudiante de 2º DAM — Centro Formativo Digitech  
📧 [sg.albandea@gmail.com](mailto:sg.albandea@gmail.com)  
🔗 [GitHub](https://github.com/SaraGAlb)
