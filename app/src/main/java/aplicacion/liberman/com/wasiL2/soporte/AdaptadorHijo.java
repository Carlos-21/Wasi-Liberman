package aplicacion.liberman.com.wasiL2.soporte;

import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.squareup.picasso.Picasso;

import aplicacion.liberman.com.wasiL2.contenedor.Hijo;
import aplicacion.liberman.com.wasiL2.R;

public class AdaptadorHijo extends FirestoreRecyclerAdapter<Hijo, HijoHolder> implements View.OnClickListener {
    private View.OnClickListener listener;

    /**
     * Create a new RecyclerView adapter that listens to a Firestore Query.  See
     * {@link FirestoreRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public AdaptadorHijo(FirestoreRecyclerOptions<Hijo> options) {
        super(options);
    }


    @Override
    public void onClick(View view) {
        if (listener != null) {
            listener.onClick(view);
        }
    }

    public void setOnClickListener(View.OnClickListener listener) {
        this.listener = listener;
    }


    @Override
    protected void onBindViewHolder(HijoHolder holder, int position, Hijo model) {
        holder.oDatoHijo.setText("Apellidos: " + model.getApellidos() + "\nNombres: " + model.getNombres() + "\nGrado: " + model.getGrado());
        Picasso.get().load(model.getImagen()).into(holder.oFotoHijo);
    }

    @Override
    public HijoHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.lista_hijos, parent, false);
        v.setOnClickListener(this);
        HijoHolder oHijoHolder = new HijoHolder(v);
        return oHijoHolder;
    }

}