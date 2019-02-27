package aplicacion.liberman.com.wasiL2.controlador;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;

import aplicacion.liberman.com.wasiL2.R;
import aplicacion.liberman.com.wasiL2.contenedor.Hijo;
import aplicacion.liberman.com.wasiL2.soporte.AdaptadorHijo;
import aplicacion.liberman.com.wasiL2.util.AlertaDialogoUtil;
import aplicacion.liberman.com.wasiL2.util.FirebaseUtilConsulta;

public class VerHijoMovilidad extends AppCompatActivity implements View.OnClickListener {
    private RecyclerView lista;
    private boolean bEstado;
    private boolean bCasa;
    private AdaptadorHijo adaptadorHijo;
    private String sIdentificador;
    private boolean bandera;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver_hijo_movilidad);

        inicializarVerHijoMovilidad();
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
                Intent intent = new Intent(VerHijoMovilidad.this, Movilidad.class);
                intent.putExtra("identificador", sIdentificador);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(VerHijoMovilidad.this, Movilidad.class);
        intent.putExtra("identificador", sIdentificador);
        startActivity(intent);
    }

    @Override
    public void onClick(View view) {
        if (bandera) {
            int posicion = lista.getChildAdapterPosition(view);
            Hijo hijo = adaptadorHijo.getItem(posicion);
            AlertaDialogoUtil.autorizarEntregaHijo(VerHijoMovilidad.this, hijo.getApellidos(), hijo.getNombres(), hijo.getIdentificador());
        }
    }

    /**
     * Método encargado de verificar si en una anterior vista se pasó
     * cinco datos con las llaves titulo, idenficador, estado, casa, bandera
     * como parámetros
     */
    private void verificarIntencion() {
        Intent oIntencion = getIntent();
        Bundle oBundle = oIntencion.getExtras();

        if (oBundle != null) {
            setTitle(oBundle.getString("titulo"));
            sIdentificador = oBundle.getString("identificador");
            bEstado = oBundle.getBoolean("estado");
            bCasa = oBundle.getBoolean("casa");
            bandera = oBundle.getBoolean("bandera");
        }
    }

    /**
     * Método encargado de inicializar los componentes
     * necesarios de la vista activity_ver_hijo_movilidad
     */
    private void inicializarVerHijoMovilidad() {
        verificarIntencion();

        lista = findViewById(R.id.listaHijoMovilidad);
        lista.setHasFixedSize(true);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        lista.setLayoutManager(linearLayoutManager);

        adaptadorHijo = new AdaptadorHijo(FirebaseUtilConsulta.listarAlumnosMovilidad(sIdentificador, bEstado, bCasa));

        adaptadorHijo.setOnClickListener(this);
        lista.setAdapter(adaptadorHijo);
    }

}
