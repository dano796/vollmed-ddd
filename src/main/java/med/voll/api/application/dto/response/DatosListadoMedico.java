package med.voll.api.application.dto.response;

import med.voll.api.domain.entities.Medico;
import med.voll.api.domain.value_objects.Especialidad;

public record DatosListadoMedico(
        Long id,
        String nombre,
        String email,
        String documento,
        Especialidad especialidad,
        Boolean activo) {

    public DatosListadoMedico(Medico medico) {
        this(medico.getId(), medico.getNombre(), medico.getEmail().getValor(),
             medico.getDocumento().getValor(), medico.getEspecialidad(), medico.getActivo());
    }
}
