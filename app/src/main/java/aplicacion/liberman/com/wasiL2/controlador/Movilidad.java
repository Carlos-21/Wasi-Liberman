package aplicacion.liberman.com.wasiL2.controlador;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import aplicacion.liberman.com.wasiL2.R;
import aplicacion.liberman.com.wasiL2.util.AlertaDialogoUtil;

public class Movilidad extends AppCompatActivity implements View.OnClickListener {
    private ImageView alumnosHabilitados;
    private ImageView alumnosNoHabilitados;
    private ImageView alumnosEntregados;
    private ImageView cerrarSesion;
    private String identificador;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movilidad);

        verificarIntencion();

        inicializarMovilidad();
    }

    /**
     * Método encargado de inicializar los componentes
     * necesarios de la vista activity_movilidad
     */
    private void inicializarMovilidad() {
        alumnosHabilitados = findViewById(R.id.alumnosHabilitados);
        alumnosHabilitados.setOnClickListener(this);
        alumnosNoHabilitados = findViewById(R.id.alumnosNoHabilitados);
        alumnosNoHabilitados.setOnClickListener(this);
        alumnosEntregados = findViewById(R.id.alumnosEntregados);
        alumnosEntregados.setOnClickListener(this);
        cerrarSesion = findViewById(R.id.cerrarSesion);
        cerrarSesion.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        Intent intent = null;
        switch (view.getId()) {
            case R.id.alumnosHabilitados:
                intent = new Intent(Movilidad.this, VerHijoMovilidad.class);
                intent.putExtra("titulo", getApplicationContext().getResources().getString(R.string.cAlumnosHabilitados));
                intent.putExtra("identificador", identificador);
                intent.putExtra("estado", true);
                intent.putExtra("casa", false);
                intent.putExtra("bandera", true);
                startActivity(intent);
                break;
            case R.id.alumnosNoHabilitados:
                intent = new Intent(Movilidad.this, VerHijoMovilidad.class);
                intent.putExtra("titulo", getApplicationContext().getResources().getString(R.string.cAlumnosNoHabilitados));
                intent.putExtra("identificador", identificador);
                intent.putExtra("estado", false);
                intent.putExtra("casa", false);
                intent.putExtra("bandera", false);
                startActivity(intent);
                break;
            case R.id.alumnosEntregados:
                intent = new Intent(Movilidad.this, VerHijoMovilidad.class);
                intent.putExtra("titulo", getApplicationContext().getResources().getString(R.string.cAlumnosEntregados));
                intent.putExtra("identificador", identificador);
                intent.putExtra("estado", true);
                intent.putExtra("casa", true);
                intent.putExtra("bandera", false);
                startActivity(intent);
                break;
            case R.id.cerrarSesion:
                AlertaDialogoUtil.cerrarSesion(Movilidad.this);
                break;
        }
    }

    @Override
    public void onBackPressed() {
        AlertaDialogoUtil.cerrarSesion(Movilidad.this);
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
