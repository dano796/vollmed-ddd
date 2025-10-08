package med.voll.api.domain.value_objects;

import jakarta.persistence.Embeddable;
import med.voll.api.domain.shared.DomainException;

import java.util.Objects;
import java.util.regex.Pattern;

@Embeddable
public final class Email {
    private static final Pattern EMAIL_PATTERN =
        Pattern.compile("^[A-Za-z0-9+_.-]+@([A-Za-z0-9.-]+\\.[A-Za-z]{2,})$");

    private final String valor;

    // Constructor sin argumentos SOLO para JPA (protegido)
    protected Email() {
        this.valor = null; // JPA lo asignará después
    }

    public Email(String email) {
        this.validar(email);
        this.valor = email.toLowerCase().trim();
    }

    private void validar(String email) {
        if (email == null || email.trim().isEmpty()) {
            throw new DomainException("El email es obligatorio");
        }
        if (!EMAIL_PATTERN.matcher(email.trim()).matches()) {
            throw new DomainException("El formato del email no es válido");
        }
    }

    // Método inmutable para actualizar el email
    public Email actualizar(String nuevoEmail) {
        return new Email(nuevoEmail);
    }

    public String getValor() {
        return valor;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Email email = (Email) o;
        return Objects.equals(valor, email.valor);
    }

    @Override
    public int hashCode() {
        return Objects.hash(valor);
    }

    @Override
    public String toString() {
        return valor;
    }
}
