package br.com.estudos.jwt.security;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

public class JWTValidateFilter extends BasicAuthenticationFilter {

    private static final String HEADER_ATRIBUTO = "Authorization";
    private static final String ATRIBUTO_PREFIXO = "Bearer ";

    public JWTValidateFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, 
                                    HttpServletResponse response, 
                                    FilterChain chain) throws IOException, ServletException {
        
        String atributo = request.getHeader(HEADER_ATRIBUTO);

        if(Objects.isNull(atributo)) {
            chain.doFilter(request, response);
            return;
        }

        if(!atributo.startsWith(ATRIBUTO_PREFIXO)) {
            chain.doFilter(request, response);
            return;
        }

        String token = atributo.replace(ATRIBUTO_PREFIXO, "");

        UsernamePasswordAuthenticationToken authenticationToken = getUsuario(token);

        SecurityContextHolder.getContext().setAuthentication(authenticationToken);

        chain.doFilter(request, response);
    }

    private UsernamePasswordAuthenticationToken getUsuario(String token) {
        
        String usuario = JWT.require(Algorithm.HMAC512(JWTAutenticateFilter.TOKEN_SENHA))
                        .build()
                        .verify(token)
                        .getSubject();

        if(Objects.isNull(usuario))
            return null;

        return new UsernamePasswordAuthenticationToken(usuario, null, new ArrayList<>());

    }

    
    
}
