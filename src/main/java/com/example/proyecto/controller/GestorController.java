package com.example.proyecto.controller;

import com.example.proyecto.entity.*;
import com.example.proyecto.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
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
    @Autowired
    AdquisicionRepository adquisicionRepository;
    @Autowired
    ProductoRepository productoRepository;
    @Autowired
    EstadoenviosedeRepository estadoenviosedeRepository;
    @Autowired
    InventariosedeRepository inventariosedeRepository;
    @Autowired
    InventarioRepository inventarioRepository;
    @Autowired
    SedeRepository sedeRepository;


    // ----------------------- ENLACES ---------------------------------
    @GetMapping("perfil")
    public String perfil() {
        return "Gestor/G-Perfil";
    }


    @GetMapping("gestorRegistroUsuarioSede")
    public String registroUsuarioSede() {
        return "Gestor/G-RegistroUsuarioSede";
    }

    @GetMapping("gestorGestionVentas")
    public String registroVentas() {
        return "Gestor/G-GestiónVentas";
    }

    @GetMapping("gestorResgistroSede")
    public String registroSede() {
        return "Gestor/G-RegistroSede";
    }

    @GetMapping("gestorReporteVentas")
    public String reporteVentas1() {
        return "Gestor/G-GenReporte";
    }

    @GetMapping("gestorReporteVentas2")
    public String reporteVentas2() {
        return "Gestor/G-GenReporte2";
    }


    @GetMapping("gestorListaUsuarioSede")
    public String listaUsuarioSede() {
        return "Gestor/G-ListaUsuarioSede";
    }


// ----------------------- CRUD INVENTARIO ---------------------------------

    @GetMapping(value = {"", "gestorPrincipal"})
    public String inventarioGestor(Model model) {
        List<Inventario> inventario = inventarioRepository.findAll();
        model.addAttribute("inventario", inventario);
        return "Gestor/G-Inventario";
    }

    @GetMapping("gestorListarSinStock")
    public String listaSinStock(Model model) {
        List<Inventario> listaSinStock = inventarioRepository.findByStock(0);
        model.addAttribute("listaSinStock", listaSinStock);
        return "Gestor/G-ListaSinStock";
    }

    @GetMapping("gestorDetallesProdcuto")
    public String detallesProdcutoCompra(Model model, @RequestParam("id") int id) {
        Optional<Inventario> producto = inventarioRepository.findById(id);
        if (producto.isPresent()) {
            Inventario producto2 = producto.get();

            model.addAttribute("producto", producto2);
            return "Gestor/G-DetallesProdcuto";
        } else {
            return "redirect:/gestorPrincipal";
        }
    }

    @GetMapping("gestorEditProdCompra")
    public String EditProdCompra() {
        return "Gestor/G-EditProdCompra";
    }

    @GetMapping("gestorRegProducto")
    public String RegistroCompra() {
        return "Gestor/G-RegCompra";
    }

    @GetMapping("borrarProducto")
    public String borrarProducto() {
        return "redirect:/gestorPrincipal";
    }

    // ----------------------- CRUD INVENTARIO ---------------------------------

// ----------------------- FIN CRUD COMUNIDAD ---------------------------------

    @GetMapping("gestorListaComunidad")
    public String listaComunidad(Model model) {
        model.addAttribute("listaComunidad", comunidadRepository.findAll());
        return "Gestor/G-ListaComunidad";
    }

    @GetMapping("gestorRegistroComunidad")
    public String registroComunidad(@ModelAttribute("comunidad") Comunidad comunidad) {
        //  model.addAttribute("listaComunidades", comunidadRepository.findAll());
        return "Gestor/G-RegistroComunidad";
    }

    @PostMapping("gestorGuardarComunidad")
    public String guardarComunidad(@ModelAttribute("comunidad") @Valid Comunidad comunidad, BindingResult bindingResult,

                                   Model model,
                                   RedirectAttributes attr) {
        List<Comunidad> listaComunidad = comunidadRepository.buscarPorNombre(comunidad.getNombre(), comunidad.getCodigo());
        if (bindingResult.hasErrors()) {
            return "Gestor/G-RegistroComunidad";
        } else {

            if ((comunidad.getIdComunidad() == 0) && (listaComunidad.size() == 0)) {
                comunidadRepository.save(comunidad);
                attr.addFlashAttribute("msg", "Comunidad creada exitosamente");
                return "redirect:/gestor/gestorListaComunidad";
            } else if (comunidad.getIdComunidad() != 0) {
                comunidadRepository.save(comunidad);
                attr.addFlashAttribute("msg", "Comunidad actualizada exitosamente");
                return "redirect:/gestor/gestorListaComunidad";
            } else {
                model.addAttribute("errorComunidad", "Los datos ingresados ya existen");
                return "Gestor/G-RegistroComunidad";
            }
        }
    }

    @PostMapping("/gestorBuscarComunidad")
    public String buscarComunidad(@RequestParam("searchField") String searchField,
                                  Model model) {

        List<Comunidad> listaComunidad = comunidadRepository.buscarPorNombre(searchField, searchField);
        model.addAttribute("listaComunidad", listaComunidad);
        return "Gestor/G-ListaComunidad";
    }

    @GetMapping("gestorEditComunidad")
    public String EditComunidad(@ModelAttribute("comunidad") Comunidad comunidad, Model model,
                                @RequestParam("idcomunidad") int idcomunidad) {

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
                                  RedirectAttributes attr) {
        Optional<Comunidad> optComunidad = comunidadRepository.findById(idcomunidad);
        if (optComunidad.isPresent()) {
            comunidadRepository.deleteById(idcomunidad);
            attr.addFlashAttribute("msg", "Comunidad borrado exitosamente");
        }
        return "redirect:/gestor/gestorListaComunidad";
    }

// ----------------------- FIN CRUD COMUNIDAD ---------------------------------


// ----------------------- INICIO CRUD CATEGORIA ---------------------------------

    @GetMapping("gestorListaCategoria")
    public String listaCategoria(Model model) {
        model.addAttribute("listaCategoria", categoriaRepository.findAll());
        return "Gestor/G-ListaCategoria";
    }

    @PostMapping("gestorBuscarCategoria")
    public String buscaCategoria(@RequestParam("searchField") String valor, Model model) {

        List<Categoria> listaCategoria = categoriaRepository.buscarCategoria(valor, valor);
        model.addAttribute("listaCategoria", listaCategoria);
        return "Gestor/G-ListaCategoria";
    }

    @GetMapping("gestorRegistrarCategoria")
    public String RegistroCategoria(Categoria categoria, Model model) {
        return "Gestor/G-EditCategoria";
    }

    @PostMapping("gestorGuardarCategoria")
    public String GuardaCategoria(@ModelAttribute("categoria") Categoria categoria, Model model, RedirectAttributes attr) {
        List<Categoria> listaCategoria = categoriaRepository.buscarCategoria(categoria.getNombre(), categoria.getCodigo());

        if ((categoria.getIdCategoria() == 0) && (listaCategoria.size() == 0)) {
            categoriaRepository.save(categoria);
            attr.addFlashAttribute("msg", "Categoria registrada correctamente");
            return "redirect:/gestor/gestorListaCategoria";
        } else if (categoria.getIdCategoria() != 0) {
            attr.addFlashAttribute("msg", "Categoría actualizada correctamente");
            categoriaRepository.save(categoria);
            return "redirect:/gestor/gestorListaCategoria";
        } else {
            model.addAttribute(categoria);
            attr.addFlashAttribute("msgError", "Los datos ingresados ya existen, por favor modificarlo");
            return "redirect:/gestor/gestorRegistrarCategoria";
        }
    }

    @GetMapping("gestorEditCategoria")
    public String EditCategoria(Model model, @RequestParam("id") int id) {

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

        Optional<Categoria> optCategoria = categoriaRepository.findById(id);
        if (optCategoria.isPresent()) {
            categoriaRepository.deleteById(id);
            attr.addFlashAttribute("msg", "Categoría Eliminada");
        }
        return "redirect:/gestor/gestorListaCategoria";
    }

// ----------------------- FIN CRUD CATEGORIA ---------------------------------


    // ----------------------- INICIO CRUD ARTESANOS ---------------------------------

    @GetMapping("gestorEditArtesano")
    public String EditArtesano(@RequestParam("id") int id, @ModelAttribute("artesano") Artesano artesano, Model model) {
        Optional<Artesano> artesanoPorID = artesanoRepository.findById(id);
        if (artesanoPorID.isPresent()) {
            model.addAttribute("artesano", artesanoPorID.get());
            model.addAttribute("listaComunidad", comunidadRepository.findAll());
            return "gestor/G-EditArtesano";
        } else {
            return "redirect:/gestor/gestorListaArtesano";
        }
    }

    @GetMapping("gestorListaArtesano")
    public String listaArtesano(Model model) {
        model.addAttribute("listaComunidad", comunidadRepository.findAll());
        model.addAttribute("listaAdquisicion", adquisicionRepository.findAll());
        model.addAttribute("listaArtesanos", artesanoRepository.findAll());

        return "Gestor/G-ListaArtesano";
    }

    @GetMapping("gestorRegistroArtesano")
    public String registroArtesano() {
        return "Gestor/G-EditArtesano";
    }

    @GetMapping("gestorBuscarArtesano")
    public String buscarArtesano(@RequestParam("busqueda") String busqueda, Model model) {
        model.addAttribute("listaArtesanos", artesanoRepository.obtenerArtesanoBusqueda(busqueda));
        return "Gestor/G-ListaArtesano";
    }

    @GetMapping("gestorBorrarArtesano")
    public String borrarArtesano(Model model, @RequestParam("idartesano") int idartesano, RedirectAttributes attr) {
        Optional<Artesano> obtenerArtesano = artesanoRepository.findById(idartesano);
        if (obtenerArtesano.isPresent()) {
            artesanoRepository.deleteById(idartesano);
            attr.addFlashAttribute("msg", "Empleado borrado exitosamente");
        }
        return "redirect:/gestor/gestorListaArtesano";
    }

    @PostMapping("gestorGuardarArtesano")
    public String guardarArtesano(@ModelAttribute("artesano") @Valid Artesano artesano, BindingResult bindingResult,
                                  RedirectAttributes attr,
                                  Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("listaComunidad", comunidadRepository.findAll());
            return "Gestor/G-EditArtesano";
        } else {
            if (artesano.getIdArtesano() == 0) {
                attr.addFlashAttribute("msg", "Artesano no existe");
                return "redirect:/gestor/gestorListaArtesano";
            } else {
                artesanoRepository.save(artesano);
                attr.addFlashAttribute("msg", "Artesano actualizado correctamente");
                return "redirect:/gestor/gestorListaArtesano";
            }
        }
    }

    // ----------------------- FIN CRUD ARTESANOS ---------------------------------

    // ----------------------- INICIO CRUD PRODUCTO ---------------------------------
    @GetMapping("gestorProductosEnviados")
    public String productosEnviados(Model model) {
        model.addAttribute("listaProdEnv", productoRepository.listaProductosEnviados());
        return "Gestor/G-ProdEnv";
    }

    @GetMapping("gestorProductosAceptados")
    public String productosAceptados(Model model) {
        model.addAttribute("listaProdReci", productoRepository.listaProductosRecibidos());
        return "Gestor/G-ProdAcep";
    }

    @GetMapping("gestorProductosRechazados")
    public String productosRechazados(Model model) {
        model.addAttribute("listaProdRecha", productoRepository.listaProductosRechazados());
        return "Gestor/G-ProdRecha";
    }


    // -------------------------- FIN CRUD PRODUCTO ---------------------------------

    // -------------------------- TODO INICIO CRUD ENVIOS ------------------------------
    @GetMapping("gestorNuevoEnvio")
    public String NuevoEnvio(@ModelAttribute("estadoenviosede") Estadoenviosede estadoenviosede, Model model) {
        List<Inventario> listaInventario = inventarioRepository.findAll();
        List<Sede> listaSede = sedeRepository.findAll();
        model.addAttribute("listaInventario", listaInventario);
        model.addAttribute("listaSede", listaSede);
        return "Gestor/G-GestionEnvios";
    }

    @PostMapping("gestorGuardarEnvio")
    public String guardarEnvio(@ModelAttribute("estadoenviosede") @Valid Estadoenviosede estadoenviosede, BindingResult bindingResult,
                               RedirectAttributes attr,
                               Model model) {
        if (bindingResult.hasErrors()) {
            List<Inventario> listaInventario = inventarioRepository.findAll();
            List<Sede> listaSede = sedeRepository.findAll();
            model.addAttribute("listaInventario", listaInventario);
            model.addAttribute("listaSede", listaSede);
            System.out.println("la cagaste man");
            return "Gestor/G-GestionEnvios";


        } else {
            System.out.println("tal vez no la cagaste no binding errors");
            if (true) {
                //int cantidadrestada = estadoenviosede.getInventariosede().getInventario().getStock() - estadoenviosede.getCantidad();
                if (true) {//cantidadrestada >= 0

                    int invkey = estadoenviosede.getInventariosede().getInventario().getIdInventario();
                    int sedkey = estadoenviosede.getInventariosede().getSede().getIdsede();
                    if (sedeRepository.findById(sedkey).isPresent() && inventarioRepository.findById(invkey).isPresent()) {
                        Optional<Sede> sede1 = sedeRepository.findById(sedkey);
                        Optional<Inventario> inventario1 = inventarioRepository.findById(invkey);
                        List<Inventariosede> inventariosede1 = inventariosedeRepository.findByInventarioAndSede(inventario1.get(), sede1.get());
                        if (!inventariosede1.isEmpty()) {
                            estadoenviosede.setInventariosede(inventariosede1.get(0));
                        } else {
                            Inventariosede inventariosede2 = new Inventariosede();
                            inventariosede2.setInventario(inventario1.get());
                            inventariosede2.setSede(sede1.get());
                            inventariosede2.setStock(0);
                            inventariosedeRepository.save(inventariosede2);
                            estadoenviosede.setInventariosede(inventariosedeRepository.findByInventarioAndSede(inventario1.get(), sede1.get()).get(0));
                        }
                        estadoenviosede.setEstado("En camino");
                        estadoenviosedeRepository.save(estadoenviosede);
                        int cantidadrestada = estadoenviosede.getInventariosede().getInventario().getStock() - estadoenviosede.getCantidad();
                        estadoenviosede.getInventariosede().getInventario().setStock(cantidadrestada);
                    }
                    System.out.println("El stock nuevo del inventario es:" + estadoenviosede.getInventariosede().getInventario().getStock());
                    inventarioRepository.save(estadoenviosede.getInventariosede().getInventario());
                    attr.addFlashAttribute("msg", "Envio guardado correctamente");
                    return "redirect:/gestor/gestorProductosEnviados";
                } else {
                    List<Inventario> listaInventario = inventarioRepository.findAll();
                    List<Sede> listaSede = sedeRepository.findAll();
                    model.addAttribute("listaInventario", listaInventario);
                    model.addAttribute("listaSede", listaSede);
                    attr.addFlashAttribute("msg", "Se esta tratando de enviar mas de lo que se tiene");
                    return "Gestor/G-GestionEnvios";
                }

            }
            System.out.println("la ide del envio sede es: " + estadoenviosede.getIdenviosede());
            List<Sede> listaSede = sedeRepository.findAll();
            model.addAttribute("listaSede", listaSede);
            List<Inventario> listaInventario = inventarioRepository.findAll();
            model.addAttribute("listaInventario", listaInventario);
            return "Gestor/G-GestionEnvios";
        }

    }


    // -------------------------- FIN CRUD ENVIOS ------------------------------

}
