package aplicacion.liberman.com.wasiL2.soporte;

import android.content.Intent;
import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.TimerTask;

import aplicacion.liberman.com.wasiL2.controlador.Perfil;
import aplicacion.liberman.com.wasiL2.controlador.Recogedor;
import aplicacion.liberman.com.wasiL2.util.SharedPreferencesUtil;

public class Temporizador extends TimerTask {
    private Recogedor recogedor;

    public Temporizador(Recogedor recogedor) {
        this.recogedor = recogedor;
    }

    @Override
    public void run() {
        final FirebaseUser usuario = FirebaseAuth.getInstance().getCurrentUser();
        FirebaseAuth.getInstance().signOut();

        AuthCredential credential = EmailAuthProvider
                .getCredential(SharedPreferencesUtil.recuperarCorreo(recogedor), SharedPreferencesUtil.recuperarClave(recogedor));

        usuario.reauthenticate(credential).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                usuario.delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Intent intent = new Intent(recogedor.getApplicationContext(), Perfil.class);
                            intent.putExtra("finalizar", true);
                            recogedor.startActivity(intent);
                            recogedor.finish();
                        }
                    }
                });
            }
        });
    }
}
