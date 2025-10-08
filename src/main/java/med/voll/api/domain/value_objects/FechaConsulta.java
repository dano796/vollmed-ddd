package med.voll.api.domain.value_objects;

import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.NoArgsConstructor;
import med.voll.api.domain.shared.DomainException;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

@Embeddable
@Getter
@NoArgsConstructor
public class FechaConsulta {
    private LocalDateTime valor;

    public FechaConsulta(LocalDateTime fecha) {
        this.validar(fecha);
        this.valor = fecha;
    }

    private void validar(LocalDateTime fecha) {
        if (fecha == null) {
            throw new DomainException("La fecha de consulta es obligatoria");
        }
        if (fecha.isBefore(LocalDateTime.now())) {
            throw new DomainException("La fecha de consulta no puede ser en el pasado");
        }
        // Validar horario de funcionamiento (7:00 - 18:00, lunes a sábado)
        int hora = fecha.getHour();
        int diaSemana = fecha.getDayOfWeek().getValue();

        if (diaSemana == 7) { // Domingo
            throw new DomainException("No se permiten consultas los domingos");
        }
        if (hora < 7 || hora >= 18) {
            throw new DomainException("Las consultas solo están disponibles de 7:00 a 18:00");
        }
    }

    public boolean esAntesDe(FechaConsulta otra) {
        return this.valor.isBefore(otra.valor);
    }

    public boolean esDespuesDe(FechaConsulta otra) {
        return this.valor.isAfter(otra.valor);
    }

    public String formatear() {
        return valor.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FechaConsulta that = (FechaConsulta) o;
        return Objects.equals(valor, that.valor);
    }

    @Override
    public int hashCode() {
        return Objects.hash(valor);
    }
}
