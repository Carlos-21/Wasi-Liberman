package aplicacion.liberman.com.wasi.util;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import aplicacion.liberman.com.wasi.contenedor.Usuario;
import aplicacion.liberman.com.wasi.controlador.ConfirmarRecogedor;
import aplicacion.liberman.com.wasi.controlador.LoginFirebase;
import aplicacion.liberman.com.wasi.controlador.Perfil;
import aplicacion.liberman.com.wasi.soporte.Mensaje;

public class FirebaseUtil {
    private static final FirebaseAuth autentificador = FirebaseAuth.getInstance();

    public static void listarUsuarios(final ArrayList<Usuario> aUsuarios, int iPerfil){
        // Access a Cloud Firestore instance from your Activity

        FirebaseFirestore db = FirebaseFirestore.getInstance();

        db.collection("Usuarios")
                .whereEqualTo("perfil",iPerfil)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (DocumentSnapshot document : task.getResult()) {
                                Usuario auxiliar = document.toObject(Usuario.class);
                                System.out.println("1231 : "+document.getId());
                                auxiliar.setIdentificador(document.getId());
                                aUsuarios.add(auxiliar);
                            }
                        } else {
                        }
                    }
                });
    }

    public static void enviarCredencialesTelefono(final ConfirmarRecogedor controlador, final String numeroTelefono, final String usuario, final String clave){

        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                numeroTelefono, 60, TimeUnit.SECONDS, controlador, new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                    @Override
                    public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {
                        Toast.makeText(controlador.getApplicationContext(), "Correcto", Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onVerificationFailed(FirebaseException e) {
                        Toast.makeText(controlador.getApplicationContext(), "INCorrecto", Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onCodeSent(String idSeguridad, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                        super.onCodeSent(idSeguridad, forceResendingToken);
                        Toast.makeText(controlador.getApplicationContext(), "Ingreso", Toast.LENGTH_LONG).show();
                        String mensaje = Mensaje.mensajeTextoRecogeor.replace("paramI", idSeguridad)
                                                                     .replace("paramU", usuario)
                                                                     .replace("paramC", clave);
                        MensajeTexto.enviarSMS(controlador, numeroTelefono, mensaje);
                    }
                }
        );
    }

    public static void verificarCredencialesTelefono(LoginFirebase controlador, String verificationId, String code) {
        // [START verify_with_code]
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationId, code);
        // [END verify_with_code]
        ingresarPorTelefono(controlador, credential);
    }

    private static void ingresarPorTelefono(final LoginFirebase controlador, PhoneAuthCredential credential) {
        autentificador.signInWithCredential(credential)
                .addOnCompleteListener(controlador, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Intent intent = new Intent(controlador, Perfil.class);
                            controlador.startActivity(intent);

                        } else {

                        }
                    }
                });
    }

}
