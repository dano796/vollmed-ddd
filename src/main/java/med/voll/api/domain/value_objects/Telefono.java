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
public class Telefono {
    private static final Pattern TELEFONO_PATTERN =
        Pattern.compile("^[0-9]{8,15}$");

    private String valor;

    public Telefono(String numero) {
        this.validar(numero);
        this.valor = numero.replaceAll("[^0-9]", ""); // Solo números
    }

    private void validar(String numero) {
        if (numero == null || numero.trim().isEmpty()) {
            throw new DomainException("El número de teléfono es obligatorio");
        }
        String numeroLimpio = numero.replaceAll("[^0-9]", "");
        if (!TELEFONO_PATTERN.matcher(numeroLimpio).matches()) {
            throw new DomainException("El número de teléfono debe tener entre 8 y 15 dígitos");
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Telefono telefono = (Telefono) o;
        return Objects.equals(valor, telefono.valor);
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
