package med.voll.api.application.service;

import med.voll.api.application.dto.request.DatosActualizarMedico;
import med.voll.api.application.dto.request.DatosRegistroMedico;
import med.voll.api.domain.entities.Medico;
import med.voll.api.domain.interfaces.repository.IMedicoRepository;
import med.voll.api.domain.value_objects.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class GestionMedicoService {

    @Autowired
    private IMedicoRepository IMedicoRepository;

    public Medico registrarMedico(DatosRegistroMedico datos) {
        Direccion direccion = new Direccion(datos.direccion());
        Email email = new Email(datos.email());
        Telefono telefono = new Telefono(datos.telefono());
        Documento documento = new Documento(datos.documento());

        Medico medico = new Medico(
            datos.nombre(),
            email,
            telefono,
            documento,
            datos.especialidad(),
            direccion
        );
        return IMedicoRepository.save(medico);
    }

    @Transactional(readOnly = true)
    public Page<Medico> listarMedicos(Pageable paginacion) {
        return IMedicoRepository.findAll(paginacion);
    }

    public Medico actualizarMedico(DatosActualizarMedico datos) {
        Medico medico = IMedicoRepository.getReferenceById(datos.id());
        Direccion nuevaDireccion = datos.direccion() != null ?
            medico.getDireccion().actualizar(datos.direccion()) : null;
        Documento nuevoDocumento = datos.documento() != null ?
            new Documento(datos.documento()) : null;
        medico.actualizarInformacion(datos.nombre(), nuevoDocumento, nuevaDireccion);
        return medico;
    }

    public void eliminarMedico(Long id) {
        Medico medico = IMedicoRepository.getReferenceById(id);
        medico.desactivar();
    }

    @Transactional(readOnly = true)
    public Medico obtenerMedico(Long id) {
        return IMedicoRepository.getReferenceById(id);
    }
}
