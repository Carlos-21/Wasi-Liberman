package aplicacion.liberman.com.wasiL2.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class SharedPreferencesUtil {
    private static final String nameFile = "PrefWasi";

    /**
     * Método encargado de guardar parámetros importantes usados por
     * clases en la seguridad del usuario
     *
     * @param context
     * @param correo
     * @param clave
     */
    public static void guardarUsuario(Context context, String correo, String clave) {
        SharedPreferences pref = context.getApplicationContext().getSharedPreferences(nameFile, Context.MODE_PRIVATE);

        Editor editor = pref.edit();
        editor.putString("usuario", correo);
        editor.putString("clave", clave);
        editor.commit();
        editor.apply();
    }

    /**
     * Método encargado de devolder el parámetro usuario
     * guardado en PrefWasi
     *
     * @param context
     * @return
     */
    public static String recuperarCorreo(Context context) {
        SharedPreferences pref = context.getApplicationContext().getSharedPreferences(nameFile, Context.MODE_PRIVATE);

        return pref.getString("usuario", "");
    }

    /**
     * Método encargado de devolder el parámetro clave
     * guardado en PrefWasi
     *
     * @param context
     * @return
     */
    public static String recuperarClave(Context context) {
        SharedPreferences pref = context.getApplicationContext().getSharedPreferences(nameFile, Context.MODE_PRIVATE);

        return pref.getString("clave", "");
    }

}
