package aplicacion.liberman.com.wasi.controlador;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import aplicacion.liberman.com.wasi.R;
import aplicacion.liberman.com.wasi.soporte.Mensaje;

public class Login extends AppCompatActivity implements View.OnClickListener{
    private EditText usuario;
    private EditText clave;
    private Button ingresar;
    private TextView labelLogin;
    private ImageView imagenLoginPerfil;
    private int perfil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        setTitle(R.string.cLogin);

        labelLogin = (TextView)findViewById(R.id.labelPerfil);
        imagenLoginPerfil = (ImageView)findViewById(R.id.imagenLoginPerfil);

        usuario = (EditText)findViewById(R.id.textoUsuario);
        clave = (EditText)findViewById(R.id.textoClave);

        ingresar = (Button)findViewById(R.id.botonIngresar);
        ingresar.setOnClickListener(this);

        tipoPerfil();
    }

    @Override
    public void onClick(View view) {
        Intent intent = null;
        switch (view.getId()){
            case R.id.botonIngresar : verificar(intent);
        }
    }

    /**
     * Método que se encargará de dirigir a una diferente vista
     * dependiendo del perfil del usuario
     * @param intent
     */
    private void verificar(Intent intent){
        switch (perfil){
            case 1 : verificarUsuario(intent, 1);
                break;
            case 2 : verificarUsuario(intent, 2);
                break;
            case 4 : verificarUsuario(intent, 4);
                break;
        }
    }

    /**
     * Recibe el usuario y contraseña y comprueba si la persona se
     * encuentra registrada
     */
    private void verificarUsuario(Intent intent, int tipoPerfil){
        String usuario = this.usuario.getText().toString();
        String clave = this.clave.getText().toString();
        switch (tipoPerfil){
            case 1 : if(usuario.equals("padre") && clave.equals("padre")){
                intent = new Intent(Login.this, Apoderado.class);
                Toast.makeText(Login.this, Mensaje.mensajeUsuarioCorrecto, Toast.LENGTH_SHORT).show();
                startActivity(intent);
                finish();
            }
            else{
                Toast.makeText(Login.this, Mensaje.mensajeUsuarioIncorrecto, Toast.LENGTH_SHORT).show();
            }
                break;
            case 2 : if(usuario.equals("movilidad") && clave.equals("movilidad")){
                intent = new Intent(Login.this, Movilidad.class);
                Toast.makeText(Login.this, Mensaje.mensajeUsuarioCorrecto, Toast.LENGTH_SHORT).show();
                startActivity(intent);
                finish();
            }
            else{
                Toast.makeText(Login.this, Mensaje.mensajeUsuarioIncorrecto, Toast.LENGTH_SHORT).show();
            }
                break;
            case 4 : if(usuario.equals("profesor") && clave.equals("profesor")){
                intent = new Intent(Login.this, Profesor.class);
                Toast.makeText(Login.this, Mensaje.mensajeUsuarioCorrecto, Toast.LENGTH_SHORT).show();
                startActivity(intent);
                finish();
            }
            else{
                Toast.makeText(Login.this, Mensaje.mensajeUsuarioIncorrecto, Toast.LENGTH_SHORT).show();
            }
                break;
        }

    }

    /**
     * Recibe el dato del tipo de perfil que esta intentando acceder la persona
     * que usa la aplicación
     */
    private void tipoPerfil(){
        Intent inten = getIntent();
        Bundle bun = inten.getExtras();

        if(bun != null){
            perfil = (int)bun.get("perfil");

            switch (perfil){
                case 1 : labelLogin.setText("Apoderado");
                    imagenLoginPerfil.setBackground(getApplicationContext().getDrawable(R.drawable.perfil_apoderado));
                    break;
                case 2 : labelLogin.setText("Movilidad");
                    imagenLoginPerfil.setBackground(getApplicationContext().getDrawable(R.drawable.perfil_movilidad));
                    break;
                case 3 : labelLogin.setText("Recogedor");
                    imagenLoginPerfil.setBackground(getApplicationContext().getDrawable(R.drawable.perfil_recogedor));
                    break;
                case 4 : labelLogin.setText("Profesor");
                    imagenLoginPerfil.setBackground(getApplicationContext().getDrawable(R.drawable.perfil_profesor));
                    break;
            }
        }
    }
}
