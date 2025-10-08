package med.voll.api.application.dto.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import med.voll.api.application.dto.DatosDireccion;
import med.voll.api.domain.value_objects.Especialidad;

public record DatosRegistroMedico(
        String nombre,
        String email,
        String telefono,
        String documento,
        Especialidad especialidad,
        DatosDireccion direccion) {
}
