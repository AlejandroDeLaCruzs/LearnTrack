package Vista.administrador;

import Controlador.ValidacionUsuarios.UsuariosController;
import Modelo.Usuarios.Usuario;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.List;

public class UsuariosView {
    private JFrame frame;
    private JPanel listaUsuarios;
    private final UsuariosController controlador = new UsuariosController();

    public void mostrar() {
        frame = new JFrame("Registro de Usuarios");
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel(new BorderLayout());

        JLabel titulo = new JLabel("Registro de Usuarios", SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 28));
        panel.add(titulo, BorderLayout.NORTH);

        listaUsuarios = new JPanel();
        listaUsuarios.setLayout(new BoxLayout(listaUsuarios, BoxLayout.Y_AXIS));

        actualizarLista();

        JPanel contenedorCentral = new JPanel(new FlowLayout(FlowLayout.CENTER));
        contenedorCentral.add(listaUsuarios);

        JScrollPane scroll = new JScrollPane(contenedorCentral,
                JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scroll.setBorder(null);
        scroll.getVerticalScrollBar().setUnitIncrement(16);
        panel.add(scroll, BorderLayout.CENTER);

        JPanel botones = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton btnAtras = new JButton("Atrás");
        JButton btnAgregar = new JButton("Agregar Usuario");

        btnAtras.addActionListener(e -> {
            frame.dispose();
            new MenuAdministradorView().mostrar();  // Se mantiene pantalla completa
        });

        btnAgregar.addActionListener(e -> mostrarFormularioAgregar());

        botones.add(btnAgregar);
        botones.add(btnAtras);

        panel.add(botones, BorderLayout.SOUTH);
        frame.setContentPane(panel);
        frame.setVisible(true);
    }

    private void actualizarLista() {
        listaUsuarios.removeAll();

        // Header con columnas separadas
        JPanel header = new JPanel(new GridLayout(1, 5, 30, 0)); // Un espaciado de 30 entre columnas
        header.setMaximumSize(new Dimension(1000, 30)); // Ajusta el tamaño máximo de la cabecera
        header.add(new JLabel("ID", SwingConstants.CENTER));
        header.add(new JLabel("Nombre", SwingConstants.CENTER));
        header.add(new JLabel("Correo", SwingConstants.CENTER));
        header.add(new JLabel("Rol", SwingConstants.CENTER));
        header.add(new JLabel("Acción", SwingConstants.CENTER));
        listaUsuarios.add(header);
        listaUsuarios.add(Box.createRigidArea(new Dimension(0, 10))); // Espacio entre el header y las filas de usuarios

        List<Usuario> usuarios = controlador.obtenerUsuarios();
        for (Usuario u : usuarios) {
            JPanel row = new JPanel(new GridLayout(1, 5, 30, 0));  // Espaciado horizontal de 30 entre columnas
            row.setMaximumSize(new Dimension(1000, 40)); // Ajusta el tamaño de cada fila
            row.setBackground(new Color(240, 240, 240));
            row.setBorder(BorderFactory.createMatteBorder(1, 0, 1, 0, Color.GRAY));

            row.add(new JLabel(u.getId(), SwingConstants.CENTER));
            row.add(new JLabel(u.getNombre(), SwingConstants.CENTER));
            row.add(new JLabel(u.getCorreo(), SwingConstants.CENTER));
            row.add(new JLabel(u.getRol(), SwingConstants.CENTER));

            JButton eliminarBtn = new JButton("Eliminar usuario");
            eliminarBtn.setFocusable(false);
            eliminarBtn.addActionListener(e -> {
                if (!controlador.puedeEliminarUsuario(u.getId(), Modelo.Sesion.obtenerUsuario().getId())) {
                    JOptionPane.showMessageDialog(frame, "No puedes eliminar tu propia cuenta.", "Acción no permitida", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                int confirmacion = JOptionPane.showConfirmDialog(frame, "¿Seguro que deseas eliminar este usuario?", "Confirmar eliminación", JOptionPane.YES_NO_OPTION);
                if (confirmacion == JOptionPane.YES_OPTION) {
                    controlador.eliminarUsuario(u.getId());
                    actualizarLista();
                    listaUsuarios.revalidate();
                    listaUsuarios.repaint();
                }
            });

            row.add(eliminarBtn); // Botón para eliminar usuario
            listaUsuarios.add(row);
            listaUsuarios.add(Box.createRigidArea(new Dimension(0, 10))); // Espacio entre filas
        }

        listaUsuarios.revalidate();
        listaUsuarios.repaint();
    }


    private void mostrarFormularioAgregar() {
        JDialog dialog = new JDialog(frame, "Agregar Usuario", true);
        dialog.setSize(500, 400);
        dialog.setLocationRelativeTo(frame);
        dialog.setLayout(new BorderLayout());

        JPanel formulario = new JPanel();
        formulario.setLayout(new BoxLayout(formulario, BoxLayout.Y_AXIS));
        formulario.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));

        // Nombre
        JPanel nombrePanel = new JPanel(new BorderLayout(5, 5));
        JLabel lblNombre = new JLabel("Nombre:");
        JTextField campoNombre = new JTextField(20);
        nombrePanel.add(lblNombre, BorderLayout.NORTH);
        nombrePanel.add(campoNombre, BorderLayout.CENTER);
        formulario.add(nombrePanel);
        formulario.add(Box.createVerticalStrut(15));

        // Correo
        JPanel correoPanel = new JPanel(new BorderLayout(5, 5));
        JLabel lblCorreo = new JLabel("Correo:");
        JTextField campoCorreo = new JTextField(20);
        correoPanel.add(lblCorreo, BorderLayout.NORTH);
        correoPanel.add(campoCorreo, BorderLayout.CENTER);
        formulario.add(correoPanel);
        formulario.add(Box.createVerticalStrut(15));

        // Rol
        JLabel lblRol = new JLabel("Rol:");
        lblRol.setAlignmentX(Component.CENTER_ALIGNMENT);
        formulario.add(lblRol);

        JPanel rolPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JRadioButton adminBtn = new JRadioButton("Administrador");
        JRadioButton profesorBtn = new JRadioButton("Profesor");
        JRadioButton alumnoBtn = new JRadioButton("Alumno");

        ButtonGroup grupoRol = new ButtonGroup();
        grupoRol.add(adminBtn);
        grupoRol.add(profesorBtn);
        grupoRol.add(alumnoBtn);

        rolPanel.add(adminBtn);
        rolPanel.add(profesorBtn);
        rolPanel.add(alumnoBtn);
        formulario.add(rolPanel);
        formulario.add(Box.createVerticalStrut(10));

        JLabel errorLabel = new JLabel("", SwingConstants.CENTER);
        errorLabel.setForeground(Color.RED);
        errorLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        formulario.add(errorLabel);

        // Botones
        JButton aceptar = new JButton("Aceptar");
        JButton cancelar = new JButton("Cancelar");

        JPanel botones = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 0));
        botones.setBorder(BorderFactory.createEmptyBorder(20, 0, 10, 0));
        botones.add(aceptar);
        botones.add(cancelar);

        dialog.add(formulario, BorderLayout.CENTER);
        dialog.add(botones, BorderLayout.SOUTH);

        aceptar.addActionListener((ActionEvent e) -> {
            String nombre = campoNombre.getText().trim();
            String correo = campoCorreo.getText().trim();
            String rol = adminBtn.isSelected() ? "Administrador"
                    : profesorBtn.isSelected() ? "Profesor"
                    : alumnoBtn.isSelected() ? "Alumno" : null;

            String error = controlador.agregarUsuario(nombre, correo, rol);
            if (error != null) {
                errorLabel.setText(error);
                return;
            }

            controlador.agregarUsuario(nombre, correo, rol);
            dialog.dispose();
            actualizarLista();
            listaUsuarios.revalidate();
            listaUsuarios.repaint();
        });

        cancelar.addActionListener(e -> dialog.dispose());

        dialog.setVisible(true);
    }
}
