package aplicacion.liberman.com.wasi.controlador;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import aplicacion.liberman.com.wasi.R;

public class Perfil extends AppCompatActivity implements View.OnClickListener{
    private ImageView perfilApoderado;
    private ImageView perfilRecogedor;
    private ImageView perfilProfesor;
    private ImageView perfilMovilidad;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);

        perfilApoderado = (ImageView)findViewById(R.id.perfilApoderado);
        perfilApoderado.setOnClickListener(this);
        perfilRecogedor = (ImageView)findViewById(R.id.perfilRecogedor);
        perfilRecogedor.setOnClickListener(this);
        perfilProfesor = (ImageView)findViewById(R.id.perfilProfesor);
        perfilProfesor.setOnClickListener(this);
        perfilMovilidad = (ImageView)findViewById(R.id.perfilMovilidad);
        perfilMovilidad.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        Intent intent = null;
        switch(view.getId()){
            case R.id.perfilApoderado :  intent = new Intent(Perfil.this, Login.class);
                intent.putExtra("perfil", 1);
                break;
            case R.id.perfilMovilidad :  intent = new Intent(Perfil.this, Login.class);
                intent.putExtra("perfil", 2);
                break;
            case R.id.perfilRecogedor :  intent = new Intent(Perfil.this, Login.class);
                intent.putExtra("perfil", 3);
                break;
            case R.id.perfilProfesor :  intent = new Intent(Perfil.this, Login.class);
                intent.putExtra("perfil", 4);
                break;
        }
        startActivity(intent);
    }
}
