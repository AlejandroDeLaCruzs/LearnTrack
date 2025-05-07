package Vista.alumno;

import Modelo.Cursos.Calificacion;
import Modelo.Cursos.Curso;
import Modelo.Ficheros.GestorCalificacionesCSV;
import Modelo.Ficheros.GestorCursosCSV;


import javax.swing.*;
import java.awt.*;
import java.util.List;

public class CalificacionesView {
    private JFrame frame;
    private JPanel panelNotas;

    public void mostrar() {
        frame = new JFrame("Mis Calificaciones");
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel(new BorderLayout());

        JLabel titulo = new JLabel("Mis Calificaciones", SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 28));

        panel.add(titulo, BorderLayout.NORTH);

        panelNotas = new JPanel();
        panelNotas.setLayout(new BoxLayout(panelNotas, BoxLayout.Y_AXIS));
        panelNotas.setBorder(BorderFactory.createEmptyBorder(20, 250, 20, 250));

        JScrollPane scroll = new JScrollPane(panelNotas);
        scroll.setBorder(null);
        scroll.getVerticalScrollBar().setUnitIncrement(16);
        panel.add(scroll, BorderLayout.CENTER);

        JButton btnAtras = new JButton("Atrás");
        btnAtras.addActionListener(e -> {
            frame.dispose();
            new MenuEstudianteView().mostrar();
        });
        JPanel abajo = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        abajo.add(btnAtras);
        panel.add(abajo, BorderLayout.SOUTH);

        frame.setContentPane(panel);
        frame.setVisible(true);

        cargarNotas();
    }

    private void cargarNotas() {
        panelNotas.removeAll();

        JPanel header = new JPanel(new GridLayout(1, 3));
        header.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
        header.add(new JLabel("ID Curso"));
        header.add(new JLabel("Nombre Curso"));
        header.add(new JLabel("Calificación"));
        panelNotas.add(header);
        panelNotas.add(Box.createRigidArea(new Dimension(0, 10)));

        List<Calificacion> calificaciones = GestorCalificacionesCSV.cargarCalificaciones();
        List<Curso> cursos = GestorCursosCSV.cargarCursos();

        String idAlumno = Modelo.Sesion.obtenerUsuario().getId(); // Esto lo deberías definir tú
        for (Calificacion c : calificaciones) {
            if (!c.getIdAlumno().equals(idAlumno)) continue;

            JPanel fila = new JPanel(new GridLayout(1, 3));
            fila.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
            fila.setBackground(new Color(245, 245, 245));
            fila.setBorder(BorderFactory.createMatteBorder(1, 0, 1, 0, Color.GRAY));

            fila.add(new JLabel(c.getIdCurso()));

            String nombreCurso = cursos.stream()
                    .filter(cur -> cur.getId().equals(c.getIdCurso()))
                    .map(Curso::getNombre)
                    .findFirst()
                    .orElse("Desconocido");
            fila.add(new JLabel(nombreCurso));

            String nota = c.getCalificacion() == null ? "Sin calificar" : c.getCalificacion();
            fila.add(new JLabel(nota));

            panelNotas.add(fila);
            panelNotas.add(Box.createRigidArea(new Dimension(0, 5)));
        }

        panelNotas.revalidate();
        panelNotas.repaint();
    }
}
