package com.example.proyecto.controller;


import com.example.proyecto.entity.*;

import com.example.proyecto.entity.Artesano;
import com.example.proyecto.entity.Comunidad;
import com.example.proyecto.entity.Inventario;
import com.example.proyecto.entity.Tienda;
import com.example.proyecto.repository.*;
import com.example.proyecto.services.VentasService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.jws.WebParam;
import javax.rmi.CORBA.Tie;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import javax.xml.bind.SchemaOutputResolver;
import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
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
    @Autowired
    ProductoRepository productoRepository;
    @Autowired
    EstadoenviosedeRepository estadoenviosedeRepository;
    @Autowired
    UsuarioRepository usuarioRepository;
    @Autowired
    SedeRepository sedeRepository;

    @Autowired
    VentasService ventasService;
    @Autowired
    ServletContext context;


    @GetMapping("perfil")
    public String perfil() {
        return "UsuarioSede/U-Perfil";
    }


    //--------------------------Inventario---------------------------------------------
    @GetMapping(value = {"", "principal"})
    public String principal(Model model, HttpSession session) {
        Usuarios usuario = (Usuarios) session.getAttribute("user");
        List<Inventariosede> lista = inventariosedeRepository.findBySede(usuario.getSede());
        model.addAttribute("listaProductos", lista);
        return "UsuarioSede/U-Principal";
    }

    @GetMapping("DetallesProducto")
    public String detallesProdcutoCompra(Model model, @RequestParam("id") int id, HttpSession session, RedirectAttributes attr) {
        Usuarios usuario = (Usuarios) session.getAttribute("user");
        List<Inventariosede> lista = inventariosedeRepository.findBySede(usuario.getSede());
        boolean presente = false;

        for (Inventariosede inven : lista){
            if (inven.getIdinventariosede() == id){
                presente = true;
                break;
            }
        }

        if (presente == true) {
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
        } else {
            attr.addFlashAttribute("msg", "Producto no encontrado");
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


//------------------------- FIN CRUD INVENTARIO -----------------------------------------------

//------------------------------PERFIL--------------------------------------------------------
    @GetMapping("editarInfo")
    public String editarInfo(@ModelAttribute("perfil") Perfil perfil, HttpSession session){
        Usuarios u = (Usuarios) session.getAttribute("user");
        perfil.setCorreo(u.getCorreo());
        perfil.setTelefono(Integer.parseInt(u.getTelefono()));
        return "UsuarioSede/U-Perfil";
    }
    @PostMapping("guardarPerfil")
    public String guardarInfo(@ModelAttribute("perfil") @Valid Perfil perfil,
                              BindingResult bindingResult,
                              RedirectAttributes attr, HttpSession session, Model model){

        Usuarios usuarioLog=(Usuarios) session.getAttribute("user");

        if(bindingResult.hasErrors()){
            return "UsuarioSede/U-Perfil";
        }else{
            if(perfil.getCorreo().equals(usuarioLog.getCorreo())){
                attr.addFlashAttribute("msg", "Información personal editada con éxito");
                usuarioLog.setCorreo(perfil.getCorreo());
                usuarioLog.setTelefono(String.valueOf(perfil.getTelefono()));
                usuarioRepository.save(usuarioLog);
                session.setAttribute("user", usuarioLog);
                return "redirect:/sede";
            }else{
                if(usuarioRepository.findByCorreo(usuarioLog.getCorreo())!=null){
                    model.addAttribute("msg", "Este correo ya está registrado");
                    return "UsuarioSede/U-Perfil";
                }else{
                    attr.addFlashAttribute("msg", "Información personal editada con éxito");
                    usuarioLog.setCorreo(perfil.getCorreo());
                    usuarioLog.setTelefono(String.valueOf(perfil.getTelefono()));
                    usuarioRepository.save(usuarioLog);
                    session.setAttribute("user", usuarioLog);
                    return "redirect:/sede";
                }
            }
        }
    }

    //--------------------CRUD VENTAS---------------
    @GetMapping("/nuevaVenta")
    public String nuevaVenta(@ModelAttribute("venta") Venta venta, Model model) {
        return "UsuarioSede/U-NuevaVenta";
    }


    @PostMapping("/buscarVenta")
    public String buscarVenta(@RequestParam("searchField") String searchField,
                              Model model) {

        List<Venta> listaVenta = ventaRepository.buscarPorNombre(searchField);
        model.addAttribute("listaVentas", listaVenta);
        return "usuarioSede/U-GestionVentas";
    }


    @GetMapping("gestionVentas")
    public String gestionDeVentas(@ModelAttribute("venta") Venta venta, Model model) {
        model.addAttribute("listaVentas", ventasService.getVentas());
        return "UsuarioSede/U-GestionVentas";
    }

    //PDF !!
    @GetMapping("createpdf")
    public void crearPDFdeLista(HttpServletRequest request, HttpServletResponse response) {
        List<Venta> venta = ventasService.getVentas();
        boolean isFlag = ventasService.createPDF(venta,context,request,response);
        if(isFlag){
            String fullPath = request.getServletContext().getRealPath("/resources/reports/" + "ventas" + ".pdf");
            filedownload(fullPath,response,"ventas.pdf");
        }
    }
    //EXCEL !!
    @GetMapping("createexcel")
    public void crearExcel(HttpServletRequest request, HttpServletResponse response){
        List<Venta> venta = ventasService.getVentas();
        boolean isFlag = ventasService.createExcel(venta, context, request, response);
        if (isFlag){
            String fullpath = request.getServletContext().getRealPath("/resources/reports/" + "ventas" + ".xls");
            filedownload(fullpath,response,"ventas.xls");
        }
    }

    private void filedownload(String fullpath, HttpServletResponse response, String filename){
        File file = new File(fullpath);
        final int BUFFER_SIZE = 4096;
        if(file.exists()){
            try {
                FileInputStream inputStream = new FileInputStream(file);
                String mimeType = context.getMimeType(fullpath);
                response.setContentType(mimeType);
                response.setHeader("content-disposition","attachment; filename=" + filename);
                OutputStream outputStream = response.getOutputStream();
                byte[] buffer = new byte[BUFFER_SIZE];
                int bytesRead = -1 ;
                while((bytesRead = inputStream.read(buffer))!= -1){
                    outputStream.write(buffer,0,bytesRead);
                }
                inputStream.close();
                outputStream.close();
                file.delete();

            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }


    @PostMapping("/guardarVenta")
    public String guardarVenta(@ModelAttribute("venta") @Valid Venta venta, BindingResult
            bindingResult, RedirectAttributes att) {

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

        }
    }

    //----------------INICIO CRUD TIENDAS-------------------
    @GetMapping("registroTiendas")
    public String registroDeTiendas(@ModelAttribute("tienda") Tienda tienda, Model model) {
        model.addAttribute("listaTiendas", tiendaRepository.findAll());

        return "UsuarioSede/U-TiendaDistribuidor";
    }

    @GetMapping("registroRealTienda")
    public String registroRealDeTiendas(@ModelAttribute("tienda") Tienda tienda, Model model) {
        model.addAttribute("listaTiendas", tiendaRepository.findAll());

        return "UsuarioSede/U-NuevaTienda";
    }

    @PostMapping("guardarTienda")
    public String guardarTienda(@ModelAttribute("tienda") @Valid Tienda tienda, BindingResult bindingResult,
                                RedirectAttributes attr,
                                Model model) {

            if (bindingResult.hasErrors()) {
                model.addAttribute("listaTiendas", tiendaRepository.findAll());
                return "UsuarioSede/U-NuevaTienda";
            }else{
                if (tienda.getIdtienda() == 0) {
                    if(tiendaRepository.findByNombre(tienda.getNombre()).size() >= 1){
                        model.addAttribute("listaTiendas", tiendaRepository.findAll());
                        attr.addFlashAttribute("msgError", "Atención! Esta tienda ya ha sido registrada anteriormente");
                        return "redirect:/sede/registroRealTienda";
                    }else {
                        tiendaRepository.save(tienda);
                        attr.addFlashAttribute("msg", "Tienda nueva agregada a la lista");
                        return "redirect:/sede/registroTiendas";
                    }
                } else {
                    if(tiendaRepository.findByNombre(tienda.getNombre()).size() >= 1){
                        model.addAttribute("listaTiendas", tiendaRepository.findAll());
                        attr.addFlashAttribute("msgError", "Atención! La tienda que intentó registrar ya se encontraba en la lista");
                        return "redirect:/sede/registroTiendas";

                    }else {
                        tiendaRepository.save(tienda);
                        attr.addFlashAttribute("msg", "Tienda actualizada correctamente");
                        return "redirect:/sede/registroTiendas";
                    }
                }
            }
    }

    @PostMapping("/buscarTienda")
    public String buscarComunidad(@ModelAttribute("tienda") Tienda tienda,@RequestParam("searchField") String searchField,
                                  Model model) {

        List<Tienda> listaT = tiendaRepository.buscarPorNombreDeTienda(searchField);
        model.addAttribute("listaTiendas", listaT);
        return "UsuarioSede/U-TiendaDistribuidor";
    }

    @GetMapping("borrarTienda")
    public String borrarTiendas(Model model, @RequestParam("id") int idtienda, RedirectAttributes attr) {
        Optional<Tienda> obtenerTienda = tiendaRepository.findById(idtienda);
        if (obtenerTienda.isPresent()) {
            tiendaRepository.deleteById(idtienda);
            attr.addFlashAttribute("msg", "La tienda ha sido eliminada permanentemente");
        }
        return "redirect:/sede/registroTiendas";
    }

    @GetMapping("editarTienda")
    public String editarTienda(@RequestParam("id") int idtienda, @ModelAttribute("tienda") Tienda tienda, Model
            model) {
        Optional<Tienda> tiendaPorID = tiendaRepository.findById(idtienda);
        if (tiendaPorID.isPresent()) {
            model.addAttribute("tienda", tiendaPorID.get());
            return "UsuarioSede/U-NuevaTienda";
        } else {
            return "redirect:/sede/registroTiendas";
        }
    }

    //----------------FIN CRUD TIENDAS-------------------

    // ver los productos en espera en una sede
    @GetMapping("/productosEnEspera")
    public String productosEnEspera(Model model, HttpSession session) {
        Usuarios usuario = (Usuarios) session.getAttribute("user");
        model.addAttribute("listaProductosEnviadosSede", productoRepository.listaProductosEnviadosSede(usuario.getSede().getIdsede()));

        return "UsuarioSede/U-ProductoEspera";
    }

    //devolver prodctos de sede a inventario
    @GetMapping("DevolverProductoSinConfirmar")
    public String DevolverProductoSinConfirmar(@RequestParam("id") int id, RedirectAttributes attr) {
        //DONE!! TODA LA LOGICA
        Optional<Estadoenviosede> estadoenviosede = estadoenviosedeRepository.findById(id);
        if (estadoenviosede.isPresent()) {
            Inventario inventario = estadoenviosede.get().getInventariosede().getInventario();
            inventario.setStock(inventario.getStock() + estadoenviosede.get().getCantidad());
            inventarioRepository.save(inventario);
            estadoenviosedeRepository.deleteById(id);
            System.out.println("----------Bien borrado ---------------");
            return "redirect:/sede/productosEnEspera";
        } else {
            System.out.println("error en Devolver producto, no existe el id");
            return "redirect:/sede/productosEnEspera";
        }
    }
    //aceptar productos en una sede
    @GetMapping("AceptarProducto")
    public String AceptarProducto(Model model, @RequestParam("id") int id) {
        Optional<Estadoenviosede> estadoenviosede = estadoenviosedeRepository.findById(id);
        if (estadoenviosede.isPresent()) {
            Inventariosede inventariosede = estadoenviosede.get().getInventariosede();
            inventariosede.setStock(estadoenviosede.get().getCantidad() + inventariosede.getStock());
            inventariosedeRepository.save(inventariosede);
            System.out.println("Se Guardo el inventariosede actualizado");
            estadoenviosedeRepository.deleteById(id);
            System.out.println("-------se borro el estado envio sede");
            return "redirect:/sede/productosEnEspera";

        } else {
            System.out.println("error en aceptar producto, no existe el id");
            return "redirect:/sede/productosEnEspera";
        }
    }

}


