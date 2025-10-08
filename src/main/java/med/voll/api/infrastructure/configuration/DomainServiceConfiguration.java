package med.voll.api.infrastructure.configuration;

import med.voll.api.domain.interfaces.IConsultaRepository;
import med.voll.api.domain.interfaces.IMedicoRepository;
import med.voll.api.domain.interfaces.IPacienteRepository;
import med.voll.api.domain.service.CancelacionConsultaService;
import med.voll.api.domain.service.ReservaConsultaService;
import med.voll.api.domain.service.ValidadorCancelacionConsulta;
import med.voll.api.domain.service.ValidadorReservaConsulta;
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
