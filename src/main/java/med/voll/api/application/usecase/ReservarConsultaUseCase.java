package med.voll.api.application.usecase;

import med.voll.api.application.dto.request.DatosReservaConsulta;
import med.voll.api.domain.aggregates.Consulta;
import med.voll.api.domain.service.ReservaConsultaService;
import med.voll.api.infrastructure.service.DomainEventPublisher;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional
public class ReservarConsultaUseCase {

    private final ReservaConsultaService reservaService;
    private final DomainEventPublisher eventPublisher;

    public ReservarConsultaUseCase(ReservaConsultaService reservaService,
                                  DomainEventPublisher eventPublisher) {
        this.reservaService = reservaService;
        this.eventPublisher = eventPublisher;
    }

    public Consulta execute(DatosReservaConsulta datos) {
        Consulta consulta = reservaService.reservar(
            datos.idPaciente(),
            datos.idMedico(),
            datos.especialidad(),
            datos.fecha()
        );

        eventPublisher.publishEvents(consulta);
        return consulta;
    }
}
