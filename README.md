# LearnTrack

**LearnTrack** es un sistema de gestión del aprendizaje (LMS) desarrollado como una aplicación de escritorio en Java. Está orientado a entornos educativos donde no se dispone de conexión a internet, utilizando archivos locales `.csv` para manejar usuarios, cursos y calificaciones. Es parte de un proyecto académico del Grado en Ingeniería Informática (curso 2024/25).

## 📌 Propósito

Proporcionar una solución ligera y funcional para la administración de cursos, evaluación y seguimiento del rendimiento académico sin necesidad de infraestructura en la nube.

## 🎯 Objetivos

- Reducir el tiempo de gestión de cursos en un 50%.
- Facilitar el acceso a materiales para el 90% de los estudiantes.
- Ser completamente funcional tras 5 minutos de uso inicial.

## 🛠️ Tecnologías

- **Lenguaje:** Java 17
- **GUI:** Java Swing
- **Almacenamiento:** Archivos `.csv` (usuarios, cursos, matrículas)
- **Arquitectura:** MVC (Modelo-Vista-Controlador)

## 🧱 Arquitectura del sistema

El sistema está organizado según el patrón MVC:

- **Modelo:** Gestiona los datos del sistema (usuarios, cursos, matrículas).
- **Vista:** Interfaz gráfica mediante paneles Swing (JPanel).
- **Controlador:** Lógica de negocio (login, registro, asignaciones, evaluaciones).

## 🖥️ Requisitos del sistema

- **Software:** Java Runtime Environment (JRE) 17+
- **Sistemas operativos soportados:**
  - Windows 10+
  - macOS 11+
  - Linux (Ubuntu 20.04+)
- **Hardware mínimo:**
  - 4 GB de RAM
  - 500 MB de almacenamiento

## 👤 Roles de usuario

- **Administrador:** Registra usuarios y cursos, asigna profesores.
- **Profesor:** Califica estudiantes de sus cursos.
- **Estudiante:** Se inscribe a cursos y consulta sus calificaciones.

## 📚 Funcionalidades principales

- Inicio de sesión por roles
- Registro y eliminación de usuarios
- Creación y eliminación de cursos
- Asignación de profesores a cursos
- Inscripción de estudiantes
- Calificación y consulta de notas

## 🖼️ Diseño de interfaz

Cada rol accede a vistas específicas en la aplicación, por ejemplo:

- **Administrador:** Panel de usuarios y cursos con gestión completa.
- **Profesor:** Lista de estudiantes y campos para notas.
- **Estudiante:** Vistas para inscribirse y ver notas.

La interfaz es dinámica y se adapta al rol una vez iniciada la sesión.

## 📊 Requisitos de rendimiento

- El 95% de las operaciones deben completarse en menos de 1 segundo.
- Soporta hasta 1,000 cursos y 5,000 registros.

## 📦 Estructura de archivos `.csv`

- `usuarios.csv`: `id`, `nombre`, `correo`, `contraseña`, `rol`
- `cursos.csv`: `id`, `nombre`, `id_profesor`
- `matriculas.csv`: `id_usuario`, `id_curso`, `calificación`

## 🔐 Seguridad

- Autenticación básica mediante correo y contraseña (almacenadas en texto plano).
- No requiere conexión a internet.

## 📈 Planes futuros

- Migración a base de datos SQL (PostgreSQL/MySQL).
- Sincronización en la nube y uso multiusuario.
- Mejora de seguridad y cifrado de contraseñas.

## 📅 Cronograma

- 📄 Entrega de documento IEEE 830: 10 de marzo de 2025
- 🚀 Primera versión funcional: dentro de los 3 meses posteriores a la aprobación

---

Proyecto académico desarrollado para la asignatura **Ingeniería del Software**, Grado en Ingeniería Informática, curso 2024/25 – Grupos 2INP y 2IIP.
