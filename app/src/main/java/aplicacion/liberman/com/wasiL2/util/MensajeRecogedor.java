package aplicacion.liberman.com.wasiL2.util;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.telephony.SmsManager;

public class MensajeRecogedor {

    /**
     * Método encargado de enviar un mensaje usando WhatsApp al
     * número telefónico que se pasa como parámetro
     *
     * @param context
     * @param sMensaje
     * @param sTelefono
     */
    public static void enviarMensajeWhatsapp(Context context, String sMensaje, String sTelefono) {
        int internet = ContextCompat.checkSelfPermission(context, Manifest.permission.INTERNET);

        if (internet != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(((Activity) context), new String[]{Manifest.permission.INTERNET}, 455);
        }

        Intent oIntencion = new Intent(Intent.ACTION_VIEW);
        String sLink = "whatsapp://send?phone=" + "51" + sTelefono + "&text=" + sMensaje;
        oIntencion.setData(Uri.parse(sLink));
        context.startActivity(oIntencion);
    }

    /**
     * Método encargado de enviar un mensaje de texto al
     * número telefónico que se pasa como parámetro
     *
     * @param context
     * @param sMensaje
     * @param sTelefono
     */
    public static void enviarMensajeTexto(Context context, String sMensaje, String sTelefono) {
        int permissionCheck = ContextCompat.checkSelfPermission(context, Manifest.permission.SEND_SMS);

        if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(((Activity) context), new String[]{Manifest.permission.SEND_SMS}, 255);
        }

        try {
            SmsManager sms = SmsManager.getDefault();
            sms.sendTextMessage(sTelefono, null, sMensaje, null, null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
