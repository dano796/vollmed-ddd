package med.voll.api.application.dto.request;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import med.voll.api.domain.value_objects.Especialidad;

import java.time.LocalDateTime;

public record DatosReservaConsulta(
        @NotNull(message = "El ID del paciente es obligatorio")
        Long idPaciente,

        Long idMedico, // Opcional - puede ser null para selección automática

        @NotNull(message = "La fecha de la consulta es obligatoria")
        @Future(message = "La fecha de la consulta debe ser en el futuro")
        LocalDateTime fecha,

        Especialidad especialidad) { // Ahora opcional - puede ser null
}
