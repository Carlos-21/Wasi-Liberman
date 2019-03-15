package aplicacion.liberman.com.wasiL2.controlador;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import aplicacion.liberman.com.wasiL2.R;
import aplicacion.liberman.com.wasiL2.util.AlertaDialogoUtil;

public class ConfiguracionUsuario extends AppCompatActivity implements View.OnClickListener {
    private ImageView oCambioUsuario;
    private ImageView oCambioContraseña;
    private String sIdentificador;
    private int iPerfil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configuracion_usuario);

        inicializarConfiguracionUsuario();
        verificarIntencion();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.cambiarUsuario:
                AlertaDialogoUtil.cambiarUsuario(this, iPerfil);
                break;
            case R.id.cambiarClave:
                AlertaDialogoUtil.cambiarClave(this);
                break;
        }
    }

    @Override
    public void onBackPressed() {
        Intent intent = null;
        switch (iPerfil) {
            case 1:
                intent = new Intent(ConfiguracionUsuario.this, Apoderado.class);
                intent.putExtra("identificador", sIdentificador);
                startActivity(intent);
                break;
            case 2:
                intent = new Intent(ConfiguracionUsuario.this, Movilidad.class);
                intent.putExtra("identificador", sIdentificador);
                startActivity(intent);
                break;
            case 4:
                intent = new Intent(ConfiguracionUsuario.this, Profesor.class);
                intent.putExtra("identificador", sIdentificador);
                startActivity(intent);
                break;
        }
    }

    /**
     * Método encargado de inicializar los componentes
     * necesarios de la vista activity_configuracion_usuario
     */
    private void inicializarConfiguracionUsuario() {
        oCambioUsuario = (ImageView) findViewById(R.id.cambiarUsuario);
        oCambioUsuario.setOnClickListener(this);
        oCambioContraseña = (ImageView) findViewById(R.id.cambiarClave);
        oCambioContraseña.setOnClickListener(this);
    }

    /**
     * Método encargado de verificar si en una anterior vista se pasó
     * un dato con la llave perfil como parámetro
     */
    private void verificarIntencion() {
        Intent inten = getIntent();
        Bundle bun = inten.getExtras();

        if (bun != null) {
            sIdentificador = bun.getString("identificador");
            iPerfil = bun.getInt("perfil");
        }
    }

}
