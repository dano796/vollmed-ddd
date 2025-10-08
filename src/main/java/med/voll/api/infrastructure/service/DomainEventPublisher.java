package med.voll.api.infrastructure.service;

import med.voll.api.domain.shared.AggregateRoot;
import med.voll.api.domain.shared.DomainEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

@Service
public class DomainEventPublisher {

    private final ApplicationEventPublisher applicationEventPublisher;

    public DomainEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.applicationEventPublisher = applicationEventPublisher;
    }

    public void publishEvents(AggregateRoot aggregate) {
        // Obtener todos los eventos pendientes del agregado
        aggregate.getDomainEvents().forEach(event -> {
            // Publicar cada evento usando Spring Events
            applicationEventPublisher.publishEvent(event);
        });

        // Limpiar eventos después de publicarlos
        aggregate.clearDomainEvents();
    }
}
