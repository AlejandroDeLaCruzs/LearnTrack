package Modelo;

import Modelo.Usuarios.Usuario;

public class Sesion {
    private static Usuario usuarioActual;

    public static void establecerUsuario(Usuario u) {
        usuarioActual = u;
    }

    public static Usuario obtenerUsuario() {
        return usuarioActual;
    }
}