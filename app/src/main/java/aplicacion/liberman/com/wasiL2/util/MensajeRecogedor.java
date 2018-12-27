package aplicacion.liberman.com.wasiL2.util;

import android.content.Intent;
import android.net.Uri;

import aplicacion.liberman.com.wasiL2.controlador.ConfirmarRecogedor;

public class MensajeRecogedor {

    public static void enviarMensajeWhatsapp(ConfirmarRecogedor oConfirmarRecogedor, String sMensaje, String sTelefono) {
        Intent oIntencion = new Intent(Intent.ACTION_VIEW);
        String sLink = "whatsapp://send?phone=" + "51" + sTelefono + "&text=" + sMensaje;
        oIntencion.setData(Uri.parse(sLink));
        oConfirmarRecogedor.startActivity(oIntencion);
    }

}
