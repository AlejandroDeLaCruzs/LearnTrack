package Modelo.Cursos;

public class Calificacion {
    private String idCurso;
    private String idAlumno;
    private String nombreAlumno;
    private String calificacion;

    public Calificacion(String idCurso, String idAlumno, String nombreAlumno, String calificacion) {
        this.idCurso = idCurso;
        this.idAlumno = idAlumno;
        this.nombreAlumno = nombreAlumno;
        this.calificacion = calificacion;
    }

    // Getters y Setters
    public String getIdCurso() { return idCurso; }
    public String getIdAlumno() { return idAlumno; }
    public String getNombreAlumno() { return nombreAlumno; }
    public String getCalificacion() { return calificacion; }

    public void setCalificacion(String calificacion) {
        this.calificacion = calificacion;
    }
}
