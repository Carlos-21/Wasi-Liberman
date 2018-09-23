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

public class PermitirSalida extends AppCompatActivity implements View.OnClickListener{
    private ImageView permitirSalida;
    private ImageView permitirMovilidad;
    private String nombresHijo;
    private String apellidosHijo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_permitir_salida);

        verificarVista();
        setTitle(apellidosHijo + " " + nombresHijo);

        permitirSalida = (ImageView)findViewById(R.id.permitirSalida);
        permitirSalida.setOnClickListener(this);
        permitirMovilidad = (ImageView)findViewById(R.id.permitirMovilidad);
        permitirMovilidad.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.permitirSalida : autorizarSalidaApoderado();
                break;
            case R.id.permitirMovilidad : autorizarSalidaMovilidad();
                break;
        }
    }

    /**
     * Definir documentación
     */
    private void verificarVista(){
        Intent inten = getIntent();
        Bundle bun = inten.getExtras();

        if(bun != null){
            nombresHijo = (String)bun.getString("nombres");
            apellidosHijo = (String)bun.getString("apellidos");
        }
    }

    /**
     * Definir documentación
     */
    private void autorizarSalidaApoderado(){
        Mensaje.nombre = apellidosHijo + " " + nombresHijo;
        AlertDialog.Builder builder = new AlertDialog.Builder(PermitirSalida.this);
        builder.setTitle(Mensaje.tituloPermitirSalida);
        builder.setMessage(Mensaje.mensajePermitirSalida.replace("paramN", Mensaje.nombre));
        builder.setCancelable(false);
        builder.setIcon(R.drawable.informacion);

        builder.setPositiveButton("Si", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Intent intent = new Intent(PermitirSalida.this, SalidaPermitida.class);
                intent.putExtra("salida",1);
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

    /**
     * Definir documentación
     */
    private void autorizarSalidaMovilidad(){
        AlertDialog.Builder builder = new AlertDialog.Builder(PermitirSalida.this);
        builder.setTitle(Mensaje.tituloPermitirMovilidad);
        builder.setMessage(Mensaje.mensajePermitirMovilidad);
        builder.setCancelable(false);
        builder.setIcon(R.drawable.informacion);

        builder.setPositiveButton("Si", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Intent intent = new Intent(PermitirSalida.this, SalidaPermitida.class);
                intent.putExtra("salida",2);
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
