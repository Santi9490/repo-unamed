package Interaz;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import aplicacion_clientes.RegistrationCliente;
import inventario.Categorias;
import inventario.Vehiculo;
import pagos.CreditCardInfo;
import pagos.PayPalGateway;
import pagos.PayUGateway;
import pagos.PaymentGateway;
import pagos.PaymentInfo;
import pagos.PaymentResult;

import java.util.ArrayList;
import java.util.Arrays;
import procesamiento.Fecha;
import procesamiento.LoaderFerreteria;
import reserva_alquiler.Alquiler;
import reserva_alquiler.Cliente;
import reserva_alquiler.Reserva;
import reserva_alquiler.Seguros;
import sedes.Empleado;
import sedes.Sede;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class AdminLocGUI extends JFrame {

    /**
	 * 
	 */
	private static final long serialVersionUID = 935476521344484866L;
	private LoaderFerreteria loaderFerreteria = LoaderFerreteria.newFerreteria();
    private Empleado empleado;

    private JMenuBar menuBar;
    private JPanel cards; // Panel que utiliza CardLayout
    private CardLayout cardLayout; // El CardLayout en sí
    // Menus
    private JMenu menuVehiculos, menuReservas, menuAlquileres, menuUsuarios, menuReportes;
    // Items menu Vehiculos
    private JMenuItem menuItemListarVehiculosDisponibles, menuItemListarVehiculosAlquilados,
            menuItemPonervehiculoMantenimiento, menuItemResivirvehiculoMantenimiento, menuItemRegistrarTranslado;
    // Items menu Usuarios
    private JMenuItem menuItemRegistrarUsuario, menuItemListarUsaurios, menuItemeliminarEmpleado;
    // Items menu Reservas
    private JMenuItem menuItemRegistrarReserva, menuItemListarReservas, menuItemeConfirmarRecogida,
            menuItemCancelarReserva;
    // Items menu Alquiler
    private JMenuItem menuItemRegistrarAlquiler, menuItemverHisAlqVehiculo, menuItemeFinalizarAlquiler;
    // Items menu Reportes
    private JMenuItem menuItemGenerarHisVehiculo, menuItemGenerReporteEmple, menuItemReporteVehiculo,
            menuItemReporteAlquiler;

    public AdminLocGUI() {
        // Configuración inicial de la ventana

        empleado = loaderFerreteria.traer_admin_local();
        setTitle("Administrador Local");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Configuración de la barra de menú
        menuBar = new JMenuBar();

        // Menú Usuarios
        menuUsuarios = new JMenu("Usuarios");

        menuItemRegistrarUsuario = new JMenuItem("Registrar nuevo usuario");
        menuItemRegistrarUsuario.addActionListener(this::mostrarFormularioRegistrarUsuario);
        menuUsuarios.add(menuItemRegistrarUsuario);

        menuItemListarUsaurios = new JMenuItem("Ver listado de empleados");
        menuItemListarUsaurios.addActionListener(this::mostrarListardoUsuarios);
        menuUsuarios.add(menuItemListarUsaurios);

        menuItemeliminarEmpleado = new JMenuItem("Eliminar un empleado");
        menuItemeliminarEmpleado.addActionListener(this::mostrarEliminarEmpleado);
        menuUsuarios.add(menuItemeliminarEmpleado);

        menuBar.add(menuUsuarios);

        // Menú Vehiculos
        menuVehiculos = new JMenu("Vehiculos");

        menuItemListarVehiculosDisponibles = new JMenuItem("Ver vehiculos disponibles");
        menuItemListarVehiculosDisponibles.addActionListener(this::mostrarListarVehiculosDisponibles);
        menuVehiculos.add(menuItemListarVehiculosDisponibles);

        menuItemListarVehiculosAlquilados = new JMenuItem("Ver vehiculos alquilados");
        menuItemListarVehiculosAlquilados.addActionListener(this::mostrarListarVehiculosAlquilados);
        menuVehiculos.add(menuItemListarVehiculosAlquilados);

        menuItemPonervehiculoMantenimiento = new JMenuItem("Poner Vehiculo Mantenimiento");
        menuItemPonervehiculoMantenimiento.addActionListener(this::mostrarRegistroMantenimiento);
        menuVehiculos.add(menuItemPonervehiculoMantenimiento);

        menuItemResivirvehiculoMantenimiento = new JMenuItem("Resivir Vehiculo Mantenimiento");
        menuItemResivirvehiculoMantenimiento.addActionListener(this::mostrarResivirVehMantenimiento);
        menuVehiculos.add(menuItemResivirvehiculoMantenimiento);

        menuItemRegistrarTranslado = new JMenuItem("Registrar Translado");
        menuItemRegistrarTranslado.addActionListener(this::mostrarRegistrarTranslado);
        menuVehiculos.add(menuItemRegistrarTranslado);

        menuBar.add(menuVehiculos);

        // Menú Reservas
        menuReservas = new JMenu("Reservas");

        menuItemRegistrarReserva = new JMenuItem("Registrar reserva");
        menuItemRegistrarReserva.addActionListener(this::mostrarFormularioRegistrarReserva);
        menuReservas.add(menuItemRegistrarReserva);

        menuItemListarReservas = new JMenuItem("Ver listado de reservas");
        menuItemListarReservas.addActionListener(this::mostrarListarReservas);
        menuReservas.add(menuItemListarReservas);

        menuItemeConfirmarRecogida = new JMenuItem("Confirmar Recogida de un Vehiculo");
        menuItemeConfirmarRecogida.addActionListener(this::mostrarConfirmarRecogida);
        menuReservas.add(menuItemeConfirmarRecogida);

        menuItemCancelarReserva = new JMenuItem("Cancelar reserva");
        menuItemCancelarReserva.addActionListener(this::mostrarFormularioCancelarReserva);
        menuReservas.add(menuItemCancelarReserva);

        menuBar.add(menuReservas);

        // Menú Alquileres
        menuAlquileres = new JMenu("Alquileres");

        menuItemRegistrarAlquiler = new JMenuItem("Registrar alquiler");
        menuItemRegistrarAlquiler.addActionListener(this::mostrarFormularioRegistrarAlquiler);
        menuAlquileres.add(menuItemRegistrarAlquiler);

        menuItemverHisAlqVehiculo = new JMenuItem("Ver historial Alquileres Vehiculos");
        menuItemverHisAlqVehiculo.addActionListener(this::mostrarListarAlquilerVehiculo);
        menuAlquileres.add(menuItemverHisAlqVehiculo);

        menuItemeFinalizarAlquiler = new JMenuItem("Finalizar Alquiler");
        menuItemeFinalizarAlquiler.addActionListener(this::mostrarFormularioFinalizarAlquiler);
        menuAlquileres.add(menuItemeFinalizarAlquiler);

        menuBar.add(menuAlquileres);

        // Menú Reportes
        menuReportes = new JMenu("Reportes");

        menuItemGenerarHisVehiculo = new JMenuItem("Generar Historial Vehiculo");
        menuItemGenerarHisVehiculo.addActionListener(this::mostrarGenerarHisVehiculo);
        menuReportes.add(menuItemGenerarHisVehiculo);

        menuItemGenerReporteEmple = new JMenuItem("Generar reporte de un empleado");
        menuItemGenerReporteEmple.addActionListener(this::mostrarGenerarReporteEmpleado);
        menuReportes.add(menuItemGenerReporteEmple);

        menuItemReporteVehiculo = new JMenuItem("Generar reporte de un vehículo");
        menuItemReporteVehiculo.addActionListener(this::mostrarGenerarReporteVehiculo);
        menuReportes.add(menuItemReporteVehiculo);

        menuItemReporteAlquiler = new JMenuItem("Generar reporte de alquiler");
        menuItemReporteAlquiler.addActionListener(this::mostrarGenerarReporteAlquiler);
        menuReportes.add(menuItemReporteAlquiler);

        menuBar.add(menuReportes);

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

    // Metodos Usuario

    private void mostrarFormularioRegistrarUsuario(ActionEvent e) {
        JPanel registroUsuarios = new JPanel(new GridLayout(0, 2, 10, 10));

        JButton btnEmpleado = new JButton("Registrar Empleado");
        btnEmpleado.addActionListener(event -> {
            RegistrationEmpleado registrationEmpleado = new RegistrationEmpleado();
            JDialog registrationDialog = new JDialog(this, "Formulario de Registro", true);
            registrationDialog.getContentPane().add(registrationEmpleado);
            registrationDialog.pack();
            registrationDialog.setLocationRelativeTo(this);
            registrationDialog.setVisible(true);
        });

        registroUsuarios.add(btnEmpleado);

        JButton btnCliente = new JButton("Registrar Cliente");
        btnCliente.addActionListener(event -> {
            RegistrationCliente registrationCliente = new RegistrationCliente();
            JDialog registrationDialog = new JDialog(this, "Formulario de Registro", true);
            registrationDialog.getContentPane().add(registrationCliente);
            registrationDialog.pack();
            registrationDialog.setLocationRelativeTo(this);
            registrationDialog.setVisible(true);
        });

        registroUsuarios.add(btnCliente);

        // Agrega un poco de espacio alrededor del panel de formulario
        JPanel panelConMargen = new JPanel(new BorderLayout());
        panelConMargen.add(registroUsuarios, BorderLayout.CENTER);
        panelConMargen.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Agrega el panel al CardLayout
        cards.add(panelConMargen, "RegistrarUsuario");
        cardLayout.show(cards, "RegistrarUsuario");
    }

    private void mostrarListardoUsuarios(ActionEvent e) {
        JPanel panelListadoUsuarios = new JPanel(new BorderLayout());
        List<Sede> sedes = loaderFerreteria.getSedes(); // Reemplaza esto con tu método real

        // Crear el modelo de la tabla
        String[] columnNames = { "Nombre", "Cédula", "Sede" };
        DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0);

        for (Sede sede : sedes) {
            for (Empleado empleado : sede.getEmpleados()) {
                String[] data = { empleado.getNombre(), Integer.toString(empleado.getCedula()), sede.getNombre() };
                tableModel.addRow(data);
            }
        }

        // Crear la tabla con el modelo
        JTable tableUsuarios = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(tableUsuarios);
        panelListadoUsuarios.add(scrollPane, BorderLayout.CENTER);

        // Agrega el panel al CardLayout
        cards.add(panelListadoUsuarios, "ListadoUsuarios");
        cardLayout.show(cards, "ListadoUsuarios");
    }

    private void mostrarEliminarEmpleado(ActionEvent e) {
        // Panel para el formulario de eliminación de empleado
        JPanel formularioEliminarEmpleado = new JPanel(new BorderLayout());

        // Etiqueta y campo de texto para la cédula del empleado
        formularioEliminarEmpleado.add(new JLabel("Cédula del Empleado:"), BorderLayout.NORTH);
        JTextField fieldCedulaEmpleado = new JTextField(10);
        formularioEliminarEmpleado.add(fieldCedulaEmpleado, BorderLayout.CENTER);

        // Botón para eliminar el empleado
        JButton btnEliminarEmpleado = new JButton("Eliminar Empleado");
        btnEliminarEmpleado.addActionListener(event -> {
            String cedulaTexto = fieldCedulaEmpleado.getText().trim();
            if (cedulaTexto.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Debe ingresar la cédula del empleado.", "Error",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }
            try {
                int cedula = Integer.parseInt(cedulaTexto);
                loaderFerreteria.eliminarEmpleado(cedula);
                JOptionPane.showMessageDialog(this, "Empleado eliminado exitosamente.", "Eliminación Exitosa",
                        JOptionPane.INFORMATION_MESSAGE);
                // Limpia el campo después de la eliminación
                fieldCedulaEmpleado.setText("");
            } catch (NumberFormatException nfe) {
                JOptionPane.showMessageDialog(this, "La cédula debe ser un número válido.", "Error",
                        JOptionPane.ERROR_MESSAGE);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error al eliminar el empleado: " + ex.getMessage(), "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        });

        formularioEliminarEmpleado.add(btnEliminarEmpleado, BorderLayout.SOUTH);

        // Agrega el panel al CardLayout
        cards.add(formularioEliminarEmpleado, "EliminarEmpleado");
        cardLayout.show(cards, "EliminarEmpleado");
    }

    // Metodos Vehiculos

    private void mostrarListarVehiculosDisponibles(ActionEvent e) {
        // Panel para listar vehículos disponibles
        JPanel panelListarVehiculosDisponibles = new JPanel(new BorderLayout());

        // Encabezados para la tabla
        String[] columnNames = { "Placa", "Marca", "Modelo" };

        // Obtener los vehículos disponibles de la sede del empleado
        List<Vehiculo> vehiculosDisponibles = empleado.getSede().getVehiculos().stream()
                .filter(Vehiculo::disponible)
                .collect(Collectors.toList());

        // Datos para la tabla
        String[][] data = new String[vehiculosDisponibles.size()][3];
        for (int i = 0; i < vehiculosDisponibles.size(); i++) {
            Vehiculo carro = vehiculosDisponibles.get(i);
            data[i][0] = carro.getPlaca();
            data[i][1] = carro.getMarca();
            data[i][2] = carro.getModelo();
        }

        // Crear la tabla con los datos
        JTable table = new JTable(data, columnNames);
        JScrollPane scrollPane = new JScrollPane(table);
        table.setFillsViewportHeight(true);

        // Añadir la tabla al panel
        panelListarVehiculosDisponibles.add(scrollPane, BorderLayout.CENTER);

        // Agrega el panel al CardLayout
        cards.add(panelListarVehiculosDisponibles, "ListarVehiculosDisponibles");
        cardLayout.show(cards, "ListarVehiculosDisponibles");
    }

    private void mostrarListarVehiculosAlquilados(ActionEvent e) {
        JPanel panelListarVehiculosAlquilados = new JPanel(new BorderLayout());

        // Tabla para mostrar los vehículos alquilados que salieron
        String[] columnas = { "Placa", "Marca", "Modelo" };
        DefaultTableModel modeloSalidos = new DefaultTableModel(columnas, 0);
        JTable tablaSalidos = new JTable(modeloSalidos);
        for (Vehiculo carro : loaderFerreteria.getVehiculos()) {
            if (carro.getAlquilado() != null
                    && carro.getAlquilado().getLugarRecogida().getNombre().equals(empleado.getSede().getNombre())) {
                modeloSalidos.addRow(new Object[] { carro.getPlaca(), carro.getMarca(), carro.getModelo() });
            }
        }

        // Tabla para mostrar los vehículos alquilados que llegarán
        DefaultTableModel modeloLlegaran = new DefaultTableModel(columnas, 0);
        JTable tablaLlegaran = new JTable(modeloLlegaran);
        for (Vehiculo carro : empleado.getSede().getVehiculos()) {
            if (carro.getAlquilado() != null
                    && carro.getAlquilado().getLugarDejada().getNombre().equals(empleado.getSede().getNombre())) {
                modeloLlegaran.addRow(new Object[] { carro.getPlaca(), carro.getMarca(), carro.getModelo() });
            }
        }

        // Añadir tablas al panel
        panelListarVehiculosAlquilados.add(new JLabel("Vehículos alquilados que salieron de la sede:"),
                BorderLayout.NORTH);
        panelListarVehiculosAlquilados.add(new JScrollPane(tablaSalidos), BorderLayout.CENTER);
        panelListarVehiculosAlquilados.add(new JLabel("Vehículos alquilados que llegarán a la sede:"),
                BorderLayout.SOUTH);
        panelListarVehiculosAlquilados.add(new JScrollPane(tablaLlegaran), BorderLayout.SOUTH);

        // Agregar el panel al CardLayout
        cards.add(panelListarVehiculosAlquilados, "ListarVehiculosAlquilados");
        cardLayout.show(cards, "ListarVehiculosAlquilados");
    }

    private void mostrarRegistroMantenimiento(ActionEvent e) {
        JPanel formularioMantenimiento = new JPanel();
        formularioMantenimiento.setLayout(new BoxLayout(formularioMantenimiento, BoxLayout.Y_AXIS));

        // Campo para la placa del vehículo
        formularioMantenimiento.add(new JLabel("Placa del vehículo:"));
        JTextField fieldPlaca = new JTextField(10);
        formularioMantenimiento.add(fieldPlaca);

        // Opción para limpieza
        formularioMantenimiento.add(new JLabel("El vehículo estará en limpieza? (Y/N):"));
        JTextField fieldLimpieza = new JTextField(2);
        formularioMantenimiento.add(fieldLimpieza);

        // Opción para mantenimiento
        formularioMantenimiento.add(new JLabel("El vehículo estará en mantenimiento? (Y/N):"));
        JTextField fieldMantenimiento = new JTextField(2);
        formularioMantenimiento.add(fieldMantenimiento);

        // Campo para los días en mantenimiento
        formularioMantenimiento.add(new JLabel("Días que estará en mantenimiento:"));
        JTextField fieldDias = new JTextField(5);
        formularioMantenimiento.add(fieldDias);

        // Botón para enviar el formulario
        JButton btnMantenimiento = new JButton("Actualizar Vehículo");
        btnMantenimiento.addActionListener(event -> {
            try {
                String placa = fieldPlaca.getText().trim();
                boolean limpieza = parseYesNoInput(fieldLimpieza.getText().trim());
                boolean mantenimiento = parseYesNoInput(fieldMantenimiento.getText().trim());
                short dias = Short.parseShort(fieldDias.getText().trim());

                loaderFerreteria.ponerMantenimientoCarro(limpieza, mantenimiento, placa, dias);
                JOptionPane.showMessageDialog(this, "Vehículo " + placa + " actualizado.", "Actualización Exitosa",
                        JOptionPane.INFORMATION_MESSAGE);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error al actualizar: " + ex.getMessage(), "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        });
        formularioMantenimiento.add(btnMantenimiento);

        cards.add(formularioMantenimiento, "RegistroMantenimiento");
        cardLayout.show(cards, "RegistroMantenimiento");
    }

    // Función auxiliar para analizar las entradas de sí/no
    private boolean parseYesNoInput(String input) {
        input = input.toLowerCase();
        return input.equals("y") || input.equals("yes") || input.equals("si");
    }

    private void mostrarResivirVehMantenimiento(ActionEvent e) {
        // Panel para el formulario de sacar un vehículo de mantenimiento
        JPanel formularioSacarMantenimiento = new JPanel(new BorderLayout());

        // Etiqueta y campo de texto para la placa del vehículo
        formularioSacarMantenimiento.add(new JLabel("Placa del Vehículo:"), BorderLayout.NORTH);
        JTextField fieldPlacaVehiculo = new JTextField(10);
        formularioSacarMantenimiento.add(fieldPlacaVehiculo, BorderLayout.CENTER);

        // Botón para sacar de mantenimiento
        JButton btnSacarMantenimiento = new JButton("Sacar de Mantenimiento");
        btnSacarMantenimiento.addActionListener(event -> {
            String placa = fieldPlacaVehiculo.getText().trim();
            if (placa.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Debe ingresar la placa del vehículo.", "Error",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }
            try {
                loaderFerreteria.quitarMantenimiento(placa);
                JOptionPane.showMessageDialog(this, "Vehículo " + placa + " actualizado.", "Operación Exitosa",
                        JOptionPane.INFORMATION_MESSAGE);
                fieldPlacaVehiculo.setText(""); // Limpia el campo después de la operación
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

    private void mostrarRegistrarTranslado(ActionEvent e) {
        // Panel para el formulario de traslado de vehículo
        JPanel formularioRegistrarTranslado = new JPanel(new BorderLayout());

        // Panel para los campos de entrada
        JPanel panelCampos = new JPanel(new GridLayout(2, 2, 10, 10)); // GridLayout para ordenar los campos

        // Campo para la placa del vehículo
        panelCampos.add(new JLabel("Placa del Vehículo:"));
        JTextField fieldPlaca = new JTextField(10);
        panelCampos.add(fieldPlaca);

        // Campo para la sede de destino
        panelCampos.add(new JLabel("Sede de Destino:"));
        JTextField fieldSede = new JTextField(10);
        panelCampos.add(fieldSede);

        formularioRegistrarTranslado.add(panelCampos, BorderLayout.CENTER);

        // Botón para realizar el traslado
        JButton btnTrasladar = new JButton("Trasladar Vehículo");
        btnTrasladar.addActionListener(event -> {
            String placa = fieldPlaca.getText().trim();
            String sede = fieldSede.getText().trim();

            if (placa.isEmpty() || sede.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Debe completar todos los campos.", "Error",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            try {
                loaderFerreteria.trasladoVehiculo(placa, sede);
                JOptionPane.showMessageDialog(this, "Vehículo trasladado exitosamente.", "Traslado Exitoso",
                        JOptionPane.INFORMATION_MESSAGE);
                // Limpia los campos después del traslado
                fieldPlaca.setText("");
                fieldSede.setText("");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error al trasladar el vehículo: " + ex.getMessage(), "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        });

        formularioRegistrarTranslado.add(btnTrasladar, BorderLayout.SOUTH);

        // Agrega el panel al CardLayout
        cards.add(formularioRegistrarTranslado, "RegistrarTranslado");
        cardLayout.show(cards, "RegistrarTranslado");
    }

    // Metodos Reservas

    private void mostrarFormularioRegistrarReserva(ActionEvent e) {
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

    private void mostrarListarReservas(ActionEvent e) {
        JPanel panelListarReservas = new JPanel(new BorderLayout());
        panelListarReservas.add(new JLabel("Reservas actuales:"), BorderLayout.NORTH);

        // Obtener la lista de reservas
        List<Reserva> reservas = loaderFerreteria.getReservas();

        // Crear los nombres de las columnas y los datos para la tabla
        String[] columnNames = { "Cliente", "Vehículo", "Fecha Inicio", "Fecha Fin" };
        Object[][] data = new Object[reservas.size()][6];

        for (int i = 0; i < reservas.size(); i++) {
            Reserva reserva = reservas.get(i);
            data[i][1] = reserva.getCliente().getNombre();
            data[i][2] = reserva.getCarroAsignado().getMarca() + " " + reserva.getCarroAsignado().getModelo();
            data[i][3] = reserva.getFechaInicio();
            data[i][4] = reserva.getFechaFinal();
        }

        // Crear la tabla y agregarla al panel
        JTable table = new JTable(data, columnNames);
        JScrollPane scrollPane = new JScrollPane(table);
        panelListarReservas.add(scrollPane, BorderLayout.CENTER);

        // Agrega el panel al CardLayout
        cards.add(panelListarReservas, "ListarReservas");
        cardLayout.show(cards, "ListarReservas");
    }

    private void mostrarConfirmarRecogida(ActionEvent e) {
        JPanel panelConfirmarRecogida = new JPanel();
        panelConfirmarRecogida.setLayout(new BoxLayout(panelConfirmarRecogida, BoxLayout.Y_AXIS));

        // Cédula del cliente
        panelConfirmarRecogida.add(new JLabel("Cédula del cliente:"));
        JTextField fieldCedulaCliente = new JTextField(10);
        panelConfirmarRecogida.add(fieldCedulaCliente);

        // Fecha actual
        panelConfirmarRecogida.add(new JLabel("Fecha actual (MM/dd/yy):"));
        JTextField fieldFechaActual = new JTextField(10);
        panelConfirmarRecogida.add(fieldFechaActual);

        // Lista de seguros
        List<Seguros> segurosDisponibles = loaderFerreteria.getSeguros();
        JCheckBox[] checkBoxesSeguros = new JCheckBox[segurosDisponibles.size()];
        int i = 0;
        for (Seguros seguro : segurosDisponibles) {
            checkBoxesSeguros[i] = new JCheckBox(seguro.getNombre());
            panelConfirmarRecogida.add(checkBoxesSeguros[i]);
            i++;
        }

        // Modificaciones de la reserva
        JCheckBox checkModificacion = new JCheckBox("Modificar reserva");
        panelConfirmarRecogida.add(checkModificacion);

        JTextField fieldFechaRecogida = new JTextField(10);
        JTextField fieldSedeDevolucion = new JTextField(10);
        JTextField fieldHoraInicio = new JTextField(5);
        JTextField fieldHoraFin = new JTextField(5);

        checkModificacion.addActionListener(event -> {
            boolean selected = checkModificacion.isSelected();
            fieldFechaRecogida.setEnabled(selected);
            fieldSedeDevolucion.setEnabled(selected);
            fieldHoraInicio.setEnabled(selected);
            fieldHoraFin.setEnabled(selected);
        });

        panelConfirmarRecogida.add(new JLabel("Fecha de recogida (MM/dd/yy):"));
        panelConfirmarRecogida.add(fieldFechaRecogida);
        panelConfirmarRecogida.add(new JLabel("Sede de devolución:"));
        panelConfirmarRecogida.add(fieldSedeDevolucion);
        panelConfirmarRecogida.add(new JLabel("Hora inicio entrega:"));
        panelConfirmarRecogida.add(fieldHoraInicio);
        panelConfirmarRecogida.add(new JLabel("Hora fin entrega:"));
        panelConfirmarRecogida.add(fieldHoraFin);

        // Botón de confirmación
        JButton btnConfirmarRecogida = new JButton("Confirmar Recogida");
        btnConfirmarRecogida.addActionListener(event -> {
            try {
                int cedulaCliente = Integer.parseInt(fieldCedulaCliente.getText());
                String fechaInicio = fieldFechaActual.getText();
                List<String> segurosElegidos = new ArrayList<>();
                for (JCheckBox checkBox : checkBoxesSeguros) {
                    if (checkBox.isSelected()) {
                        segurosElegidos.add(checkBox.getText());
                    }
                }

                String[] modificacion = checkModificacion.isSelected()
                        ? new String[] { fieldFechaRecogida.getText(), fieldSedeDevolucion.getText(),
                                fieldHoraInicio.getText(), fieldHoraFin.getText() }
                        : null;

                loaderFerreteria.recogidaVehiculo(cedulaCliente, fechaInicio, segurosElegidos, modificacion);
                JOptionPane.showMessageDialog(panelConfirmarRecogida, "Vehículo entregado con éxito.");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(panelConfirmarRecogida,
                        "Error al registrar la recogida del vehículo: " + ex.getMessage(), "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        });
        panelConfirmarRecogida.add(btnConfirmarRecogida);

        // Agrega el panel al CardLayout
        cards.add(panelConfirmarRecogida, "ConfirmarRecogida");
        cardLayout.show(cards, "ConfirmarRecogida");
    }

    private void mostrarFormularioCancelarReserva(ActionEvent e) {
        // Panel para el formulario de cancelación de reserva
        JPanel formularioCancelarReserva = new JPanel(new BorderLayout());

        // Panel para los campos de texto
        JPanel panelCampos = new JPanel(new GridLayout(2, 2)); // Grid de 2x2 para etiquetas y campos

        // Etiqueta y campo de texto para la cédula del cliente
        panelCampos.add(new JLabel("Cédula del cliente:"));
        JTextField fieldCedula = new JTextField(10);
        panelCampos.add(fieldCedula);

        // Etiqueta y campo de texto para la categoría reservada
        panelCampos.add(new JLabel("Categoría reservada:"));
        JTextField fieldCategoria = new JTextField(15);
        panelCampos.add(fieldCategoria);

        formularioCancelarReserva.add(panelCampos, BorderLayout.CENTER);

        // Botón para cancelar la reserva
        JButton btnCancelarReserva = new JButton("Cancelar Reserva");
        btnCancelarReserva.addActionListener(event -> {
            try {
                int cedula = Integer.parseInt(fieldCedula.getText().trim());
                String categoria = fieldCategoria.getText().trim();
                if (categoria.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Debe ingresar la categoría reservada.", "Error",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }
                loaderFerreteria.cancelarReserva(cedula, categoria);
                JOptionPane.showMessageDialog(this, "Reserva cancelada exitosamente.", "Cancelación Exitosa",
                        JOptionPane.INFORMATION_MESSAGE);
                // Limpia los campos después de la cancelación
                fieldCedula.setText("");
                fieldCategoria.setText("");
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "La cédula debe ser un número.", "Error",
                        JOptionPane.ERROR_MESSAGE);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error al cancelar la reserva: " + ex.getMessage(), "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        });

        formularioCancelarReserva.add(btnCancelarReserva, BorderLayout.SOUTH);

        // Agrega el panel al CardLayout
        cards.add(formularioCancelarReserva, "CancelarReserva");
        cardLayout.show(cards, "CancelarReserva");
    }

    // Metodos Alquiler

    private void mostrarFormularioRegistrarAlquiler(ActionEvent e) {
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

    private void mostrarListarAlquilerVehiculo(ActionEvent e) {
        JPanel panelListarAlquilerVehiculo = new JPanel(new BorderLayout());

        // Etiqueta y campo de texto para ingresar la placa del vehículo
        panelListarAlquilerVehiculo.add(new JLabel("Placa del Vehículo:"), BorderLayout.NORTH);
        JTextField fieldPlaca = new JTextField(10);
        panelListarAlquilerVehiculo.add(fieldPlaca, BorderLayout.CENTER);

        // Botón para mostrar el historial de alquileres
        JButton btnMostrarAlquileres = new JButton("Mostrar Historial de Alquileres");
        btnMostrarAlquileres.addActionListener(event -> {
            String placa = fieldPlaca.getText().trim();
            if (placa.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Debe ingresar la placa del vehículo.", "Error",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }
            try {
                Vehiculo vehiculo = loaderFerreteria.buscarVehiculo(placa);
                if (vehiculo == null) {
                    JOptionPane.showMessageDialog(this, "Vehículo no encontrado.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                String historial = "Historial de alquileres del vehículo " + vehiculo.getPlaca() + ":\n";
                for (Alquiler alquiler : vehiculo.getHistorial()) {
                    historial += alquiler.getInfo() + "\n";
                }
                JOptionPane.showMessageDialog(this, historial, "Historial de Alquileres",
                        JOptionPane.INFORMATION_MESSAGE);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error al buscar el vehículo: " + ex.getMessage(), "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        });

        panelListarAlquilerVehiculo.add(btnMostrarAlquileres, BorderLayout.SOUTH);

        // Agrega el panel al CardLayout
        cards.add(panelListarAlquilerVehiculo, "ListarAlquilerVehiculo");
        cardLayout.show(cards, "ListarAlquilerVehiculo");
    }

    private void mostrarFormularioFinalizarAlquiler(ActionEvent e) {
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

    // Metodos Reportes

    private void mostrarGenerarHisVehiculo(ActionEvent e) {
        JPanel panelGenerarHisVehiculo = new JPanel(new BorderLayout());

        // Etiqueta y campo de texto para ingresar la placa del vehículo
        panelGenerarHisVehiculo.add(new JLabel("Placa del Vehículo:"), BorderLayout.NORTH);
        JTextField fieldPlaca = new JTextField(10);
        panelGenerarHisVehiculo.add(fieldPlaca, BorderLayout.CENTER);

        // Botón para buscar el historial del vehículo
        JButton btnBuscarHistorial = new JButton("Buscar Historial");
        btnBuscarHistorial.addActionListener(event -> {
            String placa = fieldPlaca.getText().trim();
            if (placa.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Debe ingresar una placa.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            try {
                Vehiculo vehiculo = loaderFerreteria.buscarVehiculo(placa);
                if (vehiculo == null) {
                    JOptionPane.showMessageDialog(this, "Vehículo no encontrado.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                // Mostrar el historial de alquileres
                StringBuilder historialStr = new StringBuilder(
                        "Historial de alquileres del vehículo " + vehiculo.getPlaca() + ":\n");
                for (Alquiler alquiler : vehiculo.getHistorial()) {
                    historialStr.append(alquiler.getInfo()).append("\n");
                }
                JOptionPane.showMessageDialog(this, historialStr.toString(), "Historial Vehículo",
                        JOptionPane.INFORMATION_MESSAGE);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error al buscar el vehículo: " + ex.getMessage(), "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        });

        panelGenerarHisVehiculo.add(btnBuscarHistorial, BorderLayout.SOUTH);

        // Agrega el panel al CardLayout
        cards.add(panelGenerarHisVehiculo, "GenerarHisVehiculo");
        cardLayout.show(cards, "GenerarHisVehiculo");
    }

    private void mostrarGenerarReporteEmpleado(ActionEvent e) {
        // Panel para el formulario de generar reporte de empleado
        JPanel formularioGenerarReporteEmpleado = new JPanel(new BorderLayout());

        // Etiqueta y campo de texto para ingresar la cédula del empleado
        formularioGenerarReporteEmpleado.add(new JLabel("Cédula del empleado:"), BorderLayout.NORTH);
        JTextField fieldCedulaEmpleado = new JTextField(10);
        formularioGenerarReporteEmpleado.add(fieldCedulaEmpleado, BorderLayout.CENTER);

        // Botón para generar el reporte del empleado
        JButton btnGenerarReporte = new JButton("Generar Reporte");
        btnGenerarReporte.addActionListener(event -> {
            String cedulaStr = fieldCedulaEmpleado.getText().trim();
            if (cedulaStr.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Debe ingresar la cédula del empleado.", "Error",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }
            try {
                int cedula = Integer.parseInt(cedulaStr);
                Empleado empleado = loaderFerreteria.buscarEmpleadoCedula(cedula);
                if (empleado != null) {
                    JOptionPane.showMessageDialog(this, empleado.getInfoEmpleado(), "Información del Empleado",
                            JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(this, "Empleado no encontrado.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "La cédula debe ser un número válido.", "Error",
                        JOptionPane.ERROR_MESSAGE);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error al buscar el empleado: " + ex.getMessage(), "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        });

        formularioGenerarReporteEmpleado.add(btnGenerarReporte, BorderLayout.SOUTH);

        // Agrega el panel al CardLayout
        cards.add(formularioGenerarReporteEmpleado, "GenerarReporteEmpleado");
        cardLayout.show(cards, "GenerarReporteEmpleado");
    }

    private void mostrarGenerarReporteVehiculo(ActionEvent e) {
        // Panel para el formulario de generación de reporte
        JPanel panelGenerarReporteVehiculo = new JPanel(new BorderLayout());

        // Etiqueta y campo de texto para la placa del vehículo
        panelGenerarReporteVehiculo.add(new JLabel("Placa del Vehículo:"), BorderLayout.NORTH);
        JTextField fieldPlacaVehiculo = new JTextField(10);
        panelGenerarReporteVehiculo.add(fieldPlacaVehiculo, BorderLayout.CENTER);

        // Botón para generar el reporte
        JButton btnGenerarReporte = new JButton("Generar Reporte");
        btnGenerarReporte.addActionListener(event -> {
            String placa = fieldPlacaVehiculo.getText().trim();
            if (placa.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Debe ingresar la placa del vehículo.", "Error",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }
            try {
                Vehiculo vehiculo = loaderFerreteria.buscarVehiculo(placa);
                if (vehiculo != null) {
                    StringBuilder reporte = new StringBuilder();
                    reporte.append(vehiculo.getInfo()).append("\nHistorial de alquileres del vehículo ")
                            .append(vehiculo.getPlaca()).append(":\n");
                    for (Alquiler alquiler : vehiculo.getHistorial()) {
                        reporte.append(alquiler.getInfo()).append("\n");
                    }
                    reporte.append("\nUbicación actual del vehículo: ").append(vehiculo.ubicacionVehiculo());
                    JOptionPane.showMessageDialog(this, reporte.toString(), "Reporte de Vehículo",
                            JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(this, "Vehículo no encontrado.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error al generar el reporte: " + ex.getMessage(), "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        });

        panelGenerarReporteVehiculo.add(btnGenerarReporte, BorderLayout.SOUTH);

        // Agrega el panel al CardLayout
        cards.add(panelGenerarReporteVehiculo, "GenerarReporteVehiculo");
        cardLayout.show(cards, "GenerarReporteVehiculo");
    }

    private void mostrarGenerarReporteAlquiler(ActionEvent e) {
        // Panel para el formulario de generación de reporte de alquiler
        JPanel panelGenerarReporteAlquiler = new JPanel(new BorderLayout());

        // Etiqueta y campo de texto para la cédula del cliente
        panelGenerarReporteAlquiler.add(new JLabel("Cédula del Cliente:"), BorderLayout.NORTH);
        JTextField fieldCedulaCliente = new JTextField(20);
        panelGenerarReporteAlquiler.add(fieldCedulaCliente, BorderLayout.CENTER);

        // Botón para generar el reporte
        JButton btnGenerarReporte = new JButton("Generar Reporte");
        btnGenerarReporte.addActionListener(event -> {
            String cedula = fieldCedulaCliente.getText().trim();
            if (cedula.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Debe ingresar la cédula del cliente.", "Error",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }
            try {
                int cedulaInt = Integer.parseInt(cedula);
                Cliente cliente = loaderFerreteria.buscarCliente(cedulaInt);
                if (cliente == null) {
                    JOptionPane.showMessageDialog(this, "Cliente no encontrado.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                String reporte = "Historial de reservas del cliente " + cliente.getNombre() + ":\n";
                for (Alquiler alquiler : cliente.getAlquileres()) {
                    reporte += alquiler.getInfo() + "\n";
                }
                JOptionPane.showMessageDialog(this, reporte, "Reporte de Alquileres", JOptionPane.INFORMATION_MESSAGE);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "La cédula debe ser un número válido.", "Error",
                        JOptionPane.ERROR_MESSAGE);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error al generar el reporte: " + ex.getMessage(), "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        });

        panelGenerarReporteAlquiler.add(btnGenerarReporte, BorderLayout.SOUTH);

        // Agrega el panel al CardLayout
        cards.add(panelGenerarReporteAlquiler, "GenerarReporteAlquiler");
        cardLayout.show(cards, "GenerarReporteAlquiler");
    }

}
