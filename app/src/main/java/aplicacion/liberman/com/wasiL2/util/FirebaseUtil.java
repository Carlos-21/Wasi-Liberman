package aplicacion.liberman.com.wasiL2.util;

import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

import aplicacion.liberman.com.wasiL2.contenedor.Usuario;

public class FirebaseUtil {
    private static final FirebaseAuth autentificador = FirebaseAuth.getInstance();

    public static void listarUsuarios(final ArrayList<Usuario> aUsuarios, int iPerfil){
        // Access a Cloud Firestore instance from your Activity

        FirebaseFirestore db = FirebaseFirestore.getInstance();

        db.collection("Usuarios")
                .whereEqualTo("perfil",iPerfil)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (DocumentSnapshot document : task.getResult()) {
                                Usuario auxiliar = document.toObject(Usuario.class);
                                System.out.println("1231 : "+document.getId());
                                auxiliar.setIdentificador(document.getId());
                                aUsuarios.add(auxiliar);
                            }
                        } else {
                        }
                    }
                });
    }

}
