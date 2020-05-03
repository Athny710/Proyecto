package com.example.proyecto.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("admin")
public class AdminController {

    @GetMapping("principal")
    public String principalAdmin(){
        return "Administrador/PagPrincipal";
    }

    @GetMapping("listaUsuarios")
    public String listaUsuarios(){
        return "Administrador/ListaUsuariosSede";
    }
    @GetMapping("listaGestores")
    public String listaGestores(){
        return "Administrador/ListaGestores";
    }

}
