package Controlador.ValidacionCursos;

import Modelo.Cursos.Curso;
import Modelo.Ficheros.GestorCursosCSV;
import Modelo.Usuarios.Usuario;
import Modelo.Ficheros.GestorUsuariosCSV;

import java.util.List;

public class ValidacionAsignarProfesor {
    public boolean puedeAsignarProfesor(Curso curso, String idProfesor) {
        List<Usuario> usuarios = GestorUsuariosCSV.cargarUsuarios();
        Usuario profesor = usuarios.stream().filter(u -> u.getNombre().equals(idProfesor)).findFirst().orElse(null);

        if (profesor != null && profesor.getRol().equals("Profesor")) {
            // Verificar si ya está asignado
            return !GestorCursosCSV.profesorYaAsignado(idProfesor, curso.getId());
        }
        return false;
    }
}
