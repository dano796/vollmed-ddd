package med.voll.api.domain.aggregates;

import jakarta.persistence.*;
import lombok.*;
import med.voll.api.domain.shared.AggregateRoot;
import med.voll.api.domain.entities.Medico;
import med.voll.api.domain.entities.Paciente;
import med.voll.api.domain.event.ConsultaCanceladaEvent;
import med.voll.api.domain.event.ConsultaReservadaEvent;
import med.voll.api.domain.value_objects.MotivoCancelamiento;

import java.time.LocalDateTime;

@Table(name = "consultas")
@Entity(name = "Consulta")
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Consulta extends AggregateRoot {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "medico_id")
    private Medico medico;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "paciente_id")
    private Paciente paciente;

    private LocalDateTime fecha;

    @Column(name = "motivo_cancelamiento")
    @Enumerated(EnumType.STRING)
    private MotivoCancelamiento motivoCancelamiento;

    public Consulta(Medico medico, Paciente paciente, LocalDateTime fecha) {
        this.medico = medico;
        this.paciente = paciente;
        this.fecha = fecha;
        this.motivoCancelamiento = null;

        // Emitir evento de consulta reservada
        addDomainEvent(new ConsultaReservadaEvent(
                this.getId(),
                paciente.getId(),
                medico.getId(),
                fecha
        ));
    }

    public void cancelar(MotivoCancelamiento motivo) {
        if (this.motivoCancelamiento != null) {
            throw new IllegalStateException("La consulta ya está cancelada");
        }
        this.motivoCancelamiento = motivo;

        // Emitir evento de consulta cancelada
        addDomainEvent(new ConsultaCanceladaEvent(
                this.getId(),
                this.paciente.getId(),
                this.medico.getId(),
                motivo,
                this.fecha
        ));
    }

    public boolean estaCancelada() {
        return this.motivoCancelamiento != null;
    }

    public boolean esEnFecha(LocalDateTime fecha) {
        return this.fecha.equals(fecha);
    }

    public boolean esPaciente(Long pacienteId) {
        return this.paciente.getId().equals(pacienteId);
    }

    public boolean esMedico(Long medicoId) {
        return this.medico.getId().equals(medicoId);
    }
}
