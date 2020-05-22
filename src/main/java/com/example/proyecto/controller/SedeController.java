package com.example.proyecto.controller;


import com.example.proyecto.entity.*;

import com.example.proyecto.entity.Artesano;
import com.example.proyecto.entity.Comunidad;
import com.example.proyecto.entity.Inventario;
import com.example.proyecto.entity.Tienda;
import com.example.proyecto.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.jws.WebParam;
import javax.validation.Valid;
import javax.xml.bind.SchemaOutputResolver;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/sede")
public class SedeController {
    @Autowired
    HistorialRepository historialRepository;
    @Autowired
    InventarioRepository inventarioRepository;
    @Autowired
    TiendaRepository tiendaRepository;
    @Autowired
    VentaRepository ventaRepository;
    @Autowired
    InventariosedeRepository inventariosedeRepository;

    @GetMapping("perfil")
    public String perfil() {
        return "UsuarioSede/U-Perfil";
    }





    //--------------------------Inventario---------------------------------------------
    @GetMapping(value = {"", "principal"})
    public String principal(Model model) {
        List<Inventariosede> lista = inventariosedeRepository.findAll();
        model.addAttribute("listaProductos", lista);
        return "UsuarioSede/U-Principal";
    }

    @GetMapping("DetallesProducto")
    public String detallesProdcutoCompra(Model model, @RequestParam("id") int id) {
        Optional<Inventariosede> inventario = inventariosedeRepository.findById(id);
        List<Historial> listaHistorial = historialRepository.findAll();
        Historial historial = null;
        if (inventario.isPresent()) {
            Inventariosede inventario2 = inventario.get();
            for (Historial hi : listaHistorial) {
                if (hi.getInventario().getIdInventario() == inventario2.getInventario().getIdInventario()) {
                    historial = hi;
                    break;
                }
            }
            model.addAttribute("historial", historial);
            model.addAttribute("producto", inventario2);
            return "UsuarioSede/U-DetallesProducto";
        } else {
            return "redirect:/principal";
        }
    }

    @GetMapping("DevolverProducto")
    public String devolverProducto(@RequestParam("id") int id, RedirectAttributes attr) {
        Optional<Inventariosede> inventariosede = inventariosedeRepository.findById(id);
        if (inventariosede.isPresent()) {
            Inventariosede inventariosede2 = inventariosede.get();
            Inventario inventario = inventariosede2.getInventario();
            Integer nuevoStock = inventario.getStock() + inventariosede2.getStock();
            System.out.println("Nuevo Stock  " + nuevoStock);

            inventario.setStock(nuevoStock);

            inventarioRepository.save(inventario);
            inventariosedeRepository.deleteById(id);
            return "redirect:/principal";
        } else {
            return "redirect:/principal";
        }
    }


        @GetMapping("productosEnEspera")
        public String productosEnEspera () {
            return "UsuarioSede/U-ProductoEspera";
        }


<<<<<<< HEAD
        if (bindingResult.hasErrors()) {
            return "UsuarioSede/U-NuevaVenta";
        } else {
            Inventario inventario = new Inventario();
            Usuarios usuarios = new Usuarios();
            Tienda tienda = new Tienda();

            inventario.setIdInventario(1);
            usuarios.setIdusuarios(1);
            tienda.setIdtienda(1);

            if (venta.getIdventa() == 0) {
                venta.setInventario(inventario);
                venta.setUsuarios(usuarios);
                venta.setTienda(tienda);
                ventaRepository.save(venta);
                att.addFlashAttribute("msg", "Venta añadida exitosamente");
            }
            return "redirect:/sede/gestionVentas";
=======
//------------------------- FIN CRUD INVENTARIO -----------------------------------------------
>>>>>>> 980768593e760c8a94c82ea5aa5772a9dc778445

        //--------------------CRUD VENTAS---------------
        @GetMapping("/nuevaVenta")
        public String nuevaVenta (@ModelAttribute("venta") Venta venta, Model model){
            return "UsuarioSede/U-NuevaVenta";
        }
<<<<<<< HEAD
    }
    @PostMapping("/buscarVenta")
    public String buscarVenta(@RequestParam("searchField") String searchField,
                                  Model model) {

        List<Venta> listaVenta = ventaRepository.buscarPorNombre(searchField);
        model.addAttribute("listaVentas", listaVenta );
        return "usuarioSede/U-GestionVentas";
    }


=======
>>>>>>> 980768593e760c8a94c82ea5aa5772a9dc778445


        @GetMapping("gestionVentas")
        public String gestionDeVentas (@ModelAttribute("venta") Venta venta, Model model){
            model.addAttribute("listaVentas", ventaRepository.findAll());
            return "UsuarioSede/U-GestionVentas";
        }

        @PostMapping("/guardarVenta")
        public String guardarVenta (@ModelAttribute("venta") @Valid Venta venta, BindingResult
        bindingResult, RedirectAttributes att){

            if (bindingResult.hasErrors()) {
                return "sede/U-NuevaVenta";
            } else {
                Inventario inventario = new Inventario();
                Usuarios usuarios = new Usuarios();
                Tienda tienda = new Tienda();

                inventario.setIdInventario(1);
                usuarios.setIdusuarios(1);
                tienda.setIdtienda(1);

                if (venta.getIdventa() == 0) {
                    venta.setInventario(inventario);
                    venta.setUsuarios(usuarios);
                    venta.setTienda(tienda);
                    ventaRepository.save(venta);
                    att.addFlashAttribute("msg", "Venta añadida exitosamente");
                }
                return "redirect:/gestionVentas";

            }
        }

        //----------------INICIO CRUD TIENDAS-------------------

        @GetMapping("registroTiendas")
        public String registroDeTiendas (@ModelAttribute("tienda") Tienda tienda, Model model){
            model.addAttribute("listaTiendas", tiendaRepository.findAll());
            return "UsuarioSede/U-TiendaDistribuidor";
        }

        @PostMapping("guardarTienda")
        public String guardarTienda (@ModelAttribute("tienda") @Valid Tienda tienda, BindingResult bindingResult,
                RedirectAttributes attr,
                Model model){
            if (bindingResult.hasErrors()) {
                model.addAttribute("listaTiendas", tiendaRepository.findAll());
                return "UsuarioSede/U-TiendaDistribuidor";
            } else {

                if (tienda.getIdtienda() == 0) {
                    tiendaRepository.save(tienda);
                    attr.addFlashAttribute("msg", "Tienda agregada a la lista");
                    return "redirect:/sede/registroTiendas";
                } else {
                    tiendaRepository.save(tienda);
                    attr.addFlashAttribute("msg", "Tienda actualizada correctamente");
                    return "redirect:/sede/registroTiendas";
                }
            }
        }

        @PostMapping("/buscarTienda")
        public String buscarComunidad (@RequestParam("searchField") String searchField,
                Model model){

            List<Tienda> listaT = tiendaRepository.buscarPorNombreDeTienda(searchField);
            model.addAttribute("listaTiendas", listaT);
            return "UsuarioSede/U-TiendaDistribuidor";
        }

        @GetMapping("borrarTienda")
        public String borrarTiendas (Model model,@RequestParam("id") int idtienda, RedirectAttributes attr){
            Optional<Tienda> obtenerTienda = tiendaRepository.findById(idtienda);
            if (obtenerTienda.isPresent()) {
                tiendaRepository.deleteById(idtienda);
                attr.addFlashAttribute("msg", "Tienda borrada exitosamente");
            }
            return "redirect:/sede/registroTiendas";
        }

        @GetMapping("editarTienda")
        public String editarTienda ( @RequestParam("id") int idtienda, @ModelAttribute("tienda") Tienda tienda, Model
        model){
            Optional<Tienda> tiendaPorID = tiendaRepository.findById(idtienda);
            if (tiendaPorID.isPresent()) {
                model.addAttribute("tienda", tiendaPorID.get());

                return "UsuarioSede/U-TiendaDistribuidor";
            } else {
                return "redirect:/sede/registroTiendas";
            }
        }

        //----------------FIN CRUD TIENDAS-------------------

    }


