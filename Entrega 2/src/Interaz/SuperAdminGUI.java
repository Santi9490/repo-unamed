package Interaz;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.Date;

import inventario.Categorias;
import inventario.Vehiculo;
import procesamiento.LoaderFerreteria;
import reserva_alquiler.Alquiler;
import reserva_alquiler.Seguros;
import sedes.AdministradorLocal;
import sedes.Empleado;
import sedes.Sede;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.text.SimpleDateFormat;
import java.util.List;

public class SuperAdminGUI extends JFrame {

    private LoaderFerreteria loaderFerreteria = LoaderFerreteria.newFerreteria();

    private JMenuBar menuBar;
    private JPanel cards; // Panel que utiliza CardLayout
    private CardLayout cardLayout; // El CardLayout en sí
    // Menus
    private JMenu menuVehiculos, menuCategorias, menuSedes, menuUsuarios, menuTarifasSeguros;
    // Items menu Vehiculos
    private JMenuItem menuItemRegistrarVehiculo, menuItemListarVehiculos, menuItemdarbajaVehiculo,
            menuItemverEstadovehiculo, menuItemverHistorialVehiculo;
    // Items menu Categorias
    private JMenuItem menuItemRegistrarCategoria, menuItemListarCategorias, menuItemeditNomCategoria,
            menuItemCambiarCategoria, menuItemAgregarPrecCategoria;
    // Items menu Sedes
    private JMenuItem menuItemRegistrarSede, menuItemListarSedes, menuItemeditSede, menuItemVerVehiculosSede;
    // Items menu Usuarios
    private JMenuItem menuItemRegistrarUsuario, menuItemListarUsaurios, menuItemeliminarEmpleado,
            menuItemCambiarSedeEmpleado;
    // Items menu TarifasSeguros
    private JMenuItem menuItemRegistrarTarifa, menuItemListarTarifas, menuItemEditTarifa, menuItemEliminarTarifa,
            menuItemRegisSeguros, menuItemListarSeguros, menuItemEditSeguros, menuItemEliminarSeguro;

    public SuperAdminGUI() {
        // Configuración inicial de la ventana

        setTitle("Super Administrador");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Configuración de la barra de menú
        menuBar = new JMenuBar();

        // Menú Vehículos
        menuVehiculos = new JMenu("Vehículos");
        menuItemRegistrarVehiculo = new JMenuItem("Registrar nuevo vehículo");
        menuItemRegistrarVehiculo.addActionListener(this::mostrarFormularioRegistrarVehiculo);
        menuVehiculos.add(menuItemRegistrarVehiculo);

        menuItemListarVehiculos = new JMenuItem("Listado de vehículos");
        menuItemListarVehiculos.addActionListener(this::mostrarListadoVehiculos);
        menuVehiculos.add(menuItemListarVehiculos);

        menuItemdarbajaVehiculo = new JMenuItem("Poner de baja un Vehículo");
        menuItemdarbajaVehiculo.addActionListener(this::mostrarBajarVehiculos);
        menuVehiculos.add(menuItemdarbajaVehiculo);

        menuItemverEstadovehiculo = new JMenuItem("Ver Estado Vehículo");
        menuItemverEstadovehiculo.addActionListener(this::mostrarEstadoVehiculos);
        menuVehiculos.add(menuItemverEstadovehiculo);

        menuItemverHistorialVehiculo = new JMenuItem("Ver historial Vehículo");
        menuItemverHistorialVehiculo.addActionListener(this::mostrarHisVehiculos);
        menuVehiculos.add(menuItemverHistorialVehiculo);

        menuBar.add(menuVehiculos);

        // Menú Categorias
        menuCategorias = new JMenu("Categorias");
        menuItemRegistrarCategoria = new JMenuItem("Registrar nueva Categoria");
        menuItemRegistrarCategoria.addActionListener(this::mostrarFormularioRegistrarCategoria);
        menuCategorias.add(menuItemRegistrarCategoria);

        menuItemListarCategorias = new JMenuItem("Listado de Categorias");
        menuItemListarCategorias.addActionListener(this::mostrarListadoCategoria);
        menuCategorias.add(menuItemListarCategorias);

        menuItemeditNomCategoria = new JMenuItem("Editar Nombre Categoria");
        menuItemeditNomCategoria.addActionListener(this::mostrarEditarCategoria);
        menuCategorias.add(menuItemeditNomCategoria);

        menuItemCambiarCategoria = new JMenuItem("Cambiar la Categoria de un Vehiculo");
        menuItemCambiarCategoria.addActionListener(this::mostrarCambiarVehCategoria);
        menuCategorias.add(menuItemCambiarCategoria);

        menuItemAgregarPrecCategoria = new JMenuItem("Agregar precio a una Categoria");
        menuItemAgregarPrecCategoria.addActionListener(this::mostrarAgregarPrecCategoria);
        menuCategorias.add(menuItemAgregarPrecCategoria);

        menuBar.add(menuCategorias);

        // Menú Sedes
        menuSedes = new JMenu("Sedes");
        menuItemRegistrarSede = new JMenuItem("Registrar nueva Sede");
        menuItemRegistrarSede.addActionListener(this::mostrarFormularioRegistrarSede);
        menuSedes.add(menuItemRegistrarSede);

        menuItemListarSedes = new JMenuItem("Listado de Sedes");
        menuItemListarSedes.addActionListener(this::mostrarListadoSedes);
        menuSedes.add(menuItemListarSedes);

        menuItemeditSede = new JMenuItem("Editar Información de una Sede");
        menuItemeditSede.addActionListener(this::mostrarEditarInfoSede);
        menuSedes.add(menuItemeditSede);

        menuItemVerVehiculosSede = new JMenuItem("Ver vehiculos de una sede");
        menuItemVerVehiculosSede.addActionListener(this::mostrarVehSede);
        menuSedes.add(menuItemVerVehiculosSede);
        menuBar.add(menuSedes);

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

        menuItemCambiarSedeEmpleado = new JMenuItem("Cambiar de sede a un empleado");
        menuItemCambiarSedeEmpleado.addActionListener(this::mostrarCambioSedeEmpleado);
        menuUsuarios.add(menuItemCambiarSedeEmpleado);
        menuBar.add(menuUsuarios);

        // Menú TarifasSeguros
        menuTarifasSeguros = new JMenu("Tarifas y seguros");
        menuItemRegistrarTarifa = new JMenuItem("Registrar nueva Tarifa");
        menuItemRegistrarTarifa.addActionListener(this::mostrarFormularioRegistrarTarifa);
        menuTarifasSeguros.add(menuItemRegistrarTarifa);

        menuItemListarTarifas = new JMenuItem("Ver tarifas actuales");
        menuItemListarTarifas.addActionListener(this::mostrarListadoTarifas);
        menuTarifasSeguros.add(menuItemListarTarifas);

        menuItemEditTarifa = new JMenuItem("Cambiar tarifa existente");
        menuItemEditTarifa.addActionListener(this::mostrarCambioTarifa);
        menuTarifasSeguros.add(menuItemEditTarifa);

        menuItemEliminarTarifa = new JMenuItem("Eliminar tarifa existente");
        menuItemEliminarTarifa.addActionListener(this::mostrarEliminarTarifa);
        menuTarifasSeguros.add(menuItemEliminarTarifa);

        menuItemRegisSeguros = new JMenuItem("Definir nuevos seguros");
        menuItemRegisSeguros.addActionListener(this::mostrarFormularioRegistrarSeguro);
        menuTarifasSeguros.add(menuItemRegisSeguros);

        menuItemListarSeguros = new JMenuItem("Ver listado de seguros ofrecidos");
        menuItemListarSeguros.addActionListener(this::mostrarListadoSeguros);
        menuTarifasSeguros.add(menuItemListarSeguros);

        menuItemEditSeguros = new JMenuItem("Cambiar precio de un seguro");
        menuItemEditSeguros.addActionListener(this::mostrarCambioSeguro);
        menuTarifasSeguros.add(menuItemEditSeguros);

        menuItemEliminarSeguro = new JMenuItem("Eliminar un seguro");
        menuItemEliminarSeguro.addActionListener(this::mostrarEliminarSeguro);
        menuTarifasSeguros.add(menuItemEliminarSeguro);

        menuBar.add(menuTarifasSeguros);

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

    // Vehiculos

    private void mostrarFormularioRegistrarVehiculo(ActionEvent e) {
        JPanel panelRegistrarVehiculo = new JPanel(new GridLayout(0, 2, 10, 10)); // GridLayout para un formulario
                                                                                  // ordenado

        // Etiquetas y campos para el formulario
        panelRegistrarVehiculo.add(new JLabel("Placa:"));
        JTextField fieldPlaca = new JTextField(10);
        panelRegistrarVehiculo.add(fieldPlaca);

        panelRegistrarVehiculo.add(new JLabel("Marca:"));
        JTextField fieldMarca = new JTextField(10);
        panelRegistrarVehiculo.add(fieldMarca);

        panelRegistrarVehiculo.add(new JLabel("Modelo:"));
        JTextField fieldModelo = new JTextField(10);
        panelRegistrarVehiculo.add(fieldModelo);

        panelRegistrarVehiculo.add(new JLabel("Color:"));
        JTextField fieldColor = new JTextField(10);
        panelRegistrarVehiculo.add(fieldColor);

        panelRegistrarVehiculo.add(new JLabel("Pasajeros:"));
        JTextField fieldPasajeros = new JTextField(10);
        panelRegistrarVehiculo.add(fieldPasajeros);

        panelRegistrarVehiculo.add(new JLabel("Tipo de transmición:"));
        JTextField fieldTransmición = new JTextField(10);
        panelRegistrarVehiculo.add(fieldTransmición);

        panelRegistrarVehiculo.add(new JLabel("Categoria:"));
        JTextField fieldCategoria = new JTextField(10);
        panelRegistrarVehiculo.add(fieldCategoria);

        panelRegistrarVehiculo.add(new JLabel("Sede:"));
        JTextField fieldSede = new JTextField(10);
        panelRegistrarVehiculo.add(fieldSede);

        panelRegistrarVehiculo.add(new JLabel("Tipo:"));
        JTextField tipo = new JTextField(10);
        panelRegistrarVehiculo.add(tipo);

        panelRegistrarVehiculo.add(new JLabel("Porcentaje adicional:"));
        JTextField porcentaje = new JTextField(10);
        panelRegistrarVehiculo.add(porcentaje);

        JButton btnRegistrar = new JButton("Registrar");
        btnRegistrar.addActionListener(event -> {
            String placa = fieldPlaca.getText();
            String marca = fieldMarca.getText();
            String modelo = fieldModelo.getText();
            String color = fieldColor.getText();
            String pasajerosStr = fieldPasajeros.getText();
            short pasajeros = Short.parseShort(pasajerosStr);
            String transmicion = fieldTransmición.getText();
            String categoria = fieldCategoria.getText();
            String sede = fieldSede.getText();
            String tipoVehiculo = tipo.getText();
            float porcentajeAdicional = Float.parseFloat(porcentaje.getText());

            loaderFerreteria.crearVehiculo(placa, marca, modelo, color, pasajeros, transmicion, categoria, sede, tipoVehiculo, porcentajeAdicional);

        });

        panelRegistrarVehiculo.add(btnRegistrar);

        // Agrega el panel al CardLayout
        cards.add(panelRegistrarVehiculo, "RegistrarVehiculo");
        cardLayout.show(cards, "RegistrarVehiculo");
    }

    private void mostrarListadoVehiculos(ActionEvent e) {

        // Obtiene las categorías y los vehículos de la lógica de tu aplicación
        List<Categorias> categorias = loaderFerreteria.getCategorias();

        // Calcula el tamaño total de los vehículos para la matriz de datos
        int totalVehiculos = categorias.stream().mapToInt(categoria -> categoria.getCarros().size()).sum();
        Object[][] data = new Object[totalVehiculos][7]; 

        // Llena la matriz de datos con la información de los vehículos
        int vehiculoIndex = 0;
        for (Categorias categoria : categorias) {
            for (Vehiculo carro : categoria.getCarros()) {
                data[vehiculoIndex][0] = carro.getPlaca();
                data[vehiculoIndex][1] = carro.getMarca();
                data[vehiculoIndex][2] = carro.getModelo();
                data[vehiculoIndex][3] = carro.getColor(); // Asumiendo que hay un método getColor() en la clase
                data[vehiculoIndex][4] = carro.getTipo();
                data[vehiculoIndex][5] = carro.getPorcentaje();
                data[vehiculoIndex][6] = carro.getEstado();                                          
                vehiculoIndex++;
            }
        }

        // Define los nombres de las columnas
        String[] columnNames = { "Placa", "Marca", "Modelo", "Color", "Tipo", "PorcentajeAdicional", "Estado" };

        // Crea el JTable con los datos y los nombres de las columnas
        JTable table = new JTable(data, columnNames);

        // Añade un JScrollPane y añade el JTable a este
        JScrollPane scrollPane = new JScrollPane(table);

        // Crea el panel de listado y añade el JScrollPane
        JPanel panelListadoVehiculos = new JPanel(new BorderLayout());
        panelListadoVehiculos.add(scrollPane, BorderLayout.CENTER);

        // Agrega el panel al CardLayout y muestra el panel
        cards.add(panelListadoVehiculos, "ListadoVehiculos");
        cardLayout.show(cards, "ListadoVehiculos");
    }

    private void mostrarBajarVehiculos(ActionEvent e) {
        JPanel panelBajaVehiculos = new JPanel(); // Usa un Layout Manager adecuado
        panelBajaVehiculos.add(new JLabel("Placa del Vehículo:"));
        JTextField fieldPlaca = new JTextField(10);
        panelBajaVehiculos.add(fieldPlaca);

        JButton btnBaja = new JButton("Dar de baja");
        btnBaja.addActionListener(event -> {
            try {
                String placa = fieldPlaca.getText();
                loaderFerreteria.eliminarVehiculo(placa);
                JOptionPane.showMessageDialog(this,
                        "Carro con placa: " + fieldPlaca.getText() + ", eliminado exitosamente.");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error a Eliminar el vehiculo: " + ex.getMessage(), "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        });

        panelBajaVehiculos.add(btnBaja);

        // Agrega el panel al CardLayout
        cards.add(panelBajaVehiculos, "BajaVehiculos");
        cardLayout.show(cards, "BajaVehiculos");
    }

    private void mostrarEstadoVehiculos(ActionEvent e) {
        JPanel panelEstadoVehiculos = new JPanel();
        panelEstadoVehiculos.add(new JLabel("Placa del Vehículo:"));
        JTextField fieldPlaca = new JTextField(10);
        panelEstadoVehiculos.add(fieldPlaca);

        JButton btnConsultar = new JButton("Consultar Estado");
        btnConsultar.addActionListener(event -> {
            String placa = fieldPlaca.getText();
            Vehiculo vehiculo = loaderFerreteria.buscarVehiculo(placa);
            JOptionPane.showMessageDialog(this, vehiculo.ubicacionVehiculo());

        });

        panelEstadoVehiculos.add(btnConsultar);

        // Agrega el panel al CardLayout
        cards.add(panelEstadoVehiculos, "EstadoVehiculos");
        cardLayout.show(cards, "EstadoVehiculos");
    }

    private void mostrarHisVehiculos(ActionEvent e) {
        // Panel principal para el historial de vehículos
        JPanel panelHistorialVehiculos = new JPanel(new BorderLayout());
        panelHistorialVehiculos.add(new JLabel("Placa del Vehículo:"), BorderLayout.NORTH);

        // Campo de texto para ingresar la placa
        JTextField fieldPlaca = new JTextField(10);
        panelHistorialVehiculos.add(fieldPlaca, BorderLayout.NORTH);

        // Modelo para la lista que mostrará el historial
        DefaultListModel<String> listModel = new DefaultListModel<>();
        JList<String> listHistorial = new JList<>(listModel);
        JScrollPane scrollPane = new JScrollPane(listHistorial);
        panelHistorialVehiculos.add(scrollPane, BorderLayout.CENTER);

        // Botón para mostrar el historial
        JButton btnHistorial = new JButton("Mostrar Historial");
        btnHistorial.addActionListener(event -> {
            listModel.clear(); // Limpia la lista antes de mostrar nuevos resultados
            String placa = fieldPlaca.getText();
            if (placa.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Debe ingresar una placa.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            Vehiculo vehiculo = loaderFerreteria.buscarVehiculo(placa);
            if (vehiculo != null) {
                for (Alquiler alquiler : vehiculo.getHistorial()) {
                    listModel.addElement(alquiler.getInfo());
                }
            } else {
                JOptionPane.showMessageDialog(this, "Vehículo no encontrado.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        panelHistorialVehiculos.add(btnHistorial, BorderLayout.SOUTH);

        // Agrega el panel al CardLayout
        cards.add(panelHistorialVehiculos, "HistorialVehiculos");
        cardLayout.show(cards, "HistorialVehiculos");
    }

    // Categorias

    private void mostrarFormularioRegistrarCategoria(ActionEvent e) {
        // Panel para el formulario de registro de categoría
        JPanel formularioRegistrarCategoria = new JPanel();

        // Etiqueta y campo de texto para el nombre de la categoría
        formularioRegistrarCategoria.add(new JLabel("Nombre de la categoría:"));
        JTextField fieldNombreCategoria = new JTextField(10);
        formularioRegistrarCategoria.add(fieldNombreCategoria);

        // Botón para registrar la categoría
        JButton btnRegistrarCategoria = new JButton("Registrar Categoría");
        btnRegistrarCategoria.addActionListener(event -> {
            String nombreCategoria = fieldNombreCategoria.getText().trim();
            if (nombreCategoria.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Debe ingresar el nombre de la categoría.", "Error",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }
            try {
                loaderFerreteria.crearCategoria(nombreCategoria);
                JOptionPane.showMessageDialog(this, "Categoría registrada exitosamente.", "Registro Exitoso",
                        JOptionPane.INFORMATION_MESSAGE);
                // Limpia el campo después del registro
                fieldNombreCategoria.setText("");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error al registrar la categoría: " + ex.getMessage(), "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        });

        formularioRegistrarCategoria.add(btnRegistrarCategoria, BorderLayout.SOUTH);

        // Agrega el panel al CardLayout
        cards.add(formularioRegistrarCategoria, "RegistrarCategoria");
        cardLayout.show(cards, "RegistrarCategoria");
    }

    private void mostrarListadoCategoria(ActionEvent e) {
        // Crear el modelo para el JTable
        String[] columnNames = { "ID", "Nombre de la Categoría", "Precio Actual" };
        DefaultTableModel model = new DefaultTableModel(columnNames, 0);

        // Obtener las categorías y añadirlas al modelo del JTable
        List<Categorias> categorias = loaderFerreteria.getCategorias();
        if (categorias.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No hay Categorías Disponibles", "Información",
                    JOptionPane.INFORMATION_MESSAGE);
        } else {
            for (int i = 0; i < categorias.size(); i++) {
                Categorias categoria = categorias.get(i);
                model.addRow(new Object[] { i + 1, categoria.getNombre(), categoria.getPrecio() });
            }
        }

        // Crear el JTable con el modelo
        JTable tableCategorias = new JTable(model);
        tableCategorias.setFillsViewportHeight(true);

        // Permitir que la tabla se desplace si es necesario
        JScrollPane scrollPane = new JScrollPane(tableCategorias);

        // Panel para contener la tabla
        JPanel panelListadoCategorias = new JPanel(new BorderLayout());
        panelListadoCategorias.add(scrollPane, BorderLayout.CENTER);

        // Agrega el panel al CardLayout
        cards.add(panelListadoCategorias, "ListadoCategorias");
        cardLayout.show(cards, "ListadoCategorias");
    }

    private void mostrarEditarCategoria(ActionEvent e) {
        JPanel panelEditarCategoria = new JPanel();
        panelEditarCategoria.setLayout(new GridLayout(0, 2, 4, 10)); // Usar GridLayout para ordenar los labels y fields

        // Añadir componentes para ingresar el nombre anterior de la categoría
        panelEditarCategoria.add(new JLabel("Nombre anterior de la Categoría:"));
        JTextField fieldNombreAnterior = new JTextField();
        panelEditarCategoria.add(fieldNombreAnterior);

        // Añadir componentes para ingresar el nuevo nombre de la categoría
        panelEditarCategoria.add(new JLabel("Nombre nuevo de la Categoría:"));
        JTextField fieldNombreNuevo = new JTextField();
        panelEditarCategoria.add(fieldNombreNuevo);

        // Botón para realizar la acción de cambio de nombre
        JButton btnCambiarNombre = new JButton("Cambiar Nombre");
        btnCambiarNombre.addActionListener(event -> {
            String nombreAnterior = fieldNombreAnterior.getText();
            String nombreNuevo = fieldNombreNuevo.getText();

            // Aquí se debe llamar a la lógica de negocio para cambiar el nombre de la
            // categoría
            try {
                loaderFerreteria.editarNombreCategoria(nombreAnterior, nombreNuevo);
                JOptionPane.showMessageDialog(panelEditarCategoria, "Nombre de categoría cambiado exitosamente.",
                        "Cambio Realizado", JOptionPane.INFORMATION_MESSAGE);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(panelEditarCategoria,
                        "Error al cambiar el nombre de la categoría: " + ex.getMessage(), "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        });

        // Añadir botón al panel
        panelEditarCategoria.add(btnCambiarNombre);

        // Agrega el panel al CardLayout
        cards.add(panelEditarCategoria, "EditarCategoria");
        cardLayout.show(cards, "EditarCategoria");
    }

    private void mostrarCambiarVehCategoria(ActionEvent e) {
        // Panel para el formulario con GridLayout para organizar los componentes
        JPanel formularioCambiarCategoria = new JPanel(new GridLayout(0, 2, 10, 10));

        // Etiquetas y campos de texto para el formulario
        formularioCambiarCategoria.add(new JLabel("Placa del Vehículo:"));
        JTextField fieldPlaca = new JTextField();
        formularioCambiarCategoria.add(fieldPlaca);

        formularioCambiarCategoria.add(new JLabel("Nueva Categoría:"));
        JTextField fieldCategoria = new JTextField();
        formularioCambiarCategoria.add(fieldCategoria);

        // Botón para ejecutar la acción de cambio de categoría
        JButton btnCambiarCategoria = new JButton("Cambiar Categoría");
        btnCambiarCategoria.addActionListener(event -> {
            String placa = fieldPlaca.getText().trim();
            String categoria = fieldCategoria.getText().trim();

            try {
                loaderFerreteria.cambiarCarroCategoria(placa, categoria);
                JOptionPane.showMessageDialog(this, "Vehículo cambiado de categoría exitosamente.",
                        "Cambio de Categoría", JOptionPane.INFORMATION_MESSAGE);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error al cambiar la categoría: ");
            }
        });

        // Panel para el botón
        JPanel panelBoton = new JPanel();
        panelBoton.add(btnCambiarCategoria);

        // Panel principal para el formulario
        JPanel panelPrincipal = new JPanel(new BorderLayout());
        panelPrincipal.add(formularioCambiarCategoria, BorderLayout.CENTER);
        panelPrincipal.setBorder(BorderFactory.createEmptyBorder(40, 40, 40, 40));
        panelPrincipal.add(panelBoton, BorderLayout.SOUTH);

        // Agrega el panel principal al CardLayout
        cards.add(panelPrincipal, "CambiarCategoriaVehiculo");
        cardLayout.show(cards, "CambiarCategoriaVehiculo");
    }

    private void mostrarAgregarPrecCategoria(ActionEvent e) {
        JPanel panelAgregarPrecio = new JPanel();
        panelAgregarPrecio.setLayout(new BoxLayout(panelAgregarPrecio, BoxLayout.Y_AXIS));

        JTextField fieldNombreCategoria = new JTextField(10);
        JFormattedTextField fieldFechaInicio = new JFormattedTextField(new SimpleDateFormat("MM/dd/yy"));
        JFormattedTextField fieldFechaFin = new JFormattedTextField(new SimpleDateFormat("MM/dd/yy"));
        JTextField fieldPrecio = new JTextField(5);

        panelAgregarPrecio.add(new JLabel("Nombre de la Categoría:"));
        panelAgregarPrecio.add(fieldNombreCategoria);
        panelAgregarPrecio.add(new JLabel("Fecha Inicial (MM/dd/yy):"));
        panelAgregarPrecio.add(fieldFechaInicio);
        panelAgregarPrecio.add(new JLabel("Fecha Final (MM/dd/yy):"));
        panelAgregarPrecio.add(fieldFechaFin);
        panelAgregarPrecio.add(new JLabel("Precio:"));
        panelAgregarPrecio.add(fieldPrecio);

        JButton btnAgregarPrecio = new JButton("Agregar Precio");
        btnAgregarPrecio.addActionListener(event -> {
            try {
                String nombre = fieldNombreCategoria.getText();
                String fecha1 = fieldFechaInicio.getText();
                String fecha2 = fieldFechaFin.getText();
                int precio = Integer.parseInt(fieldPrecio.getText());
                loaderFerreteria.agregarPrecCategoria(fecha1, fecha2, precio, nombre);
                JOptionPane.showMessageDialog(this, "Precio agregado exitosamente a la categoría.", "Éxito",
                        JOptionPane.INFORMATION_MESSAGE);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Por favor, ingrese un número válido para el precio.", "Error",
                        JOptionPane.ERROR_MESSAGE);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error al agregar el precio a la categoría: " + ex.getMessage(),
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        panelAgregarPrecio.setBorder(BorderFactory.createEmptyBorder(40, 40, 40, 40));

        panelAgregarPrecio.add(btnAgregarPrecio);

        // Agrega el panel al CardLayout
        cards.add(panelAgregarPrecio, "AgregarPrecioCategoria");
        cardLayout.show(cards, "AgregarPrecioCategoria");
    }

    // Sedes

    private void mostrarFormularioRegistrarSede(ActionEvent e) {
        // Panel para el formulario con disposición de GridLayout
        JPanel panelRegistrarSede = new JPanel(new GridLayout(0, 2, 10, 10)); // 0 filas significa un número
                                                                              // indeterminado de filas

        // Crear los labels y campos de texto para el formulario
        panelRegistrarSede.add(new JLabel("Nombre de la sede:"));
        JTextField fieldNombre = new JTextField();
        panelRegistrarSede.add(fieldNombre);

        panelRegistrarSede.add(new JLabel("Ciudad de la sede:"));
        JTextField fieldCiudad = new JTextField();
        panelRegistrarSede.add(fieldCiudad);

        panelRegistrarSede.add(new JLabel("Dirección de la sede:"));
        JTextField fieldDireccion = new JTextField();
        panelRegistrarSede.add(fieldDireccion);

        panelRegistrarSede.add(new JLabel("Teléfono de la sede:"));
        JTextField fieldTelefono = new JTextField();
        panelRegistrarSede.add(fieldTelefono);

        panelRegistrarSede.add(new JLabel("Hora de apertura (HH:MM):"));
        JTextField fieldHoraApertura = new JTextField();
        panelRegistrarSede.add(fieldHoraApertura);

        panelRegistrarSede.add(new JLabel("Hora de cierre (HH:MM):"));
        JTextField fieldHoraCierre = new JTextField();
        panelRegistrarSede.add(fieldHoraCierre);

        // Botón para registrar la sede
        JButton btnRegistrar = new JButton("Registrar Sede");
        btnRegistrar.addActionListener(event -> {
            // Aquí iría el código para recoger los datos del formulario y llamar al método
            // que registra la sede
            try {
                String nombre = fieldNombre.getText();
                String ciudad = fieldCiudad.getText();
                String direccion = fieldDireccion.getText();
                String telefono = fieldTelefono.getText();
                String horasApertura = fieldHoraApertura.getText();
                String horasCierre = fieldHoraCierre.getText();

                // Suponiendo que loaderFerreteria es accesible aquí y tiene el método para
                // crear la sede
                loaderFerreteria.crearSede(nombre, ciudad, direccion, telefono, horasApertura, horasCierre);
                JOptionPane.showMessageDialog(panelRegistrarSede, "Sede registrada con éxito.");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(panelRegistrarSede, "Error al registrar la sede: " + ex.getMessage(),
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        // Panel para el botón con disposición FlowLayout para centrarlo
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.add(btnRegistrar);

        // Agrega un poco de espacio alrededor del panel de formulario
        JPanel panelConMargen = new JPanel(new BorderLayout());
        panelConMargen.add(panelRegistrarSede, BorderLayout.CENTER);
        panelConMargen.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        panelConMargen.add(buttonPanel, BorderLayout.SOUTH);

        // Agregar el panel principal al CardLayout
        cards.add(panelConMargen, "RegistrarSede");
        cardLayout.show(cards, "RegistrarSede");
    }

    private void mostrarListadoSedes(ActionEvent e) {
        // Crear el modelo para el JTable
        String[] columnNames = { "ID", "Nombre de la Sede", "Ciudad", "Teléfono" };
        DefaultTableModel model = new DefaultTableModel(columnNames, 0);

        // Obtener las sedes y añadirlas al modelo del JTable
        List<Sede> sedes = loaderFerreteria.getSedes();
        if (sedes.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No hay Sedes Disponibles", "Información",
                    JOptionPane.INFORMATION_MESSAGE);
        } else {
            for (int i = 0; i < sedes.size(); i++) {
                Sede sede = sedes.get(i);
                String formattedName = sede.getNombre() + (sede.getNombre().length() < 26 ? "\t" : "");
                model.addRow(new Object[] { i + 1, formattedName, sede.getCiudad(), sede.getTelefono() });
            }
        }

        // Crear el JTable con el modelo
        JTable tableSedes = new JTable(model);
        tableSedes.setFillsViewportHeight(true);

        // Permitir que la tabla se desplace si es necesario
        JScrollPane scrollPane = new JScrollPane(tableSedes);

        // Panel para contener la tabla
        JPanel panelListadoSedes = new JPanel(new BorderLayout());
        panelListadoSedes.add(scrollPane, BorderLayout.CENTER);

        // Agrega el panel al CardLayout
        cards.add(panelListadoSedes, "ListadoSedes");
        cardLayout.show(cards, "ListadoSedes");
    }

    private void mostrarEditarInfoSede(ActionEvent e) {
        // Panel con diseño de cuadrícula para el formulario
        JPanel panelEditarSede = new JPanel(new GridLayout(0, 2, 10, 10));

        // Etiquetas y campos de texto para cada atributo de la sede
        panelEditarSede.add(new JLabel("Nombre de la sede a editar:"));
        JTextField fieldBuscarSede = new JTextField();
        panelEditarSede.add(fieldBuscarSede);

        panelEditarSede.add(new JLabel("Nuevo nombre de la sede:"));
        JTextField fieldNombre = new JTextField();
        panelEditarSede.add(fieldNombre);

        panelEditarSede.add(new JLabel("Nueva ciudad de la sede:"));
        JTextField fieldCiudad = new JTextField();
        panelEditarSede.add(fieldCiudad);

        panelEditarSede.add(new JLabel("Nueva dirección de la sede:"));
        JTextField fieldDireccion = new JTextField();
        panelEditarSede.add(fieldDireccion);

        panelEditarSede.add(new JLabel("Nuevo teléfono de la sede:"));
        JTextField fieldTelefono = new JTextField();
        panelEditarSede.add(fieldTelefono);

        panelEditarSede.add(new JLabel("Nueva hora de apertura (HH:MM):"));
        JTextField fieldHorasApertura = new JTextField();
        panelEditarSede.add(fieldHorasApertura);

        panelEditarSede.add(new JLabel("Nueva hora de cierre (HH:MM):"));
        JTextField fieldHorasCierre = new JTextField();
        panelEditarSede.add(fieldHorasCierre);

        // Botón para realizar la acción de editar
        JButton btnEditarSede = new JButton("Editar Sede");
        btnEditarSede.addActionListener(event -> {
            // Lógica para obtener los datos y realizar la actualización
            String sedeNombre = fieldBuscarSede.getText();
            String nombre = fieldNombre.getText();
            String ciudad = fieldCiudad.getText();
            String direccion = fieldDireccion.getText();
            String telefono = fieldTelefono.getText();
            String horasApertura = fieldHorasApertura.getText();
            String horasCierre = fieldHorasCierre.getText();

            Sede sede = loaderFerreteria.buscarSede(sedeNombre);
            if (sede != null) {
                loaderFerreteria.setSede(sede, nombre, direccion, ciudad, telefono, horasApertura, horasCierre);
                JOptionPane.showMessageDialog(this, "Sede actualizada correctamente", "Información",
                        JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "Sede no encontrada", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        panelEditarSede.add(btnEditarSede);

        // Agrega un poco de espacio alrededor del panel de formulario
        JPanel panelConMargen = new JPanel(new BorderLayout());
        panelConMargen.add(panelEditarSede, BorderLayout.CENTER);
        panelConMargen.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Agrega el panel al CardLayout
        cards.add(panelConMargen, "EditarSede");
        cardLayout.show(cards, "EditarSede");
    }

    private void mostrarVehSede(ActionEvent e) {
        // Crear un panel con disposición BorderLayout
        JPanel panelVehSede = new JPanel(new BorderLayout());

        // Crear un JComboBox para las sedes
        JComboBox<String> comboSedes = new JComboBox<>();
        List<Sede> sedes = loaderFerreteria.getSedes();
        if (sedes.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No hay sedes disponibles", "Información",
                    JOptionPane.INFORMATION_MESSAGE);
        } else {
            for (Sede sede : sedes) {
                comboSedes.addItem(sede.getNombre());
            }
        }

        // Modelo para el JTable
        String[] columnNames = { "Placa", "Marca", "Modelo" };
        DefaultTableModel model = new DefaultTableModel(columnNames, 0);

        // Crear el JTable con el modelo
        JTable tableVehiculos = new JTable(model);

        // Escuchador de eventos para el JComboBox
        comboSedes.addActionListener(event -> {
            // Limpiar el modelo de la tabla
            model.setRowCount(0);

            // Obtener la sede seleccionada
            String sedeNombre = (String) comboSedes.getSelectedItem();
            Sede sedeSeleccionada = loaderFerreteria.buscarSede(sedeNombre);

            // Llenar la tabla con los vehículos de la sede seleccionada
            if (sedeSeleccionada != null) {
                for (Vehiculo vehiculo : sedeSeleccionada.getVehiculos()) {
                    model.addRow(new Object[] { vehiculo.getPlaca(), vehiculo.getMarca(), vehiculo.getModelo() });
                }
            }
        });

        // Agregar el JComboBox y el JTable al panel
        panelVehSede.add(comboSedes, BorderLayout.NORTH);
        panelVehSede.add(new JScrollPane(tableVehiculos), BorderLayout.CENTER);

        // Agregar el panel al CardLayout
        cards.add(panelVehSede, "VehiculosSede");
        cardLayout.show(cards, "VehiculosSede");
    }

    // Usuarios

    private void mostrarFormularioRegistrarUsuario(ActionEvent e) {
        JPanel registroUsuarios = new JPanel(new GridLayout(0, 2, 10, 10));

        JButton btnRegisAminLoc = new JButton("Registrar Administrador Local");
        btnRegisAminLoc.addActionListener(event -> {
            RegistrationAdminLocal registrationForm = new RegistrationAdminLocal();
            JDialog registrationDialog = new JDialog(this, "Formulario de Registro", true);
            registrationDialog.getContentPane().add(registrationForm);
            registrationDialog.pack();
            registrationDialog.setLocationRelativeTo(this);
            registrationDialog.setVisible(true);
        });

        registroUsuarios.add(btnRegisAminLoc);

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

        // Crear modelo de tabla para mostrar los datos
        String[] columnas = { "Nombre", "Cédula", "Sede" };
        DefaultTableModel modelo = new DefaultTableModel(columnas, 0);

        // Rellenar el modelo con datos
        for (AdministradorLocal admin : loaderFerreteria.getAdminsLocales()) {
            Sede sede = admin.getSede();
            modelo.addRow(new Object[] { admin.getNombre(), admin.getCedula(), sede.getNombre() });
            for (Empleado empleado : sede.getEmpleados()) {
                modelo.addRow(new Object[] { "    " + empleado.getNombre(), empleado.getCedula(), sede.getNombre() });
            }
        }

        // Crear tabla con el modelo
        JTable tablaUsuarios = new JTable(modelo);
        tablaUsuarios.setFillsViewportHeight(true);

        // Agregar tabla a un JScrollPane (para poder desplazarse por la tabla)
        JScrollPane scrollPane = new JScrollPane(tablaUsuarios);
        panelListadoUsuarios.add(scrollPane, BorderLayout.CENTER);

        // Agrega el panel al CardLayout
        cards.add(panelListadoUsuarios, "ListadoUsuarios");
        cardLayout.show(cards, "ListadoUsuarios");
    }

    private void mostrarEliminarEmpleado(ActionEvent e) {
        JPanel panelEliminarEmpleado = new JPanel();
        panelEliminarEmpleado.setLayout(new BoxLayout(panelEliminarEmpleado, BoxLayout.Y_AXIS));
        panelEliminarEmpleado.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Márgenes

        // ComboBox para seleccionar el tipo de empleado a eliminar
        String[] opciones = { "Eliminar Administrador Local", "Eliminar Empleado" };
        JComboBox<String> comboBoxOpciones = new JComboBox<>(opciones);
        comboBoxOpciones.setAlignmentX(Component.LEFT_ALIGNMENT);
        panelEliminarEmpleado.add(comboBoxOpciones);
        panelEliminarEmpleado.add(Box.createRigidArea(new Dimension(0, 5))); // Espacio

        // Etiqueta y campo de texto para ingresar la cédula
        JLabel labelCedula = new JLabel("Ingrese la cédula:");
        labelCedula.setAlignmentX(Component.LEFT_ALIGNMENT);
        panelEliminarEmpleado.add(labelCedula);

        JTextField fieldCedula = new JTextField(10);
        fieldCedula.setAlignmentX(Component.LEFT_ALIGNMENT);
        panelEliminarEmpleado.add(fieldCedula);

        panelEliminarEmpleado.add(Box.createRigidArea(new Dimension(0, 10))); // Espacio

        // Botón para realizar la acción de eliminación
        JButton btnEliminar = new JButton("Eliminar");
        btnEliminar.setAlignmentX(Component.LEFT_ALIGNMENT);
        btnEliminar.addActionListener(event -> {
            // Obtener la opción seleccionada en el JComboBox
            String opcionSeleccionada = (String) comboBoxOpciones.getSelectedItem();

            // Obtener la cédula del campo de texto
            int cedula;
            try {
                cedula = Integer.parseInt(fieldCedula.getText());
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Por favor, ingrese un número de cédula válido.", "Error",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Realizar la acción según la opción seleccionada
            try {
                if ("Eliminar Administrador Local".equals(opcionSeleccionada)) {
                    loaderFerreteria.eliminarAdminLocal(cedula);
                    JOptionPane.showMessageDialog(this, "Administrador Local eliminado exitosamente.", "Éxito",
                            JOptionPane.INFORMATION_MESSAGE);
                } else if ("Eliminar Empleado".equals(opcionSeleccionada)) {
                    loaderFerreteria.eliminarEmpleado(cedula);
                    JOptionPane.showMessageDialog(this, "Empleado eliminado exitosamente.", "Éxito",
                            JOptionPane.INFORMATION_MESSAGE);
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error al realizar la operación: " + ex.getMessage(), "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        });
        panelEliminarEmpleado.add(btnEliminar);

        // Agrega el panel al CardLayout
        cards.add(panelEliminarEmpleado, "EliminarEmpleado");
        cardLayout.show(cards, "EliminarEmpleado");
    }

    private void mostrarCambioSedeEmpleado(ActionEvent e) {
        // Panel para el formulario de cambio de sede de empleado
        JPanel formularioCambioSedeEmpleado = new JPanel(new BorderLayout());

        // Panel para agrupar las etiquetas y campos de texto
        JPanel inputPanel = new JPanel(new GridLayout(2, 2));

        // Etiqueta y campo de texto para la cédula del empleado
        inputPanel.add(new JLabel("Cédula del empleado:"));
        JTextField fieldCedula = new JTextField(10);
        inputPanel.add(fieldCedula);

        // Etiqueta y campo de texto para la nueva sede
        inputPanel.add(new JLabel("Nueva sede:"));
        JTextField fieldSede = new JTextField(20);
        inputPanel.add(fieldSede);

        formularioCambioSedeEmpleado.add(inputPanel, BorderLayout.CENTER);

        // Botón para realizar el cambio de sede
        JButton btnCambiarSede = new JButton("Cambiar Sede");
        btnCambiarSede.addActionListener(event -> {
            try {
                int cedula = Integer.parseInt(fieldCedula.getText().trim());
                String sede = fieldSede.getText().trim();
                if (sede.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Debe ingresar la sede.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                loaderFerreteria.cambiarSedeEmpleado(cedula, sede);
                JOptionPane.showMessageDialog(this, "Empleado cambiado de sede exitosamente.", "Cambio Exitoso",
                        JOptionPane.INFORMATION_MESSAGE);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Cédula inválida.", "Error", JOptionPane.ERROR_MESSAGE);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error al cambiar de sede: " + ex.getMessage(), "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        });

        formularioCambioSedeEmpleado.add(btnCambiarSede, BorderLayout.SOUTH);

        // Agrega el panel al CardLayout
        cards.add(formularioCambioSedeEmpleado, "CambioSedeEmpleado");
        cardLayout.show(cards, "CambioSedeEmpleado");
    }

    // Tarifas-Seguros

    private void mostrarFormularioRegistrarTarifa(ActionEvent e) {
        JPanel panelRegistrarTarifa = new JPanel();
        panelRegistrarTarifa.setLayout(new BoxLayout(panelRegistrarTarifa, BoxLayout.Y_AXIS));

        // Crear y añadir campos de texto y etiquetas
        panelRegistrarTarifa.add(new JLabel("Nombre de la Categoría:"));
        JTextField txtNombreCategoria = new JTextField();
        panelRegistrarTarifa.add(txtNombreCategoria);

        panelRegistrarTarifa.add(new JLabel("Fecha Inicial (MM/dd/yy):"));
        JTextField txtFechaInicial = new JTextField();
        panelRegistrarTarifa.add(txtFechaInicial);

        panelRegistrarTarifa.add(new JLabel("Fecha Final (MM/dd/yy):"));
        JTextField txtFechaFinal = new JTextField();
        panelRegistrarTarifa.add(txtFechaFinal);

        panelRegistrarTarifa.add(new JLabel("Precio:"));
        JTextField txtPrecio = new JTextField();
        panelRegistrarTarifa.add(txtPrecio);

        // Botón para enviar la información
        JButton btnAgregarPrecio = new JButton("Agregar Precio");
        btnAgregarPrecio.addActionListener(event -> {
            try {
                String nombre = txtNombreCategoria.getText();
                String fechaInicial = txtFechaInicial.getText();
                String fechaFinal = txtFechaFinal.getText();
                int precio = Integer.parseInt(txtPrecio.getText());

                // Llamada al método para agregar el precio a la categoría
                loaderFerreteria.agregarPrecCategoria(fechaInicial, fechaFinal, precio, nombre);
                JOptionPane.showMessageDialog(panelRegistrarTarifa, "Precio agregado correctamente.");
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(panelRegistrarTarifa,
                        "Error: Asegúrese de que el precio es un número válido.");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(panelRegistrarTarifa, "Error al agregar el precio: " + ex.getMessage());
            }
        });
        panelRegistrarTarifa.add(btnAgregarPrecio);

        // Agregar el panel al CardLayout
        cards.add(panelRegistrarTarifa, "RegistrarTarifa");
        cardLayout.show(cards, "RegistrarTarifa");
    }

    private void mostrarListadoTarifas(ActionEvent e) {
        // Crear el modelo para el JTable
        String[] columnNames = { "ID", "Nombre de la Categoría", "Fecha Inicio", "Fecha Fin", "Precio por Día" };
        DefaultTableModel model = new DefaultTableModel(columnNames, 0);

        // Obtener las categorías y añadirlas al modelo del JTable
        List<Categorias> categorias = loaderFerreteria.getCategorias();
        if (categorias.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No hay Categorías Disponibles", "Información",
                    JOptionPane.INFORMATION_MESSAGE);
        } else {
            for (Categorias categoria : categorias) {
                for (Date[] dias : categoria.getMapa().keySet()) {
                    Date inicio = dias[0];
                    Date fin = dias[1];
                    int precio = categoria.getMapa().get(dias);
                    model.addRow(new Object[] { categoria.getNombre(), inicio, fin, precio });
                }
            }
        }

        // Crear el JTable con el modelo
        JTable tableTarifas = new JTable(model);
        tableTarifas.setFillsViewportHeight(true);

        // Permitir que la tabla se desplace si es necesario
        JScrollPane scrollPane = new JScrollPane(tableTarifas);

        // Panel para contener la tabla
        JPanel panelListadoTarifas = new JPanel(new BorderLayout());
        panelListadoTarifas.add(scrollPane, BorderLayout.CENTER);

        // Agrega el panel al CardLayout
        cards.add(panelListadoTarifas, "ListadoTarifas");
        cardLayout.show(cards, "ListadoTarifas");
    }

    private void mostrarEliminarTarifa(ActionEvent e) {
        JPanel panelEliminarTarifa = new JPanel();
        panelEliminarTarifa.setLayout(new GridLayout(3, 2, 10, 10)); // Uso de GridLayout para organizar los componentes

        // Crear y añadir etiquetas y campos de texto
        panelEliminarTarifa.add(new JLabel("Nombre de la categoría:"));
        JTextField fieldNombre = new JTextField(10);
        panelEliminarTarifa.add(fieldNombre);

        panelEliminarTarifa.add(new JLabel("Fecha (MM/dd/yy):"));
        JTextField fieldFecha = new JTextField(10);
        panelEliminarTarifa.add(fieldFecha);

        // Botón para realizar la acción de eliminar
        JButton btnEliminar = new JButton("Eliminar Precio");
        btnEliminar.addActionListener(event -> {
            // Lógica para eliminar la tarifa
            String nombre = fieldNombre.getText();
            String fecha = fieldFecha.getText();
            try {
                loaderFerreteria.eliminarPrecCategoria(fecha, nombre);
                JOptionPane.showMessageDialog(this, "Precio eliminado exitosamente", "Éxito",
                        JOptionPane.INFORMATION_MESSAGE);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error al eliminar el precio: " + ex.getMessage(), "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        });
        panelEliminarTarifa.add(btnEliminar);

        // Agrega el panel al CardLayout
        cards.add(panelEliminarTarifa, "EliminarTarifa");
        cardLayout.show(cards, "EliminarTarifa");
    }

    private void mostrarCambioTarifa(ActionEvent e) {
        // Panel principal
        JPanel panelCambiarTarifa = new JPanel(new BorderLayout());

        // Panel para los campos de entrada
        JPanel inputPanel = new JPanel(new GridLayout(0, 2));

        // Campo para ingresar el nombre de la categoría
        inputPanel.add(new JLabel("Nombre de la categoría:"));
        JTextField fieldNombreCategoria = new JTextField(10);
        inputPanel.add(fieldNombreCategoria);

        // Campo para ingresar la fecha
        inputPanel.add(new JLabel("Fecha (MM/dd/yy):"));
        JTextField fieldFecha = new JTextField(10);
        inputPanel.add(fieldFecha);

        panelCambiarTarifa.add(inputPanel, BorderLayout.CENTER);

        // Botón para realizar el cambio de tarifa
        JButton btnCambiarTarifa = new JButton("Cambiar Tarifa");
        btnCambiarTarifa.addActionListener(event -> {
            try {
                String nombreCategoria = fieldNombreCategoria.getText();
                String fecha = fieldFecha.getText();

                // Aquí llamas a tu método para cambiar la tarifa
                loaderFerreteria.eliminarPrecCategoria(fecha, nombreCategoria);

                // Mensaje de confirmación
                JOptionPane.showMessageDialog(this, "Tarifa cambiada exitosamente", "Éxito",
                        JOptionPane.INFORMATION_MESSAGE);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error al cambiar la tarifa: " + ex.getMessage(), "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        });

        panelCambiarTarifa.add(btnCambiarTarifa, BorderLayout.SOUTH);

        // Agrega el panel al CardLayout
        cards.add(panelCambiarTarifa, "CambiarTarifa");
        cardLayout.show(cards, "CambiarTarifa");
    }

    private void mostrarFormularioRegistrarSeguro(ActionEvent e) {
        JPanel panelRegistrarSeguro = new JPanel();
        panelRegistrarSeguro.setLayout(new BoxLayout(panelRegistrarSeguro, BoxLayout.Y_AXIS));

        // Crear y añadir campos de texto y etiquetas
        panelRegistrarSeguro.add(new JLabel("Nombre del Seguro:"));
        JTextField fieldNombre = new JTextField(10);
        panelRegistrarSeguro.add(fieldNombre);

        panelRegistrarSeguro.add(new JLabel("Información sobre el Seguro:"));
        JTextField fieldInfo = new JTextField(10);
        panelRegistrarSeguro.add(fieldInfo);

        panelRegistrarSeguro.add(new JLabel("Precio:"));
        JTextField fieldPrecio = new JTextField(10);
        panelRegistrarSeguro.add(fieldPrecio);

        // Botón para registrar el seguro
        JButton btnRegistrar = new JButton("Registrar Seguro");
        btnRegistrar.addActionListener(event -> {
            try {
                String nombre = fieldNombre.getText();
                String info = fieldInfo.getText();
                int precio = Integer.parseInt(fieldPrecio.getText());
                loaderFerreteria.crearSeguro(nombre, info, precio);
                JOptionPane.showMessageDialog(panelRegistrarSeguro, "Seguro registrado exitosamente");
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(panelRegistrarSeguro, "Por favor, ingrese un precio válido", "Error",
                        JOptionPane.ERROR_MESSAGE);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(panelRegistrarSeguro, "Error al registrar el seguro: " + ex.getMessage(),
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
        panelRegistrarSeguro.add(btnRegistrar);

        // Agrega el panel al CardLayout
        cards.add(panelRegistrarSeguro, "RegistrarSeguro");
        cardLayout.show(cards, "RegistrarSeguro");
    }

    private void mostrarListadoSeguros(ActionEvent e) {
        // Crear el modelo para el JTable
        String[] columnNames = { "ID", "Nombre del Seguro", "Precio" };
        DefaultTableModel model = new DefaultTableModel(columnNames, 0);

        // Obtener los seguros y añadirlos al modelo del JTable
        List<Seguros> seguros = loaderFerreteria.getSeguros();
        if (seguros.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No hay Seguros Disponibles", "Información",
                    JOptionPane.INFORMATION_MESSAGE);
        } else {
            for (int i = 0; i < seguros.size(); i++) {
                Seguros seguro = seguros.get(i);
                model.addRow(new Object[] { i + 1, seguro.getNombre(), seguro.getPrecio() });
            }
        }

        // Crear el JTable con el modelo
        JTable tableSeguros = new JTable(model);
        tableSeguros.setFillsViewportHeight(true);

        // Permitir que la tabla se desplace si es necesario
        JScrollPane scrollPane = new JScrollPane(tableSeguros);

        // Panel para contener la tabla
        JPanel panelListadoSeguros = new JPanel(new BorderLayout());
        panelListadoSeguros.add(scrollPane, BorderLayout.CENTER);

        // Agrega el panel al CardLayout
        cards.add(panelListadoSeguros, "ListadoSeguros");
        cardLayout.show(cards, "ListadoSeguros");
    }

    private void mostrarEliminarSeguro(ActionEvent e) {
        // Panel para el formulario
        JPanel panelEliminarSeguro = new JPanel(new GridLayout(0, 2, 2, 10));
        panelEliminarSeguro.setLayout(new BoxLayout(panelEliminarSeguro, BoxLayout.Y_AXIS));

        // Etiqueta y campo de texto para el nombre del seguro
        panelEliminarSeguro.add(new JLabel("Nombre del Seguro:"));
        JTextField fieldNombre = new JTextField(10);
        panelEliminarSeguro.add(fieldNombre);

        // Botón para eliminar el seguro
        JButton btnEliminar = new JButton("Eliminar Seguro");
        btnEliminar.addActionListener(event -> {
            String nombre = fieldNombre.getText();
            try {
                // Aquí se realiza la llamada para eliminar el seguro
                loaderFerreteria.eliminarSeguro(nombre);
                JOptionPane.showMessageDialog(this, "Seguro eliminado exitosamente", "Información",
                        JOptionPane.INFORMATION_MESSAGE);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error al eliminar el seguro: " + ex.getMessage(), "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        });
        panelEliminarSeguro.add(btnEliminar);

        // Agrega el panel al CardLayout
        cards.add(panelEliminarSeguro, "EliminarSeguro");
        cardLayout.show(cards, "EliminarSeguro");
    }

    private void mostrarCambioSeguro(ActionEvent e) {
        JPanel panelCambiarSeguro = new JPanel();
        panelCambiarSeguro.setLayout(new GridLayout(0, 2, 10, 10)); // Usa un GridLayout para organizar los componentes

        // Crear y añadir los componentes al panel
        panelCambiarSeguro.add(new JLabel("Nombre del Seguro:"));
        JTextField fieldNombreSeguro = new JTextField();
        panelCambiarSeguro.add(fieldNombreSeguro);

        panelCambiarSeguro.add(new JLabel("Nuevo Precio:"));
        JTextField fieldPrecio = new JTextField();
        panelCambiarSeguro.add(fieldPrecio);

        JButton btnCambiarPrecio = new JButton("Cambiar Precio");
        btnCambiarPrecio.addActionListener(event -> {
            try {
                String nombre = fieldNombreSeguro.getText();
                int precio = Integer.parseInt(fieldPrecio.getText());
                // Cambiar el precio del seguro
                loaderFerreteria.cambiarPrecSeguro(nombre, precio);
                JOptionPane.showMessageDialog(this, "Precio del seguro actualizado con éxito", "Exito",
                        JOptionPane.INFORMATION_MESSAGE);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Por favor, ingrese un número válido para el precio", "Error",
                        JOptionPane.ERROR_MESSAGE);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error al cambiar el precio: " + ex.getMessage(), "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        });
        panelCambiarSeguro.add(btnCambiarPrecio);

        // Agrega el panel al CardLayout
        cards.add(panelCambiarSeguro, "CambiarSeguro");
        cardLayout.show(cards, "CambiarSeguro");
    }

}
