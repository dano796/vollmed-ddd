package med.voll.api.domain.interfaces.repository;

import med.voll.api.domain.entities.Paciente;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Contrato de dominio para Paciente. Implementación concreta en infraestructura.
 */
public interface IPacienteRepository {

    Paciente save(Paciente paciente);

    Optional<Paciente> findById(Long id);

    Paciente getReferenceById(Long id);

    Page<Paciente> findAll(Pageable pageable);

    Page<Paciente> findByActivoTrue(Pageable pageable);
}
