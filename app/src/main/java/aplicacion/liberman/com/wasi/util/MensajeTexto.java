package aplicacion.liberman.com.wasi.util;

import android.Manifest;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.telephony.SmsManager;
import android.widget.Toast;

import aplicacion.liberman.com.wasi.controlador.ConfirmarRecogedor;

public class MensajeTexto {

    public static void enviarSMS(ConfirmarRecogedor controlador, String sNumero, String sMensaje){
        int permissionCheck = ContextCompat.checkSelfPermission(controlador, Manifest.permission.SEND_SMS);

        if(permissionCheck != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(controlador, new String[]{Manifest.permission.SEND_SMS}, 255);
        }
        else{

        }

        try {
            SmsManager sms = SmsManager.getDefault();
            sms.sendTextMessage(sNumero,null,sMensaje,null,null);
            Toast.makeText(controlador.getApplicationContext(), "Mensaje Enviado.", Toast.LENGTH_LONG).show();
        }
        catch (Exception e) {
            Toast.makeText(controlador.getApplicationContext(), "Mensaje no enviado, datos incorrectos.", Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
    }
}
