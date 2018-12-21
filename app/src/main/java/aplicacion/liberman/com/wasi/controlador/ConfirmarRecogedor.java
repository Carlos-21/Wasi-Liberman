package aplicacion.liberman.com.wasi.controlador;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.FirebaseException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

import aplicacion.liberman.com.wasi.R;
import aplicacion.liberman.com.wasi.soporte.Generador;
import aplicacion.liberman.com.wasi.soporte.Mensaje;
import aplicacion.liberman.com.wasi.soporte.Validar;
import aplicacion.liberman.com.wasi.util.FirebaseUtil;
import aplicacion.liberman.com.wasi.util.FirebaseUtilEscritura;
import aplicacion.liberman.com.wasi.util.MensajeTexto;

public class ConfirmarRecogedor extends AppCompatActivity implements View.OnClickListener{
    private Button confirmarRecogedor;
    private Button generarDatos;
    private EditText textoUsuarioRecogedor;
    private EditText textoContraseñaRecogedor;
    private EditText textoTelefonoRecogedor;
    private String identificador;
    private FirebaseAuth autentificador = FirebaseAuth.getInstance();
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirmar_recogedor);

        textoUsuarioRecogedor = (EditText)findViewById(R.id.textoUsuarioRecogedor);
        textoContraseñaRecogedor = (EditText)findViewById(R.id.textoClaveRecogedor);
        textoTelefonoRecogedor = (EditText)findViewById(R.id.textoTelefonoRecogedor);
        confirmarRecogedor = (Button)findViewById(R.id.botonConfirmarRecogedor);
        confirmarRecogedor.setOnClickListener(this);
        generarDatos = (Button)findViewById(R.id.botonGenerarDatos);
        generarDatos.setOnClickListener(this);
        inicializar();

        verificarVista();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.botonConfirmarRecogedor : if(Validar.validarRegistroRecogedor(textoUsuarioRecogedor, textoContraseñaRecogedor, textoTelefonoRecogedor)){
                                                    enviarCredencialesTelefono(textoTelefonoRecogedor.getText().toString(),
                                                            textoUsuarioRecogedor.getText().toString(), textoContraseñaRecogedor.getText().toString());
                                                    FirebaseUtilEscritura.registrarRecogedor(textoUsuarioRecogedor.getText().toString(), textoContraseñaRecogedor.getText().toString(), identificador);
                                                }
                                                break;
            case R.id.botonGenerarDatos : generarDatos();
                                          break;
        }
    }

    /**
     * Este método se encargará de captar la respuesta del padre,
     * para poder confirmar de que desea asignar a una persona
     * de que pueda recoger a su hijo
     */
    private void mensajeConfirmarRecogedor(){
        AlertDialog.Builder builder = new AlertDialog.Builder(ConfirmarRecogedor.this);
        builder.setTitle(Mensaje.tituloAsignarRecogedor);
        builder.setMessage(Mensaje.mensajeAsignarRecogedor);
        builder.setCancelable(true);
        builder.setIcon(R.drawable.informacion);

        builder.setPositiveButton("Si", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                String usuario = textoUsuarioRecogedor.getText().toString();
                String clave = textoContraseñaRecogedor.getText().toString();
                String telefono = textoTelefonoRecogedor.getText().toString();
                /*String mensaje = Mensaje.mensajeTextoRecogeor.replace("paramU", usuario).replace("paramC", clave);
                enviarMensaje(telefono, mensaje);*/
                enviarCredencialesTelefono(telefono, usuario, clave);
                /*Intent intent = new Intent(ConfirmarRecogedor.this, Apoderado.class);
                startActivity(intent);
                finish();*/
            }
        });

        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                //No se hace nada
                dialogInterface.cancel();
            }
        });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    private void inicializar(){
        int permissionCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS);

        if(permissionCheck != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.SEND_SMS}, 255);
        }
        else{
        }

    }

    private void enviarMensaje(String sNumero, String sMensaje){
        try {
            SmsManager sms = SmsManager.getDefault();
            sms.sendTextMessage(sNumero,null,sMensaje,null,null);
            Toast.makeText(getApplicationContext(), "Mensaje Enviado.", Toast.LENGTH_LONG).show();
        }
        catch (Exception e) {
            Toast.makeText(getApplicationContext(), "Mensaje no enviado, datos incorrectos.", Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
    }

    private void generarDatos(){
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

    private void startPhoneNumberVerification(String phoneNumber) {
        // [START start_phone_auth]
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                phoneNumber,        // Phone number to verify
                60,                 // Timeout duration
                TimeUnit.SECONDS,   // Unit of timeout
                this,               // Activity (for callback binding)
                null);        // OnVerificationStateChangedCallbacks
        // [END start_phone_auth]

    }

    private void enviarCredencialesTelefono(final String numeroTelefono, final String usuario, final String clave){
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if(currentUser!=null){
            Log.d("Nulo", "Diferente de nulo");
        }
        Log.d("Dato", "Numero teleono : "+ numeroTelefono);
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                numeroTelefono, 60, TimeUnit.SECONDS, this, new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                    @Override
                    public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {
                        Toast.makeText(ConfirmarRecogedor.this.getApplicationContext(), "Correcto", Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onVerificationFailed(FirebaseException e) {
                        Toast.makeText(ConfirmarRecogedor.this.getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onCodeSent(String idSeguridad, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                        super.onCodeSent(idSeguridad, forceResendingToken);
                        Toast.makeText(ConfirmarRecogedor.this.getApplicationContext(), "Ingreso", Toast.LENGTH_LONG).show();
                        String mensaje = Mensaje.mensajeTextoRecogeor.replace("paramI", idSeguridad)
                                .replace("paramU", usuario)
                                .replace("paramC", clave);
                        enviarSMS(numeroTelefono, mensaje);
                    }
                }
        );
    }

    private  void enviarSMS(String sNumero, String sMensaje){
        try {
            SmsManager sms = SmsManager.getDefault();
            sms.sendTextMessage(sNumero,null,sMensaje,null,null);
            Toast.makeText(this.getApplicationContext(), "Mensaje Enviado.", Toast.LENGTH_LONG).show();
        }
        catch (Exception e) {
            Toast.makeText(this.getApplicationContext(), "Mensaje no enviado, datos incorrectos.", Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
    }

}
