package med.voll.api.domain.value_objects;

import jakarta.persistence.Embeddable;
import med.voll.api.application.dto.DatosDireccion;
import med.voll.api.domain.shared.DomainException;

import java.util.Objects;

@Embeddable
public final class Direccion {
    private final String calle;
    private final String distrito;
    private final String ciudad;
    private final String numero;
    private final String complemento;

    // Constructor sin argumentos SOLO para JPA (protegido)
    protected Direccion() {
        this.calle = null;
        this.distrito = null;
        this.ciudad = null;
        this.numero = null;
        this.complemento = null;
    }

    public Direccion(String calle, String distrito, String ciudad, String numero, String complemento) {
        this.validar(calle, distrito, ciudad, numero);
        this.calle = calle;
        this.distrito = distrito;
        this.ciudad = ciudad;
        this.numero = numero;
        this.complemento = complemento;
    }

    public Direccion(DatosDireccion direccion) {
        this(direccion.calle(), direccion.distrito(), direccion.ciudad(),
                direccion.numero(), direccion.complemento());
    }

    private void validar(String calle, String distrito, String ciudad, String numero) {
        if (calle == null || calle.trim().isEmpty()) {
            throw new DomainException("La calle es obligatoria");
        }
        if (distrito == null || distrito.trim().isEmpty()) {
            throw new DomainException("El distrito es obligatorio");
        }
        if (ciudad == null || ciudad.trim().isEmpty()) {
            throw new DomainException("La ciudad es obligatoria");
        }
        if (numero == null || numero.trim().isEmpty()) {
            throw new DomainException("El número es obligatorio");
        }
    }

    public Direccion actualizar(DatosDireccion nuevaDireccion) {
        return new Direccion(
                nuevaDireccion.calle() != null ? nuevaDireccion.calle() : this.calle,
                nuevaDireccion.distrito() != null ? nuevaDireccion.distrito() : this.distrito,
                nuevaDireccion.ciudad() != null ? nuevaDireccion.ciudad() : this.ciudad,
                nuevaDireccion.numero() != null ? nuevaDireccion.numero() : this.numero,
                nuevaDireccion.complemento() != null ? nuevaDireccion.complemento() : this.complemento
        );
    }

    // Getters para campos inmutables finales
    public String getCalle() {
        return calle;
    }

    public String getDistrito() {
        return distrito;
    }

    public String getCiudad() {
        return ciudad;
    }

    public String getNumero() {
        return numero;
    }

    public String getComplemento() {
        return complemento;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Direccion direccion = (Direccion) o;
        return Objects.equals(calle, direccion.calle) &&
                Objects.equals(distrito, direccion.distrito) &&
                Objects.equals(ciudad, direccion.ciudad) &&
                Objects.equals(numero, direccion.numero) &&
                Objects.equals(complemento, direccion.complemento);
    }

    @Override
    public int hashCode() {
        return Objects.hash(calle, distrito, ciudad, numero, complemento);
    }

    @Override
    public String toString() {
        return String.format("%s %s, %s - %s%s",
                calle, numero, distrito, ciudad,
                complemento != null && !complemento.trim().isEmpty() ? " (" + complemento + ")" : "");
    }
}
