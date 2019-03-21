package aplicacion.liberman.com.wasiL2.util;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.widget.Toast;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.WriteBatch;

import java.util.ArrayList;
import java.util.List;

import aplicacion.liberman.com.wasiL2.R;
import aplicacion.liberman.com.wasiL2.coleccion.AlumnoColeccion;
import aplicacion.liberman.com.wasiL2.coleccion.SalidaColeccion;
import aplicacion.liberman.com.wasiL2.coleccion.UsuarioColeccion;
import aplicacion.liberman.com.wasiL2.contenedor.Hijo;
import aplicacion.liberman.com.wasiL2.contenedor.Registro;
import aplicacion.liberman.com.wasiL2.contenedor.Usuario;
import aplicacion.liberman.com.wasiL2.controlador.Apoderado;
import aplicacion.liberman.com.wasiL2.controlador.Inicio;
import aplicacion.liberman.com.wasiL2.controlador.Login;
import aplicacion.liberman.com.wasiL2.controlador.Movilidad;
import aplicacion.liberman.com.wasiL2.controlador.Profesor;
import aplicacion.liberman.com.wasiL2.controlador.Recogedor;
import aplicacion.liberman.com.wasiL2.controlador.VerHijoApoderado;
import aplicacion.liberman.com.wasiL2.controlador.VerHijoMovilidad;
import aplicacion.liberman.com.wasiL2.controlador.VerHijoProfesor;
import aplicacion.liberman.com.wasiL2.controlador.VerRegistroSalida;
import aplicacion.liberman.com.wasiL2.soporte.Buscar;
import aplicacion.liberman.com.wasiL2.soporte.Generador;
import aplicacion.liberman.com.wasiL2.soporte.Mensaje;

public class FirebaseUtilConsulta {

    /**
     * Método encargado de verificar el usuario tanto en el módulo de autentificación
     * de firebase y en la base de datos Cloud Firestore
     *
     * @param login
     * @param sIdentificador
     * @param oUsuarioWasi
     */
    public static void verificarUsuario(final Login login, String sIdentificador, final Usuario oUsuarioWasi) {
        FirebaseFirestore firestore = FirebaseFirestore.getInstance();

        DocumentReference docRef = firestore.collection(UsuarioColeccion.sNombre).document(sIdentificador);
        docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                Usuario oUsuario = documentSnapshot.toObject(Usuario.class);

                if (Buscar.existeUsuario(oUsuario, oUsuarioWasi)) {
                    Toast.makeText(login.getApplicationContext(), Mensaje.sUsuarioCorrecto, Toast.LENGTH_SHORT).show();
                    Intent oIntencion = null;
                    switch (oUsuario.getPerfil()) {
                        case 1:
                            oIntencion = new Intent(login.getApplicationContext(), Apoderado.class);
                            oIntencion.putExtra("identificador", oUsuario.getCorreo());
                            login.startActivity(oIntencion);
                            login.finish();
                            break;
                        case 2:
                            oIntencion = new Intent(login.getApplicationContext(), Movilidad.class);
                            oIntencion.putExtra("identificador", oUsuario.getCorreo());
                            login.startActivity(oIntencion);
                            login.finish();
                            break;
                        case 3:
                            oIntencion = new Intent(login.getApplicationContext(), Recogedor.class);
                            oIntencion.putExtra("identificador", oUsuario.getCorreo());
                            login.startActivity(oIntencion);
                            login.finish();
                            break;
                        case 4:
                            oIntencion = new Intent(login.getApplicationContext(), Profesor.class);
                            oIntencion.putExtra("identificador", oUsuario.getCorreo());
                            login.startActivity(oIntencion);
                            login.finish();
                            break;
                    }
                } else {
                    FirebaseAuth.getInstance().signOut();
                    Toast.makeText(login.getApplicationContext(), Mensaje.sUsuarioIncorrecto, Toast.LENGTH_SHORT).show();
                }
            }

        });
    }

    /**
     * Método encargado de verificar el usuario cuando ya se había
     * registrado antes y no había cerrado sesión
     *
     * @param inicio
     * @param sIdentificador
     */
    public static void verificarUsuarioLogin(final Inicio inicio, String sIdentificador) {
        FirebaseFirestore firestore = FirebaseFirestore.getInstance();
        DocumentReference docRef = firestore.collection(UsuarioColeccion.sNombre).document(sIdentificador);
        docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                Usuario oUsuario = documentSnapshot.toObject(Usuario.class);
                Intent oIntencion = null;
                switch (oUsuario.getPerfil()) {
                    case 1:
                        oIntencion = new Intent(inicio.getApplicationContext(), Apoderado.class);
                        oIntencion.putExtra("identificador", oUsuario.getCorreo());
                        inicio.startActivity(oIntencion);
                        inicio.finish();
                        break;
                    case 2:
                        oIntencion = new Intent(inicio.getApplicationContext(), Movilidad.class);
                        oIntencion.putExtra("identificador", oUsuario.getCorreo());
                        inicio.startActivity(oIntencion);
                        inicio.finish();
                        break;
                    case 3:
                        oIntencion = new Intent(inicio.getApplicationContext(), Recogedor.class);
                        oIntencion.putExtra("identificador", oUsuario.getCorreo());
                        inicio.startActivity(oIntencion);
                        inicio.finish();
                        break;
                    case 4:
                        oIntencion = new Intent(inicio.getApplicationContext(), Profesor.class);
                        oIntencion.putExtra("identificador", oUsuario.getCorreo());
                        inicio.startActivity(oIntencion);
                        inicio.finish();
                        break;
                }
            }

        });
    }

    /**
     * Método encargado de traer todos los datos de los hijos pertenecientes a un apoderado
     * según su identificador que se pasa como parámetro
     *
     * @param sIdentificador
     * @return
     */
    public static FirestoreRecyclerOptions<Hijo> listarAlumnosApoderado(String sIdentificador) {
        FirebaseFirestore oFirestore = FirebaseFirestore.getInstance();
        Query oSentencia = oFirestore.collection(AlumnoColeccion.sNombre).whereEqualTo(AlumnoColeccion.sApoderado, sIdentificador);

        FirestoreRecyclerOptions<Hijo> aAlumnos = new FirestoreRecyclerOptions.Builder<Hijo>()
                .setQuery(oSentencia, Hijo.class)
                .build();

        return aAlumnos;
    }

    /**
     * Método encargado de verificar si un apoderado tiene hijos asignados en la base de datos,
     * de no ser así se deberá comunicar con el administrados
     *
     * @param context
     * @param sIdentificador
     * @param bBandera
     */
    public static void verificarHijoApoderado(final Context context, final String sIdentificador, final boolean bBandera) {
        FirebaseFirestore oFirestore = FirebaseFirestore.getInstance();

        oFirestore.collection(AlumnoColeccion.sNombre)
                .whereEqualTo(AlumnoColeccion.sApoderado, sIdentificador)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            if (task.getResult().size() != 0) {
                                Intent intent = new Intent(context.getApplicationContext(), VerHijoApoderado.class);
                                intent.putExtra("bandera", bBandera);
                                intent.putExtra("identificador", sIdentificador);
                                context.startActivity(intent);
                            } else {
                                Toast.makeText(context.getApplicationContext(), Mensaje.hijoApoderado, Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                });
    }

    /**
     * Método encargado de traer todos los datos de los alumnos pertenecientes a un profesor
     * según su identificador que se pasa como parámetro
     *
     * @param sIdentificador
     * @param bEstado
     * @return
     */
    public static FirestoreRecyclerOptions<Hijo> listarAlumnosProfesor(String sIdentificador, boolean bEstado) {
        FirebaseFirestore oFirestore = FirebaseFirestore.getInstance();
        Query oSentencia = oFirestore.collection(AlumnoColeccion.sNombre).whereEqualTo(AlumnoColeccion.sProfesor, sIdentificador)
                .whereEqualTo(AlumnoColeccion.sEstado, bEstado);

        FirestoreRecyclerOptions<Hijo> aAlumnos = new FirestoreRecyclerOptions.Builder<Hijo>()
                .setQuery(oSentencia, Hijo.class)
                .build();

        return aAlumnos;
    }

    /**
     * Método encargado de verificar que un profesor tenga alumnos asignados en la base de datos,
     * de no ser así se deberá comunicar con el administrador
     *
     * @param context
     * @param sIdentificador
     * @param bEstado
     */
    public static void verificarAlumnosProfesor(final Context context, final String sIdentificador, final boolean bEstado) {
        FirebaseFirestore oFirestore = FirebaseFirestore.getInstance();

        oFirestore.collection(AlumnoColeccion.sNombre)
                .whereEqualTo(AlumnoColeccion.sMovilidad, sIdentificador)
                .whereEqualTo(AlumnoColeccion.sEstado, bEstado)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            if (task.getResult().size() != 0) {
                                Intent intent = new Intent(context.getApplicationContext(), VerHijoProfesor.class);
                                intent.putExtra("titulo", context.getApplicationContext().getResources().getString(R.string.cAlumnosHabilitados));
                                intent.putExtra("identificador", sIdentificador);
                                intent.putExtra("estado", bEstado);
                                context.startActivity(intent);
                            } else {
                                if (bEstado) {
                                    Toast.makeText(context.getApplicationContext(), Mensaje.alumnosHProfesor, Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(context.getApplicationContext(), Mensaje.alumnosNHProfesor, Toast.LENGTH_SHORT).show();
                                }
                            }
                        }
                    }
                });
    }

    /**
     * Método encargado de traer todos los datos de los hijos pertenecientes a un apoderado
     * que designo a una persona para que realice su labor según su identificador que se
     * pasa como parámetro
     *
     * @param sIdentificador
     * @return
     */
    public static FirestoreRecyclerOptions<Hijo> listarAlumnosRecogedor(String sIdentificador) {
        FirebaseFirestore oFirestore = FirebaseFirestore.getInstance();
        Query oSentencia = oFirestore.collection(AlumnoColeccion.sNombre).whereEqualTo(AlumnoColeccion.sRecogedor, sIdentificador);

        FirestoreRecyclerOptions<Hijo> aAlumnos = new FirestoreRecyclerOptions.Builder<Hijo>()
                .setQuery(oSentencia, Hijo.class)
                .build();

        return aAlumnos;
    }

    /**
     * Método encargado de traer todos los datos de los hijos pertenecientes a un apoderado
     * que designo a una movilidad para que realice su labor según su identificador que se
     * pasa como parámetro
     *
     * @param sIdentificador
     * @param bEstado
     * @param bCasa
     * @return
     */
    public static FirestoreRecyclerOptions<Hijo> listarAlumnosMovilidad(String sIdentificador, boolean bEstado, boolean bCasa) {
        FirebaseFirestore oFirestore = FirebaseFirestore.getInstance();
        Query oSentencia = oFirestore.collection(AlumnoColeccion.sNombre).whereEqualTo(AlumnoColeccion.sMovilidad, sIdentificador)
                .whereEqualTo(AlumnoColeccion.sEstado, bEstado)
                .whereEqualTo(AlumnoColeccion.sCasa, bCasa);

        FirestoreRecyclerOptions<Hijo> aAlumnos = new FirestoreRecyclerOptions.Builder<Hijo>()
                .setQuery(oSentencia, Hijo.class)
                .build();

        return aAlumnos;
    }

    /**
     * Método encargado de verificar que un usuario con el perfil movilidad tenga asignado alumnos que recoger según la
     * base de datos, de no ser así se deberá consultar con el administrador
     *
     * @param context
     * @param sIdentificador
     * @param bEstado
     * @param bCasa
     * @param bBandera
     */
    public static void verificarAlumnosMovilidad(final Context context, final String sIdentificador, final boolean bEstado, final boolean bCasa, final boolean bBandera) {
        FirebaseFirestore oFirestore = FirebaseFirestore.getInstance();

        oFirestore.collection(AlumnoColeccion.sNombre)
                .whereEqualTo(AlumnoColeccion.sMovilidad, sIdentificador)
                .whereEqualTo(AlumnoColeccion.sEstado, bEstado)
                .whereEqualTo(AlumnoColeccion.sCasa, bCasa)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            if (task.getResult().size() != 0) {
                                Intent intent = new Intent(context.getApplicationContext(), VerHijoMovilidad.class);
                                intent.putExtra("titulo", context.getApplicationContext().getResources().getString(R.string.cAlumnosNoHabilitados));
                                intent.putExtra("identificador", sIdentificador);
                                intent.putExtra("estado", bEstado);
                                intent.putExtra("casa", bCasa);
                                intent.putExtra("bandera", bBandera);
                                context.startActivity(intent);
                            } else {
                                if (bEstado == true && bCasa == true) {
                                    Toast.makeText(context.getApplicationContext(), Mensaje.alumnosEMovilidad, Toast.LENGTH_SHORT).show();
                                }
                                if (bEstado == true && bCasa == false) {
                                    Toast.makeText(context.getApplicationContext(), Mensaje.alumnosHMovilidad, Toast.LENGTH_SHORT).show();
                                }
                                if (bEstado == false && bCasa == false) {
                                    Toast.makeText(context.getApplicationContext(), Mensaje.alumnosNHMovilidad, Toast.LENGTH_SHORT).show();
                                }
                            }
                        }
                    }
                });
    }

    /**
     * Método encargado de verificar si un usuario del perfil apoderado tiene
     * algún registro de las salidas que le ha otorgado a su hijo
     *
     * @param context
     * @param sIdentificador
     */
    public static void verificarSalidas(final Context context, final String sIdentificador) {
        FirebaseFirestore oFirestore = FirebaseFirestore.getInstance();

        oFirestore.collection(UsuarioColeccion.sNombre)
                .document(sIdentificador)
                .collection(SalidaColeccion.sNombre)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            if (task.getResult().size() != 0) {
                                Intent intent = new Intent(context.getApplicationContext(), VerRegistroSalida.class);
                                intent.putExtra("identificador", sIdentificador);
                                context.startActivity(intent);
                            } else {
                                Toast.makeText(context.getApplicationContext(), Mensaje.hijoSalidaApoderado, Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                });
    }

    /**
     * Método encargado de traer todas las salidas realizadas por el apoderado en un registro
     *
     * @param sIdentificador
     * @return
     */
    public static FirestoreRecyclerOptions<Registro> listarSalidas(String sIdentificador) {
        FirebaseFirestore oFirestore = FirebaseFirestore.getInstance();
        Query oSentencia = oFirestore.collection(UsuarioColeccion.sNombre).document(sIdentificador).collection(SalidaColeccion.sNombre);

        FirestoreRecyclerOptions<Registro> aSalidas = new FirestoreRecyclerOptions.Builder<Registro>()
                .setQuery(oSentencia, Registro.class)
                .build();

        return aSalidas;
    }

    /**
     * Método encargado de verificar si existe un usuario con el perfil recogedor que tenga el mismo correo,
     * si existe la aplicación generará otro usuario hasta que sea diferente y nuevo
     *
     * @param context
     * @param sCorreo
     * @param sClave
     * @param sTelefono
     * @param sIdentificador
     */
    public static void verificarUsuarioRecogedor(final Context context, final String sCorreo, final String sClave, final String sTelefono, final String sIdentificador) {
        FirebaseFirestore oFirestore = FirebaseFirestore.getInstance();

        oFirestore.collection(UsuarioColeccion.sNombre)
                .whereEqualTo(UsuarioColeccion.sPerfil, 3)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            String correo = sCorreo;
                            String clave = sClave;

                            List<Usuario> listaUsuarios = new ArrayList<>();
                            for (DocumentSnapshot document : task.getResult()) {
                                listaUsuarios.add(document.toObject(Usuario.class));
                            }

                            boolean bandera = false;
                            while (!bandera) {
                                for (Usuario auxiliar : listaUsuarios) {
                                    if (auxiliar.getCorreo().compareTo(correo + "@gmail.com") == 0) {
                                        bandera = true;
                                    }
                                }
                                if (!bandera) {
                                    bandera = true;
                                } else {
                                    correo = Generador.getUsuario();
                                    bandera = false;
                                }
                            }
                            correo += "@gmail.com";
                            String sMensaje = Mensaje.mensajeTextoRecogeor
                                    .replace("paramU", correo)
                                    .replace("paramC", clave);
                            MensajeRecogedor.enviarMensajeTexto(context, sMensaje, sTelefono);
                            FirebaseUtilAutorizacion.registrarRecogedorAutorizacion(context, correo, clave, sIdentificador);
                        }
                    }
                });

    }

    /**
     * Método encargado de cambiar el usuario de la base de datos de Firebase al igual que las
     * subcolecciones que contenga
     *
     * @param context
     * @param sIdentificador
     * @param iPerfil
     * @param sNuevoUsuario
     */
    public static void cambiarUsuarioFirebase(final Context context, final String sIdentificador, final int iPerfil, final String sNuevoUsuario) {
        final FirebaseFirestore firestore = FirebaseFirestore.getInstance();

        DocumentReference docRef = firestore.collection(UsuarioColeccion.sNombre).document(sIdentificador);
        docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                Usuario oUsuario2 = documentSnapshot.toObject(Usuario.class);
                final String sUsuarioActual = oUsuario2.getCorreo();

                oUsuario2.setCorreo(sNuevoUsuario);

                final Usuario oUsuario = oUsuario2;
                firestore.collection(UsuarioColeccion.sNombre)
                        .document(sIdentificador)
                        .collection(SalidaColeccion.sNombre)
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if (task.isSuccessful()) {

                                    List<Registro> listaSalidas = new ArrayList<>();

                                    if (iPerfil == 1) {
                                        for (DocumentSnapshot document : task.getResult()) {
                                            listaSalidas.add(document.toObject(Registro.class));
                                        }
                                    }

                                    final List<Registro> listaSalidas2 = listaSalidas;
                                    WriteBatch batch = firestore.batch();
                                    DocumentReference laRef = firestore.collection(UsuarioColeccion.sNombre).document(sIdentificador);
                                    batch.delete(laRef);

                                    batch.commit().addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            FirebaseUtilEscritura.registrarActualizarUsuario(context, oUsuario, iPerfil, listaSalidas2, sIdentificador);
                                        }
                                    });
                                }
                            }
                        });
            }

        });
    }

}
