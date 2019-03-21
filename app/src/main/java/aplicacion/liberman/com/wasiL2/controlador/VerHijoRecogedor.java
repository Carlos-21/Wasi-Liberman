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

public class VerHijoRecogedor extends AppCompatActivity implements View.OnClickListener {
    private RecyclerView lista;
    private AdaptadorHijo adaptadorHijo;
    private String sIdentificador;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver_hijo_recogedor);

        inicializarVerHijoRecogedor();
    }

    @Override
    public void onClick(View view) {
        int posicion = lista.getChildAdapterPosition(view);
        Hijo hijo = adaptadorHijo.getItem(posicion);
        if (!hijo.isEstado()) {
            Intent intent = new Intent(VerHijoRecogedor.this, PermitirSalida.class);

            intent.putExtra("nombres", hijo.getNombres());
            intent.putExtra("apellidos", hijo.getApellidos());
            intent.putExtra("imagen", hijo.getImagen());
            intent.putExtra("identificador", sIdentificador);
            intent.putExtra("identificadorHijo", hijo.getIdentificador());
            intent.putExtra("identificadorRecogedorApoderado", hijo.getApoderado());
            intent.putExtra("perfil", 3);

            startActivity(intent);
        } else {
            String mensaje = Mensaje.mensajeSalidaHecha.replace("paramH", hijo.getNombres() + " " + hijo.getApellidos());
            Toast.makeText(VerHijoRecogedor.this, mensaje, Toast.LENGTH_SHORT).show();
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
                intent.putExtra("identificador", sIdentificador);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(VerHijoRecogedor.this, Recogedor.class);
        intent.putExtra("identificador", sIdentificador);
        startActivity(intent);
    }

    /**
     * Método encargado de inicializar los componentes
     * necesarios de la vista activity_ver_hijo_recogedor
     */
    private void inicializarVerHijoRecogedor() {
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        setTitle(R.string.sHijos);
        verificarIntencion();

        lista = findViewById(R.id.listaHijoRecogedor);
        lista.setHasFixedSize(true);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        lista.setLayoutManager(linearLayoutManager);

        adaptadorHijo = new AdaptadorHijo(FirebaseUtilConsulta.listarAlumnosRecogedor(sIdentificador));

        adaptadorHijo.setOnClickListener(this);
        lista.setAdapter(adaptadorHijo);
    }

    /**
     * Método encargado de verificar si en una anterior vista se pasó
     * un dato con la llave identificador como parámetro
     */
    private void verificarIntencion() {
        Intent inten = getIntent();
        Bundle bun = inten.getExtras();

        if (bun != null) {
            sIdentificador = bun.getString("identificador");
        }
    }

}
