package Vista.alumno;

import Modelo.Cursos.Curso;
import Modelo.Ficheros.GestorCursosCSV;

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
                // Aquí puedes implementar tu lógica personalizada:
                // Por ejemplo, guardar matrícula, actualizar CSV, etc.

                JOptionPane.showMessageDialog(frame,
                        "Inscripción confirmada a: " + curso.getNombre(),
                        "Información",
                        JOptionPane.INFORMATION_MESSAGE);
            });

            row.add(inscribirseBtn);
            listaCursos.add(row);
            listaCursos.add(Box.createRigidArea(new Dimension(0, 5)));
        }

        listaCursos.revalidate();
        listaCursos.repaint();
    }
}
