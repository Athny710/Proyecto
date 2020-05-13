package com.example.proyecto.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class AdminController {

    //--------------------------Inventario
    @GetMapping("principal")
    public String principalAdmin(){
        return "Administrador/PagPrincipal";
    }
    @GetMapping("detalles")
    public String detalleInventario(){
        return "Administrador/DetallesInventario";
    }

    //--------------------------Gestores
    @GetMapping("listaGestores")
    public String listaGestores(){
        return "Administrador/ListaGestores";
    }
    @GetMapping("nuevoGestor")
    public String nuevoGestor(){
        return "Administrador/NuevoGestor";
    }

    //--------------------------Usuarios Sede
    @GetMapping("listaUsuarios")
    public String listaUsuarios(){
        return "Administrador/ListaUsuariosSede";
    }

    //--------------------------Sistema
    @GetMapping("perfil")
    public String perfil(){ return "Administrador/perfil"; }
    @GetMapping("generarCuenta")
    public String generarCuenta(){
        return "Administrador/GenerarCuenta";
    }

}
