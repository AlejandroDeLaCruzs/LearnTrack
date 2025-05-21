package Controlador.ValidacionCursos;

import Modelo.Cursos.Calificacion;
import Modelo.Cursos.Curso;
import Modelo.Ficheros.GestorCalificacionesCSV;
import Modelo.Ficheros.GestorCursosCSV;

import java.util.List;

public class ListaCursosController {
    public List<Curso> obtenerCursos() {
        return GestorCursosCSV.cargarCursos();
    }

    public void eliminarCurso(String cursoId) {
        List<Curso> cursos = GestorCursosCSV.cargarCursos();
        List<Calificacion> calificacions = GestorCalificacionesCSV.cargarCalificaciones();

        cursos.removeIf(c -> c.getId().equals(cursoId));
        calificacions.removeIf( c -> c.getIdCurso().equals(cursoId));

        GestorCalificacionesCSV.guardarCalificaciones(calificacions);
        GestorCursosCSV.guardarCursos(cursos);
    }
}
