package Vista.administrador;

import Modelo.Cursos.Curso;
import Modelo.Ficheros.GestorCursosCSV;
import Modelo.Ficheros.GestorUsuariosCSV;
import Modelo.Usuarios.Usuario;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.stream.Collectors;

public class CursosView {
    private JFrame frame;
    private JPanel listaCursos;

    public void mostrar() {
        frame = new JFrame("Gestión de Cursos");
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel(new BorderLayout());

        JLabel titulo = new JLabel("Gestión de Cursos", SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 28));
        panel.add(titulo, BorderLayout.NORTH);

        listaCursos = new JPanel();
        listaCursos.setLayout(new BoxLayout(listaCursos, BoxLayout.Y_AXIS));
        listaCursos.setBorder(BorderFactory.createEmptyBorder(20, 200, 20, 200));

        actualizarLista();

        JScrollPane scroll = new JScrollPane(listaCursos);
        scroll.setBorder(null);
        scroll.getVerticalScrollBar().setUnitIncrement(16);
        panel.add(scroll, BorderLayout.CENTER);

        JButton btnAtras = new JButton("Atrás");
        btnAtras.addActionListener(e -> {
            frame.dispose();
            new MenuAdministradorView().mostrar();
        });
        JPanel abajo = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        abajo.add(btnAtras);
        panel.add(abajo, BorderLayout.SOUTH);

        frame.setContentPane(panel);
        frame.setVisible(true);
    }

    private void actualizarLista() {
        listaCursos.removeAll();

        JPanel header = new JPanel(new GridLayout(1, 4));
        header.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
        header.add(new JLabel("ID"));
        header.add(new JLabel("Nombre"));
        header.add(new JLabel("Profesor Asignado"));
        header.add(new JLabel("Acción"));
        listaCursos.add(header);
        listaCursos.add(Box.createRigidArea(new Dimension(0, 10)));

        List<Curso> cursos = GestorCursosCSV.cargarCursos();
        for (Curso curso : cursos) {
            JPanel row = new JPanel(new GridLayout(1, 4));
            row.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
            row.setBackground(new Color(245, 245, 245));
            row.setBorder(BorderFactory.createMatteBorder(1, 0, 1, 0, Color.GRAY));

            row.add(new JLabel(curso.getId()));
            row.add(new JLabel(curso.getNombre()));
            String profesor = curso.getIdProfesorAsignado();
            row.add(new JLabel(profesor));

            JButton btnAsignar = new JButton("Agregar profesor");
            btnAsignar.setEnabled(profesor.equals("No hay profesor asignado"));
            btnAsignar.addActionListener(e -> asignarProfesor(curso));
            row.add(btnAsignar);

            listaCursos.add(row);
            listaCursos.add(Box.createRigidArea(new Dimension(0, 5)));
        }

        listaCursos.revalidate();
        listaCursos.repaint();
    }

    private void asignarProfesor(Curso curso) {
        JDialog dialog = new JDialog(frame, "Seleccionar Profesor", true);
        dialog.setSize(400, 300);
        dialog.setLocationRelativeTo(frame);
        dialog.setLayout(new BorderLayout());

        List<Usuario> profesoresDisponibles = GestorUsuariosCSV.cargarUsuarios().stream()
                .filter(u -> u.getRol().equals("Profesor"))
                .filter(u -> !GestorCursosCSV.profesorYaAsignado(u.getNombre()))
                .collect(Collectors.toList());

        DefaultListModel<String> model = new DefaultListModel<>();
        for (Usuario prof : profesoresDisponibles) {
            model.addElement(prof.getNombre());
        }

        JList<String> lista = new JList<>(model);
        JScrollPane scroll = new JScrollPane(lista);
        dialog.add(scroll, BorderLayout.CENTER);

        JPanel botones = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton aceptar = new JButton("Aceptar");
        JButton cancelar = new JButton("Cancelar");
        botones.add(aceptar);
        botones.add(cancelar);
        dialog.add(botones, BorderLayout.SOUTH);

        aceptar.addActionListener(e -> {
            String seleccionado = lista.getSelectedValue();
            if (seleccionado != null) {
                curso.setIdProfesorAsignado(seleccionado);
                List<Curso> cursos = GestorCursosCSV.cargarCursos();
                cursos.stream().filter(c -> c.getId().equals(curso.getId()))
                        .forEach(c -> c.setIdProfesorAsignado(seleccionado));
                GestorCursosCSV.guardarCursos(cursos);
                dialog.dispose();
                actualizarLista();
            }
        });

        cancelar.addActionListener(e -> dialog.dispose());

        dialog.setVisible(true);
    }
}
