package med.voll.api.application.dto.response;

import med.voll.api.domain.aggregates.Consulta;
import med.voll.api.domain.value_objects.MotivoCancelamiento;

import java.time.LocalDateTime;

public record DatosDetalleConsulta(
        Long id,
        Long idPaciente,
        String nombrePaciente,
        Long idMedico,
        String nombreMedico,
        LocalDateTime fecha,
        MotivoCancelamiento motivoCancelamiento) {

    public DatosDetalleConsulta(Consulta consulta) {
        this(consulta.getId(),
             consulta.getPaciente().getId(),
             consulta.getPaciente().getNombre(),
             consulta.getMedico().getId(),
             consulta.getMedico().getNombre(),
             consulta.getFecha(),
             consulta.getMotivoCancelamiento());
    }
}
