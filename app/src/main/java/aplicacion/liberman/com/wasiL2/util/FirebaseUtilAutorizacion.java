package aplicacion.liberman.com.wasiL2.util;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Calendar;
import java.util.Date;
import java.util.Timer;

import aplicacion.liberman.com.wasiL2.contenedor.Usuario;
import aplicacion.liberman.com.wasiL2.controlador.Login;
import aplicacion.liberman.com.wasiL2.controlador.Recogedor;
import aplicacion.liberman.com.wasiL2.soporte.Mensaje;
import aplicacion.liberman.com.wasiL2.soporte.Temporizador;
import aplicacion.liberman.com.wasiL2.soporte.Validar;

public class FirebaseUtilAutorizacion {

    /**
     * Mètodo encargado de realizar el proceso de autentificación
     * de Firebase mediante el correo y contraseña que definió
     * un administrador
     *
     * @param oLoginFirebase
     * @param oTextoUsuario
     * @param oTextoClave
     */
    public static void autentificarPorCorreo(final Login oLoginFirebase, final EditText oTextoUsuario, final EditText oTextoClave, final int iPerfil) {
        if (!Validar.validarLoginFirebase(oTextoUsuario, oTextoClave)) {
            return;
        }

        FirebaseAuth oAutentificar = FirebaseAuth.getInstance();

        oAutentificar.signInWithEmailAndPassword(oTextoUsuario.getText().toString(), oTextoClave.getText().toString())
                .addOnCompleteListener(oLoginFirebase, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Usuario oUsuarioWasi = new Usuario();
                            oUsuarioWasi.setCorreo(oTextoUsuario.getText().toString());
                            oUsuarioWasi.setPerfil(iPerfil);

                            FirebaseUtilConsulta.verificarUsuario(oLoginFirebase, oTextoUsuario.getText().toString(), oUsuarioWasi);
                        } else {
                            Toast.makeText(oLoginFirebase.getApplicationContext(), Mensaje.sNoAutenticidadCorreo,
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    /**
     * Método encargado de registrar las credenciales necesarias para que un recogedor
     * pueda ingresar al sistema con dicho perfil y pueda autorizar que los hijos
     * del apoderado correspondiente puedan salir de la institución educativa
     *
     * @param context
     * @param correo
     * @param clave
     * @param sIdentificador
     */
    public static void registrarRecogedorAutorizacion(final Context context, String correo, String clave, String sIdentificador) {
        FirebaseUtilEscritura.registrarRecogedor(correo, sIdentificador);

        final FirebaseAuth oAutentificar = FirebaseAuth.getInstance();

        oAutentificar.createUserWithEmailAndPassword(correo, clave).addOnCompleteListener((Activity) context, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    String correo = SharedPreferencesUtil.recuperarCorreo(context);
                    String clave = SharedPreferencesUtil.recuperarClave(context);
                    Toast.makeText(context.getApplicationContext(), Mensaje.sMensajeRecogedorAsignado,
                            Toast.LENGTH_SHORT).show();
                    oAutentificar.signInWithEmailAndPassword(correo, clave)
                            .addOnCompleteListener(((Activity) context), new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {

                                    }
                                }
                            });
                }
            }

        });
    }

    /**
     * Método encargado de cerrar la sesión de un usuario con el
     * perfil recogedor esto se realiza por motivos de seguridad,
     * el cierre automático se realiza 20 minutos después de haber iniciado sesión
     *
     * @param recogedor
     */
    public static void cerrarSesionRecogedor(Recogedor recogedor) {
        Date horaDespertar = new Date(System.currentTimeMillis());

        Calendar c = Calendar.getInstance();
        c.setTime(horaDespertar);
        c.set(Calendar.MINUTE, c.get(Calendar.MINUTE) + 5);

        horaDespertar = c.getTime();
        Timer temporizador = new Timer();
        temporizador.schedule(new Temporizador(recogedor), horaDespertar);
    }

    /**
     * Método encargado de modificar el nombre del usuario en la seguridad de Firebase
     *
     * @param context
     * @param sUsuario
     * @param iPerfil
     */
    public static void cambiarUsuarioFirebase(final Context context, final String sUsuario, final int iPerfil) {
        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        final String sUsuarioActual = user.getEmail();

        user.updateEmail(sUsuario)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            SharedPreferencesUtil.guardarUsuario(context, sUsuario, SharedPreferencesUtil.recuperarClave(context));

                            FirebaseUtilConsulta.cambiarUsuarioFirebase(context, sUsuarioActual, iPerfil, sUsuario);
                        }
                    }
                });
    }

    /**
     * Método encargado de cambiar la clave del usuario esto solo pueden hacerlos los
     * usuarios con los perfiles apoderado, profesor y movilidad
     *
     * @param context
     * @param sClave
     */
    public static void cambiarClaveFirebase(final Context context, final String sClave) {
        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        user.updatePassword(sClave)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            SharedPreferencesUtil.guardarUsuario(context, user.getEmail(), sClave);
                            Toast.makeText(context.getApplicationContext(), Mensaje.mensajeCambioClave,
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

}
