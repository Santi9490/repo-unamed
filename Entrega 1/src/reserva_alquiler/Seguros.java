package reserva_alquiler;

public class Seguros {

	private String nombre;

	private String info;

	private int precio;

	/**
	 * contructor de la clase
	 */
	public Seguros(String nombre, String info, int precio) {

		this.nombre = nombre;
		this.info = info;
		this.precio = precio;
	}

	/**
	 * Getter de la info total del seguro
	 * 
	 * @return String separado por ; con la info total del seguro
	 */
	public String getInfoSeguro() {
		String texto = nombre + ";" + info + ";" + precio + "\r";
		return texto;
	}

	/**
	 * Getter del nombre del seguro
	 * 
	 * @return String con nombre del seguro
	 */
	public String getNombre() {
		return nombre;
	}

	/**
	 * Setter del nombre del seguro
	 * 
	 * @param nombre String del seguro
	 */
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	/**
	 * Getter del informacion del seguro
	 * 
	 * @return String con la info del seguro
	 */
	public String getInfo() {
		return info;
	}

	/**
	 * Setter de la informacion del seguro
	 * 
	 * @param info String con la info del seguro
	 */
	public void setInfo(String info) {
		this.info = info;
	}

	/**
	 * Getter del precio seguro
	 * 
	 * @return int precio del seguro
	 */
	public int getPrecio() {
		return precio;
	}

	/**
	 * Setter del precio seguro
	 * 
	 * @param precio int con precio del seguro
	 */
	public void setPrecio(int precio) {
		this.precio = precio;
	}

}
