package aplicacion.liberman.com.wasi.controlador;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.login_firebase_celular, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.login_celular) {
            AlertDialog.Builder builder = new AlertDialog.Builder(LoginFirebase.this);

            LayoutInflater inflater = this.getLayoutInflater();

            builder.setView(inflater.inflate(R.layout.login_celular_firebase, null))
                    // Add action buttons
                    .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int id) {
                            // sign in the user ...
                        }
                    })
                    .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int id) {
                            dialogInterface.cancel();
                        }
            });

            AlertDialog alertDialog = builder.create();
            alertDialog.show();
        }

        return super.onOptionsItemSelected(item);
    }

}
