package aplicacion.liberman.com.wasiL2.controlador;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import aplicacion.liberman.com.wasiL2.R;
import aplicacion.liberman.com.wasiL2.util.AlertaDialogoUtil;

public class PermitirSalida extends AppCompatActivity implements View.OnClickListener {
    private ImageView permitirSalida;
    private ImageView permitirMovilidad;
    private ImageView hijoSalida;
    private String nombresHijo;
    private String apellidosHijo;
    private String imagen;
    private String identificador;
    private String identificadorHijo;
    private String identificadorRecogedorApoderado;
    private int tipoPerfil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_permitir_salida);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        permitirSalida = findViewById(R.id.permitirSalida);
        permitirSalida.setOnClickListener(this);
        permitirMovilidad = findViewById(R.id.permitirMovilidad);
        permitirMovilidad.setOnClickListener(this);
        hijoSalida = findViewById(R.id.fotoHijoSalida);

        inicializarPermitirSalida();
        setTitle(apellidosHijo + " " + nombresHijo);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.permitirSalida:
                AlertaDialogoUtil.autorizarSalidaHijo(PermitirSalida.this, 1, imagen, identificador, nombresHijo, apellidosHijo, identificadorHijo, identificadorRecogedorApoderado, tipoPerfil);
                break;
            case R.id.permitirMovilidad:
                AlertaDialogoUtil.autorizarSalidaHijo(PermitirSalida.this, 2, imagen, identificador, nombresHijo, apellidosHijo, identificadorHijo, identificadorRecogedorApoderado, tipoPerfil);
                break;
        }
    }

    /**
     * Método encargado de inicializar los componentes
     * necesarios de la vista activity_permitir_salida
     */
    private void inicializarPermitirSalida() {
        Intent inten = getIntent();
        Bundle bun = inten.getExtras();

        if (bun != null) {
            nombresHijo = bun.getString("nombres");
            apellidosHijo = bun.getString("apellidos");
            imagen = bun.getString("imagen");
            identificador = bun.getString("identificador");
            identificadorHijo = bun.getString("identificadorHijo");
            Picasso.get().load(imagen).into(hijoSalida);
            tipoPerfil = bun.getInt("perfil");
            if (tipoPerfil == 3) {
                identificadorRecogedorApoderado = bun.getString("identificadorRecogedorApoderado");
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                if (tipoPerfil == 1) {
                    Intent intent = new Intent(PermitirSalida.this, VerHijoApoderado.class);
                    intent.putExtra("bandera", true);
                    intent.putExtra("identificador", identificador);
                    startActivity(intent);
                } else {
                    Intent intent = new Intent(PermitirSalida.this, VerHijoRecogedor.class);
                    intent.putExtra("identificador", identificador);
                    startActivity(intent);
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onBackPressed() {
        if (tipoPerfil == 1) {
            Intent intent = new Intent(PermitirSalida.this, VerHijoApoderado.class);
            intent.putExtra("bandera", true);
            intent.putExtra("identificador", identificador);
            startActivity(intent);
        } else {
            Intent intent = new Intent(PermitirSalida.this, VerHijoRecogedor.class);
            intent.putExtra("identificador", identificador);
            startActivity(intent);
        }
    }
}
