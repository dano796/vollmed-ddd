package med.voll.api.domain.value_objects;

import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.NoArgsConstructor;
import med.voll.api.domain.shared.DomainException;

import java.util.Objects;
import java.util.regex.Pattern;

@Embeddable
@Getter
@NoArgsConstructor
public class Email {
    private static final Pattern EMAIL_PATTERN =
        Pattern.compile("^[A-Za-z0-9+_.-]+@([A-Za-z0-9.-]+\\.[A-Za-z]{2,})$");

    private String valor;

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
