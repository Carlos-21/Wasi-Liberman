package aplicacion.liberman.com.wasi.soporte;

import android.text.TextUtils;
import android.widget.EditText;

public class Validar {

    /**
     * Método que se encargará de validar los datos ingresados
     * en el Login para poder acceder a la base de datos
     * Firebase
     * @param oTextoUsuario
     * @param oTextoClave
     * @return
     */
    public static boolean validarLoginFirebase(EditText oTextoUsuario, EditText oTextoClave){
        boolean bValidado = true;

        String sUsuario = oTextoUsuario.getText().toString();
        if (TextUtils.isEmpty(sUsuario)) {
            oTextoUsuario.setError("Requerido");
            bValidado = false;
        } else {
            oTextoUsuario.setError(null);
        }

        String sClave = oTextoClave.getText().toString();
        if (TextUtils.isEmpty(sClave)) {
            oTextoClave.setError("Requerido");
            bValidado = false;
        } else {
            oTextoClave.setError(null);
        }

        return bValidado;
    }

    public static boolean validarLoginPerfil(EditText oTextoUsuario, EditText oTextoClave){
        boolean bValidado = true;

        String sUsuario = oTextoUsuario.getText().toString();
        if (TextUtils.isEmpty(sUsuario)) {
            oTextoUsuario.setError("Requerido");
            bValidado = false;
        } else {
            oTextoUsuario.setError(null);
        }

        String sClave = oTextoClave.getText().toString();
        if (TextUtils.isEmpty(sClave)) {
            oTextoClave.setError("Requerido");
            bValidado = false;
        } else {
            oTextoClave.setError(null);
        }

        return bValidado;
    }

}
