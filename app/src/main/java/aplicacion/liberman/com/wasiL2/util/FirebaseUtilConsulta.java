package aplicacion.liberman.com.wasiL2.util;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import aplicacion.liberman.com.wasiL2.contenedor.Hijo;

public class FirebaseUtilConsulta {

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

}
