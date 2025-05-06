package Controlador;

import Modelo.Ficheros.GestorUsuariosCSV;
import Modelo.Usuarios.*;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

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
        if (idAEliminar.equals(idActual))
            return false;

        return true;
    }

    public void eliminarUsuario(String idAEliminar) {
        List<Usuario> actualizados = GestorUsuariosCSV.cargarUsuarios()
                .stream()
                .filter(u -> !u.getId().equals(idAEliminar))
                .collect(Collectors.toList());

        GestorUsuariosCSV.guardarUsuarios(actualizados);
    }

    private String generarIdUnico(List<Usuario> usuarios) {
        String nuevoId;
        Random random = new Random();
        boolean idDuplicado;

        do {
            char letra = (char) ('A' + random.nextInt(26));
            int numeros = random.nextInt(900) + 100; // de 100 a 999
            nuevoId = letra + String.valueOf(numeros);

            final String idFinal = nuevoId; // 💥 aquí hacemos copia final para usar en lambda
            idDuplicado = usuarios.stream().anyMatch(u -> u.getId().equals(idFinal));
        } while (idDuplicado);

        return nuevoId;
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
