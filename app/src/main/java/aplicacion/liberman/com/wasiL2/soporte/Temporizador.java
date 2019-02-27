package aplicacion.liberman.com.wasiL2.soporte;

import android.content.Intent;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

import java.util.TimerTask;

import aplicacion.liberman.com.wasiL2.controlador.Perfil;
import aplicacion.liberman.com.wasiL2.controlador.Recogedor;

public class Temporizador extends TimerTask {
    private Recogedor recogedor;

    public Temporizador(Recogedor recogedor) {
        this.recogedor = recogedor;
    }

    @Override
    public void run() {
        Toast.makeText(recogedor.getApplicationContext(), Mensaje.mensajeFirebaseRecogedor, Toast.LENGTH_SHORT).show();
        FirebaseAuth.getInstance().getCurrentUser().delete();
        //FirebaseAuth.getInstance().signOut();

        Intent intent = new Intent(recogedor, Perfil.class);
        recogedor.startActivity(intent);
        recogedor.finish();

    }
}
