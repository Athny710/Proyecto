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

    @GetMapping("gestorRecuperarContraseña")
    public String recuperarContrasela (){return "Sistema/S-RecupContra";}
    @GetMapping("gestorNuevaContraseña")
    public String nuevaContrasela (){return "Sistema/S-NuevContra";}
    @GetMapping("gestorListarSinStock")
    public String listaSinStock (){return "Gestor/G-ListaSinStock";}
    @GetMapping("gestorProductosAceptados")
    public String productosAceptados (){return "Gestor/G-ProdAcep";}
    @GetMapping("gestorPrincipal")
    public String inventarioGestor (){return "Gestor/G-Inventario";}
    @GetMapping("gestorProductosRechazados")
    public String productosRechazados (){return "Gestor/G-ProdRecha";}


    @GetMapping("gestorListaUsuarioSede")
    public String listaUsuarioSede (){return "Gestor/G-ListaUsuarioSede";}
    @GetMapping("gestorListaComunidad")
    public String listaComunidad (){return "Gestor/G-ListaComunidad";}
    @GetMapping("gestorListaArtesano")
    public String listaArtesano (){return "Gestor/G-ListaArtesano";}
    @GetMapping("gestorListaCategoria")
    public String listaCategoria (){return "Gestor/G-ListaCategoria";}
    @GetMapping("gestorRegistroArtesano")
    public String registroArtesano (){return "Gestor/G-RegistroArtesano";}
    @GetMapping("gestorRegistroComunidad")
    public String registroComunidad (){return "Gestor/G-RegistroComunidad";}
    @GetMapping("gestorDetallesProdcutoCompra")
    public String detallesProdcutoCompra (){return "Gestor/G-DetallesProdcutoCompra";}
    @GetMapping("gestorDetallesProdcutoConsignacion")
    public String DetallesProdcutoConsignacion (){return "Gestor/G-DetallesProdcutoConsignacion";}

}
