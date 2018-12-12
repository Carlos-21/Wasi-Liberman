package aplicacion.liberman.com.wasi.soporte;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import aplicacion.liberman.com.wasi.R;

public class RegistroHolder extends RecyclerView.ViewHolder{
    CardView oCartaHijoSalida;
    TextView oDatoHijoSalida;
    ImageView oFotoHijoSalida;

    public RegistroHolder(View itemView) {
        super(itemView);
        oCartaHijoSalida = (CardView)itemView.findViewById(R.id.oCartaHijoSalida);
        oDatoHijoSalida = (TextView)itemView.findViewById(R.id.oTextoDatosSalida);
        oFotoHijoSalida = (ImageView)itemView.findViewById(R.id.oFotoHijoSalida);
    }
}
