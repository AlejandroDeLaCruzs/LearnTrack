package Modelo.basededatos;
import java.io.*;

public class BaseDatos {

    public BaseDatos() {
        String RUTA_BASE_DATOS = "data/";
        File directorio = new File(RUTA_BASE_DATOS);
        if (!directorio.exists()) {
            if (directorio.mkdirs()) {
                System.out.println("Directorio 'data/' creado exitosamente.");
            } else {
                System.err.println("No se pudo crear el directorio 'datos/'. Verifica permisos.");
            }
        }
        String cursos = "data/cursos.csv";
        String usuarios = "data/usuarios.csv";
        String calificaciones = "data/calificaciones.csv";

        File directorioCursos = new File(cursos);
        File directorioUsers = new File(usuarios);
        File directorioCalificacion = new File(calificaciones);


        if (!directorioUsers.exists()) {
            try {
                BufferedWriter writer = new BufferedWriter(new FileWriter(usuarios, true));
                writer.write("");
                CrearAdmin();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (!directorioCursos.exists()) {
            try {
                BufferedWriter writer = new BufferedWriter(new FileWriter(directorioCursos));
                writer.write("");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    public void CrearAdmin() {
        String usuario = "admin@admin.com";
        String password = "a:admin";
        try (BufferedWriter bw = new BufferedWriter(new FileWriter("datos/usuarios.csv", true))) {
            bw.write("051,Administrador,admin@admin.com,admin1234,Administrador" + "\n");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
