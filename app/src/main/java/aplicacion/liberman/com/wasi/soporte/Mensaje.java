package aplicacion.liberman.com.wasi.soporte;

public class Mensaje {
    public static String nombre;
    public static String mensajeUsuarioCorrecto = "Acceso concedido";
    public static String mensajeUsuarioIncorrecto = "Usuario o contreña incorrectos";

    public static String tituloCerrarSesion = "Cerrar sesión";
    public static String mensajeCerrarSesion = "¿Esta seguro de Cerrar Sesion?";

    public static String tituloAsignarRecogedor = "Asignar recogedor";
    public static String mensajeAsignarRecogedor = "¿Esta seguro de confirmar Recogedor?";

    public static String tituloPermitirSalida = "Permitir salida";
    public static String mensajePermitirSalida = "¿Estas seguro que desea permitir la salida de paramN?";

    public static String tituloPermitirMovilidad = "Permitir movilidad";
    public static String mensajePermitirMovilidad = "¿Estas seguro de permitir movilidad?";

    public static String hora;
    public static String fecha;
    public static String mensajeSalida = "Salida permitida: \nHora : paramH \nDía : paramD";

    public static String mensajeSalidaPermitida = "Se ha registro la salida con éxito";

    public static String mensajeSalidaHecha = "paramH, ya tiene permiso de salida";

    public static String tituloPermitirEntrega = "Entrega de alumno";
    public static String mensajePermitirEntrega = "¿Está en la casa de paramH?";

    public static String mensajeEntregaHecha = "paramH, ya esta en su casa";

    public static String mensajeTextoRecogeor = "Su identificador es : paramI\nSu usuario es: paramU\nSu contraseña es: paramC";

    public static String mensajeAutorizacion = "Autentificación correcta";
    public static String mensajeNoAutorizacion = "Autentificación incorrecta";

    public static String tituloQuitarPermisos = "Quitar permisos";
    public static String mensajeQuitarPermisos = "¿Desea quitar los permisos a los niños?";
    public static String mensajePermisosQuitados = "Se ha quitado los permisos con éxito";
    public static String mensajeFueraRango = "Funcionalidad fuera de hora";

    public static String mensajeFirebaseRecogedor = "Se acabo tiempo de acceso";
}
