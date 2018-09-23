package aplicacion.liberman.com.wasi.controlador;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import aplicacion.liberman.com.wasi.R;
import aplicacion.liberman.com.wasi.soporte.Fecha;
import aplicacion.liberman.com.wasi.soporte.Mensaje;

public class SalidaPermitida extends AppCompatActivity {
    private TextView mensajeSalida;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_salida_permitida);

        mensajeSalida = (TextView)findViewById(R.id.mensajeSalida);
        mostrarSalida();
    }

    /**
     * Definir documentación y definir método
     */
    private void mostrarSalida(){
        Intent inten = getIntent();
        Bundle bun = inten.getExtras();

        if(bun != null){
            int tipoSalida = (int)bun.getInt("salida");
            if(tipoSalida == 1){
                setTitle("Permitir Salida");
            }
            else{
                setTitle("Permitir Movilidad");
            }
        }

        Mensaje.hora = Fecha.horaActual();
        Mensaje.fecha = Fecha.fechaActual();

        mensajeSalida.setText(Mensaje.mensajeSalida.replace("paramH", Mensaje.hora).replace("paramD", Mensaje.fecha));
    }

}
