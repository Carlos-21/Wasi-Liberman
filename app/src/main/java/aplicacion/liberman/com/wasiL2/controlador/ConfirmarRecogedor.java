package aplicacion.liberman.com.wasiL2.controlador;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import aplicacion.liberman.com.wasiL2.R;
import aplicacion.liberman.com.wasiL2.soporte.Generador;
import aplicacion.liberman.com.wasiL2.soporte.Validar;
import aplicacion.liberman.com.wasiL2.util.FirebaseUtilAutorizacion;
import aplicacion.liberman.com.wasiL2.util.FirebaseUtilEscritura;

public class ConfirmarRecogedor extends AppCompatActivity implements View.OnClickListener {
    private Button confirmarRecogedor;
    private Button generarDatos;
    private EditText textoUsuarioRecogedor;
    private EditText textoContraseñaRecogedor;
    private EditText textoTelefonoRecogedor;
    private String identificador;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirmar_recogedor);

        inicializarConfirmarRecogedor();
        verificarIntencion();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.botonConfirmarRecogedor:
                if (Validar.validarRegistroRecogedor(textoUsuarioRecogedor, textoContraseñaRecogedor, textoTelefonoRecogedor)) {
                    String sTelefono = textoTelefonoRecogedor.getText().toString();
                    String sUsuario = textoUsuarioRecogedor.getText().toString();
                    String sClave = textoContraseñaRecogedor.getText().toString();
                    FirebaseUtilAutorizacion.verificarTelefonoc(ConfirmarRecogedor.this, sTelefono, sUsuario, sClave);
                    FirebaseUtilEscritura.registrarRecogedor(sUsuario, sClave, identificador);
                }
                break;
            case R.id.botonGenerarDatos:
                generarDatos();
                break;
        }
    }

    private void inicializarConfirmarRecogedor() {
        textoUsuarioRecogedor = findViewById(R.id.textoUsuarioRecogedor);
        textoContraseñaRecogedor = findViewById(R.id.textoClaveRecogedor);
        textoTelefonoRecogedor = findViewById(R.id.textoTelefonoRecogedor);
        confirmarRecogedor = findViewById(R.id.botonConfirmarRecogedor);
        confirmarRecogedor.setOnClickListener(this);
        generarDatos = findViewById(R.id.botonGenerarDatos);
        generarDatos.setOnClickListener(this);
    }


    private void generarDatos() {
        textoUsuarioRecogedor.setText(Generador.getUsuario());
        textoContraseñaRecogedor.setText(Generador.getClave());
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Intent intent = new Intent(ConfirmarRecogedor.this, Apoderado.class);
                intent.putExtra("identificador", identificador);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void verificarIntencion() {
        Intent oIntencion = getIntent();
        Bundle oBundle = oIntencion.getExtras();

        if (oBundle != null) {
            identificador = oBundle.getString("identificador");
        }
    }

}
