package com.example.proyecto.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/system")
public class SystemController {

    @GetMapping("principal")
    public String listar(){
        return "base/basePag";
    }

    @GetMapping("usede")
    public String gaa(){
        return "UsuarioSede/Principal";
    }

    @GetMapping("usedeAlerta")
    public String gaaaaaaaa(){
        return "UsuarioSede/PrincipalAlerta";
    }

    @GetMapping("/registroVentas")
    public String gaaaaaaaaaaaaa(){
        return "UsuarioSede/GestionVentas";
    }

    @GetMapping("/registroVentasAlerta")
    public String gaaaaaaaaaaaaaaaaaa(){
        return "UsuarioSede/GestionVentasAlerta";
    }
    @GetMapping("/TiendaDistribuidor")
    public String gaaaaaaaaaaaaaaaaaaaaaaaa(){
        return "UsuarioSede/TiendaDistribuidor";
    }

    @GetMapping("personal")
    public String ga(){
        return "UsuarioSede/InformPerso";
    }
    @GetMapping("proconf")
    public String gaaaa(){
        return "UsuarioSede/ProductoEspera";
    }
    @GetMapping(value = {"","iniciosesion"})
    public String inicioSesion(){
        return "index";
    }



    @GetMapping("RecuperarContraseña")
    public String recuperarContrasela (){return "Sistema/S-RecupContra";}
    @GetMapping("NuevaContraseña")
    public String nuevaContrasela (){return "Sistema/S-NuevContra";}
    @GetMapping("cambiarContrasena")
    public String cambiarContrasena(){return "cambiarContrasena";}




    
}
