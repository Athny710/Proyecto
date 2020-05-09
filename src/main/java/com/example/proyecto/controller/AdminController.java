package com.example.proyecto.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @GetMapping("perfil")
    public String perfil(){
        return "Administrador/perfil";
    }
    @GetMapping("detallesGestor")
    public String detallesGestor(){
        return "Administrador/GestorDetalles";
    }
    @GetMapping("nuevoGestor")
    public String nuevoGestor(){
        return "Administrador/NuevoGestor";
    }
    @GetMapping("principal")
    public String principalAdmin(){
        return "Administrador/PagPrincipal";
    }

    @GetMapping("detalles")
    public String detalleInventario(){
        return "Administrador/DetallesInventario";
    }

    @GetMapping("listaUsuarios")
    public String listaUsuarios(){
        return "Administrador/ListaUsuariosSede";
    }

    @GetMapping("listaGestores")
    public String listaGestores(){
        return "Administrador/ListaGestores";
    }

    @GetMapping("generarCuenta")
    public String generarCuenta(){
        return "Administrador/GenerarCuenta";
    }

}
