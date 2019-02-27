package aplicacion.liberman.com.wasiL2.util;

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

import java.util.ArrayList;

import aplicacion.liberman.com.wasiL2.contenedor.Hijo;
import aplicacion.liberman.com.wasiL2.contenedor.Registro;
import aplicacion.liberman.com.wasiL2.contenedor.Usuario;
import aplicacion.liberman.com.wasiL2.controlador.Apoderado;
import aplicacion.liberman.com.wasiL2.controlador.Inicio;
import aplicacion.liberman.com.wasiL2.controlador.Login;
import aplicacion.liberman.com.wasiL2.controlador.Movilidad;
import aplicacion.liberman.com.wasiL2.controlador.Perfil;
import aplicacion.liberman.com.wasiL2.controlador.Profesor;
import aplicacion.liberman.com.wasiL2.controlador.Recogedor;
import aplicacion.liberman.com.wasiL2.soporte.Buscar;
import aplicacion.liberman.com.wasiL2.soporte.Mensaje;

public class FirebaseUtilConsulta {

    /**
     * Método encargado de verificar el usuario tanto en el módulo de autentificación
     * de firebase y en la base de datos Cloud Firestore
     * @param login
     * @param sIdentificador
     * @param oUsuarioWasi
     */
    public static void verificarUsuario(final Login login, String sIdentificador, final Usuario oUsuarioWasi) {
        FirebaseFirestore firestore = FirebaseFirestore.getInstance();
        DocumentReference docRef = firestore.collection("Usuarios").document(sIdentificador);
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
        DocumentReference docRef = firestore.collection("Usuarios").document(sIdentificador);
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
     * @param sIdentificador
     * @return
     */
    public static FirestoreRecyclerOptions<Hijo> listarAlumnosApoderado(String sIdentificador) {
        FirebaseFirestore oFirestore = FirebaseFirestore.getInstance();
        Query oSentencia = oFirestore.collection("Niño").whereEqualTo("apoderado", sIdentificador);

        FirestoreRecyclerOptions<Hijo> aAlumnos = new FirestoreRecyclerOptions.Builder<Hijo>()
                .setQuery(oSentencia, Hijo.class)
                .build();

        return aAlumnos;
    }

    /**
     * Método encargado de traer todos los datos de los alumnos pertenecientes a un profesor
     * según su identificador que se pasa como parámetro
     * @param sIdentificador
     * @param bEstado
     * @return
     */
    public static FirestoreRecyclerOptions<Hijo> listarAlumnosProfesor(String sIdentificador, boolean bEstado) {
        FirebaseFirestore oFirestore = FirebaseFirestore.getInstance();
        Query oSentencia = oFirestore.collection("Niño").whereEqualTo("recogedor", sIdentificador)
                .whereEqualTo("estado", bEstado);

        FirestoreRecyclerOptions<Hijo> aAlumnos = new FirestoreRecyclerOptions.Builder<Hijo>()
                .setQuery(oSentencia, Hijo.class)
                .build();

        return aAlumnos;
    }

    /**
     * Método encargado de traer todos los datos de los hijos pertenecientes a un apoderado
     * que designo a una persona para que realice su labor según su identificador que se
     * pasa como parámetro
     * @param sIdentificador
     * @return
     */
    public static FirestoreRecyclerOptions<Hijo> listarAlumnosRecogedor(String sIdentificador) {
        FirebaseFirestore oFirestore = FirebaseFirestore.getInstance();
        Query oSentencia = oFirestore.collection("Niño").whereEqualTo("recogedor", sIdentificador);

        FirestoreRecyclerOptions<Hijo> aAlumnos = new FirestoreRecyclerOptions.Builder<Hijo>()
                .setQuery(oSentencia, Hijo.class)
                .build();

        return aAlumnos;
    }

    /**
     * Método encargado de traer todos los datos de los hijos pertenecientes a un apoderado
     * que designo a una movilidad para que realice su labor según su identificador que se
     * pasa como parámetro
     * @param sIdentificador
     * @param bEstado
     * @param bCasa
     * @return
     */
    public static FirestoreRecyclerOptions<Hijo> listarAlumnosMovilidad(String sIdentificador, boolean bEstado, boolean bCasa) {
        FirebaseFirestore oFirestore = FirebaseFirestore.getInstance();
        Query oSentencia = oFirestore.collection("Niño").whereEqualTo("movilidad", sIdentificador)
                .whereEqualTo("estado", bEstado)
                .whereEqualTo("casa", bCasa);

        FirestoreRecyclerOptions<Hijo> aAlumnos = new FirestoreRecyclerOptions.Builder<Hijo>()
                .setQuery(oSentencia, Hijo.class)
                .build();

        return aAlumnos;
    }

    /**
     * Método encargado de traer todas las salidad realizadas por el apoderado en un registro
     * @param sIdentificador
     * @return
     */
    public static FirestoreRecyclerOptions<Registro> listarSalidas(String sIdentificador) {
        FirebaseFirestore oFirestore = FirebaseFirestore.getInstance();
        Query oSentencia = oFirestore.collection("Usuarios").document(sIdentificador).collection("Salidas");

        FirestoreRecyclerOptions<Registro> aSalidas = new FirestoreRecyclerOptions.Builder<Registro>()
                .setQuery(oSentencia, Registro.class)
                .build();

        return aSalidas;
    }
}
