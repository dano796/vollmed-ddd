package med.voll.api.application.service;

import med.voll.api.application.dto.request.DatosCancelamientoConsulta;
import med.voll.api.application.dto.request.DatosReservaConsulta;
import med.voll.api.domain.aggregates.Consulta;
import med.voll.api.domain.interfaces.IConsultaRepository;
import med.voll.api.domain.service.CancelacionConsultaService;
import med.voll.api.domain.service.ReservaConsultaService;
import med.voll.api.infrastructure.service.DomainEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class GestionConsultaService {

    private final ReservaConsultaService reservaService;
    private final CancelacionConsultaService cancelacionService;
    private final DomainEventPublisher eventPublisher;
    private final IConsultaRepository consultaRepository;

    public GestionConsultaService(ReservaConsultaService reservaService,
                                 CancelacionConsultaService cancelacionService,
                                 DomainEventPublisher eventPublisher,
                                 IConsultaRepository consultaRepository) {
        this.reservaService = reservaService;
        this.cancelacionService = cancelacionService;
        this.eventPublisher = eventPublisher;
        this.consultaRepository = consultaRepository;
    }

    // ✅ SIMPLIFICADO: Recibe DTO directamente
    public Consulta reservarConsulta(DatosReservaConsulta datos) {
        Consulta consulta = reservaService.reservar(
            datos.idPaciente(),
            datos.idMedico(),
            datos.especialidad(),
            datos.fecha()
        );

        eventPublisher.publishEvents(consulta);
        return consulta;
    }

    // ✅ SIMPLIFICADO: Recibe DTO directamente
    public void cancelarConsulta(DatosCancelamientoConsulta datos) {
        Consulta consulta = cancelacionService.cancelar(datos.idConsulta(), datos.motivo());
        eventPublisher.publishEvents(consulta);
    }

    @Transactional(readOnly = true)
    public Page<Consulta> listarConsultas(Pageable paginacion) {
        return consultaRepository.findAll(paginacion);
    }

    @Transactional(readOnly = true)
    public Consulta obtenerConsultaPorId(Long id) {
        return consultaRepository.findById(id)
            .orElseThrow(() -> new med.voll.api.domain.shared.ResourceNotFoundException("Consulta", id));
    }

}
