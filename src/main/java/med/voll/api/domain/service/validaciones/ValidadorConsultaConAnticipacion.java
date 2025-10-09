package med.voll.api.domain.service.validaciones;

import med.voll.api.domain.entities.Medico;
import med.voll.api.domain.entities.Paciente;
import med.voll.api.domain.interfaces.negocio.IValidadorReservaConsulta;
import med.voll.api.domain.shared.DomainException;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalDateTime;

@Component
public class ValidadorConsultaConAnticipacion implements IValidadorReservaConsulta {

    @Override
    public void validar(Paciente paciente, Medico medico, LocalDateTime fecha) {
        var ahora = LocalDateTime.now();
        var diferenciaEnMinutos = Duration.between(ahora, fecha).toMinutes();

        if (diferenciaEnMinutos < 30) {
            throw new DomainException("Las consultas deben ser reservadas con al menos 30 minutos de anticipación");
        }
    }
}
