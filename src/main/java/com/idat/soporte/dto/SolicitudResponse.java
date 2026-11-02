package com.idat.soporte.dto;

import com.idat.soporte.model.EstadoSolicitud;

public record SolicitudResponse(
        Long id,
        String titulo,
        String descripcion,
        ClienteDTO cliente,
        TecnicoDTO tecnico,
        EstadoSolicitud estado
) {
}
