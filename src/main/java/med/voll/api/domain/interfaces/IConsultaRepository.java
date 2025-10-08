package med.voll.api.domain.interfaces;

import med.voll.api.domain.aggregates.Consulta;
import med.voll.api.domain.value_objects.Especialidad;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface IConsultaRepository extends JpaRepository<Consulta, Long> {

    Boolean existsByPacienteIdAndFechaBetween(Long idPaciente, LocalDateTime primerHorario, LocalDateTime ultimoHorario);

    Boolean existsByMedicoIdAndFecha(Long idMedico, LocalDateTime fecha);

    @Query("""
            select case when count(c) > 0 then true else false end from Consulta c 
            where c.paciente.id = :idPaciente 
            and c.fecha = :fecha 
            and c.motivoCancelamiento is null
            """)
    Boolean existsConsultaActivaEnFecha(Long idPaciente, LocalDateTime fecha);

    @Query("""
            select case when count(c) > 0 then true else false end from Consulta c 
            where c.medico.id = :idMedico 
            and c.fecha = :fecha 
            and c.motivoCancelamiento is null
            """)
    Boolean existsConsultaActivaMedicoEnFecha(Long idMedico, LocalDateTime fecha);

    // Métodos agregados para BuscarConsultasQueryHandler
    @Query("""
            select c from Consulta c 
            where (:idPaciente is null or c.paciente.id = :idPaciente)
            and (:idMedico is null or c.medico.id = :idMedico)
            and (:especialidad is null or c.medico.especialidad = :especialidad)
            and (:fechaInicio is null or c.fecha >= :fechaInicio)
            and (:fechaFin is null or c.fecha <= :fechaFin)
            and (:incluirCanceladas = true or c.motivoCancelamiento is null)
            order by c.fecha
            """)
    List<Consulta> buscarConFiltrosComplejos(
        @Param("idPaciente") Long idPaciente,
        @Param("idMedico") Long idMedico,
        @Param("especialidad") Especialidad especialidad,
        @Param("fechaInicio") LocalDateTime fechaInicio,
        @Param("fechaFin") LocalDateTime fechaFin,
        @Param("incluirCanceladas") Boolean incluirCanceladas
    );

    @Query("""
            select c from Consulta c 
            where c.fecha between :ahora and :proximaHora
            and c.motivoCancelamiento is null
            order by c.fecha
            """)
    List<Consulta> findConsultasProximasACaducar(
        @Param("ahora") LocalDateTime ahora,
        @Param("proximaHora") LocalDateTime proximaHora
    );

    // Método conveniente sin parámetros que usa valores por defecto
    default List<Consulta> findConsultasProximasACaducar() {
        LocalDateTime ahora = LocalDateTime.now();
        LocalDateTime proximaHora = ahora.plusHours(1);
        return findConsultasProximasACaducar(ahora, proximaHora);
    }
}
