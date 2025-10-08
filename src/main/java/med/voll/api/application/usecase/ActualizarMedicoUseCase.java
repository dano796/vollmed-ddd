package med.voll.api.application.usecase;

import med.voll.api.application.dto.request.DatosActualizarMedico;
import med.voll.api.domain.entities.Medico;
import med.voll.api.domain.interfaces.repository.IMedicoRepository;
import med.voll.api.domain.value_objects.Direccion;
import med.voll.api.domain.value_objects.Documento;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional
public class ActualizarMedicoUseCase {

    private final IMedicoRepository medicoRepository;

    public ActualizarMedicoUseCase(IMedicoRepository medicoRepository) {
        this.medicoRepository = medicoRepository;
    }

    public Medico execute(DatosActualizarMedico datos) {
        Medico medico = medicoRepository.getReferenceById(datos.id());

        Direccion nuevaDireccion = datos.direccion() != null ?
            medico.getDireccion().actualizar(datos.direccion()) : null;
        Documento nuevoDocumento = datos.documento() != null ?
            new Documento(datos.documento()) : null;

        medico.actualizarInformacion(datos.nombre(), nuevoDocumento, nuevaDireccion);
        return medico;
    }
}
