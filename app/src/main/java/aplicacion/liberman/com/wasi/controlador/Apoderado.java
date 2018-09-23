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

public class Apoderado extends AppCompatActivity implements View.OnClickListener{
    private ImageView verHijos;
    private ImageView permitirSalida;
    private ImageView asignarRecogedor;
    private ImageView registroSalidas;
    private ImageView cerrarSesion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apoderado);

        verHijos = (ImageView)findViewById(R.id.verHijos);
        verHijos.setOnClickListener(this);
        permitirSalida = (ImageView)findViewById(R.id.permitirSalida);
        permitirSalida.setOnClickListener(this);
        asignarRecogedor = (ImageView)findViewById(R.id.asignarRecogedor);
        asignarRecogedor.setOnClickListener(this);
        registroSalidas = (ImageView)findViewById(R.id.registroSalidas);
        registroSalidas.setOnClickListener(this);
        cerrarSesion = (ImageView)findViewById(R.id.cerrarSesion);
        cerrarSesion.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        Intent intent = null;
        switch (view.getId()){
            case R.id.verHijos : intent = new Intent(Apoderado.this, VerHijoApoderado.class);
                                 intent.putExtra("bandera","false");
                                 break;
            case R.id.permitirSalida : intent = new Intent(Apoderado.this, VerHijoApoderado.class);
                                       intent.putExtra("bandera","true");
                                       break;
            case R.id.asignarRecogedor : break;
            case R.id.registroSalidas : break;
            case R.id.cerrarSesion : cerrarSesion();
                                     break;
        }
        startActivity(intent);
    }

    /**
     * Método que se encargará de verificar si el usuario desea cerrar sessión,
     * de ser ser afirmativo se mostrará la actividad de perfiles
     */
    private void cerrarSesion(){
        AlertDialog.Builder builder = new AlertDialog.Builder(Apoderado.this);
        builder.setTitle(Mensaje.tituloCerrarSesion);
        builder.setMessage(Mensaje.mensajeCerrarSesion);
        builder.setCancelable(false);
        builder.setIcon(R.drawable.informacion);

        builder.setPositiveButton("Si", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Intent intent = new Intent(Apoderado.this, Perfil.class);
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
