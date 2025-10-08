package med.voll.api.application.dto.request;

import jakarta.validation.constraints.NotNull;
import med.voll.api.domain.value_objects.MotivoCancelamiento;

public record DatosCancelamientoConsulta(
        Long idConsulta,
        @NotNull MotivoCancelamiento motivo) {
}
