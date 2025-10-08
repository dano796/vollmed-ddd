package med.voll.api.domain.service;

import med.voll.api.domain.entities.Medico;
import med.voll.api.domain.entities.Paciente;

import java.time.LocalDateTime;

public interface ValidadorReservaConsulta {
    void validar(Paciente paciente, Medico medico, LocalDateTime fecha);
}
