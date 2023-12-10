package Interaz;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import aplicacion_clientes.RegistrationCliente;
import inventario.Categorias;
import pagos.CreditCardInfo;
import pagos.PayPalGateway;
import pagos.PayUGateway;
import pagos.PaymentGateway;
import pagos.PaymentInfo;
import pagos.PaymentResult;

import java.util.Arrays;
import procesamiento.Fecha;
import procesamiento.LoaderFerreteria;
import reserva_alquiler.Alquiler;
import sedes.Empleado;
import sedes.Sede;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Dialog.ModalityType;
import java.awt.event.ActionEvent;
import java.util.List;
import java.util.UUID;

public class EmpleadoGUI extends JFrame {

    private LoaderFerreteria loaderFerreteria = LoaderFerreteria.newFerreteria();
    private Empleado empleado;

    private JMenuBar menuBar;
    private JPanel cards; // Panel que utiliza CardLayout
    private CardLayout cardLayout; // El CardLayout en sí
    // Menus
    private JMenu menuCliente, menuVehiculos, menuAlquileres, menuReserva, menuDevolucion;
    // Items menu Cliente
    private JMenuItem menuItemRegistrarCliente, menuItemListarHisCliente;
    // Items menu Vehiculos
    private JMenuItem menuItemPonerMantenimientoVehiculo, menuItemSacarMantenimiento;
    // Items menu Reservas
    private JMenuItem menuItemRegistrarReserva;
    // Items menu Alquiler
    private JMenuItem menuItemRegistrarAlquiler;
    // Items menu Devolucion
    private JMenuItem menuItemRegistrarDevolucion;

    public EmpleadoGUI(Empleado empleado) {
        this.empleado = empleado;
        setTitle("Empleado");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Configuración de la barra de menú
        menuBar = new JMenuBar();

        // Menú Cliente
        menuCliente = new JMenu("Clientes");

        menuItemRegistrarCliente = new JMenuItem("Registrar nuevo Cliente");
        menuItemRegistrarCliente.addActionListener(this::mostrarFormularioRegistrarCliente);
        menuCliente.add(menuItemRegistrarCliente);

        menuItemListarHisCliente = new JMenuItem("Ver Historial Cliente");
        menuItemListarHisCliente.addActionListener(this::mostrarHisCliente);
        menuCliente.add(menuItemListarHisCliente);

        menuBar.add(menuCliente);

        // Menú Vehiculos
        menuVehiculos = new JMenu("Vehiculos");

        menuItemPonerMantenimientoVehiculo = new JMenuItem("Poner en mantenimiento");
        menuItemPonerMantenimientoVehiculo.addActionListener(this::mostrarPonerMantenimientoVeh);
        menuVehiculos.add(menuItemPonerMantenimientoVehiculo);

        menuItemSacarMantenimiento = new JMenuItem("Sacar de Mantenimiento");
        menuItemSacarMantenimiento.addActionListener(this::mostrarSacarMantenimientoVeh);
        menuVehiculos.add(menuItemSacarMantenimiento);

        menuBar.add(menuVehiculos);

        // Menú reserva
        menuReserva = new JMenu("Reservas");

        menuItemRegistrarReserva = new JMenuItem("Registrar reserva");
        menuItemRegistrarReserva.addActionListener(this::mostrarRegistrarReserva);
        menuReserva.add(menuItemRegistrarReserva);

        menuBar.add(menuReserva);

        // Menú alquiler
        menuAlquileres = new JMenu("Alquiler");

        menuItemRegistrarAlquiler = new JMenuItem("Registrar alquiler");
        menuItemRegistrarAlquiler.addActionListener(this::mostrarRegistrarAlquiler);
        menuAlquileres.add(menuItemRegistrarAlquiler);

        menuBar.add(menuAlquileres);

        // Menú devolucion
        menuDevolucion = new JMenu("Devolucion");

        menuItemRegistrarDevolucion = new JMenuItem("Registrar devolucion");
        menuItemRegistrarDevolucion.addActionListener(this::mostrarRegistrarDevolucion);
        menuDevolucion.add(menuItemRegistrarDevolucion);

        menuBar.add(menuDevolucion);

        // Inicializar el CardLayout y el panel que lo contiene
        cardLayout = new CardLayout();
        cards = new JPanel(cardLayout);

        // Agrega el panel de cartas a la ventana
        this.add(cards, BorderLayout.CENTER);

        // Panel inicial con mensaje de bienvenida
        JPanel initialPanel = new JPanel(new BorderLayout()); // Utiliza BorderLayout para centrar el mensaje
        JLabel welcomeLabel = new JLabel("Bienvenido al Sistema de Gestión de Rent-A-Car", SwingConstants.CENTER);
        welcomeLabel.setFont(new Font("Serif", Font.BOLD, 20)); // Establece la fuente del texto

        initialPanel.add(welcomeLabel, BorderLayout.CENTER); // Añade el mensaje de bienvenida al panel

        // Agrega el panel inicial al contenedor de cartas con un nombre único
        cards.add(initialPanel, "Inicio");

        // Muestra el panel inicial
        cardLayout.show(cards, "Inicio");

        setJMenuBar(menuBar);
    }

    // Metodos Clientes

    private void mostrarFormularioRegistrarCliente(ActionEvent e) {
        // Crear una instancia del formulario de registro y mostrarlo
        RegistrationCliente registrationForm = new RegistrationCliente();
        JDialog registrationDialog = new JDialog(this, "Formulario de Registro", true);
        registrationDialog.getContentPane().add(registrationForm);
        registrationDialog.pack();
        registrationDialog.setLocationRelativeTo(this);
        registrationDialog.setVisible(true);
    }

    private void mostrarHisCliente(ActionEvent e) {
        // Panel para el formulario de historial del cliente
        JPanel panelHistorialCliente = new JPanel(new BorderLayout());

        // Etiqueta y campo de texto para la cédula del cliente
        panelHistorialCliente.add(new JLabel("Cédula del Cliente:"), BorderLayout.NORTH);
        JTextField fieldCedulaCliente = new JTextField(10);
        panelHistorialCliente.add(fieldCedulaCliente, BorderLayout.CENTER);

        // Botón para buscar el historial
        JButton btnVerHistorial = new JButton("Ver Historial");
        btnVerHistorial.addActionListener(event -> {
            try {
                int cedula = Integer.parseInt(fieldCedulaCliente.getText().trim());
                List<Alquiler> alquileres = loaderFerreteria.verHisAlquiCliente(cedula);
                if (alquileres.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "No se encontraron alquileres para este cliente.",
                            "Sin Historial", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    // Crear un modelo de tabla para el JTable
                    String[] columnNames = { "Fecha", "Vehículo", "Precio" }; // Ejemplo de columnas
                    DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0);

                    for (Alquiler alquiler : alquileres) {
                        Object[] row = new Object[] {
                                alquiler.getFechaInicio(),
                                alquiler.getCarroAsignado().getPlaca(),
                                alquiler.calcularPrecio()
                        };
                        tableModel.addRow(row);
                    }

                    JTable table = new JTable(tableModel);
                    table.setFillsViewportHeight(true);

                    // Crear el diálogo y añadir la tabla con un JScrollPane
                    JDialog historialDialog = new JDialog(this, "Historial de Alquileres",
                            ModalityType.APPLICATION_MODAL);
                    historialDialog.add(new JScrollPane(table));
                    historialDialog.setSize(600, 400);
                    historialDialog.setLocationRelativeTo(this);
                    historialDialog.setVisible(true);
                }

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Cédula inválida. Por favor, ingrese un número válido.",
                        "Error de Formato", JOptionPane.ERROR_MESSAGE);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error al ver el historial: " + ex.getMessage(), "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        });

        panelHistorialCliente.add(btnVerHistorial, BorderLayout.SOUTH);

        // Agrega el panel al CardLayout
        cards.add(panelHistorialCliente, "HistorialCliente");
        cardLayout.show(cards, "HistorialCliente");
    }

    // Metodos Vehiculos

    private void mostrarPonerMantenimientoVeh(ActionEvent e) {
        // Panel para el formulario de poner un vehículo en mantenimiento
        JPanel panelPonerMantenimiento = new JPanel();
        panelPonerMantenimiento.setLayout(new BoxLayout(panelPonerMantenimiento, BoxLayout.Y_AXIS));

        // Campo para ingresar la placa del vehículo
        panelPonerMantenimiento.add(new JLabel("Placa del Vehículo:"));
        JTextField fieldPlaca = new JTextField(10);
        panelPonerMantenimiento.add(fieldPlaca);

        // Checkbox para limpieza
        JCheckBox checkLimpieza = new JCheckBox("En limpieza");
        panelPonerMantenimiento.add(checkLimpieza);

        // Checkbox para mantenimiento
        JCheckBox checkMantenimiento = new JCheckBox("En mantenimiento");
        panelPonerMantenimiento.add(checkMantenimiento);

        // Campo para ingresar los días de mantenimiento
        panelPonerMantenimiento.add(new JLabel("Días de mantenimiento:"));
        JTextField fieldDias = new JTextField(5);
        panelPonerMantenimiento.add(fieldDias);

        // Botón para enviar el formulario
        JButton btnPonerMantenimiento = new JButton("Poner en Mantenimiento");
        btnPonerMantenimiento.addActionListener(event -> {
            String placa = fieldPlaca.getText().trim();
            boolean limpieza = checkLimpieza.isSelected();
            boolean mantenimiento = checkMantenimiento.isSelected();
            short dias = 0;
            try {
                dias = Short.parseShort(fieldDias.getText());
                loaderFerreteria.ponerMantenimientoCarro(limpieza, mantenimiento, placa, dias);
                JOptionPane.showMessageDialog(this, "Vehículo " + placa + " actualizado.", "Actualización Exitosa",
                        JOptionPane.INFORMATION_MESSAGE);
                // Limpiar campos
                fieldPlaca.setText("");
                checkLimpieza.setSelected(false);
                checkMantenimiento.setSelected(false);
                fieldDias.setText("");
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Por favor, ingrese un número válido de días.", "Error",
                        JOptionPane.ERROR_MESSAGE);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error al poner en mantenimiento: " + ex.getMessage(), "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        });

        panelPonerMantenimiento.add(btnPonerMantenimiento);

        // Agrega el panel al CardLayout
        cards.add(panelPonerMantenimiento, "PonerMantenimiento");
        cardLayout.show(cards, "PonerMantenimiento");
    }

    private void mostrarSacarMantenimientoVeh(ActionEvent e) {
        // Panel para el formulario de sacar de mantenimiento
        JPanel formularioSacarMantenimiento = new JPanel(new BorderLayout());

        // Etiqueta y campo de texto para la placa del vehículo
        formularioSacarMantenimiento.add(new JLabel("Placa del Vehículo:"), BorderLayout.NORTH);
        JTextField fieldPlaca = new JTextField(10);
        formularioSacarMantenimiento.add(fieldPlaca, BorderLayout.CENTER);

        // Botón para sacar de mantenimiento
        JButton btnSacarMantenimiento = new JButton("Sacar de Mantenimiento");
        btnSacarMantenimiento.addActionListener(event -> {
            String placa = fieldPlaca.getText().trim();
            if (placa.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Debe ingresar la placa del vehículo.", "Error",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }
            try {
                loaderFerreteria.quitarMantenimiento(placa);
                JOptionPane.showMessageDialog(this, "Vehículo " + placa + " sacado de mantenimiento exitosamente.",
                        "Operación Exitosa", JOptionPane.INFORMATION_MESSAGE);
                // Limpia el campo después de la operación
                fieldPlaca.setText("");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error al sacar de mantenimiento: " + ex.getMessage(), "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        });

        formularioSacarMantenimiento.add(btnSacarMantenimiento, BorderLayout.SOUTH);

        // Agrega el panel al CardLayout
        cards.add(formularioSacarMantenimiento, "SacarMantenimiento");
        cardLayout.show(cards, "SacarMantenimiento");
    }

    // Metodos Reserva

    private void mostrarRegistrarReserva(ActionEvent e) {
        JPanel formularioRegistrarReserva = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);

        // Campo para la cédula del cliente
        gbc.gridx = 0;
        gbc.gridy = 0;
        formularioRegistrarReserva.add(new JLabel("Cédula del cliente:"), gbc);
        JTextField fieldCedula = new JTextField(10);
        gbc.gridx = 1;
        formularioRegistrarReserva.add(fieldCedula, gbc);

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
            try {
                int cedula = Integer.parseInt(fieldCedula.getText().trim());
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
                mostrarFormularioPago(precio);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(formularioRegistrarReserva, "Formato de cédula incorrecto.", "Error",
                        JOptionPane.ERROR_MESSAGE);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(formularioRegistrarReserva,
                        "Error al registrar la reserva: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
        formularioRegistrarReserva.add(btnRegistrarReserva, gbc);

        // Agrega el panel al CardLayout
        cards.add(formularioRegistrarReserva, "RegistrarReserva");
        cardLayout.show(cards, "RegistrarReserva");

    }

    private void mostrarFormularioPago(double precioReserva) {
        JDialog ventanaPago = new JDialog(this, "Procesar Pago", true);
        ventanaPago.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);

        // Campos para los detalles de la tarjeta de crédito
        JTextField fieldNumeroTarjeta = new JTextField(16);
        JTextField fieldNombreTitular = new JTextField(20);
        JTextField fieldFechaExpiracion = new JTextField(5);
        JTextField fieldCVV = new JTextField(3);

        // Agrega los campos y etiquetas a la ventana de pago
        int row = 0;
        ventanaPago.add(new JLabel("Número de Tarjeta:"), gbc);
        gbc.gridx = 1;
        ventanaPago.add(fieldNumeroTarjeta, gbc);

        gbc.gridx = 0;
        gbc.gridy = ++row;
        ventanaPago.add(new JLabel("Nombre del Titular:"), gbc);
        gbc.gridx = 1;
        ventanaPago.add(fieldNombreTitular, gbc);

        gbc.gridx = 0;
        gbc.gridy = ++row;
        ventanaPago.add(new JLabel("Fecha de Expiración (MM/AA):"), gbc);
        gbc.gridx = 1;
        ventanaPago.add(fieldFechaExpiracion, gbc);

        gbc.gridx = 0;
        gbc.gridy = ++row;
        ventanaPago.add(new JLabel("CVV:"), gbc);
        gbc.gridx = 1;
        ventanaPago.add(fieldCVV, gbc);

        // ComboBox para seleccionar la pasarela de pago
        gbc.gridx = 0;
        gbc.gridy = ++row;
        ventanaPago.add(new JLabel("Pasarela de Pago:"), gbc);
        gbc.gridx = 1;
        JComboBox<String> comboPasarelaPago = new JComboBox<>(new String[] {"PayU", "PayPal"});
        ventanaPago.add(comboPasarelaPago, gbc);

        // Botón para procesar el pago
        gbc.gridx = 0;
        gbc.gridy = ++row;
        gbc.gridwidth = 2;
        JButton btnProcesarPago = new JButton("Procesar Pago");
        ventanaPago.add(btnProcesarPago, gbc);

        btnProcesarPago.addActionListener(e -> {
            String numeroTarjeta = fieldNumeroTarjeta.getText();
            String nombreTitular = fieldNombreTitular.getText();
            String fechaExpiracion = fieldFechaExpiracion.getText();
            String cvv = fieldCVV.getText();
            String pasarelaSeleccionada = (String) comboPasarelaPago.getSelectedItem();

            CreditCardInfo creditCardInfo = new CreditCardInfo(numeroTarjeta, nombreTitular, fechaExpiracion, cvv);
            PaymentInfo paymentInfo = new PaymentInfo(precioReserva, "COP", UUID.randomUUID().toString());

            PaymentGateway paymentGateway;
            if ("PayU".equals(pasarelaSeleccionada)) {
                paymentGateway = new PayUGateway();
            } else {
                paymentGateway = new PayPalGateway();
            }

            PaymentResult result = paymentGateway.processPayment(creditCardInfo, paymentInfo);
            if (result.isSuccess()) {
                JOptionPane.showMessageDialog(ventanaPago, "Pago exitoso: " + result.getMessage(), "Pago Exitoso", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(ventanaPago, "Error en el pago: " + result.getMessage(), "Error de Pago", JOptionPane.ERROR_MESSAGE);
            }
        });

        ventanaPago.pack();
        ventanaPago.setLocationRelativeTo(this);
        ventanaPago.setVisible(true);
    }

    // Metodos Alquiler

    private void mostrarRegistrarAlquiler(ActionEvent e) {
        JPanel formularioRegistrarAlquiler = new JPanel();
        formularioRegistrarAlquiler.setLayout(new BoxLayout(formularioRegistrarAlquiler, BoxLayout.Y_AXIS));

        // Campo para la cédula del cliente
        formularioRegistrarAlquiler.add(new JLabel("Cédula del Cliente:"));
        JTextField fieldCedulaCliente = new JTextField(10);
        formularioRegistrarAlquiler.add(fieldCedulaCliente);

        // Selección de categoría
        formularioRegistrarAlquiler.add(new JLabel("Categoría del Vehículo:"));
        JComboBox<String> comboCategorias = new JComboBox<>();
        for (Categorias categoria : loaderFerreteria.getCategorias()) {
            comboCategorias.addItem(categoria.getNombre());
        }
        formularioRegistrarAlquiler.add(comboCategorias);

        // Selección de sede para entrega
        formularioRegistrarAlquiler.add(new JLabel("Sede para Entrega del Vehículo:"));
        JComboBox<String> comboSedes = new JComboBox<>();
        for (Sede sede : loaderFerreteria.getSedes()) {
            comboSedes.addItem(sede.getNombre());
        }
        formularioRegistrarAlquiler.add(comboSedes);

        // Fechas
        formularioRegistrarAlquiler.add(new JLabel("Fecha de Entrega (MM/dd/yy):"));
        JTextField fieldFechaFinal = new JTextField(10);
        formularioRegistrarAlquiler.add(fieldFechaFinal);

        // Horas
        formularioRegistrarAlquiler.add(new JLabel("Hora Inicial:"));
        JTextField fieldHoraInicial = new JTextField(5);
        formularioRegistrarAlquiler.add(fieldHoraInicial);

        formularioRegistrarAlquiler.add(new JLabel("Hora Final:"));
        JTextField fieldHoraFinal = new JTextField(5);
        formularioRegistrarAlquiler.add(fieldHoraFinal);

        // Seguros
        formularioRegistrarAlquiler.add(new JLabel("Seguros (separados por comas):"));
        JTextField fieldSeguros = new JTextField(20);
        formularioRegistrarAlquiler.add(fieldSeguros);

        JButton btnRegistrarAlquiler = new JButton("Registrar Alquiler");
        btnRegistrarAlquiler.addActionListener(event -> {
            try {
                int cedulaCliente = Integer.parseInt(fieldCedulaCliente.getText().trim());
                String categoriaCarro = (String) comboCategorias.getSelectedItem();
                String lugarDejada = (String) comboSedes.getSelectedItem();
                String fechaFinal = fieldFechaFinal.getText().trim();
                String inicioHora = fieldHoraInicial.getText().trim();
                String finHora = fieldHoraFinal.getText().trim();
                List<String> seguros = Arrays.asList(fieldSeguros.getText().trim().split("\\s*,\\s*"));

                String lugarRecogida = empleado.getSede().getNombre();
                String fechaInicio = Fecha.getStringFechaActual();

                loaderFerreteria.registrarAlquiler(fechaInicio, fechaFinal, inicioHora, finHora, lugarRecogida,
                        lugarDejada, cedulaCliente, categoriaCarro, seguros);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(formularioRegistrarAlquiler,
                        "Error al registrar el alquiler: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        formularioRegistrarAlquiler.add(btnRegistrarAlquiler);

        // Agrega el panel al CardLayout
        cards.add(formularioRegistrarAlquiler, "RegistrarAlquiler");
        cardLayout.show(cards, "RegistrarAlquiler");
    }

    // Metodos Devolucion

    private void mostrarRegistrarDevolucion(ActionEvent e) {
        // Panel para el formulario de finalización de alquiler
        JPanel formularioFinalizarAlquiler = new JPanel(new BorderLayout());

        // Panel para la cédula del cliente
        JPanel panelCedula = new JPanel();
        panelCedula.add(new JLabel("Cédula del Cliente:"));
        JTextField fieldCedula = new JTextField(10);
        panelCedula.add(fieldCedula);

        // Panel para la placa del vehículo
        JPanel panelPlaca = new JPanel();
        panelPlaca.add(new JLabel("Placa del Vehículo:"));
        JTextField fieldPlaca = new JTextField(10);
        panelPlaca.add(fieldPlaca);
        
        // Panel para el exedente
        JPanel panelExedente = new JPanel();
        panelExedente.add(new JLabel("Exedente Pagado:"));
        JTextField fieldExedente= new JTextField(10);
        panelPlaca.add(fieldExedente);

        // Agrega los paneles al formulario
        formularioFinalizarAlquiler.add(panelCedula, BorderLayout.NORTH);
        formularioFinalizarAlquiler.add(panelPlaca, BorderLayout.CENTER);
        formularioFinalizarAlquiler.add(panelExedente, BorderLayout.SOUTH);

        // Botón para finalizar el alquiler
        JButton btnFinalizarAlquiler = new JButton("Finalizar Alquiler");
        btnFinalizarAlquiler.addActionListener(event -> {
            try {
                int cedulaCliente = Integer.parseInt(fieldCedula.getText().trim());
                String placa = fieldPlaca.getText().trim();
                if (placa.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Debe ingresar la placa del vehículo.", "Error",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }
                float exedente= Float.parseFloat(fieldExedente.getText().trim());
                loaderFerreteria.registrarDevolucion(cedulaCliente, placa, exedente);
                JOptionPane.showMessageDialog(this, "Devolución registrada exitosamente.", "Registro Exitoso",
                        JOptionPane.INFORMATION_MESSAGE);
                // Limpia los campos después del registro
                fieldCedula.setText("");
                fieldPlaca.setText("");
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Cédula debe ser un número válido.", "Error",
                        JOptionPane.ERROR_MESSAGE);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error al registrar la devolución: " + ex.getMessage(), "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        });

        formularioFinalizarAlquiler.add(btnFinalizarAlquiler, BorderLayout.SOUTH);

        // Agrega el panel al CardLayout
        cards.add(formularioFinalizarAlquiler, "FinalizarAlquiler");
        cardLayout.show(cards, "FinalizarAlquiler");
    }

}
