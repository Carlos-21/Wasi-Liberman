package aplicacion.liberman.com.wasi.controlador;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import aplicacion.liberman.com.wasi.R;
import aplicacion.liberman.com.wasi.contenedor.Hijo;
import aplicacion.liberman.com.wasi.soporte.Adaptador;

public class VerHijoApoderado extends AppCompatActivity implements View.OnClickListener{
    private RecyclerView lista;
    private boolean bandera = true;
    private Adaptador adaptador;
    private String identificador;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver_hijo_apoderado);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        setTitle(R.string.sHijos);
        verificarVista();

        lista = (RecyclerView) findViewById(R.id.listaHijoApoderado);
        lista.setHasFixedSize(true);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        lista.setLayoutManager(linearLayoutManager);

        FirebaseFirestore database = FirebaseFirestore.getInstance();
        System.out.println("Identificador : "+identificador);
        DocumentReference sR = database.collection("Usuarios").document(identificador);

        Query query = sR.collection("Hijos");

        FirestoreRecyclerOptions<Hijo> options = new FirestoreRecyclerOptions.Builder<Hijo>()
                .setQuery(query, Hijo.class)
                .build();

        adaptador = new Adaptador(options);

        adaptador.setOnClickListener(this);
        lista.setAdapter(adaptador);
    }

    @Override
    public void onClick(View view) {
        if(bandera){
            int posicion = lista.getChildAdapterPosition(view);
            Hijo hijo = adaptador.getItem(posicion);
            Intent intent = new Intent(VerHijoApoderado.this, PermitirSalida.class);

            intent.putExtra("nombres", hijo.getNombres());
            intent.putExtra("apellidos", hijo.getApellidos());
            intent.putExtra("imagen", hijo.getImagen());
            intent.putExtra("identificador", identificador);

            startActivity(intent);
        }
    }

    /**
     * Definir documentación
     */
    private void verificarVista(){
        Intent inten = getIntent();
        Bundle bun = inten.getExtras();

        if(bun != null){
            bandera = (boolean)bun.getBoolean("bandera");
            identificador = (String)bun.getString("identificador");
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        adaptador.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();

        if (adaptador != null) {
            adaptador.stopListening();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Intent intent = new Intent(VerHijoApoderado.this, Apoderado.class);
                intent.putExtra("identificador", identificador);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
