package Controlador.ValidacionCursos;

import Modelo.Cursos.Curso;
import Modelo.Ficheros.GestorCursosCSV;
import java.util.List;

public class ValidacionCrearCurso {
    public String validar(String id, String nombre) {
        if (id.isEmpty() || nombre.isEmpty()) {
            return "Todos los campos son obligatorios.";
        }

        List<Curso> cursos = GestorCursosCSV.cargarCursos();
        boolean existe = cursos.stream().anyMatch(c -> c.getId().equals(id));
        if (existe) {
            return "Ya existe un curso con ese ID.";
        }
        return null; // Sin errores
    }
}
