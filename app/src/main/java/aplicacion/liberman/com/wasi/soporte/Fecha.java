package aplicacion.liberman.com.wasi.soporte;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Fecha {
    private static final DateFormat HORA = new SimpleDateFormat("HH:mm:ss");
    private static final DateFormat FECHA = new SimpleDateFormat("dd/MM/yyyy");

    /**
     * Definir documentación
     * @return
     */
    public static String horaActual(){
        Date date = new Date();
        return HORA.format(date);
    }

    /**
     * Definir documentación
     * @return
     */
    public static String fechaActual(){
        Date date = new Date();
        return FECHA.format(date);
    }
}
