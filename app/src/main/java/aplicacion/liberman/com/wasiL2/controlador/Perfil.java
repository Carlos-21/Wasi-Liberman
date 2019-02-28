package aplicacion.liberman.com.wasiL2.controlador;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import aplicacion.liberman.com.wasiL2.R;
import aplicacion.liberman.com.wasiL2.soporte.Mensaje;
import aplicacion.liberman.com.wasiL2.util.AlertaDialogoUtil;

public class Perfil extends AppCompatActivity implements View.OnClickListener {
    private ImageView oPerfilApoderado;
    private ImageView oPerfilRecogedor;
    private ImageView oPerfilProfesor;
    private ImageView oPerfilMovilidad;
    private boolean bandera;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);

        inicializarPerfil();

        verificarIntencion();

        AlertaDialogoUtil.autorizarPermisos(Perfil.this);

    }

    @Override
    public void onClick(View view) {
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
                oIntencion = new Intent(Perfil.this, Login.class);
                oIntencion.putExtra("perfil", 3);
                startActivity(oIntencion);
                break;
            case R.id.oImagenPerfilProfesor:
                oIntencion = new Intent(Perfil.this, Login.class);
                oIntencion.putExtra("perfil", 4);
                startActivity(oIntencion);
                break;
        }

    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onBackPressed() {
        Intent oItencion = new Intent(Perfil.this, Inicio.class);
        startActivity(oItencion);
        finish();
    }

    /**
     * Método encargado de inicializar los componentes
     * necesarios de la vista activity_perfil
     */
    private void inicializarPerfil() {
        oPerfilApoderado = findViewById(R.id.oImagenPerfilApoderado);
        oPerfilApoderado.setOnClickListener(this);
        oPerfilRecogedor = findViewById(R.id.oImagenPerfilRecogedor);
        oPerfilRecogedor.setOnClickListener(this);
        oPerfilProfesor = findViewById(R.id.oImagenPerfilProfesor);
        oPerfilProfesor.setOnClickListener(this);
        oPerfilMovilidad = findViewById(R.id.oImagenPerfilMovilidad);
        oPerfilMovilidad.setOnClickListener(this);

        bandera = false;
    }

    /**
     * Método encargado de verificar si en una anterior vista se pasó
     * un dato con la llave finalizar como parámetro
     */
    private void verificarIntencion() {
        Intent inten = getIntent();
        Bundle bun = inten.getExtras();

        if (bun != null) {
            bandera = bun.getBoolean("finalizar");

            if (bandera) {
                Toast.makeText(Perfil.this, Mensaje.mensajeFirebaseRecogedor, Toast.LENGTH_SHORT).show();
            }

        }
    }

}
