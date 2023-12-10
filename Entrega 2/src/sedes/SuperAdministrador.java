package sedes;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

import inventario.*;
import reserva_alquiler.Seguros;

/**
 * La clase SuperAdministrador representa a un usuario con permisos de
 * administrador
 * que tiene acceso a todas las funcionalidades del sistema. Esta clase hereda
 * de la
 * clase AdministradorLocal y agrega funcionalidades adicionales como la gestión
 * de
 * administradores locales, vehículos, categorías, sedes y seguros.
 */
public class SuperAdministrador extends AdministradorLocal {

	/**
	 * Lista de administradores locales registrados en el sistema.
	 */
	private static List<AdministradorLocal> adminsLocales = new ArrayList<>();

	/**
	 * Lista de vehículos registrados en el sistema.
	 */
	private static List<Vehiculo> carros = new ArrayList<>();

	/**
	 * Lista de categorías de vehículos registradas en el sistema.
	 */
	private static List<Categorias> categorias = new ArrayList<>();

	/**
	 * Lista de sedes registradas en el sistema.
	 */
	private static List<Sede> sedes = new ArrayList<>();

	/**
	 * Lista de seguros registrados en el sistema.
	 */
	private static List<Seguros> seguros = new ArrayList<>();

	/**
	 * Crea un nuevo objeto SuperAdministrador con los datos de correo, contraseña,
	 * nombre, datos de contacto, fecha de nacimiento y número de cédula
	 * especificados.
	 * 
	 * @param correo          el correo electrónico del superadministrador
	 * @param contrasenia     la contraseña del superadministrador
	 * @param nombre          el nombre completo del superadministrador
	 * @param datosContacto   los datos de contacto del superadministrador
	 * @param fechaNacimiento la fecha de nacimiento del superadministrador
	 * @param cedula          el número de cédula del superadministrador
	 */
	public SuperAdministrador(String correo, String contrasenia, String nombre, String datosContacto,
			String fechaNacimiento, int cedula) {
		super(correo, contrasenia, nombre, datosContacto, fechaNacimiento, cedula, null);
	}

	/**
	 * Obtiene la lista de administradores locales registrados en el sistema.
	 * 
	 * @return la lista de administradores locales
	 */
	public List<AdministradorLocal> getAdminsLocales() {
		return adminsLocales;
	}

	/**
	 * Obtiene la lista de vehículos registrados en el sistema.
	 * 
	 * @return la lista de vehículos
	 */
	public static List<Vehiculo> getCarros() {
		return carros;
	}

	/**
	 * Obtiene la lista de categorías de vehículos registradas en el sistema.
	 * 
	 * @return la lista de categorías de vehículos
	 */
	public static List<Categorias> getCategorias() {
		return categorias;
	}

	/**
	 * Obtiene la lista de sedes registradas en el sistema.
	 * 
	 * @return la lista de sedes
	 */
	public static List<Sede> getSedes() {
		return sedes;
	}

	/**
	 * Obtiene la lista de seguros registrados en el sistema.
	 * 
	 * @return la lista de seguros
	 */
	public static List<Seguros> getSeguros() {
		return seguros;
	}

	/**
	 * Registra un nuevo administrador local en el sistema.
	 * 
	 * @param correo          el correo electrónico del administrador local
	 * @param contrasenia     la contraseña del administrador local
	 * @param nombre          el nombre completo del administrador local
	 * @param datosContacto   los datos de contacto del administrador local
	 * @param fechaNacimiento la fecha de nacimiento del administrador local
	 * @param cedula          el número de cédula del administrador local
	 * @param sede            la sede a la que pertenece el administrador local
	 */
	public void registrarAdmin(String correo, String contrasenia, String nombre, String datosContacto,
			String fechaNacimiento, int cedula, Sede sede) {
		AdministradorLocal sujeto = new AdministradorLocal(correo, contrasenia, nombre, datosContacto, fechaNacimiento,
				cedula, sede);
		adminsLocales.add(sujeto);
	}

	/**
	 * Cambia la sede de un empleado en el sistema.
	 * 
	 * @param cedula     el número de cédula del empleado
	 * @param stringSede el nombre de la nueva sede del empleado
	 */
	public void cambiarSedeEmpleado(int cedula, String stringSede) {
		Sede nuevaSede = buscarSede(stringSede);
		Empleado empleado = buscarEmpleado(cedula);
		Sede sede = empleado.getSede();
		if (nuevaSede != null && empleado != null) {
			empleado.setSede(nuevaSede);
			sede.eliminarEmpleado(empleado);
			nuevaSede.addEmpleado(empleado);
		}
	}

	/**
	 * Elimina un administrador local del sistema.
	 * 
	 * @param cedula el número de cédula del administrador local a eliminar
	 */
	public void eliminarAdmin(int cedula) {
		for (AdministradorLocal sujeto : adminsLocales) {
			if (sujeto.getCedula() == cedula) {
				adminsLocales.remove(sujeto);
				break;
			}
		}
	}

	/**
	 * Registra un nuevo vehículo en el sistema.
	 * 
	 * @param placa           la placa del vehículo
	 * @param marca           la marca del vehículo
	 * @param modelo          el modelo del vehículo
	 * @param color           el color del vehículo
	 * @param numPasajeros    el número de pasajeros del vehículo
	 * @param tipoTransmicion el tipo de transmisión del vehículo
	 * @param categoria       la categoría del vehículo
	 * @param sede            la sede a la que pertenece el vehículo
	 */
	public void registrarCarro(String placa, String marca, String modelo, String color, short numPasajeros,
			String tipoTransmicion, Categorias categoria, Sede sede, String tipo, float porcentaje) {
		Vehiculo carro = new Vehiculo(placa, marca, modelo, color, numPasajeros, tipoTransmicion, categoria, sede, tipo, porcentaje);
		categoria.addCarro(carro);
		sede.addVehiculo(carro);
		carros.add(carro);
	}

	/**
	 * Elimina un vehículo del sistema.
	 * 
	 * @param placa la placa del vehículo a eliminar
	 */
	public void eliminarCarro(String placa) {
		for (Vehiculo carro : carros) {
			if (carro.getPlaca().equals(placa)) {
				carros.remove(carro);
				carro.getCategoria().eliminarCarro(carro);
				carro.getSede().eliminarVehiculo(carro);
				break;
			}
		}
	}

	/**
	 * Busca una categoría de vehículo en el sistema.
	 * 
	 * @param nombre el nombre de la categoría a buscar
	 * @return la categoría encontrada, o null si no existe
	 */
	public Categorias buscarCategoria(String nombre) {
		for (Categorias categoria : categorias) {
			if (categoria.getNombre().equals(nombre)) {
				return categoria;
			}
		}
		return null;
	}

	/**
	 * Busca una sede en el sistema.
	 * 
	 * @param nombre el nombre de la sede a buscar
	 * @return la sede encontrada, o null si no existe
	 */
	public Sede buscarSede(String nombre) {
		for (Sede sedem : sedes) {
			if (sedem.getNombre().equals(nombre)) {
				return sedem;
			}
		}
		return null;
	}

	/**
	 * Busca un empleado en el sistema.
	 * 
	 * @param cedula el número de cédula del empleado a buscar
	 * @return el empleado encontrado, o null si no existe
	 */
	public Empleado buscarEmpleado(int cedula) {
		for (Sede sede : sedes) {
			for (Empleado empleado : sede.getEmpleados()) {
				if (empleado.getCedula() == cedula) {
					return empleado;
				}
			}
		}
		return null;
	}

	/**
	 * Busca un vehículo en el sistema.
	 * 
	 * @param placa la placa del vehículo a buscar
	 * @return el vehículo encontrado, o null si no existe
	 */
	public Vehiculo buscarVehiculo(String placa) {
		for (Vehiculo carro : carros) {
			if (carro.getPlaca().equals(placa)) {
				return carro;
			}
		}
		return null;
	}

	/**
	 * Registra una nueva categoría de vehículo en el sistema.
	 * 
	 * @param nombre el nombre de la nueva categoría
	 */
	public void registrarCategoria(String nombre) {
		Categorias categoria = new Categorias(nombre);
		categorias.add(categoria);
	}

	/**
	 * Cambia el nombre de una categoría de vehículo en el sistema.
	 * 
	 * @param nombreAntiguo el nombre antiguo de la categoría
	 * @param nombreNuevo   el nuevo nombre de la categoría
	 */
	public void editarNombreCategoria(String nombreAntiguo, String nombreNuevo) {
		Categorias categoria = buscarCategoria(nombreAntiguo);
		if (categoria != null) {
			categoria.cambiarNombre(nombreNuevo);
		}
	}

	/**
	 * Cambia la categoría de un vehículo en el sistema.
	 * 
	 * @param placa          la placa del vehículo
	 * @param categoriaNueva el nombre de la nueva categoría del vehículo
	 */
	public void changeVehiculoCategoria(String placa, String categoriaNueva) {
		Vehiculo carro = buscarVehiculo(placa);
		Categorias categoria2 = buscarCategoria(categoriaNueva);
		if (categoria2 != null && carro != null) {
			Categorias categoria = carro.getCategoria();
			carro.setCategoria(categoria2);
			categoria.eliminarCarro(carro);
			categoria2.addCarro(carro);
		}
	}

	/**
	 * Agrega un precio a una categoría de vehículo en el sistema.
	 * 
	 * @param fechas1 la fecha de inicio del rango de precios
	 * @param fechas2 la fecha de fin del rango de precios
	 * @param precio  el precio a agregar
	 * @param nombre  el nombre de la categoría a la que se agregará el precio
	 */
	public void agregarPrecioCategoria(String fechas1, String fechas2, int precio, String nombre) {
		Categorias categoria = buscarCategoria(nombre);
		if (categoria != null) {
			categoria.addRangoFechas(fechas1, fechas2, precio);
		}
	}

	/**
	 * Cambia el precio de una categoría de vehículo en el sistema.
	 * 
	 * @param fecha  la fecha del rango de precios a cambiar
	 * @param precio el nuevo precio
	 * @param nombre el nombre de la categoría a la que se cambiará el precio
	 */
	public void cambiarPrecioCategoria(String fecha, int precio, String nombre) {
		Categorias categoria = buscarCategoria(nombre);
		if (categoria != null) {
			categoria.changePrecio(fecha, precio);
		}
	}

	/**
	 * Elimina un precio de una categoría de vehículo en el sistema.
	 * 
	 * @param fecha  la fecha del rango de precios a eliminar
	 * @param nombre el nombre de la categoría a la que se eliminará el precio
	 */
	public void eliminarPrecioCategoria(String fecha, String nombre) {
		Categorias categoria = buscarCategoria(nombre);
		if (categoria != null) {
			categoria.removeRangoFechas(fecha);
		}
	}

	/**
	 * Registra una nueva sede en el sistema.
	 * 
	 * @param nombre        el nombre de la nueva sede
	 * @param direccion     la dirección de la nueva sede
	 * @param ciudad        la ciudad de la nueva sede
	 * @param telefono      el teléfono de la nueva sede
	 * @param horasApertura las horas de apertura de la nueva sede
	 * @param horasCierre   las horas de cierre de la nueva sede
	 */
	public void registrarSede(String nombre, String direccion, String ciudad, String telefono, String horasApertura,
			String horasCierre) {
		Sede sede = new Sede(nombre, direccion, ciudad, telefono, horasApertura, horasCierre);
		sedes.add(sede);
	}

	/**
	 * Registra un nuevo seguro en el sistema.
	 * 
	 * @param nombre el nombre del nuevo seguro
	 * @param info   la información del nuevo seguro
	 * @param precio el precio del nuevo seguro
	 */
	public void registrarSeguro(String nombre, String info, int precio) {
		Seguros seguro = new Seguros(nombre, info, precio);
		seguros.add(seguro);
	}

	/**
	 * Elimina un seguro del sistema.
	 * 
	 * @param nombre el nombre del seguro a eliminar
	 */
	public void eliminarSeguro(String nombre) {
		for (Seguros seguro : seguros) {
			if (seguro.getNombre().equals(nombre)) {
				seguros.remove(seguro);
				break;
			}
		}
	}

	/**
	 * Cambia el precio de un seguro en el sistema.
	 * 
	 * @param nombre el nombre del seguro a cambiar
	 * @param precio el nuevo precio del seguro
	 */
	public void cambiarPrecioSeguro(String nombre, int precio) {
		for (Seguros seguro : seguros) {
			if (seguro.getNombre().equals(nombre)) {
				seguro.setPrecio(precio);
				break;
			}
		}
	}

	@Override
	public void guardarDatos() throws IOException {
		super.guardarDatos();

		// guardar administradores locales
		File archivo = new File("usuarios/administradoresLocales.txt");
		try {

			String texto = "";
			archivo.setWritable(true);
			FileWriter escriarchivo = new FileWriter(archivo, true);
			for (AdministradorLocal administradorLocal : adminsLocales) {
				boolean marcador = false;
				try (BufferedReader br = new BufferedReader(new FileReader("usuarios/administradoresLocales.txt"))) {
					String linea;
					while ((linea = br.readLine()) != null) {
						String[] partes = linea.split(";");
						String correoadm = partes[0];
						if (correoadm.equals(administradorLocal.getCorreo())) {
							marcador = true;
						}
					}
				} catch (IOException e) {
					System.out.println("Error al leer y guardar archivo clientes: " + e.getMessage());
				}
				if (!marcador) {
					texto += administradorLocal.getInfo();
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
			for (AdministradorLocal administradorLocal : adminsLocales) {
				boolean marcador = false;
				try (BufferedReader br = new BufferedReader(new FileReader("usuarios/usuarios.txt"))) {
					String linea;
					while ((linea = br.readLine()) != null) {
						String[] partes = linea.split(";");
						String correoadm = partes[0];
						if (correoadm.equals(administradorLocal.getCorreo())) {
							marcador = true;
						}
					}
				} catch (IOException e) {
					System.out.println("Error al leer y guardar archivo clientes: " + e.getMessage());
				}
				if (!marcador) {
					texto += administradorLocal.getInfoUsuario() + ";" + "adminlocal" + "\r";
				}

			}

			escriarchivo.write(texto);
			escriarchivo.close();
			archivo.setReadOnly();
		} catch (IOException e) {
			System.out.println("No se pudo guardar el archivo: " + e.getMessage());
		}

		// guardar vehiculos
		archivo = new File("datos/vehiculos.txt");
		try {
			String texto = "";
			archivo.setWritable(true);
			FileWriter escriarchivo = new FileWriter(archivo, true);
			for (Vehiculo vehiculo : carros) {
				boolean marcador = false;
				try (BufferedReader br = new BufferedReader(new FileReader("datos/vehiculos.txt"))) {
					String linea;
					while ((linea = br.readLine()) != null) {
						String[] partes = linea.split(";");
						String placa = partes[0];
						if (placa.equals(vehiculo.getPlaca())) {
							marcador = true;
						}
					}
				} catch (IOException e) {
					System.out.println("Error al leer y guardar archivo clientes: " + e.getMessage());
				}
				if (!marcador) {
					texto += vehiculo.getInfoVehiculo();
				}

			}

			escriarchivo.write(texto);
			escriarchivo.close();
			archivo.setReadOnly();
		} catch (IOException e) {
			System.out.println("No se pudo guardar el archivo: " + e.getMessage());
		}

		// guardar categorias
		archivo = new File("datos/categorias.txt");
		try {
			String texto = "";
			archivo.setWritable(true);
			FileWriter escriarchivo = new FileWriter(archivo, true);
			for (Categorias categoria : categorias) {
				boolean marcador = false;
				try (BufferedReader br = new BufferedReader(new FileReader("datos/categorias.txt"))) {
					String linea;
					while ((linea = br.readLine()) != null) {
						String[] partes = linea.split(";");
						String nombre = partes[0];
						if (nombre.equals(categoria.getNombre())) {
							marcador = true;
						}
					}
				} catch (IOException e) {
					System.out.println("Error al leer y guardar archivo clientes: " + e.getMessage());
				}
				if (!marcador) {
					texto += categoria.generarStrCategoria() + "\r";
				}

			}

			escriarchivo.write(texto);
			escriarchivo.close();
			archivo.setReadOnly();
		} catch (IOException e) {
			System.out.println("No se pudo guardar el archivo: " + e.getMessage());
		}

		// guardar sedes
		archivo = new File("datos/sedes.txt");
		try {
			String texto = "";
			archivo.setWritable(true);
			FileWriter escriarchivo = new FileWriter(archivo, true);
			for (Sede sede : sedes) {
				boolean marcador = false;
				try (BufferedReader br = new BufferedReader(new FileReader("datos/sedes.txt"))) {
					String linea;
					while ((linea = br.readLine()) != null) {
						String[] partes = linea.split(";");
						String nombre = partes[0];
						if (nombre.equals(sede.getNombre())) {
							marcador = true;
						}
					}
				} catch (IOException e) {
					System.out.println("Error al leer y guardar archivo clientes: " + e.getMessage());
				}
				if (!marcador) {
					texto += sede.getInfoSede();
				}

			}

			escriarchivo.write(texto);
			escriarchivo.close();
			archivo.setReadOnly();
		} catch (IOException e) {
			System.out.println("No se pudo guardar el archivo: " + e.getMessage());
		}

		// guardar seguros
		archivo = new File("datos/seguros.txt");
		try {
			String texto = "";
			archivo.setWritable(true);
			FileWriter escriarchivo = new FileWriter(archivo, true);
			for (Seguros seguro : seguros) {
				boolean marcador = false;
				try (BufferedReader br = new BufferedReader(new FileReader("datos/seguros.txt"))) {
					String linea;
					while ((linea = br.readLine()) != null) {
						String[] partes = linea.split(";");
						String nombre = partes[0];
						if (nombre.equals(seguro.getNombre())) {
							marcador = true;
						}
					}
				} catch (IOException e) {
					System.out.println("Error al leer y guardar archivo clientes: " + e.getMessage());
				}
				if (!marcador) {
					texto += seguro.getInfoSeguro();
				}

			}

			escriarchivo.write(texto);
			escriarchivo.close();
			archivo.setReadOnly();
		} catch (IOException e) {
			System.out.println("No se pudo guardar el archivo: " + e.getMessage());
		}

	}

	@Override

	public void guardar_eliminado() {
		super.guardar_eliminado();

		// guardar administradores locales
		File archivo = new File("usuarios/administradoresLocales.txt");
		try {

			String texto = "";
			archivo.setWritable(true);
			FileWriter escriarchivo = new FileWriter(archivo);
			for (AdministradorLocal administradorLocal : adminsLocales) {
				texto += administradorLocal.getInfo();

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
			for (AdministradorLocal administradorLocal : adminsLocales) {
				texto += administradorLocal.getInfoUsuario() + ";" + "adminlocal" + "\r";
			}

			escriarchivo.write(texto);
			escriarchivo.close();
			archivo.setReadOnly();
		} catch (IOException e) {
			System.out.println("No se pudo guardar el archivo: " + e.getMessage());
		}

		// guardar vehiculos
		archivo = new File("datos/vehiculos.txt");
		try {
			String texto = "";
			archivo.setWritable(true);
			FileWriter escriarchivo = new FileWriter(archivo);
			for (Vehiculo vehiculo : carros) {
				texto += vehiculo.getInfoVehiculo();
			}

			escriarchivo.write(texto);
			escriarchivo.close();
			archivo.setReadOnly();
		} catch (IOException e) {
			System.out.println("No se pudo guardar el archivo: " + e.getMessage());
		}

		// guardar categorias
		archivo = new File("datos/categorias.txt");
		try {
			String texto = "";
			archivo.setWritable(true);
			FileWriter escriarchivo = new FileWriter(archivo);
			for (Categorias categoria : categorias) {
				texto += categoria.generarStrCategoria() + "\r";

			}

			escriarchivo.write(texto);
			escriarchivo.close();
			archivo.setReadOnly();
		} catch (IOException e) {
			System.out.println("No se pudo guardar el archivo: " + e.getMessage());
		}

		// guardar sedes
		archivo = new File("datos/sedes.txt");
		try {
			String texto = "";
			archivo.setWritable(true);
			FileWriter escriarchivo = new FileWriter(archivo);
			for (Sede sede : sedes) {
				texto += sede.getInfoSede();

			}

			escriarchivo.write(texto);
			escriarchivo.close();
			archivo.setReadOnly();
		} catch (IOException e) {
			System.out.println("No se pudo guardar el archivo: " + e.getMessage());
		}

		// guardar seguros
		archivo = new File("datos/seguros.txt");
		try {
			String texto = "";
			archivo.setWritable(true);
			FileWriter escriarchivo = new FileWriter(archivo);
			for (Seguros seguro : seguros) {
				texto += seguro.getInfoSeguro();

			}

			escriarchivo.write(texto);
			escriarchivo.close();
			archivo.setReadOnly();
		} catch (IOException e) {
			System.out.println("No se pudo guardar el archivo: " + e.getMessage());
		}
	}

	/**
	 * Guarda las categorías editadas en el archivo "datos/categorias.txt".
	 */
	public void guardarCategoriaEditada() {
		// guardar categorias
		File archivo = new File("datos/categorias.txt");
		try {
			String texto = "";
			archivo.setWritable(true);
			FileWriter escriarchivo = new FileWriter(archivo);
			for (Categorias categoria : categorias) {
				texto += categoria.generarStrCategoria() + "\r";

			}

			escriarchivo.write(texto);
			escriarchivo.close();
			archivo.setReadOnly();
		} catch (IOException e) {
			System.out.println("No se pudo guardar el archivo: " + e.getMessage());
		}
	}

}
