package aplicacion.liberman.com.wasi.controlador;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import aplicacion.liberman.com.wasi.R;
import aplicacion.liberman.com.wasi.contenedor.Hijo;
import aplicacion.liberman.com.wasi.soporte.Adaptador;

public class VerHijoApoderado extends AppCompatActivity implements View.OnClickListener{
    private RecyclerView lista;
    private boolean bandera = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver_hijo_apoderado);

        setTitle(R.string.cHijos);
        verificarVista();

        lista = (RecyclerView) findViewById(R.id.listaHijoApoderado);
        lista.setHasFixedSize(true);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        lista.setLayoutManager(linearLayoutManager);

        Adaptador adaptador = new Adaptador(Hijo.listarHijos());
        adaptador.setOnClickListener(this);
        lista.setAdapter(adaptador);

    }

    @Override
    public void onClick(View view) {
        if(bandera){
            Intent intent = new Intent(VerHijoApoderado.this, PermitirSalida.class);
            intent.putExtra("nombres", Hijo.listarHijos().get(lista.getChildAdapterPosition(view)).getNombre());
            intent.putExtra("apellidos", Hijo.listarHijos().get(lista.getChildAdapterPosition(view)).getApellido());
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
            System.out.println("boolean " + (boolean)bun.getBoolean("bandera"));
            bandera = (boolean)bun.getBoolean("bandera");
        }
    }
}
