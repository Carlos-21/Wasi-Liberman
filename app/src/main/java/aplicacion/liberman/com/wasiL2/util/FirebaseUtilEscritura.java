package aplicacion.liberman.com.wasiL2.util;

import android.content.Context;
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

import aplicacion.liberman.com.wasiL2.coleccion.AlumnoColeccion;
import aplicacion.liberman.com.wasiL2.coleccion.LectorColeccion;
import aplicacion.liberman.com.wasiL2.coleccion.SalidaColeccion;
import aplicacion.liberman.com.wasiL2.coleccion.UsuarioColeccion;
import aplicacion.liberman.com.wasiL2.controlador.Profesor;
import aplicacion.liberman.com.wasiL2.soporte.Mensaje;

public class FirebaseUtilEscritura {

    /**
     * Método encargado de quitar los permisos de los alumnos que pertenecen
     * a la clase de un profesor identificado
     *
     * @param identificadorProfesor
     * @param profesor
     */
    public static void quitarPermisosAlumnos(String identificadorProfesor, final Profesor profesor) {
        final FirebaseFirestore db = FirebaseFirestore.getInstance();
        final WriteBatch batch = db.batch();

        db.collection(AlumnoColeccion.sNombre)
                .whereEqualTo(AlumnoColeccion.sProfesor, identificadorProfesor)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                DocumentReference nycRef = db.collection(AlumnoColeccion.sNombre).document(document.getId());
                                batch.update(nycRef, AlumnoColeccion.sEstado, false);
                                batch.update(nycRef, AlumnoColeccion.sCasa, false);

                                DocumentReference nycRef2 = db.collection(LectorColeccion.sNombre).document(document.getId());
                                batch.update(nycRef2, LectorColeccion.sBandera, false);
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

    /**
     * Método encargado de registrar el identificador del recogedor en los documentos
     * respectivos de la base de datos Cloud Firestore de Firebase, se registra en la
     * colección Niño en el parámetro recogedor
     *
     * @param identificadorApoderado
     * @param identificadorRecogedor
     */
    private static void asignarRecogedorHijo(String identificadorApoderado, final String identificadorRecogedor) {
        final FirebaseFirestore db = FirebaseFirestore.getInstance();
        final WriteBatch batch = db.batch();
        db.collection(AlumnoColeccion.sNombre)
                .whereEqualTo(AlumnoColeccion.sApoderado, identificadorApoderado)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                DocumentReference nycRef = db.collection(AlumnoColeccion.sNombre).document(document.getId());
                                batch.update(nycRef, AlumnoColeccion.sRecogedor, identificadorRecogedor);
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

    /**
     * Método encargado de registrar el recogedor en la base de datos Cloud Firestore de
     * Firebase
     *
     * @param usuario
     * @param identificadorApoderado
     */
    public static void registrarRecogedor(final String usuario, final String identificadorApoderado) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        Map<String, Object> data = new HashMap<>();
        data.put(UsuarioColeccion.sCorreo, usuario);
        data.put(UsuarioColeccion.sEstado, true);
        data.put(UsuarioColeccion.sPerfil, 3);

        db.collection(UsuarioColeccion.sNombre)
                .document(usuario)
                .set(data)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        asignarRecogedorHijo(identificadorApoderado, usuario);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                    }
                });
    }


    /**
     * Método encargado de registrar la salida que el usuario ha realizado
     * al momento de permitir que el hijo que había seleccionado previamente
     * pueda salir de la institución educativa
     *
     * @param context
     * @param identificador
     * @param nombreHijo
     * @param identificadorHijo
     * @param imagenHijo
     * @param apoderado
     */
    public static void registrarSalida(final Context context, String identificador, String nombreHijo, String identificadorHijo, String imagenHijo, String apoderado) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        Map<String, Object> data = new HashMap<>();
        data.put(SalidaColeccion.sHijo, nombreHijo);
        data.put(SalidaColeccion.sUsuario, identificador);
        data.put(SalidaColeccion.sFecha, Mensaje.fecha);
        data.put(SalidaColeccion.sHora, Mensaje.hora);
        data.put(SalidaColeccion.sImagen, imagenHijo);

        String identificadorUsuario = identificador;
        if (apoderado != null) {
            identificadorUsuario = apoderado;
        }

        db.collection(UsuarioColeccion.sNombre).document(identificadorUsuario).collection(SalidaColeccion.sNombre)
                .add(data)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Toast.makeText(context.getApplicationContext(), Mensaje.mensajeSalidaPermitida, Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                    }
                });

        WriteBatch batch = db.batch();

        DocumentReference nycRef = db.collection(AlumnoColeccion.sNombre).document(identificadorHijo);
        batch.update(nycRef, AlumnoColeccion.sEstado, true);

        batch.commit().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                // ...
            }
        });
    }

    /**
     * Método encargado de cambiar el valor en un documento de la base de datos en
     * Firebase; permitiendo a un alumno poder tener autorización de salir de la
     * institución educativa
     *
     * @param identificadorHijo
     */
    public static void registrarSalidaLector(String identificadorHijo) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        WriteBatch batch = db.batch();

        DocumentReference nycRef = db.collection(LectorColeccion.sNombre).document(identificadorHijo);
        batch.update(nycRef, LectorColeccion.sBandera, true);

        batch.commit().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                // ...
            }
        });
    }

    /**
     * Método encarga de registrar la llegada del hijo según su identificador que
     * se verificará en la base de datos Cloud Firestore de Firebase, se cambiará
     * el parámetro casa indicando que el hijo de un apoderado se encuentra en su
     * casa
     *
     * @param identificadorHijo
     */
    public static void entregaHijoMovilidad(String identificadorHijo) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        WriteBatch batch = db.batch();

        DocumentReference nycRef = db.collection(AlumnoColeccion.sNombre).document(identificadorHijo);
        batch.update(nycRef, AlumnoColeccion.sCasa, true);

        batch.commit().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                // ...
            }
        });
    }
}
