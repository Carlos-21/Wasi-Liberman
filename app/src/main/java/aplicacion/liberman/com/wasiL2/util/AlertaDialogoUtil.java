package aplicacion.liberman.com.wasiL2.util;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

import aplicacion.liberman.com.wasiL2.R;
import aplicacion.liberman.com.wasiL2.controlador.ConfirmarRecogedor;
import aplicacion.liberman.com.wasiL2.controlador.Perfil;
import aplicacion.liberman.com.wasiL2.controlador.PermitirSalida;
import aplicacion.liberman.com.wasiL2.controlador.Profesor;
import aplicacion.liberman.com.wasiL2.controlador.SalidaPermitida;
import aplicacion.liberman.com.wasiL2.servicio.ServicioFirebase;
import aplicacion.liberman.com.wasiL2.soporte.Mensaje;
import aplicacion.liberman.com.wasiL2.soporte.Validar;

public class AlertaDialogoUtil {

    /**
     * Método encargado de mostrar un mensaje para poder asegurarnos de que el
     * usuario con el perfil apoderado desea asignar un recogedor
     *
     * @param oConfirmarRecogedor
     * @param sTelefono
     * @param sUsuario
     * @param sClave
     * @param sIdentificador
     */
    public static void dialogoAlertaConfirmarRecogedor(final ConfirmarRecogedor oConfirmarRecogedor, final String sTelefono, final String sUsuario, final String sClave, final String sIdentificador) {
        AlertDialog.Builder oConstructor = new AlertDialog.Builder(oConfirmarRecogedor);
        oConstructor.setTitle(Mensaje.sTituloAsignarRecogedor);
        oConstructor.setMessage(Mensaje.sMensajeAsignarRecogedor);
        oConstructor.setCancelable(true);
        oConstructor.setIcon(R.drawable.informacion);

        oConstructor.setPositiveButton("Si", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                FirebaseUtilConsulta.verificarUsuarioRecogedor(oConfirmarRecogedor, sUsuario, sClave, sTelefono, sIdentificador);
            }
        });

        oConstructor.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });

        AlertDialog oDialogoAlerta = oConstructor.create();
        oDialogoAlerta.show();
    }

    /**
     * Método que mostrará un diálogo de alerta donde permitirá
     * escoger al profesor para que pueda quitar los permisos
     * a sus alumnos o no
     *
     * @param identificador
     * @param profesor
     */
    public static void inhabilitarAlumnos(final String identificador, final Profesor profesor) {
        AlertDialog.Builder builder = new AlertDialog.Builder(profesor);
        builder.setTitle(Mensaje.tituloQuitarPermisos);
        builder.setMessage(Mensaje.mensajeQuitarPermisos);
        builder.setCancelable(false);
        builder.setIcon(R.drawable.informacion);

        builder.setPositiveButton("Si", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                FirebaseUtilEscritura.quitarPermisosAlumnos(identificador, profesor, true);
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
    public static void cerrarSesion(final Context context) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);

        builder.setTitle(Mensaje.tituloCerrarSesion);
        builder.setMessage(Mensaje.mensajeCerrarSesion);
        builder.setCancelable(false);
        builder.setIcon(R.drawable.informacion);

        builder.setPositiveButton("Si", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                SharedPreferencesUtil.guardarBandera(context.getApplicationContext(), true);

                context.stopService(new Intent(context.getApplicationContext(), ServicioFirebase.class));

                FirebaseAuth.getInstance().signOut();

                Intent intent = new Intent(context.getApplicationContext(), Perfil.class);
                context.startActivity(intent);
                ((Activity) context).finish();
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
     * Método encargado de autorizar la salida del hijo que se ha seleccionado, el mensaje
     * cambiará dependiendo del parámetro salidad ya que define el tipo de salida que puede
     * ser para movilidad como que el apoderado se haga presente
     *
     * @param permitirSalida
     * @param salida
     * @param imagen
     * @param identificador
     * @param nombresHijo
     * @param apellidosHijo
     * @param identificadorHijo
     * @param identificadorRecogedorApoderado
     * @param tipoPerfil
     */
    public static void autorizarSalidaHijo(final PermitirSalida permitirSalida, final int salida, final String imagen, final String identificador, final String nombresHijo,
                                           final String apellidosHijo, final String identificadorHijo, final String identificadorRecogedorApoderado, final int tipoPerfil) {

        AlertDialog.Builder builder = new AlertDialog.Builder(permitirSalida);

        if (salida == 1) {
            Mensaje.nombre = apellidosHijo + " " + nombresHijo;
            builder.setTitle(Mensaje.tituloPermitirSalida);
            builder.setMessage(Mensaje.mensajePermitirSalida.replace("paramN", Mensaje.nombre));
        } else {
            builder.setTitle(Mensaje.tituloPermitirMovilidad);
            builder.setMessage(Mensaje.mensajePermitirMovilidad);
        }
        builder.setCancelable(false);
        builder.setIcon(R.drawable.informacion);

        builder.setPositiveButton("Si", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Intent intent = new Intent(permitirSalida.getApplication(), SalidaPermitida.class);
                intent.putExtra("salida", salida);
                intent.putExtra("imagen", imagen);
                intent.putExtra("identificador", identificador);
                intent.putExtra("nombres", nombresHijo + " " + apellidosHijo);
                intent.putExtra("identificadorHijo", identificadorHijo);
                intent.putExtra("tipoPerfil", tipoPerfil);
                if (identificadorRecogedorApoderado != null) {
                    intent.putExtra("apoderado", identificadorRecogedorApoderado);
                }
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


    /**
     * Método encargado de verificar que la aplicación tenga los
     * permisos correspodientes para poder funcionar de manera
     * correcta
     *
     * @param context
     */
    public static void autorizarPermisos(final Activity context) {
        if (!PermisosUtil.verificarPermisosSistema(context).isEmpty()) {
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setCancelable(true);
            builder.setTitle(Mensaje.tituloPermisosSistema);
            builder.setMessage(Mensaje.mensajePermisosSistema);
            builder.setIcon(R.drawable.informacion);

            builder.setPositiveButton("Si", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    PermisosUtil.adquirirPermisosSistema(context);
                }
            });

            builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    //No se hace nada
                    dialogInterface.cancel();
                }
            });

            AlertDialog alert = builder.create();
            alert.show();
        }
    }

    /**
     * Método encargado de mostrar un mensaje para que el usuario con
     * el perfil de movilidad para decidir si el hijo ya esta en su casa
     * y con esto realizar un proceso de cambio de estado en los datos
     * del hijo
     *
     * @param context
     * @param apellidosHijo
     * @param nombresHijo
     * @param identificadorHijo
     */
    public static void autorizarEntregaHijo(final Context context, String apellidosHijo, String nombresHijo, final String identificadorHijo) {
        Mensaje.nombre = apellidosHijo + " " + nombresHijo;
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(Mensaje.tituloPermitirEntrega);
        builder.setMessage(Mensaje.mensajePermitirEntrega.replace("paramH", Mensaje.nombre));
        builder.setCancelable(false);
        builder.setIcon(R.drawable.informacion);

        builder.setPositiveButton("Si", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                String mensaje = Mensaje.mensajeEntregaHecha.replace("paramH", Mensaje.nombre);
                FirebaseUtilEscritura.entregaHijoMovilidad(identificadorHijo);
                Toast.makeText(context.getApplicationContext(), mensaje, Toast.LENGTH_SHORT).show();
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
     * Método encargado de mostrar un mensaje para que el usuario
     * pueda decidir si desea salir de la aplicación o no
     *
     * @param context
     */
    public static void cerrarAplicacion(final Context context) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(Mensaje.tituloCerrarApp);
        builder.setMessage(Mensaje.mensajeCerrarApp);
        builder.setCancelable(false);
        builder.setIcon(R.drawable.informacion);

        builder.setPositiveButton("Si", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Intent intent = new Intent(Intent.ACTION_MAIN);
                intent.addCategory(Intent.CATEGORY_HOME);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
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
     * Método encargado de mostrar une mensaje que mostrará el actual nombre del usuario
     * y una caja de texto donde podrá ingresar su nuevo nombre de usuario
     *
     * @param context
     * @param iPerfil
     */
    public static void cambiarUsuario(final Context context, final int iPerfil) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        View oVista = ((Activity) context).getLayoutInflater().inflate(R.layout.dialogo_configuracion_usuario, null);

        final TextView oUsuarioActual = oVista.findViewById(R.id.textoUsuarioActual);
        final EditText oUsuarioNuevo = oVista.findViewById(R.id.textoNuevoUsuario);
        oUsuarioActual.setText("Usuario actual : " + SharedPreferencesUtil.recuperarCorreo(context));

        builder.setCancelable(false);

        builder.setView(oVista);

        builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (Validar.validarCambioUsuario(oUsuarioNuevo)) {
                    FirebaseUtilAutorizacion.cambiarUsuarioFirebase(context, oUsuarioNuevo.getText().toString(), iPerfil);
                    FirebaseUtilEscritura.actualizarAlumnoUsuario(context, SharedPreferencesUtil.recuperarCorreo(context), oUsuarioNuevo.getText().toString(), iPerfil);
                }
            }
        });

        builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
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
     * Método encargado de mostrar une mensaje que mostrará la actual contraseña del usuario
     * y una caja de texto donde podrá ingresar su nueva contraseña
     *
     * @param context
     */
    public static void cambiarClave(final Context context) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        View oVista = ((Activity) context).getLayoutInflater().inflate(R.layout.dialogo_configuracion_clave, null);

        final TextView oClaveActual = oVista.findViewById(R.id.textoContraseñaActual);
        final EditText oClaveNueva = oVista.findViewById(R.id.textoNuevaContraseña);
        oClaveActual.setText("Contraseña actual : " + SharedPreferencesUtil.recuperarClave(context));

        builder.setCancelable(false);

        builder.setView(oVista);

        builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (Validar.validarCambioContraseña(oClaveNueva)) {
                    FirebaseUtilAutorizacion.cambiarClaveFirebase(context, oClaveNueva.getText().toString());
                }
            }
        });

        builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
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
