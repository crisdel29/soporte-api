package com.idat.soporte.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record TecnicoDTO(
        Long id,

        @NotBlank(message = "El nombre del técnico es obligatorio")
        String nombre,

        @NotBlank(message = "La especialidad es obligatoria")
        String especialidad,

        @Email(message = "El correo no tiene un formato válido")
        String correo
) {
}
