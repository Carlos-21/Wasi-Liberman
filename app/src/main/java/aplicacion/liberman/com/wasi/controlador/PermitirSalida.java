package aplicacion.liberman.com.wasi.controlador;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import aplicacion.liberman.com.wasi.R;
import aplicacion.liberman.com.wasi.soporte.Mensaje;
import aplicacion.liberman.com.wasi.util.AlertaDialogoUtil;

public class PermitirSalida extends AppCompatActivity implements View.OnClickListener{
    private ImageView permitirSalida;
    private ImageView permitirMovilidad;
    private ImageView hijoSalida;
    private String nombresHijo;
    private String apellidosHijo;
    private String imagen;
    private String identificador;
    private String identificadorHijo;
    private String identificadorRecogedorApoderado;
    private int tipoPerfil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_permitir_salida);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        permitirSalida = (ImageView)findViewById(R.id.permitirSalida);
        permitirSalida.setOnClickListener(this);
        permitirMovilidad = (ImageView)findViewById(R.id.permitirMovilidad);
        permitirMovilidad.setOnClickListener(this);
        hijoSalida = (ImageView)findViewById(R.id.fotoHijoSalida);

        verificarVista();
        setTitle(apellidosHijo + " " + nombresHijo);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.permitirSalida : AlertaDialogoUtil.autorizarSalidaHijo(PermitirSalida.this, 1, imagen, identificador, nombresHijo, apellidosHijo, identificadorHijo, identificadorRecogedorApoderado, tipoPerfil);
                break;
            case R.id.permitirMovilidad : AlertaDialogoUtil.autorizarSalidaHijo(PermitirSalida.this, 2, imagen, identificador, nombresHijo, apellidosHijo, identificadorHijo, identificadorRecogedorApoderado, tipoPerfil);
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
            imagen = (String)bun.getString("imagen");
            identificador = (String)bun.getString("identificador");
            identificadorHijo = (String)bun.getString("identificadorHijo");
            Picasso.get().load(imagen).into(hijoSalida);
            tipoPerfil = (int)bun.getInt("perfil");
            if(tipoPerfil == 3){
                identificadorRecogedorApoderado = (String)bun.getString("identificadorRecogedorApoderado");
            }
        }
    }

    /*
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
                intent.putExtra("imagen",imagen);
                intent.putExtra("identificador", identificador);
                intent.putExtra("nombres", nombresHijo + " " + apellidosHijo);
                intent.putExtra("identificadorHijo", identificadorHijo);
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
                intent.putExtra("imagen",imagen);
                intent.putExtra("identificador", identificador);
                intent.putExtra("nombres", nombresHijo + " " + apellidosHijo);
                intent.putExtra("identificadorHijo", identificadorHijo);
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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                if(tipoPerfil == 1){
                    Intent intent = new Intent(PermitirSalida.this, VerHijoApoderado.class);
                    intent.putExtra("bandera", true);
                    intent.putExtra("identificador", identificador);
                    startActivity(intent);
                }
                else{
                    Intent intent = new Intent(PermitirSalida.this, VerHijoRecogedor.class);
                    intent.putExtra("identificador", identificador);
                    startActivity(intent);
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
