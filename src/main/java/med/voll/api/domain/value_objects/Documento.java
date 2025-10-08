package med.voll.api.domain.value_objects;

import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.NoArgsConstructor;
import med.voll.api.domain.shared.DomainException;

import java.util.Objects;

@Embeddable
@Getter
@NoArgsConstructor
public class Documento {
    private String valor;

    public Documento(String numero) {
        this.validar(numero);
        this.valor = numero.replaceAll("[^0-9]", ""); // Solo números
    }

    private void validar(String numero) {
        if (numero == null || numero.trim().isEmpty()) {
            throw new DomainException("El número de documento es obligatorio");
        }
        String numeroLimpio = numero.replaceAll("[^0-9]", "");
        if (numeroLimpio.length() < 8 || numeroLimpio.length() > 14) {
            throw new DomainException("El número de documento debe tener entre 8 y 14 dígitos");
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Documento documento = (Documento) o;
        return Objects.equals(valor, documento.valor);
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
