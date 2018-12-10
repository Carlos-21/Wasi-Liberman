package aplicacion.liberman.com.wasi.soporte;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import aplicacion.liberman.com.wasi.R;

public class HijoHolder extends RecyclerView.ViewHolder {
    CardView oCartaHijo;
    TextView oDatoHijo;
    ImageView oFotoHijo;

    public HijoHolder(View itemView) {
        super(itemView);
        oCartaHijo = (CardView)itemView.findViewById(R.id.oCartaHijo);
        oDatoHijo = (TextView)itemView.findViewById(R.id.oTextoDatos);
        oFotoHijo = (ImageView)itemView.findViewById(R.id.oFotoHijo);
    }
}
