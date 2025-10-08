package med.voll.api.infrastructure.repository.adapters;

import lombok.RequiredArgsConstructor;
import med.voll.api.domain.entities.Medico;
import med.voll.api.domain.interfaces.repository.IMedicoRepository;
import med.voll.api.domain.value_objects.Especialidad;
import med.voll.api.infrastructure.repository.springdata.SpringDataMedicoRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class MedicoRepositoryAdapter implements IMedicoRepository {

    private final SpringDataMedicoRepository delegate;

    @Override
    public Medico save(Medico medico) {
        return delegate.save(medico);
    }

    @Override
    public Optional<Medico> findById(Long id) {
        return delegate.findById(id);
    }

    @Override
    public Medico getReferenceById(Long id) {
        return delegate.getReferenceById(id);
    }

    @Override
    public Page<Medico> findAll(Pageable pageable) {
        return delegate.findAll(pageable);
    }

    @Override
    public Page<Medico> findByActivoTrue(Pageable pageable) {
        return delegate.findByActivoTrue(pageable);
    }

    @Override
    public List<Medico> findMedicosDisponiblesPorEspecialidadYFecha(Especialidad especialidad, LocalDateTime fecha, Pageable pageable) {
        return delegate.findMedicosDisponiblesPorEspecialidadYFecha(especialidad, fecha, pageable);
    }
}

