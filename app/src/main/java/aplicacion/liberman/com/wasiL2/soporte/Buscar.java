package aplicacion.liberman.com.wasiL2.soporte;

import java.util.ArrayList;

import aplicacion.liberman.com.wasiL2.contenedor.Usuario;

public class Buscar {

    /**
     * Verificar la existencia de un usuario buscando en un
     * usuario traido de la base de datos en
     * Firebase
     *
     * @param oUsuarioFirebase
     * @param oUsuarioLogin
     * @return
     */
    public static boolean existeUsuario(Usuario oUsuarioFirebase, Usuario oUsuarioLogin) {
        if (oUsuarioFirebase.getCorreo().equals(oUsuarioLogin.getCorreo()) && oUsuarioFirebase.getPerfil() == oUsuarioLogin.getPerfil()) {
            return true;
        }
        return false;
    }

}
