package com.example.proyecto.controller;

import com.example.proyecto.dto.*;
import com.example.proyecto.entity.*;
import com.example.proyecto.repository.*;
import com.example.proyecto.services.VentasService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import java.io.IOException;
import java.util.*;

import java.net.InetAddress;
import java.net.UnknownHostException;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/gestor")
public class GestorController {
    @Autowired
    HistorialRepository historialRepository;
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
    VentaRepository ventaRepository;
    @Autowired
    SedeRepository sedeRepository;
    @Autowired
    UsuarioRepository usuarioRepository;
    @Autowired
    DenominacionRepository denominacionRepository;
    @Autowired
    TamañoRepository tamañoRepository;
    @Autowired
    LineaRepository lineaRepository;
    @Autowired
    VentasService ventasService;
    @Autowired
    ServletContext context;


    // ----------------------- ENLACES ---------------------------------

    @GetMapping("AnadirCompra")
    public String AnadirCompra(@ModelAttribute("historial") Historial historial, Model model, @RequestParam("id") int id) {
        Optional<Inventario> OPinventario = inventarioRepository.findById(id);
        if (OPinventario.isPresent()) {
            Inventario inventario = OPinventario.get();
            historial.setInventario(inventario);
            return "Gestor/G-AnadirHistorial";
        } else {
            return "redirect:/gestor/";
        }

    }

    @PostMapping("gestorGuardarCompra")
    public String gestorGuardarCompra(@ModelAttribute("historial") @Valid Historial historial, BindingResult bindingResult,
                                      Model model,
                                      RedirectAttributes attr) {
        if (bindingResult.hasErrors()) {
            if (inventarioRepository.findById(historial.getInventario().getIdInventario()).isPresent()) {
                historial.setInventario(inventarioRepository.findById(historial.getInventario().getIdInventario()).get());
                model.addAttribute("historial", historial);
                return "Gestor/G-AnadirHistorial";
            } else {
                return "redirect:/gestor/";
            }
        } else {
            System.out.println("---------------- no binding result error");
            System.out.println("ID INVENTARIO ENCONTRADO: " + historial.getInventario().getIdInventario());
            Optional<Inventario> OPinventario = inventarioRepository.findById(historial.getInventario().getIdInventario());

            if (OPinventario.isPresent()) {
                System.out.println("--------------- inventario presnete id: " + OPinventario.get().getIdInventario());
                historial.setIdhistorial(0);
                Inventario inventario = OPinventario.get();
                inventario.setStock(inventario.getStock() + historial.getCantidad());
                historialRepository.save(historial);
                inventarioRepository.save(inventario);
                attr.addFlashAttribute("msg", "Compra realizada exitosamente");
                return "redirect:/gestor/gestorPrincipal";
            } else { // error en id inventario. hackerman?
                System.out.println("error id inventario!");
                attr.addFlashAttribute("msgError", "Error en la compra");
                return "redirect:/gestor/gestorPrincipal";
            }
        }
    }


    @GetMapping("perfil")
    public String perfil() {
        return "Gestor/G-Perfil";
    }


    @GetMapping("gestorGestionVentas")
    public String registroVentas() {

        return "G-GestionVentas";
    }


    @GetMapping("gestorReporteVentas")
    public String reporteVentas1(Model model, RedirectAttributes attr) {
        List<ClientesQueCompraron> listaClientes = ventaRepository.obtenerClientes();
        List<ListaSedesQueVendieron> listasedes = ventaRepository.obtenerSedes();
        List<FechaVenta> listaAnhos = ventaRepository.obtenerAñosDeVenta();
        List<FechaMesVenta> listaMeses = ventaRepository.obtenerMesesDeVenta();
        List<ProductosQueSeVendieron> listaProductosVendidos = ventaRepository.obtenerProductosVendidos();
        List<ProdComunidades> listaComunidades = ventaRepository.obtenerPComunidad();

        if (listaClientes == null && listasedes == null && listaAnhos == null && listaMeses == null && listaProductosVendidos == null && listaComunidades == null) {
            attr.addFlashAttribute("msg1", "Aún no se han realizado ventas.Pulse sobre este mensaje para registrar la primera venta");
            return "redirect:/gestor/gestorReporteVentasError";
        }

        model.addAttribute("listaClients", listaClientes);
        model.addAttribute("listasedes", listasedes);
        model.addAttribute("listaAnhos", listaAnhos);
        model.addAttribute("listaMeses", listaMeses);
        model.addAttribute("listaProductosVendidos", listaProductosVendidos);
        model.addAttribute("listaComunidades", listaComunidades);
        return "Gestor/G-GenReporte";
    }

    @GetMapping("gestorReporteVentasError")
    public String reporteVentas2(Model model) {
        List<ClientesQueCompraron> listaClientes = ventaRepository.obtenerClientes();
        List<ListaSedesQueVendieron> listasedes = ventaRepository.obtenerSedes();
        List<FechaVenta> listaAnhos = ventaRepository.obtenerAñosDeVenta();
        List<FechaMesVenta> listaMeses = ventaRepository.obtenerMesesDeVenta();
        List<ProductosQueSeVendieron> listaProductosVendidos = ventaRepository.obtenerProductosVendidos();
        List<ProdComunidades> listaComunidades = ventaRepository.obtenerPComunidad();

        model.addAttribute("listaClientes1", listaClientes);
        model.addAttribute("listasedes1", listasedes);
        model.addAttribute("listaAnhos1", listaAnhos);
        model.addAttribute("listaMeses1", listaMeses);
        model.addAttribute("listaProductosVendidos1", listaProductosVendidos);
        model.addAttribute("listaComunidades1", listaComunidades);
        return "Gestor/G-GenReporte";
    }


    //------------------------Perfil-------------------------------------------
    @GetMapping("editarInfo")
    public String editarInfo(@ModelAttribute("perfil") Perfil perfil, HttpSession session) {
        Usuarios u = (Usuarios) session.getAttribute("user");
        perfil.setCorreo(u.getCorreo());
        perfil.setTelefono(u.getTelefono());
        return "Gestor/G-Perfil";
    }

    @PostMapping("guardarPerfil")
    public String guardarInfo(@ModelAttribute("perfil") @Valid Perfil perfil,
                              BindingResult bindingResult,
                              RedirectAttributes attr, HttpSession session, Model model) {

        Usuarios usuarioLog = (Usuarios) session.getAttribute("user");

        if (bindingResult.hasErrors()) {
            return "Gestor/G-Perfil";
        } else {
            if (perfil.getCorreo().equals(usuarioLog.getCorreo())) {
                attr.addFlashAttribute("msg", "Información personal editada con éxito");
                usuarioLog.setCorreo(perfil.getCorreo());
                usuarioLog.setTelefono(perfil.getTelefono());
                usuarioRepository.save(usuarioLog);
                session.setAttribute("user", usuarioLog);
                return "redirect:/gestor/editarInfo";
            } else {
                Usuarios us = usuarioRepository.findByCorreo(perfil.getCorreo());
                if (us != null && us.getActivo() == 1) {
                    model.addAttribute("msgC", "Este correo ya está registrado");
                    return "Gestor/G-Perfil";
                } else {
                    attr.addFlashAttribute("msg", "Información personal editada con éxito");
                    usuarioLog.setCorreo(perfil.getCorreo());
                    usuarioLog.setTelefono(perfil.getTelefono());
                    usuarioRepository.save(usuarioLog);
                    session.setAttribute("user", usuarioLog);
                    return "redirect:/gestor/editarInfo";
                }
            }
        }
    }

    // ----------------------- CRUD USUARIOS SEDE ---------------------------------

    @GetMapping("gestorRegistroUsuarioSede")
    public String registroUsuarioSede(@ModelAttribute("usuarios") Usuarios usuarios, Model model) {
        model.addAttribute("listasedes", sedeRepository.findAll());
        return "Gestor/G-RegistroUsuarioSede";
    }


    @GetMapping("gestorListaUsuarioSede")
    public String listaUsuarioSede(Model model) {
        List<Usuarios> listausuariosedes = usuarioRepository.findByTipoAndActivo("sede", 1);
        model.addAttribute("listausuariosedes", listausuariosedes);
        return "Gestor/G-ListaUsuarioSede";
    }

    @GetMapping("gestorEditUsuarioSede")
    public String editarUsuarioSede(@RequestParam("idusuarios") int idusuarios, @ModelAttribute("usuarios") Usuarios usuarios, Model model) {
        Optional<Usuarios> usuariosID = usuarioRepository.findById(idusuarios);
        if (usuariosID.isPresent()) {
            usuarios = usuariosID.get();
            model.addAttribute("usuarios", usuarios);
            model.addAttribute("listasedes", sedeRepository.findAll());
            return "Gestor/G-EditUsuarioSede";
        } else {
            return "redirect:/gestor/gestorListaUsuarioSede";
        }
    }


    @PostMapping("guardarUsuarioSede")
    public String guardarUsuarioSede(@ModelAttribute("usuarios") @Valid Usuarios usuarios, BindingResult bindingResult,
                                     Model model,
                                     RedirectAttributes attr, HttpServletRequest request) throws MessagingException {
        if (bindingResult.hasErrors()) {
            if (!usuarios.getCorreo().matches("^[A-Za-z0-9\\._-]+@[mM][Oo][Ss][Qq][Oo][Yy]\\.[Oo][Rr][Gg]$")) {

                model.addAttribute("msgError", "El correo ingresado no es un correo");
            }

            usuarios.setCorreo(usuarios.getCorreo().toLowerCase());
            if (usuarios.getIdusuarios() != 0) {
                Optional<Usuarios> usuariosID = usuarioRepository.findById(usuarios.getIdusuarios());
                if (usuariosID.isPresent()) {// todo mostrar errores
                    usuarios = usuariosID.get();
                    model.addAttribute("usuarios", usuarios);
                    model.addAttribute("listasedes", sedeRepository.findAll());
                    return "/Gestor/G-EditUsuarioSede";
                } else {//todo mostrar errores
                    model.addAttribute("listasedes", sedeRepository.findAll());
                    return "/Gestor/G-RegistroUsuarioSede";
                }
            } else {
                model.addAttribute("listasedes", sedeRepository.findAll());
                return "Gestor/G-RegistroUsuarioSede";
            }

        } else {
            if (!usuarios.getCorreo().matches("^[A-Za-z0-9\\._-]+@[mM][Oo][Ss][Qq][Oo][Yy]\\.[Oo][Rr][Gg]$")) {// validacion tipo correo
                usuarios.setCorreo(usuarios.getCorreo().toLowerCase());
                if (usuarios.getIdusuarios() != 0) {
                    Optional<Usuarios> usuariosID = usuarioRepository.findById(usuarios.getIdusuarios());
                    if (usuariosID.isPresent()) {// todo mostrar errores
                        usuarios = usuariosID.get();
                        model.addAttribute("usuarios", usuarios);
                        model.addAttribute("listasedes", sedeRepository.findAll());
                        return "gestor/G-EditUsuarioSede";
                    } else {//todo mostrar errores
                        model.addAttribute("listasedes", sedeRepository.findAll());
                        return "/Gestor/G-RegistroUsuarioSede";
                    }
                } else {

                    System.out.println("ENTRO EN ESTO NO ES UN CORREO");
                    model.addAttribute("msgError", "Este correo no es valido");

                    model.addAttribute("listasedes", sedeRepository.findAll());
                    model.addAttribute(usuarios);

                    return "Gestor/G-RegistroUsuarioSede";
                }
            }
            if (usuarios.getIdusuarios() == 0 && usuarioRepository.findByCorreo(usuarios.getCorreo()) == null) {
                usuarios.setPassword(getAlphaNumericString(12));
                usuarios.setTipo("sede");
                usuarios.setActivo(1);
                String passwordSinEncriptar = usuarios.getPassword();
                BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
                usuarios.setPassword(bCryptPasswordEncoder.encode(usuarios.getPassword()));
                System.out.println(usuarios.getPassword());
                if (!sedeRepository.findById(usuarios.getSede().getIdsede()).isPresent()) {
                    System.out.println("hackerman cambio el id sede");
                    model.addAttribute("msgError", "Esta sede no existe");
                    model.addAttribute("listasedes", sedeRepository.findAll());
                    model.addAttribute(usuarios);

                    return "Gestor/G-RegistroUsuarioSede";
                }
                usuarioRepository.save(usuarios);
                //Envia email para recuperar la cuenta (se envia email con CambiarContra.html)
                Email email = new Email();
                email.emailEnviarPrimeraContraseña(usuarios.getCorreo(), passwordSinEncriptar, usuarios.getCorreo());

                attr.addFlashAttribute("msgSucc", "Usuario sede creado exitosamente");
                return "redirect:/gestor/gestorListaUsuarioSede";
            } else if (usuarios.getIdusuarios() != 0) {
                usuarios.setTipo("sede");
                usuarios.setActivo(usuarioRepository.findById(usuarios.getIdusuarios()).get().getActivo());
                usuarios.setPassword(usuarioRepository.findById(usuarios.getIdusuarios()).get().getPassword());
                usuarios.setCorreo(usuarioRepository.findById(usuarios.getIdusuarios()).get().getCorreo());


                usuarioRepository.save(usuarios);
                attr.addFlashAttribute("msgSucc", "Usuario sede actualizado exitosamente");
                return "redirect:/gestor/gestorListaUsuarioSede";
            } else { //ya existe el correo, mostrar errores
                if (usuarioRepository.findByCorreo(usuarios.getCorreo()) != null) {
                    System.out.println("ENTRO EN ESTO NO ES U CORREO");
                    model.addAttribute("msgError", "Este no es un correo valido");
                }
                model.addAttribute("listasedes", sedeRepository.findAll());
                model.addAttribute(usuarios);

                return "Gestor/G-RegistroUsuarioSede";
            }
        }
    }


    @GetMapping("borrarUsuarioSede")
    public String borrarUsuarioSede(@RequestParam("idusuarios") int idusuarios, RedirectAttributes attr) {

        Optional<Usuarios> optionalUsuarios = usuarioRepository.findById(idusuarios);
        if (optionalUsuarios.isPresent()) {
            usuarioRepository.deleteById(idusuarios);
            attr.addFlashAttribute("msgSucc", "Usuario Sede Eliminado");
        } else {
            attr.addFlashAttribute("msgFail", "Este usuario no existe");
        }
        return "redirect:/gestor/gestorListaUsuarioSede";
    }


    // ----------------------- FIN CRUD USUARIOS SEDE ---------------------------------


    // ----------------------- CRUD SEDES ---------------------------------


    @GetMapping("gestorResgistroSede")
    public String registroSede(@ModelAttribute("sede") Sede sede) {
        return "Gestor/G-RegistroSede";
    }

    @GetMapping("gestorListaSedes")
    public String listaSede(Model model) {
        List<Sede> listasedes = sedeRepository.findAll();
        model.addAttribute("listasedes", listasedes);
        return "Gestor/G-ListaSedes";
    }

    @GetMapping("gestorEditSede")
    public String editarSede(@RequestParam("idsede") int idsede, @ModelAttribute("sede") Sede sede, Model model) {
        Optional<Sede> sedeID = sedeRepository.findById(idsede);
        if (sedeID.isPresent()) {
            sede = sedeID.get();
            model.addAttribute("sede", sede);
            model.addAttribute("listasedes", sedeRepository.findAll());
            return "Gestor/G-EditSede";
        } else {
            return "redirect:/gestor/gestorListaSedes";
        }
    }


    @PostMapping("guardarSede")
    public String guardarSede(@ModelAttribute("sede") @Valid Sede sede, BindingResult bindingResult,
                              Model model,
                              RedirectAttributes attr) {
        if (bindingResult.hasErrors()) {
            return "Gestor/G-RegistroSede";
        } else {
            //
            Sede sedeTemp = sedeRepository.findByNombre(sede.getNombre());
            if (sedeTemp != null) {
                model.addAttribute(sede);
                model.addAttribute("msgError", "Los datos ingresados ya existen");
                return "Gestor/G-EditSede";
            }
            //
            if (sede.getIdsede() == null) {
                sedeRepository.save(sede);
                attr.addFlashAttribute("msg", "Sede creada exitosamente");
                return "redirect:/gestor/gestorListaSedes";
            } else if (sede.getIdsede() != 0) {
                sedeRepository.save(sede);
                attr.addFlashAttribute("msg", "Sede actualizada exitosamente");
                return "redirect:/gestor/gestorListaSedes";
            } else { //EL IDSEDE ES IGUAL A 0
                // System.out.println("ID SEDE ES 0 POR ALGUA RAZON");
                model.addAttribute(sede);
                attr.addFlashAttribute("msgError", "Los datos ingresados ya existen, por favor modificarlo");
                return "Gestor/G-RegistroSede";
            }
        }
    }


    @GetMapping("borrarSede")
    public String borrarSede(@RequestParam("idsede") int idsede, RedirectAttributes attr) {

        Optional<Sede> optionalSede = sedeRepository.findById(idsede);
        if (optionalSede.isPresent()) {
            // sedeRepository.deleteById(idsede);
            // attr.addFlashAttribute("msg", "Sede Eliminada");
            try {
                sedeRepository.deleteById(idsede);
                attr.addFlashAttribute("msg", "Sede Eliminada");
            } catch (Exception e) {
                attr.addFlashAttribute("msgE", "Ocurrió un error, no puede ser borrada");
            }
        }
        return "redirect:/gestor/gestorListaSedes";
    }

    // ----------------------- FIN CRUD SEDES ---------------------------------

// ----------------------- CRUD INVENTARIO ---------------------------------

    @GetMapping(value = {"", "gestorPrincipal"})
    public String inventarioGestor(Model model) {
        List<Inventario> inventario = inventarioRepository.listarStockMayor0();
        // para listar el stock total en vez del stock en almacen principal
        /*
        for (Inventario inv: inventario) {
            Integer stockTotal = 0;
            stockTotal = inventariosedeRepository.obtenerStockTotal(inv.getIdInventario());
            if(stockTotal != null){
                inv.setStock(stockTotal);
            }
        }*/
        //todo mostrar  mensaje de stock bajo
        boolean validar1 = false;
        int validar2=0;
        //Se va a definir una variable que se pasará como model attribute para poder mostrar un modal al inicio
        if (productoRepository.productoPorEstado("Vencida").size()>0 && productoRepository.productoPorEstado("Proxima").size()>0){
            validar1=true;
        }

        model.addAttribute("inventario", inventario);

        model.addAttribute("listaComunidades", comunidadRepository.findAll());
        model.addAttribute("listaArtesanos", artesanoRepository.findAll());
        model.addAttribute("listaCategoria", categoriaRepository.findAll());

        model.addAttribute("validar",validar1);

        return "Gestor/G-Inventario";
    }

    @GetMapping("/buscador")
    public String buscadorAvanzado(Model model,@RequestParam("idComu") int idComu, @RequestParam("tipo") String tipo,
                                   @RequestParam("idArt") int idArt,@RequestParam("idCate") int idCate) {

        if (idComu != 0 && tipo.equalsIgnoreCase("todo") && idArt == 0 && idCate == 0) {
            List<Inventario> Lista1 = inventarioRepository.listarPorComunidad(idComu);
            model.addAttribute("listaComunidades", comunidadRepository.findAll());
            model.addAttribute("listaArtesanos", artesanoRepository.findAll());
            model.addAttribute("listaCategoria", categoriaRepository.findAll());
            model.addAttribute("inventario", Lista1);
            return "Gestor/G-Inventario";
        } else if (idComu == 0 && tipo.equalsIgnoreCase("todo") && idArt == 0 && idCate != 0) {
            List<Inventario> Lista2 = inventarioRepository.listarPorCategoria(idCate);
            model.addAttribute("listaComunidades", comunidadRepository.findAll());
            model.addAttribute("listaArtesanos", artesanoRepository.findAll());
            model.addAttribute("listaCategoria", categoriaRepository.findAll());
            model.addAttribute("inventario", Lista2);
            return "Gestor/G-Inventario";
        } else if (idComu == 0 && !tipo.equalsIgnoreCase("todo") && idArt == 0 && idCate == 0) {
            List<Inventario> Lista3 = inventarioRepository.listarPorAdquisicion(tipo);
            model.addAttribute("listaComunidades", comunidadRepository.findAll());
            model.addAttribute("listaArtesanos", artesanoRepository.findAll());
            model.addAttribute("listaCategoria", categoriaRepository.findAll());
            model.addAttribute("inventario", Lista3);
            return "Gestor/G-Inventario";
        } else if (idComu == 0 && tipo.equalsIgnoreCase("consignado") && idArt != 0 && idCate == 0) {
            List<Inventario> Lista4 = inventarioRepository.listarPorArtesanoConConsigna(tipo, idArt);
            model.addAttribute("listaComunidades", comunidadRepository.findAll());
            model.addAttribute("listaArtesanos", artesanoRepository.findAll());
            model.addAttribute("listaCategoria", categoriaRepository.findAll());
            model.addAttribute("inventario", Lista4);
            return "Gestor/G-Inventario";
        }else if (idComu != 0 && tipo.equalsIgnoreCase("todo") && idArt == 0 && idCate != 0){
            List<Inventario> Lista5 = inventarioRepository.listarPorCategoriaYComunidad(idCate, idComu);
            model.addAttribute("listaComunidades", comunidadRepository.findAll());
            model.addAttribute("listaArtesanos", artesanoRepository.findAll());
            model.addAttribute("listaCategoria", categoriaRepository.findAll());
            model.addAttribute("inventario", Lista5);
            return "Gestor/G-Inventario";
        } else if (idComu != 0 && !tipo.equalsIgnoreCase("todo") && idArt == 0 && idCate == 0){
            List<Inventario> Lista6 = inventarioRepository.listarPorComunidadYModalidad( idComu, tipo);
            model.addAttribute("listaComunidades", comunidadRepository.findAll());
            model.addAttribute("listaArtesanos", artesanoRepository.findAll());
            model.addAttribute("listaCategoria", categoriaRepository.findAll());
            model.addAttribute("inventario", Lista6);
            return "Gestor/G-Inventario";
        }else if (idComu != 0 && tipo.equalsIgnoreCase("consignado") && idArt != 0 && idCate == 0){
            List<Inventario> Lista6 = inventarioRepository.listarPorComunidadConsignadoYArtesano( idComu, idArt);
            model.addAttribute("listaComunidades", comunidadRepository.findAll());
            model.addAttribute("listaArtesanos", artesanoRepository.findAll());
            model.addAttribute("listaCategoria", categoriaRepository.findAll());
            model.addAttribute("inventario", Lista6);
            return "Gestor/G-Inventario";
        }else if (idComu == 0 && tipo.equalsIgnoreCase("consignado") && idArt != 0 && idCate != 0){
            List<Inventario> Lista7 = inventarioRepository.listarPorCategoriaConsignadoYArtesano( idCate, idArt);
            model.addAttribute("listaComunidades", comunidadRepository.findAll());
            model.addAttribute("listaArtesanos", artesanoRepository.findAll());
            model.addAttribute("listaCategoria", categoriaRepository.findAll());
            model.addAttribute("inventario", Lista7);
            return "Gestor/G-Inventario";
        } else if (idComu == 0 && !tipo.equalsIgnoreCase("todo") && idArt == 0 && idCate != 0){
            List<Inventario> Lista8 = inventarioRepository.listarPorCategoriaYModalidad( idCate, tipo);
            model.addAttribute("listaComunidades", comunidadRepository.findAll());
            model.addAttribute("listaArtesanos", artesanoRepository.findAll());
            model.addAttribute("listaCategoria", categoriaRepository.findAll());
            model.addAttribute("inventario", Lista8);
            return "Gestor/G-Inventario";
        }else if (idComu != 0 && !tipo.equalsIgnoreCase("todo") && idArt == 0 && idCate != 0){
            List<Inventario> Lista9 = inventarioRepository.listarPorCategoriaYComunidadYModalidad( idCate, tipo, idComu);
            model.addAttribute("listaComunidades", comunidadRepository.findAll());
            model.addAttribute("listaArtesanos", artesanoRepository.findAll());
            model.addAttribute("listaCategoria", categoriaRepository.findAll());
            model.addAttribute("inventario", Lista9);
            return "Gestor/G-Inventario";
        }else if (idComu != 0 && tipo.equalsIgnoreCase("consignado") && idArt != 0 && idCate != 0){
            List<Inventario> Lista10 = inventarioRepository.listarPorCategoriaComunidadConsignadoYArtesano( idCate, idComu, idArt);
            model.addAttribute("listaComunidades", comunidadRepository.findAll());
            model.addAttribute("listaArtesanos", artesanoRepository.findAll());
            model.addAttribute("listaCategoria", categoriaRepository.findAll());
            model.addAttribute("inventario", Lista10);
            return "Gestor/G-Inventario";
        } else{
            return "redirect:/gestor";
        }

    }

    @GetMapping("/verHistorial")
    public String HistorialDeInventario(Model model, @RequestParam("id") int id) {
        Optional<Inventario> inventario1 = inventarioRepository.findById(id);
        if (inventario1.isPresent()) {
            List<Historial> historiales = historialRepository.listarHistorialDeUnPro(inventario1.get().getIdInventario());
            model.addAttribute("historiales", historiales);
            model.addAttribute("inventario", inventario1.get());
            return "Gestor/G-ListaDeHistorial";
        } else {
            return "redirect:/gestor/gestorPrincipal";
        }

    }

    @GetMapping("productos")
    public String listarProductos(Model model) {
        List<Producto> productos = productoRepository.findAll();
        model.addAttribute("productos", productos);
        return "Gestor/G-ListarProductos";
    }

    @GetMapping("gestorListarSinStock")
    public String listaSinStock(Model model) {
        List<Inventario> listaSinStock = inventarioRepository.findByStock(0);
        model.addAttribute("listaSinStock", listaSinStock);
        return "Gestor/G-ListaSinStock";
    }


    @GetMapping("gestorDetallesProducto")
    public String detallesProdcutoCompra(Model model, @RequestParam("id") int id) {
        Optional<Inventario> inventario = inventarioRepository.findById(id);
        List<Historial> listaHistorial = historialRepository.findAll();
        Historial historial = null;
        if (inventario.isPresent()) {
            Inventario inventario2 = inventario.get();
            for (Historial hi : listaHistorial) {
                if (hi.getInventario().getIdInventario() == inventario2.getIdInventario()) {
                    historial = hi;
                    break;
                }
            }

            // no queria crear un DTO asi que ahora en idSede le guardare el Stock de la sede
            List<Inventariosede> listaInvSede = inventariosedeRepository.findByInventario(inventario2);
            if (!listaInvSede.isEmpty()) {
                ArrayList<Sede> StockSede = new ArrayList<Sede>();
                for (Inventariosede IS : listaInvSede) {
                    if (IS.getStock() > 0) {
                        Sede temp = new Sede();
                        temp.setIdsede(IS.getStock());
                        temp.setNombre(IS.getSede().getNombre());

                        StockSede.add(temp);
                    }
                }
                if (!StockSede.isEmpty()) {
                    model.addAttribute("StockSede", StockSede);
                }
            }

            model.addAttribute("historial", historial);
            model.addAttribute("producto", inventario2);

            return "Gestor/G-DetallesProdcuto";
        } else {
            return "redirect:/gestor/gestorPrincipal";
        }
    }


    @GetMapping("gestorEditProducto")
    public String EditProdCompra(@ModelAttribute("formulario") FormularioProducto formulario,
                                 Model model, @RequestParam("id") int id) {
        formulario.setCrearActualizar(id);
        Optional<Producto> producto = productoRepository.findById(id);
        if (producto.isPresent()) {
            Producto productooooo = producto.get();
            model.addAttribute("listaComunidades", comunidadRepository.findAll());
            model.addAttribute("listaDenominaciones", denominacionRepository.findAll());
            model.addAttribute("listaCategorias", categoriaRepository.findAll());
            model.addAttribute("listaTama", tamañoRepository.findAll());
            model.addAttribute("listaLinea", lineaRepository.findAll());
            model.addAttribute("listaArtesanos", artesanoRepository.findAll());
            formulario.setDescripcion(productooooo.getDenominacion().getDescripcion());
            formulario.setNombreCategoria(productooooo.getCategoria().getNombre());
            formulario.setNombreComun(productooooo.getComunidad().getNombre());
            formulario.setNombreLinea(productooooo.getDenominacion().getLinea().getNombre());
            formulario.setNombreProducto(productooooo.getDenominacion().getNombre());
            formulario.setNombreTama(productooooo.getTamanho().getNombre());
            formulario.setCodigoProducto(productooooo.getDenominacion().getCodigonombre());
            formulario.setCodDescripcion(productooooo.getDenominacion().getCodigodescripcion());
            return "Gestor/G-EditProdCompra";
        } else {
            return "redirect:/gestor/productos";
        }

    }

    @GetMapping("gestorRegProducto")
    public String RegistroCompra(@ModelAttribute("formulario") FormularioProducto formulario,
                                 Model model) {
        model.addAttribute("listaComunidades", comunidadRepository.findAll());
        model.addAttribute("listaDenominaciones", denominacionRepository.findAll());
        model.addAttribute("listaCategorias", categoriaRepository.findAll());
        model.addAttribute("listaTama", tamañoRepository.findAll());
        model.addAttribute("listaLinea", lineaRepository.findAll());
        model.addAttribute("listaArtesanos", artesanoRepository.findAll());
        formulario.setCrearActualizar(0);
        return "Gestor/G-RegCompra";
    }


    @PostMapping("guardarProducto")
    public String guardarProducto(@ModelAttribute("formulario") @Valid FormularioProducto formulario,
                                  BindingResult bindingResult, RedirectAttributes attr, Model model, @RequestParam("modalidad") String modalidad) {

        if (bindingResult.hasErrors()) {
            if (formulario.getCrearActualizar() == 0) {
                System.out.println("Tiene errores");
                model.addAttribute("listaComunidades", comunidadRepository.findAll());
                model.addAttribute("listaDenominaciones", denominacionRepository.findAll());
                model.addAttribute("listaCategorias", categoriaRepository.findAll());
                model.addAttribute("listaTama", tamañoRepository.findAll());
                model.addAttribute("listaLinea", lineaRepository.findAll());
                model.addAttribute("listaArtesanos", artesanoRepository.findAll());
                return "Gestor/G-RegCompra";
            } else if (formulario.getCrearActualizar() > 0) {
                System.out.println("Tiene errores");
                model.addAttribute("listaComunidades", comunidadRepository.findAll());
                model.addAttribute("listaDenominaciones", denominacionRepository.findAll());
                model.addAttribute("listaCategorias", categoriaRepository.findAll());
                model.addAttribute("listaTama", tamañoRepository.findAll());
                model.addAttribute("listaLinea", lineaRepository.findAll());
                model.addAttribute("listaArtesanos", artesanoRepository.findAll());
                return "Gestor/G-EditProdCompra";
            } else {
                return "redirect:/gestor/productos";
            }

        } else {
            System.out.println("No Tiene errores");
            List<Categoria> categoria1 = categoriaRepository.findByNombre(formulario.getNombreCategoria());
            List<Linea> linea1 = lineaRepository.findByNombre(formulario.getNombreLinea());
            List<Comunidad> comu = comunidadRepository.findByNombre(formulario.getNombreComun());
            List<Tamaño> tamaños = tamañoRepository.findByNombre(formulario.getNombreTama());
            List<Denominacion> denomi1 = denominacionRepository.buscarRepetido(formulario.getNombreProducto(), formulario.getCodigoProducto(), formulario.getCodDescripcion());

            if (formulario.getCrearActualizar() == 0) {
                System.out.println("Voy a crear");
                if (!denomi1.isEmpty()) {
                    model.addAttribute("msgE", "Los datos ingresados ya existen");
                    model.addAttribute("listaComunidades", comunidadRepository.findAll());
                    model.addAttribute("listaDenominaciones", denominacionRepository.findAll());
                    model.addAttribute("listaCategorias", categoriaRepository.findAll());
                    model.addAttribute("listaTama", tamañoRepository.findAll());
                    model.addAttribute("listaLinea", lineaRepository.findAll());
                    model.addAttribute("listaArtesanos", artesanoRepository.findAll());
                    return "Gestor/G-RegCompra";
                } else {
                    if (!categoria1.isEmpty() && !linea1.isEmpty() && !comu.isEmpty() && !tamaños.isEmpty()) {
                        Producto producto = new Producto();

                        Denominacion denominacion = new Denominacion();
                        denominacion.setCodigodescripcion(formulario.getCodDescripcion());
                        denominacion.setCodigonombre(formulario.getCodigoProducto());
                        denominacion.setNombre(formulario.getNombreProducto());
                        denominacion.setDescripcion(formulario.getDescripcion());
                        producto.setCategoria(categoria1.get(0));
                        producto.setComunidad(comu.get(0));
                        producto.setTamanho(tamaños.get(0));
                        denominacion.setLinea(linea1.get(0));
                        denominacionRepository.save(denominacion);
                        List<Denominacion> denomi2 = denominacionRepository.findByNombre(denominacion.getNombre());
                        producto.setDenominacion(denomi2.get(0));
                        if (modalidad.equalsIgnoreCase("compra")) {
                            formulario.setModa(modalidad);
                            String codigo = denomi2.get(0).getLinea().getCodigo() + categoria1.get(0).getCodigo() + denomi2.get(0).getCodigonombre() +
                                    denomi2.get(0).getCodigodescripcion() + tamaños.get(0).getCodigo() + comu.get(0).getCodigo();
                            producto.setCodigoGenerado(codigo);
                            producto.setAdquisicion(adquisicionRepository.findById(1).get());
                            productoRepository.save(producto);
                            attr.addFlashAttribute("msg1", "Guardado Exitósamente");
                            return "redirect:/gestor/productos";
                        } else if (modalidad.equalsIgnoreCase("consignacion")) {
                            formulario.setModa(modalidad);
                            Adquisicion adqui = new Adquisicion();
                            Optional<Artesano> artesa = artesanoRepository.findById(formulario.getCodigoArtesano());
                            if (!artesa.isPresent()) {
                                model.addAttribute("msgE", "El artesano no existe");
                                model.addAttribute("listaComunidades", comunidadRepository.findAll());
                                model.addAttribute("listaDenominaciones", denominacionRepository.findAll());
                                model.addAttribute("listaCategorias", categoriaRepository.findAll());
                                model.addAttribute("listaTama", tamañoRepository.findAll());
                                model.addAttribute("listaLinea", lineaRepository.findAll());
                                model.addAttribute("listaArtesanos", artesanoRepository.findAll());
                                return "Gestor/G-RegCompra";
                            }
                            adqui.setFecha(formulario.getFechainicio());
                            adqui.setFechafin(formulario.getFechafin());
                            adqui.setArtesano(artesa.get());
                            adqui.setModalidad("consignado");
                            adquisicionRepository.save(adqui);
                            List<Adquisicion> listaAdqui = adquisicionRepository.findAll();
                            producto.setAdquisicion(listaAdqui.get(listaAdqui.size() - 1));
                            String año = String.valueOf(formulario.getFechainicio().getYear()).substring(2);

                            String codigo = denomi2.get(0).getLinea().getCodigo() + categoria1.get(0).getCodigo() + denomi2.get(0).getCodigonombre() +
                                    denomi2.get(0).getCodigodescripcion() + tamaños.get(0).getCodigo() + comu.get(0).getCodigo() + año + String.valueOf(formulario.getFechainicio().getMonth()).substring(0, 3);
                            producto.setCodigoGenerado(codigo);
                            productoRepository.save(producto);
                            attr.addFlashAttribute("msg1", "Guardado Exitosamente");
                            return "redirect:/gestor/productos";
                        } else {
                            model.addAttribute("msgE", "Valor inválido. Solo se permite consignación o compra");
                            model.addAttribute("listaComunidades", comunidadRepository.findAll());
                            model.addAttribute("listaDenominaciones", denominacionRepository.findAll());
                            model.addAttribute("listaCategorias", categoriaRepository.findAll());
                            model.addAttribute("listaTama", tamañoRepository.findAll());
                            model.addAttribute("listaLinea", lineaRepository.findAll());
                            model.addAttribute("listaArtesanos", artesanoRepository.findAll());
                            return "Gestor/G-RegCompra";

                        }
                    } else {
                        model.addAttribute("msgE", "Valor seleccionado nó válido");
                        model.addAttribute("listaComunidades", comunidadRepository.findAll());
                        model.addAttribute("listaDenominaciones", denominacionRepository.findAll());
                        model.addAttribute("listaCategorias", categoriaRepository.findAll());
                        model.addAttribute("listaTama", tamañoRepository.findAll());
                        model.addAttribute("listaLinea", lineaRepository.findAll());
                        model.addAttribute("listaArtesanos", artesanoRepository.findAll());
                        return "Gestor/G-RegCompra";
                    }
                }
            } else if (formulario.getCrearActualizar() > 0) {
                System.out.println("Voy a actualizar");
                Optional<Producto> producto2 = productoRepository.findById(formulario.getCrearActualizar());
                if (producto2.isPresent()) {
                    Producto producto1 = producto2.get();
                    producto1.getDenominacion().setDescripcion(formulario.getDescripcion());
                    producto1.getDenominacion().setNombre(formulario.getNombreProducto());
                    productoRepository.save(producto1);
                    attr.addFlashAttribute("msg1", "Actualizado Correctamente");
                    return "redirect:/gestor/productos";
                } else {
                    attr.addFlashAttribute("msg", "ID no válido");
                    return "redirect:/gestor/productos";
                }
            } else {
                attr.addFlashAttribute("msg", "ID no válido");
                return "redirect:/gestor/productos";
            }
        }
    }

    @GetMapping("borrarProducto")
    public String borrarProducto(@RequestParam("id") int id, Model model, RedirectAttributes attr) {

        Optional<Producto> producto1 = productoRepository.findById(id);
        if (producto1.isPresent()) {
            Producto producto2 = producto1.get();
            List<Inventario> lista = inventarioRepository.findByProducto(producto2);
            if (lista.isEmpty()) {
                productoRepository.deleteById(producto2.getIdProducto());
                denominacionRepository.deleteById(producto2.getDenominacion().getIddenominacion());
                attr.addFlashAttribute("msg1", "Producto borrado exitósamente");
                return "redirect:/gestor/productos";
            } else {
                attr.addFlashAttribute("msg", "Producto en Inventario, No puede ser borrado");
                return "redirect:/gestor/productos";
            }

        } else {
            attr.addFlashAttribute("msg", "ID no válido");
            return "redirect:/gestor/productos";
        }

    }

    @GetMapping("gestorRegInventario")
    public String añadirEnInventario(@ModelAttribute("historial") Historial historial, Model model) {
        model.addAttribute("litaProductos", productoRepository.findAll());
        return "Gestor/G-AñadirEnInventario";
    }

    @PostMapping("guardarProductoEnInventario")
    public String guardarEnInventario(@ModelAttribute("historial") @Valid Historial historial, BindingResult bindingResult,
                                      RedirectAttributes attr, @RequestParam("archivo") MultipartFile file, Model model) {
        if (bindingResult.hasErrors()) {
            System.out.println("Tengo errores");
            model.addAttribute("litaProductos", productoRepository.findAll());
            return "Gestor/G-AñadirEnInventario";
        } else {
            if (file.isEmpty()) {
                model.addAttribute("msg", "Debe subir un archivo");
                model.addAttribute("litaProductos", productoRepository.findAll());
                return "Gestor/G-AñadirEnInventario";
            }

            String fileName = file.getOriginalFilename();

            if (fileName.contains("..")) {
                model.addAttribute("msg", "No se permiten '..' en el archivo ");
                model.addAttribute("litaProductos", productoRepository.findAll());
                return "Gestor/G-AñadirEnInventario";
            }

            try {
                historial.getInventario().setFoto(file.getBytes());
                historial.getInventario().setFotonombre(fileName);
                historial.getInventario().setFotocontenttype(file.getContentType());
                Optional<Producto> producto = productoRepository.findById(historial.getInventario().getProducto().getIdProducto());
                if (producto.isPresent()) {
                    Producto producto1 = producto.get();
                    historial.getInventario().setProducto(producto1);
                    historial.getInventario().setStock(historial.getCantidad());
                    inventarioRepository.save(historial.getInventario());
                    List<Inventario> inventarios = inventarioRepository.findAll();
                    historial.setInventario(inventarios.get(inventarios.size() - 1));
                    historialRepository.save(historial);
                    attr.addFlashAttribute("msg", "Agregado Exitosamente");
                    return "redirect:/gestor/gestorPrincipal";

                } else {
                    model.addAttribute("msg", "El producto seleccionado no existe");
                    model.addAttribute("litaProductos", productoRepository.findAll());
                    return "Gestor/G-AñadirEnInventario";
                }


            } catch (IOException e) {
                e.printStackTrace();
                model.addAttribute("msg", "Ocurrió un error al subir el archivo ");
                model.addAttribute("litaProductos", productoRepository.findAll());
                return "Gestor/G-AñadirEnInventario";
            }

        }

    }

    @GetMapping("/image/{id}")
    public ResponseEntity<byte[]> mostrarImagen(@PathVariable("id") int id) {
        Optional<Inventario> inventario = inventarioRepository.findById(id);
        if (inventario.isPresent()) {
            Inventario i = inventario.get();

            // para mandar un error si es que no se encuentra la imagen en la bd.
            // En el HTML automaticamente se muestra una imagen por defecto
            if (i.getFotocontenttype() == null || i.getFotocontenttype().isEmpty() || i.getFoto().length == 0 || i.getFoto() == null) {
                HttpHeaders httpHeaders = new HttpHeaders();
                return new ResponseEntity<>(null, httpHeaders, HttpStatus.NOT_FOUND);
            } // fin IF

            byte[] imagenComoBytes = i.getFoto();
            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.setContentType(MediaType.parseMediaType(i.getFotocontenttype()));
            return new ResponseEntity<>(imagenComoBytes, httpHeaders, HttpStatus.OK);

        } else {
            return null;
        }
    }

    // ----------------------- FIN CRUD INVENTARIO ---------------------------------

// ----------------------- INICIO CRUD COMUNIDAD ---------------------------------

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
        //  List<Comunidad> listaComunidad = comunidadRepository.buscarPorNombre(comunidad.getNombre(), comunidad.getCodigo());
        List<Comunidad> listaComunidad = comunidadRepository.buscarComunidad(comunidad.getNombre(), comunidad.getCodigo());
        if (bindingResult.hasErrors()) {
            model.addAttribute(comunidad);
            return "Gestor/G-RegistroComunidad";
        } else {

            // if ((comunidad.getIdComunidad() == 0) && (listaComunidad.size() == 0)) {
            if (comunidad.getIdComunidad() == 0 && listaComunidad.size() == 0) {
                comunidadRepository.save(comunidad);
                attr.addFlashAttribute("msg", "Comunidad creada exitosamente");
                return "redirect:/gestor/gestorListaComunidad";
                // } else if (comunidad.getIdComunidad() != 0) {
            } else if (comunidad.getIdComunidad() != 0 && listaComunidad.size() == 1) {
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

        //  List<Comunidad> listaComunidad = comunidadRepository.buscarPorNombre(searchField, searchField);
        List<Comunidad> listaComunidad = comunidadRepository.buscarComunidad(searchField, searchField);
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
                                  @RequestParam("idcomunidad") int idcomunidad,
                                  RedirectAttributes attr) {
        Optional<Comunidad> optComunidad = comunidadRepository.findById(idcomunidad);
        if (optComunidad.isPresent()) {
            // comunidadRepository.deleteById(idcomunidad);
            // attr.addFlashAttribute("msg", "Comunidad borrada exitosamente");
            try {
                comunidadRepository.deleteById(idcomunidad);
                attr.addFlashAttribute("msg", "Comunidad Eliminada");
            } catch (Exception e) {
                attr.addFlashAttribute("msgE", "Comunidad en inventario, no puede ser borrado");
            }
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
    public String RegistroCategoria(@ModelAttribute("categoria") Categoria categoria, Model model) {
        return "Gestor/G-EditCategoria";
    }


    @PostMapping("gestorGuardarCategoria")
    public String GuardaCategoria(@ModelAttribute("categoria") @Valid Categoria categoria, BindingResult bindingResult, Model model, RedirectAttributes attr) {
        List<Categoria> listaCategoria = categoriaRepository.buscarCategoria(categoria.getNombre(), categoria.getCodigo());
        List<Categoria> listaCategoriaPorNombre = categoriaRepository.buscarCategoriaPorNombre(categoria.getNombre());
        List<Categoria> listaCategoriaPorCodigo = categoriaRepository.buscarCategoriaPorNombre(categoria.getNombre());


        if (bindingResult.hasErrors()) {
            model.addAttribute(categoria);
            return "Gestor/G-EditCategoria";
        } else {

            if (categoria.getIdCategoria() == 0 && listaCategoria.size() == 0) {
                categoriaRepository.save(categoria);
                attr.addFlashAttribute("msg", "Categoria creada exitosamente");
                return "redirect:/gestor/gestorListaCategoria";
            } else if (categoria.getIdCategoria() != 0 && listaCategoria.size() == 1) {
                categoriaRepository.save(categoria);
                attr.addFlashAttribute("msg", "Categoria actualizada exitosamente");
                return "redirect:/gestor/gestorListaCategoria";
            } else {
                model.addAttribute(categoria);
                model.addAttribute("msgError", "Los datos ingresados ya existen");
                return "Gestor/G-EditCategoria";
            }

        }


    }


    @GetMapping("gestorEditCategoria")
    public String EditCategoria(@RequestParam("id") int id, @ModelAttribute("categoria") Categoria categoria, Model model) {

        Optional<Categoria> optCategoria = categoriaRepository.findById(id);

        if (optCategoria.isPresent()) {
            categoria = optCategoria.get();
            model.addAttribute("categoria", categoria);
            model.addAttribute("listaCategoria", categoriaRepository.findAll());
            return "Gestor/G-EditCategoria";
        } else {
            return "redirect:/gestor/gestorListaCategoria";
        }
    }


    @GetMapping("gestorEliminarCategoria")
    public String EliminarCategoria(@RequestParam("id") int id, RedirectAttributes attr) {

        Optional<Categoria> optCategoria = categoriaRepository.findById(id);
        if (optCategoria.isPresent()) {
            // categoriaRepository.deleteById(id);
            //  attr.addFlashAttribute("msg", "Categoría Eliminada");
            try {
                categoriaRepository.deleteById(id);
                attr.addFlashAttribute("msg", "Categoría Eliminada");
            } catch (Exception e) {
                attr.addFlashAttribute("msgE", "Categoría en inventario, no puede ser borrada");
            }
        }
        return "redirect:/gestor/gestorListaCategoria";
    }

// ----------------------- FIN CRUD CATEGORIA ---------------------------------


    // ----------------------- INICIO CRUD ARTESANOS ---------------------------------

    @GetMapping("gestorEditArtesano")
    public String EditArtesano(@RequestParam("idartesano") int idartesano, @ModelAttribute("artesano") Artesano artesano, Model model) {
        Optional<Artesano> artesanoPorID = artesanoRepository.findById(idartesano);
        if (artesanoPorID.isPresent()) {
            artesano = artesanoPorID.get();
            model.addAttribute("artesano", artesano);
            model.addAttribute("listaComunidad", comunidadRepository.findAll());
            return "Gestor/G-EditArtesano";
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
    public String registroArtesano(@ModelAttribute("artesano") Artesano artesano, Model model) {
        model.addAttribute("listaComunidad", comunidadRepository.findAll());
        return "Gestor/G-RegistroArtesano";
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
            attr.addFlashAttribute("msg", "Artesano Eliminado");
        } else {
            attr.addFlashAttribute("msge", "Artesano no ha podido eliminarse");
        }
        return "redirect:/gestor/gestorListaArtesano";
    }

    @PostMapping("gestorGuardarArtesano")
    public String guardarArtesano(@ModelAttribute("artesano") @Valid Artesano artesano, BindingResult bindingResult,
                                  RedirectAttributes attr,
                                  Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("listaComunidad", comunidadRepository.findAll());
            return "Gestor/G-RegistroArtesano";
        } else {


            //validacion codigo de  artesano (INICIALES)
            String aux1 = null;
            String aux2 = null;
            String aux3 = null;
            if (!artesano.getApellidomaterno().isEmpty()) { // codigos con apellido materno
                aux1 = artesano.getNombre().substring(0, 1) + artesano.getApellidopaterno().substring(0, 1) + artesano.getApellidomaterno().substring(0, 1);
                aux2 = artesano.getNombre().substring(0, 2) + artesano.getApellidopaterno().substring(0, 1) + artesano.getApellidomaterno().substring(0, 1);
                aux3 = artesano.getNombre().substring(0, 1) + artesano.getApellidopaterno().substring(0, 1) + artesano.getApellidomaterno().substring(0, 2);
            } else {// codigos sin apellido materno
                aux1 = artesano.getNombre().substring(0, 1) + artesano.getApellidopaterno().substring(0, 1);
                aux2 = artesano.getNombre().substring(0, 2) + artesano.getApellidopaterno().substring(0, 1);
            }
            //fin validacion codigo de artesano


            if (artesano.getIdArtesano() == null) {
                if (artesanoRepository.findByCodigo(artesano.getCodigo()).size() >= 1 ||  // en caso el codigo se repita o no tenga un codigo esperado
                        !(artesano.getCodigo().equalsIgnoreCase(aux1) || artesano.getCodigo().equalsIgnoreCase(aux2) || artesano.getCodigo().equalsIgnoreCase(aux3))) {
                    model.addAttribute("listaComunidad", comunidadRepository.findAll());
                    model.addAttribute("msgError", "Recuerde que el codigo debe ser las iniciales del artesano");
                    return "Gestor/G-RegistroArtesano";
                } else {
                    System.out.println("ARTESANO NULL ----------- 1");
                    Optional<Comunidad> comunidad = comunidadRepository.findById(artesano.getComunidad().getIdComunidad());
                    System.out.println(comunidad.get().getIdComunidad() + "ID COMUNIDAD ---------");
                    artesano.setComunidad(comunidad.get());
                    artesanoRepository.save(artesano);
                    attr.addFlashAttribute("msg", "Artesano creado exitosamente");
                    return "redirect:/gestor/gestorListaArtesano";
                }
            } else {
                Optional<Artesano> artesano2 = artesanoRepository.findById(artesano.getIdArtesano());
                if (artesano2.isPresent()) { // El ID ESTA BIEN
                    artesano.setComunidad(comunidadRepository.findById(artesano.getComunidad().getIdComunidad()).get());
                    artesanoRepository.save(artesano);
                    attr.addFlashAttribute("msg", "Artesano actualizado exitosamente");
                } else { // EL ID NO ESTA BIEN
                    attr.addFlashAttribute("msgE", "error en el ID del artesano");
                }
                return "redirect:/gestor/gestorListaArtesano";

            }




            /*if (artesanoRepository.findByCodigo(artesano.getCodigo()).size() >= 1 ||  // en caso el codigo se repita o no tenga un codigo esperado
                    !(artesano.getCodigo().equalsIgnoreCase(aux1) || artesano.getCodigo().equalsIgnoreCase(aux2))) {
                model.addAttribute("listaComunidad", comunidadRepository.findAll());
                attr.addFlashAttribute("msgError", "Recuerde que el codigo debe ser las iniciales del artesano");
                return "Gestor/G-RegistroArtesano";
            }


            if (artesano.getIdArtesano() == null) { // nuevo artesano
                System.out.println("ARTESANO NULL ----------- 1");
                Optional<Comunidad> comunidad = comunidadRepository.findById(artesano.getComunidad().getIdComunidad());
                System.out.println(comunidad.get().getIdComunidad() + "ID COMUNIDAD ---------");
                artesano.setComunidad(comunidad.get());
                artesanoRepository.save(artesano);
                attr.addFlashAttribute("msg", "Artesano creado exitosamente");
                return "redirect:/gestor/gestorListaArtesano";
            } else if (artesano.getIdArtesano() != 0) { // Editar Artesano
                Optional<Artesano> artesano2 = artesanoRepository.findById(artesano.getIdArtesano());
                if (artesano2.isPresent()) { // El ID ESTA BIEN
                    artesano.setComunidad(comunidadRepository.findById(artesano.getComunidad().getIdComunidad()).get());
                    artesanoRepository.save(artesano);
                    attr.addFlashAttribute("msg", "Artesano actualizado exitosamente");
                    return "redirect:/gestor/gestorListaArtesano";
                } else { // EL ID NO ESTA BIEN
                    attr.addFlashAttribute("msg", "error en el ID del artesano");
                    return "redirect:/gestor/gestorListaArtesano";
                }

            } else { // EL IDARTESANO ES IGUAL A 0
                System.out.println("ID ARTESANO ES 0 POR ALGUA RAZON");
                model.addAttribute(artesano);
                attr.addFlashAttribute("msgError", "Los datos ingresados ya existen, por favor modificarlo");
                return "Gestor/G-RegistroArtesano";
            }*/
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

    @GetMapping("borrarRechazoDeEnvio")
    public String borrarProductosRechazados(@RequestParam("id") int idRechazado, RedirectAttributes attr) {
        Optional<Estadoenviosede> obtenerEstado = estadoenviosedeRepository.findById(idRechazado);
        if (obtenerEstado.isPresent()) {
            estadoenviosedeRepository.deleteById(idRechazado);
            attr.addFlashAttribute("msg", "El producto rechazado ha sido eliminado exitosamente");
        }
        return "redirect:/gestor/gestorProductosRechazados";
    }

    @GetMapping("gestorEditarEnvio")
    public String editarEnvio(@RequestParam("id") int id, @ModelAttribute("estadoenviosede") Estadoenviosede estadoenviosede, Model model) {
        Optional<Estadoenviosede> estadoPorID = estadoenviosedeRepository.findById(id);
        if (estadoPorID.isPresent()) {
            estadoenviosede = estadoPorID.get();
            model.addAttribute("estadoenviosede", estadoenviosede);
            List<Inventario> listaInventario = inventarioRepository.findAll();
            List<Sede> listaSede = sedeRepository.findAll();
            model.addAttribute("listaInventario", listaInventario);
            model.addAttribute("listaSede", listaSede);
            //todo hacer que de alguna manera se borre el estadoenviosede con estado "rechazado"
            return "Gestor/G-GestionEnvios";
        } else {
            return "redirect:/gestor/gestorProductosRechazados";
        }
    }


    // -------------------------- FIN CRUD PRODUCTO ---------------------------------

    // --------------------------  INICIO CRUD ENVIOS ------------------------------
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
            return "Gestor/G-GestionEnvios";


        } else {

            int invkey = estadoenviosede.getInventariosede().getInventario().getIdInventario();
            int sedkey = estadoenviosede.getInventariosede().getSede().getIdsede();
            if (sedeRepository.findById(sedkey).isPresent() && inventarioRepository.findById(invkey).isPresent()) {
                Optional<Sede> sede1 = sedeRepository.findById(sedkey);
                Optional<Inventario> inventario1 = inventarioRepository.findById(invkey);
                List<Inventariosede> inventariosede1 = inventariosedeRepository.findByInventarioAndSede(inventario1.get(), sede1.get());
                if ((inventario1.get().getStock() - estadoenviosede.getCantidad()) >= 0) {
                    //conseguir inventariosede
                    if (!inventariosede1.isEmpty()) {// si ya hay un registro se usa
                        estadoenviosede.setInventariosede(inventariosede1.get(0));
                    } else {// sino se crea uno
                        Inventariosede inventariosede2 = new Inventariosede();
                        inventariosede2.setInventario(inventario1.get());
                        inventariosede2.setSede(sede1.get());
                        inventariosede2.setStock(0);
                        inventariosedeRepository.save(inventariosede2);
                        estadoenviosede.setInventariosede(inventariosedeRepository.findByInventarioAndSede(inventario1.get(), sede1.get()).get(0));
                    }
                    //fin conseguir invetariosede
                    estadoenviosede.setEstado("En camino");
                    if (estadoenviosede.getIdenviosede() != 0) {// este es un reenvio (creo)
                        if (estadoenviosedeRepository.findById(estadoenviosede.getIdenviosede()).get().getEstado().equalsIgnoreCase("rechazado")) {
                            //es un producto rechazado
                            estadoenviosedeRepository.deleteById(estadoenviosede.getIdenviosede());
                            estadoenviosedeRepository.save(estadoenviosede);
                        } else {
                            //fue hackerman
                            List<Inventario> listaInventario = inventarioRepository.findAll();
                            List<Sede> listaSede = sedeRepository.findAll();
                            model.addAttribute("listaInventario", listaInventario);
                            model.addAttribute("listaSede", listaSede);
                            model.addAttribute("msg", "Por favor no editar el HTML con F12 :>");
                            return "Gestor/G-GestionEnvios";
                        }
                        //todo logica de borrar el anterior y aquello ??????
                    } else {//este no es un reenvio
                        estadoenviosedeRepository.save(estadoenviosede);
                    }

                    int cantidadrestada = estadoenviosede.getInventariosede().getInventario().getStock() - estadoenviosede.getCantidad();
                    estadoenviosede.getInventariosede().getInventario().setStock(cantidadrestada);
                } else { // cantidad restada menor a 0
                    List<Inventario> listaInventario = inventarioRepository.findAll();
                    List<Sede> listaSede = sedeRepository.findAll();
                    model.addAttribute("listaInventario", listaInventario);
                    model.addAttribute("listaSede", listaSede);
                    model.addAttribute("msg", "Se esta tratando de enviar mas de lo que se tiene");
                    return "Gestor/G-GestionEnvios";
                }
                System.out.println("El stock nuevo del inventario es:" + estadoenviosede.getInventariosede().getInventario().getStock());
                inventarioRepository.save(estadoenviosede.getInventariosede().getInventario());
                attr.addFlashAttribute("msg", "Envio guardado correctamente");
                return "redirect:/gestor/gestorProductosEnviados";
            }
            // aqui solo se entra si alguien edita el HTML con F12
            model.addAttribute("msg", "Por favor no editar el HTML :)");
            List<Sede> listaSede = sedeRepository.findAll();
            model.addAttribute("listaSede", listaSede);
            List<Inventario> listaInventario = inventarioRepository.findAll();
            model.addAttribute("listaInventario", listaInventario);
            return "Gestor/G-GestionEnvios";

        }

    }


    // -------------------------- FIN CRUD ENVIOS ------------------------------


    //--------------------CRUD VENTAS---------------
    @GetMapping("/nuevaVenta")
    public String nuevaVenta(@ModelAttribute("venta") Venta venta, Model model) {
        List<Inventario> listaInventario = inventarioRepository.findAll();
        model.addAttribute("listaInventario", listaInventario);
        return "Gestor/G-NuevaVenta";
    }


    @GetMapping("gestionVentas")
    public String gestionDeVentas(@ModelAttribute("venta") Venta venta, Model model) {
        model.addAttribute("listaVentas", ventaRepository.findAll());
        return "Gestor/G-GestionVentas";
    }


    @PostMapping("/guardarVenta")
    public String guardarVenta(@ModelAttribute("venta") @Valid Venta venta, BindingResult bindingResult, Model model,
                               HttpSession session, RedirectAttributes attr) {

        if (bindingResult.hasErrors()) {
            List<Inventario> listaInventario = inventarioRepository.findAll();
            model.addAttribute("listaInventario", listaInventario);
            return "Gestor/G-NuevaVenta";
        } else {


            int invkey = venta.getInventario().getIdInventario();
            if (inventarioRepository.findById(invkey).isPresent()) {
                Optional<Inventario> inventario1 = inventarioRepository.findById(invkey);
                if ((inventario1.get().getStock() - venta.getCantidad()) >= 0) {
                    inventario1.get().setStock(inventario1.get().getStock() - venta.getCantidad());
                    Usuarios u = (Usuarios) session.getAttribute("user");
                    venta.setInventario(inventario1.get());
                    venta.setUsuarios(u);
                    ventaRepository.save(venta);
                    attr.addFlashAttribute("msg", "Venta añadida exitosamente");
                    return "redirect:/gestor/gestionVentas";
                } else {
                    List<Inventario> listaInventario = inventarioRepository.findAll();
                    model.addAttribute("listaInventario", listaInventario);
                    attr.addFlashAttribute("msg", "Se esta tratando de vender mas de lo que se tiene");
                    return "redirect:/gestor/nuevaVenta";
                }

            } else {
                return "Gestor/G-NuevaVenta";

            }

        }
    }

    @PostMapping("/buscarVenta")
    public String buscarVenta(@RequestParam("searchField") String searchField,
                              Model model) {

        List<Venta> listaVenta = ventaRepository.buscarPorNombre(searchField);
        model.addAttribute("listaVentas", listaVenta);
        return "gestor/G-GestionVentas";
    }

    // Java program generate a random AlphaNumeric String
    // using Math.random() method
    // function to generate a random string of length n
    public String getAlphaNumericString(int n) {

        // chose a Character random from this String
        String AlphaNumericString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
                + "0123456789"
                + "abcdefghijklmnopqrstuvxyz";

        // create StringBuffer size of AlphaNumericString
        StringBuilder sb = new StringBuilder(n);

        for (int i = 0; i < n; i++) {

            // generate a random number between
            // 0 to AlphaNumericString variable length
            int index
                    = (int) (AlphaNumericString.length()
                    * Math.random());

            // add Character one by one in end of sb
            sb.append(AlphaNumericString
                    .charAt(index));
        }

        return sb.toString();
    }

///////////// REPORTES DE EXCEL ///////////////////

    //EXCEL !!
    @PostMapping("crearExcelPorCliente")
    public String crearExcelCliente(@RequestParam("filtrado") int val, @RequestParam("estandar") String cliente, @RequestParam("mes") String mes, @RequestParam("año") String año, HttpServletRequest request, HttpServletResponse response, RedirectAttributes attr) {
        if (val != 1) {
            attr.addFlashAttribute("msg", "Debe ingresar un parámetro para el filtrado");
            return "redirect:/gestor/gestorReporteVentas";
        } else {
            if (mes.equals("todo")) {
                List<ReporteConCamposOriginales> ventaXClienteAnual = ventasService.getVentasPorClienteAnual(año, cliente);
                String titulo = "Ventas anuales realizadas al cliente " + cliente;
                boolean isFlag = ventasService.createExcelXCliente(ventaXClienteAnual, titulo, mes, context, request, response);
                if (isFlag) {
                    String fullpath = request.getServletContext().getRealPath("/resources/reports/" + "ventas_por_cliente" + ".xls");
                    filedownload(fullpath, response, "ventas_clientes_anual.xls");
                }
            } else if (mes.equals("trimestre")) {
                List<ReporteConCamposOriginales> ventaXClienteTrimestral = ventasService.getVentasPorClienteTrimestral(año, cliente);
                String titulo = "Ventas trimestrales realizadas al cliente " + cliente;
                boolean isFlag = ventasService.createExcelXCliente(ventaXClienteTrimestral, titulo, mes, context, request, response);
                if (isFlag) {
                    String fullpath = request.getServletContext().getRealPath("/resources/reports/" + "ventas_por_cliente" + ".xls");
                    filedownload(fullpath, response, "ventas_clientes_trimestral.xls");
                }
            } else {
                List<ReporteConCamposOriginales> ventaXClienteMensual = ventasService.getVentasPorCliente(mes, año, cliente);
                String titulo = "Ventas mensuales realizadas al cliente " + cliente;
                boolean isFlag = ventasService.createExcelXCliente(ventaXClienteMensual, titulo, mes, context, request, response);
                if (isFlag) {
                    String fullpath = request.getServletContext().getRealPath("/resources/reports/" + "ventas_por_cliente" + ".xls");
                    filedownload(fullpath, response, "ventas_clientes_mensual.xls");
                }
            }
        }
        return cliente;
    }

    @PostMapping("crearExcelPorSede")
    public String crearExcelPorSede(@RequestParam("filtrado") int val, @RequestParam("estandar") String idsede, @RequestParam("mes") String mes, @RequestParam("año") String año, HttpServletRequest request, HttpServletResponse response, RedirectAttributes attr) {
        if (val != 2) {
            attr.addFlashAttribute("msg", "Debe ingresar un parámetro para el filtrado");
            return "redirect:/gestor/gestorReporteVentas";
        } else {
            if (mes.equals("todo")) {
                List<ReporteConCamposOriginales> ventaXSedeAnual = ventasService.getVentasPorSedeAnual(año, idsede);
                String titulo = "Ventas anuales realizadas por la sede " + idsede;
                boolean isFlag = ventasService.createExcelXCliente(ventaXSedeAnual, titulo, mes, context, request, response);
                if (isFlag) {
                    String fullpath = request.getServletContext().getRealPath("/resources/reports/" + "ventas_por_cliente" + ".xls");
                    filedownload(fullpath, response, "ventas_sedes_anual.xls");
                }
            } else if (mes.equals("trimestre")) {
                List<ReporteConCamposOriginales> ventaXSedeTrimestral = ventasService.getVentasPorSedeTrimestral(año, idsede);
                String titulo = "Ventas trimestrales realizadas al cliente " + idsede;
                boolean isFlag = ventasService.createExcelXCliente(ventaXSedeTrimestral, titulo, mes, context, request, response);
                if (isFlag) {
                    String fullpath = request.getServletContext().getRealPath("/resources/reports/" + "ventas_por_cliente" + ".xls");
                    filedownload(fullpath, response, "ventas_sedes_trimestral.xls");
                }
            } else {
                List<ReporteConCamposOriginales> ventaXSedeMensual = ventasService.getVentasPorSede(mes, año, idsede);
                String titulo = "Ventas mensuales realizadas al cliente " + idsede;
                boolean isFlag = ventasService.createExcelXCliente(ventaXSedeMensual, titulo, mes, context, request, response);
                if (isFlag) {
                    String fullpath = request.getServletContext().getRealPath("/resources/reports/" + "ventas_por_cliente" + ".xls");
                    filedownload(fullpath, response, "ventas_sedes_mensual.xls");
                }
            }
        }
        return idsede;
    }

    @PostMapping("crearExcelPorArticulo")
    public String crearExcelPorArticulo(@RequestParam("filtrado") int val, @RequestParam("estandar") String articulo, @RequestParam("mes") String mes, @RequestParam("año") String año, HttpServletRequest request, HttpServletResponse response, RedirectAttributes attr) {
        if (val != 3) {
            attr.addFlashAttribute("msg", "Debe ingresar un parámetro para el filtrado");
            return "redirect:/gestor/gestorReporteVentas";
        } else {
            if (mes.equals("todo")) {
                List<ReporteConCamposOriginales> ventaXArticuloAnual = ventasService.getVentasPorArticuloAnual(año, articulo);
                String titulo = "Ventas anuales del artículo " + articulo;
                boolean isFlag = ventasService.createExcelXCliente(ventaXArticuloAnual, titulo, mes, context, request, response);
                if (isFlag) {
                    String fullpath = request.getServletContext().getRealPath("/resources/reports/" + "ventas_por_cliente" + ".xls");
                    filedownload(fullpath, response, "ventas_articulo_anual.xls");
                }
            } else if (mes.equals("trimestre")) {
                List<ReporteConCamposOriginales> ventaXArticuloTrimestral = ventasService.getVentasPorArticuloTrimestral(año, articulo);
                String titulo = "Ventas trimestrales del artículo " + articulo;
                boolean isFlag = ventasService.createExcelXCliente(ventaXArticuloTrimestral, titulo, mes, context, request, response);
                if (isFlag) {
                    String fullpath = request.getServletContext().getRealPath("/resources/reports/" + "ventas_por_cliente" + ".xls");
                    filedownload(fullpath, response, "ventas_articulo_trimestral.xls");
                }
            } else {
                List<ReporteConCamposOriginales> ventaXArticuloMensual = ventasService.getVentasPorArticuloMensual(mes, año, articulo);
                String titulo = "Ventas mensuales del artículo " + articulo;
                boolean isFlag = ventasService.createExcelXCliente(ventaXArticuloMensual, titulo, mes, context, request, response);
                if (isFlag) {
                    String fullpath = request.getServletContext().getRealPath("/resources/reports/" + "ventas_por_cliente" + ".xls");
                    filedownload(fullpath, response, "ventas_articulo_mensual.xls");
                }
            }
        }
        return articulo;
    }

    @PostMapping("crearExcelPorComunidad")
    public String crearExcelPorComunidad(@RequestParam("filtrado") int val, @RequestParam("estandar") String comunidad, @RequestParam("mes") String mes, @RequestParam("año") String año, HttpServletRequest request, HttpServletResponse response, RedirectAttributes attr) {
        if (val != 4) {
            attr.addFlashAttribute("msg", "Debe ingresar un parámetro para el filtrado");
            return "redirect:/gestor/gestorReporteVentas";
        } else {
            if (mes.equals("todo")) {
                List<ReporteConCamposOriginales> ventaXComunidadAnual = ventasService.getVentasPorComunidadAnual(año, comunidad);
                String titulo = "Ventas anuales de los productos de la comunidad " + comunidad;
                boolean isFlag = ventasService.createExcelXCliente(ventaXComunidadAnual, titulo, mes, context, request, response);
                if (isFlag) {
                    String fullpath = request.getServletContext().getRealPath("/resources/reports/" + "ventas_por_cliente" + ".xls");
                    filedownload(fullpath, response, "ventas_comunidad_anual.xls");
                }
            } else if (mes.equals("trimestre")) {
                List<ReporteConCamposOriginales> ventaXComunidadTrimestral = ventasService.getVentasPorComunidadTrimestral(año, comunidad);
                String titulo = "Ventas trimestrales de los productos de la comunidad " + comunidad;
                boolean isFlag = ventasService.createExcelXCliente(ventaXComunidadTrimestral, titulo, mes, context, request, response);
                if (isFlag) {
                    String fullpath = request.getServletContext().getRealPath("/resources/reports/" + "ventas_por_cliente" + ".xls");
                    filedownload(fullpath, response, "ventas_comunidad_trimestral.xls");
                }
            } else {
                List<ReporteConCamposOriginales> ventaXComunidadMensual = ventasService.getVentasPorComunidadMensual(mes, año, comunidad);
                String titulo = "Ventas mensuales de los productos de la comunidad " + comunidad;
                boolean isFlag = ventasService.createExcelXCliente(ventaXComunidadMensual, titulo, mes, context, request, response);
                if (isFlag) {
                    String fullpath = request.getServletContext().getRealPath("/resources/reports/" + "ventas_por_cliente" + ".xls");
                    filedownload(fullpath, response, "ventas_comunidad_mensual.xls");
                }
            }
        }
        return comunidad;
    }

    @PostMapping("crearExcelTotal")
    public void crearExcelTotal(@RequestParam("mes4") String mes, @RequestParam("año4") String año, HttpServletRequest request, HttpServletResponse response) {
        if (mes.equals("todo")) {
            List<ReporteConCamposOriginales> ventaXAnual = ventasService.getVentaAnual(año);
            String titulo = "Ventas totales de Mosqoy del año " + año;
            boolean isFlag = ventasService.createExcelXCliente(ventaXAnual, titulo, mes, context, request, response);
            if (isFlag) {
                String fullpath = request.getServletContext().getRealPath("/resources/reports/" + "ventas_por_cliente" + ".xls");
                filedownload(fullpath, response, "ventas_total_anual.xls");
            }
        } else if (mes.equals("trimestre")) {
            List<ReporteConCamposOriginales> ventaXTrimestral = ventasService.getVentaTrimestral(año);
            String titulo = "Ventas trimestrales de Mosqoy del año " + año;
            boolean isFlag = ventasService.createExcelXCliente(ventaXTrimestral, titulo, mes, context, request, response);
            if (isFlag) {
                String fullpath = request.getServletContext().getRealPath("/resources/reports/" + "ventas_por_cliente" + ".xls");
                filedownload(fullpath, response, "ventas_total_trimestral.xls");
            }
        } else {
            List<ReporteConCamposOriginales> ventaXMensual = ventasService.getVentaMensual(mes, año);
            String titulo = "Ventas Mosqoy de " + mes + " del año " + año;
            boolean isFlag = ventasService.createExcelXCliente(ventaXMensual, titulo, mes, context, request, response);
            if (isFlag) {
                String fullpath = request.getServletContext().getRealPath("/resources/reports/" + "ventas_por_cliente" + ".xls");
                filedownload(fullpath, response, "ventas_total_mensual.xls");
            }
        }
    }

    private void filedownload(String fullpath, HttpServletResponse response, String filename) {
        File file = new File(fullpath);
        final int BUFFER_SIZE = 4096;
        if (file.exists()) {
            try {
                FileInputStream inputStream = new FileInputStream(file);
                String mimeType = context.getMimeType(fullpath);
                response.setContentType(mimeType);
                response.setHeader("content-disposition", "attachment; filename=" + filename);
                OutputStream outputStream = response.getOutputStream();
                byte[] buffer = new byte[BUFFER_SIZE];
                int bytesRead = -1;
                while ((bytesRead = inputStream.read(buffer)) != -1) {
                    outputStream.write(buffer, 0, bytesRead);
                }
                inputStream.close();
                outputStream.close();
                file.delete();

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    @GetMapping("consignacionesProxVencer")
    public String consignacionesProximasAVencer(@ModelAttribute("inventario") Inventario inventario, Model model) {
        model.addAttribute("lista", inventarioRepository.findByEstado("Proxima"));
        return "Gestor/G-ConsignacionesProxAVencer";
    }

    @GetMapping("consignacionesVencidas")
    public String consignacionesVencidas(@ModelAttribute("inventario") Inventario inventario, Model model) {
        model.addAttribute("lista", inventarioRepository.findByEstado("Vencida"));
        return "Gestor/G-ConsignacionesVencidas";
    }

    @GetMapping("prodDevueltos")
    public String prodDevueltos(@ModelAttribute("inventario") Inventario inventario, Model model) {
        model.addAttribute("lista", inventarioRepository.findByEstado("Devuelto"));
        return "Gestor/G-ProdDevueltos";
    }

    @GetMapping("devolverProducto1")
    public String devolverProducto1(@RequestParam("id") int id, Model model) {
        Date date = new Date();
        Optional<Inventario> inventario1 = inventarioRepository.findById(id);
        if (inventario1.isPresent()) {
            Inventario inventario = inventario1.get();
            inventario.setIdInventario(id);
            inventario.setEstado("Devuelto");
            inventario.setFechadevolucion(date);
            inventarioRepository.save(inventario);
            model.addAttribute("lista", inventarioRepository.findByEstado("Proxima"));
            model.addAttribute("msg", "Producto Devuelto exitosamente");
            return "Gestor/G-ConsignacionesProxAVencer";
        } else {
            return "redirect:/gestor/consignacionesProxVencer";
        }

    }


    @GetMapping("devolverProducto2")
    public String devolverProducto2(@RequestParam("id") int id, Model model) {
        Date date = new Date();
        Optional<Inventario> inventario1 = inventarioRepository.findById(id);
        if (inventario1.isPresent()) {
            Inventario inventario = inventario1.get();
            inventario.setIdInventario(id);
            inventario.setEstado("Devuelto");
            inventario.setFechadevolucion(date);
            inventarioRepository.save(inventario);
            model.addAttribute("lista", inventarioRepository.findByEstado("Vencida"));
            model.addAttribute("msg", "Producto Devuelto exitosamente");
            return "Gestor/G-ConsignacionesVencidas";
        } else {
            return "redirect:/gestor/ConsignacionesVencidas";
        }

    }


    @ExceptionHandler(Exception.class)
    public String ExceptionHandlerGestor(Exception e,RedirectAttributes attr ){
        attr.addFlashAttribute("msgError", "Ocurrio un error, no se completo el proceso");
        System.out.println("!!!!! \n \n OCURRIO EL SIGUIENTE ERROR: \n  " + e.getMessage() + " \n \n !!!!!!!");
        System.out.println("!!!!! \n \n OCURRIO EL SIGUIENTE ERROR: \n  " + e.getCause().getMessage() + " \n \n !!!!!!!");
        return "redirect:/gestor/gestorPrincipal";


    }


    // CRONES TU TERROR!!!!!


    // SE EJECUTARÁ CADA PRIMERO DE CADA MES A LAS 2 AM ,  EN LA BASE DE DATOS SE REALIZARÁN LOS CAMBIOS DE ESTADOS EL MISMO DÍA PERO UNA HORA ANTES (1 AM)--OJO!!!!!
    //@Scheduled(cron = "0 0/3 * * * ?", zone = "GMT-5")
    @Scheduled(cron = "0 0 2 1 * ?", zone = "GMT-5")
    public void mensajeMensualDeAlertaDeVencimientoDeProductosParaLosGestores() throws MessagingException {

        List<String> listaCorreosGestor = usuarioRepository.obtenerCorreosGestorActivos();
        List<String> codigosPorVencer = productoRepository.productoPorEstado("Proxima");
        if(codigosPorVencer.size() >0){
            if (listaCorreosGestor.size()>=1){

                for (String correo:listaCorreosGestor) {
                    Email email = new Email();
                    email.emailAlertaConsignacionGestor(correo);
                    //System.out.println("Se envió");
                }
            } else {System.out.println("No hay productos próximos a vencer");}

        }

    }


    // SE EJECUTARÁ TODOS LOS DIAS A LAS 00:00 HORAS ,  SERA UNA ALERTA QUE ENVIARÁ CORREO A GESTORES INDICANDO QUE PRODUCTOS EN UNA SEMANA, ES INDIFERENTE DEL ESTADO!!!!!
    @Scheduled(cron = "0 0 0 * * *", zone = "GMT-5")
    public void mensajeDiarioDeProductosAVencerEnUnaSemana() throws MessagingException {

        List<String> listaCorreosGestor = usuarioRepository.obtenerCorreosGestorActivos();
        List<String> productoSemanaVencer = productoRepository.productoAunaSemanaDeVencer();
        if(productoSemanaVencer.size() >0){
            if (listaCorreosGestor.size()>=1){

                for (String correo:listaCorreosGestor) {

                    Email email = new Email();
                    email.emailAlertaConsignacionParaVender(correo,productoSemanaVencer);
                    //System.out.println("Se envió");
                }
            } else {System.out.println("No hay productos a vencer en la semana");}

        }

        }

}


