package com.example.proyecto.controller;

import com.example.proyecto.entity.Artesano;
import com.example.proyecto.entity.Inventario;
import com.example.proyecto.entity.Tienda;
import com.example.proyecto.repository.InventarioRepository;
import com.example.proyecto.repository.TiendaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.jws.WebParam;
import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/sede")
public class SedeController {

    @Autowired
    InventarioRepository inventarioRepository;
    @Autowired
    TiendaRepository tiendaRepository;

    @GetMapping("perfil")
    public String perfil(){ return "UsuarioSede/U-Perfil"; }

    //--------------------------Inventario
    @GetMapping("principal")
    public String principal(Model model){
        List<Inventario> lista = inventarioRepository.findAll();
        model.addAttribute("listaProductos", lista);
        return "UsuarioSede/U-Principal";
    }

    @GetMapping("productosEnEspera")
    public String productosEnEspera(){
        return "UsuarioSede/U-ProductoEspera";
    }

    @GetMapping("gestionVentas")
    public String gestionDeVentas(){
        return "UsuarioSede/U-GestionVentas";
    }


    //----------------INICIO CRUD TIENDAS-------------------


    @GetMapping("registroTiendas")
    public String registroDeTiendas(@ModelAttribute("tienda") Tienda tienda,Model model){
        model.addAttribute("listaTiendas",tiendaRepository.findAll());
        return "UsuarioSede/U-TiendaDistribuidor";
    }

    @PostMapping("guardarTienda")
    public String guardarTienda(@ModelAttribute("tienda") Tienda tienda,
                                  RedirectAttributes attr,
                                  Model model){

            if (tienda.getIdtienda() == 0) {
                tiendaRepository.save(tienda);
                attr.addFlashAttribute("msg", "Se registró la tienda exitosamente");
                return "redirect:/sede/registroTiendas";
            } else {
                tiendaRepository.save(tienda);
                attr.addFlashAttribute("msg", "Tienda actualizada correctamente");
                return "redirect:/sede/registroTiendas";
            }
    }

    @GetMapping("borrarTienda")
    public String borrarTiendas(Model model, @RequestParam("id") int idtienda, RedirectAttributes attr){
        Optional<Tienda> obtenerTienda = tiendaRepository.findById(idtienda);
        if (obtenerTienda.isPresent()) {
            tiendaRepository.deleteById(idtienda);
            attr.addFlashAttribute("msg","Tienda borrada exitosamente");
        }
        return "redirect:/sede/registroTiendas";
    }

    @GetMapping("editarTienda")
    public String editarTienda(@RequestParam("id") int idtienda, @ModelAttribute("tienda") Tienda tienda, Model model){
        Optional<Tienda> tiendaPorID = tiendaRepository.findById(idtienda);
        if (tiendaPorID.isPresent()) {
            model.addAttribute("tienda",tiendaPorID.get());

            return "UsuarioSede/U-TiendaDistribuidor";
        } else {
            return "redirect:/sede/registroTiendas";
        }
    }

    //----------------FIN CRUD TIENDAS-------------------

}
