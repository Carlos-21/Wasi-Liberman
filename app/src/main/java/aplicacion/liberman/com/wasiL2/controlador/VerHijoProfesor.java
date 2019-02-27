package aplicacion.liberman.com.wasiL2.controlador;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;

import aplicacion.liberman.com.wasiL2.R;
import aplicacion.liberman.com.wasiL2.soporte.AdaptadorHijo;
import aplicacion.liberman.com.wasiL2.util.FirebaseUtilConsulta;

public class VerHijoProfesor extends AppCompatActivity {
    private RecyclerView lista;
    private boolean bEstado;
    private AdaptadorHijo adaptadorHijo;
    private String sIdentificador;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver_hijo_profesor);

        inicializarVerHijoProfesor();
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
                Intent intent = new Intent(VerHijoProfesor.this, Profesor.class);
                intent.putExtra("identificador", sIdentificador);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(VerHijoProfesor.this, Profesor.class);
        intent.putExtra("identificador", sIdentificador);
        startActivity(intent);
    }

    /**
     * Método encargado de inicializar los componentes
     * necesarios de la vista activity_ver_hijo_profesor
     */
    private void inicializarVerHijoProfesor() {
        verificarIntencion();

        lista = findViewById(R.id.listaHijoProfesor);
        lista.setHasFixedSize(true);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        lista.setLayoutManager(linearLayoutManager);

        adaptadorHijo = new AdaptadorHijo(FirebaseUtilConsulta.listarAlumnosProfesor(sIdentificador, bEstado));

        lista.setAdapter(adaptadorHijo);
    }

    /**
     * Método encargado de verificar si en una anterior vista se pasó
     * tres datos con las llaves titulo, identificador, estado como
     * parámetros
     */
    private void verificarIntencion() {
        Intent oIntencion = getIntent();
        Bundle oBundle = oIntencion.getExtras();

        if (oBundle != null) {
            setTitle(oBundle.getString("titulo"));
            sIdentificador = oBundle.getString("identificador");
            bEstado = oBundle.getBoolean("estado");
        }
    }

}
