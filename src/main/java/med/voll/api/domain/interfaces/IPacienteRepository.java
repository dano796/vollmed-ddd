package med.voll.api.domain.interfaces;

import med.voll.api.domain.entities.Paciente;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IPacienteRepository extends JpaRepository<Paciente, Long> {

    Page<Paciente> findByActivoTrue(Pageable paginacion);
}
