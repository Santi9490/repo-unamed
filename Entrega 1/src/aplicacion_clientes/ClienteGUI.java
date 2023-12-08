package aplicacion_clientes;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import com.formdev.flatlaf.FlatLightLaf;

import inventario.Categorias;

import procesamiento.LoaderFerreteria;
import procesamiento.Login;
import reserva_alquiler.Alquiler;
import reserva_alquiler.Cliente;
import sedes.Sede;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

public class ClienteGUI extends JFrame {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private LoaderFerreteria loaderFerreteria = LoaderFerreteria.newFerreteria();
    private Cliente cliente;

    private JMenuBar menuBar;
    private JPanel cards; // Panel que utiliza CardLayout
    private CardLayout cardLayout; // El CardLayout en sí
    // Menus
    private JMenu menuCategorias, menuReservas, menuAlquileres, menuVehiculos;

    // Items menu Categorias
    private JMenuItem menuItemVerCategoriasPrecios;
    // Items menu Reserva
    private JMenuItem menuItemReservarVehiculo;
    // Items menu HistorialAlquileres
    private JMenuItem menuItemHisAlquileres;
    // Items menu Vehiculos
    private JMenuItem menuItemDiagramaVehiculos;
    
    private JButton registerButton;
    
    private JButton loginButton;
    
    private JLabel titleLabel;
    
    private JLabel carImageLabel;
    
    Login login = new Login();    

    public ClienteGUI(Cliente cliente) {
        loaderFerreteria.cargarDatos();
        this.cliente = cliente;
        setTitle("Cliente");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
        
        // Configuración de la barra de menú
        menuBar = new JMenuBar();
        menuBar.setFont(new Font("Palatino", Font.BOLD, 20)); // Establece la fuente del texto
        menuBar.setBackground(new Color(227, 72, 45));
        menuBar.setForeground(Color.WHITE);

        registerButton = new JButton("Registrarse");
        registerButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                showRegistrationForm();
            }
        });

        loginButton = new JButton("Login");

        loginButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                showLoginForm();
            }
        });

        menuBar.add(registerButton);
        menuBar.add(loginButton);

        

        // Menú Cliente
        menuCategorias = new JMenu("Categorias");
        menuCategorias.setBackground(new Color(227, 72, 45));

        menuItemVerCategoriasPrecios = new JMenuItem("Ver Categorias y Precios");
        menuItemVerCategoriasPrecios.addActionListener(this::mostrarCategoriasPrecios);
        menuCategorias.add(menuItemVerCategoriasPrecios);

        menuBar.add(menuCategorias);

        // Menú Reserva
        menuReservas = new JMenu("Reservar");

        menuItemReservarVehiculo = new JMenuItem("Realizar una reserva");
        menuItemReservarVehiculo.addActionListener(this::mostrarFormularioReserva);
        menuReservas.add(menuItemReservarVehiculo);

        menuBar.add(menuReservas);

        // Menú Historial
        menuAlquileres = new JMenu("Historial");

        menuItemHisAlquileres = new JMenuItem("Ver Historial de Alquileres");
        menuItemHisAlquileres.addActionListener(this::mostrarHistorialAlquileres);
        menuAlquileres.add(menuItemHisAlquileres);

        menuBar.add(menuAlquileres);

        // Menú Vehiculos
        menuVehiculos = new JMenu("Vehiculos");

        menuItemDiagramaVehiculos = new JMenuItem("Ver diagrama disponibilidad vehiculos");
        menuItemDiagramaVehiculos.addActionListener(this::mostrarVehiculos);
        menuVehiculos.add(menuItemDiagramaVehiculos);

        menuBar.add(menuAlquileres);

     // Inicializar el CardLayout y el panel que lo contiene
        cardLayout = new CardLayout();
        cards = new JPanel(cardLayout);

        // Agrega el panel de cartas a la ventana
        this.add(cards, BorderLayout.CENTER);

        // Panel inicial con mensaje de bienvenida
        JPanel initialPanel = new JPanel(new BorderLayout()); // Utiliza BorderLayout para centrar el mensaje
        initialPanel.setBackground(new Color(227, 72, 45));
        
        // Título
        titleLabel = new JLabel("Bienvenido a Ferretería Rent-A-Car", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Palatino", Font.BOLD, 25));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        initialPanel.add(titleLabel, BorderLayout.CENTER);

        // Espaciador
        initialPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        
        // Imagen del carro
        ImageIcon originalIcon = new ImageIcon("datos/imagenes/car-isometric-symbol-color-png.png");
        // Redimensionar la imagen
        Image scaledImage = originalIcon.getImage().getScaledInstance(120, 100, Image.SCALE_SMOOTH);
        
        

        ImageIcon scaledIcon = new ImageIcon(scaledImage);
        carImageLabel = new JLabel(scaledIcon);
        carImageLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        initialPanel.add(carImageLabel);

        
        cards.add(initialPanel, "Inicio");
        
        

        // Muestra el panel inicial
        cardLayout.show(cards, "Inicio");

        setJMenuBar(menuBar);
    }

    private void mostrarCategoriasPrecios(ActionEvent e) {
        // Crear el modelo para el JTable
        String[] columnNames = { "Nombre de la Categoría", "Precio" };
        DefaultTableModel model = new DefaultTableModel(columnNames, 0);

        // Obtener las categorías y añadirlas al modelo del JTable
        List<Categorias> categorias = loaderFerreteria.getCategorias();
        boolean hayCategoriasConPrecio = false;
        for (Categorias categoria : categorias) {
            int precio = categoria.getPrecio();
            if (precio != 0) {
                hayCategoriasConPrecio = true;
                model.addRow(new Object[] { categoria.getNombre(), precio });
            }
        }

        if (!hayCategoriasConPrecio) {
            JOptionPane.showMessageDialog(this, "No hay categorías con precios definidos en este momento",
                    "Información", JOptionPane.INFORMATION_MESSAGE);
        } else {
            // Crear el JTable con el modelo
            JTable tableCategorias = new JTable(model);
            tableCategorias.setFillsViewportHeight(true);

            // Permitir que la tabla se desplace si es necesario
            JScrollPane scrollPane = new JScrollPane(tableCategorias);

            // Panel para contener la tabla
            JPanel panelCategoriasPrecios = new JPanel(new BorderLayout());
            panelCategoriasPrecios.add(scrollPane, BorderLayout.CENTER);

            // Agrega el panel al CardLayout
            cards.add(panelCategoriasPrecios, "CategoriasPrecios");
            cardLayout.show(cards, "CategoriasPrecios");
        }
    }

    private void mostrarFormularioReserva(ActionEvent e) {
        JPanel formularioRegistrarReserva = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);

        // ComboBox para la categoría del vehículo
        gbc.gridx = 0;
        gbc.gridy = 1;
        formularioRegistrarReserva.add(new JLabel("Categoría del vehículo:"), gbc);
        JComboBox<String> comboCategoria = new JComboBox<>();
        for (Categorias categoria : loaderFerreteria.getCategorias()) {
            comboCategoria.addItem(categoria.getNombre());
        }
        gbc.gridx = 1;
        formularioRegistrarReserva.add(comboCategoria, gbc);

        // ComboBox para la sede de recogida
        gbc.gridx = 0;
        gbc.gridy = 2;
        formularioRegistrarReserva.add(new JLabel("Sede de recogida:"), gbc);
        JComboBox<String> comboRecogida = new JComboBox<>();
        for (Sede sede : loaderFerreteria.getSedes()) {
            comboRecogida.addItem(sede.getNombre());
        }
        gbc.gridx = 1;
        formularioRegistrarReserva.add(comboRecogida, gbc);

        // ComboBox para la sede de entrega
        gbc.gridx = 0;
        gbc.gridy = 3;
        formularioRegistrarReserva.add(new JLabel("Sede de entrega:"), gbc);
        JComboBox<String> comboEntrega = new JComboBox<>();
        for (Sede sede : loaderFerreteria.getSedes()) {
            comboEntrega.addItem(sede.getNombre());
        }
        gbc.gridx = 1;
        formularioRegistrarReserva.add(comboEntrega, gbc);

        // Campos para las fechas y horas
        JTextField fieldFechaInicio = new JTextField(10);
        JTextField fieldFechaFinal = new JTextField(10);
        JTextField fieldInicioHora = new JTextField(5);
        JTextField fieldFinHora = new JTextField(5);
        JTextField fieldHoraLlegada = new JTextField(5);

        // Añadir campos para las fechas y horas con sus respectivas etiquetas
        gbc.gridwidth = 1;

        // Fecha de inicio
        gbc.gridx = 0;
        gbc.gridy = 4;
        formularioRegistrarReserva.add(new JLabel("Fecha de inicio (MM/dd/yy):"), gbc);
        gbc.gridx = 1;
        formularioRegistrarReserva.add(fieldFechaInicio, gbc);

        // Fecha final
        gbc.gridx = 0;
        gbc.gridy = 5;
        formularioRegistrarReserva.add(new JLabel("Fecha final (MM/dd/yy):"), gbc);
        gbc.gridx = 1;
        formularioRegistrarReserva.add(fieldFechaFinal, gbc);

        // Hora inicial
        gbc.gridx = 0;
        gbc.gridy = 6;
        formularioRegistrarReserva.add(new JLabel("Hora inicial (HH:mm):"), gbc);
        gbc.gridx = 1;
        formularioRegistrarReserva.add(fieldInicioHora, gbc);

        // Hora final
        gbc.gridx = 0;
        gbc.gridy = 7;
        formularioRegistrarReserva.add(new JLabel("Hora final (HH:mm):"), gbc);
        gbc.gridx = 1;
        formularioRegistrarReserva.add(fieldFinHora, gbc);

        // Hora de llegada
        gbc.gridx = 0;
        gbc.gridy = 8;
        formularioRegistrarReserva.add(new JLabel("Hora de llegada (HH:mm):"), gbc);
        gbc.gridx = 1;
        formularioRegistrarReserva.add(fieldHoraLlegada, gbc);

        // Botón para registrar la reserva
        JButton btnRegistrarReserva = new JButton("Registrar Reserva");
        gbc.gridx = 0;
        gbc.gridy = 9;
        gbc.gridwidth = 2;
        btnRegistrarReserva.addActionListener(event -> {
        	if (cliente!=null) {
            try {
                int cedula = cliente.getCedula();
                String categoriaCarro = comboCategoria.getSelectedItem().toString();
                String lugarRecogida = comboRecogida.getSelectedItem().toString();
                String lugarDejada = comboEntrega.getSelectedItem().toString();

                // Recoger y validar los valores de fechas y horas
                String fechaInicio = fieldFechaInicio.getText().trim();
                String fechaFinal = fieldFechaFinal.getText().trim();
                String inicioHora = fieldInicioHora.getText().trim();
                String finHora = fieldFinHora.getText().trim();
                String horaLlegada = fieldHoraLlegada.getText().trim();

                // Llamada al método que registra la reserva
                int precio = loaderFerreteria.registrarReserva(fechaInicio, fechaFinal, inicioHora, finHora,
                        lugarRecogida, lugarDejada, cedula, categoriaCarro, horaLlegada);
                JOptionPane.showMessageDialog(formularioRegistrarReserva, "Reserva registrada. Precio: " + precio,
                        "Reserva Exitosa", JOptionPane.INFORMATION_MESSAGE);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(formularioRegistrarReserva, "Formato de cédula incorrecto.", "Error",
                        JOptionPane.ERROR_MESSAGE);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(formularioRegistrarReserva,
                        "Error al registrar la reserva: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }}else {
            	JOptionPane.showMessageDialog(this, "Inicia Sesión Primero.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
        formularioRegistrarReserva.add(btnRegistrarReserva, gbc);

        // Agrega el panel al CardLayout
        cards.add(formularioRegistrarReserva, "RegistrarReserva");
        cardLayout.show(cards, "RegistrarReserva");

    }

    private void mostrarHistorialAlquileres(ActionEvent e) {
        // Crear el modelo para el JTable
    	if (cliente!=null) {
        String[] columnNames = { "ID Alquiler", "Información del Alquiler" };
        DefaultTableModel model = new DefaultTableModel(columnNames, 0);

        // Obtener los alquileres y añadirlos al modelo del JTable
        List<Alquiler> alquileres = cliente.getAlquileres();
        if (alquileres.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Realice un alquiler para ver su historial", "Información",
                    JOptionPane.INFORMATION_MESSAGE);
        } else {
            for (int i = 0; i < alquileres.size(); i++) {
                Alquiler alquiler = alquileres.get(i);
                String info = alquiler.getInfoAlquilado();
                model.addRow(new Object[] { i + 1, info });
            }
        }

        // Crear el JTable con el modelo
        JTable tableHistorialAlquileres = new JTable(model);
        tableHistorialAlquileres.setFillsViewportHeight(true);

        // Permitir que la tabla se desplace si es necesario
        JScrollPane scrollPane = new JScrollPane(tableHistorialAlquileres);

        // Panel para contener la tabla
        JPanel panelHistorialAlquileres = new JPanel(new BorderLayout());
        panelHistorialAlquileres.add(scrollPane, BorderLayout.CENTER);

        // Agrega el panel al CardLayout
        cards.add(panelHistorialAlquileres, "HistorialAlquileres");
        cardLayout.show(cards, "HistorialAlquileres");}
    	else 
        {
    		JOptionPane.showMessageDialog(this, "Inicia Sesión Primero.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }


    private void mostrarVehiculos(ActionEvent e) {
        // Crear el modelo para el JTable
        String[] columnNames = { "ID Alquiler", "Información del Alquiler" };
        DefaultTableModel model = new DefaultTableModel(columnNames, 0);

        // Obtener los alquileres y añadirlos al modelo del JTable
        List<Alquiler> alquileres = cliente.getAlquileres();
        if (alquileres.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Realice un alquiler para ver su historial", "Información",
                    JOptionPane.INFORMATION_MESSAGE);
        } else {
            for (int i = 0; i < alquileres.size(); i++) {
                Alquiler alquiler = alquileres.get(i);
                String info = alquiler.getInfoAlquilado();
                model.addRow(new Object[] { i + 1, info });
            }
        }

        // Crear el JTable con el modelo
        JTable tableHistorialAlquileres = new JTable(model);
        tableHistorialAlquileres.setFillsViewportHeight(true);

        // Permitir que la tabla se desplace si es necesario
        JScrollPane scrollPane = new JScrollPane(tableHistorialAlquileres);

        // Panel para contener la tabla
        JPanel panelHistorialAlquileres = new JPanel(new BorderLayout());
        panelHistorialAlquileres.add(scrollPane, BorderLayout.CENTER);

        // Agrega el panel al CardLayout
        cards.add(panelHistorialAlquileres, "HistorialAlquileres");
        cardLayout.show(cards, "HistorialAlquileres");
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
            case "cliente":
                cliente = loaderFerreteria.buscarClienteCorreo(correo);
                break;
            default:
                JOptionPane.showMessageDialog(this, "Credenciales incorrectas.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        FlatLightLaf.install();
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        SwingUtilities.invokeLater(() -> new ClienteGUI(null).setVisible(true));
    }

}
