package com.idat.soporte.exception;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestControllerAdvice
public class GlobalExceptionHandler {
    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    // 404 - La solicitud no existe
    @ExceptionHandler(SolicitudNoEncontradaException.class)
    public ResponseEntity<ErrorResponse> manejarNoEncontrada(
            SolicitudNoEncontradaException ex, HttpServletRequest request) {
        return construir(HttpStatus.NOT_FOUND, ex.getMessage(), request, Map.of());
    }

    // 409 - Se rompe una regla de negocio
    @ExceptionHandler(ReglaNegocioException.class)
    public ResponseEntity<ErrorResponse> manejarReglaNegocio(
            ReglaNegocioException ex, HttpServletRequest request) {
        return construir(HttpStatus.CONFLICT, ex.getMessage(), request, Map.of());
    }

    // 400 - Fallan las validaciones (@NotBlank, @Email, etc.)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> manejarValidacion(
            MethodArgumentNotValidException ex, HttpServletRequest request) {
        Map<String, String> detalles = new HashMap<>();
        ex.getBindingResult().getFieldErrors()
                .forEach(e -> detalles.put(e.getField(), e.getDefaultMessage()));
        return construir(HttpStatus.BAD_REQUEST,
                "Los datos enviados no son válidos", request, detalles);
    }

    // 400 - JSON mal formado o estado inválido (ej: "estado": "XYZ")
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorResponse> manejarJsonInvalido(
            HttpMessageNotReadableException ex, HttpServletRequest request) {
        log.warn("Cuerpo de petición inválido: {}", ex.getMostSpecificCause().getMessage());
        return construir(HttpStatus.BAD_REQUEST,
                "El cuerpo de la petición es inválido o tiene un valor no permitido",
                request, Map.of());
    }

    // 400 - El id de la URL no es un número (ej: /api/solicitudes/abc)
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ErrorResponse> manejarTipoIncorrecto(
            MethodArgumentTypeMismatchException ex, HttpServletRequest request) {
        return construir(HttpStatus.BAD_REQUEST,
                "El parámetro '" + ex.getName() + "' tiene un formato incorrecto",
                request, Map.of());
    }

    // 405 - Método HTTP no permitido
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<ErrorResponse> manejarMetodoNoPermitido(
            HttpRequestMethodNotSupportedException ex, HttpServletRequest request) {
        return construir(HttpStatus.METHOD_NOT_ALLOWED,
                "El método " + ex.getMethod() + " no está permitido en esta ruta",
                request, Map.of());
    }

    // 500 - Cualquier otro error inesperado
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> manejarGeneral(
            Exception ex, HttpServletRequest request) {
        log.error("Error inesperado en {}", request.getRequestURI(), ex);
        return construir(HttpStatus.INTERNAL_SERVER_ERROR,
                "Ocurrió un error interno en el servidor", request, Map.of());
    }

    private ResponseEntity<ErrorResponse> construir(HttpStatus status, String mensaje,
                                                    HttpServletRequest request,
                                                    Map<String, String> detalles) {
        ErrorResponse cuerpo = new ErrorResponse(
                LocalDateTime.now(),
                status.value(),
                status.getReasonPhrase(),
                mensaje,
                request.getRequestURI(),
                detalles
        );
        return ResponseEntity.status(status).body(cuerpo);
    }
}