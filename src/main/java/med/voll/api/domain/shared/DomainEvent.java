package med.voll.api.domain.shared;

import java.time.LocalDateTime;

public interface DomainEvent {
    LocalDateTime fechaEvento();
}
