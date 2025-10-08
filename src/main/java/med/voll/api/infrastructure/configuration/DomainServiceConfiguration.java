package med.voll.api.infrastructure.configuration;

import med.voll.api.domain.interfaces.repository.IConsultaRepository;
import med.voll.api.domain.interfaces.repository.IMedicoRepository;
import med.voll.api.domain.interfaces.repository.IPacienteRepository;
import med.voll.api.domain.service.CancelacionConsultaService;
import med.voll.api.domain.service.ReservaConsultaService;
import med.voll.api.domain.interfaces.negocio.ValidadorCancelacionConsulta;
import med.voll.api.domain.interfaces.negocio.ValidadorReservaConsulta;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class DomainServiceConfiguration {

    @Bean
    public ReservaConsultaService reservaConsultaService(
            IConsultaRepository consultaRepository,
            IMedicoRepository medicoRepository,
            IPacienteRepository pacienteRepository,
            List<ValidadorReservaConsulta> validadores) {
        return new ReservaConsultaService(consultaRepository, medicoRepository, pacienteRepository, validadores);
    }

    @Bean
    public CancelacionConsultaService cancelacionConsultaService(
            IConsultaRepository consultaRepository,
            List<ValidadorCancelacionConsulta> validadores) {
        return new CancelacionConsultaService(consultaRepository, validadores);
    }
}
