package aplicacion.liberman.com.wasi.controlador;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import aplicacion.liberman.com.wasi.R;
import aplicacion.liberman.com.wasi.soporte.Validar;

public class LoginFirebase extends AppCompatActivity implements View.OnClickListener {
    private EditText oTextoUsuario;
    private EditText oTextoClave;
    private Button oBotonIngresar;
    private FirebaseAuth oAutentificacion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_firebase);

        inicializarLoginFirebase();
    }

    private void inicializarLoginFirebase(){
        // Initialize Firebase Auth
        oAutentificacion = FirebaseAuth.getInstance();

        oTextoUsuario = (EditText)findViewById(R.id.oTextoUsuarioLogin1);
        oTextoClave = (EditText)findViewById(R.id.oTextoCLaveLogin1);
        oBotonIngresar = (Button)findViewById(R.id.oBotonIngresarLogin1);
        oBotonIngresar.setOnClickListener(this);
    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = oAutentificacion.getCurrentUser();
        if(currentUser!=null){
            Intent intent = new Intent(LoginFirebase.this,Perfil.class);
            startActivity(intent);
            finish();
        }
    }


    private void signIn(String email, String password) {
        if (!Validar.validarLoginFirebase(oTextoUsuario, oTextoClave)) {
            return;
        }


        // [START sign_in_with_email]
        oAutentificacion.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            //FirebaseUser user = oAutentificacion.getCurrentUser();
                            Toast.makeText(LoginFirebase.this, "Authentication correct.",
                                    Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(LoginFirebase.this,Perfil.class);
                            startActivity(intent);
                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(LoginFirebase.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }

                        // [START_EXCLUDE]
                        if (!task.isSuccessful()) {

                        }
                        // [END_EXCLUDE]
                    }
                });
        // [END sign_in_with_email]
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.oBotonIngresarLogin1 : signIn(oTextoUsuario.getText().toString(), oTextoClave.getText().toString());
                                             break;
        }
    }
}
