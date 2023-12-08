package sedes;

import java.util.*;
import inventario.*;
import procesamiento.Fecha;

public class Sede {

	private String nombre;
	private String direccion;
	private String ciudad;
	private Date[] horasAtencion;
	private String telefono;
	private List<Vehiculo> vehiculos = new ArrayList<>();
	private List<Empleado> empleados = new ArrayList<>();

	/**
	 * Metodo constructor de la clase.
	 * 
	 * @param nombre       nombre de la sede
	 * @param direccion    direccion de la sede
	 * @param ciudad       ciudad de la sede
	 * @param telefono     telefono de la sede
	 * @param horaApertura hora apertura de la sede
	 * @param horaCierre   hora cierre de la sede
	 * @return instancia de la clase
	 */
	public Sede(String nombre, String direccion, String ciudad, String telefono, String horaApertura,
			String horaCierre) {
		this.nombre = nombre;
		this.direccion = direccion;
		this.ciudad = ciudad;
		this.telefono = telefono;
		this.horasAtencion = new Date[] { Fecha.convertirHora(horaApertura), Fecha.convertirHora(horaCierre) };
	}

	/**
	 * establece los valores de la sede generadas por defecto
	 * 
	 * @param nombre       nombre de la sede
	 * @param direccion    direccion de la sede
	 * @param ciudad       ciudad de la sede
	 * @param telefono     telefono de la sede
	 * @param horaApertura hora apertura de la sede
	 * @param horaCierre   hora cierre de la sede
	 * @return void
	 * @throws nombre-clase Descripción de la excepción lanzada.
	 */
	public void setSede(String nombre, String direccion, String ciudad, String telefono, String horaApertura,
			String horaCierre) {
		this.nombre = nombre;
		this.direccion = direccion;
		this.ciudad = ciudad;
		this.telefono = telefono;
		this.horasAtencion = new Date[] { Fecha.convertirHora(horaApertura), Fecha.convertirHora(horaCierre) };
	}

	/**
	 * Getter del nombre de la sede
	 * 
	 * @return String nombre de la sede
	 */
	public String getNombre() {
		return nombre;
	}

	/**
	 * Getter de las horas de atencion de la sede
	 * 
	 * @return lista con las horas de atencion de la sede
	 */
	public Date[] getHorasAtencion() {
		return horasAtencion;
	}

	/**
	 * Getter de la informacion de todos los parametros de la sede
	 * 
	 * @return String con los valores de todos los atributos
	 */
	public String getInfoSede() {
		String texto = nombre + ";" + direccion + ";" + ciudad + ";" + telefono + ";" + horasAtencion[0] + ";"
				+ horasAtencion[1] + "\r";
		return texto;

	}

	/**
	 * Getter de la informacion de todos los parametros de la sede, pero con formato
	 * especifico
	 * 
	 * @return String con los valores de todos los atributos formateados
	 */
	public String getInfo() {
		String texto = String.format(
				"    Nombre: %s%n    Dirección: %s%n    Ciudad: %s%n    Telefono: %s%n    Horas de Atencion: De %tT hasta %tT",
				nombre, direccion, ciudad, telefono, horasAtencion[0], horasAtencion[1]);
		return texto;

	}

	/**
	 * Getter de la ciudad de la sede
	 * 
	 * @return String ciudad de la sede
	 */
	public String getCiudad() {
		return ciudad;
	}

	/**
	 * Getter del telefono de la sede
	 * 
	 * @return String telefono de la sede
	 */
	public String getTelefono() {
		return telefono;
	}

	/**
	 * Getter de la lista de vehiculos de la sede
	 * 
	 * @return lista con las instancia de vehiculos almacenados en la sede.
	 */
	public List<Vehiculo> getVehiculos() {
		return vehiculos;
	}

	/**
	 * Getter de la lista de empleados de la sede
	 * 
	 * @return lista con las instancia de empleados almacenados en la sede.
	 */
	public List<Empleado> getEmpleados() {
		return empleados;
	}

	/**
	 * añadir vehiculo a la sede
	 *
	 * @param carro instancia de la clase vehiculo que queremos almacenar
	 */
	public void addVehiculo(Vehiculo carro) {
		vehiculos.add(carro);
	}

	/**
	 * eliminar vehiculo de la sede.
	 * 
	 * @precond es necesario que exista el carro a eliminar en la sede
	 * @param carro instancia de la clase vehiculo que se desea eliminar
	 */
	public void eliminarVehiculo(Vehiculo carro) {
		vehiculos.remove(carro);
	}

	/**
	 * añadir empleado a la sede
	 *
	 * @param empleado instancia de la clase vehiculo que queremos almacenar
	 */
	public void addEmpleado(Empleado empleado) {
		empleados.add(empleado);
	}

	/**
	 * eliminar empleado de la sede.
	 * 
	 * @precond es necesario que exista el empleado a eliminar en la sede
	 * @param empleado instancia de la clase Empleado que se desea eliminar
	 */
	public void eliminarEmpleado(Empleado empleado) {
		empleados.remove(empleado);
	}

}
