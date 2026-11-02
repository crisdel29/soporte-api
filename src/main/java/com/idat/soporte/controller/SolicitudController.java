package com.idat.soporte.controller;

import com.idat.soporte.dto.SolicitudRequest;
import com.idat.soporte.dto.SolicitudResponse;
import com.idat.soporte.service.SolicitudService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/solicitudes")
@Tag(name = "Solicitudes", description = "Gestión de solicitudes de soporte técnico")
public class SolicitudController {

    private final SolicitudService solicitudService;

    public SolicitudController(SolicitudService solicitudService) {
        this.solicitudService = solicitudService;
    }

    @Operation(summary = "Listar todas las solicitudes")
    @ApiResponse(responseCode = "200", description = "Lista de solicitudes (puede estar vacía)")
    @GetMapping
    public ResponseEntity<List<SolicitudResponse>> listar() {
        return ResponseEntity.ok(solicitudService.listar());
    }

    @Operation(summary = "Buscar una solicitud por su id")
    @ApiResponse(responseCode = "200", description = "Solicitud encontrada")
    @ApiResponse(responseCode = "404", description = "No existe una solicitud con ese id")
    @GetMapping("/{id}")
    public ResponseEntity<SolicitudResponse> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(solicitudService.buscarPorId(id));
    }

    @Operation(summary = "Registrar una nueva solicitud",
            description = "La solicitud se crea siempre con estado PENDIENTE.")
    @ApiResponse(responseCode = "201", description = "Solicitud creada")
    @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos")
    @PostMapping
    public ResponseEntity<SolicitudResponse> crear(@Valid @RequestBody SolicitudRequest request) {
        SolicitudResponse creada = solicitudService.crear(request);
        URI ubicacion = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(creada.id())
                .toUri();
        return ResponseEntity.created(ubicacion).body(creada);
    }

    @Operation(summary = "Actualizar una solicitud existente",
            description = "No se puede modificar una solicitud que ya está en estado RESUELTA.")
    @ApiResponse(responseCode = "200", description = "Solicitud actualizada")
    @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos")
    @ApiResponse(responseCode = "404", description = "No existe una solicitud con ese id")
    @ApiResponse(responseCode = "409", description = "La solicitud ya fue resuelta y no puede modificarse")
    @PutMapping("/{id}")
    public ResponseEntity<SolicitudResponse> actualizar(@PathVariable Long id,
                                                        @Valid @RequestBody SolicitudRequest request) {
        return ResponseEntity.ok(solicitudService.actualizar(id, request));
    }

    @Operation(summary = "Eliminar una solicitud")
    @ApiResponse(responseCode = "204", description = "Solicitud eliminada")
    @ApiResponse(responseCode = "404", description = "No existe una solicitud con ese id")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        solicitudService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}