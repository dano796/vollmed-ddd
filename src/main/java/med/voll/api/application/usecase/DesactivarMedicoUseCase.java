package med.voll.api.application.usecase;

import med.voll.api.domain.entities.Medico;
import med.voll.api.domain.interfaces.repository.IMedicoRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional
public class DesactivarMedicoUseCase {

    private final IMedicoRepository medicoRepository;

    public DesactivarMedicoUseCase(IMedicoRepository medicoRepository) {
        this.medicoRepository = medicoRepository;
    }

    public void execute(Long id) {
        Medico medico = medicoRepository.getReferenceById(id);
        medico.desactivar();
    }
}
