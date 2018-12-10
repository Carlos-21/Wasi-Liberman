package aplicacion.liberman.com.wasi.controlador;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import aplicacion.liberman.com.wasi.R;

public class Inicio extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(Inicio.this, LoginFirebase.class);
                startActivity(intent);
                finish();
            }
        }, 4000);
    }
}
