package Modelo.Ficheros;

import Modelo.Cursos.Calificacion;

import java.io.*;
import java.util.*;

public class GestorCalificacionesCSV {
    private static final String RUTA = "data/calificaciones.csv";

    public static List<Calificacion> cargarCalificaciones() {
        List<Calificacion> lista = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(RUTA))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] partes = linea.split(",", -1);
                if (partes.length >= 4) {
                    lista.add(new Calificacion(partes[0], partes[1], partes[2], partes[3]));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return lista;
    }

    public static void guardarCalificaciones(List<Calificacion> calificaciones) {
        try (PrintWriter pw = new PrintWriter(new FileWriter(RUTA))) {
            for (Calificacion c : calificaciones) {
                pw.println(c.getIdCurso() + "," + c.getIdAlumno() + "," + c.getNombreAlumno() + "," + c.getCalificacion());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
