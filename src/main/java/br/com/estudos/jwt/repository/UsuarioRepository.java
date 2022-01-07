package br.com.estudos.jwt.repository;

import java.util.Optional;
import br.com.estudos.jwt.model.Usuario;

public interface UsuarioRepository {

    Optional<Usuario> findUsuario(String username);

}
