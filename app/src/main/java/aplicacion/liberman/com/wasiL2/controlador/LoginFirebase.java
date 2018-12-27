package aplicacion.liberman.com.wasiL2.controlador;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import aplicacion.liberman.com.wasiL2.R;
import aplicacion.liberman.com.wasiL2.util.AlertaDialogoUtil;
import aplicacion.liberman.com.wasiL2.util.FirebaseUtilAutorizacion;

public class LoginFirebase extends AppCompatActivity implements View.OnClickListener {
    private EditText oTextoUsuario;
    private EditText oTextoClave;
    private Button oBotonIngresar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_firebase);

        inicializarLoginFirebase();
    }

    private void inicializarLoginFirebase() {
        oTextoUsuario = findViewById(R.id.oTextoUsuarioLogin1);
        oTextoClave = findViewById(R.id.oTextoCLaveLogin1);
        oBotonIngresar = findViewById(R.id.oBotonIngresarLogin1);
        oBotonIngresar.setOnClickListener(this);
    }

    @Override
    public void onStart() {
        super.onStart();
        FirebaseAuth oAutentificar = FirebaseAuth.getInstance();

        FirebaseUser oUsuarioFirebase = oAutentificar.getCurrentUser();
        if (oUsuarioFirebase != null) {
            Intent oIntencion = new Intent(LoginFirebase.this, Perfil.class);
            oIntencion.putExtra("bRecogedor", false);
            startActivity(oIntencion);
            finish();
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.oBotonIngresarLogin1:
                FirebaseUtilAutorizacion.autentificarPorCorreo(LoginFirebase.this, oTextoUsuario, oTextoClave);
                break;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.login_firebase_celular, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem oItem) {
        int nIdentificador = oItem.getItemId();

        if (nIdentificador == R.id.login_celular) {
            AlertaDialogoUtil.dialogoAlertaFirebaseCelular(LoginFirebase.this);
        }

        return super.onOptionsItemSelected(oItem);
    }

}
