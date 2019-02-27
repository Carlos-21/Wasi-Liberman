package aplicacion.liberman.com.wasiL2.controlador;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import aplicacion.liberman.com.wasiL2.R;
import aplicacion.liberman.com.wasiL2.util.AlertaDialogoUtil;
import aplicacion.liberman.com.wasiL2.util.FirebaseUtilAutorizacion;

public class Recogedor extends AppCompatActivity implements View.OnClickListener {
    private ImageView permitirSalidaRecogedor;
    private ImageView cerrarSessionRecogedor;
    private String identificador;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recogedor);

        verificarIntencion();

        inicializarRecogedor();
    }

    @Override
    public void onClick(View view) {
        Intent intent = null;
        switch (view.getId()) {
            case R.id.permitirSalidaRecogedor:
                intent = new Intent(Recogedor.this, VerHijoRecogedor.class);
                intent.putExtra("identificador", identificador);
                startActivity(intent);
                break;
            case R.id.cerrarSesionRecogedor:
                AlertaDialogoUtil.cerrarSesion(Recogedor.this);
        }
    }

    @Override
    public void onBackPressed() {
        AlertaDialogoUtil.cerrarSesion(Recogedor.this);
    }

    /**
     * Método encargado de inicializar los componentes
     * necesarios de la vista activity_recogedor
     */
    private void inicializarRecogedor() {
        permitirSalidaRecogedor = findViewById(R.id.permitirSalidaRecogedor);
        permitirSalidaRecogedor.setOnClickListener(this);
        cerrarSessionRecogedor = findViewById(R.id.cerrarSesionRecogedor);
        cerrarSessionRecogedor.setOnClickListener(this);

        FirebaseUtilAutorizacion.cerrarSesionRecogedor(Recogedor.this);
    }

    /**
     * Método encargado de verificar si en una anterior vista se pasó
     * un dato con la llave identificador como parámetro
     */
    private void verificarIntencion() {
        Intent inten = getIntent();
        Bundle bun = inten.getExtras();

        if (bun != null) {
            identificador = bun.getString("identificador");
        }
    }

}
