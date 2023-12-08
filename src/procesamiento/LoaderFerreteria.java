package procesamiento;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import inventario.Categorias;
import inventario.Vehiculo;
import reserva_alquiler.Alquiler;
import reserva_alquiler.Cliente;
import reserva_alquiler.Reserva;
import reserva_alquiler.Seguros;
import sedes.AdministradorLocal;
import sedes.Empleado;
import sedes.Sede;
import sedes.SuperAdministrador;

/**
 * Clase encargada de cargar y guardar los datos de la ferretería, así como de
 * realizar las operaciones de registro, edición y eliminación de los diferentes
 * objetos de la ferretería (clientes, administradores, empleados, sedes,
 * vehículos, seguros, categorías, reservas y alquileres).
 * También se encarga de realizar operaciones como el traslado de vehículos
 * entre sedes, la recogida y devolución de vehículos, la asignación de
 * mantenimiento a vehículos, entre otras.
 */
public class LoaderFerreteria {

    /**
     * Atributos de la clase
     */
    private static SuperAdministrador supAdmin = null;
    private static AdministradorLocal adminloc = null;
    private static Empleado empleado = null;
    private static Cliente cliente = null;
    private static List<Categorias> categorias = new ArrayList<>();
    private static List<SuperAdministrador> superadmins = new ArrayList<>();
    private static List<AdministradorLocal> adminsLocales = new ArrayList<>();
    private static List<Empleado> empleados = new ArrayList<>();
    private static List<Sede> sedes = new ArrayList<>();
    private static List<Vehiculo> vehiculos = new ArrayList<>();
    private static List<Seguros> seguros = new ArrayList<>();
    private static List<Cliente> clientes = new ArrayList<>();
    private static List<Alquiler> alquileres = new ArrayList<>();
    private static List<Reserva> reservas = new ArrayList<>();
    private static LoaderFerreteria loaderFerreteria;

    /**
     * Constructor privado de la clase
     */
    private LoaderFerreteria() {

    }

    /**
     * Método que devuelve una instancia de la clase LoaderFerreteria
     * 
     * @return instancia de la clase LoaderFerreteria
     */
    public static LoaderFerreteria newFerreteria() {
        if (loaderFerreteria == null) {
            loaderFerreteria = new LoaderFerreteria();
        }
        return loaderFerreteria;
    }

    // Registro

    /**
     * Método que crea un nuevo cliente y lo registra en la ferretería
     * 
     * @param correo             correo electrónico del cliente
     * @param contrasenia        contraseña del cliente
     * @param cedula             número de cédula del cliente
     * @param nombre             nombre del cliente
     * @param datosContacto      datos de contacto del cliente
     * @param fechaNacimiento    fecha de nacimiento del cliente
     * @param nacionalidad       nacionalidad del cliente
     * @param rutaImagen         ruta de la imagen del cliente
     * @param numeroLicencia     número de licencia del cliente
     * @param paisExpedicion     país de expedición de la licencia del cliente
     * @param fechaVencimiento   fecha de vencimiento de la licencia del cliente
     * @param rutaImagenLicencia ruta de la imagen de la licencia del cliente
     * @param tipoTarjeta        tipo de tarjeta del cliente
     * @param banco              banco del cliente
     * @param numeroTarjeta      número de tarjeta del cliente
     * @param cVC                código de verificación de la tarjeta del cliente
     */
    public void crearCliente(String correo, String contrasenia, int cedula, String nombre, String datosContacto,
            String fechaNacimiento, String nacionalidad, String rutaImagen,
            String numeroLicencia, String paisExpedicion, String fechaVencimiento, String rutaImagenLicencia,
            String tipoTarjeta, String banco, String numeroTarjeta, short cVC) {

        supAdmin.registrarCliente(correo, contrasenia, cedula, nombre, datosContacto, fechaNacimiento, nacionalidad,
                rutaImagen, numeroLicencia, paisExpedicion, fechaVencimiento, rutaImagenLicencia, tipoTarjeta, banco,
                numeroTarjeta, cVC);
        clientes = SuperAdministrador.getClientes();
        guardadatos();

    }

    /**
     * Método que crea un nuevo administrador local y lo registra en la ferretería
     * 
     * @param correo          correo electrónico del administrador local
     * @param contrasenia     contraseña del administrador local
     * @param nombre          nombre del administrador local
     * @param datosContacto   datos de contacto del administrador local
     * @param fechaNacimiento fecha de nacimiento del administrador local
     * @param cedula          número de cédula del administrador local
     * @param sedestr         nombre de la sede del administrador local
     */
    public void crearAdministradorLocal(String correo, String contrasenia, String nombre, String datosContacto,
            String fechaNacimiento, int cedula, String sedestr) {
        Sede sede = supAdmin.buscarSede(sedestr);
        supAdmin.registrarAdmin(correo, contrasenia, nombre, datosContacto, fechaNacimiento, cedula, sede);
        adminsLocales = supAdmin.getAdminsLocales();
        guardadatos();

    }

    /**
     * Método que crea un nuevo superadministrador y lo registra en la ferretería
     * 
     * @param correo          correo electrónico del superadministrador
     * @param contrasenia     contraseña del superadministrador
     * @param nombre          nombre del superadministrador
     * @param datosContacto   datos de contacto del superadministrador
     * @param fechaNacimiento fecha de nacimiento del superadministrador
     * @param cedula          número de cédula del superadministrador
     */
    public void crearAdministrador(String correo, String contrasenia, String nombre, String datosContacto,
            String fechaNacimiento, int cedula) {
        supAdmin = new SuperAdministrador(correo, contrasenia, nombre, datosContacto, fechaNacimiento, cedula);
        superadmins.add(supAdmin);
    }

    /**
     * Método que crea un nuevo empleado y lo registra en la ferretería
     * 
     * @param correo          correo electrónico del empleado
     * @param contrasenia     contraseña del empleado
     * @param nombre          nombre del empleado
     * @param datosContacto   datos de contacto del empleado
     * @param fechaNacimiento fecha de nacimiento del empleado
     * @param cedula          número de cédula del empleado
     * @param sedestr         nombre de la sede del empleado
     */
    public void crearEmpleado(String correo, String contrasenia, String nombre, String datosContacto,
            String fechaNacimiento, int cedula, String sedestr) {
        Sede sede = supAdmin.buscarSede(sedestr);
        supAdmin.registrarEmpleado(correo, contrasenia, nombre, datosContacto, fechaNacimiento, cedula, sede);
        empleados = supAdmin.getEmpleados();
        guardadatos();
    }

    /**
     * Método que crea un nuevo vehículo y lo registra en la ferretería
     * 
     * @param placa           placa del vehículo
     * @param marca           marca del vehículo
     * @param modelo          modelo del vehículo
     * @param color           color del vehículo
     * @param numPasajeros    número de pasajeros del vehículo
     * @param tipoTransmicion tipo de transmisión del vehículo
     * @param categoriastr    nombre de la categoría del vehículo
     * @param sedestr         nombre de la sede del vehículo
     */
    public void crearVehiculo(String placa, String marca, String modelo, String color, short numPasajeros,
            String tipoTransmicion, String categoriastr, String sedestr, String tipo, float porcentaje) {
        Categorias categoria = buscarCategoria(categoriastr);
        Sede sede = buscarSede(sedestr);

        supAdmin.registrarCarro(placa, marca, modelo, color, numPasajeros, tipoTransmicion, categoria, sede, tipo,
                porcentaje);
        vehiculos = SuperAdministrador.getCarros();
        guardadatos();
    }

    /**
     * Método que crea una nueva categoría y la registra en la ferretería
     * 
     * @param nombre nombre de la categoría
     */
    public void crearCategoria(String nombre) {
        supAdmin.registrarCategoria(nombre);
        categorias = SuperAdministrador.getCategorias();
        guardadatos();

    }

    /**
     * Método que crea una nueva sede y la registra en la ferretería
     * 
     * @param nombre        nombre de la sede
     * @param direccion     dirección de la sede
     * @param ciudad        ciudad de la sede
     * @param telefono      teléfono de la sede
     * @param horasApertura horas de apertura de la sede
     * @param horasCierre   horas de cierre de la sede
     */
    public void crearSede(String nombre, String direccion, String ciudad, String telefono, String horasApertura,
            String horasCierre) {
        supAdmin.registrarSede(nombre, direccion, ciudad, telefono, horasApertura, horasCierre);
        sedes = SuperAdministrador.getSedes();
        guardadatos();
    }

    /**
     * Método que crea un nuevo seguro y lo registra en la ferretería
     * 
     * @param nombre nombre del seguro
     * @param info   información del seguro
     * @param precio precio del seguro
     */
    public void crearSeguro(String nombre, String info, int precio) {
        supAdmin.registrarSeguro(nombre, info, precio);
        seguros = SuperAdministrador.getSeguros();
        guardadatos();
    }

    /**
     * Método que elimina un administrador local de la ferretería
     * 
     * @param cedula número de cédula del administrador local a eliminar
     */
    public void eliminarAdminLocal(int cedula) {
        supAdmin.eliminarAdmin(cedula);
        adminsLocales = supAdmin.getAdminsLocales();
        guardar_eliminado();

    }

    /**
     * Método que elimina un empleado de la ferretería
     * 
     * @param cedula número de cédula del empleado a eliminar
     */
    public void eliminarEmpleado(int cedula) {
        supAdmin.eliminarEmpleado(cedula);
        empleados = supAdmin.getEmpleados();
        guardar_eliminado();
    }

    /**
     * Método que elimina un precio de una categoría de la ferretería
     * 
     * @param fecha  fecha del precio a eliminar
     * @param nombre nombre de la categoría a la que pertenece el precio
     */
    public void eliminarPrecCategoria(String fecha, String nombre) {
        supAdmin.eliminarPrecioCategoria(fecha, nombre);
        categorias = SuperAdministrador.getCategorias();
        guardar_eliminado();
    }

    /**
     * Método que elimina un seguro de la ferretería
     * 
     * @param nombre nombre del seguro a eliminar
     */
    public void eliminarSeguro(String nombre) {
        supAdmin.eliminarSeguro(nombre);
        seguros = SuperAdministrador.getSeguros();
        guardar_eliminado();

    }

    /**
     * Método que elimina un vehículo de la ferretería
     * 
     * @param placa placa del vehículo a eliminar
     */
    public void eliminarVehiculo(String placa) {
        supAdmin.eliminarCarro(placa);
        vehiculos = SuperAdministrador.getCarros();
        guardar_eliminado();
    }

    /**
     * Método que cancela una reserva de un cliente en la ferretería
     * 
     * @param cedula          número de cédula del cliente que hizo la reserva
     * @param nombreCategoria nombre de la categoría del vehículo reservado
     */
    public void cancelarReserva(int cedula, String nombreCategoria) {
        Categorias categoria = buscarCategoria(nombreCategoria);
        cliente = buscarCliente(cedula);
        empleado.cancelarReserva(cliente, categoria);
        guardadatos();
    }

    /**
     * Método que edita el nombre de una categoría de la ferretería
     * 
     * @param nombreAnt   nombre anterior de la categoría
     * @param nombreNuevo nuevo nombre de la categoría
     */
    public void editarNombreCategoria(String nombreAnt, String nombreNuevo) {
        supAdmin.editarNombreCategoria(nombreAnt, nombreNuevo);
        categorias = SuperAdministrador.getCategorias();
        guardar_eliminado();
    }

    /**
     * Método que cambia la categoría de un vehículo de la ferretería
     * 
     * @param placa          placa del vehículo
     * @param categoriaNueva nombre de la nueva categoría del vehículo
     */
    public void cambiarCarroCategoria(String placa, String categoriaNueva) {
        supAdmin.changeVehiculoCategoria(placa, categoriaNueva);
        categorias = SuperAdministrador.getCategorias();
        guardar_eliminado();
    }

    /**
     * Método que agrega un precio a una categoría de la ferretería
     * 
     * @param fecha1 fecha de inicio del precio
     * @param fecha2 fecha de fin del precio
     * @param precio precio a agregar
     * @param nombre nombre de la categoría a la que se le agregará el precio
     */
    public void agregarPrecCategoria(String fecha1, String fecha2, int precio, String nombre) {
        supAdmin.agregarPrecioCategoria(fecha1, fecha2, precio, nombre);
        categorias = SuperAdministrador.getCategorias();
        supAdmin.guardarCategoriaEditada();
    }

    /**
     * Método que cambia el precio de una categoría de la ferretería
     * 
     * @param fecha  fecha del precio a cambiar
     * @param precio nuevo precio
     * @param nombre nombre de la categoría a la que pertenece el precio
     */
    public void cambiarPrecCategoria(String fecha, int precio, String nombre) {
        supAdmin.cambiarPrecioCategoria(fecha, precio, nombre);
        categorias = SuperAdministrador.getCategorias();
        guardar_eliminado();
    }

    /**
     * Método que cambia el precio de un seguro de la ferretería
     * 
     * @param nombre nombre del seguro a cambiar
     * @param precio nuevo precio del seguro
     */
    public void cambiarPrecSeguro(String nombre, int precio) {
        supAdmin.cambiarPrecioSeguro(nombre, precio);
        seguros = SuperAdministrador.getSeguros();
        guardar_eliminado();
    }

    // Crear, editar y eliminar objetos Administrador Local y Empleado

    /**
     * Método que traslada un vehículo de una sede a otra en la ferretería
     * 
     * @param placa     placa del vehículo a trasladar
     * @param sedeNueva nombre de la nueva sede del vehículo
     */
    public void trasladoVehiculo(String placa, String sedeNueva) {
        Sede sede = buscarSede(sedeNueva);
        if (sede != null) {
            empleado.trasladoVehiculo(placa, sede);
            guardadatos();
        }
    }

    /**
     * Método que registra la recogida de un vehículo por parte de un cliente en la
     * ferretería
     * 
     * @param cedulaCliente  número de cédula del cliente que recoge el vehículo
     * @param fechaInicio    fecha de inicio del alquiler
     * @param seguros        lista de seguros contratados por el cliente
     * @param modificaciones modificaciones solicitadas por el cliente
     */
    public void recogidaVehiculo(int cedulaCliente, String fechaInicio, List<String> seguros, String[] modificaciones) {
        List<Seguros> seg = new ArrayList<>();
        for (String nombreSeguro : seguros) {
            seg.add(buscarSeguro(nombreSeguro));
        }
        Cliente cliente = buscarCliente(cedulaCliente);
        if (cliente != null) {
            empleado.recogidaVehiculo(cliente, Fecha.convertirFecha(fechaInicio), seg, modificaciones);
            guardadatos();
        }
    }

    /**
     * Método que pone un vehículo en mantenimiento en la ferretería
     * 
     * @param limpieza            indica si se realizará limpieza al vehículo
     * @param mantenimiento       indica si se realizará mantenimiento al vehículo
     * @param placa               placa del vehículo a poner en mantenimiento
     * @param diasEnMantenimiento días que el vehículo estará en mantenimiento
     */
    public void ponerMantenimientoCarro(boolean limpieza, boolean mantenimiento, String placa,
            short diasEnMantenimiento) {
        empleado.ponerMantenimientoCarro(limpieza, mantenimiento, placa, diasEnMantenimiento);
        guardadatos();
    }

    /**
     * Método que quita un vehículo del mantenimiento en la ferretería
     * 
     * @param placa placa del vehículo a quitar del mantenimiento
     */
    public void quitarMantenimiento(String placa) {
        empleado.quitarMantenimiento(placa);
        guardar_eliminado();
    }

    /**
     * Método que registra una reserva de un cliente en la ferretería
     * 
     * @param fechaInicio       fecha de inicio de la reserva
     * @param fechaFinal        fecha de fin de la reserva
     * @param inicioHora        hora de inicio de la reserva
     * @param finHora           hora de fin de la reserva
     * @param lugarRecogidastr  nombre de la sede donde se recogerá el vehículo
     * @param lugarDejadaStr    nombre de la sede donde se dejará el vehículo
     * @param cedula_cliente    número de cédula del cliente que hace la reserva
     * @param categoriaCarroStr nombre de la categoría del vehículo reservado
     * @param horaLLegada       hora de llegada del cliente a la sede de recogida
     * @return precio de la reserva
     */
    public int registrarReserva(String fechaInicio, String fechaFinal, String inicioHora, String finHora,
            String lugarRecogidastr,
            String lugarDejadaStr, int cedula_cliente, String categoriaCarroStr, String horaLLegada) {
        Sede lugarRecogida = buscarSede(lugarRecogidastr);
        Sede lugarDejada = buscarSede(lugarDejadaStr);
        Cliente cliente = buscarCliente(cedula_cliente);
        Categorias categoriaCarro = buscarCategoria(categoriaCarroStr);
        int precio = supAdmin.registrarReserva(fechaInicio, fechaFinal, inicioHora, finHora, lugarRecogida, lugarDejada,
                cliente, categoriaCarro, horaLLegada);
        alquileres = supAdmin.getAlquileres();
        reservas = supAdmin.getReservas();
        guardadatos();
        return precio;
    }

    /**
     * Método que guarda una reserva de un cliente en la ferretería
     * 
     * @param fechaInicio       fecha de inicio de la reserva
     * @param fechaFinal        fecha de fin de la reserva
     * @param inicioHora        hora de inicio de la reserva
     * @param finHora           hora de fin de la reserva
     * @param lugarRecogidastr  nombre de la sede donde se recogerá el vehículo
     * @param lugarDejadaStr    nombre de la sede donde se dejará el vehículo
     * @param cedula_cliente    número de cédula del cliente que hace la reserva
     * @param categoriaCarroStr nombre de la categoría del vehículo reservado
     * @param horaLLegada       hora de llegada del cliente a la sede de recogida
     */
    public void guardar_reserva_cliente(String fechaInicio, String fechaFinal, String inicioHora, String finHora,
            String lugarRecogidastr,
            String lugarDejadaStr, int cedula_cliente, String categoriaCarroStr, String horaLLegada) {
        cliente = buscarCliente(cedula_cliente);
        if (cliente != null) {
            Alquiler alquiler = buscarAlquiler(fechaInicio, fechaFinal, inicioHora, finHora, lugarRecogidastr,
                    lugarDejadaStr, cedula_cliente, categoriaCarroStr);
            cliente.agregarAlquileres(alquiler);
        }
    }

    /**
     * Método que registra un alquiler de un vehículo por parte de un cliente en la
     * ferretería
     * 
     * @param fechaInicio       fecha de inicio del alquiler
     * @param fechaFinal        fecha de fin del alquiler
     * @param inicioHora        hora de inicio del alquiler
     * @param finHora           hora de fin del alquiler
     * @param lugarRecogidaStr  nombre de la sede donde se recogerá el vehículo
     * @param lugarDejadaStr    nombre de la sede donde se dejará el vehículo
     * @param cedula_cliente    número de cédula del cliente que alquila el vehículo
     * @param categoriaCarroStr nombre de la categoría del vehículo alquilado
     * @param seguros           lista de seguros contratados por el cliente
     */
    public void registrarAlquiler(String fechaInicio, String fechaFinal, String inicioHora, String finHora,
            String lugarRecogidaStr,
            String lugarDejadaStr, int cedula_cliente, String categoriaCarroStr, List<String> seguros) {
        Sede lugarRecogida = buscarSede(lugarRecogidaStr);
        Sede lugarDejada = buscarSede(lugarDejadaStr);
        cliente = buscarCliente(cedula_cliente);
        if (cliente != null) {
            Categorias categoriaCarro = buscarCategoria(categoriaCarroStr);
            List<Seguros> seg = new ArrayList<>();
            for (String nombreSeguro : seguros) {
                seg.add(buscarSeguro(nombreSeguro));
            }
            supAdmin.registrarAlquiler(fechaInicio, fechaFinal, inicioHora, finHora, lugarRecogida, lugarDejada,
                    cliente, categoriaCarro, seg);
            alquileres = supAdmin.getAlquileres();
            guardadatos();

        } else {
            System.out.println(cedula_cliente);
        }

    }

    /**
     * Método que registra la devolución de un vehículo por parte de un cliente en
     * la ferretería
     * 
     * @param cedulaCliente número de cédula del cliente que devuelve el vehículo
     * @param placa         placa del vehículo devuelto
     */
    public void registrarDevolucion(int cedulaCliente, String placa) {
        empleado.registrarDevolucion(cedulaCliente, placa);
        alquileres = supAdmin.getAlquileres();
        guardar_eliminado();
    }

    public void cambiarSedeEmpleado(int cedula, String sede) {
        supAdmin.cambiarSedeEmpleado(cedula, sede);
        empleados = supAdmin.getEmpleados();
        guardar_eliminado();
    }

    public void setSede(Sede sede, String nombre, String direccion, String ciudad, String telefono,
            String horasApertura, String horasCierre) {
        sede.setSede(nombre, direccion, ciudad, telefono, horasApertura, horasCierre);
        sedes = SuperAdministrador.getSedes();
        guardar_eliminado();
    }

    public List<Alquiler> verHisAlquiCliente(int cedula) {
        cliente = buscarCliente(cedula);
        return cliente.getAlquileres();
    }

    public List<Categorias> getCategorias() {
        return categorias;
    }

    public List<Vehiculo> getVehiculos() {
        return vehiculos;
    }

    public List<Reserva> getReservas() {
        return reservas;
    }

    public List<Alquiler> getAlquileres() {
        return alquileres;
    }

    public List<Sede> getSedes() {
        return sedes;
    }

    public List<Alquiler> verHistorialAlquileres() {
        return cliente.getAlquileres();
    }

    public List<AdministradorLocal> getAdminsLocales() {
        return adminsLocales;
    }

    public List<Seguros> getSeguros() {
        return seguros;
    }

    // Busqueda

    public Vehiculo buscarVehiculo(String placa) {
        for (Vehiculo vehiculo : SuperAdministrador.getCarros()) {
            if (vehiculo.getPlaca().equals(placa)) {
                return vehiculo;
            }
        }
        return null;
    }

    public Categorias buscarCategoria(String nombre) {
        for (Categorias categoria : categorias) {
            if (categoria.getNombre().equals(nombre)) {
                return categoria;
            }
        }
        return null;
    }

    public Sede buscarSede(String nombre) {
        sedes = SuperAdministrador.getSedes();
        for (Sede sedem : sedes) {
            if (sedem.getNombre().equals(nombre)) {
                return sedem;
            }
        }
        return null;
    }

    public Seguros buscarSeguro(String nombre) {
        seguros = SuperAdministrador.getSeguros();
        for (Seguros seguro : seguros) {
            if (seguro.getNombre().equals(nombre)) {
                return seguro;
            }
        }
        return null;
    }

    public Cliente buscarCliente(int cedula) {
        for (Cliente cliente : clientes) {
            if (cliente.getCedula() == cedula) {
                return cliente;
            }
        }
        return null;
    }

    public AdministradorLocal buscarAdminLocCorreo(String correo) {
        for (AdministradorLocal administradorLocal : adminsLocales) {
            if (administradorLocal.getCorreo().equals(correo)) {
                return administradorLocal;
            }
        }
        return null;
    }

    public Empleado buscarEmpleadoCorreo(String correo) {
        for (Empleado empleado : empleados) {
            if (empleado.getCorreo().equals(correo)) {
                return empleado;
            }
        }
        return null;
    }

    public Empleado buscarEmpleadoCedula(int cedula) {
        for (Empleado empleado : empleados) {
            if (empleado.getCedula() == cedula) {
                return empleado;
            }
        }
        return null;
    }

    public Cliente buscarClienteCorreo(String correo) {
        for (Cliente cliente : clientes) {
            if (cliente.getCorreo().equals(correo)) {
                return cliente;
            }
        }
        return null;
    }

    public Alquiler buscarAlquiler(String fechaInicio, String fechaFinal, String inicioHora, String finHora,
            String lugarRecogidastr,
            String lugarDejadaStr, int cedula_cliente, String categoriaCarroStr) {
        for (Alquiler alquiler : alquileres) {
            if (alquiler.getInfoAlquilado()
                    .equals(fechaInicio + ";" + fechaFinal + ";" + inicioHora + ";" + finHora + ";" + lugarRecogidastr
                            + ";" + lugarDejadaStr + ";" + Integer.toString(cedula_cliente) + categoriaCarroStr)) {
                return alquiler;

            }
        }
        return null;

    }
    // Guardar Datos

    public void guardadatos() {
        try {
            // guardar super administradores
            File archivo = new File("usuarios/superAdministradores.txt");
            try {

                String texto = "";
                archivo.setWritable(true);
                FileWriter escriarchivo = new FileWriter(archivo, true);
                for (SuperAdministrador supeadmin : superadmins) {
                    boolean marcador = false;
                    try (BufferedReader br = new BufferedReader(new FileReader("usuarios/superAdministradores.txt"))) {
                        String linea;
                        while ((linea = br.readLine()) != null) {
                            String[] partes = linea.split(";");
                            String correoadm = partes[0];
                            if (correoadm.equals(supeadmin.getCorreo())) {
                                marcador = true;
                            }
                        }
                    } catch (IOException e) {
                        System.out.println("Error al leer y guardar archivo clientes: " + e.getMessage());
                    }
                    if (!marcador) {
                        texto += supeadmin.getInfo();
                    }

                }

                escriarchivo.write(texto);
                escriarchivo.close();
                archivo.setReadOnly();
            } catch (IOException e) {
                System.out.println("No se pudo guardar el archivo: " + e.getMessage());
            }

            archivo = new File("usuarios/usuarios.txt");
            try {
                String texto = "";
                archivo.setWritable(true);
                FileWriter escriarchivo = new FileWriter(archivo, true);
                for (SuperAdministrador superadmin : superadmins) {
                    boolean marcador = false;
                    try (BufferedReader br = new BufferedReader(new FileReader("usuarios/usuarios.txt"))) {
                        String linea;
                        while ((linea = br.readLine()) != null) {
                            String[] partes = linea.split(";");
                            String correoadm = partes[0];
                            if (correoadm.equals(superadmin.getCorreo())) {
                                marcador = true;
                            }
                        }
                    } catch (IOException e) {
                        System.out.println("Error al leer y guardar archivo clientes: " + e.getMessage());
                    }
                    if (!marcador) {
                        texto += superadmin.getInfoUsuario() + ";" + "superadmin" + "\r";
                    }

                }

                escriarchivo.write(texto);
                escriarchivo.close();
                archivo.setReadOnly();
            } catch (IOException e) {
                System.out.println("No se pudo guardar el archivo: " + e.getMessage());
            }
            if (supAdmin != null) {
                supAdmin.guardarDatos();
            } else if (adminloc != null) {
                adminloc.guardarDatos();
            } else if (empleado != null) {
                empleado.guardarDatos();
            } else if (cliente != null) {
                cliente.guardarDatos();
            }
        } catch (IOException e) {
            System.out.println("Error al guardar los datos: " + e.getMessage());
        }

    }

    public void guardar_eliminado() {
        // guardar super administradores
        File archivo = new File("usuarios/superAdministradores.txt");
        try {

            String texto = "";
            archivo.setWritable(true);
            FileWriter escriarchivo = new FileWriter(archivo);
            for (SuperAdministrador superadmin : superadmins) {
                texto += superadmin.getInfo();

            }

            escriarchivo.write(texto);
            escriarchivo.close();
            archivo.setReadOnly();
        } catch (IOException e) {
            System.out.println("No se pudo guardar el archivo: " + e.getMessage());
        }
        archivo = new File("usuarios/usuarios.txt");
        try {
            String texto = "";
            archivo.setWritable(true);
            FileWriter escriarchivo = new FileWriter(archivo);
            for (SuperAdministrador superAdmin : superadmins) {
                texto += superAdmin.getInfoUsuario() + ";" + "superadmin" + "\r";
            }

            escriarchivo.write(texto);
            escriarchivo.close();
            archivo.setReadOnly();
        } catch (IOException e) {
            System.out.println("No se pudo guardar el archivo: " + e.getMessage());
        }
        if (supAdmin != null) {
            supAdmin.guardar_eliminado();
        } else if (adminloc != null) {
            adminloc.guardar_eliminado();
        } else if (empleado != null) {
            empleado.guardar_eliminado();
        }
        if (cliente != null) {
            cliente.guardar_eliminado();
        }

    }

    public void cargarDatos() {

        if (!archivoExiste("usuarios/usuarios.txt")) {
            crearArchivosUsuarios();
            crearArchivosDatos();
            loginSuperAdmin();
            cargarSedes();
            cargarAdminLoc();
            adminloc = adminsLocales.get(0);
            cargarEmpleados();
            empleado = empleados.get(0);

        } else {
            loginSuperAdmin();
            cargarCategorias();
            cargarSedes();
            cargarSeguros();
            cargarVehiculos();
            cargarAdminLoc();
            adminloc = adminsLocales.get(0);
            cargarEmpleados();
            empleado = empleados.get(0);
            cargarClientes();
            if (SuperAdministrador.getClientes().size() != 0) {
                cliente = SuperAdministrador.getClientes().get(0);
            }

            cargar_Alquileres();
            cargar_Reservas();
            cargar_Mantenimientos();

        }
    }

    public void cargarCategorias() {
        try (BufferedReader br = new BufferedReader(new FileReader("datos/categorias.txt"))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] partes = linea.split(";");
                String nombre = partes[0];
                crearCategoria(nombre);
                for (int i = 1; i < partes.length; i += 3) {
                    String fechaInicio = partes[i];
                    String fechaFin = partes[i + 1];
                    int precio = Integer.parseInt(partes[i + 2]);
                    agregarPrecCategoria(fechaInicio, fechaFin, precio, nombre);
                }
            }
        } catch (IOException e) {
            System.out.println("Error al leer el archivo de categoria: " + e.getMessage());
        }

    }

    public void cargarSedes() {
        try (BufferedReader br = new BufferedReader(new FileReader("datos/sedes.txt"))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] partes = linea.split(";");

                if (partes.length == 6) {
                    String nombre = partes[0];
                    String direccion = partes[1];
                    String ciudad = partes[2];
                    String telefono = partes[3];
                    String horasApertura = partes[4];
                    String horaCierre = partes[5];
                    crearSede(nombre, direccion, ciudad, telefono, horasApertura, horaCierre);
                }

            }
        } catch (IOException e) {
            System.out.println("Error al leer el archivo de sedes: " + e.getMessage());
        }

    }

    public void cargarSeguros() {
        try (BufferedReader br = new BufferedReader(new FileReader("datos/seguros.txt"))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] partes = linea.split(";");
                if (partes.length == 3) {
                    String nombre = partes[0];
                    String info = partes[1];
                    int precio = Integer.parseInt(partes[2]);
                    crearSeguro(nombre, info, precio);
                }

            }
        } catch (IOException e) {
            System.out.println("Error al leer el archivo de seguros: " + e.getMessage());
        }

    }

    public void cargarVehiculos() {
        try (BufferedReader br = new BufferedReader(new FileReader("datos/vehiculos.txt"))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] partes = linea.split(";");
                if (partes.length == 10) {
                    String placa = partes[0];
                    String marca = partes[1];
                    String modelo = partes[2];
                    String color = partes[3];
                    short numPasajeros = Short.parseShort(partes[4]);
                    String tipoTransmicion = partes[5];
                    String categoriastr = partes[6];
                    String sedestr = partes[7];
                    String tipo = partes[8];
                    float porcentaje = Float.parseFloat(partes[9]);
                    crearVehiculo(placa, marca, modelo, color, numPasajeros, tipoTransmicion, categoriastr, sedestr, tipo,
                            porcentaje);
                }

            }
        } catch (IOException e) {
            System.out.println("Error al leer el archivo de seguros: " + e.getMessage());
        }

    }

    public void cargarAdminLoc() {
        try (BufferedReader br = new BufferedReader(new FileReader("usuarios/administradoresLocales.txt"))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] partes = linea.split(";");
                if (partes.length == 7) {
                    String correo = partes[0];
                    String contrasenia = partes[1];
                    String nombre = partes[2];
                    String datosContacto = partes[3];
                    String fechaNacimiento = partes[4];
                    int cedula = Integer.parseInt(partes[5]);
                    String sedestr = partes[6];
                    crearAdministradorLocal(correo, contrasenia, nombre, datosContacto, fechaNacimiento, cedula,
                            sedestr);
                }

            }
        } catch (IOException e) {
            System.out.println("Error al leer el archivo de seguros: " + e.getMessage());
        }

    }

    public void cargarEmpleados() {
        try (BufferedReader br = new BufferedReader(new FileReader("usuarios/empleados.txt"))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] partes = linea.split(";");
                if (partes.length == 7) {
                    String correo = partes[0];
                    String contrasenia = partes[1];
                    String nombre = partes[2];
                    String datosContacto = partes[3];
                    String fechaNacimiento = partes[4];
                    int cedula = Integer.parseInt(partes[5]);
                    String sedestr = partes[6];
                    crearEmpleado(correo, contrasenia, nombre, datosContacto, fechaNacimiento, cedula, sedestr);

                }

            }
        } catch (IOException e) {
            System.out.println("Error al leer el archivo de seguros: " + e.getMessage());
        }

    }

    public void cargarClientes() {
        try (BufferedReader br = new BufferedReader(new FileReader("usuarios/clientes.txt"))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] partes = linea.split(";");
                if (partes.length == 16) {
                    String correo = partes[0];
                    String contrasenia = partes[1];
                    int cedula = Integer.parseInt(partes[2]);
                    String nombre = partes[3];
                    String datosContato = partes[4];
                    String fechaNacimiento = partes[5];
                    String nacionalidad = partes[6];
                    String rutaImagen = partes[7];
                    String numeroLicencia = partes[8];
                    String paisExpedicion = partes[9];
                    String fechaVencimiento = partes[10];
                    String rutaImagenLicencia = partes[11];
                    String tipoTarjeta = partes[12];
                    String banco = partes[13];
                    String numeroTarjeta = partes[14];
                    short cVC = Short.parseShort(partes[15]);
                    crearCliente(correo, contrasenia, cedula, nombre, datosContato, fechaNacimiento, nacionalidad,
                            rutaImagen, numeroLicencia, paisExpedicion, fechaVencimiento, rutaImagenLicencia,
                            tipoTarjeta, banco, numeroTarjeta, cVC);

                }

            }
        } catch (IOException e) {
            System.out.println("Error al leer el archivo de seguros: " + e.getMessage());
        }

    }

    public void cargar_Alquileres() {
        try (BufferedReader br = new BufferedReader(new FileReader("datos/alquileres.txt"))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] partes = linea.split(";");
                String fechaInicio = partes[0];
                String fechaFinal = partes[1];
                String inicioHora = partes[2];
                String finHora = partes[3];
                String lugarRecogidaStr = partes[4];
                String ligarDejadaStr = partes[5];
                int cedula_cliente = Integer.parseInt(partes[6]);
                String categoriaCarroStr = partes[7];
                List<String> seg = new ArrayList<>();
                for (int i = 8; i < partes.length; i++) {
                    seg.add(partes[i]);
                }
                registrarAlquiler(fechaInicio, fechaFinal, inicioHora, finHora, lugarRecogidaStr, ligarDejadaStr,
                        cedula_cliente, categoriaCarroStr, seg);
                guardar_reserva_cliente(fechaInicio, fechaFinal, inicioHora, finHora, lugarRecogidaStr, ligarDejadaStr,
                        cedula_cliente, categoriaCarroStr, categoriaCarroStr);

            }
        } catch (IOException e) {
            System.out.println("Error al leer el archivo de alquileres: " + e.getMessage());
        }

    }

    public void cargar_Reservas() {
        try (BufferedReader br = new BufferedReader(new FileReader("datos/reservas.txt"))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] partes = linea.split(";");
                if (partes.length == 9) {
                    String fechaInicio = partes[0];
                    String fechaFinal = partes[1];
                    String inicioHora = partes[2];
                    String finHora = partes[3];
                    String lugarRecogidaStr = partes[4];
                    String ligarDejadaStr = partes[5];
                    int cedula_cliente = Integer.parseInt(partes[6]);
                    String categoriaCarroStr = partes[7];
                    String horaLLegada = partes[8];
                    registrarReserva(fechaInicio, fechaFinal, inicioHora, finHora, lugarRecogidaStr, ligarDejadaStr,
                            cedula_cliente, categoriaCarroStr, horaLLegada);
                }

            }
        } catch (IOException e) {
            System.out.println("Error al leer el archivo de reservas: " + e.getMessage());
        }

    }

    public void cargar_Mantenimientos() {
        try (BufferedReader br = new BufferedReader(new FileReader("datos/mantenimientos.txt"))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] partes = linea.split(";");
                if (partes.length == 5) {
                    boolean limpieza = Boolean.parseBoolean(partes[0]);
                    boolean mante = Boolean.parseBoolean(partes[1]);
                    String placa = partes[2];
                    short dias = Short.parseShort(partes[3]);
                    String correo = partes[4];
                    empleado = buscarEmpleadoCorreo(correo);
                    ponerMantenimientoCarro(limpieza, mante, placa, dias);
                    ;
                }

            }
        } catch (IOException e) {
            System.out.println("Error al leer el archivo de mantenimientos: " + e.getMessage());
        }

    }

    public void crearArchivosDatos() {
        try {
            File categoriasFile = new File("datos/categorias.txt");
            categoriasFile.getParentFile().mkdirs();
            File sedesFile = new File("datos/sedes.txt");
            sedesFile.getParentFile().mkdirs();
            File segurosFile = new File("datos/seguros.txt");
            segurosFile.getParentFile().mkdirs();
            File vehiculosFile = new File("datos/vehiculos.txt");
            vehiculosFile.getParentFile().mkdirs();
            File alquilerFile = new File("datos/alquileres.txt");
            alquilerFile.getParentFile().mkdirs();
            File reservasFile = new File("datos/reservas.txt");
            reservasFile.getParentFile().mkdirs();
            File mantenimientosFile = new File("datos/mantenimientos.txt");
            mantenimientosFile.getParentFile().mkdirs();

            categoriasFile.setWritable(true);
            sedesFile.setWritable(true);
            segurosFile.setWritable(true);
            vehiculosFile.setWritable(true);
            alquilerFile.setWritable(true);
            reservasFile.setWritable(true);

            FileWriter escriCategoria = new FileWriter(categoriasFile);
            FileWriter escrisedes = new FileWriter(sedesFile);
            FileWriter escriSeguros = new FileWriter(segurosFile);
            FileWriter escrivehiculos = new FileWriter(vehiculosFile);
            FileWriter escrialquiler = new FileWriter(alquilerFile);

            escriCategoria.write("Compacto;01/01/23;01/15/23;15000" + "\r");
            escrisedes.write("EstiloCar Rentas;Cll 127 #21-10;Cali;3089012345;10:00;17:00" + "\r");
            escriSeguros.write(
                    "Seguro a Terceros; Cubre daños causados a otros vehículos o propiedades en caso de accidente.;300000"
                            + "\r");
            escrivehiculos.write("ABC123;Toyota;Corolla;Blanco;5;Automático;Sedán;AutoRápido Alquileres" + "\r");
            escrialquiler.write(
                    "01/02/23;01/05/23;12:00;14:00;AutoRápido Alquileres;CityCar Rentas;12345678;Compacto;Seguro a Terceros;Seguro de Robo"
                            + "\r");

            escriCategoria.close();
            escrisedes.close();
            escriSeguros.close();
            escrivehiculos.close();
            escrialquiler.close();

            categoriasFile.setReadOnly();
            sedesFile.setReadOnly();
            segurosFile.setReadOnly();
            vehiculosFile.setReadOnly();
            alquilerFile.setReadOnly();
            reservasFile.setReadOnly();
            mantenimientosFile.setReadOnly();
        } catch (IOException e) {
            System.out.println("Error al crear los archivos de datos: " + e.getMessage());
        }
    }

    public void crearArchivosUsuarios() {

        try {
            File usuarios = new File("usuarios/usuarios.txt");
            usuarios.getParentFile().mkdirs();
            File superAdmins = new File("usuarios/superAdministradores.txt");
            superAdmins.getParentFile().mkdirs();
            File adminLocaFile = new File("usuarios/administradoresLocales.txt");
            adminLocaFile.getParentFile().mkdirs();
            File empleadosFile = new File("usuarios/empleados.txt");
            empleadosFile.getParentFile().mkdirs();
            File clientesFile = new File("usuarios/clientes.txt");
            clientesFile.getParentFile().mkdirs();

            usuarios.setWritable(true);
            superAdmins.setWritable(true);
            adminLocaFile.setWritable(true);
            empleadosFile.setWritable(true);
            clientesFile.setWritable(true);

            FileWriter escriUsuarios = new FileWriter(usuarios);
            FileWriter escriAdmin = new FileWriter(superAdmins);
            FileWriter escriAdminLoc = new FileWriter(adminLocaFile);
            FileWriter escriEmpleados = new FileWriter(empleadosFile);
            FileWriter escriCliente = new FileWriter(clientesFile);

            escriUsuarios.write("superadmin;supadmin123;superadmin" + "\r");
            escriAdmin.write("superadmin;supadmin123;super;aaafffef;12/12/1998;0" + "\r");
            escriAdminLoc.write("adminloc" + ";" + "adminloc" + ";" + "adminloc" + ";" + "aaaaa" + ";" + "12/12/1998"
                    + ";" + "0" + ";" + "Supersede" + "\r");
            escriEmpleados.write("empleado" + ";" + "empleado" + ";" + "Juan" + ";" + "sssss" + ";" + "12/12/1998" + ";"
                    + "0" + ";" + "Supersede" + "\r");
            escriCliente.write("root;root;root;root;root;12/12/1998;0;Supersede" + "\r");

            escriUsuarios.close();
            escriAdmin.close();
            escriAdminLoc.close();
            escriEmpleados.close();
            escriCliente.close();

            usuarios.setReadOnly();
            superAdmins.setReadOnly();
            adminLocaFile.setReadOnly();
            empleadosFile.setReadOnly();
            clientesFile.setReadOnly();

        } catch (IOException e) {
            System.out.println("Error al crear el archivos de usuario: " + e.getMessage());
        }
    }

    public void loginSuperAdmin() {
        try (BufferedReader br = new BufferedReader(new FileReader("usuarios/superAdministradores.txt"))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] partes = linea.split(";");
                if (partes.length == 6) {
                    String correo = partes[0];
                    String contrasenia = partes[1];
                    String nombre = partes[2];
                    String datosContacto = partes[3];
                    String fecha_Nacimiento = partes[4];
                    int cedula = Integer.parseInt(partes[5]);
                    crearAdministrador(correo, contrasenia, nombre, datosContacto, fecha_Nacimiento, cedula);
                    supAdmin = superadmins.get(0);
                }
            }
        } catch (IOException e) {
            System.out.println("Error al hacer el login del super admin: " + e.getMessage());
        }

    }

    public void loginAdminLoc(String correo) {
        adminloc = buscarAdminLocCorreo(correo);
        if (adminloc == null) {
            System.out.println("Error al cargar su usuario, por favor registrese nuevamente");
        } else {
            empleado = empleados.get(0);
        }
    }

    public void loginEmpleado(String correo) {
        empleado = buscarEmpleadoCorreo(correo);
        if (empleado == null) {
            System.out.println("Error al cargar su usuario, por favor registrese nuevamente");
        }

    }

    public void loginCliente(String correo) {
        cliente = buscarClienteCorreo(correo);
        if (cliente == null) {
            System.out.println("Error al cargar su usuario, por favor registrese nuevamente");
        } else {
            empleado = empleados.get(0);
        }
    }

    private boolean archivoExiste(String ruta) {
        File carpeta = new File(ruta.substring(0, ruta.lastIndexOf('/')));
        File archivo = new File(ruta);

        return carpeta.exists() && carpeta.isDirectory() && archivo.exists() && !archivo.isDirectory();
    }

    public AdministradorLocal traer_admin_local() {
        return adminloc;
    }

}
