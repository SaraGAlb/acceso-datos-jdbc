# 📚 acceso-datos-jdbc

[![Java](https://img.shields.io/badge/Java-22-orange?logo=openjdk)](https://openjdk.org/)
[![Maven](https://img.shields.io/badge/Maven-3.x-red?logo=apachemaven)](https://maven.apache.org/)
[![PostgreSQL](https://img.shields.io/badge/PostgreSQL-16-blue?logo=postgresql)](https://www.postgresql.org/)
[![JDBC](https://img.shields.io/badge/JDBC-API-green)](https://docs.oracle.com/javase/tutorial/jdbc/)

Proyecto desarrollado durante el ciclo formativo de **Desarrollo de Aplicaciones Multiplataforma (DAM)** para el módulo de **Acceso a Datos**.

Contiene cuatro subproyectos que muestran la evolución progresiva desde un acceso básico a base de datos hasta la gestión de operaciones atómicas con transacciones sobre múltiples tablas relacionadas.

---

## 📁 Estructura del repositorio

```
acceso-datos-jdbc/
├── jdbc-basico/           → CRUD directo con un único DAO
├── arquitectura-capas/    → Aplicación estructurada en capas (repositorio, servicio, app)
├── transacciones/         → Control manual de transacciones JDBC (commit y rollback)
└── prestamos/             → Sistema de préstamos de libros con transacciones sobre dos tablas
```

---

## 🗂️ Subproyectos

### 1. [`jdbc-basico`](./jdbc-basico)

Demostración mínima del uso de JDBC. Un único DAO realiza todas las operaciones CRUD sobre una tabla `libro` en PostgreSQL. Ideal para entender cómo funciona la conexión y las consultas sin capas intermedias.

### 2. [`arquitectura-capas`](./arquitectura-capas)

Evolución del proyecto anterior aplicando una arquitectura en capas real: modelo, repositorio (interfaz + implementación JDBC), servicio con lógica de negocio y clase `main`. Demuestra desacoplamiento e inyección de dependencias manual.

### 3. [`transacciones`](./transacciones)

Introduce el control manual de transacciones JDBC sobre la tabla `libro`. Muestra cómo deshabilitar el autocommit, compartir una conexión entre repositorio y servicio, y aplicar `commit` o `rollback` según el resultado de las operaciones. Incluye una demo con fallo forzado para verificar que el rollback funciona correctamente.

### 4. [`prestamos`](./prestamos)

Caso de uso real que combina transacciones con una relación entre tablas (`libro` y `prestamo`). Al prestar un libro se crea un registro de préstamo y se marca el libro como no disponible en la misma transacción atómica. La devolución revierte ambos estados. Incluye script DDL con datos de prueba y validación de disponibilidad antes de prestar.

---

## 🛠️ Tecnologías utilizadas

| Tecnología | Uso |
|------------|-----|
| Java 22 | Lenguaje principal |
| JDBC API | Conexión, consultas y control de transacciones |
| PostgreSQL | Base de datos relacional |
| Maven | Gestión de dependencias y compilación |

---

## 👩‍💻 Autora

**Sara García Albandea**  
Estudiante de 2º DAM — Centro Formativo Digitech  
📧 [sg.albandea@gmail.com](mailto:sg.albandea@gmail.com)  
🔗 [GitHub](https://github.com/SaraGAlb)
