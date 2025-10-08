package med.voll.api.application.dto.response;

import med.voll.api.domain.entities.Paciente;

public record DatosListaPaciente(
        Long id,
        String nombre,
        String email,
        String telefono,
        String documento,
        Boolean activo) {

    public DatosListaPaciente(Paciente paciente) {
        this(paciente.getId(),
             paciente.getNombre(),
             paciente.getEmail().getValor(),
             paciente.getTelefono().getValor(),
             paciente.getDocumento().getValor(),
             paciente.getActivo());
    }
}
