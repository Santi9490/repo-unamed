package reserva_alquiler;

import java.util.Date;

import inventario.Categorias;
import procesamiento.Fecha;
import sedes.Sede;

public class Reserva extends Alquiler {

	private Date horaLLegada;

	/**
	 * contructor de la clase reserva, reutiliza el constructor de la clase alquiler
	 * 
	 * @param fechaInicio    String con la fecha de inicio de la reserva
	 * @param fechaFinal     String con la fecha de final de la reserva
	 * @param inicioHora     String con la hora de inicio de la reserva
	 * @param finHora        String con la hora fin de la reserva
	 * @param lugarRecogida  sede lugar de la recogida del coche
	 * @param lugarDejada    sede lugar de la dejada del coche
	 * @param cliente        cliente instancia de cliente quien hace la reserva
	 * @param categoriaCarro categorias la categoria del coche
	 * @param horaLLegada    string con la hora de llegada real del cliente
	 * @throws IllegalArgumentException La hora de llegada no esta dentro del
	 *                                  horario de atencion de la sede
	 */
	public Reserva(String fechaInicio, String fechaFinal, String inicioHora, String finHora, Sede lugarRecogida,
			Sede lugarDejada, Cliente cliente, Categorias categoriaCarro, String horaLLegada) {
		super(fechaInicio, fechaFinal, inicioHora, finHora, lugarRecogida, lugarDejada, cliente, categoriaCarro);
		this.horaLLegada = Fecha.convertirHora(horaLLegada);
		if (!Fecha.isIn(lugarRecogida.getHorasAtencion(), this.horaLLegada)) {
			throw new IllegalArgumentException("La hora de llegada no esta dentro del horario de atencion de la sede");
		}
	}

	@Override
	public String getInfoReserva() {
		String texto = super.getInfoReserva();
		texto += ";" + horaLLegada + "\r";
		return texto;
	}

	@Override
	public String getInfo() {
		String texto = String.format("    Cliente:%n%s%n    Categoria: %s%n    Para el %tD al %tD%n    En: %n%s.",
				getCliente().getInfo(), getCategoriaCarro().getNombre(), getFechas()[0], getFechas()[1],
				getLugarDejada().getInfoSede());
		return texto;
	}

}
