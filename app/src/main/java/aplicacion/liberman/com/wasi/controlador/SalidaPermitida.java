package aplicacion.liberman.com.wasi.controlador;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.WriteBatch;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.Map;

import aplicacion.liberman.com.wasi.R;
import aplicacion.liberman.com.wasi.soporte.Fecha;
import aplicacion.liberman.com.wasi.soporte.Mensaje;

public class SalidaPermitida extends AppCompatActivity {
    private TextView mensajeSalida;
    private ImageView fotoHijoSalida;
    private String identificador;
    private String nombreHijo;
    private String identificadorHijo;
    private String imagenHijo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_salida_permitida);

        mensajeSalida = (TextView)findViewById(R.id.mensajeSalida);
        fotoHijoSalida = (ImageView)findViewById(R.id.fotoHijoSalida);
        mostrarSalida();
        registrarSalida();
    }

    /**
     * Definir documentación y definir método
     */
    private void mostrarSalida(){
        Intent inten = getIntent();
        Bundle bun = inten.getExtras();

        if(bun != null){
            int tipoSalida = (int)bun.getInt("salida");
            if(tipoSalida == 1){
                setTitle("Permitir Salida");
            }
            else{
                setTitle("Permitir Movilidad");
            }
            imagenHijo = (String)bun.getString("imagen");
            Picasso.get().load(imagenHijo).into(fotoHijoSalida);
            identificador = (String)bun.getString("identificador");
            nombreHijo = (String)bun.getString("nombres");
            identificadorHijo = (String)bun.getString("identificadorHijo");
        }

        Mensaje.hora = Fecha.horaActual();
        Mensaje.fecha = Fecha.fechaActual();

        mensajeSalida.setText(Mensaje.mensajeSalida.replace("paramH", Mensaje.hora).replace("paramD", Mensaje.fecha));
    }

    /**
     * Definir documentación
     */
    private void registrarSalida(){
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        Map<String, Object> data = new HashMap<>();
        data.put("hijo", nombreHijo);
        data.put("usuario", identificador);
        data.put("fecha", Mensaje.fecha);
        data.put("hora", Mensaje.hora);
        data.put("imagen", imagenHijo);

        db.collection("Usuarios").document(identificador).collection("Salidas")
                .add(data)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Toast.makeText(SalidaPermitida.this, Mensaje.mensajeSalidaPermitida, Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                    }
                });

        WriteBatch batch = db.batch();

        DocumentReference nycRef = db.collection("Niño").document(identificadorHijo);
        batch.update(nycRef, "estado", true);

        batch.commit().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                // ...
            }
        });
    }

}
