package Vista.alumno;

import Controlador.ValidacionMatricula.ValidacionMatricula;
import Modelo.Cursos.Curso;
import Modelo.Cursos.Calificacion;
import Modelo.Ficheros.GestorCalificacionesCSV;
import Modelo.Ficheros.GestorCursosCSV;
import Modelo.Ficheros.GestorUsuariosCSV;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class MatriculacionView {
    private JFrame frame;
    private JPanel listaCursos;

    public void mostrar() {
        frame = new JFrame("Matriculación a Cursos");
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel(new BorderLayout());

        JLabel titulo = new JLabel("Matriculación a Cursos", SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 28));
        panel.add(titulo, BorderLayout.NORTH);

        listaCursos = new JPanel();
        listaCursos.setLayout(new BoxLayout(listaCursos, BoxLayout.Y_AXIS));
        listaCursos.setBorder(BorderFactory.createEmptyBorder(20, 250, 20, 250));

        JScrollPane scroll = new JScrollPane(listaCursos);
        scroll.setBorder(null);
        scroll.getVerticalScrollBar().setUnitIncrement(16);
        panel.add(scroll, BorderLayout.CENTER);

        JPanel botones = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton btnAtras = new JButton("Atrás");
        btnAtras.addActionListener(e -> {
            frame.dispose();
            new MenuEstudianteView().mostrar();
        });
        botones.add(btnAtras);
        panel.add(botones, BorderLayout.SOUTH);

        frame.setContentPane(panel);
        frame.setVisible(true);

        actualizarListaCursos();
    }

    private void actualizarListaCursos() {
        listaCursos.removeAll();

        JPanel header = new JPanel(new GridLayout(1, 4));
        header.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
        header.setBackground(new Color(220, 220, 220));
        header.add(new JLabel("ID Curso"));
        header.add(new JLabel("Nombre Asignatura"));
        header.add(new JLabel("Profesor"));
        header.add(new JLabel("Acción"));
        listaCursos.add(header);
        listaCursos.add(Box.createRigidArea(new Dimension(0, 10)));

        List<Curso> cursos = GestorCursosCSV.cargarCursos();
        List<Calificacion> calificaciones = GestorCalificacionesCSV.cargarCalificaciones();

        String idAlumno = Modelo.Sesion.obtenerUsuario().getId();
        String nombreAlumno = Modelo.Sesion.obtenerUsuario().getNombre();

        for (Curso curso : cursos) {
            JPanel row = new JPanel(new GridLayout(1, 4));
            row.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
            row.setBackground(new Color(245, 245, 245));
            row.setBorder(BorderFactory.createMatteBorder(1, 0, 1, 0, Color.GRAY));

            row.add(new JLabel(curso.getId()));
            row.add(new JLabel(curso.getNombre()));
            row.add(new JLabel(curso.getIdProfesorAsignado()));

            boolean yaInscrito = calificaciones.stream()
                    .anyMatch(c -> c.getIdCurso().equals(curso.getId()) && c.getIdAlumno().equals(idAlumno));

            JButton accionBtn = new JButton(yaInscrito ? "Desinscribirse" : "Inscribirse");
            accionBtn.setFont(new Font("Arial", Font.BOLD, 13));
            accionBtn.setFocusable(false);

            accionBtn.addActionListener(e -> {
                if (yaInscrito) {
                    calificaciones.removeIf(c -> c.getIdCurso().equals(curso.getId()) && c.getIdAlumno().equals(idAlumno));
                    GestorCalificacionesCSV.guardarCalificaciones(calificaciones);

                    JOptionPane.showMessageDialog(frame,
                            "Te has desinscrito de: " + curso.getNombre(),
                            "Información",
                            JOptionPane.INFORMATION_MESSAGE);
                } else {
                    Calificacion nueva = new Calificacion(curso.getId(), idAlumno, nombreAlumno, "-");
                    calificaciones.add(nueva);
                    GestorCalificacionesCSV.guardarCalificaciones(calificaciones);

                    JOptionPane.showMessageDialog(frame,
                            "Inscripción confirmada a: " + curso.getNombre(),
                            "Información",
                            JOptionPane.INFORMATION_MESSAGE);
                }

                actualizarListaCursos(); // Recargar vista
            });

            row.add(accionBtn);
            listaCursos.add(row);
            listaCursos.add(Box.createRigidArea(new Dimension(0, 5)));
        }

        listaCursos.revalidate();
        listaCursos.repaint();
    }

}
