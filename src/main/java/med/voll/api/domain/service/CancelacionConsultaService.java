package med.voll.api.domain.service;

import med.voll.api.domain.aggregates.Consulta;
import med.voll.api.domain.interfaces.IConsultaRepository;
import med.voll.api.domain.shared.DomainException;
import med.voll.api.domain.value_objects.MotivoCancelamiento;

import java.util.List;

public class CancelacionConsultaService {

    private final IConsultaRepository consultaRepository;
    private final List<ValidadorCancelacionConsulta> validadores;

    public CancelacionConsultaService(IConsultaRepository consultaRepository,
                                    List<ValidadorCancelacionConsulta> validadores) {
        this.consultaRepository = consultaRepository;
        this.validadores = validadores;
    }

    public Consulta cancelar(Long idConsulta, MotivoCancelamiento motivo) {
        Consulta consulta = consultaRepository.findById(idConsulta)
                .orElseThrow(() -> new DomainException("El Id informado de la consulta no existe"));

        // Ejecutar todas las validaciones de cancelación
        validadores.forEach(validador -> validador.validar(consulta, motivo));

        // Cancelar la consulta (esto emite el evento automáticamente)
        consulta.cancelar(motivo);
        consultaRepository.save(consulta);

        return consulta; // Devolver para poder publicar eventos
    }
}
