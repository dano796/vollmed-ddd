package med.voll.api.infrastructure.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

@Configuration
@EnableWebSecurity
public class SecurityConfigurations {

    @Autowired
    private SecurityFilter securityFilter;

    @Autowired
    private ObjectMapper objectMapper;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .cors(cors -> cors.and())  // Habilitar CORS
                .csrf(csrf -> csrf.disable())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers(HttpMethod.POST, "/login").permitAll()
                        .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll() // Permitir preflight CORS
                        .requestMatchers("/v3/api-docs/**", "/swagger-ui.html", "/swagger-ui/**").permitAll()
                        .requestMatchers("/error").permitAll()
                        .anyRequest().authenticated()
                )
                .exceptionHandling(exceptions -> exceptions
                        .authenticationEntryPoint(authenticationEntryPoint())
                        .accessDeniedHandler((request, response, accessDeniedException) -> {
                            if (!response.isCommitted()) {
                                handleError(response, HttpStatus.FORBIDDEN, "Acceso denegado",
                                          "No tienes permisos para acceder a este recurso");
                            }
                        })
                )
                .addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    @Bean
    public AuthenticationEntryPoint authenticationEntryPoint() {
        return (request, response, authException) -> {
            if (!response.isCommitted()) {
                handleError(response, HttpStatus.UNAUTHORIZED, "Error de autenticación",
                          "Token requerido o inválido");
            }
        };
    }

    private void handleError(HttpServletResponse response, HttpStatus status, String error, String message) throws IOException {
        response.setStatus(status.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding("UTF-8");

        // Agregar headers CORS
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
        response.setHeader("Access-Control-Allow-Headers", "*");

        ErrorResponse errorResponse = new ErrorResponse(
                status.value(),
                error,
                List.of(message),
                LocalDateTime.now()
        );

        // Usar el ObjectMapper inyectado que ya tiene configurado el JavaTimeModule
        objectMapper.writeValue(response.getWriter(), errorResponse);
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
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

        // Getters
        public int getStatus() { return status; }
        public String getError() { return error; }
        public List<String> getMessages() { return messages; }
        public LocalDateTime getTimestamp() { return timestamp; }
    }
}
