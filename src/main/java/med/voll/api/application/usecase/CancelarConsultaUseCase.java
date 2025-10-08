package med.voll.api.application.usecase;

import med.voll.api.application.dto.request.DatosCancelamientoConsulta;
import med.voll.api.domain.aggregates.Consulta;
import med.voll.api.domain.service.CancelacionConsultaService;
import med.voll.api.infrastructure.service.DomainEventPublisher;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional
public class CancelarConsultaUseCase {

    private final CancelacionConsultaService cancelacionService;
    private final DomainEventPublisher eventPublisher;

    public CancelarConsultaUseCase(CancelacionConsultaService cancelacionService,
                                  DomainEventPublisher eventPublisher) {
        this.cancelacionService = cancelacionService;
        this.eventPublisher = eventPublisher;
    }

    public void execute(DatosCancelamientoConsulta datos) {
        Consulta consulta = cancelacionService.cancelar(datos.idConsulta(), datos.motivo());
        eventPublisher.publishEvents(consulta);
    }
}
