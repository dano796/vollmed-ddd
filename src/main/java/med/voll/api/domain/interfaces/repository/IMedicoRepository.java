package med.voll.api.domain.interfaces.repository;

import med.voll.api.domain.entities.Medico;
import med.voll.api.domain.value_objects.Especialidad;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * Contrato de dominio para acceso a Medicos. Sin dependencias de JPA concreta (no extiende JpaRepository).
 * La implementación concreta vive en la capa de infraestructura y delega a Spring Data.
 */
public interface IMedicoRepository {

    Medico save(Medico medico);

    Optional<Medico> findById(Long id);

    /**
     * Conservamos este método porque la capa de aplicación lo utiliza para obtener un proxy perezoso.
     * La implementación delegará en el JpaRepository subyacente.
     */
    Medico getReferenceById(Long id);

    Page<Medico> findAll(Pageable pageable);

    Page<Medico> findByActivoTrue(Pageable pageable);

    /**
     * Devuelve un subconjunto aleatorio (limitado por el Pageable) de médicos disponibles
     * para la especialidad y fecha indicadas.
     */
    List<Medico> findMedicosDisponiblesPorEspecialidadYFecha(Especialidad especialidad, LocalDateTime fecha, Pageable pageable);
}
