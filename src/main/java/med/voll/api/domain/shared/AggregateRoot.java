package med.voll.api.domain.shared;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import med.voll.api.domain.interfaces.negocio.DomainEvent;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@MappedSuperclass
@Getter
@EqualsAndHashCode(of = "id")
public abstract class AggregateRoot {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Transient
    private final List<DomainEvent> domainEvents = new ArrayList<>();

    protected AggregateRoot() {}

    protected AggregateRoot(Long id) {
        this.id = id;
    }

    public boolean isNew() {
        return this.id == null;
    }

    protected void addDomainEvent(DomainEvent event) {
        this.domainEvents.add(event);
    }

    public List<DomainEvent> getDomainEvents() {
        return Collections.unmodifiableList(domainEvents);
    }

    public void clearDomainEvents() {
        this.domainEvents.clear();
    }
}
