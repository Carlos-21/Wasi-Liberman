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
import aplicacion.liberman.com.wasiL2.util.FirebaseUtilConsulta;

public class Profesor extends AppCompatActivity implements View.OnClickListener {
    private ImageView alumnosHabilitados;
    private ImageView alumnosNoHabilitados;
    private ImageView inhabilitarAlumnos;
    private ImageView configuracionUsuario;
    private ImageView cerrarSesion;
    private String identificador;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profesor);

        verificarVista();

        inicializarProfesor();
    }

    /**
     * Método encargado de inicializar los componentes
     * necesarios de la vista activity_profesor
     */
    private void inicializarProfesor() {
        alumnosHabilitados = findViewById(R.id.alumnosHabilitados);
        alumnosHabilitados.setOnClickListener(this);
        alumnosNoHabilitados = findViewById(R.id.alumnosNoHabilitados);
        alumnosNoHabilitados.setOnClickListener(this);
        inhabilitarAlumnos = findViewById(R.id.inhabilitarAlumnos);
        inhabilitarAlumnos.setOnClickListener(this);
        configuracionUsuario = findViewById(R.id.configuracionUsuario);
        configuracionUsuario.setOnClickListener(this);
        cerrarSesion = findViewById(R.id.cerrarSesion);
        cerrarSesion.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.alumnosHabilitados:
                FirebaseUtilConsulta.verificarAlumnosProfesor(Profesor.this, identificador, true);
                break;
            case R.id.alumnosNoHabilitados:
                FirebaseUtilConsulta.verificarAlumnosProfesor(Profesor.this, identificador, false);
                break;
            case R.id.inhabilitarAlumnos:
                if (Fecha.rangoFecha()) {
                    AlertaDialogoUtil.inhabilitarAlumnos(identificador, Profesor.this);
                } else {
                    Toast.makeText(this, Mensaje.mensajeFueraRango, Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.configuracionUsuario:
                Intent intent = new Intent(Profesor.this, ConfiguracionUsuario.class);
                intent.putExtra("identificador", identificador);
                intent.putExtra("perfil", 2);
                startActivity(intent);
                break;
            case R.id.cerrarSesion:
                AlertaDialogoUtil.cerrarSesion(Profesor.this);
                break;
        }
    }

    @Override
    public void onBackPressed() {
        AlertaDialogoUtil.cerrarSesion(Profesor.this);
    }

    /**
     * Método encargado de verificar si en una anterior vista se pasó
     * un dato con la llave identificador como parámetro
     */
    private void verificarVista() {
        Intent inten = getIntent();
        Bundle bun = inten.getExtras();

        if (bun != null) {
            identificador = bun.getString("identificador");
        }
    }

}
