package aplicacion.liberman.com.wasi.controlador;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.gson.Gson;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import aplicacion.liberman.com.wasi.R;
import aplicacion.liberman.com.wasi.contenedor.Usuario;
import aplicacion.liberman.com.wasi.data.Direccion;
import aplicacion.liberman.com.wasi.data.VolleySingleton;
import aplicacion.liberman.com.wasi.soporte.Mensaje;

public class Login extends AppCompatActivity implements View.OnClickListener{
    private EditText usuario;
    private EditText clave;
    private Button ingresar;
    private TextView labelLogin;
    private ImageView imagenLoginPerfil;
    private int perfil;
    private Usuario[]listaUsuario;
    private Gson gson = new Gson();

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

        llamarServicioRest();

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

        Usuario usuarioWasi = new Usuario(usuario, clave);
        switch (tipoPerfil){
            case 1 : if(Usuario.existeUsuario(listaUsuario, usuarioWasi)){
                intent = new Intent(Login.this, Apoderado.class);
                Toast.makeText(Login.this, Mensaje.mensajeUsuarioCorrecto, Toast.LENGTH_SHORT).show();
                startActivity(intent);
                finish();
            }
            else{
                Toast.makeText(Login.this, Mensaje.mensajeUsuarioIncorrecto, Toast.LENGTH_SHORT).show();
            }
                break;
            case 2 : if(Usuario.existeUsuario(listaUsuario, usuarioWasi)){
                intent = new Intent(Login.this, Movilidad.class);
                Toast.makeText(Login.this, Mensaje.mensajeUsuarioCorrecto, Toast.LENGTH_SHORT).show();
                startActivity(intent);
                finish();
            }
            else{
                Toast.makeText(Login.this, Mensaje.mensajeUsuarioIncorrecto, Toast.LENGTH_SHORT).show();
            }
                break;
            case 4 : if(Usuario.existeUsuario(listaUsuario, usuarioWasi)){
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

    /**
     * Carga el adaptador con las metas obtenidas
     * en la respuesta
     */
    public void llamarServicioRest() {
        String newURL = Direccion.GET + "?tipoPerfil=" + perfil;
        // Petición GET
        VolleySingleton.
                getInstance(Login.this).
                addToRequestQueue(
                        new JsonObjectRequest(
                                Request.Method.GET,
                                newURL,
                                null,
                                new Response.Listener<JSONObject>() {

                                    @Override
                                    public void onResponse(JSONObject response) {
                                        // Procesar la respuesta Json
                                        procesarRespuesta(response);
                                    }
                                },
                                new Response.ErrorListener() {
                                    @Override
                                    public void onErrorResponse(VolleyError error) {
                                        Log.d("TAG-Volley-Error", "Error Volley: " + error.getMessage());
                                    }
                                }

                        )
                );
    }

    /**
     * Interpreta los resultados de la respuesta y así
     * realizar las operaciones correspondientes
     *
     * @param response Objeto Json con la respuesta
     */
    private void procesarRespuesta(JSONObject response) {
        try {
            // Obtener atributo "estado"
            String estado = response.getString("estado");
            Log.d("TAG-ARRAY1", estado);
            switch (estado) {
                case "1": // EXITO
                    // Obtener array "metas" Json
                    JSONArray mensaje = response.getJSONArray("usuarios");
                    // Parsear con Gson
                    listaUsuario = gson.fromJson(mensaje.toString(), Usuario[].class);
                    Log.d("TAG-ARRAY2", "Error Volley: " + listaUsuario.length);
                    break;
                case "2": // FALLIDO
                    String mensaje2 = response.getString("mensaje");
                    Toast.makeText(
                            Login.this,
                            mensaje2,
                            Toast.LENGTH_LONG).show();
                    break;
            }

        } catch (JSONException e) {
            Log.d("TAG-Volley-Error-52", "Error Volley: ");
        }

    }

}
