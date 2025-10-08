package med.voll.api.domain.service.validaciones;

import med.voll.api.domain.interfaces.negocio.ValidadorReservaConsulta;
import med.voll.api.domain.shared.DomainException;
import med.voll.api.domain.entities.Medico;
import med.voll.api.domain.entities.Paciente;
import med.voll.api.domain.interfaces.repository.IConsultaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class ValidadorMedicoConOtraConsulta implements ValidadorReservaConsulta {

    @Autowired
    private IConsultaRepository IConsultaRepository;

    @Override
    public void validar(Paciente paciente, Medico medico, LocalDateTime fecha) {
        if (IConsultaRepository.existsConsultaActivaMedicoEnFecha(medico.getId(), fecha)) {
            throw new DomainException("El médico ya tiene otra consulta programada en el mismo horario");
        }
    }
}
