package Vista.administrador;

import javax.swing.*;
import java.awt.*;

public class MenuAdministradorView {
    private JFrame frame;

    public void mostrar() {
        frame = new JFrame("Administrador - Menú Principal");
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH); // Pantalla completa
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel(new BorderLayout());

        JLabel titulo = new JLabel("Panel del Administrador", SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 32));
        panel.add(titulo, BorderLayout.NORTH);

        JPanel centro = new JPanel();
        centro.setLayout(new BoxLayout(centro, BoxLayout.Y_AXIS));
        centro.setBorder(BorderFactory.createEmptyBorder(100, 300, 100, 300)); // Margen para centrar

        JButton btnUsuarios = new JButton("Usuarios");
        JButton btnCursos = new JButton("Cursos");
        JButton btnCerrarSesion = new JButton("Cerrar sesión");

        Dimension btnSize = new Dimension(200, 40);
        for (JButton btn : new JButton[]{btnUsuarios, btnCursos, btnCerrarSesion}) {
            btn.setAlignmentX(Component.CENTER_ALIGNMENT);
            btn.setMaximumSize(btnSize);
            btn.setPreferredSize(btnSize);
            centro.add(btn);
            centro.add(Box.createRigidArea(new Dimension(0, 20))); // Espacio entre botones
        }

        btnUsuarios.addActionListener(e -> {
            frame.dispose();
            new UsuariosView().mostrar();
        });

        btnCursos.addActionListener(e -> {
            frame.dispose();
            new CursosView().mostrar();
        });

        btnCerrarSesion.addActionListener(e -> {
            frame.dispose();
            new Vista.LoginView().mostrar();
        });

        panel.add(centro, BorderLayout.CENTER);
        frame.setContentPane(panel);
        frame.setVisible(true);
    }
}