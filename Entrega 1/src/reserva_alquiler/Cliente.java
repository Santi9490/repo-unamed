package reserva_alquiler;

import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

import inventario.Vehiculo;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Representa un cliente que puede alquilar vehículos.
 * Contiene información personal del cliente, como su correo, contraseña, cédula, nombre, datos de contacto, fecha de nacimiento, nacionalidad, imagen de su documento de identidad, medio de pago y licencia de conducción.
 * También tiene una lista de alquileres que ha realizado.
 */
public class Cliente {

	/**
	 * Correo electrónico del cliente.
	 */
	private String correo;

	/**
	 * Contraseña del cliente.
	 */
	private String contrasenia;

	/**
	 * Cédula del cliente.
	 */
	private int cedula;

	/**
	 * Nombre del cliente.
	 */
	private String nombre;

	/**
	 * Datos de contacto del cliente.
	 */
	private String datosContacto;

	/**
	 * Fecha de nacimiento del cliente.
	 */
	private String fechaString;

	/**
	 * Nacionalidad del cliente.
	 */
	private String nacionalidad;

	/**
	 * Ruta de la imagen del documento de identidad del cliente.
	 */
	private String rutaImageString;

	/**
	 * Imagen del documento de identidad del cliente.
	 */
	private BufferedImage imagenDocumentoIdentidad;

	/**
	 * Medio de pago del cliente.
	 */
	private MedioPago medioPago;

	/**
	 * Licencia de conducción del cliente.
	 */
	private LicenciaConduccion licencia;

	/**
	 * Lista de alquileres que ha realizado el cliente.
	 */
	private List<Alquiler> alquileres = new ArrayList<>();

	/**
	 * Constructor de la clase Cliente.
	 * @param correo Correo electrónico del cliente.
	 * @param contrasenia Contraseña del cliente.
	 * @param cedula Cédula del cliente.
	 * @param nombre Nombre del cliente.
	 * @param datosContacto Datos de contacto del cliente.
	 * @param fechaNacimiento Fecha de nacimiento del cliente.
	 * @param nacionalidad Nacionalidad del cliente.
	 * @param rutaImagen Ruta de la imagen del documento de identidad del cliente.
	 * @param numeroLicencia Número de la licencia de conducción del cliente.
	 * @param paisExpedicion País de expedición de la licencia de conducción del cliente.
	 * @param fechaVencimiento Fecha de vencimiento de la licencia de conducción del cliente.
	 * @param rutaImagenLicencia Ruta de la imagen de la licencia de conducción del cliente.
	 * @param tipoTarjeta Tipo de tarjeta de crédito del cliente.
	 * @param banco Banco emisor de la tarjeta de crédito del cliente.
	 * @param numeroTarjeta Número de la tarjeta de crédito del cliente.
	 * @param cVC Código de verificación de la tarjeta de crédito del cliente.
	 */
	public Cliente(String correo, String contrasenia, int cedula, String nombre, String datosContacto,
			String fechaNacimiento, String nacionalidad, String rutaImagen,
			String numeroLicencia, String paisExpedicion, String fechaVencimiento, String rutaImagenLicencia,
			String tipoTarjeta, String banco, String numeroTarjeta, short cVC) {

		this.correo = correo;
		this.contrasenia = contrasenia;
		this.cedula = cedula;
		this.nombre = nombre;
		this.datosContacto = datosContacto;
		this.fechaString = fechaNacimiento;
		this.nacionalidad = nacionalidad;
		this.rutaImageString = rutaImagen;
		try {
			this.imagenDocumentoIdentidad = ImageIO.read(new File(rutaImagen));
		} catch (IOException e) {
			e.printStackTrace();
		}

		this.medioPago = new MedioPago(tipoTarjeta, banco, numeroTarjeta, cVC);
		this.licencia = new LicenciaConduccion(numeroLicencia, paisExpedicion, fechaVencimiento, rutaImagenLicencia);
	}

	/**
	 * Retorna la información del cliente en formato de texto.
	 * @return Información del cliente en formato de texto.
	 */
	public String getInfoCliente() {
		String texto = correo + ";" + contrasenia + ";" + Integer.toString(cedula) + ";" + nombre + ";" + datosContacto
				+ ";" + fechaString + ";" + nacionalidad + ";" + rutaImageString + ";" + licencia.getInfoLicencia()
				+ ";" + medioPago.getInfoTarjeta() + "\r";
		return texto;
	}

	/**
	 * Retorna la información del cliente en formato de texto.
	 * @return Información del cliente en formato de texto.
	 */
	public String getInfo() {
		String texto = String.format(
				"Nombre: %s%nCorreo: %s%nDatos de Contacto: %s%nFecha de Nacimiento: %tD%nNacionalidad: %s%nLicencia de Conduccion:%n%s%nMedio de Pago:%n%s",
				nombre, correo, datosContacto, fechaString, nacionalidad, licencia.getInfoLicencia(),
				medioPago.getInfoTarjeta());
		return texto;
	}

	/**
	 * Retorna el nombre del cliente.
	 * @return Nombre del cliente.
	 */
	public String getNombre() {
		return nombre;
	}

	/**
	 * Retorna la información de usuario del cliente en formato de texto.
	 * @return Información de usuario del cliente en formato de texto.
	 */
	public String getInfoUsuarioCliente() {
		String texto = correo + ";" + contrasenia;
		return texto;
	}

	/**
	 * Agrega un alquiler a la lista de alquileres del cliente.
	 * @param alquiler Alquiler a agregar.
	 */
	public void agregarAlquileres(Alquiler alquiler) {
		alquileres.add(alquiler);
	}

	/**
	 * Retorna la cédula del cliente.
	 * @return Cédula del cliente.
	 */
	public int getCedula() {
		return cedula;
	}

	public void devolverVehiculo(Vehiculo vehiculo) {
        // Aquí asumimos que tienes un método en Vehiculo para marcarlo como disponible
        vehiculo.setDisponible(true);

        if (!alquileres.isEmpty()) {
            Alquiler ultimoAlquiler = alquileres.get(alquileres.size() - 1);
            ultimoAlquiler.devolverCarro();
        }
    }

	/**
	 * Retorna el correo electrónico del cliente.
	 * @return Correo electrónico del cliente.
	 */
	public String getCorreo() {
		return correo;
	}

	/**
	 * Retorna la lista de alquileres del cliente.
	 * @return Lista de alquileres del cliente.
	 */
	public List<Alquiler> getAlquileres() {
		return alquileres;
	}

	/**
	 * Guarda los datos del cliente en un archivo.
	 * @throws IOException Si ocurre un error al guardar los datos.
	 */
	public void guardarDatos() throws IOException {
		// guardar alquileres
		File archivo = new File("datos/alquileres.txt");

		try {
			String texto = "";
			archivo.setWritable(true);
			FileWriter escriarchivo = new FileWriter(archivo);
			for (Alquiler alquiler : alquileres) {
				boolean marcador = false;
				try (BufferedReader br = new BufferedReader(new FileReader("datos/alquileres.txt"))) {
					String linea;
					while ((linea = br.readLine()) != null) {
						String[] partes = linea.split(";");
						if (partes.length == 8) {
							String fechaInicioal = partes[0];
							String fechaFinalal = partes[1];
							String inicioHoraal = partes[2];
							String finHoraal = partes[3];
							String cedula_clienteal = partes[6];
							if (fechaInicioal.equals(alquiler.getFechaInicio())
									&& fechaFinalal.equals(alquiler.getFechaFinal())
									&& inicioHoraal.equals(alquiler.getinicioHora())
									&& finHoraal.equals(alquiler.getfinHora())
									&& cedula_clienteal.equals(alquiler.getCedulaCliente())) {
								marcador = true;
							}
						}

					}
				} catch (IOException e) {
					System.out.println("Error al leer y guardar archivo alquileres: " + e.getMessage());
				}
				if (!marcador) {
					texto += alquiler.getInfoAlquilado();
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
	 * Guarda los datos eliminados del cliente en un archivo.
	 */
	public void guardar_eliminado() {
		// guardar alquileres
		File archivo = new File("datos/alquileres.txt");

		try {
			String texto = "";
			archivo.setWritable(true);
			FileWriter escriarchivo = new FileWriter(archivo);
			for (Alquiler alquiler : alquileres) {
				texto += alquiler.getInfoAlquilado();

			}

			escriarchivo.write(texto);
			escriarchivo.close();
			archivo.setReadOnly();
		} catch (IOException e) {
			System.out.println("No se pudo guardar el archivo: " + e.getMessage());
		}
		

	}

}
