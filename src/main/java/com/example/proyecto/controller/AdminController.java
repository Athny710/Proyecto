package com.example.proyecto.controller;


import com.example.proyecto.dto.inventarioStockTotal;


import com.example.proyecto.entity.*;
import com.example.proyecto.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.mail.MessagingException;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.net.UnknownServiceException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    UsuarioRepository usuarioRepository;
    @Autowired
    InventarioRepository inventarioRepository;
    @Autowired
    ComunidadRepository comunidadRepository;
    @Autowired
    ArtesanoRepository artesanoRepository;
    @Autowired
    CategoriaRepository categoriaRepository;
    @Autowired
    InventariosedeRepository inventariosedeRepository;

    //--------------------------Inventario
    @GetMapping(value = {"", "principal"})
    public String principalAdmin(Model model) {
        model.addAttribute("listaComunidades", comunidadRepository.findAll());
        model.addAttribute("listaArtesanos", artesanoRepository.findAll());
        model.addAttribute("listaCategoria", categoriaRepository.findAll());

        //Nueva lista donde se guardan los productos con stock mayor a 0. Se envía al html
        List<Inventario> listaMayor0 = new ArrayList<>();
        //listaInventarioStockTotal es la lista del query. Devuelve stock total y el id de inventario
        for (inventarioStockTotal i: inventarioRepository.listaInventarioStockTotal()) {
            //Si el stock es mayor a cero se obtiene el producto y se guarda en la nueva lista
            if (i.getStockTotal()!=0){
                Inventario inv = new Inventario();
                Optional<Inventario> opt = inventarioRepository.findById(i.getIdInvent());
                inv.setIdInventario(opt.get().getIdInventario());
                inv.setStock(i.getStockTotal());
                inv.setComentario(opt.get().getComentario());
                inv.setColor(opt.get().getColor());
                inv.setPreciomosqoy(opt.get().getPreciomosqoy());
                inv.setProducto(opt.get().getProducto());
                inv.setEstado(opt.get().getEstado());
                inv.setFechadevolucion(opt.get().getFechadevolucion());

                listaMayor0.add(inv);
            }
        }
        model.addAttribute("inventario", listaMayor0);
        return "Administrador/A-PagPrincipal";
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
            return "Administrador/A-PagPrincipal";
        } else if (idComu == 0 && tipo.equalsIgnoreCase("todo") && idArt == 0 && idCate != 0) {
            List<Inventario> Lista2 = inventarioRepository.listarPorCategoria(idCate);
            model.addAttribute("listaComunidades", comunidadRepository.findAll());
            model.addAttribute("listaArtesanos", artesanoRepository.findAll());
            model.addAttribute("listaCategoria", categoriaRepository.findAll());
            model.addAttribute("inventario", Lista2);
            return "Administrador/A-PagPrincipal";
        } else if (idComu == 0 && !tipo.equalsIgnoreCase("todo") && idArt == 0 && idCate == 0) {
            List<Inventario> Lista3 = inventarioRepository.listarPorAdquisicion(tipo);
            model.addAttribute("listaComunidades", comunidadRepository.findAll());
            model.addAttribute("listaArtesanos", artesanoRepository.findAll());
            model.addAttribute("listaCategoria", categoriaRepository.findAll());
            model.addAttribute("inventario", Lista3);
            return "Administrador/A-PagPrincipal";
        } else if (idComu == 0 && tipo.equalsIgnoreCase("consignado") && idArt != 0 && idCate == 0) {
            List<Inventario> Lista4 = inventarioRepository.listarPorArtesanoConConsigna(tipo, idArt);
            model.addAttribute("listaComunidades", comunidadRepository.findAll());
            model.addAttribute("listaArtesanos", artesanoRepository.findAll());
            model.addAttribute("listaCategoria", categoriaRepository.findAll());
            model.addAttribute("inventario", Lista4);
            return "Administrador/A-PagPrincipal";
        }else if (idComu != 0 && tipo.equalsIgnoreCase("todo") && idArt == 0 && idCate != 0){
            List<Inventario> Lista5 = inventarioRepository.listarPorCategoriaYComunidad(idCate, idComu);
            model.addAttribute("listaComunidades", comunidadRepository.findAll());
            model.addAttribute("listaArtesanos", artesanoRepository.findAll());
            model.addAttribute("listaCategoria", categoriaRepository.findAll());
            model.addAttribute("inventario", Lista5);
            return "Administrador/A-PagPrincipal";
        } else if (idComu != 0 && !tipo.equalsIgnoreCase("todo") && idArt == 0 && idCate == 0){
            List<Inventario> Lista6 = inventarioRepository.listarPorComunidadYModalidad( idComu, tipo);
            model.addAttribute("listaComunidades", comunidadRepository.findAll());
            model.addAttribute("listaArtesanos", artesanoRepository.findAll());
            model.addAttribute("listaCategoria", categoriaRepository.findAll());
            model.addAttribute("inventario", Lista6);
            return "Administrador/A-PagPrincipal";
        }else if (idComu != 0 && tipo.equalsIgnoreCase("consignado") && idArt != 0 && idCate == 0){
            List<Inventario> Lista6 = inventarioRepository.listarPorComunidadConsignadoYArtesano( idComu, idArt);
            model.addAttribute("listaComunidades", comunidadRepository.findAll());
            model.addAttribute("listaArtesanos", artesanoRepository.findAll());
            model.addAttribute("listaCategoria", categoriaRepository.findAll());
            model.addAttribute("inventario", Lista6);
            return "Administrador/A-PagPrincipal";
        }else if (idComu == 0 && tipo.equalsIgnoreCase("consignado") && idArt != 0 && idCate != 0){
            List<Inventario> Lista7 = inventarioRepository.listarPorCategoriaConsignadoYArtesano( idCate, idArt);
            model.addAttribute("listaComunidades", comunidadRepository.findAll());
            model.addAttribute("listaArtesanos", artesanoRepository.findAll());
            model.addAttribute("listaCategoria", categoriaRepository.findAll());
            model.addAttribute("inventario", Lista7);
            return "Administrador/A-PagPrincipal";
        } else if (idComu == 0 && !tipo.equalsIgnoreCase("todo") && idArt == 0 && idCate != 0){
            List<Inventario> Lista8 = inventarioRepository.listarPorCategoriaYModalidad( idCate, tipo);
            model.addAttribute("listaComunidades", comunidadRepository.findAll());
            model.addAttribute("listaArtesanos", artesanoRepository.findAll());
            model.addAttribute("listaCategoria", categoriaRepository.findAll());
            model.addAttribute("inventario", Lista8);
            return "Administrador/A-PagPrincipal";
        }else if (idComu != 0 && !tipo.equalsIgnoreCase("todo") && idArt == 0 && idCate != 0){
            List<Inventario> Lista9 = inventarioRepository.listarPorCategoriaYComunidadYModalidad( idCate, tipo, idComu);
            model.addAttribute("listaComunidades", comunidadRepository.findAll());
            model.addAttribute("listaArtesanos", artesanoRepository.findAll());
            model.addAttribute("listaCategoria", categoriaRepository.findAll());
            model.addAttribute("inventario", Lista9);
            return "Administrador/A-PagPrincipal";
        }else if (idComu != 0 && tipo.equalsIgnoreCase("consignado") && idArt != 0 && idCate != 0){
            List<Inventario> Lista10 = inventarioRepository.listarPorCategoriaComunidadConsignadoYArtesano( idCate, idComu, idArt);
            model.addAttribute("listaComunidades", comunidadRepository.findAll());
            model.addAttribute("listaArtesanos", artesanoRepository.findAll());
            model.addAttribute("listaCategoria", categoriaRepository.findAll());
            model.addAttribute("inventario", Lista10);
            return "Administrador/A-PagPrincipal";
        } else{
            return "redirect:/admin";
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

    @GetMapping("detalles")
    public String detalleInventario(@RequestParam("id") int id, Model model) {
        Optional<Inventario> opt = inventarioRepository.findById(id);
        if (opt.isPresent()) {
            Inventario inventa = opt.get();


            // no queria crear un DTO asi que ahora en idSede le guardare el Stock de la sede
            List<Inventariosede> listaInvSede = inventariosedeRepository.findByInventario(inventa);
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






            model.addAttribute("d", inventa);
            return "Administrador/A-DetallesInventario";
        } else {
            return "redirect:/admin";
        }
    }

    //--------------------------Perfil
    @GetMapping("editarInfo")
    public String editarInfo(@ModelAttribute("perfil") Perfil perfil, HttpSession session) {
        Usuarios u = (Usuarios) session.getAttribute("user");
        perfil.setCorreo(u.getCorreo());
        perfil.setTelefono(u.getTelefono());
        return "Administrador/A-Perfil";
    }

    @PostMapping("guardarPerfil")
    public String guardarInfo(@ModelAttribute("perfil") @Valid Perfil perfil,
                              BindingResult bindingResult,
                              RedirectAttributes attr, HttpSession session, Model model) {

        Usuarios usuarioLog = (Usuarios) session.getAttribute("user");

        if (bindingResult.hasErrors()) {
            return "Administrador/A-Perfil";
        } else {
            if (perfil.getCorreo().equals(usuarioLog.getCorreo())) {
                attr.addFlashAttribute("msg", "Información personal editada con éxito");
                usuarioLog.setCorreo(perfil.getCorreo());
                usuarioLog.setTelefono(perfil.getTelefono());
                usuarioRepository.save(usuarioLog);
                session.setAttribute("user", usuarioLog);
                return "redirect:/admin/editarInfo";
            } else {
                Usuarios us = usuarioRepository.findByCorreo(perfil.getCorreo());
                if (us != null && us.getActivo() == 1) {
                    System.out.println(usuarioLog.getCorreo());
                    model.addAttribute("msgC", "Este correo ya está registrado");
                    return "Administrador/A-Perfil";
                } else {
                    attr.addFlashAttribute("msg", "Información personal editada con éxito");
                    usuarioLog.setCorreo(perfil.getCorreo());
                    usuarioLog.setTelefono(perfil.getTelefono());
                    usuarioRepository.save(usuarioLog);
                    session.setAttribute("user", usuarioLog);
                    return "redirect:/admin/editarInfo";
                }
            }
        }
    }


    //--------------------------Gestores
    @GetMapping("listaGestores")
    public String listaGestores(Model model) {
        model.addAttribute("listaGestores", usuarioRepository.findByTipoAndActivo("gestor", 1));
        return "Administrador/A-ListaGestores";
    }

    @GetMapping("nuevoGestor")
    public String nuevoGestor(@ModelAttribute("usu") Usuarios usuario) {
        return "Administrador/A-NuevoGestor";
    }

    @PostMapping("guardarGestor")
    public String guardarGestor(@ModelAttribute("usu") @Valid Usuarios usuarios,
                                BindingResult bindingResult, Model model,
                                RedirectAttributes attr) throws MessagingException {

        if (bindingResult.hasErrors()) {
            return "Administrador/A-NuevoGestor";
        } else {
            usuarios.setCorreo(usuarios.getCorreo().toLowerCase());

            if (usuarioRepository.findByCorreo(usuarios.getCorreo()) == null) {
                if (usuarios.getCorreo().matches("^[A-Za-z0-9\\._-]+@.+\\..+$")) {
                    if (usuarioRepository.findByTelefono(usuarios.getTelefono()) == null) {
                        if (usuarioRepository.findByNombreAndApellido(usuarios.getNombre(), usuarios.getApellido()) == null) {
                            usuarios.setPassword(getAlphaNumericString(12));
                            usuarios.setTipo("gestor");
                            usuarios.setActivo(1);
                            String passwordSinEncriptar = usuarios.getPassword();
                            BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
                            usuarios.setPassword(bCryptPasswordEncoder.encode(usuarios.getPassword()));
                            usuarioRepository.save(usuarios);
                            Email email = new Email();
                            email.emailEnviarPrimeraContraseña(usuarios.getCorreo(), passwordSinEncriptar, usuarios.getCorreo());
                            attr.addFlashAttribute("msg", "Gestor creado exitosamente");
                            return "redirect:/admin/listaGestores";
                        } else {
                            model.addAttribute("msgNA", "Esta persona ya está registrada");
                            return "Administrador/A-NuevoGestor";
                        }
                    } else {
                        model.addAttribute("msgT", "Este teléfono ya está registrado");
                        return "Administrador/A-NuevoGestor";
                    }
                } else { //correo no termina en @mosqoy.org
                    model.addAttribute("msgC", "Este correo no es valido");
                    return "Administrador/A-NuevoGestor";
                }
            } else { //ya existe el correo, mostrar errores
                model.addAttribute("msgC", "Este correo ya está registrado");
                return "Administrador/A-NuevoGestor";
            }
        }
    }

    @GetMapping("borrarGestor")
    public String borrarGestor(@RequestParam("id") int id,
                               RedirectAttributes attr) {
        Optional<Usuarios> opt = usuarioRepository.findById(id);
        Usuarios u = new Usuarios();
        if (opt.isPresent()) {
            u = opt.get();
            u.setActivo(0);
            usuarioRepository.save(u);
            attr.addFlashAttribute("msg", "Gestor eliminado correctamete");
        }
        return "redirect:/admin/listaGestores";
    }

    //--------------------------Usuarios Sede
    @GetMapping("listaUsuarios")
    public String listaUsuarios(Model model) {
        model.addAttribute("listUsuariosSede", usuarioRepository.findByTipoAndActivo("sede", 1));
        return "Administrador/A-ListaUsuariosSede";
    }


    //--------------------------Sistema
    @GetMapping("perfil")
    public String perfil() {
        return "Administrador/A-Perfil";
    }

    @GetMapping("generarCuenta")
    public String generarCuenta() {
        return "Administrador/A-GenerarCuenta";
    }


    //------------Alfanumerico contraseñas
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

    /*
    @ExceptionHandler(Exception.class)
    public String ExceptionHandlerSede(Exception e, RedirectAttributes attr) {
        attr.addFlashAttribute("msgError", "Ocurrio un error, no se completo el proceso");
        System.out.println("!!!!! \n \n OCURRIO EL SIGUIENTE ERROR: \n  " + e.getMessage() + " \n \n !!!!!!!");
        return "redirect:/admin/principal";

    }*/
}
