package med.voll.api.domain.service;

import med.voll.api.domain.aggregates.Consulta;
import med.voll.api.domain.entities.Medico;
import med.voll.api.domain.entities.Paciente;
import med.voll.api.domain.interfaces.negocio.ValidadorReservaConsulta;
import med.voll.api.domain.interfaces.repository.IConsultaRepository;
import med.voll.api.domain.interfaces.repository.IMedicoRepository;
import med.voll.api.domain.interfaces.repository.IPacienteRepository;
import med.voll.api.domain.shared.DomainException;
import med.voll.api.domain.value_objects.Especialidad;

import java.time.LocalDateTime;
import java.util.List;

public class ReservaConsultaService {

    private final IConsultaRepository consultaRepository;
    private final IMedicoRepository medicoRepository;
    private final IPacienteRepository pacienteRepository;
    private final List<ValidadorReservaConsulta> validadores;

    public ReservaConsultaService(IConsultaRepository consultaRepository,
                                 IMedicoRepository medicoRepository,
                                 IPacienteRepository pacienteRepository,
                                 List<ValidadorReservaConsulta> validadores) {
        this.consultaRepository = consultaRepository;
        this.medicoRepository = medicoRepository;
        this.pacienteRepository = pacienteRepository;
        this.validadores = validadores;
    }

    public Consulta reservar(Long idPaciente, Long idMedico, Especialidad especialidad, LocalDateTime fecha) {
        // Verificar existencia del paciente
        Paciente paciente = pacienteRepository.findById(idPaciente)
                .orElseThrow(() -> new DomainException("Paciente no encontrado"));

        // Elegir médico
        Medico medico = elegirMedico(idMedico, especialidad, fecha);
        if (medico == null) {
            throw new DomainException("No existe un medico disponible para la especialidad y fecha informada");
        }

        // Ejecutar todas las validaciones
        validadores.forEach(validador -> validador.validar(paciente, medico, fecha));

        // Crear y guardar la consulta
        Consulta consulta = new Consulta(medico, paciente, fecha);
        consulta = consultaRepository.save(consulta);

        consulta.marcarComoReservada();

        return consulta;
    }

    private Medico elegirMedico(Long idMedico, Especialidad especialidad, LocalDateTime fecha) {
        if (idMedico != null) {
            return medicoRepository.findById(idMedico)
                    .orElseThrow(() -> new DomainException("Medico no encontrado"));
        }

        if (especialidad == null) {
            throw new DomainException("Especialidad requerida cuando no se especifica un medico");
        }

        // Usar PageRequest para obtener solo 1 médico aleatorio
        List<Medico> medicosDisponibles = medicoRepository.findMedicosDisponiblesPorEspecialidadYFecha(
            especialidad,
            fecha,
            org.springframework.data.domain.PageRequest.of(0, 1)
        );

        return medicosDisponibles.isEmpty() ? null : medicosDisponibles.get(0);
    }
}
