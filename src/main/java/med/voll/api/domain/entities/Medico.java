package med.voll.api.domain.entities;

import jakarta.persistence.*;
import lombok.*;
import med.voll.api.domain.shared.AggregateRoot;
import med.voll.api.domain.value_objects.*;

@Table(name = "medicos")
@Entity(name = "Medico")
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Medico extends AggregateRoot {
    private String nombre;

    @Embedded
    @AttributeOverride(name = "valor", column = @Column(name = "email"))
    private Email email;

    @Embedded
    @AttributeOverride(name = "valor", column = @Column(name = "telefono"))
    private Telefono telefono;

    @Embedded
    @AttributeOverride(name = "valor", column = @Column(name = "documento"))
    private Documento documento;

    private Boolean activo = true;

    @Enumerated(EnumType.STRING)
    private Especialidad especialidad;

    @Embedded
    private Direccion direccion;

    public Medico(String nombre, Email email, Telefono telefono, Documento documento,
                  Especialidad especialidad, Direccion direccion) {
        this.activo = true;
        this.nombre = nombre;
        this.email = email;
        this.telefono = telefono;
        this.documento = documento;
        this.especialidad = especialidad;
        this.direccion = direccion;
    }

    public void actualizarInformacion(String nombre, Documento documento, Direccion direccion) {
        if (nombre != null) {
            this.nombre = nombre;
        }
        if (documento != null) {
            this.documento = documento;
        }
        if (direccion != null) {
            this.direccion = direccion;
        }
    }

    public void desactivar() {
        this.activo = false;
    }

    public boolean estaActivo() {
        return this.activo;
    }

    public boolean tieneEspecialidad(Especialidad especialidad) {
        return this.especialidad.equals(especialidad);
    }
}
