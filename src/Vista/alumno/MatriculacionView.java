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
        header.add(new JLabel("ID Curso"));
        header.add(new JLabel("Nombre Asignatura"));
        header.add(new JLabel("Profesor"));
        header.add(new JLabel("Acción"));
        listaCursos.add(header);
        listaCursos.add(Box.createRigidArea(new Dimension(0, 10)));

        List<Curso> cursos = GestorCursosCSV.cargarCursos();
        for (Curso curso : cursos) {
            JPanel row = new JPanel(new GridLayout(1, 4));
            row.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
            row.setBackground(new Color(240, 240, 240));
            row.setBorder(BorderFactory.createMatteBorder(1, 0, 1, 0, Color.GRAY));

            row.add(new JLabel(curso.getId()));
            row.add(new JLabel(curso.getNombre()));
            row.add(new JLabel(curso.getIdProfesorAsignado()));

            JButton inscribirseBtn = new JButton("Inscribirse");
            inscribirseBtn.setPreferredSize(new Dimension(150, 30));
            inscribirseBtn.setFont(new Font("Arial", Font.BOLD, 13));
            inscribirseBtn.setFocusable(false);

            inscribirseBtn.addActionListener(e -> {
                // Obtener el ID del alumno (puedes obtenerlo desde una sesión o contexto)
                String idAlumno = Modelo.Sesion.obtenerUsuario().getId(); // Asegúrate de obtener el ID del alumno actual (puede ser un valor dinámico)

                // Obtener el nombre del alumno (puedes obtenerlo desde una sesión o contexto)
                String nombreAlumno = Modelo.Sesion.obtenerUsuario().getNombre(); // Similar al ID, este valor debe ser obtenido dinámicamente.

                // Crear el objeto Calificación con calificación 'null' para el nuevo inscrito
                boolean exito = ValidacionMatricula.inscribirAlumno(curso.getId(), idAlumno, nombreAlumno);

                if (exito) {
                    JOptionPane.showMessageDialog(frame,
                            "Inscripción confirmada a: " + curso.getNombre(),
                            "Información",
                            JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(frame,
                            "Ya estás inscrito en: " + curso.getNombre(),
                            "Aviso",
                            JOptionPane.WARNING_MESSAGE);
                }
            });
            row.add(inscribirseBtn);
            listaCursos.add(row);
            listaCursos.add(Box.createRigidArea(new Dimension(0, 5)));
        }

        listaCursos.revalidate();
        listaCursos.repaint();
    }
}
