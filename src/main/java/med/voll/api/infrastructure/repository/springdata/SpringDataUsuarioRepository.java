package med.voll.api.infrastructure.repository.springdata;

import med.voll.api.domain.entities.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

public interface SpringDataUsuarioRepository extends JpaRepository<Usuario, Long> {
    UserDetails findByLogin(String username);
}

