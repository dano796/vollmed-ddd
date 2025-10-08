package med.voll.api.infrastructure.repository.adapters;

import lombok.RequiredArgsConstructor;
import med.voll.api.domain.aggregates.Consulta;
import med.voll.api.domain.interfaces.repository.IConsultaRepository;
import med.voll.api.domain.value_objects.Especialidad;
import med.voll.api.infrastructure.repository.springdata.SpringDataConsultaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class ConsultaRepositoryAdapter implements IConsultaRepository {

    private final SpringDataConsultaRepository delegate;

    @Override
    public Consulta save(Consulta consulta) {
        return delegate.save(consulta);
    }

    @Override
    public Optional<Consulta> findById(Long id) {
        return delegate.findById(id);
    }

    @Override
    public Consulta getReferenceById(Long id) {
        return delegate.getReferenceById(id);
    }

    @Override
    public Boolean existsByPacienteIdAndFechaBetween(Long idPaciente, LocalDateTime primerHorario, LocalDateTime ultimoHorario) {
        return delegate.existsByPacienteIdAndFechaBetween(idPaciente, primerHorario, ultimoHorario);
    }

    @Override
    public Boolean existsByMedicoIdAndFecha(Long idMedico, LocalDateTime fecha) {
        return delegate.existsByMedicoIdAndFecha(idMedico, fecha);
    }

    @Override
    public Boolean existsConsultaActivaEnFecha(Long idPaciente, LocalDateTime fecha) {
        return delegate.existsConsultaActivaEnFecha(idPaciente, fecha);
    }

    @Override
    public Boolean existsConsultaActivaMedicoEnFecha(Long idMedico, LocalDateTime fecha) {
        return delegate.existsConsultaActivaMedicoEnFecha(idMedico, fecha);
    }

    @Override
    public List<Consulta> buscarConFiltrosComplejos(Long idPaciente, Long idMedico, Especialidad especialidad, LocalDateTime fechaInicio, LocalDateTime fechaFin, Boolean incluirCanceladas) {
        return delegate.buscarConFiltrosComplejos(idPaciente, idMedico, especialidad, fechaInicio, fechaFin, incluirCanceladas);
    }

    @Override
    public List<Consulta> findConsultasProximasACaducar(LocalDateTime ahora, LocalDateTime proximaHora) {
        return delegate.findConsultasProximasACaducar(ahora, proximaHora);
    }

    @Override
    public Page<Consulta> findAll(Pageable pageable) {
        return delegate.findAll(pageable);
    }
}
