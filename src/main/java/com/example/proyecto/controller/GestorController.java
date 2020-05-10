package com.example.proyecto.controller;


import com.example.proyecto.entity.Comunidad;
import com.example.proyecto.repository.ComunidadRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/gestor")
public class GestorController {

    @Autowired
    ComunidadRepository comunidadRepository;

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


    @GetMapping("gestorResgistroSede")
    public String registroSede(){return "Gestor/G-RegistroSede";}

    @GetMapping("gestorReporteVentas")
    public String reporteVentas1(){return "Gestor/G-GenReporte";}

    @GetMapping("gestorReporteVentas2")
    public String reporteVentas2(){return "Gestor/G-GenReporte2";}


    @GetMapping("gestorListarSinStock")
    public String listaSinStock (){return "Gestor/G-ListaSinStock";}
    @GetMapping("gestorProductosAceptados")
    public String productosAceptados (){return "Gestor/G-ProdAcep";}
    @GetMapping("gestorPrincipal")
    public String inventarioGestor (){return "Gestor/G-Inventario";}
    @GetMapping("gestorProductosRechazados")
    public String productosRechazados (){return "Gestor/G-ProdRecha";}
    @GetMapping("gestorProductosEnviados")
    public String productosEnviados (){return "Gestor/G-ProdEnv";}



    @GetMapping("gestorListaUsuarioSede")
    public String listaUsuarioSede (){return "Gestor/G-ListaUsuarioSede";}

    @GetMapping("comunidades")
    public String listaComunidad (Model model){
        model.addAttribute("listaComunidades", comunidadRepository.findAll());
        return "Gestor/G-ListaComunidad";
    }
    @GetMapping("registrarComunidad")
    public String registroComunidad (){
        return "Gestor/G-RegistroComunidad";
    }
    @GetMapping("añadirComunidad")
    public String añadirComunidad(Comunidad c){
        comunidadRepository.save(c);
        return "Gestor/G-ListaComunidad";
    }

    @GetMapping("gestorListaArtesano")
    public String listaArtesano (){return "Gestor/G-ListaArtesano";}
    @GetMapping("gestorListaCategoria")
    public String listaCategoria (){return "Gestor/G-ListaCategoria";}
    @GetMapping("gestorRegistroArtesano")
    public String registroArtesano (){return "Gestor/G-RegistroArtesano";}

    @GetMapping("gestorDetallesProdcutoCompra")
    public String detallesProdcutoCompra (){return "Gestor/G-DetallesProdcutoCompra";}
    @GetMapping("gestorDetallesProdcutoConsignacion")
    public String DetallesProdcutoConsignacion (){return "Gestor/G-DetallesProdcutoConsignacion";}

}
