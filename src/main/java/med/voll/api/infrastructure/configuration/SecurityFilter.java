package med.voll.api.infrastructure.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import med.voll.api.domain.interfaces.repository.IUsuarioRepository;
import med.voll.api.infrastructure.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

@Component
public class SecurityFilter extends OncePerRequestFilter {

    @Autowired
    private TokenService tokenService;

    @Autowired
    private IUsuarioRepository IUsuarioRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        var authHeader = request.getHeader("Authorization");

        if (authHeader != null) {
            try {
                var token = authHeader.replace("Bearer ", "");
                var nombreUsuario = tokenService.getSubject(token);

                if (nombreUsuario != null) {
                    var usuario = IUsuarioRepository.findByLogin(nombreUsuario);
                    if (usuario != null) {
                        var authentication = new UsernamePasswordAuthenticationToken(usuario, null,
                                usuario.getAuthorities());
                        SecurityContextHolder.getContext().setAuthentication(authentication);
                    }
                }
            } catch (Exception e) {
                // Token inválido o expirado - devolver 401 en lugar de permitir que Spring Security devuelva 403
                handleTokenError(response, "Token inválido o expirado");
                return;
            }
        }

        filterChain.doFilter(request, response);
    }

    private void handleTokenError(HttpServletResponse response, String message) throws IOException {
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding("UTF-8");

        ErrorResponse errorResponse = new ErrorResponse(
                HttpStatus.UNAUTHORIZED.value(),
                "Error de autenticación",
                List.of(message),
                LocalDateTime.now()
        );

        ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(response.getWriter(), errorResponse);
    }

    // Clase para respuesta de error consistente
    private static class ErrorResponse {
        private int status;
        private String error;
        private List<String> messages;
        private LocalDateTime timestamp;

        public ErrorResponse(int status, String error, List<String> messages, LocalDateTime timestamp) {
            this.status = status;
            this.error = error;
            this.messages = messages;
            this.timestamp = timestamp;
        }

        // Getters para JSON serialization
        public int getStatus() { return status; }
        public String getError() { return error; }
        public List<String> getMessages() { return messages; }
        public LocalDateTime getTimestamp() { return timestamp; }
    }
}
