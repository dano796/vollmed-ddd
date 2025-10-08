package med.voll.api.infrastructure.configuration;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.ConstraintViolationException;
import med.voll.api.domain.shared.DomainException;
import med.voll.api.domain.shared.ResourceNotFoundException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // Errores de validación (400 Bad Request)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationErrors(MethodArgumentNotValidException ex) {
        List<String> errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(FieldError::getDefaultMessage)
                .collect(Collectors.toList());

        ErrorResponse errorResponse = new ErrorResponse(
                HttpStatus.BAD_REQUEST.value(),
                "Errores de validación",
                errors,
                LocalDateTime.now()
        );

        return ResponseEntity.badRequest().body(errorResponse);
    }

    // Errores de dominio (400 Bad Request)
    @ExceptionHandler(DomainException.class)
    public ResponseEntity<ErrorResponse> handleDomainException(DomainException ex) {
        ErrorResponse errorResponse = new ErrorResponse(
                HttpStatus.BAD_REQUEST.value(),
                "Error de regla de negocio",
                List.of(ex.getMessage()),
                LocalDateTime.now()
        );

        return ResponseEntity.badRequest().body(errorResponse);
    }

    // Recurso no encontrado específico (404 Not Found)
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleResourceNotFound(ResourceNotFoundException ex) {
        ErrorResponse errorResponse = new ErrorResponse(
                HttpStatus.NOT_FOUND.value(),
                "Recurso no encontrado",
                List.of(ex.getMessage()),
                LocalDateTime.now()
        );

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
    }

    // Entidad no encontrada (404 Not Found)
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleEntityNotFound(EntityNotFoundException ex) {
        ErrorResponse errorResponse = new ErrorResponse(
                HttpStatus.NOT_FOUND.value(),
                "Recurso no encontrado",
                List.of("El recurso solicitado no existe"),
                LocalDateTime.now()
        );

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
    }

    // Errores de autenticación (401 Unauthorized)
    @ExceptionHandler({AuthenticationException.class, BadCredentialsException.class})
    public ResponseEntity<ErrorResponse> handleAuthenticationException(Exception ex) {
        ErrorResponse errorResponse = new ErrorResponse(
                HttpStatus.UNAUTHORIZED.value(),
                "Error de autenticación",
                List.of("Credenciales inválidas"),
                LocalDateTime.now()
        );

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorResponse);
    }

    // Errores de autorización (403 Forbidden) - SOLO para problemas reales de permisos
    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ErrorResponse> handleAccessDenied(AccessDeniedException ex) {
        ErrorResponse errorResponse = new ErrorResponse(
                HttpStatus.FORBIDDEN.value(),
                "Acceso denegado",
                List.of("No tienes permisos para acceder a este recurso"),
                LocalDateTime.now()
        );

        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(errorResponse);
    }

    // Errores de formato JSON (400 Bad Request)
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorResponse> handleInvalidJson(HttpMessageNotReadableException ex) {
        ErrorResponse errorResponse = new ErrorResponse(
                HttpStatus.BAD_REQUEST.value(),
                "Formato de datos inválido",
                List.of("El formato JSON enviado es inválido"),
                LocalDateTime.now()
        );

        return ResponseEntity.badRequest().body(errorResponse);
    }

    // Errores de tipo de parámetro (400 Bad Request)
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ErrorResponse> handleTypeMismatch(MethodArgumentTypeMismatchException ex) {
        ErrorResponse errorResponse = new ErrorResponse(
                HttpStatus.BAD_REQUEST.value(),
                "Tipo de parámetro inválido",
                List.of("El parámetro '" + ex.getName() + "' tiene un tipo inválido"),
                LocalDateTime.now()
        );

        return ResponseEntity.badRequest().body(errorResponse);
    }

    // Errores de integridad de datos (409 Conflict)
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ErrorResponse> handleDataIntegrityViolation(DataIntegrityViolationException ex) {
        ErrorResponse errorResponse = new ErrorResponse(
                HttpStatus.CONFLICT.value(),
                "Conflicto de datos",
                List.of("Ya existe un registro con los mismos datos únicos"),
                LocalDateTime.now()
        );

        return ResponseEntity.status(HttpStatus.CONFLICT).body(errorResponse);
    }

    // Errores de violación de constraints (400 Bad Request)
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ErrorResponse> handleConstraintViolation(ConstraintViolationException ex) {
        List<String> errors = ex.getConstraintViolations()
                .stream()
                .map(violation -> violation.getMessage())
                .collect(Collectors.toList());

        ErrorResponse errorResponse = new ErrorResponse(
                HttpStatus.BAD_REQUEST.value(),
                "Violación de restricciones",
                errors,
                LocalDateTime.now()
        );

        return ResponseEntity.badRequest().body(errorResponse);
    }

    // Error genérico para casos no manejados (500 Internal Server Error)
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGenericException(Exception ex) {
        ErrorResponse errorResponse = new ErrorResponse(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "Error interno del servidor",
                List.of("Ha ocurrido un error inesperado"),
                LocalDateTime.now()
        );

        // Log del error para debugging
        ex.printStackTrace();

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
    }

    // Clase interna para la respuesta de error
    public static class ErrorResponse {
        private int status;
        private String error;
        private List<String> messages;
        private LocalDateTime timestamp;

        public ErrorResponse(int status, String error, List<String> messages, LocalDateTime timestamp) {
            this.status = status;
            this.error = error;
            this.messages = messages;
            this.timestamp = timestamp;
        }

        // Getters
        public int getStatus() { return status; }
        public String getError() { return error; }
        public List<String> getMessages() { return messages; }
        public LocalDateTime getTimestamp() { return timestamp; }
    }
}
