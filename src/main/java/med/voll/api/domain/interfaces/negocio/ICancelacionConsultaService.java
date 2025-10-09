package med.voll.api.domain.interfaces.negocio;

import med.voll.api.domain.aggregates.Consulta;
import med.voll.api.domain.value_objects.MotivoCancelamiento;

/**
 * Interfaz para el servicio de dominio que maneja la lógica compleja de cancelación de consultas.
 * Define el contrato para aplicar reglas de negocio y validaciones durante la cancelación.
 */
public interface ICancelacionConsultaService {

    /**
     * Cancela una consulta aplicando todas las validaciones y reglas de negocio.
     *
     * @param idConsulta ID de la consulta a cancelar
     * @param motivo Motivo de la cancelación
     * @return La consulta cancelada con el estado actualizado
     * @throws med.voll.api.domain.shared.DomainException si no se puede cancelar la consulta
     */
    Consulta cancelar(Long idConsulta, MotivoCancelamiento motivo);
}
