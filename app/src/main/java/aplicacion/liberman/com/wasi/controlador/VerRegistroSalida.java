package aplicacion.liberman.com.wasi.controlador;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import aplicacion.liberman.com.wasi.R;
import aplicacion.liberman.com.wasi.contenedor.Registro;
import aplicacion.liberman.com.wasi.soporte.AdaptadorRegistro;

public class VerRegistroSalida extends AppCompatActivity {
    private RecyclerView lista;
    private AdaptadorRegistro adaptadorRegistro;
    private String identificador;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver_registro_salida);

        setTitle(R.string.sRegistroSalidas);

        verificarVista();

        lista = (RecyclerView) findViewById(R.id.listaRegistroSalida);
        lista.setHasFixedSize(true);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        lista.setLayoutManager(linearLayoutManager);

        FirebaseFirestore database = FirebaseFirestore.getInstance();
        Query query = database.collection("Usuarios").document(identificador).collection("Salidas");

        FirestoreRecyclerOptions<Registro> options = new FirestoreRecyclerOptions.Builder<Registro>()
                .setQuery(query, Registro.class)
                .build();

        adaptadorRegistro = new AdaptadorRegistro(options);

        lista.setAdapter(adaptadorRegistro);

    }

    /**
     * Definir documentación
     */
    private void verificarVista(){
        Intent inten = getIntent();
        Bundle bun = inten.getExtras();

        if(bun != null){
            identificador = (String)bun.getString("identificador");
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        adaptadorRegistro.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();

        if (adaptadorRegistro != null) {
            adaptadorRegistro.stopListening();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Intent intent = new Intent(VerRegistroSalida.this, Apoderado.class);
                intent.putExtra("identificador", identificador);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
