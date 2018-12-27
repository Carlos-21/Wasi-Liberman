package aplicacion.liberman.com.wasiL2.controlador;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import aplicacion.liberman.com.wasiL2.R;
import aplicacion.liberman.com.wasiL2.soporte.Mensaje;

public class Perfil extends AppCompatActivity implements View.OnClickListener {
    private ImageView oPerfilApoderado;
    private ImageView oPerfilRecogedor;
    private ImageView oPerfilProfesor;
    private ImageView oPerfilMovilidad;
    private boolean bRecogedor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);

        inicializarPerfil();
        verificarIntencion();
    }

    @Override
    public void onClick(View view) {
        if (bRecogedor) {
            switch (view.getId()) {
                case R.id.oImagenPerfilApoderado:
                    Toast.makeText(Perfil.this, Mensaje.sPerfilRecogedor, Toast.LENGTH_SHORT).show();
                    break;
                case R.id.oImagenPerfilMovilidad:
                    Toast.makeText(Perfil.this, Mensaje.sPerfilRecogedor, Toast.LENGTH_SHORT).show();
                    break;
                case R.id.oImagenPerfilRecogedor:
                    Intent oIntencion = new Intent(Perfil.this, Login.class);
                    oIntencion.putExtra("perfil", 3);
                    startActivity(oIntencion);
                    break;
                case R.id.oImagenPerfilProfesor:
                    Toast.makeText(Perfil.this, Mensaje.sPerfilRecogedor, Toast.LENGTH_SHORT).show();
                    break;
            }
        } else {
            Intent oIntencion = null;
            switch (view.getId()) {
                case R.id.oImagenPerfilApoderado:
                    oIntencion = new Intent(Perfil.this, Login.class);
                    oIntencion.putExtra("perfil", 1);
                    startActivity(oIntencion);
                    break;
                case R.id.oImagenPerfilMovilidad:
                    oIntencion = new Intent(Perfil.this, Login.class);
                    oIntencion.putExtra("perfil", 2);
                    startActivity(oIntencion);
                    break;
                case R.id.oImagenPerfilRecogedor:
                    Toast.makeText(Perfil.this, Mensaje.sNoPerfilRecogedor, Toast.LENGTH_SHORT).show();
                    break;
                case R.id.oImagenPerfilProfesor:
                    oIntencion = new Intent(Perfil.this, Login.class);
                    oIntencion.putExtra("perfil", 4);
                    startActivity(oIntencion);
                    break;
            }

        }

    }

    private void inicializarPerfil() {
        oPerfilApoderado = findViewById(R.id.oImagenPerfilApoderado);
        oPerfilApoderado.setOnClickListener(this);
        oPerfilRecogedor = findViewById(R.id.oImagenPerfilRecogedor);
        oPerfilRecogedor.setOnClickListener(this);
        oPerfilProfesor = findViewById(R.id.oImagenPerfilProfesor);
        oPerfilProfesor.setOnClickListener(this);
        oPerfilMovilidad = findViewById(R.id.oImagenPerfilMovilidad);
        oPerfilMovilidad.setOnClickListener(this);
    }

    private void verificarIntencion() {
        Intent oItencion = getIntent();
        Bundle oBundle = oItencion.getExtras();

        if (oBundle != null) {
            bRecogedor = oBundle.getBoolean("bRecogedor");
        }
    }

}
