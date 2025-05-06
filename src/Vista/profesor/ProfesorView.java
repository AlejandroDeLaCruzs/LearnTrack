package Vista.profesor;

import Modelo.Cursos.Calificacion;
import Modelo.Ficheros.GestorCalificacionesCSV;
import Vista.LoginView;

import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.util.List;

public class ProfesorView {
    private JFrame frame;
    private String idCurso;
    private List<Calificacion> calificaciones;
    private Map<String, JTextField> camposNotas = new HashMap<>();

    public ProfesorView(String idCurso) {
        this.idCurso = idCurso;
    }

    public void mostrar() {
        frame = new JFrame("Gestión de Calificaciones");
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panelPrincipal = new JPanel(new BorderLayout());
        JLabel titulo = new JLabel("Alumnos del curso: " + idCurso, SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 26));
        panelPrincipal.add(titulo, BorderLayout.NORTH);

        // Cargar calificaciones del curso
        calificaciones = GestorCalificacionesCSV.cargarCalificaciones().stream()
                .filter(c -> c.getIdCurso().equals(idCurso))
                .toList();

        JPanel panelTabla = new JPanel();
        panelTabla.setLayout(new BoxLayout(panelTabla, BoxLayout.Y_AXIS));
        panelTabla.setBorder(BorderFactory.createEmptyBorder(30, 200, 30, 200));

        JPanel header = new JPanel(new GridLayout(1, 3));
        header.add(new JLabel("ID del Alumno"));
        header.add(new JLabel("Nombre"));
        header.add(new JLabel("Calificación"));
        panelTabla.add(header);
        panelTabla.add(Box.createRigidArea(new Dimension(0, 10)));

        for (Calificacion c : calificaciones) {
            JPanel fila = new JPanel(new GridLayout(1, 3));
            fila.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));

            fila.add(new JLabel(c.getIdAlumno()));
            fila.add(new JLabel(c.getNombreAlumno()));

            JTextField campo = new JTextField(c.getCalificacion(), 5);
            camposNotas.put(c.getIdAlumno(), campo);
            fila.add(campo);

            panelTabla.add(fila);
            panelTabla.add(Box.createRigidArea(new Dimension(0, 5)));
        }

        JScrollPane scroll = new JScrollPane(panelTabla);
        scroll.setBorder(null);
        scroll.getVerticalScrollBar().setUnitIncrement(16);
        panelPrincipal.add(scroll, BorderLayout.CENTER);

        JPanel botones = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton guardar = new JButton("Guardar calificaciones");
        JButton volver = new JButton("Volver");

        guardar.addActionListener(e -> guardarCambios());
        volver.addActionListener(e -> {
            frame.dispose();
            new LoginView().mostrar();
        });

        botones.add(guardar);
        botones.add(volver);
        panelPrincipal.add(botones, BorderLayout.SOUTH);

        frame.setContentPane(panelPrincipal);
        frame.setVisible(true);
    }

    private void guardarCambios() {
        for (Calificacion c : calificaciones) {
            String nuevaNota = camposNotas.get(c.getIdAlumno()).getText().trim();
            c.setCalificacion(nuevaNota);
        }

        // Sobrescribimos todas las calificaciones
        List<Calificacion> todas = GestorCalificacionesCSV.cargarCalificaciones();
        for (Calificacion c : todas) {
            if (c.getIdCurso().equals(idCurso)) {
                Optional<Calificacion> nueva = calificaciones.stream()
                        .filter(n -> n.getIdAlumno().equals(c.getIdAlumno()))
                        .findFirst();
                nueva.ifPresent(cal -> c.setCalificacion(cal.getCalificacion()));
            }
        }

        GestorCalificacionesCSV.guardarCalificaciones(todas);
        JOptionPane.showMessageDialog(frame, "Calificaciones guardadas correctamente.");
    }
}
