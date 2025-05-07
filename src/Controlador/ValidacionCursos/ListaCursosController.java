package Controlador.ValidacionCursos;

import Modelo.Cursos.Curso;
import Modelo.Ficheros.GestorCursosCSV;

import java.util.List;

public class ListaCursosController {
    public List<Curso> obtenerCursos() {
        return GestorCursosCSV.cargarCursos();
    }

    public void eliminarCurso(String cursoId) {
        List<Curso> cursos = GestorCursosCSV.cargarCursos();
        cursos.removeIf(c -> c.getId().equals(cursoId));
        GestorCursosCSV.guardarCursos(cursos);
    }
}
