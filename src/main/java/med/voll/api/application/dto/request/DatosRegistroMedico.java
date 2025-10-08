package med.voll.api.application.dto.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import med.voll.api.application.dto.DatosDireccion;
import med.voll.api.domain.value_objects.Especialidad;

public record DatosRegistroMedico(
        @NotBlank(message = "El nombre es obligatorio")
        String nombre,

        @NotBlank(message = "El email es obligatorio")
        @Email(message = "El formato del email no es válido")
        String email,

        @NotBlank(message = "El teléfono es obligatorio")
        @Pattern(regexp = "\\d{8,15}", message = "El teléfono debe contener entre 8 y 15 dígitos")
        String telefono,

        @NotBlank(message = "El documento es obligatorio")
        @Pattern(regexp = "\\d{8,14}", message = "El documento debe contener entre 8 y 14 dígitos")
        String documento,

        @NotNull(message = "La especialidad es obligatoria")
        Especialidad especialidad,

        @NotNull(message = "La dirección es obligatoria")
        @Valid
        DatosDireccion direccion) {
}
