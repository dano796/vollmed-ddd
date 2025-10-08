package med.voll.api.domain.service;

import med.voll.api.domain.aggregates.Consulta;
import med.voll.api.domain.interfaces.negocio.ValidadorCancelacionConsulta;
import med.voll.api.domain.shared.DomainException;
import med.voll.api.domain.value_objects.MotivoCancelamiento;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalDateTime;

@Component
public class ValidadorHorarioAntecedencia implements ValidadorCancelacionConsulta {

    public void validar(Consulta consulta, MotivoCancelamiento motivo) {
        var ahora = LocalDateTime.now();
        var diferenciasEnHoras = Duration.between(ahora, consulta.getFecha()).toHours();

        if (diferenciasEnHoras < 24) {
            throw new DomainException("La consulta solamente puede ser cancelada con antecedencia mínima de 24h!");
        }
    }
}
