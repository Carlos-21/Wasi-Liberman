package aplicacion.liberman.com.wasi.contenedor;

import java.util.ArrayList;

import aplicacion.liberman.com.wasi.soporte.Codificar;

public class Usuario {
    private String clave;
    private String nombre;
    private int perfil;
    private String identificador;

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

    public int getPerfil() {
        return perfil;
    }

    public void setPerfil(int perfil) {
        this.perfil = perfil;
    }

    public String getIdentificador() {
        return identificador;
    }

    public void setIdentificador(String identificador) {
        this.identificador = identificador;
    }

}
