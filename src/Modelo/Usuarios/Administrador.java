package Modelo.Usuarios;

public class Administrador extends Usuario {
    public Administrador(String id, String nombre, String correo, String contrasena) {
        super(id, nombre, correo, contrasena, "Administrador");
    }
}

