package med.voll.api.domain.event;

import med.voll.api.domain.interfaces.negocio.IDomainEvent;

import java.time.LocalDateTime;

public record ConsultaReservadaEvent(
    Long consultaId,
    Long pacienteId,
    Long medicoId,
    LocalDateTime fechaConsulta,
    LocalDateTime fechaEvento
) implements IDomainEvent {

    public ConsultaReservadaEvent(Long consultaId, Long pacienteId, Long medicoId, LocalDateTime fechaConsulta) {
        this(consultaId, pacienteId, medicoId, fechaConsulta, LocalDateTime.now());
    }
}
