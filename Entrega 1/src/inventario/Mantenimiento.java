package inventario;

import sedes.*;

/**
 * La clase Mantenimiento representa el mantenimiento de un vehículo en el inventario.
 * Contiene información sobre si se ha realizado la limpieza y el mantenimiento, la placa del vehículo,
 * el número de días hasta que esté disponible y el empleado que realizó el mantenimiento.
 */
public class Mantenimiento {

	/**
	 * Indica si se ha realizado la limpieza del vehículo.
	 */
	private boolean limpieza;

	/**
	 * Indica si se ha realizado el mantenimiento del vehículo.
	 */
	private boolean mantenimiento;

	/**
	 * La placa del vehículo que se está manteniendo.
	 */
	private String placa;

	/**
	 * El número de días hasta que el vehículo esté disponible.
	 */
	private short dias;

	/**
	 * El empleado que realizó el mantenimiento.
	 */
	private Empleado empleado;

	/**
	 * Crea una instancia de la clase Mantenimiento con los valores especificados.
	 * @param limpieza Indica si se ha realizado la limpieza del vehículo.
	 * @param mantenimiento Indica si se ha realizado el mantenimiento del vehículo.
	 * @param placa La placa del vehículo que se está manteniendo.
	 * @param dias El número de días hasta que el vehículo esté disponible.
	 * @param empleado El empleado que realizó el mantenimiento.
	 */
	public Mantenimiento(boolean limpieza, boolean mantenimiento, String placa, short dias, Empleado empleado) {
		this.limpieza = limpieza;
		this.mantenimiento = mantenimiento;
		this.dias = dias;
		this.empleado = empleado;
		this.placa = placa;
	}

	/**
	 * Devuelve si se ha realizado la limpieza del vehículo.
	 * @return true si se ha realizado la limpieza, false en caso contrario.
	 */
	public boolean isLimpieza() {
		return limpieza;
	}

	/**
	 * Establece si se ha realizado la limpieza del vehículo.
	 * @param limpieza true si se ha realizado la limpieza, false en caso contrario.
	 */
	public void setLimpieza(boolean limpieza) {
		this.limpieza = limpieza;
	}

	/**
	 * Devuelve si se ha realizado el mantenimiento del vehículo.
	 * @return true si se ha realizado el mantenimiento, false en caso contrario.
	 */
	public boolean isMantenimiento() {
		return mantenimiento;
	}

	/**
	 * Establece si se ha realizado el mantenimiento del vehículo.
	 * @param mantenimiento true si se ha realizado el mantenimiento, false en caso contrario.
	 */
	public void setMantenimiento(boolean mantenimiento) {
		this.mantenimiento = mantenimiento;
	}

	/**
	 * Devuelve el número de días hasta que el vehículo esté disponible.
	 * @return El número de días hasta que el vehículo esté disponible.
	 */
	public short getDias() {
		return dias;
	}

	/**
	 * Establece el número de días hasta que el vehículo esté disponible.
	 * @param dias El número de días hasta que el vehículo esté disponible.
	 */
	public void setDias(short dias) {
		this.dias = dias;
	}

	/**
	 * Devuelve el empleado que realizó el mantenimiento.
	 * @return El empleado que realizó el mantenimiento.
	 */
	public Empleado getEmpleado() {
		return empleado;
	}

	/**
	 * Devuelve la placa del vehículo que se está manteniendo.
	 * @return La placa del vehículo que se está manteniendo.
	 */
	public String getPlaca() {
		return placa;
	}

	/**
	 * Devuelve una cadena con información sobre el mantenimiento del vehículo.
	 * @return Una cadena con información sobre el mantenimiento del vehículo.
	 */
	public String getInfoMantenimiento() {
		String texto = String.format(
				"    Limpieza: %b%n    Mantenimiento: %b%n    Disponibilidad en %d dias. %n    Mantenimiento puesto por:%n%s",
				limpieza, mantenimiento, dias, empleado.getInfo());
		return texto;
	}

	/**
	 * Devuelve una cadena con información sobre el mantenimiento del vehículo en formato de archivo CSV.
	 * @return Una cadena con información sobre el mantenimiento del vehículo en formato de archivo CSV.
	 */
	public String getMantenimientoStr() {
		String texto = String.valueOf(limpieza) + ";" + String.valueOf(mantenimiento) + ";" + placa + ";"
				+ Short.toString(dias) + ";" + empleado.getCorreo();
		return texto;
	}
}
