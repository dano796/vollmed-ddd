package med.voll.api.application.usecase;

import med.voll.api.application.dto.request.DatosRegistroPaciente;
import med.voll.api.domain.entities.Paciente;
import med.voll.api.domain.interfaces.repository.IPacienteRepository;
import med.voll.api.domain.value_objects.*;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional
public class RegistrarPacienteUseCase {

    private final IPacienteRepository pacienteRepository;

    public RegistrarPacienteUseCase(IPacienteRepository pacienteRepository) {
        this.pacienteRepository = pacienteRepository;
    }

    public Paciente execute(DatosRegistroPaciente datos) {
        Direccion direccion = new Direccion(datos.direccion());
        Email email = new Email(datos.email());
        Telefono telefono = new Telefono(datos.telefono());
        Documento documento = new Documento(datos.documento());

        Paciente paciente = new Paciente(
            datos.nombre(),
            email,
            telefono,
            documento,
            direccion
        );

        return pacienteRepository.save(paciente);
    }
}
