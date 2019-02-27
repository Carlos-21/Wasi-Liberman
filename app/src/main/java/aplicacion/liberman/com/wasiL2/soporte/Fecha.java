package aplicacion.liberman.com.wasiL2.soporte;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class Fecha {
    private static final DateFormat HORA = new SimpleDateFormat("HH:mm:ss");
    private static final DateFormat FECHA = new SimpleDateFormat("dd/MM/yyyy");

    /**
     * Definir documentación
     *
     * @return
     */
    public static String horaActual() {
        Date date = new Date();
        return HORA.format(date);
    }

    /**
     * Definir documentación
     *
     * @return
     */
    public static String fechaActual() {
        Date date = new Date();
        return FECHA.format(date);
    }

    /**
     * Definir documentación
     *
     * @return
     */
    public static boolean rangoFecha() {
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
        System.out.println("Fechas : " + horaInicio + " " + horaFin);
        if (horaActual.compareTo(horaInicio) >= 0 && horaActual.compareTo(horaFin) <= 0) {
            return true;
        }
        return false;
    }
}
