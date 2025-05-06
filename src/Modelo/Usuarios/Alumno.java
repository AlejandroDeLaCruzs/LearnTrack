package Modelo.Usuarios;

public class Alumno extends Usuario {
    public Alumno(String id, String nombre, String correo, String contrasena) {
        super(id, nombre, correo, contrasena, "Alumno");
    }
}