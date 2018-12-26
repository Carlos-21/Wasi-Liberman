package aplicacion.liberman.com.wasiL2.soporte;

import android.content.Intent;

import com.google.firebase.auth.FirebaseAuth;

import java.util.TimerTask;

import aplicacion.liberman.com.wasiL2.controlador.LoginFirebase;
import aplicacion.liberman.com.wasiL2.controlador.Recogedor;

public class Temporizador extends TimerTask {
    private Recogedor recogedor;
    public Temporizador(Recogedor recogedor) {
        this.recogedor = recogedor;
    }

    @Override
    public void run() {
        FirebaseAuth.getInstance().signOut();

        Intent intent = new Intent(recogedor, LoginFirebase.class);
        recogedor.startActivity(intent);
        recogedor.finish();

    }
}
