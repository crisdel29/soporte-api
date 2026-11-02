package com.idat.soporte.service;

import com.idat.soporte.dto.SolicitudRequest;
import com.idat.soporte.dto.SolicitudResponse;
import java.util.List;

public interface SolicitudService {

    List<SolicitudResponse> listar();

    SolicitudResponse buscarPorId(Long id);

    SolicitudResponse crear(SolicitudRequest request);

    SolicitudResponse actualizar(Long id, SolicitudRequest request);

    void eliminar(Long id);
}
