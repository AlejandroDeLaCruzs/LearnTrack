package Controlador.ValidacionUsuarios;

import Modelo.Ficheros.GestorUsuariosCSV;
import Modelo.Usuarios.*;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.Comparator;

public class UsuariosController {

    public List<Usuario> obtenerUsuarios() {
        return GestorUsuariosCSV.cargarUsuarios();
    }

    public String agregarUsuario(String nombre, String correo, String rol) {
        if (nombre.isEmpty() || correo.isEmpty() || rol == null)
            return "Complete todos los campos.";

        if (!correo.contains("@"))
            return "El correo debe contener @";

        if (!(correo.endsWith(".com") || correo.endsWith(".es")))
            return "El correo debe terminar en .com o .es";

        List<Usuario> usuarios = GestorUsuariosCSV.cargarUsuarios();

        boolean nombreExiste = usuarios.stream().anyMatch(u -> u.getNombre().equalsIgnoreCase(nombre));
        boolean correoExiste = usuarios.stream().anyMatch(u -> u.getCorreo().equalsIgnoreCase(correo));

        if (nombreExiste || correoExiste)
            return "Nombre o correo ya existentes.";

        String id = generarIdUnico(usuarios);
        String contrasena = generarContrasena();

        Usuario nuevo;
        switch (rol) {
            case "Administrador":
                nuevo = new Administrador(id, nombre, correo, contrasena);
                break;
            case "Profesor":
                nuevo = new Profesor(id, nombre, correo, contrasena);
                break;
            default:
                nuevo = new Alumno(id, nombre, correo, contrasena);
                break;
        }

        usuarios.add(nuevo);
        GestorUsuariosCSV.guardarUsuarios(usuarios);
        return null; // null indica éxito
    }

    public boolean puedeEliminarUsuario(String idAEliminar, String idActual) {
        return !idAEliminar.equals(idActual);
    }

    public void eliminarUsuario(String idAEliminar) {
        List<Usuario> actualizados = GestorUsuariosCSV.cargarUsuarios()
                .stream()
                .filter(u -> !u.getId().equals(idAEliminar))
                .collect(Collectors.toList());

        GestorUsuariosCSV.guardarUsuarios(actualizados);
    }

    private String generarIdUnico(List<Usuario> usuarios) {
        if (usuarios.isEmpty()) return "A000";

        String maxId = usuarios.stream()
                .map(Usuario::getId)
                .max(Comparator.comparing(this::idToComparable))
                .orElse("A000");

        // Convertir el ID máximo a la siguiente secuencia
        char letra = maxId.charAt(0);
        int numero = Integer.parseInt(maxId.substring(1));

        if (numero == 999) {
            letra++;
            numero = 0;
        } else {
            numero++;
        }

        return String.format("%c%03d", letra, numero);
    }

    // Utilidad para comparar IDs correctamente
    private int idToComparable(String id) {
        char letra = id.charAt(0);
        int numero = Integer.parseInt(id.substring(1));
        return (letra - 'A') * 1000 + numero;
    }


    private String generarContrasena() {
        Random random = new Random();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 4; i++) {
            sb.append((char) (random.nextInt(26) + 'A'));
        }
        for (int i = 0; i < 4; i++) {
            sb.append(random.nextInt(10));
        }
        return sb.toString();
    }
}
