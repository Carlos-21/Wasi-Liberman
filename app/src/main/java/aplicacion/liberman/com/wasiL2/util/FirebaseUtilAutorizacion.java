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
        c.set(Calendar.MINUTE, c.get(Calendar.MINUTE) + 1);

        horaDespertar = c.getTime();
        Timer temporizador = new Timer();
        temporizador.schedule(new Temporizador(recogedor), horaDespertar);
    }

}
