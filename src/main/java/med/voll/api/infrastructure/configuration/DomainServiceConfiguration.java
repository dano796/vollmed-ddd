package med.voll.api.infrastructure.configuration;

import med.voll.api.domain.interfaces.negocio.ICancelacionConsultaService;
import med.voll.api.domain.interfaces.negocio.IReservaConsultaService;
import med.voll.api.domain.interfaces.repository.IConsultaRepository;
import med.voll.api.domain.interfaces.repository.IMedicoRepository;
import med.voll.api.domain.interfaces.repository.IPacienteRepository;
import med.voll.api.domain.service.CancelacionConsultaService;
import med.voll.api.domain.service.ReservaConsultaService;
import med.voll.api.domain.interfaces.negocio.IValidadorCancelacionConsulta;
import med.voll.api.domain.interfaces.negocio.IValidadorReservaConsulta;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class DomainServiceConfiguration {

    @Bean
    public IReservaConsultaService reservaConsultaService(
            IConsultaRepository consultaRepository,
            IMedicoRepository medicoRepository,
            IPacienteRepository pacienteRepository,
            List<IValidadorReservaConsulta> validadores) {
        return new ReservaConsultaService(consultaRepository, medicoRepository, pacienteRepository, validadores);
    }

    @Bean
    public ICancelacionConsultaService cancelacionConsultaService(
            IConsultaRepository consultaRepository,
            List<IValidadorCancelacionConsulta> validadores) {
        return new CancelacionConsultaService(consultaRepository, validadores);
    }
}
