package Modelo.Usuarios;

public class Profesor extends Usuario {
    public Profesor(String id, String nombre, String correo, String contrasena) {
        super(id, nombre, correo, contrasena, "Profesor");
    }
}