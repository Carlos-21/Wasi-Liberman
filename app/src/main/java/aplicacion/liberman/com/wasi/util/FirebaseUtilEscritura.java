package aplicacion.liberman.com.wasi.util;

import android.support.annotation.NonNull;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.WriteBatch;

import java.util.HashMap;
import java.util.Map;

import aplicacion.liberman.com.wasi.controlador.Profesor;
import aplicacion.liberman.com.wasi.controlador.SalidaPermitida;
import aplicacion.liberman.com.wasi.soporte.Mensaje;

public class FirebaseUtilEscritura {

    /**
     * Método encargado de quitar los permisos de los alumnos que pertenecen
     * a la clase de un profesor identificado
     * @param identificadorProfesor
     * @param profesor
     */
    public static void quitarPermisosAlumnos(String identificadorProfesor, final Profesor profesor){
        final FirebaseFirestore db = FirebaseFirestore.getInstance();
        final WriteBatch batch = db.batch();

        db.collection("Niño")
                .whereEqualTo("profesor", identificadorProfesor)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                DocumentReference nycRef = db.collection("Niño").document(document.getId());
                                batch.update(nycRef, "estado", false);
                            }
                        } else {

                        }
                        batch.commit().addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                Toast.makeText(profesor.getApplicationContext(), Mensaje.mensajePermisosQuitados, Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                });

    }


    private static void asignarRecogedorHijo(String identificadorApoderado, final String identificadorRecogedor){
        final FirebaseFirestore db = FirebaseFirestore.getInstance();
        final WriteBatch batch = db.batch();
        db.collection("Niño")
                .whereEqualTo("apoderado", identificadorApoderado)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                DocumentReference nycRef = db.collection("Niño").document(document.getId());
                                batch.update(nycRef, "recogedor", identificadorRecogedor);
                            }
                        } else {

                        }
                        batch.commit().addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {

                            }
                        });
                    }
                });
    }

    public static void registrarRecogedor(String usuario, String contraseña, final String identificadorApoderado){
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        Map<String, Object> data = new HashMap<>();
        data.put("clave", contraseña);
        data.put("nombre", usuario);
        data.put("perfil", 3);

        db.collection("Usuarios")
                .add(data)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        asignarRecogedorHijo(identificadorApoderado, documentReference.getId());
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                    }
                });
    }

}
