package br.com.estudos.jwt.service;

import java.util.Optional;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import br.com.estudos.jwt.data.DetalheUsuario;
import br.com.estudos.jwt.model.Usuario;
import br.com.estudos.jwt.repository.UsuarioRepository;

@Component
public class DetalheUsuarioServiceImpl implements UserDetailsService    {

    private final UsuarioRepository repository;

    public DetalheUsuarioServiceImpl(UsuarioRepository repository) {
        this.repository = repository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        
        Optional<Usuario> usuario = repository.findUsuario(username);
        
        if(!StringUtils.hasLength(username) || usuario.isEmpty())
            throw new UsernameNotFoundException("Usuário " + username + " não existe");

        return new DetalheUsuario(usuario);
         
    }
    
}
