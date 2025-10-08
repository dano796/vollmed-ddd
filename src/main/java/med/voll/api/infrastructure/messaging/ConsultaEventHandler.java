package med.voll.api.infrastructure.messaging;

import med.voll.api.domain.event.ConsultaReservadaEvent;
import med.voll.api.domain.event.ConsultaCanceladaEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class ConsultaEventHandler {

    @EventListener
    public void handle(ConsultaReservadaEvent event) {
        // ✅ Acciones cuando se reserva una consulta
        System.out.println("🎉 CONSULTA RESERVADA:");
        System.out.println("   - Consulta ID: " + event.consultaId());
        System.out.println("   - Paciente ID: " + event.pacienteId());
        System.out.println("   - Médico ID: " + event.medicoId());
        System.out.println("   - Fecha: " + event.fechaConsulta());

        // Aquí podrías hacer:
        // - Enviar email de confirmación al paciente
        // - Enviar notificación al médico
        // - Actualizar calendario
        // - Registrar en auditoría
        // - Integrar con sistema externo

        enviarEmailConfirmacion(event.pacienteId(), event.fechaConsulta());
        notificarMedico(event.medicoId(), event.fechaConsulta());
    }

    @EventListener
    public void handle(ConsultaCanceladaEvent event) {
        // ✅ Acciones cuando se cancela una consulta
        System.out.println("❌ CONSULTA CANCELADA:");
        System.out.println("   - Consulta ID: " + event.consultaId());
        System.out.println("   - Motivo: " + event.motivo());
        System.out.println("   - Fecha original: " + event.fechaConsulta());

        // Aquí podrías hacer:
        // - Liberar el slot en el calendario
        // - Notificar cancelación al paciente y médico
        // - Registrar estadísticas de cancelaciones
        // - Aplicar políticas de penalización

        liberarSlotCalendario(event.medicoId(), event.fechaConsulta());
        notificarCancelacion(event.pacienteId(), event.medicoId());
    }

    // Métodos simulados - en la vida real serían servicios reales
    private void enviarEmailConfirmacion(Long pacienteId, java.time.LocalDateTime fecha) {
        // Integración con servicio de email
        System.out.println("📧 Email enviado al paciente " + pacienteId);
    }

    private void notificarMedico(Long medicoId, java.time.LocalDateTime fecha) {
        // Integración con sistema de notificaciones
        System.out.println("🔔 Médico " + medicoId + " notificado");
    }

    private void liberarSlotCalendario(Long medicoId, java.time.LocalDateTime fecha) {
        // Lógica para liberar el horario
        System.out.println("📅 Slot liberado para médico " + medicoId);
    }

    private void notificarCancelacion(Long pacienteId, Long medicoId) {
        // Notificaciones de cancelación
        System.out.println("📱 Cancelación notificada a paciente y médico");
    }
}
