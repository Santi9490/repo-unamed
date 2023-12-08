package reserva_alquiler;

/**
 * La clase MedioPago representa un medio de pago para una reserva de alquiler.
 * Contiene información sobre el tipo de tarjeta, el banco, el número de tarjeta y el CVC.
 */
public class MedioPago {

	/**
	 * El tipo de tarjeta utilizado para el pago.
	 */
	private String tipoTarjeta;

	/**
	 * El banco asociado con la tarjeta de crédito utilizada para el pago.
	 */
	private String banco;

	/**
	 * El número de tarjeta de crédito utilizado para el pago.
	 */
	private String numeroTarjeta;

	/**
	 * El código de verificación de la tarjeta de crédito utilizado para el pago.
	 */
	private short CVC;

	/**
	 * Crea un nuevo objeto MedioPago con la información de pago proporcionada.
	 * 
	 * @param tipoTarjeta El tipo de tarjeta utilizado para el pago.
	 * @param banco El banco asociado con la tarjeta de crédito utilizada para el pago.
	 * @param numeroTarjeta El número de tarjeta de crédito utilizado para el pago.
	 * @param cVC El código de verificación de la tarjeta de crédito utilizado para el pago.
	 */
	public MedioPago(String tipoTarjeta, String banco, String numeroTarjeta, short cVC) {

		this.tipoTarjeta = tipoTarjeta;
		this.banco = banco;
		this.numeroTarjeta = numeroTarjeta;
		this.CVC = cVC;
	}

	/**
	 * Devuelve la información de la tarjeta de crédito utilizada para el pago en un formato de cadena.
	 * 
	 * @return La información de la tarjeta de crédito utilizada para el pago en un formato de cadena.
	 */
	public String getInfoTarjeta() {
		String texto = tipoTarjeta + ";" + banco + ";" + numeroTarjeta + ";" + Short.toString(CVC);
		return texto;
	}

}
