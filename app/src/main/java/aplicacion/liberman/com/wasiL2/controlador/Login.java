package aplicacion.liberman.com.wasiL2.controlador;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import aplicacion.liberman.com.wasiL2.R;
import aplicacion.liberman.com.wasiL2.contenedor.Usuario;
import aplicacion.liberman.com.wasiL2.soporte.Buscar;
import aplicacion.liberman.com.wasiL2.soporte.Mensaje;
import aplicacion.liberman.com.wasiL2.soporte.Validar;
import aplicacion.liberman.com.wasiL2.util.FirebaseUtilAutorizacion;
import aplicacion.liberman.com.wasiL2.util.FirebaseUtilConsulta;
import aplicacion.liberman.com.wasiL2.util.SharedPreferencesUtil;

public class Login extends AppCompatActivity implements View.OnClickListener {
    private EditText oTextoUsuario;
    private EditText oTextoClave;
    private Button oBotonIngresar;
    private TextView oLabelLogin;
    private ImageView oImagenLogin;
    private int iPerfil;
    private ArrayList<Usuario> aListaUsuarios;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        inicializarLogin();
    }

    /**
     * Método encargado de inicializar los componentes
     * necesarios de la vista activity_login
     */
    private void inicializarLogin() {
        setTitle(R.string.sLogin);
        aListaUsuarios = new ArrayList<>();

        oLabelLogin = findViewById(R.id.oLabelLogin2);
        oImagenLogin = findViewById(R.id.oImagenLogin2);

        oTextoUsuario = findViewById(R.id.oTextoUsuarioLogin2);
        oTextoClave = findViewById(R.id.oTextoCLaveLogin2);

        oBotonIngresar = findViewById(R.id.oBotonIngresarLogin2);
        oBotonIngresar.setOnClickListener(this);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            verificarIntencion();
        }

    }

    @Override
    public void onClick(View view) {
        verificarUsuario();
    }

    @Override
    public void onBackPressed() {
        Intent oIntent = new Intent(Login.this, Perfil.class);
        startActivity(oIntent);
    }

    /**
     * Recibe el oTextoUsuario y contraseña y comprueba si la persona se
     * encuentra registrada
     */
    private void verificarUsuario() {
        if (!Validar.validarLoginPerfil(oTextoUsuario, oTextoClave)) {
            return;
        }

        SharedPreferencesUtil.guardarUsuario(Login.this, oTextoUsuario.getText().toString(), oTextoClave.getText().toString());

        FirebaseUtilAutorizacion.autentificarPorCorreo(Login.this, oTextoUsuario, oTextoClave, iPerfil);
    }

    /**
     * Recibe el dato del tipo de iPerfil que esta intentando acceder la persona
     * que usa la aplicación
     */
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void verificarIntencion() {
        Intent inten = getIntent();
        Bundle bun = inten.getExtras();

        if (bun != null) {
            iPerfil = (int) bun.get("perfil");

            switch (iPerfil) {
                case 1:
                    oLabelLogin.setText("Apoderado");
                    oImagenLogin.setBackground(getApplicationContext().getDrawable(R.drawable.perfil_apoderado));
                    break;
                case 2:
                    oLabelLogin.setText("Movilidad");
                    oImagenLogin.setBackground(getApplicationContext().getDrawable(R.drawable.perfil_movilidad));
                    break;
                case 3:
                    oLabelLogin.setText("Recogedor");
                    oImagenLogin.setBackground(getApplicationContext().getDrawable(R.drawable.perfil_recogedor));
                    break;
                case 4:
                    oLabelLogin.setText("Profesor");
                    oImagenLogin.setBackground(getApplicationContext().getDrawable(R.drawable.perfil_profesor));
                    break;
            }
        }
    }
}
