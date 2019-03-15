package aplicacion.liberman.com.wasiL2.controlador;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import aplicacion.liberman.com.wasiL2.R;
import aplicacion.liberman.com.wasiL2.util.AlertaDialogoUtil;
import aplicacion.liberman.com.wasiL2.util.FirebaseUtilConsulta;

public class Movilidad extends AppCompatActivity implements View.OnClickListener {
    private ImageView alumnosHabilitados;
    private ImageView alumnosNoHabilitados;
    private ImageView alumnosEntregados;
    private ImageView configuracionUsuario;
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
        configuracionUsuario = findViewById(R.id.configuracionUsuario);
        configuracionUsuario.setOnClickListener(this);
        cerrarSesion = findViewById(R.id.cerrarSesion);
        cerrarSesion.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.alumnosHabilitados:
                FirebaseUtilConsulta.verificarAlumnosMovilidad(Movilidad.this, identificador, true, false, true);
                break;
            case R.id.alumnosNoHabilitados:
                FirebaseUtilConsulta.verificarAlumnosMovilidad(Movilidad.this, identificador, false, false, false);
                break;
            case R.id.alumnosEntregados:
                FirebaseUtilConsulta.verificarAlumnosMovilidad(Movilidad.this, identificador, true, true, false);
                break;
            case R.id.configuracionUsuario:
                Intent intent = new Intent(Movilidad.this, ConfiguracionUsuario.class);
                intent.putExtra("identificador", identificador);
                intent.putExtra("perfil", 2);
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
