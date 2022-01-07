package br.com.estudos.jwt.repository;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import br.com.estudos.jwt.model.Usuario;

@Repository
public class UsuarioRepositoryImpl implements UsuarioRepository {

    private final Map<String, Usuario> usuariosDB = new HashMap<>();

    public UsuarioRepositoryImpl() {
        this.usuariosDB.put("pablo", new Usuario(10L, "Pablo", "pablo", "$2a$12$9sZpavPtjb.bzEUlvwN2k.PYWFMUGDBxRrU8MaasazJgm0n01TTt6"));
    }

    @Override
    public Optional<Usuario> findUsuario(String username) {
        return Optional.of(usuariosDB.get(username));
    }
    
}
