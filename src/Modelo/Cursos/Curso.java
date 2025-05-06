package Modelo.Cursos;

public class Curso {
    private String id;
    private String nombre;
    private String idProfesorAsignado;

    public Curso(String id, String nombre, String idProfesorAsignado) {
        this.id = id;
        this.nombre = nombre;
        this.idProfesorAsignado = idProfesorAsignado;
    }

    public String getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getIdProfesorAsignado() {
        return idProfesorAsignado == null || idProfesorAsignado.isEmpty()
                ? "No hay profesor asignado" : idProfesorAsignado;
    }

    public void setIdProfesorAsignado(String idProfesorAsignado) {
        this.idProfesorAsignado = idProfesorAsignado;
    }
}
