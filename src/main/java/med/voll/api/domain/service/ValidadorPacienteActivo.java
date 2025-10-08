package med.voll.api.domain.service;

import med.voll.api.domain.interfaces.negocio.ValidadorReservaConsulta;
import med.voll.api.domain.shared.DomainException;
import med.voll.api.domain.entities.Medico;
import med.voll.api.domain.entities.Paciente;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class ValidadorPacienteActivo implements ValidadorReservaConsulta {

    @Override
    public void validar(Paciente paciente, Medico medico, LocalDateTime fecha) {
        if (!paciente.estaActivo()) {
            throw new DomainException("El paciente no está activo");
        }
    }
}
