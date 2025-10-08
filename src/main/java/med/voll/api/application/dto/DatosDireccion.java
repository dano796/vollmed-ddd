package med.voll.api.application.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record DatosDireccion(
        @NotBlank(message = "La calle es obligatoria")
        @Size(max = 100, message = "La calle no puede exceder 100 caracteres")
        String calle,

        @NotBlank(message = "El distrito es obligatorio")
        @Size(max = 50, message = "El distrito no puede exceder 50 caracteres")
        String distrito,

        @NotBlank(message = "La ciudad es obligatoria")
        @Size(max = 50, message = "La ciudad no puede exceder 50 caracteres")
        String ciudad,

        @NotBlank(message = "El número es obligatorio")
        @Size(max = 20, message = "El número no puede exceder 20 caracteres")
        String numero,

        @Size(max = 100, message = "El complemento no puede exceder 100 caracteres")
        String complemento) { // Opcional
}
