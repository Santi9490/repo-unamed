package reserva_alquiler;

import javax.imageio.ImageIO;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * La clase LicenciaConduccion representa la información de una licencia de conducción.
 * Contiene el número de la licencia, el país de expedición, la fecha de vencimiento y la imagen de la licencia.
 */
public class LicenciaConduccion {

	/**
	 * Número de la licencia de conducción.
	 */
	private String numeroLicencia;

	/**
	 * País de expedición de la licencia de conducción.
	 */
	private String paisExpedicion;

	/**
	 * Fecha de vencimiento de la licencia de conducción en formato de cadena.
	 */
	private String fechaString;

	/**
	 * Ruta de la imagen de la licencia de conducción.
	 */
	private String rutaImageString;

	/**
	 * Imagen de la licencia de conducción.
	 */
	private BufferedImage imagenLicencia;

	/**
	 * Constructor de la clase LicenciaConduccion.
	 * @param numeroLicencia Número de la licencia de conducción.
	 * @param paisExpedicion País de expedición de la licencia de conducción.
	 * @param fechaVencimiento Fecha de vencimiento de la licencia de conducción en formato de cadena.
	 * @param imagenLicencia Ruta de la imagen de la licencia de conducción.
	 */
	public LicenciaConduccion(String numeroLicencia, String paisExpedicion, String fechaVencimiento,
			String imagenLicencia) {

		this.numeroLicencia = numeroLicencia;
		this.paisExpedicion = paisExpedicion;
		this.fechaString = fechaVencimiento;
		this.rutaImageString = imagenLicencia;
		try {
			this.imagenLicencia = ImageIO.read(new File(imagenLicencia));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Obtiene la información de la licencia de conducción.
	 * @return Cadena con la información de la licencia de conducción.
	 */
	public String getInfoLicencia() {
		String texto = numeroLicencia + ";" + paisExpedicion + ";" + fechaString + ";" + rutaImageString;
		return texto;
	}
}
