package med.voll.api.domain.interfaces.negocio;

import med.voll.api.domain.aggregates.Consulta;
import med.voll.api.domain.value_objects.MotivoCancelamiento;

public interface IValidadorCancelacionConsulta {
    void validar(Consulta consulta, MotivoCancelamiento motivo);
}
