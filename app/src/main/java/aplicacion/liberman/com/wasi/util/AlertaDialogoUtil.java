package aplicacion.liberman.com.wasi.util;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;

import aplicacion.liberman.com.wasi.R;
import aplicacion.liberman.com.wasi.controlador.Apoderado;
import aplicacion.liberman.com.wasi.controlador.Movilidad;
import aplicacion.liberman.com.wasi.controlador.Perfil;
import aplicacion.liberman.com.wasi.controlador.PermitirSalida;
import aplicacion.liberman.com.wasi.controlador.Profesor;
import aplicacion.liberman.com.wasi.controlador.SalidaPermitida;
import aplicacion.liberman.com.wasi.soporte.Mensaje;

public class AlertaDialogoUtil {

    /**
     * Método que mostrará un diálogo de alerta donde permitirá
     * escoger al profesor para que pueda quitar los permisos
     * a sus alumnos o no
     * @param identificador
     * @param profesor
     */
    public static void inhabilitarAlumnos(final String identificador, final Profesor profesor){
        AlertDialog.Builder builder = new AlertDialog.Builder(profesor);
        builder.setTitle(Mensaje.tituloQuitarPermisos);
        builder.setMessage(Mensaje.mensajeQuitarPermisos);
        builder.setCancelable(false);
        builder.setIcon(R.drawable.informacion);

        builder.setPositiveButton("Si", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                FirebaseUtilEscritura.quitarPermisosAlumnos(identificador, profesor);
            }
        });

        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                //No se hace nada
                dialogInterface.cancel();
            }
        });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    /**
     * Método que se encargará de verificar si el usuario desea cerrar sessión,
     * de ser ser afirmativo se mostrará la actividad de perfiles
     */
    public static void cerrarSesion(final Apoderado apoderado, final Profesor profesor, final Movilidad movilidad, int valor){
        AlertDialog.Builder builder = null;

        switch (valor){
            case 1 : builder = new AlertDialog.Builder(apoderado);
                     break;
            case 2 : builder = new AlertDialog.Builder(movilidad);
                     break;
            case 3 : break;
            case 4 : builder = new AlertDialog.Builder(profesor);
                     break;
        }

        builder.setTitle(Mensaje.tituloCerrarSesion);
        builder.setMessage(Mensaje.mensajeCerrarSesion);
        builder.setCancelable(false);
        builder.setIcon(R.drawable.informacion);

        builder.setPositiveButton("Si", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                volverPerfiles(apoderado, profesor, movilidad);
            }
        });

        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                //No se hace nada
                dialogInterface.cancel();
            }
        });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    private static void volverPerfiles(Apoderado apoderado, Profesor profesor, Movilidad movilidad){
        Intent intent = null;
        if(apoderado!=null){
            intent = new Intent(apoderado.getApplication(), Perfil.class);
            apoderado.startActivity(intent);
            apoderado.finish();
        }
        if(profesor!=null){
            intent = new Intent(profesor.getApplication(), Perfil.class);
            profesor.startActivity(intent);
            profesor.finish();
        }
        if(movilidad!=null){
            intent = new Intent(movilidad.getApplication(), Perfil.class);
            movilidad.startActivity(intent);
            movilidad.finish();
        }

    }

    public static void autorizarSalidaHijo(final PermitirSalida permitirSalida, final int salida, final String imagen, final String identificador, final String nombresHijo, final String apellidosHijo, final String identificadorHijo){

        AlertDialog.Builder builder = new AlertDialog.Builder(permitirSalida);
        builder.setTitle(Mensaje.tituloPermitirMovilidad);
        builder.setMessage(Mensaje.mensajePermitirMovilidad);
        builder.setCancelable(false);
        builder.setIcon(R.drawable.informacion);

        builder.setPositiveButton("Si", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Intent intent = new Intent(permitirSalida.getApplication(), SalidaPermitida.class);
                intent.putExtra("salida",salida);
                intent.putExtra("imagen",imagen);
                intent.putExtra("identificador", identificador);
                intent.putExtra("nombres", nombresHijo + " " + apellidosHijo);
                intent.putExtra("identificadorHijo", identificadorHijo);
                permitirSalida.startActivity(intent);
                permitirSalida.finish();
            }
        });

        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                //No se hace nada
                dialogInterface.cancel();
            }
        });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

}
