package med.voll.api.application.usecase;

import med.voll.api.domain.entities.Paciente;
import med.voll.api.domain.interfaces.repository.IPacienteRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional
public class InactivarPacienteUseCase {

    private final IPacienteRepository pacienteRepository;

    public InactivarPacienteUseCase(IPacienteRepository pacienteRepository) {
        this.pacienteRepository = pacienteRepository;
    }

    public void execute(Long id) {
        Paciente paciente = pacienteRepository.getReferenceById(id);
        paciente.inactivar();
    }
}
