package com.idat.soporte.dto;

import com.idat.soporte.model.EstadoSolicitud;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record SolicitudRequest(
        @NotBlank(message = "El título es obligatorio")
        @Size(min = 5, max = 100, message = "El título debe tener entre 5 y 100 caracteres")
        String titulo,

        @NotBlank(message = "La descripción es obligatoria")
        String descripcion,

        @NotNull(message = "El cliente es obligatorio")
        @Valid
        ClienteDTO cliente,

        @Valid
        TecnicoDTO tecnico,

        EstadoSolicitud estado
) {
}
