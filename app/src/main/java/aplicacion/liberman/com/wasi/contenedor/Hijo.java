package aplicacion.liberman.com.wasi.contenedor;

import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

public class Hijo {
    private ImageView foto;
    private String apellido;
    private String nombre;
    private String grado;

    public Hijo(ImageView foto, String apellido, String nombre, String grado) {
        this.foto = foto;
        this.apellido = apellido;
        this.nombre = nombre;
        this.grado = grado;
    }

    public ImageView getFoto() {
        return foto;
    }

    public void setFoto(ImageView foto) {
        this.foto = foto;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getGrado() {
        return grado;
    }

    public void setGrado(String grado) {
        this.grado = grado;
    }

    public static List<Hijo> listarHijos(){
        List<Hijo> lista = new ArrayList<>();
        lista.add(new Hijo(null, "ape1", "nom1","5to"));
        lista.add(new Hijo(null, "ape2", "nom2","2to"));
        lista.add(new Hijo(null, "ape3", "nom3","3to"));
        return lista;
    }
}
