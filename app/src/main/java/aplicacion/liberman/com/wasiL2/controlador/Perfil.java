package aplicacion.liberman.com.wasiL2.controlador;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import aplicacion.liberman.com.wasiL2.R;

public class Perfil extends AppCompatActivity implements View.OnClickListener{
    private ImageView perfilApoderado;
    private ImageView perfilRecogedor;
    private ImageView perfilProfesor;
    private ImageView perfilMovilidad;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);

        perfilApoderado = (ImageView)findViewById(R.id.oImagenPerfilApoderado);
        perfilApoderado.setOnClickListener(this);
        perfilRecogedor = (ImageView)findViewById(R.id.oImagenPerfilRecogedor);
        perfilRecogedor.setOnClickListener(this);
        perfilProfesor = (ImageView)findViewById(R.id.oImagenPerfilProfesor);
        perfilProfesor.setOnClickListener(this);
        perfilMovilidad = (ImageView)findViewById(R.id.oImagenPerfilMovilidad);
        perfilMovilidad.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        Intent intent = null;
        switch(view.getId()){
            case R.id.oImagenPerfilApoderado :  intent = new Intent(Perfil.this, Login.class);
                intent.putExtra("perfil", 1);
                break;
            case R.id.oImagenPerfilMovilidad :  intent = new Intent(Perfil.this, Login.class);
                intent.putExtra("perfil", 2);
                break;
            case R.id.oImagenPerfilRecogedor :  intent = new Intent(Perfil.this, Login.class);
                intent.putExtra("perfil", 3);
                break;
            case R.id.oImagenPerfilProfesor :  intent = new Intent(Perfil.this, Login.class);
                intent.putExtra("perfil", 4);
                break;
        }
        startActivity(intent);
    }
}
