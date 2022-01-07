package br.com.estudos.jwt.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/")
public class IndexController {
    
    @GetMapping(path = "/hello")
    public String hello() {
        return "Hello";
    }

    @GetMapping(path = "/login")
    public String login() {
        return "login";
    }

}
