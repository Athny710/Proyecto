package com.example.proyecto.controller;

import com.example.proyecto.entity.Categoria;
import com.example.proyecto.entity.Comunidad;
import com.example.proyecto.repository.ArtesanoRepository;
import com.example.proyecto.repository.CategoriaRepository;
import com.example.proyecto.repository.ComunidadRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/gestor")
public class GestorController {

    @Autowired
    ComunidadRepository comunidadRepository;
    @Autowired
    CategoriaRepository categoriaRepository;
    @Autowired
    ArtesanoRepository artesanoRepository;


    @GetMapping("gestorRegCompra")
    public String RegistroCompra(){return "Gestor/G-RegCompra";}
    @GetMapping("gestorEditProdCompra")
    public String EditProdCompra(){return "Gestor/G-EditProdCompra";}
   // @GetMapping("gestorEditComunidad")
   // public String EditComunidad(){return "Gestor/G-EditComunidad";}
    @GetMapping("gestorEditArtesano")
    public String EditArtesano(){return "Gestor/G-EditArtesano";}
    @GetMapping("gestorRegistroUsuarioSede")
    public String registroUsuarioSede(){return "Gestor/G-RegistroUsuarioSede";}

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

    @GetMapping("gestorListaArtesano")
    public String listaArtesano (){return "Gestor/G-ListaArtesano";}

    @GetMapping("gestorRegistroArtesano")
    public String registroArtesano (){return "Gestor/G-RegistroArtesano";}

    @GetMapping("gestorDetallesProdcutoCompra")
    public String detallesProdcutoCompra (){return "Gestor/G-DetallesProdcutoCompra";}
    @GetMapping("gestorDetallesProdcutoConsignacion")
    public String DetallesProdcutoConsignacion (){return "Gestor/G-DetallesProdcutoConsignacion";}

/*
=======


    // ------------------ INICIO CRUD COMUNIDAD ------------------------
>>>>>>> 61d7116698be4fcf386317b655fbb795030dbde4
    @GetMapping("gestorListaComunidad")
    public String listaComunidad (Model model){
        model.addAttribute("listaComunidades", comunidadRepository.findAll());
        return "Gestor/G-ListaComunidad";
    }
    @GetMapping("gestorRegistroComunidad")
    public String registroComunidad (){
        return "Gestor/G-RegistroComunidad";
    }
    @PostMapping("gestorGuardarComunidad")
    public String guardarComunidad(Comunidad c){
        comunidadRepository.save(c);
        return "redirect:/gestor/gestorListaComunidad";
    }
*/


    @GetMapping("gestorListaComunidad")
    public String listaComunidad (Model model){
        model.addAttribute("listaComunidad", comunidadRepository.findAll());
        return "Gestor/G-ListaComunidad";
    }


    @GetMapping("gestorRegistroComunidad")
    public String registroComunidad (@ModelAttribute("comunidad") Comunidad comunidad){
      //  model.addAttribute("listaComunidades", comunidadRepository.findAll());
        return "Gestor/G-RegistroComunidad";
    }


    @PostMapping("gestorGuardarComunidad")
    public String guardarComunidad(@ModelAttribute("comunidad") Comunidad comunidad,
                                   Model model,
                                   RedirectAttributes attr) {
        List<Comunidad> listaComunidad = comunidadRepository.buscarPorNombre(comunidad.getNombre(),comunidad.getCodigo());


        if((comunidad.getIdComunidad()==0) && (listaComunidad.size() == 0)){
            comunidadRepository.save(comunidad);
            attr.addFlashAttribute("msg", "Comunidad creada exitosamente");
            return "redirect:/gestor/gestorListaComunidad";
        } else if (comunidad.getIdComunidad()!=0){
            comunidadRepository.save(comunidad);
            attr.addFlashAttribute("msg", "Comunidad actualizada exitosamente");
            return "redirect:/gestor/gestorListaComunidad";
        } else {
            model.addAttribute("errorComunidad","Los datos ingresados ya existen");
            return "Gestor/G-RegistroComunidad";
        }
    }

        /*
        if (comunidad.getNombre().equals("")) {
            model.addAttribute("errorComunidad", "El nombre no puede ser vacío");
            return "Gestor/G-RegistroComunidad";
        } else {
            if (comunidad.getIdComunidad() == 0) {
                attr.addFlashAttribute("msg", "Comunidad creada exitosamente");
                comunidadRepository.save(comunidad);
            } else {
                attr.addFlashAttribute("msg", "Comunidad actualizada exitosamente");
            }
            comunidadRepository.save(comunidad);
            return "redirect:/gestor/gestorListaComunidad";
        }   */




    @GetMapping("gestorEditComunidad")
    public String EditComunidad(@ModelAttribute("comunidad") Comunidad comunidad, Model model,
                                @RequestParam("idcomunidad") int idcomunidad){

        Optional<Comunidad> optComunidad = comunidadRepository.findById(idcomunidad);
        if (optComunidad.isPresent()) {
          //  Comunidad comunidad = optComunidad.get();
            comunidad = optComunidad.get();
            model.addAttribute("comunidad", comunidad);
         //   model.addAttribute("listaComunidades", comunidadRepository.findAll());
            return "Gestor/G-RegistroComunidad";
        } else {
            return "redirect:/gestor/gestorListaComunidad";
         }
    }


    @GetMapping("gestorBorarComunidad")
    public String borrarComunidad(Model model,
                                  @RequestParam("id") int idcomunidad,
                                  RedirectAttributes attr){
        Optional<Comunidad> optComunidad = comunidadRepository.findById(idcomunidad);
        if (optComunidad.isPresent()) {
              comunidadRepository.deleteById(idcomunidad);
            attr.addFlashAttribute("msg","Comunidad borrado exitosamente");
        }
        return "redirect:/gestor/gestorListaComunidad";
    }

// ----------------------- FIN CRUD COMUNIDAD ---------------------------------




// ----------------------- INICIO CRUD CATEGORIA ---------------------------------

    @GetMapping("gestorListaCategoria")
    public String listaCategoria (Model model){
        model.addAttribute("listaCategoria", categoriaRepository.findAll());


        return "Gestor/G-ListaCategoria";}

    @PostMapping("gestorBuscarCategoria")
    public String buscaCategoria (@RequestParam("searchField") String valor ,Model model){

        List<Categoria> listaCategoria = categoriaRepository.buscarCategoria(valor,valor);
        model.addAttribute("listaCategoria",listaCategoria);

        return "Gestor/G-ListaCategoria";}


    @GetMapping("gestorRegistrarCategoria")
    public String RegistroCategoria(Categoria categoria, Model model){

        return "Gestor/G-RegCategoria";
    }


    @PostMapping("gestorGuardarCategoria")
    public String GuardaCategoria(Model model, Categoria categoria, RedirectAttributes attr){
        List<Categoria> listaCategoria = categoriaRepository.buscarCategoria(categoria.getNombre(),categoria.getCodigo());

        if((categoria.getIdCategoria() == 0) && (listaCategoria.size() == 0)){
            categoriaRepository.save(categoria);
            attr.addFlashAttribute("msg","Categoria registrada correctamente");
            return "redirect:/gestor/gestorListaCategoria";
        } else if (categoria.getIdCategoria()!=0) {
            attr.addFlashAttribute("msg","Categoría actualizada correctamente");
            categoriaRepository.save(categoria);
            return "redirect:/gestor/gestorListaCategoria";
        } else {
            model.addAttribute(categoria);
            attr.addFlashAttribute("msgError","Los datos ingresados ya existen, por favor modificarlo");
            return "redirect:/gestor/gestorRegistrarCategoria";
        }


    }

    @GetMapping("gestorEditCategoria")
    public String EditCategoria(Model model, @RequestParam("id") int id){

        Optional<Categoria> optCategoria = categoriaRepository.findById(id);

        if (optCategoria.isPresent()) {
            Categoria categoria = optCategoria.get();
            model.addAttribute("categoria", categoria);
            return "Gestor/G-EditCategoria";
        } else {
            return "redirect:/gestor/gestorListaCategoria";
        }

    }



    @GetMapping("gestorEliminarCategoria")
    public String EliminarCategoria(@RequestParam("id") int id, RedirectAttributes attr) {

        Optional<Categoria> optCategoria= categoriaRepository.findById(id);
        if(optCategoria.isPresent()){
            categoriaRepository.deleteById(id);
            attr.addFlashAttribute("msg","Categoría Eliminada");
        }
        return "redirect:/gestor/gestorListaCategoria";
    }


// ----------------------- FIN CRUD CATEGORIA ---------------------------------


    @PostMapping("/gestorBuscarComunidad")
    public String buscarComunidad(@RequestParam("searchField") String searchField,
                                      Model model) {

        List<Comunidad> listaComunidad = comunidadRepository.buscarPorNombre(searchField, searchField);
        model.addAttribute("listaComunidad", listaComunidad );
        return "Gestor/G-ListaComunidad";
    }


}
