package aplicacion.liberman.com.wasi.soporte;

public class Generador {
    private static String NUMEROS = "0123456789";

    private static String MAYUSCULAS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";

    private static String MINUSCULAS = "abcdefghijklmnopqrstuvwxyz";

    public static String getUsuario(){
        return getRandom(MINUSCULAS + MAYUSCULAS + NUMEROS, 7);
    }

    public static String getClave(){
        return getRandom(NUMEROS + MAYUSCULAS + MINUSCULAS, 7);
    }

    private static String getRandom(String key, int length) {
        String dato = "";

        for (int i = 0; i < length; i++) {
            dato+=(key.charAt((int)(Math.random() * key.length())));
        }

        return dato;
    }

}
