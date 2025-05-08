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

        calificaciones = GestorCalificacionesCSV.cargarCalificaciones().stream()
                .filter(c -> c.getIdCurso().equals(idCurso))
                .toList();

        JPanel panelTabla = new JPanel();
        panelTabla.setLayout(new BoxLayout(panelTabla, BoxLayout.Y_AXIS));
        panelTabla.setBorder(BorderFactory.createEmptyBorder(30, 400, 30, 400)); // reduce ancho general

        JPanel header = new JPanel(new GridLayout(1, 3));
        header.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
        header.setBackground(new Color(220, 220, 220));
        header.add(new JLabel("ID del Alumno", SwingConstants.CENTER));
        header.add(new JLabel("Nombre", SwingConstants.CENTER));
        header.add(new JLabel("Calificación", SwingConstants.CENTER));
        panelTabla.add(header);
        panelTabla.add(Box.createRigidArea(new Dimension(0, 10)));

        for (Calificacion c : calificaciones) {
            JPanel fila = new JPanel(new GridLayout(1, 3));
            fila.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
            fila.setBackground(new Color(245, 245, 245));
            fila.setBorder(BorderFactory.createMatteBorder(1, 0, 1, 0, Color.GRAY));

            JLabel idLabel = new JLabel(c.getIdAlumno(), SwingConstants.CENTER);
            JLabel nombreLabel = new JLabel(c.getNombreAlumno(), SwingConstants.CENTER);

            JTextField campo = new JTextField(c.getCalificacion(), 5);
            campo.setHorizontalAlignment(JTextField.CENTER);
            campo.setPreferredSize(new Dimension(80, 30));

            camposNotas.put(c.getIdAlumno(), campo);

            fila.add(idLabel);
            fila.add(nombreLabel);
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
        boolean hayErrores = false;

        // Primero, quitamos cualquier mensaje previo
        Component[] filas = ((JPanel)((JScrollPane)frame.getContentPane().getComponent(1)).getViewport().getView()).getComponents();
        for (Component comp : filas) {
            if (comp instanceof JPanel fila && fila.getComponentCount() == 4) {
                fila.remove(3); // quitar mensaje de error anterior
            }
        }

        for (Calificacion c : calificaciones) {
            String textoNota = camposNotas.get(c.getIdAlumno()).getText().trim();
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
                JPanel fila = (JPanel) camposNotas.get(c.getIdAlumno()).getParent();
                JLabel error = new JLabel("Calificación no válida (0 - 10)");
                error.setForeground(Color.RED);
                fila.add(error); // Añadimos el mensaje de error como 4º componente
                fila.revalidate();
                fila.repaint();
            } else {
                c.setCalificacion(textoNota);
            }
        }

        if (hayErrores) {
            JOptionPane.showMessageDialog(frame, "Corrige las calificaciones no válidas antes de guardar.", "Error de validación", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Sobrescribimos todas las calificaciones válidas
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
