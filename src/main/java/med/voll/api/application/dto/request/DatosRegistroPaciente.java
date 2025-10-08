package med.voll.api.application.dto.request;

import med.voll.api.application.dto.DatosDireccion;

public record DatosRegistroPaciente(
        String nombre,
        String email,
        String telefono,
        String documento,
        DatosDireccion direccion) {
}
