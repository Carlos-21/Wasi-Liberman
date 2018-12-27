package aplicacion.liberman.com.wasiL2.controlador;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import aplicacion.liberman.com.wasiL2.R;

public class Inicio extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent oIntent = new Intent(Inicio.this, LoginFirebase.class);
                startActivity(oIntent);
                finish();
            }
        }, 4000);
    }
}
