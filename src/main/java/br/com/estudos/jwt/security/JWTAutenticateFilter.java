package br.com.estudos.jwt.security;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import br.com.estudos.jwt.data.DetalheUsuario;
import br.com.estudos.jwt.model.Usuario;

/*
 * Classe responsável por autenticar o usuário e gerar token JWT
 */
public class JWTAutenticateFilter extends UsernamePasswordAuthenticationFilter {
    
    private static final Integer EXPIRACAO = 600_000;
    public static final String TOKEN_SENHA = "51128806-6d08-11ec-90d6-0242ac120003";
    private final AuthenticationManager authenticationManager;

    public JWTAutenticateFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        try {
            Usuario usuario = new ObjectMapper().readValue(request.getInputStream(), Usuario.class);
            return authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                        usuario.getLogin()
                        , usuario.getSenha()
                        , new ArrayList<>()
                    )
                );
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {

        DetalheUsuario usuario = (DetalheUsuario) authResult.getPrincipal();

        String token = JWT.create()
            .withSubject(usuario.getUsername())
            .withExpiresAt(new Date(System.currentTimeMillis() + EXPIRACAO))
            .sign(Algorithm.HMAC512(TOKEN_SENHA));

        response.getWriter().write(token);
        response.getWriter().flush();    
    }
    
    public static void main(String[] args) {
        String token = JWT.create()
            .withSubject("pablo")
            .withExpiresAt(new Date(System.currentTimeMillis() + EXPIRACAO))
            .sign(Algorithm.HMAC512(TOKEN_SENHA));
        System.out.println(token);
        String subject = JWT.require(Algorithm.HMAC512(JWTAutenticateFilter.TOKEN_SENHA))
                        .build()
                        .verify(token)
                        .getSubject();
        System.out.println(subject);
    }

}
