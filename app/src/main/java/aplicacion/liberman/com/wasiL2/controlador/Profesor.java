package aplicacion.liberman.com.wasiL2.controlador;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import aplicacion.liberman.com.wasiL2.R;
import aplicacion.liberman.com.wasiL2.soporte.Fecha;
import aplicacion.liberman.com.wasiL2.soporte.Mensaje;
import aplicacion.liberman.com.wasiL2.util.AlertaDialogoUtil;

public class Profesor extends AppCompatActivity implements View.OnClickListener {
    private ImageView alumnosHabilitados;
    private ImageView alumnosNoHabilitados;
    private ImageView inhabilitarAlumnos;
    private ImageView cerrarSesion;
    private String identificador;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profesor);

        verificarVista();

        alumnosHabilitados = findViewById(R.id.alumnosHabilitados);
        alumnosHabilitados.setOnClickListener(this);
        alumnosNoHabilitados = findViewById(R.id.alumnosNoHabilitados);
        alumnosNoHabilitados.setOnClickListener(this);
        inhabilitarAlumnos = findViewById(R.id.inhabilitarAlumnos);
        inhabilitarAlumnos.setOnClickListener(this);
        cerrarSesion = findViewById(R.id.cerrarSesion);
        cerrarSesion.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        Intent intent = null;
        switch (view.getId()) {
            case R.id.alumnosHabilitados:
                intent = new Intent(Profesor.this, VerHijoProfesor.class);
                intent.putExtra("titulo", getApplicationContext().getResources().getString(R.string.cAlumnosHabilitados));
                intent.putExtra("identificador", identificador);
                intent.putExtra("estado", true);
                startActivity(intent);
                break;
            case R.id.alumnosNoHabilitados:
                intent = new Intent(Profesor.this, VerHijoProfesor.class);
                intent.putExtra("titulo", getApplicationContext().getResources().getString(R.string.cAlumnosNoHabilitados));
                intent.putExtra("identificador", identificador);
                intent.putExtra("estado", false);
                startActivity(intent);
                break;
            case R.id.inhabilitarAlumnos:
                if (Fecha.rangoFecha()) {
                    AlertaDialogoUtil.inhabilitarAlumnos(identificador, Profesor.this);
                } else {
                    Toast.makeText(this, Mensaje.mensajeFueraRango, Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.cerrarSesion:
                AlertaDialogoUtil.cerrarSesion(null, null, null, Profesor.this, 4);
                break;
        }
    }

    @Override
    public void onBackPressed() {
        AlertaDialogoUtil.cerrarSesion(null, null, null, Profesor.this, 4);
    }

    /**
     * Definir documentación
     */
    private void verificarVista() {
        Intent inten = getIntent();
        Bundle bun = inten.getExtras();

        if (bun != null) {
            identificador = bun.getString("identificador");
        }
    }

}
