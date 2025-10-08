package med.voll.api.domain.service;

import med.voll.api.domain.entities.Medico;
import med.voll.api.domain.entities.Paciente;
import med.voll.api.domain.interfaces.negocio.ValidadorReservaConsulta;
import med.voll.api.domain.shared.DomainException;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@Order(1)
public class ValidadorHorarioFuncionamiento implements ValidadorReservaConsulta {

    public void validar(Paciente paciente, Medico medico, LocalDateTime fecha) {
        var domingo = fecha.getDayOfWeek().getValue() == 7;
        var antesDelApertura = fecha.getHour() < 7;
        var despuesDelCierre = fecha.getHour() > 18;

        if (domingo || antesDelApertura || despuesDelCierre) {
            throw new DomainException("Horario de atención de la clínica es de lunes a sábado, de 07:00 a 19:00 horas");
        }
    }
}
