package aplicacion.liberman.com.wasi.soporte;

import android.content.Intent;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

import java.util.TimerTask;

import aplicacion.liberman.com.wasi.controlador.Login;
import aplicacion.liberman.com.wasi.controlador.LoginFirebase;
import aplicacion.liberman.com.wasi.controlador.Recogedor;

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
