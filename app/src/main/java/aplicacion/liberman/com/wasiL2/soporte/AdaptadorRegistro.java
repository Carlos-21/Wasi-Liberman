package aplicacion.liberman.com.wasiL2.soporte;

import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.squareup.picasso.Picasso;

import aplicacion.liberman.com.wasiL2.R;
import aplicacion.liberman.com.wasiL2.contenedor.Registro;

public class AdaptadorRegistro extends FirestoreRecyclerAdapter<Registro, RegistroHolder> implements View.OnClickListener {
    private View.OnClickListener listener;

    /**
     * Create a new RecyclerView adapter that listens to a Firestore Query.  See
     * {@link FirestoreRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public AdaptadorRegistro(FirestoreRecyclerOptions<Registro> options) {
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
    protected void onBindViewHolder(RegistroHolder holder, int position, Registro model) {
        holder.oDatoHijoSalida.setText("Hijo: " + model.getHijo() + "\nFecha: " + model.getFecha() + "\nHora: " + model.getHora() + "\nUsuario: " + model.getUsuario());
        Picasso.get().load(model.getImagen()).into(holder.oFotoHijoSalida);
    }

    @Override
    public RegistroHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.lista_registro, parent, false);
        v.setOnClickListener(this);
        RegistroHolder oRegistroHolder = new RegistroHolder(v);
        return oRegistroHolder;
    }

}
