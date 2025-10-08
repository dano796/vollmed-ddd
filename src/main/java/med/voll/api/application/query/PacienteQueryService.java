package med.voll.api.application.query;

import med.voll.api.domain.entities.Paciente;
import med.voll.api.domain.interfaces.repository.IPacienteRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional(readOnly = true)
public class PacienteQueryService {

    private final IPacienteRepository pacienteRepository;

    public PacienteQueryService(IPacienteRepository pacienteRepository) {
        this.pacienteRepository = pacienteRepository;
    }

    public Page<Paciente> listarPacientes(Pageable paginacion) {
        return pacienteRepository.findAll(paginacion);
    }

    public Paciente obtenerPaciente(Long id) {
        return pacienteRepository.getReferenceById(id);
    }
}
