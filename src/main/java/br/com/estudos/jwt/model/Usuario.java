package br.com.estudos.jwt.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @AllArgsConstructor @NoArgsConstructor
public class Usuario {

    private Long id;
    private String nome;
    private String login;
    private String senha;
    
}
