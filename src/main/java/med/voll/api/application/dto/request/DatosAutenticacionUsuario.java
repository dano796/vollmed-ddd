package med.voll.api.application.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record DatosAutenticacionUsuario(
        @NotBlank(message = "El login es obligatorio")
        @Size(min = 3, max = 50, message = "El login debe tener entre 3 y 50 caracteres")
        String login,

        @NotBlank(message = "La clave es obligatoria")
        @Size(min = 6, message = "La clave debe tener al menos 6 caracteres")
        String clave) {
}
