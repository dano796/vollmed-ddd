package med.voll.api.application.query;

import med.voll.api.domain.entities.Medico;
import med.voll.api.domain.interfaces.repository.IMedicoRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional(readOnly = true)
public class MedicoQueryService {

    private final IMedicoRepository medicoRepository;

    public MedicoQueryService(IMedicoRepository medicoRepository) {
        this.medicoRepository = medicoRepository;
    }

    public Page<Medico> listarMedicos(Pageable paginacion) {
        return medicoRepository.findAll(paginacion);
    }

    public Medico obtenerMedico(Long id) {
        return medicoRepository.getReferenceById(id);
    }
}
