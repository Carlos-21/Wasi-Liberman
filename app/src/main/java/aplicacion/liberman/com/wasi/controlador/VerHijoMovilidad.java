package aplicacion.liberman.com.wasi.controlador;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.WriteBatch;

import aplicacion.liberman.com.wasi.R;
import aplicacion.liberman.com.wasi.contenedor.Hijo;
import aplicacion.liberman.com.wasi.soporte.AdaptadorHijo;
import aplicacion.liberman.com.wasi.soporte.Mensaje;

public class VerHijoMovilidad extends AppCompatActivity implements View.OnClickListener{
    private RecyclerView lista;
    private boolean estado;
    private boolean casa;
    private AdaptadorHijo adaptadorHijo;
    private String identificador;
    private boolean bandera;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver_hijo_movilidad);

        verificarVista();

        lista = (RecyclerView) findViewById(R.id.listaHijoMovilidad);
        lista.setHasFixedSize(true);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        lista.setLayoutManager(linearLayoutManager);

        FirebaseFirestore database = FirebaseFirestore.getInstance();
        Query query = database.collection("Niño").whereEqualTo("movilidad",identificador)
                                                              .whereEqualTo("estado", estado)
                                                              .whereEqualTo("casa", casa);

        FirestoreRecyclerOptions<Hijo> options = new FirestoreRecyclerOptions.Builder<Hijo>()
                .setQuery(query, Hijo.class)
                .build();

        adaptadorHijo = new AdaptadorHijo(options);

        adaptadorHijo.setOnClickListener(this);
        lista.setAdapter(adaptadorHijo);
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

    /**
     * Definir documentación
     */
    private void verificarVista(){
        Intent inten = getIntent();
        Bundle bun = inten.getExtras();

        if(bun != null){
            setTitle((String)bun.getString("titulo"));
            identificador = (String)bun.getString("identificador");
            estado = (boolean)bun.getBoolean("estado");
            casa = (boolean)bun.getBoolean("casa");
            bandera = (boolean)bun.getBoolean("bandera");
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Intent intent = new Intent(VerHijoMovilidad.this, Movilidad.class);
                intent.putExtra("identificador", identificador);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
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
