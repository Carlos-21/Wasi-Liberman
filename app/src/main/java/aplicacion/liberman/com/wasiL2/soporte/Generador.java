package aplicacion.liberman.com.wasiL2.soporte;

public class Generador {
    private static String NUMEROS = "0123456789";

    private static String MAYUSCULAS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";

    private static String MINUSCULAS = "abcdefghijklmnopqrstuvwxyz";

    private static int NUMERO_GENERADOR = 8;

    public static String getUsuario() {
        return getRandom(MINUSCULAS + NUMEROS, NUMERO_GENERADOR);
    }

    public static String getClave() {
        return getRandom(NUMEROS + MAYUSCULAS + MINUSCULAS, NUMERO_GENERADOR);
    }

    private static String getRandom(String key, int length) {
        String dato = "";

        for (int i = 0; i < length; i++) {
            dato += (key.charAt((int) (Math.random() * key.length())));
        }

        return dato;
    }

}
