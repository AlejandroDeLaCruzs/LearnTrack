package Vista.administrador;

import Controlador.ValidacionCursos.ValidacionCrearCurso;
import Controlador.ValidacionCursos.ValidacionAsignarProfesor;
import Controlador.ValidacionCursos.ListaCursosController;
import Modelo.Cursos.Curso;
import Modelo.Ficheros.GestorCursosCSV;
import Modelo.Usuarios.Usuario;
import Modelo.Ficheros.GestorUsuariosCSV;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.stream.Collectors;

public class CursosView {
    private JFrame frame;
    private JPanel listaCursos;
    private final ListaCursosController listaCursosController = new ListaCursosController();
    private final ValidacionCrearCurso validacionCrearCurso = new ValidacionCrearCurso();


    public void mostrar() {
        frame = new JFrame("Gestión de Cursos");
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel(new BorderLayout());
        JLabel titulo = new JLabel("Gestión de Cursos", SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 28));
        panel.add(titulo, BorderLayout.NORTH);

        JButton btnAgregarCurso = new JButton("Agregar Curso");
        btnAgregarCurso.addActionListener(e -> abrirVentanaAgregarCurso());

        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.add(titulo, BorderLayout.CENTER);
        topPanel.add(btnAgregarCurso, BorderLayout.EAST);
        panel.add(topPanel, BorderLayout.NORTH);

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

    private void abrirVentanaAgregarCurso() {
        JDialog dialog = new JDialog(frame, "Agregar Nuevo Curso", true);
        dialog.setSize(400, 250);
        dialog.setLayout(new GridBagLayout());
        dialog.setLocationRelativeTo(frame);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel lblId = new JLabel("ID del Curso:");
        String idGenerado = generarIdCursoUnico();
        JTextField campoId = new JTextField(idGenerado, 20);

        JLabel lblNombre = new JLabel("Nombre del Curso:");
        JTextField campoNombre = new JTextField(20);

        JLabel errorLabel = new JLabel("");
        errorLabel.setForeground(Color.RED);

        JButton btnAceptar = new JButton("Aceptar");
        JButton btnCancelar = new JButton("Cancelar");

        gbc.gridx = 0;
        gbc.gridy = 0;
        dialog.add(lblId, gbc);
        gbc.gridx = 1;
        dialog.add(campoId, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        dialog.add(lblNombre, gbc);
        gbc.gridx = 1;
        dialog.add(campoNombre, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        dialog.add(errorLabel, gbc);

        JPanel botones = new JPanel();
        botones.add(btnAceptar);
        botones.add(btnCancelar);

        gbc.gridy = 3;
        dialog.add(botones, gbc);

        btnAceptar.addActionListener(e -> {
            String id = campoId.getText().trim();
            String nombre = campoNombre.getText().trim();
            String error = validacionCrearCurso.validar(id, nombre);
            if (error != null) {
                errorLabel.setText(error);
                return;
            }

            List<Curso> cursos = GestorCursosCSV.cargarCursos();

            // Validación: no repetir nombre
            boolean nombreExiste = cursos.stream()
                    .anyMatch(c -> c.getNombre().equalsIgnoreCase(nombre));
            if (nombreExiste) {
                errorLabel.setText("Nombre ya existente");
                return;
            }

            cursos.add(new Curso(id, nombre, ""));
            GestorCursosCSV.guardarCursos(cursos);
            dialog.dispose();
            actualizarLista();
        });


        btnCancelar.addActionListener(e -> dialog.dispose());

        dialog.setVisible(true);
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

        List<Curso> cursos = listaCursosController.obtenerCursos();
        for (Curso curso : cursos) {
            JPanel row = new JPanel(new GridLayout(1, 4));
            row.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
            row.setBackground(new Color(245, 245, 245));
            row.setBorder(BorderFactory.createMatteBorder(1, 0, 1, 0, Color.GRAY));

            row.add(new JLabel(curso.getId()));
            row.add(new JLabel(curso.getNombre()));

            String idProfesor = curso.getIdProfesorAsignado();
            String nombreProfesor = "No hay profesor asignado";
            if (!idProfesor.equals("No hay profesor asignado")) {
                List<Usuario> usuarios = GestorUsuariosCSV.cargarUsuarios();
                for (Usuario u : usuarios) {
                    if (u.getNombre().equals(idProfesor)) {
                        nombreProfesor = u.getNombre();  // ya es el nombre
                        break;
                    }
                }
            }

            row.add(new JLabel(nombreProfesor));

            JPanel acciones = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));

            JButton btnAsignar = new JButton(idProfesor.equals("No hay profesor asignado") ? "Asignar profesor" : "Cambiar profesor");
            btnAsignar.addActionListener(e -> asignarProfesor(curso));
            acciones.add(btnAsignar);

            JButton btnEliminar = new JButton("Eliminar");
            btnEliminar.addActionListener(e -> {
                int confirm = JOptionPane.showConfirmDialog(frame,
                        "¿Estás seguro de que deseas eliminar el curso?",
                        "Confirmar eliminación", JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) {
                    listaCursosController.eliminarCurso(curso.getId());
                    actualizarLista();
                }
            });
            acciones.add(btnEliminar);

            row.add(acciones);
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
                .collect(Collectors.toList());

        DefaultListModel<String> model = new DefaultListModel<>();
        for (Usuario prof : profesoresDisponibles) {
            model.addElement(prof.getNombre());
        }

        JList<String> lista = new JList<>(model);
        JScrollPane scroll = new JScrollPane(lista);
        dialog.add(scroll, BorderLayout.CENTER);

        JLabel errorLabel = new JLabel("");
        errorLabel.setForeground(Color.RED);
        dialog.add(errorLabel, BorderLayout.NORTH);

        JPanel botones = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton aceptar = new JButton("Aceptar");
        JButton cancelar = new JButton("Cancelar");
        botones.add(aceptar);
        botones.add(cancelar);
        dialog.add(botones, BorderLayout.SOUTH);

        aceptar.addActionListener(e -> {
            String seleccionado = lista.getSelectedValue();
            if (seleccionado != null) {
                // Verificar si ya está asignado a este curso
                if (seleccionado.equals(curso.getIdProfesorAsignado())) {
                    errorLabel.setText("Profesor ya asignado a este curso");
                    return;
                }

                // Verificar si el profesor está asignado a otro curso
                boolean yaAsignado = GestorCursosCSV.cargarCursos().stream()
                        .anyMatch(c -> c.getIdProfesorAsignado().equals(seleccionado) && !c.getId().equals(curso.getId()));

                if (yaAsignado) {
                    errorLabel.setText("Profesor ya asignado a otro curso");
                    return;
                }

                // Asignar el profesor
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

    private String generarIdCursoUnico() {
        List<Curso> existentes = GestorCursosCSV.cargarCursos();

        String maxId = existentes.stream()
                .map(Curso::getId)
                .filter(id -> id.matches("[A-Z]{2}[0-9]{3}"))
                .max(String::compareTo)
                .orElse("AA000");

        char letra1 = maxId.charAt(0);
        char letra2 = maxId.charAt(1);
        int numero = Integer.parseInt(maxId.substring(2));

        if (numero == 999) {
            numero = 0;
            if (letra2 == 'Z') {
                letra2 = 'A';
                letra1++;
            } else {
                letra2++;
            }
        } else {
            numero++;
        }

        return "" + letra1 + letra2 + String.format("%03d", numero);
    }
}