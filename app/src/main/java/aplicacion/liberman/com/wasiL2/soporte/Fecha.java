package aplicacion.liberman.com.wasiL2.soporte;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;

public class Fecha {
    private static final String LUGAR = "America/Lima";
    private static final DateFormat HORA = new SimpleDateFormat("HH:mm:ss");
    private static final DateFormat FECHA = new SimpleDateFormat("dd/MM/yyyy");

    /**
     * Método encargado de devolver la hora Actual de la ciudad de Lima
     *
     * @return
     */
    public static String horaActual() {
        TimeZone zone = TimeZone.getTimeZone(LUGAR);
        Calendar calendar = Calendar.getInstance(zone);
        return HORA.format(calendar.getTime());
    }

    /**
     * Método encargado de devolver la fecha Actual de la ciudad de Lima
     *
     * @return
     */
    public static String fechaActual() {
        TimeZone zone = TimeZone.getTimeZone(LUGAR);
        Calendar calendar = Calendar.getInstance(zone);
        return FECHA.format(calendar.getTime());
    }

    /**
     * Método encargado de poder verificar la hora actual, para así con
     * el resultado del método determinar si se puede ejecutar una función
     * o no
     *
     * @return
     */
    public static boolean rangoHora() {
        GregorianCalendar cal = new GregorianCalendar();

        Date horaActual = cal.getTime();

        cal.set(Calendar.AM_PM, Calendar.AM);

        cal.set(GregorianCalendar.HOUR, 8);
        cal.set(GregorianCalendar.MINUTE, 0);
        cal.set(GregorianCalendar.SECOND, 0);
        Date horaInicio = cal.getTime();

        cal.set(GregorianCalendar.HOUR, 9);
        cal.set(GregorianCalendar.MINUTE, 0);
        cal.set(GregorianCalendar.SECOND, 0);
        Date horaFin = cal.getTime();

        if (horaActual.compareTo(horaInicio) >= 0 && horaActual.compareTo(horaFin) <= 0) {
            return true;
        }
        return false;
    }
}
