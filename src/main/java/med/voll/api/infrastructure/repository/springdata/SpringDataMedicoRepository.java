package med.voll.api.infrastructure.repository.springdata;

import med.voll.api.domain.entities.Medico;
import med.voll.api.domain.value_objects.Especialidad;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface SpringDataMedicoRepository extends JpaRepository<Medico, Long> {

    // Método existente en antiguo contrato
    org.springframework.data.domain.Page<Medico> findByActivoTrue(org.springframework.data.domain.Pageable pageable);

    @Query("""
            select m from Medico m 
            where m.activo = true 
            and m.especialidad = :especialidad 
            and m.id not in (
                select c.medico.id from Consulta c 
                where c.fecha = :fecha 
                and c.motivoCancelamiento is null
            ) 
            order by function('RANDOM') 
            """)
    List<Medico> findMedicosDisponiblesPorEspecialidadYFecha(@Param("especialidad") Especialidad especialidad,
                                                              @Param("fecha") LocalDateTime fecha,
                                                              Pageable pageable);
}

