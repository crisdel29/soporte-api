package com.idat.soporte.mapper;

import com.idat.soporte.dto.ClienteDTO;
import com.idat.soporte.dto.SolicitudRequest;
import com.idat.soporte.dto.SolicitudResponse;
import com.idat.soporte.dto.TecnicoDTO;
import com.idat.soporte.model.Cliente;
import com.idat.soporte.model.Solicitud;
import com.idat.soporte.model.Tecnico;

public final class SolicitudMapper {

    private SolicitudMapper() {
    }

    //Request -> Modelo (los ids los asigna el service)
    public static Solicitud toEntity(SolicitudRequest request) {
        Solicitud solicitud = new Solicitud();
        solicitud.setTitulo(request.titulo());
        solicitud.setDescripcion(request.descripcion());
        solicitud.setCliente(new Cliente(null,
                request.cliente().nombre(),
                request.cliente().correo()));

        if (request.tecnico() != null) {
            solicitud.setTecnico(new Tecnico(null,
                    request.tecnico().nombre(),
                    request.tecnico().especialidad(),
                    request.tecnico().correo()));
        }
        if (request.estado() != null) {
            solicitud.setEstado(request.estado());
        }
        return solicitud;
    }

    //Modelo -> Response
    public static SolicitudResponse toResponse(Solicitud solicitud) {
        Cliente c = solicitud.getCliente();
        Tecnico t = solicitud.getTecnico();

        ClienteDTO clienteDTO = new ClienteDTO(c.getId(), c.getNombre(), c.getCorreo());
        TecnicoDTO tecnicoDTO = (t == null) ? null
                : new TecnicoDTO(t.getId(), t.getNombre(), t.getEspecialidad(), t.getCorreo());

        return new SolicitudResponse(
                solicitud.getId(),
                solicitud.getTitulo(),
                solicitud.getDescripcion(),
                clienteDTO,
                tecnicoDTO,
                solicitud.getEstado()
        );
    }
}
