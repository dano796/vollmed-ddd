package med.voll.api.domain.service;

import med.voll.api.domain.entities.Medico;
import med.voll.api.domain.entities.Paciente;
import med.voll.api.domain.shared.DomainException;
import med.voll.api.domain.interfaces.IConsultaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class ValidadorPacienteSinConsulta implements ValidadorReservaConsulta {

    @Autowired
    private IConsultaRepository IConsultaRepository;

    public void validar(Paciente paciente, Medico medico, LocalDateTime fecha) {
        var primerHorario = fecha.withHour(7);
        var ultimoHorario = fecha.withHour(18);

        var pacienteTieneOtraConsultaEnElDia = IConsultaRepository.existsByPacienteIdAndFechaBetween(
            paciente.getId(), primerHorario, ultimoHorario);

        if (pacienteTieneOtraConsultaEnElDia) {
            throw new DomainException("No se permite más de una consulta en el mismo día para el paciente");
        }
    }
}
