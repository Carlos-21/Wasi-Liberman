package aplicacion.liberman.com.wasiL2.controlador;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import aplicacion.liberman.com.wasiL2.R;
import aplicacion.liberman.com.wasiL2.contenedor.Hijo;
import aplicacion.liberman.com.wasiL2.soporte.AdaptadorHijo;

public class VerHijoProfesor extends AppCompatActivity {
    private RecyclerView lista;
    private boolean estado;
    private AdaptadorHijo adaptadorHijo;
    private String identificador;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver_hijo_profesor);

        verificarVista();

        lista = (RecyclerView) findViewById(R.id.listaHijoProfesor);
        lista.setHasFixedSize(true);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        lista.setLayoutManager(linearLayoutManager);

        FirebaseFirestore database = FirebaseFirestore.getInstance();
        Query query = database.collection("Niño").whereEqualTo("profesor",identificador).whereEqualTo("estado", estado);

        FirestoreRecyclerOptions<Hijo> options = new FirestoreRecyclerOptions.Builder<Hijo>()
                .setQuery(query, Hijo.class)
                .build();

        adaptadorHijo = new AdaptadorHijo(options);

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
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Intent intent = new Intent(VerHijoProfesor.this, Profesor.class);
                intent.putExtra("identificador", identificador);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
