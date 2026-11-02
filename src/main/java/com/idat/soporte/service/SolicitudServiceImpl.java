package com.idat.soporte.service;

import com.idat.soporte.dto.SolicitudRequest;
import com.idat.soporte.dto.SolicitudResponse;
import com.idat.soporte.exception.ReglaNegocioException;
import com.idat.soporte.exception.SolicitudNoEncontradaException;
import com.idat.soporte.mapper.SolicitudMapper;
import com.idat.soporte.model.EstadoSolicitud;
import com.idat.soporte.model.Solicitud;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class SolicitudServiceImpl implements SolicitudService {

    // Simula las tablas de la base de datos
    private final List<Solicitud> solicitudes = new ArrayList<>();

    // Simulan los autoincrementales
    private final AtomicLong contadorSolicitud = new AtomicLong(1);
    private final AtomicLong contadorCliente = new AtomicLong(1);
    private final AtomicLong contadorTecnico = new AtomicLong(1);

    @Override
    public List<SolicitudResponse> listar() {
        return solicitudes.stream()
                .map(SolicitudMapper::toResponse)
                .toList();
    }

    @Override
    public SolicitudResponse buscarPorId(Long id) {
        return SolicitudMapper.toResponse(obtener(id));
    }

    @Override
    public SolicitudResponse crear(SolicitudRequest request) {
        Solicitud solicitud = SolicitudMapper.toEntity(request);
        solicitud.setId(contadorSolicitud.getAndIncrement());
        solicitud.setEstado(EstadoSolicitud.PENDIENTE);
        asignarIds(solicitud);
        solicitudes.add(solicitud);
        return SolicitudMapper.toResponse(solicitud);
    }

    @Override
    public SolicitudResponse actualizar(Long id, SolicitudRequest request) {
        Solicitud existente = obtener(id);

        if (existente.getEstado() == EstadoSolicitud.RESUELTA) {
            throw new ReglaNegocioException(
                    "No se puede modificar una solicitud que ya fue resuelta");
        }

        Solicitud datos = SolicitudMapper.toEntity(request);
        existente.setTitulo(datos.getTitulo());
        existente.setDescripcion(datos.getDescripcion());
        existente.setCliente(datos.getCliente());
        existente.setTecnico(datos.getTecnico());
        if (request.estado() != null) {
            existente.setEstado(request.estado());
        }
        asignarIds(existente);
        return SolicitudMapper.toResponse(existente);
    }

    @Override
    public void eliminar(Long id) {
        solicitudes.remove(obtener(id));
    }

    // Busca la entidad interna o lanza la excepción 404
    private Solicitud obtener(Long id) {
        return solicitudes.stream()
                .filter(s -> s.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new SolicitudNoEncontradaException(id));
    }

    private void asignarIds(Solicitud solicitud) {
        solicitud.getCliente().setId(contadorCliente.getAndIncrement());
        if (solicitud.getTecnico() != null) {
            solicitud.getTecnico().setId(contadorTecnico.getAndIncrement());
        }
    }
}