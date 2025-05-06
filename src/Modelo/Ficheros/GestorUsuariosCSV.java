package Modelo.Ficheros;

import Modelo.Usuarios.*;
import java.io.*;
import java.util.*;

public class GestorUsuariosCSV {
    private static final String ARCHIVO = "data/usuarios.csv";

    public static List<Usuario> cargarUsuarios() {
        List<Usuario> usuarios = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(ARCHIVO))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] datos = linea.split(",");
                String id = datos[0];
                String nombre = datos[1];
                String correo = datos[2];
                String contrasena = datos[3];
                String rol = datos[4];

                switch (rol) {
                    case "Administrador":
                        usuarios.add(new Administrador(id, nombre, correo, contrasena));
                        break;
                    case "Profesor":
                        usuarios.add(new Profesor(id, nombre, correo, contrasena));
                        break;
                    case "Alumno":
                        usuarios.add(new Alumno(id, nombre, correo, contrasena));
                        break;
                }
            }
        } catch (IOException e) {
            System.err.println("Error al leer el archivo de usuarios: " + e.getMessage());
        }
        return usuarios;
    }

    public static void guardarUsuarios(List<Usuario> usuarios) {
        try (PrintWriter pw = new PrintWriter(new FileWriter(ARCHIVO))) {
            for (Usuario u : usuarios) {
                pw.println(String.join(",", u.getId(), u.getNombre(), u.getCorreo(), u.getContrasena(), u.getRol()));
            }
        } catch (IOException e) {
            System.err.println("Error al guardar los usuarios: " + e.getMessage());
        }
    }
}
