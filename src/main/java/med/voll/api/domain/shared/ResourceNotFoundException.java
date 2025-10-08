package med.voll.api.domain.shared;

/**
 * Excepción específica para cuando no se encuentra un recurso
 * Debe devolver 404 Not Found en lugar de 403 Forbidden
 */
public class ResourceNotFoundException extends DomainException {

    public ResourceNotFoundException(String message) {
        super(message);
    }

    public ResourceNotFoundException(String entity, Long id) {
        super(String.format("%s con ID %d no encontrado", entity, id));
    }
}
