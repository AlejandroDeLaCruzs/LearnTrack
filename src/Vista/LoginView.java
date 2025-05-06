package Vista;

import Vista.administrador.MenuAdministradorView;
import Controlador.ValidacionUsuarios.ValidacionLogin;
import Modelo.Ficheros.GestorUsuariosCSV;
import Modelo.Usuarios.Usuario;
import javax.swing.*;
import java.awt.*;
import Modelo.Ficheros.GestorCursosCSV;
import Vista.profesor.ProfesorView;

public class LoginView {
    private JFrame frame;
    private JTextField campoCorreo;
    private JPasswordField campoContrasena;
    private JLabel mensajeErrorGeneral;
    private JLabel mensajeErrorCorreo;
    private JLabel mensajeErrorContrasena;
    private JButton botonIniciar;

    public void mostrar() {
        frame = new JFrame("LearnTrack - Inicio de sesión");
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panelPrincipal = new JPanel(new GridBagLayout());
        panelPrincipal.setBackground(Color.WHITE);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.NONE;

        JLabel titulo = new JLabel("LearnTrack");
        titulo.setFont(new Font("Arial", Font.BOLD, 36));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        panelPrincipal.add(titulo, gbc);

        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.EAST;
        gbc.gridy++;
        panelPrincipal.add(new JLabel("Correo:"), gbc);

        campoCorreo = new JTextField(20);
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        panelPrincipal.add(campoCorreo, gbc);
        campoCorreo.addActionListener(e -> botonIniciar.doClick());

        mensajeErrorCorreo = new JLabel("");
        mensajeErrorCorreo.setForeground(Color.RED);
        gbc.gridx = 2   ;
        panelPrincipal.add(mensajeErrorCorreo, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        gbc.anchor = GridBagConstraints.EAST;
        panelPrincipal.add(new JLabel("Contraseña:"), gbc);

        campoContrasena = new JPasswordField(20);
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        panelPrincipal.add(campoContrasena, gbc);
        campoContrasena.addActionListener(e -> botonIniciar.doClick());


        mensajeErrorContrasena = new JLabel("");
        mensajeErrorContrasena.setForeground(Color.RED);
        gbc.gridx = 2;
        panelPrincipal.add(mensajeErrorContrasena, gbc);

        botonIniciar = new JButton("Iniciar sesión");
        gbc.gridx = 0;
        gbc.gridy++;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        panelPrincipal.add(botonIniciar, gbc);

        mensajeErrorGeneral = new JLabel("");
        mensajeErrorGeneral.setForeground(Color.RED);
        mensajeErrorGeneral.setFont(new Font("Arial", Font.PLAIN, 16));
        gbc.gridy++;
        panelPrincipal.add(mensajeErrorGeneral, gbc);

        botonIniciar.addActionListener(e -> {
            mensajeErrorCorreo.setText("");
            mensajeErrorContrasena.setText("");
            mensajeErrorGeneral.setText("");

            String correo = campoCorreo.getText().trim();
            String contrasena = new String(campoContrasena.getPassword()).trim();
            if (!correo.contains("@") || !(correo.endsWith(".com") || correo.endsWith(".es"))) {
                mensajeErrorCorreo.setText("No cumple con los criterios (@, .com, .es)");
                return;
            }
            String resultado = ValidacionLogin.validar(correo, contrasena);

            switch (resultado) {
                case "Administrador":
                    Usuario usuario = GestorUsuariosCSV.cargarUsuarios()
                            .stream().filter(u -> u.getCorreo().equalsIgnoreCase(correo))
                            .findFirst().orElse(null);
                    if (usuario != null) {
                        Modelo.Sesion.establecerUsuario(usuario);
                        frame.dispose();
                        new MenuAdministradorView().mostrar();
                    } else {
                        mensajeErrorGeneral.setText("Error interno: usuario no encontrado.");
                    }
                    break;
                case "Profesor":
                    String cursoAsignado = GestorCursosCSV.obtenerCursoPorProfesor(correo);
                    if (cursoAsignado == null) {
                        JOptionPane.showMessageDialog(frame, "No tienes asignado un curso.", "Información", JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        frame.dispose();
                        new ProfesorView(cursoAsignado).mostrar();
                    }
                    break;


                case "El correo no existe":
                    mensajeErrorCorreo.setText(resultado);
                    break;
                case "Contraseña incorrecta":
                    mensajeErrorContrasena.setText(resultado);
                    break;
                case "Rellene el correo":
                    mensajeErrorCorreo.setText(resultado);
                    break;
                case "Rellene la contraseña":
                    mensajeErrorContrasena.setText(resultado);
                    break;
                case "Rellene los datos":
                    mensajeErrorGeneral.setText(resultado);
                    break;
                default:
                    mensajeErrorGeneral.setText(resultado);
                    break;
            }
        });

        frame.setContentPane(panelPrincipal);
        frame.setVisible(true);
    }
}
