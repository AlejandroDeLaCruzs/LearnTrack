package Vista.alumno;

import Vista.LoginView;

import javax.swing.*;
import java.awt.*;

public class MenuEstudianteView {
    private JFrame frame;

    public void mostrar() {
        frame = new JFrame("Estudiante - Menú Principal");
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH); // Pantalla completa
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel(new BorderLayout());

        JLabel titulo = new JLabel("Menú del Estudiante", SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 32));
        panel.add(titulo, BorderLayout.NORTH);

        JPanel centro = new JPanel();
        centro.setLayout(new BoxLayout(centro, BoxLayout.Y_AXIS));
        centro.setBorder(BorderFactory.createEmptyBorder(100, 300, 100, 300)); // Margen para centrar

        JButton btnMatricularse = new JButton("Matricularse en curso");
        JButton btnMisCalificaciones = new JButton("Mis calificaciones");
        JButton btnCerrarSesion = new JButton("Cerrar sesión");

        Dimension btnSize = new Dimension(200, 40);
        for (JButton btn : new JButton[]{btnMatricularse, btnMisCalificaciones, btnCerrarSesion}) {
            btn.setAlignmentX(Component.CENTER_ALIGNMENT);
            btn.setMaximumSize(btnSize);
            btn.setPreferredSize(btnSize);
            centro.add(btn);
            centro.add(Box.createRigidArea(new Dimension(0, 20))); // Espacio entre botones
        }

        // Acción para matricularse en un curso
        btnMatricularse.addActionListener(e -> {
            frame.dispose();  // Cierra el menú principal
           new MatriculacionView().mostrar();  // Abre la vista para matriculación
        });

        // Acción para ver las calificaciones del estudiante
        btnMisCalificaciones.addActionListener(e -> {
            frame.dispose();  // Cierra el menú principal
            new CalificacionesView().mostrar();  // Abre la vista para ver calificaciones
        });

        // Acción para cerrar sesión
        btnCerrarSesion.addActionListener(e -> {
            frame.dispose();  // Cierra el menú principal
            new LoginView().mostrar();  // Redirige a la vista de login
        });

        panel.add(centro, BorderLayout.CENTER);
        frame.setContentPane(panel);
        frame.setVisible(true);
    }
}

