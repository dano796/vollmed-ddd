package med.voll.api.application.service;

import med.voll.api.application.dto.request.DatosActualizacionPaciente;
import med.voll.api.application.dto.request.DatosRegistroPaciente;
import med.voll.api.domain.entities.Paciente;
import med.voll.api.domain.interfaces.repository.IPacienteRepository;
import med.voll.api.domain.value_objects.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class GestionPacienteService {

    @Autowired
    private IPacienteRepository IPacienteRepository;

    public Paciente registrarPaciente(DatosRegistroPaciente datos) {
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
        return IPacienteRepository.save(paciente);
    }

    @Transactional(readOnly = true)
    public Page<Paciente> listarPacientes(Pageable paginacion) {
        return IPacienteRepository.findAll(paginacion);
    }

    public Paciente actualizarPaciente(DatosActualizacionPaciente datos) {
        Paciente paciente = IPacienteRepository.getReferenceById(datos.id());
        Direccion nuevaDireccion = datos.direccion() != null ?
            paciente.getDireccion().actualizar(datos.direccion()) : null;
        Telefono nuevoTelefono = datos.telefono() != null ?
            new Telefono(datos.telefono()) : null;
        paciente.actualizarInformacion(datos.nombre(), nuevoTelefono, nuevaDireccion);
        return paciente;
    }

    public void eliminarPaciente(Long id) {
        Paciente paciente = IPacienteRepository.getReferenceById(id);
        paciente.inactivar();
    }

    @Transactional(readOnly = true)
    public Paciente obtenerPaciente(Long id) {
        return IPacienteRepository.getReferenceById(id);
    }
}
