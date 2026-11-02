package com.idat.soporte.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record ClienteDTO(
    Long id,

    @NotBlank(message = "El nombre del cliente es obligatorio")
    String nombre,

    @NotBlank(message = "El correo del cliente es obligatorio")
    @Email(message = "El correo no tiene un formato válido")
    String correo
){
}
