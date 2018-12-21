package aplicacion.liberman.com.wasi.controlador;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import aplicacion.liberman.com.wasi.R;
import aplicacion.liberman.com.wasi.util.AlertaDialogoUtil;
import aplicacion.liberman.com.wasi.util.FirebaseUtilAutorizacion;

public class Recogedor extends AppCompatActivity implements View.OnClickListener{
    private ImageView permitirSalidaRecogedor;
    private ImageView cerrarSessionRecogedor;
    private String identificador;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recogedor);

        verificarVista();

        permitirSalidaRecogedor = (ImageView)findViewById(R.id.permitirSalidaRecogedor);
        permitirSalidaRecogedor.setOnClickListener(this);
        cerrarSessionRecogedor = (ImageView)findViewById(R.id.cerrarSesionRecogedor);
        cerrarSessionRecogedor.setOnClickListener(this);

        FirebaseUtilAutorizacion.cerrarSesionRecogedor(Recogedor.this);
    }

    @Override
    public void onClick(View view) {
        Intent intent = null;
        switch (view.getId()){
            case R.id.permitirSalidaRecogedor : intent = new Intent(Recogedor.this, VerHijoRecogedor.class);
                intent.putExtra("identificador", identificador);
                startActivity(intent);
                break;
            case R.id.cerrarSesionRecogedor : AlertaDialogoUtil.cerrarSesion(null, null, Recogedor.this, null,3);
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

}
