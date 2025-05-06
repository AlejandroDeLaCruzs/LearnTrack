package Controlador.ValidacionUsuarios;

import Modelo.Ficheros.GestorUsuariosCSV;
import Modelo.Usuarios.Usuario;
import java.util.List;

public class ValidacionLogin {
    public static String validar(String correo, String contrasena) {
        if (correo.isEmpty() && contrasena.isEmpty()) return "Rellene los datos";
        if (correo.isEmpty()) return "Rellene el correo";
        if (contrasena.isEmpty()) return "Rellene la contraseña";

        List<Usuario> usuarios = GestorUsuariosCSV.cargarUsuarios();
        for (Usuario u : usuarios) {
            if (u.getCorreo().equals(correo)) {
                if (u.getContrasena().equals(contrasena)) {
                    return u.getRol();
                } else {
                    return "Contraseña incorrecta";
                }
            }
        }
        return "El correo no existe";
    }
}