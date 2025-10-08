package med.voll.api.application.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import med.voll.api.domain.value_objects.MotivoCancelamiento;

public record DatosCancelamientoConsulta(
        @NotNull(message = "El ID de la consulta es obligatorio")
        @Positive(message = "El ID de la consulta debe ser un número positivo")
        Long idConsulta,

        @NotNull(message = "El motivo de cancelamiento es obligatorio")
        MotivoCancelamiento motivo) {
}
