package aplicacion.liberman.com.wasi.contenedor;

import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

public class Hijo {
    private String apellidos;
    private String imagen;
    private String grado;
    private String nombres;

    public Hijo(String apellidos, String imagen, String grado, String nombres) {
        this.apellidos = apellidos;
        this.imagen = imagen;
        this.grado = grado;
        this.nombres = nombres;
    }

    public Hijo() {

    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public String getGrado() {
        return grado;
    }

    public void setGrado(String grado) {
        this.grado = grado;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }
}
