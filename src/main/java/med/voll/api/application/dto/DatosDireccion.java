package med.voll.api.application.dto;

public record DatosDireccion(
        String calle,
        String distrito,
        String ciudad,
        String numero,
        String complemento) {
}
