package aplicacion.liberman.com.wasiL2.soporte;

public class Mensaje {
    //Mensaje de la clase LoginFirebase
    public static String sAutenticidadTelefono = "Acceso temporal concedido";
    public static String sNoAutenticidadTelefono = "Datos incorrectos o acceso caducado";
    public static String sAutenticidadCorreo = "Autentificación correcta";
    public static String sNoAutenticidadCorreo = "Autentificación incorrecta";

    //Mensaje de la clase Perfil
    public static String sPerfilRecogedor = "Solo tiene acceso para el perfil de Recogedor";
    public static String sNoPerfilRecogedor = "Su cuenta no tiene acceso para el perfil Recogedor";

    //Mensaje de la Clase Login
    public static String sUsuarioCorrecto = "Acceso concedido";
    public static String sUsuarioIncorrecto = "Usuario o contraseña incorrectos";

    //Mensaje de la Clase ConfirmarRecogedor
    public static String sCodigoEnviado = "Código enviado";
    public static String sCodigoNoEnviado = "Código no enviado";
    public static String sTituloAsignarRecogedor = "Asignar recogedor";
    public static String sMensajeAsignarRecogedor = "¿Esta seguro de confirmar Recogedor?";
    public static String sMensajeRecogedorAsignado = "Recogedor asignado correctamente";

    public static String nombre;

    public static String tituloCerrarSesion = "Cerrar sesión";
    public static String mensajeCerrarSesion = "¿Esta seguro de Cerrar Sesion?";


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

    public static String mensajeTextoRecogeor = "Su usuario es: paramU\nSu contraseña es: paramC";


    public static String tituloQuitarPermisos = "Quitar permisos";
    public static String mensajeQuitarPermisos = "¿Desea quitar los permisos a los niños?";
    public static String mensajePermisosQuitados = "Se ha quitado los permisos con éxito";
    public static String mensajeFueraRango = "Funcionalidad fuera de hora";

    public static String mensajeFirebaseRecogedor = "Se acabo tiempo de acceso";

    //Mensaje para permisos
    public static String tituloPermisosSistema = "Permisos necesarios";
    public static String mensajePermisosSistema = "¿Desea habilitar los permisos necesarios para la aplicación?";

    //Mensaje para cuando un usuario no tiene hijo, alumnos asignados
    public static String alumnosHProfesor = "No hay alumnos habilitados";
    public static String alumnosNHProfesor = "No hay alumnos asignados";
    public static String alumnosHMovilidad = "No hay alumnos habilitados";
    public static String alumnosNHMovilidad = "No hay alumnos asignados";
    public static String alumnosEMovilidad = "No hay alumnos entregados";

}
