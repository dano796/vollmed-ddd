package med.voll.api.domain.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import med.voll.api.domain.shared.AggregateRoot;
import med.voll.api.domain.value_objects.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "Paciente")
@Table(name = "pacientes")
public class Paciente extends AggregateRoot {
    private String nombre;

    @Embedded
    @AttributeOverride(name = "valor", column = @Column(name = "email"))
    private Email email;

    @Embedded
    @AttributeOverride(name = "valor", column = @Column(name = "documento"))
    private Documento documento;

    @Embedded
    @AttributeOverride(name = "valor", column = @Column(name = "telefono"))
    private Telefono telefono;

    @Embedded
    private Direccion direccion;

    private Boolean activo;

    public Paciente(String nombre, Email email, Telefono telefono, Documento documento, Direccion direccion) {
        this.activo = true;
        this.nombre = nombre;
        this.email = email;
        this.telefono = telefono;
        this.documento = documento;
        this.direccion = direccion;
    }

    public void actualizarInformacion(String nombre, Telefono telefono, Direccion direccion) {
        if (nombre != null) {
            this.nombre = nombre;
        }
        if (telefono != null) {
            this.telefono = telefono;
        }
        if (direccion != null) {
            this.direccion = direccion;
        }
    }

    public void inactivar() {
        this.activo = false;
    }

    public boolean estaActivo() {
        return this.activo;
    }
}
