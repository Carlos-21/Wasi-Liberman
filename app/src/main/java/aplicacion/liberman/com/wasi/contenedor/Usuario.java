package aplicacion.liberman.com.wasi.contenedor;

public class Usuario {
    private String nombre;
    private String clave;

    public Usuario(String nombre, String clave) {
        this.nombre = nombre;
        this.clave = clave;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    /**
     * Método encargado de comprobar la existencia de un usuario
     * @param lista
     * @param usuario
     * @return
     */
    public static boolean existeUsuario(Usuario[] lista, Usuario usuario){
        for (Usuario auxiliar : lista){
            if(auxiliar.getNombre().equals(usuario.getNombre()) && auxiliar.getClave().equals(usuario.getClave())){
                return true;
            }
        }
        return false;
    }

}
