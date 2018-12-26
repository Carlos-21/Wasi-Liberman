package aplicacion.liberman.com.wasiL2.controlador;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import aplicacion.liberman.com.wasiL2.R;
import aplicacion.liberman.com.wasiL2.contenedor.Hijo;
import aplicacion.liberman.com.wasiL2.soporte.AdaptadorHijo;
import aplicacion.liberman.com.wasiL2.soporte.Mensaje;

public class VerHijoRecogedor extends AppCompatActivity implements View.OnClickListener{
    private RecyclerView lista;
    private AdaptadorHijo adaptadorHijo;
    private String identificador;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver_hijo_recogedor);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        setTitle(R.string.sHijos);
        verificarVista();

        lista = (RecyclerView) findViewById(R.id.listaHijoRecogedor);
        lista.setHasFixedSize(true);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        lista.setLayoutManager(linearLayoutManager);

        FirebaseFirestore database = FirebaseFirestore.getInstance();
        Query query = database.collection("Niño").whereEqualTo("recogedor",identificador);

        FirestoreRecyclerOptions<Hijo> options = new FirestoreRecyclerOptions.Builder<Hijo>()
                .setQuery(query, Hijo.class)
                .build();

        adaptadorHijo = new AdaptadorHijo(options);

        adaptadorHijo.setOnClickListener(this);
        lista.setAdapter(adaptadorHijo);
    }

    @Override
    public void onClick(View view) {
        int posicion = lista.getChildAdapterPosition(view);
        Hijo hijo = adaptadorHijo.getItem(posicion);
        if(!hijo.isEstado()){
            Intent intent = new Intent(VerHijoRecogedor.this, PermitirSalida.class);

            intent.putExtra("nombres", hijo.getNombres());
            intent.putExtra("apellidos", hijo.getApellidos());
            intent.putExtra("imagen", hijo.getImagen());
            intent.putExtra("identificador", identificador);
            intent.putExtra("identificadorHijo", hijo.getIdentificador());
            intent.putExtra("identificadorRecogedorApoderado", hijo.getApoderado());
            intent.putExtra("perfil", 3);

            startActivity(intent);
        }
        else{
            String mensaje = Mensaje.mensajeSalidaHecha.replace("paramH", hijo.getNombres() + " " + hijo.getApellidos());
            Toast.makeText(VerHijoRecogedor.this, mensaje, Toast.LENGTH_SHORT).show();
        }
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
                Intent intent = new Intent(VerHijoRecogedor.this, Recogedor.class);
                intent.putExtra("identificador", identificador);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
