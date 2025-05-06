package Vista.administrador;

import Modelo.Ficheros.GestorUsuariosCSV;
import Modelo.Usuarios.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class UsuariosView {
    private JFrame frame;
    private JPanel listaUsuarios;

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
        listaUsuarios.setBorder(BorderFactory.createEmptyBorder(20, 300, 20, 300));

        actualizarLista();

        JScrollPane scroll = new JScrollPane(listaUsuarios);
        scroll.setBorder(null);
        scroll.getVerticalScrollBar().setUnitIncrement(16);
        panel.add(scroll, BorderLayout.CENTER);

        JPanel botones = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton btnAtras = new JButton("Atrás");
        JButton btnAgregar = new JButton("Agregar Usuario");

        btnAtras.addActionListener(e -> {
            frame.dispose();
            new MenuAdministradorView().mostrar();
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

        JPanel header = new JPanel(new GridLayout(1, 5));
        header.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
        header.add(new JLabel("ID"));
        header.add(new JLabel("Nombre"));
        header.add(new JLabel("Correo"));
        header.add(new JLabel("Rol"));
        header.add(new JLabel("Acción"));
        listaUsuarios.add(header);
        listaUsuarios.add(Box.createRigidArea(new Dimension(0, 10)));

        List<Usuario> usuarios = GestorUsuariosCSV.cargarUsuarios();
        for (Usuario u : usuarios) {
            JPanel row = new JPanel(new GridLayout(1, 5));
            row.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
            row.setBackground(new Color(240, 240, 240));
            row.setBorder(BorderFactory.createMatteBorder(1, 0, 1, 0, Color.GRAY));

            row.add(new JLabel(u.getId()));
            row.add(new JLabel(u.getNombre()));
            row.add(new JLabel(u.getCorreo()));
            row.add(new JLabel(u.getRol()));

            JButton eliminarBtn = new JButton("Eliminar usuario");
            eliminarBtn.setFocusable(false);
            eliminarBtn.addActionListener(e -> {
                Usuario actual = Modelo.Sesion.obtenerUsuario();
                if (actual != null && actual.getId().equals(u.getId())) {
                    JOptionPane.showMessageDialog(frame, "No puedes eliminar tu propia cuenta.", "Acción no permitida", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                int confirmacion = JOptionPane.showConfirmDialog(frame, "¿Seguro que deseas eliminar este usuario?", "Confirmar eliminación", JOptionPane.YES_NO_OPTION);
                if (confirmacion == JOptionPane.YES_OPTION) {
                    List<Usuario> actualizados = GestorUsuariosCSV.cargarUsuarios()
                            .stream().filter(usuario -> !usuario.getId().equals(u.getId()))
                            .collect(Collectors.toList());
                    GestorUsuariosCSV.guardarUsuarios(actualizados);
                    actualizarLista();
                    listaUsuarios.revalidate();
                    listaUsuarios.repaint();
                }
            });

            row.add(eliminarBtn);
            listaUsuarios.add(row);
            listaUsuarios.add(Box.createRigidArea(new Dimension(0, 5)));
        }
    }

    private void mostrarFormularioAgregar() {
        JDialog dialog = new JDialog(frame, "Agregar Usuario", true);
        dialog.setSize(450, 350); // Ventana más pequeña
        dialog.setLocationRelativeTo(frame);
        dialog.setLayout(new BorderLayout());

        JPanel formulario = new JPanel();
        formulario.setLayout(new GridBagLayout());
        formulario.setBorder(BorderFactory.createEmptyBorder(20, 30, 10, 30));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel lblNombre = new JLabel("Nombre:");
        JLabel lblCorreo = new JLabel("Correo:");

        JTextField campoNombre = new JTextField(20);
        JTextField campoCorreo = new JTextField(20);

        Dimension campoSize = new Dimension(200, 24);
        campoNombre.setPreferredSize(campoSize);
        campoCorreo.setPreferredSize(campoSize);

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.EAST;
        formulario.add(lblNombre, gbc);

        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        formulario.add(campoNombre, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.EAST;
        formulario.add(lblCorreo, gbc);

        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        formulario.add(campoCorreo, gbc);

        // Etiqueta centrada "Rol:"
        JLabel lblRol = new JLabel("Rol:");
        lblRol.setHorizontalAlignment(SwingConstants.CENTER);
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        formulario.add(lblRol, gbc);

        // Opciones de rol
        JRadioButton adminBtn = new JRadioButton("Administrador");
        JRadioButton profesorBtn = new JRadioButton("Profesor");
        JRadioButton alumnoBtn = new JRadioButton("Alumno");

        ButtonGroup grupoRol = new ButtonGroup();
        grupoRol.add(adminBtn);
        grupoRol.add(profesorBtn);
        grupoRol.add(alumnoBtn);

        JPanel rolPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        rolPanel.add(adminBtn);
        rolPanel.add(profesorBtn);
        rolPanel.add(alumnoBtn);

        gbc.gridy = 3;
        formulario.add(rolPanel, gbc);

        // Mensaje de error
        JLabel errorLabel = new JLabel("", SwingConstants.CENTER);
        errorLabel.setForeground(Color.RED);
        gbc.gridy = 4;
        formulario.add(errorLabel, gbc);

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
            String rolSeleccionado = null;

            if (adminBtn.isSelected()) rolSeleccionado = "Administrador";
            else if (profesorBtn.isSelected()) rolSeleccionado = "Profesor";
            else if (alumnoBtn.isSelected()) rolSeleccionado = "Alumno";

            // Validaciones
            if (nombre.isEmpty() || correo.isEmpty() || rolSeleccionado == null) {
                errorLabel.setText("Complete todos los campos.");
                return;
            }

            if (!correo.contains("@")) {
                errorLabel.setText("(Es necesario @)");
                return;
            }

            if (!(correo.endsWith(".com") || correo.endsWith(".es"))) {
                errorLabel.setText("(El correo debe terminar en .com o .es)");
                return;
            }

            List<Usuario> usuarios = GestorUsuariosCSV.cargarUsuarios();
            boolean nombreRepetido = usuarios.stream().anyMatch(u -> u.getNombre().equalsIgnoreCase(nombre));
            boolean correoRepetido = usuarios.stream().anyMatch(u -> u.getCorreo().equalsIgnoreCase(correo));

            if (nombreRepetido || correoRepetido) {
                errorLabel.setText("Nombre o correo ya existentes.");
                return;
            }

            String id = generarIdUnico(usuarios);
            String contrasena = generarContrasena();

            Usuario nuevo;
            switch (rolSeleccionado) {
                case "Administrador":
                    nuevo = new Administrador(id, nombre, correo, contrasena);
                    break;
                case "Profesor":
                    nuevo = new Profesor(id, nombre, correo, contrasena);
                    break;
                default:
                    nuevo = new Alumno(id, nombre, correo, contrasena);
                    break;
            }

            usuarios.add(nuevo);
            GestorUsuariosCSV.guardarUsuarios(usuarios);
            dialog.dispose();
            actualizarLista();
            listaUsuarios.revalidate();
            listaUsuarios.repaint();
        });

        cancelar.addActionListener(e -> dialog.dispose());

        dialog.setVisible(true);
    }

    private String generarIdUnico(List<Usuario> usuarios) {
        String nuevoId;
        Random random = new Random();
        boolean idDuplicado;

        do {
            char letra = (char) ('A' + random.nextInt(26));
            int numeros = random.nextInt(900) + 100; // de 100 a 999
            nuevoId = letra + String.valueOf(numeros);

            final String idFinal = nuevoId; // 💥 aquí hacemos copia final para usar en lambda
            idDuplicado = usuarios.stream().anyMatch(u -> u.getId().equals(idFinal));
        } while (idDuplicado);

        return nuevoId;
    }


    private String generarContrasena() {
        Random random = new Random();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 4; i++) {
            sb.append((char) (random.nextInt(26) + 'A'));
        }
        for (int i = 0; i < 4; i++) {
            sb.append(random.nextInt(10));
        }
        return sb.toString();
    }
}
