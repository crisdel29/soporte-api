package com.idat.soporte.model;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class Solicitud {

    private Long id;

    @NotBlank(message = "El título es obligatorio")
    @Size(min = 5, max = 100, message = "El título debe tener entre 5 y 100 caracteres")
    private String titulo;

    @NotBlank(message = "La descripción es obligatoria")
    private String descripcion;

    @NotNull(message = "El cliente es obligatorio")
    @Valid
    private Cliente cliente;

    private Tecnico tecnico;

    private EstadoSolicitud estado = EstadoSolicitud.PENDIENTE;

    public Solicitud() {
    }

    public Solicitud (Long id, String titulo, String descripcion, Cliente cliente, Tecnico tecnico, EstadoSolicitud estado) {
        this.id = id;
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.cliente = cliente;
        this.tecnico = tecnico;
        this.estado = estado;
    }

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }
    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescripcion() {
        return descripcion;
    }
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Cliente getCliente() {
        return cliente;
    }
    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Tecnico getTecnico() {
        return tecnico;
    }
    public void setTecnico(Tecnico tecnico) {
        this.tecnico = tecnico;
    }

    public EstadoSolicitud getEstado() {
        return estado;
    }
    public void setEstado(EstadoSolicitud estado) {
        this.estado = estado;
    }
}
