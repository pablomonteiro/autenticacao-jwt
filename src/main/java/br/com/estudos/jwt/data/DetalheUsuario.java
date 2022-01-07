package br.com.estudos.jwt.data;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import br.com.estudos.jwt.model.Usuario;

public class DetalheUsuario implements UserDetails {

    private final Optional<Usuario> usuario;

    public DetalheUsuario(Optional<Usuario> usuario) {
        this.usuario = usuario;
    }

    /*
     * Refere-se às permissões do usuário
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // TODO Auto-generated method stub
        return new ArrayList<>();
    }

    @Override
    public String getPassword() {
        // TODO Auto-generated method stub
        return usuario.orElse(new Usuario()).getSenha();
    }

    @Override
    public String getUsername() {
        // TODO Auto-generated method stub
        return usuario.orElse(new Usuario()).getLogin();
    }

    @Override
    public boolean isAccountNonExpired() {
        // TODO Auto-generated method stub
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        // TODO Auto-generated method stub
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        // TODO Auto-generated method stub
        return true;
    }

    @Override
    public boolean isEnabled() {
        // TODO Auto-generated method stub
        return true;
    }
    
}
