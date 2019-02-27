package aplicacion.liberman.com.wasiL2.controlador;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;

import aplicacion.liberman.com.wasiL2.R;
import aplicacion.liberman.com.wasiL2.soporte.AdaptadorRegistro;
import aplicacion.liberman.com.wasiL2.util.FirebaseUtilConsulta;

public class VerRegistroSalida extends AppCompatActivity {
    private RecyclerView lista;
    private AdaptadorRegistro adaptadorRegistro;
    private String sIdentificador;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver_registro_salida);

        inicializarVerRegistroSalida();
    }

    /**
     * Método encargado de inicializar los componentes
     * necesarios de la vista activity_ver_registro_salida
     */
    private void inicializarVerRegistroSalida() {
        setTitle(R.string.sRegistroSalidas);

        verificarIntencion();

        lista = findViewById(R.id.listaRegistroSalida);
        lista.setHasFixedSize(true);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        lista.setLayoutManager(linearLayoutManager);


        adaptadorRegistro = new AdaptadorRegistro(FirebaseUtilConsulta.listarSalidas(sIdentificador));

        lista.setAdapter(adaptadorRegistro);
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
                intent.putExtra("identificador", sIdentificador);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(VerRegistroSalida.this, Apoderado.class);
        intent.putExtra("identificador", sIdentificador);
        startActivity(intent);
    }
}
