package med.voll.api.application.dto.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import med.voll.api.application.dto.DatosDireccion;

public record DatosActualizacionPaciente(
        @NotNull(message = "El ID del paciente es obligatorio")
        @Positive(message = "El ID del paciente debe ser un número positivo")
        Long id,

        String nombre, // Opcional en actualización

        @Pattern(regexp = "\\d{8,15}", message = "El teléfono debe contener entre 8 y 15 dígitos")
        String telefono, // Opcional en actualización

        @Valid
        DatosDireccion direccion) { // Opcional en actualización
}
