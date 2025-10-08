package med.voll.api.application.usecase;

import med.voll.api.application.dto.request.DatosActualizacionPaciente;
import med.voll.api.domain.entities.Paciente;
import med.voll.api.domain.interfaces.repository.IPacienteRepository;
import med.voll.api.domain.value_objects.Direccion;
import med.voll.api.domain.value_objects.Telefono;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional
public class ActualizarPacienteUseCase {

    private final IPacienteRepository pacienteRepository;

    public ActualizarPacienteUseCase(IPacienteRepository pacienteRepository) {
        this.pacienteRepository = pacienteRepository;
    }

    public Paciente execute(DatosActualizacionPaciente datos) {
        Paciente paciente = pacienteRepository.getReferenceById(datos.id());

        Direccion nuevaDireccion = datos.direccion() != null ?
            paciente.getDireccion().actualizar(datos.direccion()) : null;
        Telefono nuevoTelefono = datos.telefono() != null ?
            new Telefono(datos.telefono()) : null;

        paciente.actualizarInformacion(datos.nombre(), nuevoTelefono, nuevaDireccion);
        return paciente;
    }
}
