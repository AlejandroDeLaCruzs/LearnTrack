package Modelo.Ficheros;

import Modelo.Cursos.Curso;
import Modelo.Usuarios.Usuario;
import Vista.LoginView;
import Vista.profesor.ProfesorView;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

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

    public static boolean profesorYaAsignado(String nombre, String cursoExcluidoId) {
        List<Curso> cursos = cargarCursos();
        return cursos.stream()
                .anyMatch(c -> c.getIdProfesorAsignado().equals(nombre) && !c.getId().equals(cursoExcluidoId));
    }

    public static String generarNuevoId() {
        Random random = new Random();
        String letras = "";
        for (int i = 0; i < 2; i++) {
            letras += (char) (random.nextInt(26) + 'A');
        }
        String numeros = String.format("%03d", random.nextInt(1000));
        return letras + numeros;
    }

    public static String obtenerCursoPorProfesorPorNombre(String nombreProfesor) {
        List<Curso> cursos = cargarCursos();
        for (Curso c : cursos) {
            if (nombreProfesor.equalsIgnoreCase(c.getIdProfesorAsignado())) {
                return c.getId();
            }
        }
        return null;
    }

}