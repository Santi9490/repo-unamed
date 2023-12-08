package inventario;

import java.util.*;
import reserva_alquiler.*;
import sedes.*;

/**
 * La clase Vehiculo representa un vehículo que puede ser alquilado en una sede de la empresa.
 * Contiene información sobre la placa, marca, modelo, color, número de pasajeros, tipo de transmisión,
 * categoría, mantenimiento, historial de alquileres, sede y estado actual (alquilado o disponible).
 */
public class Vehiculo {

	// ATRIBUTOS

	private String placa;

	private String tipo;

	private float porcentaje;

	private String marca;

	private String modelo;

	private String color;

	private short numPasajeros;

	private String tipoTransmicion;

	private Categorias categoria;

	private Mantenimiento arreglo = null;

	private List<Alquiler> historial = new ArrayList<>();;

	private Alquiler alquilado = null;

	private Sede sede = null;

	/**
	 * Constructor de la clase Vehiculo
	 * @param placa La placa del vehículo
	 * @param marca La marca del vehículo
	 * @param modelo El modelo del vehículo
	 * @param color El color del vehículo
	 * @param numPasajeros El número de pasajeros que puede transportar el vehículo
	 * @param tipoTransmicion El tipo de transmisión del vehículo
	 * @param categoria La categoría del vehículo
	 * @param sede La sede donde se encuentra el vehículo
	 */
	public Vehiculo(String placa, String marca, String modelo, String color, short numPasajeros,
			String tipoTransmicion, Categorias categoria, Sede sede, String tipo, float porcentaje) {
		this.placa = placa;
		this.marca = marca;
		this.modelo = modelo;
		this.color = color;
		this.numPasajeros = numPasajeros;
		this.tipoTransmicion = tipoTransmicion;
		this.categoria = categoria;
		this.sede = sede;
		this.tipo = tipo;
		this.porcentaje = porcentaje;

	}

	// GETTERS & SETTERS

	/**
	 * Obtiene la información del vehículo
	 * @return La información del vehículo
	 */
	public String getInfoVehiculo() {
		String texto = (placa + ";" + marca + ";" + modelo + ";" + color + ";" + Short.toString(numPasajeros) + ";"
				+ tipoTransmicion + ";" + categoria.getNombre() + ";" + sede.getNombre()) + tipo + Float.toString(porcentaje)+"\r";
		return texto;
	}

	/**
	 * Obtiene la información detallada del vehículo
	 * @return La información detallada del vehículo
	 */
	public String getInfo() {
		String texto = String.format(
				"Modelo: %s%nPlaca: %s%nMarca: %s%nColor: %s%nNumero de pasajeros: %d%nTipo de transmision: %s%nCategoria: %s",
				modelo, placa, marca, color, numPasajeros, tipoTransmicion, categoria.getNombre());
		return texto;
	}

	/**
	 * Obtiene la categoría del vehículo
	 * @return La categoría del vehículo
	 */
	public Categorias getCategoria() {
		return categoria;
	}

	/**
	 * Establece la categoría del vehículo
	 * @param categoria La nueva categoría del vehículo
	 */
	public void setCategoria(Categorias categoria) {
		this.categoria = categoria;
	}

	/**
	 * Obtiene el arreglo de mantenimiento del vehículo
	 * @return El arreglo de mantenimiento del vehículo
	 */
	public Mantenimiento getArreglo() {
		return arreglo;
	}

	/**
	 * Establece el arreglo de mantenimiento del vehículo
	 * @param arreglo El nuevo arreglo de mantenimiento del vehículo
	 */
	public void setArreglo(Mantenimiento arreglo) {
		this.arreglo = arreglo;
		this.alquilado = null;
	}

	/**
	 * Obtiene el historial de alquileres del vehículo
	 * @return El historial de alquileres del vehículo
	 */
	public List<Alquiler> getHistorial() {
		return historial;
	}

	/**
	 * Agrega un nuevo alquiler al historial de alquileres del vehículo
	 * @param alquiler El nuevo alquiler a agregar al historial de alquileres del vehículo
	 */
	public void agregarHistorial(Alquiler alquiler) {
		historial.add(alquiler);
	}

	/**
	 * Obtiene la sede donde se encuentra el vehículo
	 * @return La sede donde se encuentra el vehículo
	 */
	public Sede getSede() {
		return sede;
	}

	/**
	 * Establece la sede donde se encuentra el vehículo
	 * @param sede La nueva sede donde se encuentra el vehículo
	 */
	public void setSede(Sede sede) {
		this.sede = sede;
		this.alquilado = null;
	}

	/**
	 * Obtiene el modelo del vehículo
	 * @return El modelo del vehículo
	 */
	public String getModelo() {
		return modelo;
	}

	/**
	 * Obtiene la placa del vehículo
	 * @return La placa del vehículo
	 */
	public String getPlaca() {
		return placa;
	}

	/**
	 * Obtiene la marca del vehículo
	 * @return La marca del vehículo
	 */
	public String getMarca() {
		return marca;
	}

	/**
	 * Obtiene el color del vehículo
	 * @return El color del vehículo
	 */
	public String getColor() {
		return color;
	}

	/**
	 * Obtiene el alquiler actual del vehículo
	 * @return El alquiler actual del vehículo
	 */
	public Alquiler getAlquilado() {
		return alquilado;
	}

	/**
	 * Establece el alquiler actual del vehículo
	 * @param alquilado El nuevo alquiler actual del vehículo
	 */
	public void setAlquilado(Alquiler alquilado) {
		this.alquilado = alquilado;
		this.sede = null;
		this.arreglo = null;
	}

	// METODOS

	/**
	 * Obtiene la ubicación actual del vehículo
	 * @return La ubicación actual del vehículo
	 */
	public String ubicacionVehiculo() {
		String texto;
		if (sede != null && arreglo == null) {
			texto = String.format(
					"\nEl vehiculo se encuentra en:%n%s%nY ya esta DISPONIBLE para ser alquilado de nuevo.",
					sede.getInfo());
		} else if (sede != null && arreglo != null) {
			texto = String.format(
					"\nEl vehiculo se encuentra en:%n%s%nY NO ESTA DISPONIBLE para ser alquilado, ya que se encuentra:%n%s",
					sede.getInfo(), arreglo.getInfoMantenimiento());
		} else {
			texto = String.format("\nEl carro se encuentra ALQUILADO:%n%s", alquilado.getInfo());
		}

		return texto;
	}

	/**
	 * Verifica si el vehículo está disponible para alquilar
	 * @return true si el vehículo está disponible para alquilar, false en caso contrario
	 */
	public boolean disponible() {
		if (sede != null && arreglo == null) {
			return true;
		} else {
			return false;
		}
	}

}
