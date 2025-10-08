package med.voll.api.infrastructure.repository.adapters;

import lombok.RequiredArgsConstructor;
import med.voll.api.domain.entities.Usuario;
import med.voll.api.domain.interfaces.repository.IUsuarioRepository;
import med.voll.api.infrastructure.repository.springdata.SpringDataUsuarioRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class UsuarioRepositoryAdapter implements IUsuarioRepository {

    private final SpringDataUsuarioRepository delegate;

    @Override
    public Usuario save(Usuario usuario) {
        return delegate.save(usuario);
    }

    @Override
    public Optional<Usuario> findById(Long id) {
        return delegate.findById(id);
    }

    @Override
    public Usuario getReferenceById(Long id) {
        return delegate.getReferenceById(id);
    }

    @Override
    public UserDetails findByLogin(String username) {
        return delegate.findByLogin(username);
    }
}

