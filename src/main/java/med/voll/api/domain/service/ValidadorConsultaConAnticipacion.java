package med.voll.api.domain.service;

import med.voll.api.domain.interfaces.negocio.ValidadorReservaConsulta;
import med.voll.api.domain.shared.DomainException;
import med.voll.api.domain.entities.Medico;
import med.voll.api.domain.entities.Paciente;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalDateTime;

@Component
@Order(2)
public class ValidadorConsultaConAnticipacion implements ValidadorReservaConsulta {

    @Override
    public void validar(Paciente paciente, Medico medico, LocalDateTime fecha) {
        var ahora = LocalDateTime.now();
        var diferenciaEnMinutos = Duration.between(ahora, fecha).toMinutes();

        if (diferenciaEnMinutos < 30) {
            throw new DomainException("Las consultas deben ser reservadas con al menos 30 minutos de anticipación");
        }
    }
}
