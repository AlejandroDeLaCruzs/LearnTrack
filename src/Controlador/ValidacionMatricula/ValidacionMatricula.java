package Controlador.ValidacionMatricula;

import Modelo.Cursos.Calificacion;
import Modelo.Ficheros.GestorCalificacionesCSV;

import java.util.List;

public class ValidacionMatricula {

    public static boolean inscribirAlumno(String idCurso, String idAlumno, String nombreAlumno) {
        List<Calificacion> calificaciones = GestorCalificacionesCSV.cargarCalificaciones();

        // Comprobar si ya está inscrito
        for (Calificacion c : calificaciones) {
            if (c.getIdCurso().equals(idCurso) && c.getIdAlumno().equals(idAlumno)) {
                return false; // Ya está inscrito
            }
        }

        // Agregar nueva calificación con calificación null
        Calificacion nueva = new Calificacion(idCurso, idAlumno, nombreAlumno, null);
        calificaciones.add(nueva);
        GestorCalificacionesCSV.guardarCalificaciones(calificaciones);
        return true;
    }
}
