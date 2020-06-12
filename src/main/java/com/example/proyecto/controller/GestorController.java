package com.example.proyecto.controller;

import com.example.proyecto.entity.*;
import com.example.proyecto.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
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


    // ----------------------- ENLACES ---------------------------------
    @GetMapping("perfil")
    public String perfil() {
        return "Gestor/G-Perfil";
    }


    @GetMapping("gestorGestionVentas")
    public String registroVentas() {
        return "G-GestionVentas";
    }


    @GetMapping("gestorReporteVentas")
    public String reporteVentas1() {
        return "Gestor/G-GenReporte";
    }

    @GetMapping("gestorReporteVentas2")
    public String reporteVentas2() {
        return "Gestor/G-GenReporte2";
    }


    //------------------------Perfil-------------------------------------------
    @GetMapping("editarInfo")
    public String editarInfo(@ModelAttribute("perfil") Perfil perfil, HttpSession session) {
        Usuarios u = (Usuarios) session.getAttribute("user");
        perfil.setCorreo(u.getCorreo());
        perfil.setTelefono(Integer.parseInt(u.getTelefono()));
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
                usuarioLog.setTelefono(String.valueOf(perfil.getTelefono()));
                usuarioRepository.save(usuarioLog);
                session.setAttribute("user", usuarioLog);
                return "redirect:/gestor";
            } else {
                if (usuarioRepository.findByCorreo(usuarioLog.getCorreo()) != null) {
                    model.addAttribute("msg", "Este correo ya está registrado");
                    return "Gestor/G-Perfil";
                } else {
                    attr.addFlashAttribute("msg", "Información personal editada con éxito");
                    usuarioLog.setCorreo(perfil.getCorreo());
                    usuarioLog.setTelefono(String.valueOf(perfil.getTelefono()));
                    usuarioRepository.save(usuarioLog);
                    session.setAttribute("user", usuarioLog);
                    return "redirect:/gestor";
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
        List<Usuarios> listausuariosedes = usuarioRepository.findByTipo("sede");
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
                                     RedirectAttributes attr) {
        if (bindingResult.hasErrors()) {
            return "Gestor/G-EditUsuarioSede";
        } else {
            if (usuarios.getIdusuarios() == 0 && usuarioRepository.findByCorreo(usuarios.getCorreo()) == null) {
                usuarios.setPassword(getAlphaNumericString(12));
                usuarios.setTipo("sede");
                usuarios.setActivo(1);
                BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
                usuarios.setPassword(bCryptPasswordEncoder.encode(usuarios.getPassword()));
                System.out.println(usuarios.getPassword());
                usuarioRepository.save(usuarios);
                //TODO ENVIAR CORREO CON EL PASSWORD

                attr.addFlashAttribute("msg", "Usuario sede creado exitosamente");
                return "redirect:/gestor/gestorListaUsuarioSede";
            } else if (usuarios.getIdusuarios() != 0) {
                usuarios.setTipo("sede");
                usuarios.setActivo(usuarioRepository.findById(usuarios.getIdusuarios()).get().getActivo());
                usuarios.setPassword(usuarioRepository.findById(usuarios.getIdusuarios()).get().getPassword());
                usuarios.setCorreo(usuarioRepository.findById(usuarios.getIdusuarios()).get().getCorreo());


                usuarioRepository.save(usuarios);
                attr.addFlashAttribute("msg", "Sede actualizada exitosamente");
                return "redirect:/gestor/gestorListaUsuarioSede";
            } else { //ya existe el correo, mostrar errores
                if (usuarioRepository.findByCorreo(usuarios.getCorreo()) != null){
                    model.addAttribute("msgError", "Ya hay un usuario con ese correo");
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
            attr.addFlashAttribute("msg", "Usuario Sede Eliminado");
        } else {
            attr.addFlashAttribute("msg", "Este usuario no existe");
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
            if (sede.getIdsede() == null) {
                sedeRepository.save(sede);
                attr.addFlashAttribute("msg", "Sede creada exitosamente");
                return "redirect:/gestor/gestorListaSedes";
            } else if (sede.getIdsede() != 0) {
                sedeRepository.save(sede);
                attr.addFlashAttribute("msg", "Sede actualizada exitosamente");
                return "redirect:/gestor/gestorListaSedes";
            } else { //EL IDSEDE ES IGUAL A 0
                System.out.println("ID SEDE ES 0 POR ALGUA RAZON");
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
            sedeRepository.deleteById(idsede);
            attr.addFlashAttribute("msg", "Sede Eliminada");
        }
        return "redirect:/gestor/gestorListaSedes";
    }

    // ----------------------- FIN CRUD SEDES ---------------------------------

// ----------------------- CRUD INVENTARIO ---------------------------------

    @GetMapping(value = {"", "gestorPrincipal"})
    public String inventarioGestor(Model model) {
        List<Inventario> inventario = inventarioRepository.findAll();
        //todo mostrar  mensaje de stock bajo

        model.addAttribute("inventario", inventario);
        return "Gestor/G-Inventario";
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
            model.addAttribute("historial", historial);
            model.addAttribute("producto", inventario2);

            return "Gestor/G-DetallesProdcuto";
        } else {
            return "redirect:/gestorPrincipal";
        }
    }

    @GetMapping("gestorEditProdCompra")
    public String EditProdCompra(@ModelAttribute("inventario") Inventario inventario, @ModelAttribute("producto") Producto producto,
                                 @ModelAttribute("historial") Historial historial,
                                 Model model, @RequestParam("id") int id) {

        return "Gestor/G-EditProdCompra";
    }

    @GetMapping("gestorRegProducto")
    public String RegistroCompra(@ModelAttribute("inventario") Inventario inventario, @ModelAttribute("producto") Producto producto,
                                 @ModelAttribute("historial") Historial historial,
                                 Model model) {
        model.addAttribute("listaComunidades", comunidadRepository.findAll());
        model.addAttribute("listaDenominaciones", denominacionRepository.findAll());
        model.addAttribute("listaCategorias", categoriaRepository.findAll());
        model.addAttribute("listaTama", tamañoRepository.findAll());
        model.addAttribute("listaAdqui", adquisicionRepository.findAll());
        return "Gestor/G-RegCompra";
    }

    @GetMapping("/guardarProducto")
    public String guardarProducto(@ModelAttribute("inventario") @Valid Inventario inventario, @ModelAttribute("producto") @Valid Producto producto,
                                  @ModelAttribute("historial") @Valid Historial historial, BindingResult bindingResult, RedirectAttributes attr) {


        return "redirect:/gestorPrincipal";
    }

    @GetMapping("borrarProducto")
    public String borrarProducto(@ModelAttribute("inventario") Inventario inventario, Model model) {
        return "redirect:/gestorPrincipal";
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
                                  @RequestParam("idcomunidad") int idcomunidad,
                                  RedirectAttributes attr) {
        Optional<Comunidad> optComunidad = comunidadRepository.findById(idcomunidad);
        if (optComunidad.isPresent()) {
            comunidadRepository.deleteById(idcomunidad);
            attr.addFlashAttribute("msg", "Comunidad borrada exitosamente");
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
                model.addAttribute("msgError", "Los datos ingresados ya existen, por favor modificarlo");
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
            categoriaRepository.deleteById(id);
            attr.addFlashAttribute("msg", "Categoría Eliminada");
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
            return "Gestor/G-RegistroArtesano";
        } else {

            //validacion codigo de  artesano (INICIALES)
            String aux1 = null;
            String aux2 = null;
            if (!artesano.getApellidoMaterno().isEmpty()) { // codigos con apellido materno
                aux1 = artesano.getNombre().substring(0, 1) + artesano.getApellidoPaterno().substring(0, 1) + artesano.getApellidoMaterno().substring(0, 1);
                aux2 = artesano.getNombre().substring(0, 2) + artesano.getApellidoPaterno().substring(0, 1) + artesano.getApellidoMaterno().substring(0, 1);
            } else {// codigos sin apellido materno
                aux1 = artesano.getNombre().substring(0, 1) + artesano.getApellidoPaterno().substring(0, 1);
                aux2 = artesano.getNombre().substring(0, 2) + artesano.getApellidoPaterno().substring(0, 1);
            }
            //fin validacion codigo de artesano

            if (artesanoRepository.findByCodigo(artesano.getCodigo()).size() >= 1 ||  // en caso el codigo se repita o no tenga un codigo esperado
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
                    //fin conseguir invetariosede
                    estadoenviosede.setEstado("En camino");
                    estadoenviosedeRepository.save(estadoenviosede);
                    int cantidadrestada = estadoenviosede.getInventariosede().getInventario().getStock() - estadoenviosede.getCantidad();
                    estadoenviosede.getInventariosede().getInventario().setStock(cantidadrestada);
                } else { // cantidad restada menor a 0
                    List<Inventario> listaInventario = inventarioRepository.findAll();
                    List<Sede> listaSede = sedeRepository.findAll();
                    model.addAttribute("listaInventario", listaInventario);
                    model.addAttribute("listaSede", listaSede);
                    attr.addFlashAttribute("msg", "Se esta tratando de enviar mas de lo que se tiene");
                    return "Gestor/G-GestionEnvios";
                }
                System.out.println("El stock nuevo del inventario es:" + estadoenviosede.getInventariosede().getInventario().getStock());
                inventarioRepository.save(estadoenviosede.getInventariosede().getInventario());
                attr.addFlashAttribute("msg", "Envio guardado correctamente");
                return "redirect:/gestor/gestorProductosEnviados";
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


    //--------------------CRUD VENTAS---------------
    @GetMapping("/nuevaVenta")
    public String nuevaVenta(@ModelAttribute("venta") Venta venta, Model model) {
        return "Gestor/G-NuevaVenta";
    }


    @GetMapping("gestionVentas")
    public String gestionDeVentas(@ModelAttribute("venta") Venta venta, Model model) {
        model.addAttribute("listaVentas", ventaRepository.findAll());
        return "Gestor/G-GestionVentas";
    }

    @PostMapping("/guardarVenta")
    public String guardarVenta(@ModelAttribute("venta") @Valid Venta venta, BindingResult bindingResult, RedirectAttributes att) {

        if (bindingResult.hasErrors()) {
            return "Gestor/G-NuevaVenta";
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
            return "redirect:/gestor/gestionVentas";

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


}
