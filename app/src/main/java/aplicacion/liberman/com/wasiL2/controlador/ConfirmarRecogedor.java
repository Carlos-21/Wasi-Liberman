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
import aplicacion.liberman.com.wasiL2.util.AlertaDialogoUtil;

public class ConfirmarRecogedor extends AppCompatActivity implements View.OnClickListener {
    private Button confirmarRecogedor;
    private Button generarDatos;
    private EditText textoUsuarioRecogedor;
    private EditText textoContraseñaRecogedor;
    private EditText textoTelefonoRecogedor;
    private String sIdentificador;

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
                    String sUsuario = textoUsuarioRecogedor.getText().toString() + "@gmail.com";
                    String sClave = textoContraseñaRecogedor.getText().toString();
                    AlertaDialogoUtil.dialogoAlertaConfirmarRecogedor(ConfirmarRecogedor.this, sTelefono,
                            sUsuario, sClave, sIdentificador);
                }
                break;
            case R.id.botonGenerarDatos:
                generarDatos();
                break;
        }
    }

    /**
     * Método encargado de inicializar los componentes
     * necesarios de la vista activity_confirmar_recogedor
     */
    private void inicializarConfirmarRecogedor() {
        setTitle("Asignación");

        textoUsuarioRecogedor = findViewById(R.id.textoUsuarioRecogedor);
        textoContraseñaRecogedor = findViewById(R.id.textoClaveRecogedor);
        textoTelefonoRecogedor = findViewById(R.id.textoTelefonoRecogedor);
        confirmarRecogedor = findViewById(R.id.botonConfirmarRecogedor);
        confirmarRecogedor.setOnClickListener(this);
        generarDatos = findViewById(R.id.botonGenerarDatos);
        generarDatos.setOnClickListener(this);
    }

    /**
     * Método encargado de generar datos aleatorios para el campo
     * de texto de usuario y el campo de texto de contraseña
     */
    private void generarDatos() {
        textoUsuarioRecogedor.setText(Generador.getUsuario());
        textoContraseñaRecogedor.setText(Generador.getClave());
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Intent intent = new Intent(ConfirmarRecogedor.this, Apoderado.class);
                intent.putExtra("identificador", sIdentificador);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(ConfirmarRecogedor.this, Apoderado.class);
        intent.putExtra("identificador", sIdentificador);
        startActivity(intent);
    }

    /**
     * Método encargado de verificar si en una anterior vista se pasó
     * un dato con la llave identificador como parámetro
     */
    private void verificarIntencion() {
        Intent oIntencion = getIntent();
        Bundle oBundle = oIntencion.getExtras();

        if (oBundle != null) {
            sIdentificador = oBundle.getString("identificador");
        }
    }

}
