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

import java.util.ArrayList;

import aplicacion.liberman.com.wasi.R;
import aplicacion.liberman.com.wasi.componente.UsuarioDAO;
import aplicacion.liberman.com.wasi.contenedor.Usuario;
import aplicacion.liberman.com.wasi.data.Direccion;
import aplicacion.liberman.com.wasi.data.VolleySingleton;
import aplicacion.liberman.com.wasi.soporte.Buscar;
import aplicacion.liberman.com.wasi.soporte.Mensaje;

public class Login extends AppCompatActivity implements View.OnClickListener{
    private EditText oTextoUsuario;
    private EditText oTextoClave;
    private Button oBotonIngresar;
    private TextView oLabelLogin;
    private ImageView oImagenLogin;
    private int iPerfil;
    private ArrayList<Usuario> aListaUsuarios;
    private Usuario[]listaUsuario; //revisar arreglo
    private Gson gson = new Gson(); //revisar variable

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        inicializarLogin();
    }

    private void inicializarLogin(){
        setTitle(R.string.sLogin);
        aListaUsuarios = new ArrayList<>();
                
        oLabelLogin = (TextView)findViewById(R.id.oLabelLogin2);
        oImagenLogin = (ImageView)findViewById(R.id.oImagenLogin2);

        oTextoUsuario = (EditText)findViewById(R.id.oTextoUsuarioLogin2);
        oTextoClave = (EditText)findViewById(R.id.oTextoCLaveLogin2);

        oBotonIngresar = (Button)findViewById(R.id.oBotonIngresarLogin2);
        oBotonIngresar.setOnClickListener(this);

        tipoPerfil();

        //llamarServicioRest();
        System.out.println("Perfil : "+iPerfil);
        UsuarioDAO.listarUsuarios(aListaUsuarios, iPerfil);
    }

    @Override
    public void onClick(View view) {
        Intent intent = null;
        switch (view.getId()){
            case R.id.oBotonIngresarLogin2 : verificar(intent);
        }
    }

    /**
     * Método que se encargará de dirigir a una diferente vista
     * dependiendo del iPerfil del oTextoUsuario
     * @param intent
     */
    private void verificar(Intent intent){
        switch (iPerfil){
            case 1 : verificarUsuario(intent, 1);
                break;
            case 2 : verificarUsuario(intent, 2);
                break;
            case 4 : verificarUsuario(intent, 4);
                break;
        }
    }

    /**
     * Recibe el oTextoUsuario y contraseña y comprueba si la persona se
     * encuentra registrada
     */
    private void verificarUsuario(Intent intent, int tipoPerfil){
        String oTextoUsuario = this.oTextoUsuario.getText().toString();
        String oTextoClave = this.oTextoClave.getText().toString();

        Usuario oUsuarioWasi = new Usuario();
        oUsuarioWasi.setNombre(oTextoUsuario);
        oUsuarioWasi.setClave(oTextoClave);
        switch (tipoPerfil){
            case 1 : if(Buscar.existeUsuario(aListaUsuarios, oUsuarioWasi)){
                intent = new Intent(Login.this, Apoderado.class);
                intent.putExtra("identificador",oUsuarioWasi.getIdentificador());
                Toast.makeText(Login.this, Mensaje.mensajeUsuarioCorrecto, Toast.LENGTH_SHORT).show();
                startActivity(intent);
                finish();
            }
            else{
                Toast.makeText(Login.this, Mensaje.mensajeUsuarioIncorrecto, Toast.LENGTH_SHORT).show();
            }
                break;
            case 2 : if(Buscar.existeUsuario(aListaUsuarios, oUsuarioWasi)){
                intent = new Intent(Login.this, Movilidad.class);
                intent.putExtra("identificador",oUsuarioWasi.getIdentificador());
                Toast.makeText(Login.this, Mensaje.mensajeUsuarioCorrecto, Toast.LENGTH_SHORT).show();
                startActivity(intent);
                finish();
            }
            else{
                Toast.makeText(Login.this, Mensaje.mensajeUsuarioIncorrecto, Toast.LENGTH_SHORT).show();
            }
                break;
            case 4 : if(Buscar.existeUsuario(aListaUsuarios, oUsuarioWasi)){
                intent = new Intent(Login.this, Profesor.class);
                intent.putExtra("identificador",oUsuarioWasi.getIdentificador());
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
     * Recibe el dato del tipo de iPerfil que esta intentando acceder la persona
     * que usa la aplicación
     */
    private void tipoPerfil(){
        Intent inten = getIntent();
        Bundle bun = inten.getExtras();

        if(bun != null){
            iPerfil = (int)bun.get("perfil");

            switch (iPerfil){
                case 1 : oLabelLogin.setText("Apoderado");
                    oImagenLogin.setBackground(getApplicationContext().getDrawable(R.drawable.perfil_apoderado));
                    break;
                case 2 : oLabelLogin.setText("Movilidad");
                    oImagenLogin.setBackground(getApplicationContext().getDrawable(R.drawable.perfil_movilidad));
                    break;
                case 3 : oLabelLogin.setText("Recogedor");
                    oImagenLogin.setBackground(getApplicationContext().getDrawable(R.drawable.perfil_recogedor));
                    break;
                case 4 : oLabelLogin.setText("Profesor");
                    oImagenLogin.setBackground(getApplicationContext().getDrawable(R.drawable.perfil_profesor));
                    break;
            }
        }
    }

    //Verificar los siguiente metodos, para servicios REST
    /**
     * Carga el adaptador con las metas obtenidas
     * en la respuesta
     */
    public void llamarServicioRest() {
        String newURL = Direccion.GET + "?tipoPerfil=" + iPerfil;
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
                    JSONArray mensaje = response.getJSONArray("oTextoUsuarios");
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
