package med.voll.api.domain.value_objects;

import jakarta.persistence.Embeddable;
import med.voll.api.domain.shared.DomainException;

import java.util.Objects;

@Embeddable
public final class Documento {
    private final String valor;

    // Constructor sin argumentos SOLO para JPA (protegido)
    protected Documento() {
        this.valor = null; // JPA lo asignará después
    }

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

    // Método inmutable para actualizar el documento
    public Documento actualizar(String nuevoNumero) {
        return new Documento(nuevoNumero);
    }

    public String getValor() {
        return valor;
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
