package reserva_alquiler;

import java.util.*;

import inventario.*;
import procesamiento.Fecha;
import sedes.Sede;
import sedes.SuperAdministrador;


/**
 * La clase Alquiler representa un alquiler de un vehículo por parte de un cliente.
 * Contiene información sobre las fechas de alquiler, la hora de entrega y recogida, las sedes de recogida y entrega,
 * el cliente que realiza el alquiler, la categoría del vehículo y el vehículo asignado.
 * También permite agregar conductores adicionales y seguros, asignar un vehículo disponible y calcular el precio del alquiler.
 */
public class Alquiler {

	// Atributos de la clase
	private Date[] fechas;
	private String fechainicioString;
	private String fechaFinalString;
	private Date[] rangoHoraEntrega;
	private Sede[] lugar;
	private Cliente cliente;
	private String inicio_Hora;
	private String finHora;
	private Sede lugar_recojida;
	private Sede lugar_dejada;
	private Categorias categoriaCarro;
	private Vehiculo carroAsignado = null;
	private List<LicenciaConduccion> conductores = new ArrayList<>();
	private List<Seguros> seguros = new ArrayList<>();
	private String estado;

	/**
	 * Constructor de la clase Alquiler.
	 * @param fechaInicio La fecha de inicio del alquiler en formato String.
	 * @param fechaFinal La fecha de finalización del alquiler en formato String.
	 * @param inicioHora La hora de recogida del vehículo en formato String.
	 * @param finHora La hora de entrega del vehículo en formato String.
	 * @param lugarRecogida La sede de recogida del vehículo.
	 * @param lugarDejada La sede de entrega del vehículo.
	 * @param cliente El cliente que realiza el alquiler.
	 * @param categoriaCarro La categoría del vehículo solicitado.
	 * @throws IllegalArgumentException Si el rango de horas de entrega del vehículo no está dentro del horario de atención de la sede de entrega.
	 */
	public Alquiler(String fechaInicio, String fechaFinal, String inicioHora, String finHora, Sede lugarRecogida,
					Sede lugarDejada, Cliente cliente, Categorias categoriaCarro) throws IllegalArgumentException {

		this.fechas = new Date[] { Fecha.convertirFecha(fechaFinal), Fecha.convertirFecha(fechaFinal) };
		this.fechainicioString = fechaInicio;
		this.fechaFinalString = fechaFinal;
		this.inicio_Hora = inicioHora;
		this.finHora = finHora;
		this.lugar_recojida = lugarRecogida;
		this.lugar_dejada = lugarDejada;
		this.rangoHoraEntrega = new Date[] { Fecha.convertirHora(inicioHora), Fecha.convertirHora(finHora) };
		this.lugar = new Sede[] { lugarRecogida, lugarDejada };
		this.cliente = cliente;
		this.categoriaCarro = categoriaCarro;
		this.estado = "Activo";

		if (!Fecha.isIn(lugarDejada.getHorasAtencion(), rangoHoraEntrega[0])
				|| !Fecha.isIn(lugarDejada.getHorasAtencion(), rangoHoraEntrega[1])) {
			throw new IllegalArgumentException(
					"El rango de horas de entrega del vehículo no esta dentro del horario de atencion de la sede");
		}

	}

	/**
	 * Retorna un String con la información del alquiler para ser mostrada en la interfaz de usuario.
	 * @return Un String con la información del alquiler.
	 */
	public String getInfoAlquilado() {
		String texto = fechainicioString + ";" + fechaFinalString + ";" + inicio_Hora + ";" + finHora + ";"
				+ lugar_recojida.getNombre() + ";" + lugar_dejada.getNombre() + ";"
				+ Integer.toString(cliente.getCedula()) + ";" + categoriaCarro.getNombre();
		return texto;
	}

	/**
	 * Retorna un String con la información del alquiler y el vehículo asignado para ser mostrada en la interfaz de usuario.
	 * @return Un String con la información del alquiler y el vehículo asignado.
	 */
	public String getInfo() {
		String texto = String.format("    Carro:%n%s%n    Cliente:%n%s%n    Devolucion el dia %tD en: %n%s.",
				carroAsignado.getInfo(), cliente.getInfoCliente(), fechas[1], lugar[1].getInfoSede());
		return texto;
	}

	/**
	 * Retorna un String con la información de la reserva para ser mostrada en la interfaz de usuario.
	 * @return Un String con la información de la reserva.
	 */
	public String getInfoReserva() {
		String texto = fechainicioString + ";" + fechaFinalString + ";" + rangoHoraEntrega[0] + ";"
				+ rangoHoraEntrega[1] + ";" + lugar[0].getNombre() + ";" + lugar[1].getNombre() + ";"
				+ Integer.toString(cliente.getCedula()) + categoriaCarro.getNombre();
		return texto;
	}

	/**
	 * Retorna la cédula del cliente que realiza el alquiler.
	 * @return La cédula del cliente.
	 */
	public int getCedulaCliente() {
		return cliente.getCedula();
	}



	/**
	 * Retorna el objeto Cliente que realiza el alquiler.
	 * @return El objeto Cliente.
	 */
	public Cliente getCliente() {
		return cliente;
	}

	/**
	 * Retorna la fecha de inicio del alquiler en formato String.
	 * @return La fecha de inicio del alquiler.
	 */
	public String getFechaInicio() {
		return fechainicioString;
	}

	/**
	 * Retorna la fecha de finalización del alquiler en formato String.
	 * @return La fecha de finalización del alquiler.
	 */
	public String getFechaFinal() {
		return fechaFinalString;
	}

	/**
	 * Retorna el objeto Vehiculo asignado al alquiler.
	 * @return El objeto Vehiculo asignado.
	 */
	public Vehiculo getCarroAsignado() {
		return carroAsignado;
	}

	/**
	 * Retorna la hora de recogida del vehículo en formato String.
	 * @return La hora de recogida del vehículo.
	 */
	public String getinicioHora() {
		return inicio_Hora;
	}

	/**
	 * Retorna la hora de entrega del vehículo en formato String.
	 * @return La hora de entrega del vehículo.
	 */
	public String getfinHora() {
		return finHora;
	}

	/**
	 * Retorna un arreglo de objetos Date con las fechas de inicio y finalización del alquiler.
	 * @return Un arreglo de objetos Date con las fechas de inicio y finalización del alquiler.
	 */
	public Date[] getFechas() {
		return fechas;
	}

	/**
	 * Retorna la sede de recogida del vehículo.
	 * @return La sede de recogida del vehículo.
	 */
	public Sede getLugarRecogida() {
		return lugar_recojida;
	}

	/**
	 * Retorna la sede de entrega del vehículo.
	 * @return La sede de entrega del vehículo.
	 */
	public Sede getLugarDejada() {
		return lugar_dejada;
	}

	/**
	 * Retorna la categoría del vehículo solicitado.
	 * @return La categoría del vehículo.
	 */
	public Categorias getCategoriaCarro() {
		return categoriaCarro;
	}

	/**
	 * Agrega un conductor adicional al alquiler.
	 * @param numeroLicencia El número de la licencia de conducción del conductor adicional.
	 * @param paisExpedicion El país de expedición de la licencia de conducción del conductor adicional.
	 * @param fechaVencimiento La fecha de vencimiento de la licencia de conducción del conductor adicional en formato String.
	 * @param imagenLicencia La imagen de la licencia de conducción del conductor adicional en formato String.
	 */
	public void agregarConductor(String numeroLicencia, String paisExpedicion, String fechaVencimiento,
								 String imagenLicencia) {
		LicenciaConduccion conductor = new LicenciaConduccion(numeroLicencia, paisExpedicion, fechaVencimiento,
				imagenLicencia);
		conductores.add(conductor);
	}

	/**
	 * Agrega un seguro adicional al alquiler.
	 * @param seguro El objeto Seguros a agregar.
	 */
	public void agregarSeguro(Seguros seguro) {
		seguros.add(seguro);
	}

	/**
	 * Asigna un vehículo disponible al alquiler.
	 * Si no hay vehículos disponibles en la categoría solicitada, busca en categorías superiores hasta encontrar uno disponible.
	 * Si no hay vehículos disponibles en ninguna categoría, lanza una excepción.
	 * Asigna el vehículo al objeto Alquiler y agrega el objeto Alquiler al historial del vehículo.
	 * @throws IllegalArgumentException Si no hay vehículos disponibles para ninguna categoría mejor o igual a la solicitada.
	 */
	public void asignarCarro() throws IllegalArgumentException {
		for (Vehiculo carro : categoriaCarro.getCarros()) {
			if (carro.disponible()) {
				this.carroAsignado = carro;
				break;
			}
		}
		if (carroAsignado == null) {
			int promedio = categoriaCarro.promedioPrecio();
			for (Categorias cate : SuperAdministrador.getCategorias()) {
				if (promedio <= cate.promedioPrecio() && cate.disponibilidadCategoria()) {
					this.categoriaCarro = cate;
					for (Vehiculo carro : categoriaCarro.getCarros()) {
						if (carro.disponible()) {
							this.carroAsignado = carro;
							break;
						}
					}
					break;
				}
			}
		}
		if (carroAsignado == null) {
			throw new IllegalArgumentException(
					"No hay carros disponibles para ninguna categoria mejor o igual a la solicitada");
		}
		carroAsignado.setAlquilado(this);
		carroAsignado.agregarHistorial(this);
	}

	/**
	 * Devuelve el vehículo asignado al alquiler a la sede de entrega.
	 * Actualiza el estado del vehículo y del objeto Alquiler.
	 */
	public void devolverCarro() {
		carroAsignado.setSede(lugar[1]);
		carroAsignado.setAlquilado(null);
		this.estado = "Completado";

        // Marcar el vehículo como disponible
        
    	carroAsignado.setDisponible(true);
        
	}

	/**
     * Obtiene el estado actual del alquiler.
     * @return Estado del alquiler.
     */
    public String getEstado() {
        return estado;
    }

	/**
	 * Calcula el precio del alquiler.
	 * El precio se calcula multiplicando el precio base de la categoría del vehículo por el número de días de alquiler,
	 * y sumando el precio de los seguros adicionales.
	 * @return El precio del alquiler.
	 */
	public int calcularPrecio() {
		int precio = carroAsignado.getCategoria().getPrecio();
		int dias = Fecha.diferenciaDias(fechas[0], fechas[1]);
		precio *= dias;
		for (Seguros seguro : seguros) {
			precio += seguro.getPrecio();
		}
		return precio;
	}
}
