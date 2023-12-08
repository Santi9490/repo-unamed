package inventario;

import java.util.*;

import procesamiento.Fecha;

/**
 * Clase que representa una categoría de vehículos en el inventario.
 * Contiene un nombre, una lista de vehículos y un mapa de precios por rango de fechas.
 */
public class Categorias {

	/**
	 * Nombre de la categoría.
	 */
	private String nombre;

	/**
	 * Lista de vehículos de la categoría.
	 */
	private List<Vehiculo> carros;

	/**
	 * Mapa de precios por rango de fechas.
	 */
	private Map<Date[], Integer> mapa;

	/**
	 * Constructor de la clase Categorias.
	 * @param nombre El nombre de la categoría.
	 */
	public Categorias(String nombre) {

		this.nombre = nombre;
		carros = new ArrayList<>();
		mapa = new HashMap<>();

	}

	/**
	 * Obtiene el nombre de la categoría.
	 * @return El nombre de la categoría.
	 */
	public String getNombre() {
		return nombre;
	}

	/**
	 * Cambia el nombre de la categoría.
	 * @param nombre El nuevo nombre de la categoría.
	 */
	public void cambiarNombre(String nombre) {
		this.nombre = nombre;
	}

	/**
	 * Obtiene la lista de vehículos de la categoría.
	 * @return La lista de vehículos de la categoría.
	 */
	public List<Vehiculo> getCarros() {
		return carros;
	}

	/**
	 * Obtiene el mapa de precios por rango de fechas.
	 * @return El mapa de precios por rango de fechas.
	 */
	public Map<Date[], Integer> getMapa() {
		return mapa;
	}

	/**
	 * Agrega un vehículo a la lista de vehículos de la categoría.
	 * @param carro El vehículo a agregar.
	 */
	public void addCarro(Vehiculo carro) {
		carros.add(carro);
	}

	/**
	 * Elimina un vehículo de la lista de vehículos de la categoría.
	 * @param carro El vehículo a eliminar.
	 */
	public void eliminarCarro(Vehiculo carro) {
		carros.remove(carro);
	}

	/**
	 * Obtiene el precio actual de la categoría.
	 * @return El precio actual de la categoría.
	 */
	public int getPrecio() {
		Date fechaActual = Fecha.getFechaActual();
		for (Date[] key : mapa.keySet()) {
			if (Fecha.isIn(key, fechaActual)) {
				return mapa.get(key);
			}
		}
		return 0;
	}

	/**
	 * Agrega un rango de fechas con su respectivo precio al mapa de precios por rango de fechas.
	 * @param fechas1 La primera fecha del rango.
	 * @param fechas2 La segunda fecha del rango.
	 * @param precio El precio correspondiente al rango de fechas.
	 */
	public void addRangoFechas(String fechas1, String fechas2, int precio) {
		Date[] rangoFechas = { Fecha.convertirFecha(fechas1), Fecha.convertirFecha(fechas2) };
		mapa.put(rangoFechas, precio);
	}

	/**
	 * Cambia el precio correspondiente a un rango de fechas en el mapa de precios por rango de fechas.
	 * @param fechaIn La fecha correspondiente al rango de fechas a modificar.
	 * @param precio El nuevo precio correspondiente al rango de fechas.
	 */
	public void changePrecio(String fechaIn, int precio) {
		Date fecha = Fecha.convertirFecha(fechaIn);
		for (Date[] key : mapa.keySet()) {
			if (Fecha.isIn(key, fecha)) {
				mapa.put(key, precio);
			}
		}
	}

	/**
	 * Elimina un rango de fechas del mapa de precios por rango de fechas.
	 * @param fechaIn La fecha correspondiente al rango de fechas a eliminar.
	 */
	public void removeRangoFechas(String fechaIn) {
		Date fecha = Fecha.convertirFecha(fechaIn);
		for (Date[] key : mapa.keySet()) {
			if (Fecha.isIn(key, fecha)) {
				mapa.remove(key);
				break;
			}
		}
	}

	/**
	 * Genera una cadena de caracteres con la información de la categoría.
	 * @return La cadena de caracteres con la información de la categoría.
	 */
	public String generarStrCategoria() {
		String respuesta = nombre;
		if (mapa.size() != 0) {
			for (Date[] key : mapa.keySet()) {
				int precio = mapa.get(key);
				String precio_str = Integer.toString(precio);
				String fecha_1 = Fecha.ontener_primera_fecha(key);
				String fecha_2 = Fecha.ontener_segunda_fecha(key);
				respuesta += ";" + fecha_1 + ";" + fecha_2 + ";" + precio_str;

			}

		}
		return respuesta;
	}

	/**
	 * Verifica si hay disponibilidad de vehículos en la categoría.
	 * @return true si hay disponibilidad de vehículos en la categoría, false en caso contrario.
	 */
	public boolean disponibilidadCategoria() {
		for (Vehiculo carro : carros) {
			if (carro.disponible()) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Calcula el promedio de precios de la categoría.
	 * @return El promedio de precios de la categoría.
	 */
	public int promedioPrecio() {
		int promedio = 0;
		for (Date[] key : mapa.keySet()) {
			promedio += mapa.get(key);
		}
		promedio = promedio / mapa.size();
		return promedio;
	}

}
