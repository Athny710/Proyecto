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
    @GetMapping("admin")
    public String principalAdmin(){return "Administrador/PagPrincipal";}
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
    @GetMapping("gestorEditProdCompra")
    public String EditProdCompra(){return "Gestor/G-EditProdCompra";}
    @GetMapping("gestorEditComunidad")
    public String EditComunidad(){return "Gestor/G-EditComunidad";}
    @GetMapping("gestorEditArtesano")
    public String EditArtesano(){return "Gestor/G-EditArtesano";}
    @GetMapping("gestorRegistroUsuarioSede")
    public String registroUsuarioSede(){return "Gestor/G-RegistroUsuarioSede";}
    @GetMapping("gestorRegCategoria")
    public String RegistroCategoria(){return "Gestor/G-RegCategoria";}
    @GetMapping("gestorEditCategoria")
    public String EditCategoria(){return "Gestor/G-EditCategoria";}
    @GetMapping("gestorGestionVentas")
    public String registroVentas(){return "Gestor/G-GestiónVentas";}

    @GetMapping("gestorGenerarReporte")
    public String reporteVentas(){return "Gestor/G-GenReporte";}
    @GetMapping("gestorGenerarReporte2")
    public String reporteVentas2(){return "Gestor/G-GenReporte2";}
    @GetMapping("gestorRegistroSede")
    public String registroSede(){return "Gestor/G-RegistroSede";}
    @GetMapping("cambiarContrasena")
    public String cambiarContrasena(){return "cambiarContrasena";}

    @GetMapping("recuperarContraseña")
    public String recuperarContrasela (){return "Sistema/S-RecupContra";}
    @GetMapping("nuevaContraseña")
    public String nuevaContrasela (){return "Sistema/S-NuevContra";}
    @GetMapping("listarSinStock")
    public String listaSinStock (){return "Gestor/G-ListaSinStock";}
    @GetMapping("productosAceptados")
    public String productosAceptados (){return "Gestor/G-ProdAcep";}



}
