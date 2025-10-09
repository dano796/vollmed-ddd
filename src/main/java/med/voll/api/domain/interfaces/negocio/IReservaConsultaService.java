package med.voll.api.domain.interfaces.negocio;

import med.voll.api.domain.aggregates.Consulta;
import med.voll.api.domain.value_objects.Especialidad;

import java.time.LocalDateTime;

/**
 * Interfaz para el servicio de dominio que maneja la lógica compleja de reserva de consultas.
 * Define el contrato para coordinar operaciones entre Paciente, Medico y Consulta.
 */
public interface IReservaConsultaService {

    /**
     * Reserva una consulta aplicando todas las reglas de negocio y validaciones.
     *
     * @param idPaciente ID del paciente que solicita la consulta
     * @param idMedico ID del médico específico (opcional, puede ser null para selección automática)
     * @param especialidad Especialidad médica requerida (opcional si se especifica médico)
     * @param fecha Fecha y hora de la consulta
     * @return La consulta reservada con todos los datos completos
     * @throws med.voll.api.domain.shared.DomainException si no se puede completar la reserva
     */
    Consulta reservar(Long idPaciente, Long idMedico, Especialidad especialidad, LocalDateTime fecha);
}
