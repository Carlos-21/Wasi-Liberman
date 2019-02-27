package aplicacion.liberman.com.wasiL2.util;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

import java.util.ArrayList;
import java.util.List;

public class PermisosUtil {
    public static final int REQUEST_ID_MULTIPLE_PERMISSIONS = 101;

    public static boolean verificarPermisosSistema(final Activity context) {
        int internet = ContextCompat.checkSelfPermission(context, Manifest.permission.INTERNET);

        int sms = ContextCompat.checkSelfPermission(context, Manifest.permission.SEND_SMS);

        List<String> listPermissionsNeeded = new ArrayList<>();

        if (internet != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.INTERNET);
        }

        if (sms != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.SEND_SMS);
        }

        if (!listPermissionsNeeded.isEmpty()) {
            ActivityCompat.requestPermissions(context, listPermissionsNeeded
                            .toArray(new String[listPermissionsNeeded.size()]),
                    REQUEST_ID_MULTIPLE_PERMISSIONS);
            return false;
        }
        return true;
    }

}
