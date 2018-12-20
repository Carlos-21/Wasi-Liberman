package aplicacion.liberman.com.wasi.controlador;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import aplicacion.liberman.com.wasi.R;
import aplicacion.liberman.com.wasi.soporte.Fecha;
import aplicacion.liberman.com.wasi.soporte.Mensaje;
import aplicacion.liberman.com.wasi.util.AlertaDialogoUtil;
import aplicacion.liberman.com.wasi.util.FirebaseUtil;

public class Profesor extends AppCompatActivity implements View.OnClickListener{
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

        alumnosHabilitados = (ImageView)findViewById(R.id.alumnosHabilitados);
        alumnosHabilitados.setOnClickListener(this);
        alumnosNoHabilitados = (ImageView)findViewById(R.id.alumnosNoHabilitados);
        alumnosNoHabilitados.setOnClickListener(this);
        inhabilitarAlumnos = (ImageView)findViewById(R.id.inhabilitarAlumnos);
        inhabilitarAlumnos.setOnClickListener(this);
        cerrarSesion = (ImageView)findViewById(R.id.cerrarSesion);
        cerrarSesion.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        Intent intent = null;
        switch (view.getId()){
            case R.id.alumnosHabilitados : intent = new Intent(Profesor.this, VerHijoProfesor.class);
                intent.putExtra("titulo", getApplicationContext().getResources().getString(R.string.cAlumnosHabilitados));
                intent.putExtra("identificador", identificador);
                intent.putExtra("estado", true);
                startActivity(intent);
                break;
            case R.id.alumnosNoHabilitados : intent = new Intent(Profesor.this, VerHijoProfesor.class);
                intent.putExtra("titulo", getApplicationContext().getResources().getString(R.string.cAlumnosNoHabilitados));
                intent.putExtra("identificador", identificador);
                intent.putExtra("estado", false);
                startActivity(intent);
                break;
            case R.id.inhabilitarAlumnos :  if(Fecha.rangoFecha()){
                                                AlertaDialogoUtil.inhabilitarAlumnos(identificador, Profesor.this);
                                            }
                                            else{
                                                Toast.makeText(this, Mensaje.mensajeFueraRango, Toast.LENGTH_SHORT).show();
                                            }
                                            break;
            case R.id.cerrarSesion : AlertaDialogoUtil.cerrarSesion(null, Profesor.this, null, 4);
                break;
        }
    }

    /**
     * Método que se encargará de verificar si el usuario desea cerrar sessión,
     * de ser ser afirmativo se mostrará la actividad de perfiles
     */
    /*private void cerrarSesion(){
        AlertDialog.Builder builder = new AlertDialog.Builder(Profesor.this);
        builder.setTitle(Mensaje.tituloCerrarSesion);
        builder.setMessage(Mensaje.mensajeCerrarSesion);
        builder.setCancelable(false);
        builder.setIcon(R.drawable.informacion);

        builder.setPositiveButton("Si", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Intent intent = new Intent(Profesor.this, Perfil.class);
                startActivity(intent);
                finish();
            }
        });

        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                //No se hace nada
                dialogInterface.cancel();
            }
        });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }*/

    /*private void inhabilitarAlumnos(){
        AlertDialog.Builder builder = new AlertDialog.Builder(Profesor.this);
        builder.setTitle(Mensaje.tituloQuitarPermisos);
        builder.setMessage(Mensaje.mensajeQuitarPermisos);
        builder.setCancelable(false);
        builder.setIcon(R.drawable.informacion);

        builder.setPositiveButton("Si", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                FirebaseUtil.quitarPermisosAlumnos(identificador, Profesor.this);
            }
        });

        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                //No se hace nada
                dialogInterface.cancel();
            }
        });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }*/

    /**
     * Definir documentación
     */
    private void verificarVista(){
        Intent inten = getIntent();
        Bundle bun = inten.getExtras();

        if(bun != null){
            identificador = (String)bun.getString("identificador");
        }
    }

}
