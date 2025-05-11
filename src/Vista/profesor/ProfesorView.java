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
    private String nombreCurso;
    private List<Calificacion> calificaciones;
    private Map<String, JTextField> camposNotas = new HashMap<>();
    private Map<String, JLabel> mensajesError = new HashMap<>();

    public ProfesorView(String idCurso) {
        this.idCurso = idCurso;
        this.nombreCurso = Modelo.Ficheros.GestorCursosCSV.obtenerNombreCursoPorId(idCurso);
    }

    public void mostrar() {
        frame = new JFrame("Gestión de Calificaciones");
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panelPrincipal = new JPanel(new BorderLayout());
        JLabel titulo = new JLabel("Alumnos del Curso: " + nombreCurso, SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 26));
        panelPrincipal.add(titulo, BorderLayout.NORTH);

        calificaciones = GestorCalificacionesCSV.cargarCalificaciones().stream()
                .filter(c -> c.getIdCurso().equals(idCurso))
                .toList();

        JPanel panelTabla = new JPanel();
        panelTabla.setLayout(new BoxLayout(panelTabla, BoxLayout.Y_AXIS));
        panelTabla.setBorder(BorderFactory.createEmptyBorder(30, 400, 30, 400));

        // Encabezado con 3 columnas
        JPanel header = new JPanel(new GridLayout(1, 3));
        header.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
        header.setBackground(new Color(220, 220, 220));
        header.add(new JLabel("ID del Alumno", SwingConstants.CENTER));
        header.add(new JLabel("Nombre", SwingConstants.CENTER));
        header.add(new JLabel("Calificación", SwingConstants.CENTER));
        panelTabla.add(header);
        panelTabla.add(Box.createRigidArea(new Dimension(0, 10)));

        for (Calificacion c : calificaciones) {
            JPanel filaTabla = new JPanel(new GridLayout(1, 3));
            filaTabla.setMaximumSize(new Dimension(Integer.MAX_VALUE, 60));
            filaTabla.setBackground(new Color(245, 245, 245));
            filaTabla.setBorder(BorderFactory.createMatteBorder(1, 0, 1, 0, Color.GRAY));

            JLabel idLabel = new JLabel(c.getIdAlumno(), SwingConstants.CENTER);
            JLabel nombreLabel = new JLabel(c.getNombreAlumno(), SwingConstants.CENTER);

            JTextField campo = new JTextField(c.getCalificacion(), 5);
            campo.setHorizontalAlignment(JTextField.CENTER);
            campo.setPreferredSize(new Dimension(80, 30));
            camposNotas.put(c.getIdAlumno(), campo);

            JLabel mensajeError = new JLabel("Calificación no válida (0 - 10)", SwingConstants.CENTER);
            mensajeError.setForeground(Color.RED);
            mensajeError.setVisible(false);
            mensajesError.put(c.getIdAlumno(), mensajeError);

            // Panel vertical para calificación + error
            JPanel celdaCalificacion = new JPanel(new BorderLayout());
            celdaCalificacion.setOpaque(false);
            celdaCalificacion.add(campo, BorderLayout.CENTER);
            celdaCalificacion.add(mensajeError, BorderLayout.SOUTH);

            filaTabla.add(idLabel);
            filaTabla.add(nombreLabel);
            filaTabla.add(celdaCalificacion);

            panelTabla.add(filaTabla);
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
        boolean hayErrores = false;

        // Limpiar errores anteriores
        for (JLabel errorLabel : mensajesError.values()) {
            errorLabel.setVisible(false);
        }

        // Procesar cada calificación
        for (Calificacion c : calificaciones) {
            JTextField campo = camposNotas.get(c.getIdAlumno());
            JLabel mensajeError = mensajesError.get(c.getIdAlumno());

            String textoNota = campo.getText().trim();

            if (textoNota.isEmpty() || textoNota.equals("-")) {
                textoNota = "-";
                c.setCalificacion(textoNota);
                continue;
            }

            double valor;
            boolean notaValida = true;

            try {
                valor = Double.parseDouble(textoNota);
                if (valor < 0 || valor > 10) {
                    notaValida = false;
                }
            } catch (NumberFormatException e) {
                notaValida = false;
            }

            if (!notaValida) {
                hayErrores = true;
                mensajeError.setVisible(true);
            } else {
                mensajeError.setVisible(false);
                c.setCalificacion(textoNota);
            }
        }

        // Guardar solo las calificaciones válidas
        List<Calificacion> todas = GestorCalificacionesCSV.cargarCalificaciones();
        for (Calificacion c : todas) {
            if (c.getIdCurso().equals(idCurso)) {
                Optional<Calificacion> nueva = calificaciones.stream()
                        .filter(n -> n.getIdAlumno().equals(c.getIdAlumno()))
                        .findFirst();

                if (nueva.isPresent()) {
                    String nuevaNota = nueva.get().getCalificacion();
                    if (!mensajesError.get(c.getIdAlumno()).isVisible()) {
                        c.setCalificacion(nuevaNota);
                    }
                }
            }
        }

        GestorCalificacionesCSV.guardarCalificaciones(todas);

        if (hayErrores) {
            JOptionPane.showMessageDialog(frame, "Algunas calificaciones no se guardaron por ser inválidas. Revise los errores.", "Error de validación", JOptionPane.WARNING_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(frame, "Calificaciones guardadas correctamente.");
        }
    }
}