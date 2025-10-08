package med.voll.api.application.query;

import med.voll.api.domain.aggregates.Consulta;
import med.voll.api.domain.interfaces.repository.IConsultaRepository;
import med.voll.api.domain.shared.ResourceNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional(readOnly = true)
public class ConsultaQueryService {

    private final IConsultaRepository consultaRepository;

    public ConsultaQueryService(IConsultaRepository consultaRepository) {
        this.consultaRepository = consultaRepository;
    }

    public Page<Consulta> listarConsultas(Pageable paginacion) {
        return consultaRepository.findAll(paginacion);
    }

    public Consulta obtenerConsultaPorId(Long id) {
        return consultaRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Consulta", id));
    }
}
