package br.com.estudos.jwt.security;

import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import br.com.estudos.jwt.service.DetalheUsuarioServiceImpl;

@EnableWebSecurity //Habilita a classe como componente de segurança
public class JWTConfiguration extends WebSecurityConfigurerAdapter {
    
    private final DetalheUsuarioServiceImpl detalheUsuarioServiceImpl;
    // private final PasswordEncoder passwordEncoder;

    public JWTConfiguration(DetalheUsuarioServiceImpl detalheUsuarioServiceImpl) {
        this.detalheUsuarioServiceImpl = detalheUsuarioServiceImpl;
        // this.passwordEncoder = passwordEncoder;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(detalheUsuarioServiceImpl).passwordEncoder(passwordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable().authorizeRequests()
            .antMatchers(HttpMethod.POST, "/login")
            .permitAll()
            .anyRequest().authenticated()
            .and()
            .addFilter(new JWTAutenticateFilter(authenticationManager()))
            .addFilter(new JWTValidateFilter(authenticationManager()))
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }

    @Bean //Transforma método em componente da aplicação
    CorsConfigurationSource corsConfigurationSource() {
        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration corsConfiguration = new CorsConfiguration().applyPermitDefaultValues();
        source.registerCorsConfiguration("**", corsConfiguration);
        return source;
    }

}
