package com.example.proyecto.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class AdminController {

    //--------------------------Inventario
    @GetMapping(value = {"","principal"})
    public String principalAdmin(){
        return "Administrador/A-PagPrincipal";
    }
    @GetMapping("detalles")
    public String detalleInventario(){
        return "Administrador/A-DetallesInventario";
    }

    //--------------------------Gestores
    @GetMapping("listaGestores")
    public String listaGestores(){
        return "Administrador/A-ListaGestores";
    }
    @GetMapping("nuevoGestor")
    public String nuevoGestor(){
        return "Administrador/A-NuevoGestor";
    }

    //--------------------------Usuarios Sede
    @GetMapping("listaUsuarios")
    public String listaUsuarios(){
        return "Administrador/A-ListaUsuariosSede";
    }

    //--------------------------Sistema
    @GetMapping("perfil")
    public String perfil(){ return "Administrador/A-Perfil"; }
    @GetMapping("generarCuenta")
    public String generarCuenta(){
        return "Administrador/A-GenerarCuenta";
    }

}
