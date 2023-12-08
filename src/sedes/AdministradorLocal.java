package sedes;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

/**
 * La clase AdministradorLocal representa a un empleado que tiene la capacidad de registrar y eliminar otros empleados en una sede específica.
 * Esta clase extiende la clase Empleado y tiene una lista de empleados registrados en la sede que administra.
 */
public class AdministradorLocal extends Empleado {

	/**
	 * Lista de empleados registrados en la sede que administra.
	 */
	private static List<Empleado> empleados = new ArrayList<>();

	/**
	 * Constructor de la clase AdministradorLocal.
	 * @param correo Correo electrónico del administrador local.
	 * @param contrasenia Contraseña del administrador local.
	 * @param nombre Nombre completo del administrador local.
	 * @param datosContacto Información de contacto del administrador local.
	 * @param fechaNacimiento Fecha de nacimiento del administrador local.
	 * @param cedula Número de cédula del administrador local.
	 * @param sede Sede a la que pertenece el administrador local.
	 */
	public AdministradorLocal(String correo, String contrasenia, String nombre, String datosContacto,
			String fechaNacimiento, int cedula, Sede sede) {
		super(correo, contrasenia, nombre, datosContacto, fechaNacimiento, cedula, sede);
	}

	/**
	 * Registra un nuevo empleado en la sede que administra.
	 * @param correo Correo electrónico del nuevo empleado.
	 * @param contrasenia Contraseña del nuevo empleado.
	 * @param nombre Nombre completo del nuevo empleado.
	 * @param datosContacto Información de contacto del nuevo empleado.
	 * @param fechaNacimiento Fecha de nacimiento del nuevo empleado.
	 * @param cedula Número de cédula del nuevo empleado.
	 * @param sede Sede a la que pertenece el nuevo empleado.
	 */
	public void registrarEmpleado(String correo, String contrasenia, String nombre, String datosContacto,
			String fechaNacimiento, int cedula, Sede sede) {
		Empleado sujeto = new Empleado(correo, contrasenia, nombre, datosContacto, fechaNacimiento, cedula, sede);
		empleados.add(sujeto);
		sede.addEmpleado(sujeto);
	}

	/**
	 * Elimina un empleado registrado en la sede que administra.
	 * @param cedula Número de cédula del empleado a eliminar.
	 */
	public void eliminarEmpleado(int cedula) {
		for (Empleado sujeto : empleados) {
			if (sujeto.getCedula() == cedula) {
				empleados.remove(sujeto);
				sujeto.getSede().eliminarEmpleado(sujeto);
				break;
			}
		}
	}

	/**
	 * Obtiene la lista de empleados registrados en la sede que administra.
	 * @return Lista de empleados registrados en la sede que administra.
	 */
	public List<Empleado> getEmpleados() {
		return empleados;
	}

	@Override

	public void guardarDatos() throws IOException {
		super.guardarDatos();
		// guardar empleados
		File archivo = new File("usuarios/empleados.txt");
		try {
			String texto = "";
			archivo.setWritable(true);
			FileWriter escriarchivo = new FileWriter(archivo, true);
			for (Empleado empleado : empleados) {
				boolean marcador = false;
				try (BufferedReader br = new BufferedReader(new FileReader("usuarios/empleados.txt"))) {
					String linea;
					while ((linea = br.readLine()) != null) {
						String[] partes = linea.split(";");
						String correoemp = partes[0];
						if (correoemp.equals(empleado.getCorreo())) {
							marcador = true;
						}
					}
				} catch (IOException e) {
					System.out.println("Error al leer y guardar archivo clientes: " + e.getMessage());
				}
				if (!marcador) {
					texto += empleado.getInfo();
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
			for (Empleado empleado : empleados) {
				boolean marcador = false;
				try (BufferedReader br = new BufferedReader(new FileReader("usuarios/usuarios.txt"))) {
					String linea;
					while ((linea = br.readLine()) != null) {
						String[] partes = linea.split(";");
						String correoemp = partes[0];
						if (correoemp.equals(empleado.getCorreo())) {
							marcador = true;
						}
					}
				} catch (IOException e) {
					System.out.println("Error al leer y guardar archivo clientes: " + e.getMessage());
				}
				if (!marcador) {
					texto += empleado.getInfoUsuario() + ";" + "empleado" + "\r";
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
		// guardar empleados
		File archivo = new File("usuarios/empleados.txt");
		try {
			String texto = "";
			archivo.setWritable(true);
			FileWriter escriarchivo = new FileWriter(archivo);
			for (Empleado empleado : empleados) {
				texto += empleado.getInfo();

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
			for (Empleado empleado : empleados) {
				texto += empleado.getInfoUsuario() + ";" + "empleado" + "\r";
			}

			escriarchivo.write(texto);
			escriarchivo.close();
			archivo.setReadOnly();
		} catch (IOException e) {
			System.out.println("No se pudo guardar el archivo: " + e.getMessage());
		}
	}
}