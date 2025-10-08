package med.voll.api.domain.interfaces.repository;

import med.voll.api.domain.aggregates.Consulta;
import med.voll.api.domain.value_objects.Especialidad;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * Contrato de dominio para acceso a Consultas. Implementación concreta en infraestructura.
 */
public interface IConsultaRepository {

    Consulta save(Consulta consulta);

    Optional<Consulta> findById(Long id);

    /**
     * Se mantiene para los casos donde se necesita un proxy perezoso.
     */
    Consulta getReferenceById(Long id);

    Page<Consulta> findAll(Pageable pageable);

    Boolean existsByPacienteIdAndFechaBetween(Long idPaciente, LocalDateTime primerHorario, LocalDateTime ultimoHorario);

    Boolean existsByMedicoIdAndFecha(Long idMedico, LocalDateTime fecha);

    Boolean existsConsultaActivaEnFecha(Long idPaciente, LocalDateTime fecha);

    Boolean existsConsultaActivaMedicoEnFecha(Long idMedico, LocalDateTime fecha);

    List<Consulta> buscarConFiltrosComplejos(Long idPaciente,
                                             Long idMedico,
                                             Especialidad especialidad,
                                             LocalDateTime fechaInicio,
                                             LocalDateTime fechaFin,
                                             Boolean incluirCanceladas);

    List<Consulta> findConsultasProximasACaducar(LocalDateTime ahora, LocalDateTime proximaHora);

    /**
     * Método conveniente sin parámetros que usa valores por defecto.
     */
    default List<Consulta> findConsultasProximasACaducar() {
        LocalDateTime ahora = LocalDateTime.now();
        LocalDateTime proximaHora = ahora.plusHours(1);
        return findConsultasProximasACaducar(ahora, proximaHora);
    }
}
