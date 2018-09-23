package aplicacion.liberman.com.wasi.controlador;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import aplicacion.liberman.com.wasi.R;
import aplicacion.liberman.com.wasi.soporte.Mensaje;

public class ConfirmarRecogedor extends AppCompatActivity implements View.OnClickListener{
    private Button confirmarRecogedor;
    private EditText textoUsuarioRecogedor;
    private EditText textoContraseñaRecogedor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirmar_recogedor);

        textoUsuarioRecogedor = (EditText)findViewById(R.id.textoUsuarioRecogedor);
        textoContraseñaRecogedor = (EditText)findViewById(R.id.textoClaveRecogedor);
        confirmarRecogedor = (Button)findViewById(R.id.botonConfirmarRecogedor);
        confirmarRecogedor.setOnClickListener(this);

        //Este código después debe aceptar una mejor implementación
        textoUsuarioRecogedor.setText("usuario: 2K349");
        textoContraseñaRecogedor.setText("contraseña: 4T890");
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.botonConfirmarRecogedor : mensajeConfirmarRecogedor();
                                                break;
        }
    }

    /**
     * Este método se encargará de captar la respuesta del padre,
     * para poder confirmar de que desea asignar a una persona
     * de que pueda recoger a su hijo
     */
    private void mensajeConfirmarRecogedor(){
        AlertDialog.Builder builder = new AlertDialog.Builder(ConfirmarRecogedor.this);
        builder.setTitle(Mensaje.tituloAsignarRecogedor);
        builder.setMessage(Mensaje.mensajeAsignarRecogedor);
        builder.setCancelable(true);
        builder.setIcon(R.drawable.informacion);

        builder.setPositiveButton("Si", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Intent intent = new Intent(ConfirmarRecogedor.this, Apoderado.class);
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
