package med.voll.api.domain.event;

import med.voll.api.domain.interfaces.negocio.DomainEvent;
import med.voll.api.domain.value_objects.MotivoCancelamiento;

import java.time.LocalDateTime;

public record ConsultaCanceladaEvent(
    Long consultaId,
    Long pacienteId,
    Long medicoId,
    MotivoCancelamiento motivo,
    LocalDateTime fechaConsulta,
    LocalDateTime fechaEvento
) implements DomainEvent {

    public ConsultaCanceladaEvent(Long consultaId, Long pacienteId, Long medicoId, MotivoCancelamiento motivo, LocalDateTime fechaConsulta) {
        this(consultaId, pacienteId, medicoId, motivo, fechaConsulta, LocalDateTime.now());
    }
}
