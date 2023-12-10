package Interaz;

import javax.swing.*;
import procesamiento.LoaderFerreteria;
import procesamiento.Login;
import reserva_alquiler.Cliente;
import aplicacion_clientes.RegistrationCliente;
import aplicacion_clientes.ClienteGUI;
import sedes.Empleado;
import com.formdev.flatlaf.FlatLightLaf;

import java.awt.*;
import java.awt.event.*;

public class RentACarGUI extends JFrame {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Empleado empleado;
    private Cliente cliente;

    private JButton registerButton;
    private JButton loginButton;
    private JButton exitButton;
    private JLabel titleLabel;
    private JLabel carImageLabel;

    Login login = new Login();

    private LoaderFerreteria loaderFerreteria = LoaderFerreteria.newFerreteria();

    public RentACarGUI() {

        loaderFerreteria.cargarDatos();

        // Configuración inicial de la ventana
        setTitle("Bienvenido a Ferretería Rent-A-Car");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
        getContentPane().setBackground(new Color(227, 72, 45));

        // Título
        titleLabel = new JLabel("Bienvenido a Ferretería Rent-A-Car", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Palatino", Font.BOLD, 25));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        add(titleLabel);

        // Espaciador
        add(Box.createRigidArea(new Dimension(0, 20)));

        // Imagen del carro
        ImageIcon originalIcon = new ImageIcon("datos/imagenes/car-isometric-symbol-color-png.png");
        // Redimensionar la imagen
        Image scaledImage = originalIcon.getImage().getScaledInstance(120, 100, Image.SCALE_SMOOTH);

        ImageIcon scaledIcon = new ImageIcon(scaledImage);
        carImageLabel = new JLabel(scaledIcon);
        carImageLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        add(carImageLabel);

        // Crear botones y mejorar su estilo
        registerButton = new JButton("Registrar Cliente");
        styleButton(registerButton);

        loginButton = new JButton("Iniciar sesión");
        styleButton(loginButton);

        exitButton = new JButton("Salir");
        styleButton(exitButton);

        // Añadir botones al panel de contenido
        add(Box.createRigidArea(new Dimension(0, 20)));
        add(registerButton);
        add(Box.createRigidArea(new Dimension(0, 10)));
        add(loginButton);
        add(Box.createRigidArea(new Dimension(0, 10)));
        add(exitButton);

        // Añadir manejadores de eventos
        registerButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                showRegistrationForm();
            }
        });

        loginButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                showLoginForm();
            }
        });

        exitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
    }

    private void styleButton(JButton button) {
        button.setFont(new Font("Palatino", Font.BOLD, 14)); // Usar Palatino
        button.setForeground(Color.WHITE); // Letra blanca
        button.setBackground(new Color(199, 13, 0)); // Fondo rojo oscuro
        button.setOpaque(false);
        button.setContentAreaFilled(false);
        button.setBorderPainted(false); // Quitar el borde pintado
        button.setFocusPainted(false);
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.setMargin(new Insets(10, 20, 10, 20)); // Margen dentro del botón

        // Usar un borde vacío para forzar el espacio, pero sin pintarlo
        button.setBorder(BorderFactory.createEmptyBorder(10, 25, 10, 25));

        // Agregar un MouseListener para cambiar el aspecto del botón al pasar el mouse
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                JButton btn = (JButton) e.getSource();
                btn.setOpaque(true);
                btn.setContentAreaFilled(true);
                btn.setBorderPainted(false);
                btn.setBorder(BorderFactory.createEmptyBorder(10, 25, 10, 25));
                btn.repaint();
            }

            @Override
            public void mouseExited(MouseEvent e) {
                JButton btn = (JButton) e.getSource();
                btn.setOpaque(false);
                btn.setContentAreaFilled(false);
                btn.setBorderPainted(false);
                btn.setBorder(BorderFactory.createEmptyBorder(10, 25, 10, 25));
                btn.repaint();
            }
        });
    }

    private void showRegistrationForm() {
        // Crear una instancia del formulario de registro y mostrarlo
        RegistrationCliente registrationForm = new RegistrationCliente();
        JDialog registrationDialog = new JDialog(this, "Formulario de Registro", true);
        registrationDialog.getContentPane().add(registrationForm);
        registrationDialog.pack();
        registrationDialog.setLocationRelativeTo(this);
        registrationDialog.setVisible(true);
    }

    private void showLoginForm() {
        login.cargarUsuarios();

        JPanel loginPanel = new JPanel();
        loginPanel.setLayout(new GridLayout(3, 2, 5, 5));
        loginPanel.setBackground(new Color(227, 72, 45));

        // Crear la etiqueta para el correo
        JLabel correoLabel = new JLabel("Correo:");
        correoLabel.setForeground(Color.WHITE);
        correoLabel.setFont(new Font("Palatino", Font.BOLD, 12));
        loginPanel.add(correoLabel);

        JTextField usuarioField = new JTextField();
        loginPanel.add(usuarioField);

        // Crear la etiqueta para la contraseña
        JLabel contraseniaLabel = new JLabel("Contraseña:");
        contraseniaLabel.setForeground(Color.WHITE);
        contraseniaLabel.setFont(new Font("Palatino", Font.BOLD, 12));
        loginPanel.add(contraseniaLabel);

        JPasswordField contraseniaField = new JPasswordField();
        loginPanel.add(contraseniaField);

        JButton loginButton = new JButton("Iniciar Sesión");
        styleButton(loginButton);
        loginPanel.add(loginButton);

        JDialog loginDialog = new JDialog(this, "Inicio de Sesión", true);
        loginDialog.getContentPane().add(loginPanel);
        loginDialog.pack();
        loginDialog.setLocationRelativeTo(this);

        loginButton.addActionListener(e -> {
            String usuario = usuarioField.getText();
            String correo = correoLabel.getText();
            String contrasenia = new String(contraseniaField.getPassword());

            if (autenticarUsuario(usuario, contrasenia)) {
                loginDialog.dispose();
                mostrarMenuUsuario(usuario, correo);
            } else {
                JOptionPane.showMessageDialog(loginDialog, "Credenciales incorrectas.", "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        });

        loginDialog.setVisible(true);
    }

    private boolean autenticarUsuario(String usuario, String contrasenia) {
        return login.UsuarioExist(usuario, contrasenia);
    }

    private void mostrarMenuUsuario(String usuario, String correo) {
        String rol = login.getrol(usuario, loaderFerreteria);
        switch (rol) {
            case "superadmin":
                mostrarMenuSuperAdmin();
                break;
            case "adminlocal":
                mostrarMenuAdminLoc();
                break;
            case "empleado":
                empleado = loaderFerreteria.buscarEmpleadoCorreo(correo);
                mostrarMenuEmpleado(empleado);
                break;
            case "cliente":
                cliente = loaderFerreteria.buscarClienteCorreo(correo);
                mostrarMenuCliente(cliente);
                break;
            default:
                JOptionPane.showMessageDialog(this, "Rol no reconocido.");
        }
    }

    private void mostrarMenuSuperAdmin() {
        this.setVisible(false);

        SuperAdminGUI superAdminGUI = new SuperAdminGUI();
        superAdminGUI.setVisible(true);
    }

    private void mostrarMenuAdminLoc() {
        this.setVisible(false); // Ocultar la ventana actual

        AdminLocGUI adminLocGUI = new AdminLocGUI();
        adminLocGUI.setVisible(true); // Mostrar la interfaz de Administrador Local
    }

    private void mostrarMenuEmpleado(Empleado empleado) {
        this.setVisible(false); // Ocultar la ventana actual

        EmpleadoGUI empleadoGUI = new EmpleadoGUI(empleado);
        empleadoGUI.setVisible(true); // Mostrar la interfaz de Empleado
    }

    private void mostrarMenuCliente(Cliente cliente) {
        this.setVisible(false);

        ClienteGUI clienteGUI = new ClienteGUI(cliente);
        clienteGUI.setVisible(true);
    }

    public static void main(String[] args) {
        FlatLightLaf.install();
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        SwingUtilities.invokeLater(() -> new RentACarGUI().setVisible(true));
    }

}
