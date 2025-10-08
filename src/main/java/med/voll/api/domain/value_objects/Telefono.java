package med.voll.api.domain.value_objects;

import jakarta.persistence.Embeddable;
import med.voll.api.domain.shared.DomainException;

import java.util.Objects;
import java.util.regex.Pattern;

@Embeddable
public final class Telefono {
    private static final Pattern TELEFONO_PATTERN =
        Pattern.compile("^[0-9]{8,15}$");

    private final String valor;

    // Constructor sin argumentos SOLO para JPA (protegido)
    protected Telefono() {
        this.valor = null; // JPA lo asignará después
    }

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

    // Método inmutable para actualizar el teléfono
    public Telefono actualizar(String nuevoNumero) {
        return new Telefono(nuevoNumero);
    }

    public String getValor() {
        return valor;
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
