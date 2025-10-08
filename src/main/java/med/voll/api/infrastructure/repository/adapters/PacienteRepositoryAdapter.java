package med.voll.api.infrastructure.repository.adapters;

import lombok.RequiredArgsConstructor;
import med.voll.api.domain.entities.Paciente;
import med.voll.api.domain.interfaces.repository.IPacienteRepository;
import med.voll.api.infrastructure.repository.springdata.SpringDataPacienteRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class PacienteRepositoryAdapter implements IPacienteRepository {

    private final SpringDataPacienteRepository delegate;

    @Override
    public Paciente save(Paciente paciente) {
        return delegate.save(paciente);
    }

    @Override
    public Optional<Paciente> findById(Long id) {
        return delegate.findById(id);
    }

    @Override
    public Paciente getReferenceById(Long id) {
        return delegate.getReferenceById(id);
    }

    @Override
    public Page<Paciente> findAll(Pageable pageable) {
        return delegate.findAll(pageable);
    }

    @Override
    public Page<Paciente> findByActivoTrue(Pageable pageable) {
        return delegate.findByActivoTrue(pageable);
    }
}

