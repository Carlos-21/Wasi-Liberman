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
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.WriteBatch;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import aplicacion.liberman.com.wasiL2.coleccion.AlumnoColeccion;
import aplicacion.liberman.com.wasiL2.coleccion.LectorColeccion;
import aplicacion.liberman.com.wasiL2.coleccion.SalidaColeccion;
import aplicacion.liberman.com.wasiL2.coleccion.UsuarioColeccion;
import aplicacion.liberman.com.wasiL2.contenedor.Registro;
import aplicacion.liberman.com.wasiL2.contenedor.Usuario;
import aplicacion.liberman.com.wasiL2.controlador.Profesor;
import aplicacion.liberman.com.wasiL2.soporte.Mensaje;

public class FirebaseUtilEscritura {

    /**
     * Método encargado de quitar los permisos de los alumnos que pertenecen
     * a la clase de un profesor identificado
     *
     * @param identificadorProfesor
     * @param profesor
     * @param bandera
     */
    public static void quitarPermisosAlumnos(String identificadorProfesor, final Profesor profesor, final boolean bandera) {
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
                                if (bandera) {
                                    Toast.makeText(profesor.getApplicationContext(), Mensaje.mensajePermisosQuitados, Toast.LENGTH_SHORT).show();
                                }
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

    /**
     * Método encargado de registrar la actualización de un Usuario transfiriendo las nuevas salidas al nuevo usuario
     * si es que tiene el perfil de apoderado, de no ser así solo actualizará
     *
     * @param context
     * @param oUsuario
     * @param iPerfil
     * @param listaRegistro
     * @param sIdentificador
     */
    public static void registrarActualizarUsuario(final Context context, final Usuario oUsuario, final int iPerfil, final List<Registro> listaRegistro, final String sIdentificador) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        Map<String, Object> data = new HashMap<>();
        data.put(UsuarioColeccion.sApellidos, oUsuario.getApellidos());
        data.put(UsuarioColeccion.sCorreo, oUsuario.getCorreo());
        data.put(UsuarioColeccion.sEstado, oUsuario.isEstado());
        data.put(UsuarioColeccion.sNombres, oUsuario.getNombres());
        data.put(UsuarioColeccion.sPerfil, oUsuario.getPerfil());

        db.collection(UsuarioColeccion.sNombre)
                .document(oUsuario.getCorreo())
                .set(data)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        if (iPerfil == 1) {
                            for (Registro auxiliar : listaRegistro) {
                                registrarSalidaActualizarUsuario(context, auxiliar, oUsuario.getCorreo());
                            }
                            eliminarSalidaUsuario(sIdentificador);
                            Toast.makeText(context.getApplicationContext(), Mensaje.mensajeCambioUsuario, Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(context.getApplicationContext(), Mensaje.mensajeCambioUsuario, Toast.LENGTH_SHORT).show();
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                    }
                });
    }

    /**
     * Método encargado de eliminar los documentos de la colección Salida del antiguo usuario
     * para evitar duplicidad de datos
     *
     * @param sIdentificador
     */
    public static void eliminarSalidaUsuario(final String sIdentificador) {
        final FirebaseFirestore firestore = FirebaseFirestore.getInstance();

        firestore.collection(UsuarioColeccion.sNombre)
                .document(sIdentificador)
                .collection(SalidaColeccion.sNombre)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                firestore.collection(UsuarioColeccion.sNombre).document(sIdentificador).collection(SalidaColeccion.sNombre).document(document.getId())
                                        .delete()
                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {
                                            }
                                        })
                                        .addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {

                                            }
                                        });
                            }
                        }
                    }
                });

    }

    /**
     * Método encargado de registrar un documento en la colección Salida del nuevo usuario
     * del perfil Apoderado
     *
     * @param context
     * @param oRegistro
     * @param identificadorUsuario
     */
    public static void registrarSalidaActualizarUsuario(final Context context, Registro oRegistro, String identificadorUsuario) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        Map<String, Object> data = new HashMap<>();
        data.put(SalidaColeccion.sHijo, oRegistro.getHijo());
        data.put(SalidaColeccion.sUsuario, oRegistro.getUsuario());
        data.put(SalidaColeccion.sFecha, oRegistro.getFecha());
        data.put(SalidaColeccion.sHora, oRegistro.getHora());
        data.put(SalidaColeccion.sImagen, oRegistro.getImagen());

        db.collection(UsuarioColeccion.sNombre).document(identificadorUsuario).collection(SalidaColeccion.sNombre)
                .add(data)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                    }
                });

    }

    /**
     * Método encargado de actualizar los campos necesarios de la colección Alumno
     * para realizar las nuevas referencias respectivas
     *
     * @param context
     * @param sIdentificador
     * @param sNuevoUsuario
     * @param iPerfil
     */
    public static void actualizarAlumnoUsuario(Context context, String sIdentificador, final String sNuevoUsuario, final int iPerfil) {
        final FirebaseFirestore db = FirebaseFirestore.getInstance();
        final WriteBatch batch = db.batch();

        Query query = null;

        switch (iPerfil) {
            case 1:
                query = db.collection(AlumnoColeccion.sNombre).whereEqualTo(AlumnoColeccion.sApoderado, sIdentificador);
                break;
            case 2:
                query = db.collection(AlumnoColeccion.sNombre).whereEqualTo(AlumnoColeccion.sMovilidad, sIdentificador);
                break;
            case 4:
                query = db.collection(AlumnoColeccion.sNombre).whereEqualTo(AlumnoColeccion.sProfesor, sIdentificador);
                break;
        }

        query.get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                DocumentReference nycRef = db.collection(AlumnoColeccion.sNombre).document(document.getId());
                                batch.update(nycRef, AlumnoColeccion.sEstado, false);
                                switch (iPerfil) {
                                    case 1:
                                        batch.update(nycRef, AlumnoColeccion.sApoderado, sNuevoUsuario);
                                        break;
                                    case 2:
                                        batch.update(nycRef, AlumnoColeccion.sMovilidad, sNuevoUsuario);
                                        break;
                                    case 4:
                                        batch.update(nycRef, AlumnoColeccion.sProfesor, sNuevoUsuario);
                                        break;
                                }
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

}
