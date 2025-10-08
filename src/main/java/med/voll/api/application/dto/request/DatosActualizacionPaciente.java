package med.voll.api.application.dto.request;

import med.voll.api.application.dto.DatosDireccion;

public record DatosActualizacionPaciente(
        Long id,
        String nombre,
        String telefono,
        DatosDireccion direccion) {
}
