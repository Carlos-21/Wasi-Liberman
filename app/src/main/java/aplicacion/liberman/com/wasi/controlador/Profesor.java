package aplicacion.liberman.com.wasi.controlador;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import aplicacion.liberman.com.wasi.R;
import aplicacion.liberman.com.wasi.soporte.Mensaje;

public class Profesor extends AppCompatActivity implements View.OnClickListener{
    private ImageView alumnosHabilitados;
    private ImageView alumnosNoHabilitados;
    private ImageView cerrarSesion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profesor);

        alumnosHabilitados = (ImageView)findViewById(R.id.alumnosHabilitados);
        alumnosHabilitados.setOnClickListener(this);
        alumnosNoHabilitados = (ImageView)findViewById(R.id.alumnosNoHabilitados);
        alumnosNoHabilitados.setOnClickListener(this);
        cerrarSesion = (ImageView)findViewById(R.id.cerrarSesion);
        cerrarSesion.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        Intent intent = null;
        switch (view.getId()){
            case R.id.alumnosHabilitados : intent = new Intent(Profesor.this, VerHijoProfesor.class);
                intent.putExtra("titulo", getApplicationContext().getResources().getString(R.string.cAlumnosHabilitados));
                startActivity(intent);
                break;
            case R.id.alumnosNoHabilitados : intent = new Intent(Profesor.this, VerHijoProfesor.class);
                intent.putExtra("titulo", getApplicationContext().getResources().getString(R.string.cAlumnosNoHabilitados));
                startActivity(intent);
                break;
            case R.id.cerrarSesion : cerrarSesion();
                break;
        }
    }

    /**
     * Método que se encargará de verificar si el usuario desea cerrar sessión,
     * de ser ser afirmativo se mostrará la actividad de perfiles
     */
    private void cerrarSesion(){
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
    }

}
