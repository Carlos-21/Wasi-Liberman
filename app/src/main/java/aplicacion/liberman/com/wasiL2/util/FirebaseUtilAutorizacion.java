package aplicacion.liberman.com.wasiL2.util;

import java.util.Calendar;
import java.util.Date;
import java.util.Timer;

import aplicacion.liberman.com.wasiL2.controlador.Recogedor;
import aplicacion.liberman.com.wasiL2.soporte.Temporizador;

public class FirebaseUtilAutorizacion {

    public static void cerrarSesionRecogedor(Recogedor recogedor){
        Date horaDespertar = new Date(System.currentTimeMillis());

        Calendar c = Calendar.getInstance();
        c.setTime(horaDespertar);
        c.set(Calendar.MINUTE, c.get(Calendar.MINUTE)+20);

        horaDespertar = c.getTime();
        Timer temporizador = new Timer();
        temporizador.schedule(new Temporizador(recogedor), horaDespertar);
    }

}
