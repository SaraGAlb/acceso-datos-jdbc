# 📚 acceso-datos-jdbc

![Java](https://img.shields.io/badge/Java-22-orange?logo=openjdk)
![Maven](https://img.shields.io/badge/Maven-3.x-red?logo=apachemaven)
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-16-blue?logo=postgresql)
![JDBC](https://img.shields.io/badge/JDBC-API-green)

Proyecto desarrollado durante el ciclo formativo de **Desarrollo de Aplicaciones Multiplataforma (DAM)** para el módulo de **Acceso a Datos**.

Contiene dos subproyectos que muestran la evolución desde un acceso básico a base de datos hasta una arquitectura limpia y desacoplada por capas.

---

## 📁 Estructura del repositorio

```
acceso-datos-jdbc/
├── jdbc-basico/          → CRUD directo con un único DAO
└── arquitectura-capas/   → Aplicación estructurada en capas (repositorio, servicio, app)
```

---

## 🗂️ Subproyectos

### 1. [`jdbc-basico`](./jdbc-basico)
Demostración mínima del uso de JDBC. Un único DAO realiza todas las operaciones CRUD sobre una tabla `libro` en PostgreSQL. Ideal para entender cómo funciona la conexión y las consultas sin capas intermedias.

### 2. [`arquitectura-capas`](./arquitectura-capas)
Evolución del proyecto anterior aplicando una arquitectura en capas real: modelo, repositorio (interfaz + implementación JDBC), servicio con lógica de negocio y clase `main`. Demuestra desacoplamiento e inyección de dependencias manual.

---

## 🛠️ Tecnologías utilizadas

| Tecnología | Uso |
|---|---|
| Java 22 | Lenguaje principal |
| JDBC API | Conexión y consultas a base de datos |
| PostgreSQL | Base de datos relacional |
| Maven | Gestión de dependencias y compilación |

---

## 👩‍💻 Autora

**Sara García Albandea**  
Estudiante de 2º DAM — Centro Formativo Digitech  
📧 sg.albandea@gmail.com  
🔗 [GitHub](https://github.com/tu-usuario)
