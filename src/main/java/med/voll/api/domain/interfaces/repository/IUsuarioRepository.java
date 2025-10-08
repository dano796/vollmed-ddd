package med.voll.api.domain.interfaces.repository;

import med.voll.api.domain.entities.Usuario;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;

/**
 * Contrato de dominio para Usuario. Implementación concreta en infraestructura.
 */
public interface IUsuarioRepository {

    Usuario save(Usuario usuario);

    Optional<Usuario> findById(Long id);

    Usuario getReferenceById(Long id);

    UserDetails findByLogin(String username);
}
