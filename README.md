# LearnTrack

**LearnTrack** es un sistema de gestión del aprendizaje (LMS) de escritorio, desarrollado en Java con interfaz gráfica Swing. Está diseñado para entornos sin conexión a internet, utilizando archivos `.csv` para almacenar y gestionar usuarios, cursos y calificaciones.

Este proyecto fue desarrollado como parte de la asignatura **Ingeniería del Software** del **Grado en Ingeniería Informática** (curso 2024/25) — Grupos 2INP y 2IIP.

---

## 🎯 Objetivo

Proporcionar una plataforma de gestión educativa ligera, portátil y funcional para docentes, alumnos y administradores, sin depender de conexión a internet ni infraestructura externa.

---

## 🛠️ Tecnologías

- **Lenguaje:** Java 17
- **GUI:** Java Swing
- **Arquitectura:** MVC (Modelo-Vista-Controlador)
- **Persistencia:** Archivos `.csv`

---

## 🧱 Arquitectura

LearnTrack está diseñado siguiendo el patrón **Modelo-Vista-Controlador (MVC)**:

- **Modelo:** Maneja los datos (usuarios, cursos, matrículas) y su almacenamiento.
- **Vista:** Interfaz dinámica en Java Swing según el rol del usuario.
- **Controlador:** Lógica de negocio que conecta vista y modelo (login, creación de cursos, inscripción, calificación).

---

## 👤 Roles de usuario

- **Administrador:** Registra usuarios, crea cursos, asigna profesores.
- **Profesor:** Califica a los estudiantes de sus cursos.
- **Estudiante:** Se inscribe en cursos y consulta calificaciones.

---

## 📚 Funcionalidades

- Inicio de sesión por rol
- Registro de usuarios
- Creación y eliminación de cursos
- Asignación de profesores
- Inscripción de estudiantes
- Calificación por parte de profesores
- Consulta de notas por parte de estudiantes

---

## 📦 Estructura de Archivos `.csv`

- `usuarios.csv`: `id`, `nombre`, `correo`, `contraseña`, `rol`
- `cursos.csv`: `id`, `nombre`, `id_profesor`
- `matriculas.csv`: `id_usuario`, `id_curso`, `calificación`

---

## ⚙️ Requisitos del sistema

- **Java Runtime:** JRE 17 o superior
- **Sistemas operativos soportados:**
  - Windows 10 o superior
  - macOS 11 o superior
  - Linux (Ubuntu 20.04 o superior)
- **Hardware mínimo:**
  - 4 GB de RAM
  - 500 MB de almacenamiento libre

---

## 🔐 Seguridad

- Autenticación básica por correo y contraseña (almacenadas en texto plano — se recomienda mejora futura).
- Sin conexión a internet: sistema completamente local.

---

## 📅 Cronograma académico

- 📝 **Entrega IEEE 830:** 10 de marzo de 2025  
- 🚀 **Primera versión funcional:** dentro de los 3 meses posteriores a la aprobación del SRS

---

## 🧪 Instalación y Ejecución

### 🔁 Clonar el repositorio

```bash
https://github.com/AlejandroDeLaCruzs/learntrack.git
