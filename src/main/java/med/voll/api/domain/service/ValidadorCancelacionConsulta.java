package med.voll.api.domain.service;

import med.voll.api.domain.aggregates.Consulta;
import med.voll.api.domain.value_objects.MotivoCancelamiento;

public interface ValidadorCancelacionConsulta {
    void validar(Consulta consulta, MotivoCancelamiento motivo);
}
