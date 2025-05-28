# LearnTrack

**LearnTrack** es una aplicación de escritorio desarrollada en Java que funciona como un sistema de gestión del aprendizaje (LMS - Learning Management System). Está diseñada para gestionar cursos y usuarios en entornos educativos sin necesidad de conexión a internet, utilizando archivos locales en formato `.csv`.

## 🚀 Propósito

LearnTrack tiene como objetivo mejorar la administración de cursos, evaluación y comunicación entre administradores, profesores y estudiantes. Se busca reducir el tiempo de gestión en un 50% y mejorar el acceso a materiales para el 90% de los estudiantes, siendo utilizable con solo 5 minutos de capacitación.

## 🧩 Características principales

- Gestión local de usuarios y cursos usando archivos `.csv`.
- Interfaz gráfica basada en Java Swing.
- Rol de usuario definido: Administrador, Profesor, Estudiante.
- Acceso y funcionalidades diferenciadas por rol.
- Calificación, inscripción, creación y eliminación de cursos.

## 🖥️ Tecnologías

- **Lenguaje:** Java 17
- **Interfaz gráfica:** Swing
- **Formato de datos:** Archivos `.csv` (sin base de datos)
- **Requisitos mínimos del sistema:**
  - Java Runtime Environment (JRE) 17 o superior
  - 4 GB de RAM
  - 500 MB de almacenamiento
  - Compatible con Windows 10+, macOS 11+ y distribuciones populares de Linux (Ubuntu 20.04+)

## 📋 Casos de Uso Principales

- **CU-1:** Inicio de sesión (Estudiantes, Profesores, Administradores)
- **CU-2:** Registro de usuarios (Administrador)
- **CU-3:** Creación de cursos (Administrador)
- **CU-4:** Asignación de profesores (Administrador)
- **CU-5:** Eliminación de cursos (Administrador)
- **CU-6:** Inscripción de estudiantes en cursos
- **CU-7:** Evaluación de estudiantes (Profesor)
- **CU-8:** Consulta de calificaciones (Estudiante)

## 📈 Requisitos de rendimiento

- El 95% de las operaciones deben ejecutarse en menos de 1 segundo.
- Soporte para hasta 1,000 cursos y 5,000 estudiantes.

## 📦 Estructura de Datos (Archivos)

- **Usuarios:** `id`, `nombre`, `correo`, `contraseña`, `rol`
- **Cursos:** `id`, `nombre`, `id_profesor`
- **Matrículas:** `id_usuario`, `id_curso`, `calificación`

## 🔐 Seguridad

- Autenticación mediante correo y contraseña (texto plano, con posibilidad de mejora).
- No requiere conexión a internet.

## 🔮 Planes Futuros

- Migración a una base de datos distribuida como PostgreSQL/MySQL.
- Sincronización en tiempo real.
- Escalabilidad para más de 1,000 usuarios simultáneos.

## 📅 Cronograma

- **Entrega del documento IEEE 830:** 10 de marzo de 2025
- **Primera versión funcional:** Dentro de 3 meses desde la aprobación del documento SRS.

---

Desarrollado como parte de la asignatura **Ingeniería del Software - Grado en Ingeniería Informática (2024/25)**.
