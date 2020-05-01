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

    @GetMapping("gestorRegCompra")
    public String RegistroCompra(){return "Gestor/G-RegCompra";}
    @GetMapping("gestorRegConsig")
    public String RegistroConsig(){return "Gestor/G-RegConsig";}
    @GetMapping("gestorEditProdCompra")
    public String EditProdCompra(){return "Gestor/G-EditProdCompra";}
    @GetMapping("gestorEditProdConsig")
    public String EditProdConsig(){return "Gestor/G-EditProdConsig";}
}
