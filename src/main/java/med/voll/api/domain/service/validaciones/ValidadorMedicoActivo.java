package med.voll.api.domain.service.validaciones;

import med.voll.api.domain.entities.Medico;
import med.voll.api.domain.entities.Paciente;
import med.voll.api.domain.interfaces.negocio.IValidadorReservaConsulta;
import med.voll.api.domain.shared.DomainException;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class ValidadorMedicoActivo implements IValidadorReservaConsulta {

    @Override
    public void validar(Paciente paciente, Medico medico, LocalDateTime fecha) {
        if (!medico.estaActivo()) {
            throw new DomainException("El médico seleccionado no está activo");
        }
    }
}
