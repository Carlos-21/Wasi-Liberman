package aplicacion.liberman.com.wasi.soporte;

import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import aplicacion.liberman.com.wasi.contenedor.Hijo;
import aplicacion.liberman.com.wasi.R;

public class Adaptador extends RecyclerView.Adapter<Adaptador.PersonViewHolder> implements View.OnClickListener{
    private List<Hijo> listaH;
    private View.OnClickListener listener;

    public Adaptador(List<Hijo> listaH) {
        this.listaH = listaH;
    }

    @Override
    public void onClick(View view) {
        if(listener!=null){
            listener.onClick(view);
        }
    }

    public static class PersonViewHolder extends RecyclerView.ViewHolder {
        CardView carta;
        TextView nombre;
        TextView apellido;
        TextView grado;
        ImageView foto;

        PersonViewHolder(View itemView) {
            super(itemView);
            carta = (CardView)itemView.findViewById(R.id.carta);
            nombre = (TextView)itemView.findViewById(R.id.textoNombre);
            apellido = (TextView)itemView.findViewById(R.id.textoApellido);
            grado = (TextView)itemView.findViewById(R.id.textoGrado);
            foto = (ImageView)itemView.findViewById(R.id.fotoHijo);
        }
    }

    @NonNull
    @Override
    public PersonViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.lista_hijos, parent, false);
        v.setOnClickListener(this);
        PersonViewHolder pvh = new PersonViewHolder(v);
        return pvh;
    }

    public void setOnClickListener(View.OnClickListener listener){
        this.listener = listener;
    }

    @Override
    public void onBindViewHolder(@NonNull PersonViewHolder holder, int position) {
        holder.nombre.setText("Nombre : " + listaH.get(position).getNombre());
        holder.apellido.setText("Apellido : " + listaH.get(position).getApellido());
        holder.grado.setText("Grado : " + listaH.get(position).getGrado());
        holder.foto.setImageResource(R.drawable.hijo);
    }

    @Override
    public int getItemCount() {
        return listaH.size();
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

}