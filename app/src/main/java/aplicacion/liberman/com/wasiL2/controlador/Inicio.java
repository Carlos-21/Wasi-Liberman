package aplicacion.liberman.com.wasiL2.controlador;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import aplicacion.liberman.com.wasiL2.R;
import aplicacion.liberman.com.wasiL2.util.FirebaseUtilConsulta;

public class Inicio extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                FirebaseAuth oAutentificar = FirebaseAuth.getInstance();

                FirebaseUser oUsuarioFirebase = oAutentificar.getCurrentUser();
                if (oUsuarioFirebase != null) {
                    FirebaseUtilConsulta.verificarUsuarioLogin(Inicio.this, oUsuarioFirebase.getEmail());
                } else {
                    Intent oIntent = new Intent(Inicio.this, Perfil.class);
                    startActivity(oIntent);
                    finish();
                }
            }
        }, 4000);
    }
}
