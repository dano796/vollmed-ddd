package med.voll.api.application.usecase;

import med.voll.api.application.dto.request.DatosRegistroMedico;
import med.voll.api.domain.entities.Medico;
import med.voll.api.domain.interfaces.repository.IMedicoRepository;
import med.voll.api.domain.value_objects.*;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional
public class RegistrarMedicoUseCase {

    private final IMedicoRepository medicoRepository;

    public RegistrarMedicoUseCase(IMedicoRepository medicoRepository) {
        this.medicoRepository = medicoRepository;
    }

    public Medico execute(DatosRegistroMedico datos) {
        // Crear value objects
        Direccion direccion = new Direccion(datos.direccion());
        Email email = new Email(datos.email());
        Telefono telefono = new Telefono(datos.telefono());
        Documento documento = new Documento(datos.documento());

        // Crear entidad de dominio
        Medico medico = new Medico(
            datos.nombre(),
            email,
            telefono,
            documento,
            datos.especialidad(),
            direccion
        );

        // Persistir
        return medicoRepository.save(medico);
    }
}
