package aplicacion.liberman.com.wasiL2.controlador;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.WriteBatch;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.Map;

import aplicacion.liberman.com.wasiL2.R;
import aplicacion.liberman.com.wasiL2.soporte.Fecha;
import aplicacion.liberman.com.wasiL2.soporte.Mensaje;
import aplicacion.liberman.com.wasiL2.util.FirebaseUtilEscritura;

public class SalidaPermitida extends AppCompatActivity {
    private TextView mensajeSalida;
    private ImageView fotoHijoSalida;
    private String identificador;
    private String nombreHijo;
    private String identificadorHijo;
    private String imagenHijo;
    private String apoderado;
    private int tipoPerfil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_salida_permitida);

        mensajeSalida = findViewById(R.id.mensajeSalida);
        fotoHijoSalida = findViewById(R.id.fotoHijoSalida);
        mostrarSalida();
        FirebaseUtilEscritura.registrarSalida(SalidaPermitida.this, identificador, nombreHijo, identificadorHijo, imagenHijo, apoderado);
    }

    /**
     * Método encargado de mostrar la fecha y hora en la cual se esta
     * realizando el proceso de permitir que el hijo pueda salir de la
     * institución educativa
     */
    private void mostrarSalida() {
        Intent inten = getIntent();
        Bundle bun = inten.getExtras();

        if (bun != null) {
            int tipoSalida = bun.getInt("salida");
            if (tipoSalida == 1) {
                setTitle("Permitir Salida");
            } else {
                setTitle("Permitir Movilidad");
            }
            imagenHijo = bun.getString("imagen");
            Picasso.get().load(imagenHijo).into(fotoHijoSalida);
            identificador = bun.getString("identificador");
            nombreHijo = bun.getString("nombres");
            identificadorHijo = bun.getString("identificadorHijo");
            apoderado = bun.getString("apoderado");
            tipoPerfil = bun.getInt("tipoPerfil");
        }

        Mensaje.hora = Fecha.horaActual();
        Mensaje.fecha = Fecha.fechaActual();

        mensajeSalida.setText(Mensaje.mensajeSalida.replace("paramH", Mensaje.hora).replace("paramD", Mensaje.fecha));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                if (tipoPerfil == 1) {
                    Intent intent = new Intent(SalidaPermitida.this, VerHijoApoderado.class);
                    intent.putExtra("bandera", true);
                    intent.putExtra("identificador", identificador);
                    startActivity(intent);
                } else {
                    Intent intent = new Intent(SalidaPermitida.this, VerHijoRecogedor.class);
                    intent.putExtra("identificador", identificador);
                    startActivity(intent);
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onBackPressed() {
        if (tipoPerfil == 1) {
            Intent intent = new Intent(SalidaPermitida.this, VerHijoApoderado.class);
            intent.putExtra("bandera", true);
            intent.putExtra("identificador", identificador);
            startActivity(intent);
        } else {
            Intent intent = new Intent(SalidaPermitida.this, VerHijoRecogedor.class);
            intent.putExtra("identificador", identificador);
            startActivity(intent);
        }
    }
}
