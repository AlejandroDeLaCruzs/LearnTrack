package Modelo.Ficheros;

import Modelo.Cursos.Curso;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class GestorCursosCSV {
    private static final String RUTA = "data/cursos.csv";

    public static List<Curso> cargarCursos() {
        List<Curso> cursos = new ArrayList<>();
        File archivo = new File(RUTA);
        if (!archivo.exists()) return cursos;

        try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] datos = linea.split(",");
                if (datos.length >= 2) {
                    String id = datos[0];
                    String nombre = datos[1];
                    String profesorId = datos.length >= 3 ? datos[2] : "";
                    cursos.add(new Curso(id, nombre, profesorId));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return cursos;
    }

    public static void guardarCursos(List<Curso> cursos) {
        try (PrintWriter pw = new PrintWriter(new FileWriter(RUTA))) {
            for (Curso c : cursos) {
                pw.println(c.getId() + "," + c.getNombre() + "," + (c.getIdProfesorAsignado() == null ? "" : c.getIdProfesorAsignado()));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static boolean profesorYaAsignado(String nombreProfesor) {
        return cargarCursos().stream()
                .anyMatch(c -> nombreProfesor.equals(c.getIdProfesorAsignado()));
    }

}

