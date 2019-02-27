package aplicacion.liberman.com.wasiL2.controlador;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import aplicacion.liberman.com.wasiL2.R;
import aplicacion.liberman.com.wasiL2.servicio.ServicioFirebase;
import aplicacion.liberman.com.wasiL2.util.AlertaDialogoUtil;

public class Apoderado extends AppCompatActivity implements View.OnClickListener {
    private ImageView verHijos;
    private ImageView permitirSalida;
    private ImageView asignarRecogedor;
    private ImageView registroSalidas;
    private ImageView cerrarSesion;
    private String identificador;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apoderado);

        verificarIntencion();
        inicializarApoderado();
    }

    @Override
    public void onClick(View view) {
        Intent intent = null;
        switch (view.getId()) {
            case R.id.verHijos:
                intent = new Intent(Apoderado.this, VerHijoApoderado.class);
                intent.putExtra("bandera", false);
                intent.putExtra("identificador", identificador);
                startActivity(intent);
                break;
            case R.id.permitirSalida:
                intent = new Intent(Apoderado.this, VerHijoApoderado.class);
                intent.putExtra("bandera", true);
                intent.putExtra("identificador", identificador);
                startActivity(intent);
                break;
            case R.id.asignarRecogedor:
                intent = new Intent(Apoderado.this, ConfirmarRecogedor.class);
                intent.putExtra("identificador", identificador);
                startActivity(intent);
                break;
            case R.id.registroSalidas:
                intent = new Intent(Apoderado.this, VerRegistroSalida.class);
                intent.putExtra("identificador", identificador);
                startActivity(intent);
                break;
            case R.id.cerrarSesion:
                AlertaDialogoUtil.cerrarSesion(Apoderado.this);
                break;
        }
    }

    @Override
    public void onBackPressed() {
        AlertaDialogoUtil.cerrarSesion(Apoderado.this);
    }

    /**
     * Método encargado de inicializar los componentes
     * necesarios de la vista activity_apoderado
     */
    private void inicializarApoderado() {
        verHijos = findViewById(R.id.verHijos);
        verHijos.setOnClickListener(this);
        permitirSalida = findViewById(R.id.permitirSalida);
        permitirSalida.setOnClickListener(this);
        asignarRecogedor = findViewById(R.id.asignarRecogedor);
        asignarRecogedor.setOnClickListener(this);
        registroSalidas = findViewById(R.id.registroSalidas);
        registroSalidas.setOnClickListener(this);
        cerrarSesion = findViewById(R.id.cerrarSesion);
        cerrarSesion.setOnClickListener(this);

        ServicioFirebase.sIdentificadorApoderado = identificador;

        Intent intent = new Intent(Apoderado.this, ServicioFirebase.class);
        startService(intent);
    }

    /**
     * Método encargado de verificar si en una anterior vista se pasó
     * un dato con la llave identificador como parámetro
     */
    private void verificarIntencion() {
        Intent inten = getIntent();
        Bundle bun = inten.getExtras();

        if (bun != null) {
            identificador = bun.getString("identificador");
        }
    }
}
