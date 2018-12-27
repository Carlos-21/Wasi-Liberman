package aplicacion.liberman.com.wasiL2.controlador;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import aplicacion.liberman.com.wasiL2.R;
import aplicacion.liberman.com.wasiL2.contenedor.Hijo;
import aplicacion.liberman.com.wasiL2.soporte.AdaptadorHijo;
import aplicacion.liberman.com.wasiL2.soporte.Mensaje;
import aplicacion.liberman.com.wasiL2.util.FirebaseUtilConsulta;

public class VerHijoApoderado extends AppCompatActivity implements View.OnClickListener{
    private RecyclerView lista;
    private boolean bClick = true;
    private AdaptadorHijo adaptadorHijo;
    private String sIdentificador;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver_hijo_apoderado);

        inicializarVerHijoApoderado();
    }

    @Override
    public void onClick(View view) {
        if (bClick) {
            int posicion = lista.getChildAdapterPosition(view);
            Hijo hijo = adaptadorHijo.getItem(posicion);
            if(!hijo.isEstado()){
                Intent intent = new Intent(VerHijoApoderado.this, PermitirSalida.class);

                intent.putExtra("nombres", hijo.getNombres());
                intent.putExtra("apellidos", hijo.getApellidos());
                intent.putExtra("imagen", hijo.getImagen());
                intent.putExtra("identificador", sIdentificador);
                intent.putExtra("identificadorHijo", hijo.getIdentificador());
                intent.putExtra("perfil", 1);

                startActivity(intent);
            }
            else{
                String mensaje = Mensaje.mensajeSalidaHecha.replace("paramH", hijo.getNombres() + " " + hijo.getApellidos());
                Toast.makeText(VerHijoApoderado.this, mensaje, Toast.LENGTH_SHORT).show();
            }

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
                Intent intent = new Intent(VerHijoApoderado.this, Apoderado.class);
                intent.putExtra("identificador", sIdentificador);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(VerHijoApoderado.this, Apoderado.class);
        intent.putExtra("identificador", sIdentificador);
        startActivity(intent);
    }

    private void inicializarVerHijoApoderado() {
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        setTitle(R.string.sHijos);
        verificarIntencion();

        lista = findViewById(R.id.listaHijoApoderado);
        lista.setHasFixedSize(true);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        lista.setLayoutManager(linearLayoutManager);

        adaptadorHijo = new AdaptadorHijo(FirebaseUtilConsulta.listarAlumnosApoderado(sIdentificador));

        adaptadorHijo.setOnClickListener(this);
        lista.setAdapter(adaptadorHijo);
    }

    private void verificarIntencion() {
        Intent oIntencion = getIntent();
        Bundle oBundle = oIntencion.getExtras();

        if (oBundle != null) {
            bClick = oBundle.getBoolean("bandera");
            sIdentificador = oBundle.getString("identificador");
        }
    }

}
