package com.idat.soporte.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public class Cliente {
    private Long id;

    @NotBlank(message = "El nombre del cliente es obligatorio")
    private String nombre;

    @NotBlank(message = "El correo del cliente es obligatorio")
    @Email(message = "El correo no tiene un formato válido")
    private  String correo;

    public Cliente (){
    }
    public Cliente(Long id, String nombre, String correo) {
        this.id = id;
        this.nombre = nombre;
        this.correo = correo;
    }

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }
}
