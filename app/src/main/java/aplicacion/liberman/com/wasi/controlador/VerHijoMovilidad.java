package aplicacion.liberman.com.wasi.controlador;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import aplicacion.liberman.com.wasi.R;
import aplicacion.liberman.com.wasi.contenedor.Hijo;
import aplicacion.liberman.com.wasi.soporte.Adaptador;

public class VerHijoMovilidad extends AppCompatActivity {
    private RecyclerView lista;

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
        DocumentReference sR = database.collection("Usuarios").document("user01");
        Query query = sR.collection("Hijos");

        FirestoreRecyclerOptions<Hijo> options = new FirestoreRecyclerOptions.Builder<Hijo>()
                .setQuery(query, Hijo.class)
                .build();

        Adaptador adaptador = new Adaptador(options);

        lista.setAdapter(adaptador);
    }

    /**
     * Definir documentación
     */
    private void verificarVista(){
        Intent inten = getIntent();
        Bundle bun = inten.getExtras();

        if(bun != null){
            System.out.println("Titulo : "+(String)bun.getString("titulo"));
            setTitle((String)bun.getString("titulo"));
        }
    }

}
