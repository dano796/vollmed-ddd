package med.voll.api.domain.event;

import med.voll.api.domain.interfaces.negocio.DomainEvent;

import java.time.LocalDateTime;

public record ConsultaReservadaEvent(
    Long consultaId,
    Long pacienteId,
    Long medicoId,
    LocalDateTime fechaConsulta,
    LocalDateTime fechaEvento
) implements DomainEvent {

    public ConsultaReservadaEvent(Long consultaId, Long pacienteId, Long medicoId, LocalDateTime fechaConsulta) {
        this(consultaId, pacienteId, medicoId, fechaConsulta, LocalDateTime.now());
    }
}
