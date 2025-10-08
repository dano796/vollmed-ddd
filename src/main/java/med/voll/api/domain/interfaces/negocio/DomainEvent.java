package med.voll.api.domain.interfaces.negocio;

import java.time.LocalDateTime;

public interface DomainEvent {
    LocalDateTime fechaEvento();
}
