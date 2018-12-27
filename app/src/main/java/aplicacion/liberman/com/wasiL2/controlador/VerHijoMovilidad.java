package aplicacion.liberman.com.wasiL2.controlador;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.WriteBatch;

import aplicacion.liberman.com.wasiL2.R;
import aplicacion.liberman.com.wasiL2.contenedor.Hijo;
import aplicacion.liberman.com.wasiL2.soporte.AdaptadorHijo;
import aplicacion.liberman.com.wasiL2.soporte.Mensaje;
import aplicacion.liberman.com.wasiL2.util.FirebaseUtilConsulta;

public class VerHijoMovilidad extends AppCompatActivity implements View.OnClickListener{
    private RecyclerView lista;
    private boolean bEstado;
    private boolean bCasa;
    private AdaptadorHijo adaptadorHijo;
    private String sIdentificador;
    private boolean bandera;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver_hijo_movilidad);

        inicializarVerHijoMovilidad();
    }

    @Override
    protected void onStart() {
        super.onStart();
        adaptadorHijo.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();

        if (adaptadorHijo != null) {
            adaptadorHijo.stopListening();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Intent intent = new Intent(VerHijoMovilidad.this, Movilidad.class);
                intent.putExtra("identificador", sIdentificador);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(VerHijoMovilidad.this, Movilidad.class);
        intent.putExtra("identificador", sIdentificador);
        startActivity(intent);
    }

    @Override
    public void onClick(View view) {
        if(bandera){
            int posicion = lista.getChildAdapterPosition(view);
            Hijo hijo = adaptadorHijo.getItem(posicion);
            autorizarEntregaHijo(hijo.getApellidos(), hijo.getNombres(), hijo.getIdentificador());
            registrarSalida(hijo.getIdentificador());
        }
    }

    private void verificarIntencion() {
        Intent oIntencion = getIntent();
        Bundle oBundle = oIntencion.getExtras();

        if (oBundle != null) {
            setTitle(oBundle.getString("titulo"));
            sIdentificador = oBundle.getString("identificador");
            bEstado = oBundle.getBoolean("estado");
            bCasa = oBundle.getBoolean("casa");
            bandera = oBundle.getBoolean("bandera");
        }
    }

    private void inicializarVerHijoMovilidad() {
        verificarIntencion();

        lista = findViewById(R.id.listaHijoMovilidad);
        lista.setHasFixedSize(true);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        lista.setLayoutManager(linearLayoutManager);

        adaptadorHijo = new AdaptadorHijo(FirebaseUtilConsulta.listarAlumnosMovilidad(sIdentificador, bEstado, bCasa));

        adaptadorHijo.setOnClickListener(this);
        lista.setAdapter(adaptadorHijo);
    }

    /**
     * Definir documentación
     */
    private void autorizarEntregaHijo(String apellidosHijo, String nombresHijo, String identificadorHijo){
        Mensaje.nombre = apellidosHijo + " " + nombresHijo;
        AlertDialog.Builder builder = new AlertDialog.Builder(VerHijoMovilidad.this);
        builder.setTitle(Mensaje.tituloPermitirEntrega);
        builder.setMessage(Mensaje.mensajePermitirEntrega.replace("paramH", Mensaje.nombre));
        builder.setCancelable(false);
        builder.setIcon(R.drawable.informacion);

        builder.setPositiveButton("Si", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                String mensaje = Mensaje.mensajeEntregaHecha.replace("paramH", Mensaje.nombre);
                Toast.makeText(VerHijoMovilidad.this, mensaje, Toast.LENGTH_SHORT).show();
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
    private void registrarSalida(String identificadorHijo){
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        WriteBatch batch = db.batch();

        DocumentReference nycRef = db.collection("Niño").document(identificadorHijo);
        batch.update(nycRef, "casa", true);

        batch.commit().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                // ...
            }
        });
    }

}
