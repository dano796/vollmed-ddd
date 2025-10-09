package med.voll.api.domain.interfaces.negocio;

import med.voll.api.domain.entities.Medico;
import med.voll.api.domain.entities.Paciente;

import java.time.LocalDateTime;

public interface IValidadorReservaConsulta {
    void validar(Paciente paciente, Medico medico, LocalDateTime fecha);
}
