package aplicacion.liberman.com.wasiL2.util;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.Calendar;
import java.util.Date;
import java.util.Timer;
import java.util.concurrent.TimeUnit;

import aplicacion.liberman.com.wasiL2.controlador.ConfirmarRecogedor;
import aplicacion.liberman.com.wasiL2.controlador.LoginFirebase;
import aplicacion.liberman.com.wasiL2.controlador.Perfil;
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
    public static void autentificarPorCorreo(final LoginFirebase oLoginFirebase, EditText oTextoUsuario, EditText oTextoClave) {
        if (!Validar.validarLoginFirebase(oTextoUsuario, oTextoClave)) {
            return;
        }

        FirebaseAuth oAutentificar = FirebaseAuth.getInstance();

        oAutentificar.signInWithEmailAndPassword(oTextoUsuario.getText().toString(), oTextoClave.getText().toString())
                .addOnCompleteListener(oLoginFirebase, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(oLoginFirebase.getApplicationContext(), Mensaje.sAutenticidadCorreo,
                                    Toast.LENGTH_SHORT).show();

                            Intent oIntencion = new Intent(oLoginFirebase.getApplication(), Perfil.class);
                            oLoginFirebase.startActivity(oIntencion);
                            oLoginFirebase.finish();
                        } else {
                            Toast.makeText(oLoginFirebase.getApplicationContext(), Mensaje.sNoAutenticidadCorreo,
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    /**
     * Método encargado de realizar el proceso de autentificación de firebase
     * mediante el código que se le envió el apoderado al recogedor mediante
     * SMS y el token enviado por Whatsapp
     *
     * @param oLoginFirebase
     * @param sToken
     * @param sCodigoSeguridad
     */
    public static void autentificarPorCelular(final LoginFirebase oLoginFirebase, String sToken, String sCodigoSeguridad) {
        FirebaseAuth oAutentificar = FirebaseAuth.getInstance();

        System.out.println("Token asfsaf : " + sToken);
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(sToken, sCodigoSeguridad);

        oAutentificar.signInWithCredential(credential)
                .addOnCompleteListener(oLoginFirebase, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(oLoginFirebase.getApplicationContext(), Mensaje.sAutenticidadTelefono,
                                    Toast.LENGTH_SHORT).show();

                            Intent oIntencion = new Intent(oLoginFirebase.getApplication(), Perfil.class);
                            oIntencion.putExtra("bRecogedor", true);
                            oLoginFirebase.startActivity(oIntencion);
                            oLoginFirebase.finish();
                        } else {
                            Toast.makeText(oLoginFirebase.getApplicationContext(), Mensaje.sNoAutenticidadTelefono,
                                    Toast.LENGTH_SHORT).show();
                        }
                    }

                });
    }

    public static void verificarTelefono(final ConfirmarRecogedor oConfirmarRecogedor, final String sTelefono, final String sUsuario, final String sClave, final String sIdentificador) {
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                "+51" + sTelefono, 60, TimeUnit.SECONDS, oConfirmarRecogedor, new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                    @Override
                    public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {
                        Toast.makeText(oConfirmarRecogedor.getApplicationContext(), Mensaje.sCodigoEnviado, Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onVerificationFailed(FirebaseException e) {
                        Toast.makeText(oConfirmarRecogedor.getApplicationContext(), Mensaje.sCodigoNoEnviado, Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onCodeSent(String idSeguridad, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                        super.onCodeSent(idSeguridad, forceResendingToken);

                        FirebaseUtilEscritura.registrarRecogedor(sUsuario, sClave, sIdentificador);
                        String sMensaje = Mensaje.mensajeTextoRecogeor
                                .replace("paramI", idSeguridad)
                                .replace("paramU", sUsuario)
                                .replace("paramC", sClave);
                        MensajeRecogedor.enviarMensajeWhatsapp(oConfirmarRecogedor, sMensaje, sTelefono);
                    }

                }
        );
    }

    public static void cerrarSesionRecogedor(Recogedor recogedor) {
        Date horaDespertar = new Date(System.currentTimeMillis());

        Calendar c = Calendar.getInstance();
        c.setTime(horaDespertar);
        c.set(Calendar.MINUTE, c.get(Calendar.MINUTE) + 20);

        horaDespertar = c.getTime();
        Timer temporizador = new Timer();
        temporizador.schedule(new Temporizador(recogedor), horaDespertar);
    }

}
