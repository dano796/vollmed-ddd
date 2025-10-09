package med.voll.api.domain.shared;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import med.voll.api.domain.interfaces.negocio.IDomainEvent;

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
    private final List<IDomainEvent> domainEvents = new ArrayList<>();

    protected AggregateRoot() {}

    protected AggregateRoot(Long id) {
        this.id = id;
    }

    public boolean isNew() {
        return this.id == null;
    }

    protected void addDomainEvent(IDomainEvent event) {
        this.domainEvents.add(event);
    }

    public List<IDomainEvent> getDomainEvents() {
        return Collections.unmodifiableList(domainEvents);
    }

    public void clearDomainEvents() {
        this.domainEvents.clear();
    }
}
