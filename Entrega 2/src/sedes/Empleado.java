package sedes;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

import inventario.*;
import procesamiento.Fecha;
import reserva_alquiler.Alquiler;
import reserva_alquiler.Cliente;
import reserva_alquiler.Reserva;
import reserva_alquiler.Seguros;

/**
 * La clase Empleado representa a un empleado de la empresa de alquiler de vehículos.
 * Contiene información personal del empleado, así como métodos para realizar operaciones
 * relacionadas con alquileres, reservas, mantenimiento de vehículos, entre otros.
 */
public class Empleado {

	/**
	 * Correo electrónico del empleado.
	 */
	private String correo;
	
	/**
	 * Contraseña del empleado.
	 */
	private String contrasenia;

	/**
	 * Nombre completo del empleado.
	 */
	private String nombre;

	/**
	 * Información de contacto del empleado.
	 */
	private String datosContacto;

	/**
	 * Fecha de nacimiento del empleado en formato String.
	 */
	private String fechaNaciminetoString;

	/**
	 * Fecha de nacimiento del empleado en formato Date.
	 */
	private Date fechaNacimiento;

	/**
	 * Cédula del empleado.
	 */
	private int cedula;

	/**
	 * Sede a la que pertenece el empleado.
	 */
	private Sede sede;

	/**
	 * Lista de clientes registrados en el sistema.
	 */
	private static List<Cliente> clientes = new ArrayList<>();

	/**
	 * Lista de alquileres registrados en el sistema.
	 */
	private static List<Alquiler> alquileres = new ArrayList<>();

	/**
	 * Lista de reservas registradas en el sistema.
	 */
	private static List<Reserva> reservas = new ArrayList<>();

	/**
	 * Lista de mantenimientos registrados en el sistema.
	 */
	private static List<Mantenimiento> mantenimientos = new ArrayList<>();

	/**
	 * Constructor de la clase Empleado.
	 * @param correo Correo electrónico del empleado.
	 * @param contrasenia Contraseña del empleado.
	 * @param nombre Nombre completo del empleado.
	 * @param datosContacto Información de contacto del empleado.
	 * @param fechaNacimiento Fecha de nacimiento del empleado en formato String.
	 * @param cedula Cédula del empleado.
	 * @param sede Sede a la que pertenece el empleado.
	 */
	public Empleado(String correo, String contrasenia, String nombre, String datosContacto, String fechaNacimiento,
			int cedula, Sede sede) {
		this.correo = correo;
		this.contrasenia = contrasenia;
		this.nombre = nombre;
		this.datosContacto = datosContacto;
		this.fechaNaciminetoString = fechaNacimiento;
		this.fechaNacimiento = Fecha.convertirFecha(fechaNacimiento);
		this.cedula = cedula;
		this.sede = sede;
	}

	/**
	 * Obtiene la sede a la que pertenece el empleado.
	 * @return Sede a la que pertenece el empleado.
	 */
	public Sede getSede() {
		return sede;
	}

	/**
	 * Obtiene la lista de alquileres registrados en el sistema.
	 * @return Lista de alquileres registrados en el sistema.
	 */
	public List<Alquiler> getAlquileres() {
		return alquileres;
	}

	/**
	 * Obtiene el nombre completo del empleado.
	 * @return Nombre completo del empleado.
	 */
	public String getNombre() {
		return nombre;
	}

	/**
	 * Obtiene la lista de reservas registradas en el sistema.
	 * @return Lista de reservas registradas en el sistema.
	 */
	public List<Reserva> getReservas() {
		return reservas;
	}

	/**
	 * Obtiene la lista de clientes registrados en el sistema.
	 * @return Lista de clientes registrados en el sistema.
	 */
	public static List<Cliente> getClientes() {
		return clientes;
	}

	/**
	 * Obtiene la lista de mantenimientos registrados en el sistema.
	 * @return Lista de mantenimientos registrados en el sistema.
	 */
	public List<Mantenimiento> getMantenimientos() {
		return mantenimientos;
	}

	/**
	 * Establece la sede a la que pertenece el empleado.
	 * @param sede Nueva sede a la que pertenece el empleado.
	 */
	public void setSede(Sede sede) {
		this.sede = sede;
	}

	/**
	 * Obtiene la cédula del empleado.
	 * @return Cédula del empleado.
	 */
	public int getCedula() {
		return cedula;
	}

	/**
	 * Obtiene el correo electrónico del empleado.
	 * @return Correo electrónico del empleado.
	 */
	public String getCorreo() {
		return correo;
	}

	/**
	 * Obtiene la información del empleado en formato de texto.
	 * @return Información del empleado en formato de texto.
	 */
	public String getInfo() {
		if (sede != null) {
			String texto = correo + ";" + contrasenia + ";" + nombre + ";" + datosContacto + ";" + fechaNaciminetoString
					+ ";" + Integer.toString(cedula) + ";" + sede.getNombre() + "\r";
			return texto;
		} else {
			String texto = correo + ";" + contrasenia + ";" + nombre + ";" + datosContacto + ";" + fechaNaciminetoString
					+ ";" + Integer.toString(cedula) + "\r";
			return texto;
		}
	}

	/**
	 * Obtiene la información del empleado en formato de texto con formato específico.
	 * @return Información del empleado en formato de texto con formato específico.
	 */
	public String getInfoEmpleado() {
		String texto = String.format(
				"Nombre: %s%nDatos de Contacto: %s%nFecha de Nacimiento: %tD%nCedula: %d%nSede:%n%s",
				nombre, datosContacto, fechaNacimiento, cedula, sede.getInfoSede());
		return texto;
	}

	/**
	 * Obtiene la información del usuario en formato de texto.
	 * @return Información del usuario en formato de texto.
	 */
	public String getInfoUsuario() {
		String texto = correo + ";" + contrasenia;
		return texto;
	}

	/**
	 * Realiza el traslado de un vehículo de una sede a otra.
	 * @param placa Placa del vehículo a trasladar.
	 * @param nuevaSede Nueva sede a la que se trasladará el vehículo.
	 */
	public void trasladoVehiculo(String placa, Sede nuevaSede) {
		for (Vehiculo carro : sede.getVehiculos()) {
			if (carro.getPlaca().equals(placa)) {
				carro.setSede(sede);
				break;
			}
		}
	}

	/**
	 * Realiza la recogida de un vehículo por parte de un cliente.
	 * @param cliente Cliente que realiza la recogida del vehículo.
	 * @param fechaInicio Fecha de inicio del alquiler.
	 * @param seguros Lista de seguros contratados por el cliente.
	 * @param modificaciones Arreglo de Strings que almacena información sobre las modificaciones realizadas en la reserva.
	 */
	public void recogidaVehiculo(Cliente cliente, Date fechaInicio, List<Seguros> seguros, String[] modificaciones) {
		for (Reserva reserva : reservas) {
			if (reserva.getCliente().equals(cliente) && reserva.getFechas()[0].equals(fechaInicio)) {
				if (modificaciones[0] == null) {
					modificaciones[0] = reserva.getFechas()[1].toString();
					modificaciones[1] = reserva.getLugarDejada().getNombre();
					modificaciones[2] = reserva.getinicioHora().toString();
					modificaciones[3] = reserva.getfinHora().toString();
				}
				Sede dejada = buscarSede(modificaciones[1]);
				registrarAlquiler(reserva.getFechas()[0].toString(), modificaciones[0], modificaciones[2],
						modificaciones[3], reserva.getLugarRecogida(), dejada, reserva.getCliente(),
						reserva.getCategoriaCarro(), seguros);
				reservas.remove(reserva);
				break;
			}
		}
	}

	/**
	 * Busca una sede en el sistema a partir de su nombre.
	 * @param nombre Nombre de la sede a buscar.
	 * @return Sede encontrada en el sistema, o null si no se encontró ninguna sede con ese nombre.
	 */
	public Sede buscarSede(String nombre) {
		for (Sede sede : SuperAdministrador.getSedes()) {
			if (sede.getNombre().equals(nombre)) {
				return sede;
			}
		}
		return null;
	}

	/**
	 * Agrega un seguro a un alquiler registrado en el sistema.
	 * @param nombre Nombre del seguro a agregar.
	 * @param alquiler Alquiler al que se le agregará el seguro.
	 */
	public void agregarSeguro(String nombre, Alquiler alquiler) {
		for (Seguros seguro : SuperAdministrador.getSeguros()) {
			if (seguro.getNombre().equals(nombre)) {
				alquiler.agregarSeguro(seguro);
				break;
			}
		}
	}

	/**
	 * Registra un mantenimiento para un vehículo.
	 * @param limpieza Indica si se realizará limpieza en el vehículo durante el mantenimiento.
	 * @param mantenimiento Indica si se realizará mantenimiento en el vehículo durante el mantenimiento.
	 * @param placa Placa del vehículo al que se le realizará el mantenimiento.
	 * @param diasEnMantenimiento Cantidad de días que durará el mantenimiento.
	 */
	public void ponerMantenimientoCarro(boolean limpieza, boolean mantenimiento, String placa,
			short diasEnMantenimiento) {
		Mantenimiento arreglito = new Mantenimiento(limpieza, mantenimiento, placa, diasEnMantenimiento, this);
		for (Vehiculo carro : sede.getVehiculos()) {
			if (carro.getPlaca().equals(placa)) {
				carro.setArreglo(arreglito);
				mantenimientos.add(arreglito);
				break;
			}
		}
	}

	/**
	 * Quita el mantenimiento de un vehículo.
	 * @param placa Placa del vehículo al que se le quitará el mantenimiento.
	 */
	public void quitarMantenimiento(String placa) {
		for (Vehiculo carro : sede.getVehiculos()) {
			if (carro.getPlaca().equals(placa)) {
				carro.setArreglo(null);
				break;
			}
		}
	}

	/**
	 * Registra un nuevo cliente en el sistema.
	 * @param correo Correo electrónico del cliente.
	 * @param contrasenia Contraseña del cliente.
	 * @param cedula Cédula del cliente.
	 * @param nombre Nombre completo del cliente.
	 * @param datosContacto Información de contacto del cliente.
	 * @param fechaNacimiento Fecha de nacimiento del cliente en formato String.
	 * @param nacionalidad Nacionalidad del cliente.
	 * @param rutaImagen Ruta de la imagen del cliente.
	 * @param numeroLicencia Número de licencia de conducir del cliente.
	 * @param paisExpedicion País de expedición de la licencia de conducir del cliente.
	 * @param fechaVencimiento Fecha de vencimiento de la licencia de conducir del cliente en formato String.
	 * @param rutaImagenLicencia Ruta de la imagen de la licencia de conducir del cliente.
	 * @param tipoTarjeta Tipo de tarjeta de crédito del cliente.
	 * @param banco Banco emisor de la tarjeta de crédito del cliente.
	 * @param numeroTarjeta Número de la tarjeta de crédito del cliente.
	 * @param cVC Código de verificación de la tarjeta de crédito del cliente.
	 */
	public void registrarCliente(String correo, String contrasenia, int cedula, String nombre, String datosContacto,
			String fechaNacimiento, String nacionalidad, String rutaImagen,
			String numeroLicencia, String paisExpedicion, String fechaVencimiento, String rutaImagenLicencia,
			String tipoTarjeta, String banco, String numeroTarjeta, short cVC) {
		Cliente cliente = new Cliente(correo, contrasenia, cedula, nombre, datosContacto, fechaNacimiento, nacionalidad,
				rutaImagen, numeroLicencia, paisExpedicion, fechaVencimiento, rutaImagenLicencia, tipoTarjeta, banco,
				numeroTarjeta, cVC);
		clientes.add(cliente);
	}

	/**
	 * Cancela una reserva realizada por un cliente.
	 * @param cliente Cliente que realizó la reserva.
	 * @param categorias Categoría del vehículo reservado.
	 */
	public void cancelarReserva(Cliente cliente, Categorias categorias) {
		for (Reserva reserva : reservas) {
			if (reserva.getCliente().equals(cliente) && reserva.getCategoriaCarro().equals(categorias)) {
				reservas.remove(reserva);
				break;
			}
		}
	}

	/**
	 * Registra una nueva reserva en el sistema.
	 * @param fechaInicio Fecha de inicio de la reserva en formato String.
	 * @param fechaFinal Fecha de finalización de la reserva en formato String.
	 * @param inicioHora Hora de inicio de la reserva en formato String.
	 * @param finHora Hora de finalización de la reserva en formato String.
	 * @param lugarRecogida Sede en la que se recogerá el vehículo.
	 * @param lugarDejada Sede en la que se dejará el vehículo.
	 * @param cliente Cliente que realiza la reserva.
	 * @param categoriaCarro Categoría del vehículo a reservar.
	 * @param horaLLegada Hora estimada de llegada del cliente a la sede de recogida en formato String.
	 * @return Precio de la reserva.
	 */
	public int registrarReserva(String fechaInicio, String fechaFinal, String inicioHora, String finHora,
			Sede lugarRecogida,
			Sede lugarDejada, Cliente cliente, Categorias categoriaCarro, String horaLLegada) {
		Reserva reserva = new Reserva(fechaInicio, fechaFinal, inicioHora, finHora, lugarRecogida, lugarDejada, cliente,
				categoriaCarro, horaLLegada);
		reservas.add(reserva);
		int precio = reserva.calcularPrecio();
		return (int) (precio * 0.3);
	}

	/**
	 * Registra un nuevo alquiler en el sistema.
	 * @param fechaInicio Fecha de inicio del alquiler en formato String.
	 * @param fechaFinal Fecha de finalización del alquiler en formato String.
	 * @param inicioHora Hora de inicio del alquiler en formato String.
	 * @param finHora Hora de finalización del alquiler en formato String.
	 * @param lugarRecogida Sede en la que se recogerá el vehículo.
	 * @param lugarDejada Sede en la que se dejará el vehículo.
	 * @param cliente Cliente que realiza el alquiler.
	 * @param categoriaCarro Categoría del vehículo a alquilar.
	 * @param seguros Lista de seguros contratados por el cliente.
	 */
	public void registrarAlquiler(String fechaInicio, String fechaFinal, String inicioHora, String finHora,
			Sede lugarRecogida,
			Sede lugarDejada, Cliente cliente, Categorias categoriaCarro, List<Seguros> seguros) {
		if (!categoriaCarro.disponibilidadCategoria()) {
			throw new IllegalArgumentException("No hay carros disponibles de esta categoria");
		}
		Alquiler alquiler = new Alquiler(fechaInicio, fechaFinal, inicioHora, finHora, lugarRecogida, lugarDejada,
				cliente, categoriaCarro);
		for (Seguros seguro : seguros) {
			alquiler.agregarSeguro(seguro);
		}
		alquileres.add(alquiler);
		cliente.agregarAlquileres(alquiler);
		alquiler.asignarCarro();
	}

	/**
	 * Registra la devolución de un vehículo por parte de un cliente.
	 * @param cedulaCliente Cédula del cliente que realiza la devolución.
	 * @param placa Placa del vehículo que se está devolviendo.
	 */
	public void registrarDevolucion(int cedulaCliente, String placa) {
		for (Cliente cliente : clientes) {
			if (cliente.getCedula() == cedulaCliente) {
				for (Alquiler alqui : cliente.getAlquileres()) {
					if (alqui.getCarroAsignado().getPlaca().equals(placa)) {
						alqui.devolverCarro();
						break;
					}
				}
			}
		}
	}

	/**
	 * Guarda los datos del sistema en archivos de texto.
	 * @throws IOException Si ocurre un error al guardar los archivos.
	 */
	public void guardarDatos() throws IOException {
		// guardar clientes
		File archivo = new File("usuarios/clientes.txt");

		try {
			String texto = "";
			archivo.setWritable(true);
			FileWriter escriarchivo = new FileWriter(archivo, true);
			for (Cliente cliente : clientes) {
				boolean marcador = false;
				try (BufferedReader br = new BufferedReader(new FileReader("usuarios/clientes.txt"))) {
					String linea;
					while ((linea = br.readLine()) != null) {
						String[] partes = linea.split(";");
						String correocli = partes[0];
						if (correocli.equals(cliente.getCorreo())) {
							marcador = true;
						}
					}
				} catch (IOException e) {
					System.out.println("Error al leer y guardar archivo clientes: " + e.getMessage());
				}
				if (!marcador) {
					texto += cliente.getInfoCliente();
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
			for (Cliente cliente : clientes) {
				boolean marcador = false;
				try (BufferedReader br = new BufferedReader(new FileReader("usuarios/usuarios.txt"))) {
					String linea;
					while ((linea = br.readLine()) != null) {
						String[] partes = linea.split(";");
						String correocli = partes[0];
						if (correocli.equals(cliente.getCorreo())) {
							marcador = true;
						}
					}
				} catch (IOException e) {
					System.out.println("Error al leer y guardar archivo clientes: " + e.getMessage());
				}
				if (!marcador) {
					texto += cliente.getInfoUsuarioCliente() + ";" + "cliente" + "\r";
				}

			}

			escriarchivo.write(texto);
			escriarchivo.close();
			archivo.setReadOnly();
		} catch (IOException e) {
			System.out.println("No se pudo guardar el archivo: " + e.getMessage());
		}

		// guardar alquileres
		archivo = new File("datos/alquileres.txt");

		try {
			String texto = "";
			archivo.setWritable(true);
			FileWriter escriarchivo = new FileWriter(archivo, true);
			for (Alquiler alquiler : alquileres) {
				boolean marcador = false;
				try (BufferedReader br = new BufferedReader(new FileReader("datos/alquileres.txt"))) {
					String linea;
					while ((linea = br.readLine()) != null) {
						String[] partes = linea.split(";");

						String fechaInicioal = partes[0];
						String fechaFinalal = partes[1];
						String inicioHoraal = partes[2];
						String finHoraal = partes[3];
						int cedula_clienteal = Integer.parseInt(partes[6]);
						if (fechaInicioal.equals(alquiler.getFechaInicio())
								&& fechaFinalal.equals(alquiler.getFechaFinal())
								&& inicioHoraal.equals(alquiler.getinicioHora())
								&& finHoraal.equals(alquiler.getfinHora())
								&& (cedula_clienteal == alquiler.getCedulaCliente())) {
							marcador = true;

						}

					}
				} catch (IOException e) {
					System.out.println("Error al leer y guardar archivo alquileres: " + e.getMessage());
				}
				if (!marcador) {
					texto += alquiler.getInfoAlquilado() + "\r";
				}

			}

			escriarchivo.write(texto);
			escriarchivo.close();
			archivo.setReadOnly();
		} catch (IOException e) {
			System.out.println("No se pudo guardar el archivo: " + e.getMessage());
		}

		// guardar reservas
		archivo = new File("datos/reservas.txt");

		try {
			String texto = "";
			archivo.setWritable(true);
			FileWriter escriarchivo = new FileWriter(archivo, true);
			for (Reserva reserva : reservas) {
				boolean marcador = false;
				try (BufferedReader br = new BufferedReader(new FileReader("datos/reservas.txt"))) {
					String linea;
					while ((linea = br.readLine()) != null) {
						String[] partes = linea.split(";");
						if (partes.length == 9) {
							String fechaInicioal = partes[0];
							String fechaFinalal = partes[1];
							String inicioHoraal = partes[2];
							String finHoraal = partes[3];
							String cedula_clienteal = partes[6];
							if (fechaInicioal.equals(reserva.getFechaInicio())
									&& fechaFinalal.equals(reserva.getFechaFinal())
									&& inicioHoraal.equals(reserva.getinicioHora())
									&& finHoraal.equals(reserva.getfinHora())
									&& cedula_clienteal.equals(reserva.getCedulaCliente())) {
								marcador = true;
							}
						}

					}
				} catch (IOException e) {
					System.out.println("Error al leer y guardar archivo reservas: " + e.getMessage());
				}
				if (!marcador) {
					texto += reserva.getInfoReserva() + "\r";
				}

			}

			escriarchivo.write(texto);
			escriarchivo.close();
			archivo.setReadOnly();
		} catch (IOException e) {
			System.out.println("No se pudo guardar el archivo: " + e.getMessage());
		}

		// guardar mantenimientos
		archivo = new File("datos/mantenimientos.txt");

		try {
			String texto = "";
			archivo.setWritable(true);
			FileWriter escriarchivo = new FileWriter(archivo, true);
			for (Mantenimiento mantenimiento : mantenimientos) {
				boolean marcador = false;
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

							if ((mantenimiento.isLimpieza() == limpieza) && (mantenimiento.isMantenimiento() == mante)
									&& (mantenimiento.getDias() == dias) && (mantenimiento.getPlaca().equals(placa))
									&& (mantenimiento.getEmpleado().getCorreo().equals(correo))) {
								marcador = true;
							}
						}

					}
				} catch (IOException e) {
					System.out.println("Error al leer y guardar archivo reservas: " + e.getMessage());
				}
				if (!marcador) {
					texto += mantenimiento.getMantenimientoStr() + "\r";
				}

			}

			escriarchivo.write(texto);
			escriarchivo.close();
			archivo.setReadOnly();
		} catch (IOException e) {
			System.out.println("No se pudo guardar el archivo: " + e.getMessage());
		}

	}
	
	/**
	 * Guarda los datos del sistema eliminados en archivos de texto.
	 * @throws IOException Si ocurre un error al guardar los archivos.
	 */	
	public void guardar_eliminado() {
		// guardar clientes
		File archivo = new File("usuarios/clientes.txt");

		try {
			String texto = "";
			archivo.setWritable(true);
			FileWriter escriarchivo = new FileWriter(archivo);
			for (Cliente cliente : clientes) {
				texto += cliente.getInfoCliente();
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
			for (Cliente cliente : clientes) {
				texto += cliente.getInfoUsuarioCliente() + ";" + "cliente" + "\r";

			}

			escriarchivo.write(texto);
			escriarchivo.close();
			archivo.setReadOnly();
		} catch (IOException e) {
			System.out.println("No se pudo guardar el archivo: " + e.getMessage());
		}

		// guardar alquileres
		archivo = new File("datos/alquileres.txt");

		try {
			String texto = "";
			archivo.setWritable(true);
			FileWriter escriarchivo = new FileWriter(archivo);
			for (Alquiler alquiler : alquileres) {
				texto += alquiler.getInfoAlquilado() + "\r";
			}

			escriarchivo.write(texto);
			escriarchivo.close();
			archivo.setReadOnly();
		} catch (IOException e) {
			System.out.println("No se pudo guardar el archivo: " + e.getMessage());
		}

		// guardar reservas
		archivo = new File("datos/reservas.txt");

		try {
			String texto = "";
			archivo.setWritable(true);
			FileWriter escriarchivo = new FileWriter(archivo);
			for (Reserva reserva : reservas) {
				texto += reserva.getInfoReserva() + "\r";
			}

			escriarchivo.write(texto);
			escriarchivo.close();
			archivo.setReadOnly();
		} catch (IOException e) {
			System.out.println("No se pudo guardar el archivo: " + e.getMessage());
		}

		// guardar mantenimientos
		archivo = new File("datos/mantenimientos.txt");

		try {
			String texto = "";
			archivo.setWritable(true);
			FileWriter escriarchivo = new FileWriter(archivo);
			for (Mantenimiento mantenimiento : mantenimientos) {
				texto += mantenimiento.getMantenimientoStr() + "\r";
			}

			escriarchivo.write(texto);
			escriarchivo.close();
			archivo.setReadOnly();
		} catch (IOException e) {
			System.out.println("No se pudo guardar el archivo: " + e.getMessage());
		}

	}

}
