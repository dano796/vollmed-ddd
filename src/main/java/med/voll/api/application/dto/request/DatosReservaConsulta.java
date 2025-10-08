package med.voll.api.application.dto.request;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import med.voll.api.domain.value_objects.Especialidad;

import java.time.LocalDateTime;

public record DatosReservaConsulta(
        Long idPaciente,
        Long idMedico,
        LocalDateTime fecha,
        Especialidad especialidad) {
}
