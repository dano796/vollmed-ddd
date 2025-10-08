package med.voll.api.application.dto.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import med.voll.api.application.dto.DatosDireccion;

public record DatosActualizarMedico(
        @NotNull(message = "El ID del médico es obligatorio")
        @Positive(message = "El ID del médico debe ser un número positivo")
        Long id,

        String nombre, // Opcional en actualización

        @Pattern(regexp = "\\d{8,14}", message = "El documento debe contener entre 8 y 14 dígitos")
        String documento, // Opcional en actualización

        @Valid
        DatosDireccion direccion) { // Opcional en actualización
}
