package aplicacion.liberman.com.wasiL2.util;

import android.support.annotation.NonNull;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

import aplicacion.liberman.com.wasiL2.contenedor.Hijo;
import aplicacion.liberman.com.wasiL2.contenedor.Registro;
import aplicacion.liberman.com.wasiL2.contenedor.Usuario;

public class FirebaseUtilConsulta {

    public static void listarUsuarios(final ArrayList<Usuario> aUsuarios, int iPerfil) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        db.collection("Usuarios")
                .whereEqualTo("perfil", iPerfil)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (DocumentSnapshot document : task.getResult()) {
                                Usuario auxiliar = document.toObject(Usuario.class);
                                auxiliar.setIdentificador(document.getId());
                                aUsuarios.add(auxiliar);
                            }
                        }
                    }
                });
    }

    public static FirestoreRecyclerOptions<Hijo> listarAlumnosApoderado(String sIdentificador) {
        FirebaseFirestore oFirestore = FirebaseFirestore.getInstance();
        Query oSentencia = oFirestore.collection("Niño").whereEqualTo("apoderado", sIdentificador);

        FirestoreRecyclerOptions<Hijo> aAlumnos = new FirestoreRecyclerOptions.Builder<Hijo>()
                .setQuery(oSentencia, Hijo.class)
                .build();

        return aAlumnos;
    }

    public static FirestoreRecyclerOptions<Hijo> listarAlumnosProfesor(String sIdentificador, boolean bEstado) {
        FirebaseFirestore oFirestore = FirebaseFirestore.getInstance();
        Query oSentencia = oFirestore.collection("Niño").whereEqualTo("recogedor", sIdentificador)
                .whereEqualTo("estado", bEstado);

        FirestoreRecyclerOptions<Hijo> aAlumnos = new FirestoreRecyclerOptions.Builder<Hijo>()
                .setQuery(oSentencia, Hijo.class)
                .build();

        return aAlumnos;
    }

    public static FirestoreRecyclerOptions<Hijo> listarAlumnosRecogedor(String sIdentificador) {
        FirebaseFirestore oFirestore = FirebaseFirestore.getInstance();
        Query oSentencia = oFirestore.collection("Niño").whereEqualTo("recogedor", sIdentificador);

        FirestoreRecyclerOptions<Hijo> aAlumnos = new FirestoreRecyclerOptions.Builder<Hijo>()
                .setQuery(oSentencia, Hijo.class)
                .build();

        return aAlumnos;
    }

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

    public static FirestoreRecyclerOptions<Registro> listarSalidas(String sIdentificador) {
        FirebaseFirestore oFirestore = FirebaseFirestore.getInstance();
        Query oSentencia = oFirestore.collection("Usuarios").document(sIdentificador).collection("Salidas");

        FirestoreRecyclerOptions<Registro> aSalidas = new FirestoreRecyclerOptions.Builder<Registro>()
                .setQuery(oSentencia, Registro.class)
                .build();

        return aSalidas;
    }
}
