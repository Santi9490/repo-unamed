package procesamiento;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Clase que contiene métodos para el manejo de fechas y horas.
 */
public class Fecha {

    private static Date fechaActual = new Date();

    /**
     * Devuelve la fecha actual en formato de texto.
     * 
     * @return la fecha actual en formato de texto.
     */
    public static String getStringFechaActual() {
        String texto = String.format("%tD", fechaActual);
        return texto;
    }

    public static Date getFechaActual() {
        return fechaActual;
    }

    /**
     * Cambia la fecha actual a la fecha especificada como una cadena de texto en
     * formato "MM/dd/yy".
     *
     * @param nuevaFecha la nueva fecha como una cadena de texto en formato
     *                   "MM/dd/yy".
     */
    public static void cambiarFechaActual(String nuevaFecha) {
        fechaActual = convertirFecha(nuevaFecha);
    }

    /**
     * Convierte una cadena de texto en formato "MM/dd/yy" a un objeto Date.
     *
     * @param fechaStr la fecha como una cadena de texto en formato "MM/dd/yy".
     * @return la fecha como un objeto Date.
     */
    public static Date convertirFecha(String fechaStr) {
        SimpleDateFormat formato = new SimpleDateFormat("MM/dd/yy");

        try {
            return formato.parse(fechaStr);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Convierte una cadena de texto en formato "HH:mm" a un objeto Date.
     *
     * @param horaStr la hora como una cadena de texto en formato "HH:mm".
     * @return la hora como un objeto Date.
     */
    public static Date convertirHora(String horaStr) {
        SimpleDateFormat formato = new SimpleDateFormat("HH:mm");
        try {
            return formato.parse(horaStr);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Obtiene la primera fecha de un arreglo de fechas como una cadena de texto en
     * formato "MM/dd/yy".
     *
     * @param fechas el arreglo de fechas.
     * @return la primera fecha como una cadena de texto en formato "MM/dd/yy".
     */
    public static String ontener_primera_fecha(Date[] fechas) {
        SimpleDateFormat formato = new SimpleDateFormat("MM/dd/yy");

        String fechaComoString = formato.format(fechas[0]);

        return fechaComoString;
    }

    /**
     * Obtiene la segunda fecha de un arreglo de fechas como una cadena de texto en
     * formato "MM/dd/yy".
     *
     * @param fechas el arreglo de fechas.
     * @return la segunda fecha como una cadena de texto en formato "MM/dd/yy".
     */
    public static String ontener_segunda_fecha(Date[] fechas) {
        SimpleDateFormat formato = new SimpleDateFormat("MM/dd/yy");
        String fechaComoString = formato.format(fechas[1]);
        return fechaComoString;
    }

    /**
     * Verifica si una fecha se encuentra dentro de un rango de fechas.
     *
     * @param fechas el rango de fechas como un arreglo de dos elementos.
     * @param fecha  la fecha a verificar.
     * @return true si la fecha se encuentra dentro del rango de fechas, false en
     *         caso contrario.
     */
    public static boolean isIn(Date[] fechas, Date fecha) {
        if (fechas[0].before(fecha) && fechas[1].after(fecha)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Calcula la diferencia en días entre dos fechas.
     *
     * @param fecha1 la primera fecha.
     * @param fecha2 la segunda fecha.
     * @return la diferencia en días entre las dos fechas.
     */
    public static int diferenciaDias(Date fecha1, Date fecha2) {
        long diferencia = fecha2.getTime() - fecha1.getTime();
        int dias = (int) Math.floor(diferencia / (1000 * 60 * 60 * 24));
        return dias;
    }
}
