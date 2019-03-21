package aplicacion.liberman.com.wasiL2.soporte;

import android.text.TextUtils;
import android.widget.EditText;

public class Validar {

    /**
     * Método que se encargará de validar los datos ingresados
     * en el Login para poder acceder a la base de datos
     * Firebase
     *
     * @param oTextoUsuario
     * @param oTextoClave
     * @return
     */
    public static boolean validarLoginFirebase(EditText oTextoUsuario, EditText oTextoClave) {
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

    /**
     * Método encargado de validar los datos ingresados en la vista activity_login
     * para poder asegurar de que los campos no se encuentren vacios
     *
     * @param oTextoUsuario
     * @param oTextoClave
     * @return
     */
    public static boolean validarLoginPerfil(EditText oTextoUsuario, EditText oTextoClave) {
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

    /**
     * Método encargado de valir los datos ingresados en la vista activity_confirmar_recogedor
     * para poder asegurar que los campos no esten vacios y que deben cumplir con un requisito
     * mínimo de longitud
     *
     * @param oTextoUsuario
     * @param oTextoClave
     * @param oTextoTelefono
     * @return
     */
    public static boolean validarRegistroRecogedor(EditText oTextoUsuario, EditText oTextoClave, EditText oTextoTelefono) {
        boolean bValidado = true;

        String sUsuario = oTextoUsuario.getText().toString();
        if (TextUtils.isEmpty(sUsuario)) {
            oTextoUsuario.setError("Requerido");
            bValidado = false;
        } else {
            if (sUsuario.length() < 8) {
                oTextoUsuario.setError("Mínimo 8 carácteres");
                bValidado = false;
            } else {

                oTextoUsuario.setError(null);
            }
        }

        String sClave = oTextoClave.getText().toString();
        if (TextUtils.isEmpty(sClave)) {
            oTextoClave.setError("Requerido");
            bValidado = false;
        } else {
            if (sClave.length() < 8) {
                oTextoClave.setError("Mínimo 8 carácteres");
                bValidado = false;
            } else {
                oTextoClave.setError(null);
            }
        }

        String sTelefono = oTextoTelefono.getText().toString();

        if (TextUtils.isEmpty(sTelefono)) {
            oTextoTelefono.setError("Requerido");
            bValidado = false;
        } else {
            if (sTelefono.length() < 9) {
                oTextoTelefono.setError("El celular tiene 9 dígitos");
                bValidado = false;
            } else {
                oTextoTelefono.setError(null);
            }
        }

        return bValidado;
    }

    /**
     * Método encargado de validar que la caja de texto para ingresar la contraseña
     * no se encuentre vacía
     *
     * @param oTextoClave
     * @return
     */
    public static boolean validarCambioContraseña(EditText oTextoClave) {
        boolean bValidado = true;

        String sClave = oTextoClave.getText().toString();
        if (TextUtils.isEmpty(sClave)) {
            oTextoClave.setError("Requerido");
            bValidado = false;
        } else {
            oTextoClave.setError(null);
        }

        return bValidado;
    }

    /**
     * Método encargado de validar que la caja de texto para ingresar el usuario
     * no se encuentre vacía
     *
     * @param oTextoUsuario
     * @return
     */
    public static boolean validarCambioUsuario(EditText oTextoUsuario) {
        boolean bValidado = true;

        String sUsuario = oTextoUsuario.getText().toString();
        if (TextUtils.isEmpty(sUsuario)) {
            oTextoUsuario.setError("Requerido");
            bValidado = false;
        } else {
            oTextoUsuario.setError(null);
        }

        return bValidado;
    }

}
