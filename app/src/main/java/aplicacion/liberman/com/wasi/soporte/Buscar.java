package aplicacion.liberman.com.wasi.soporte;

import java.util.ArrayList;

import aplicacion.liberman.com.wasi.contenedor.Usuario;

public class Buscar {

    /**
     * Verificar la existencia de un usuario buscando en un
     * arreglo de usuarios traidos de la base de datos en
     * Firebase
     * @param lista
     * @param usuario
     * @return
     */
    public static boolean existeUsuario(ArrayList<Usuario> lista, Usuario usuario){
        int iPosicion = -1;
        for (Usuario auxiliar : lista){
            if(auxiliar.getNombre().equals(usuario.getNombre()) && auxiliar.getClave().equals(usuario.getClave())){
                usuario.setIdentificador(auxiliar.getIdentificador());
                System.out.println("Idenficiaroda s: "+auxiliar.getIdentificador());
                return true;
            }
        }
        return false;
    }

}
