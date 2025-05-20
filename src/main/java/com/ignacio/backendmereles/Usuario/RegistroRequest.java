package com.ignacio.backendmereles.Usuario;

public class RegistroRequest {
    private String nombre;
    private String usuario;
    private String password;
    private Permiso permiso;

    public String getNombre() {
        return nombre;
    }

    public String getUsuario() {
        return usuario;
    }

    public String getPassword() {
        return password;
    }

    public Permiso getPermiso() {
        return permiso;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setPermiso(Permiso permiso) {
        this.permiso = permiso;
    }
}
