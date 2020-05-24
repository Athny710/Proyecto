package com.example.proyecto.controller;

import com.example.proyecto.entity.Perfil;
import com.example.proyecto.entity.Usuarios;
import com.example.proyecto.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import sun.misc.Perf;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.net.UnknownServiceException;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    UsuarioRepository usuarioRepository;

    //--------------------------Inventario
    @GetMapping(value = {"","principal"})
    public String principalAdmin(){
        return "Administrador/A-PagPrincipal";
    }
    @GetMapping("detalles")
    public String detalleInventario(){
        return "Administrador/A-DetallesInventario";
    }
    //--------------------------Perfil
    @GetMapping("editarInfo")
    public String editarInfo(@ModelAttribute("perfil") Perfil perfil, HttpSession session){
        Usuarios u = (Usuarios) session.getAttribute("user");
        perfil.setCorreo(u.getCorreo());
        perfil.setTelefono(Integer.parseInt(u.getTelefono()));
        return "Administrador/A-Perfil";
    }
    @PostMapping("guardarPerfil")
    public String guardarInfo(@ModelAttribute("perfil") @Valid Perfil perfil,
                              BindingResult bindingResult,
                              RedirectAttributes attr, HttpSession session){

        if(bindingResult.hasErrors()){
            return "Administrador/A-Perfil";
        }else{
            attr.addFlashAttribute("msg", "Información personal editada con éxito");
            Usuarios usuarioLog=(Usuarios) session.getAttribute("user");
            usuarioLog.setCorreo(perfil.getCorreo());
            usuarioLog.setTelefono(String.valueOf(perfil.getTelefono()));
            usuarioRepository.save(usuarioLog);
            session.setAttribute("user", usuarioLog);
            return "Administrador/A-PagPrincipal";
        }

    }




    //--------------------------Gestores
    @GetMapping("listaGestores")
    public String listaGestores(Model model){
        model.addAttribute("listaGestores", usuarioRepository.findByTipo("gestor"));
        return "Administrador/A-ListaGestores";
    }
    @GetMapping("nuevoGestor")
    public String nuevoGestor(@ModelAttribute("usu") Usuarios usuario){
        return "Administrador/A-NuevoGestor";
    }
    @PostMapping("guardarGestor")
    public String guardarGestor(@ModelAttribute("usu") @Valid Usuarios usuario,
                                BindingResult bindingResult,
                                RedirectAttributes attr){
        if(bindingResult.hasErrors()){
            return "Administrador/A-NuevoGestor";
        }else{
            usuario.setTipo("gestor");
            usuario.setActivo(1);
            usuario.setPassword("123456789");
            attr.addFlashAttribute("msg", "Gestor creado exitósamente!");
            usuarioRepository.save(usuario);
            return "redirect:/admin/listaGestores";
        }
    }
    @GetMapping("borrarGestor")
    public String borrarGestor(@RequestParam("id") int id,
                               RedirectAttributes attr){
        Optional<Usuarios> opt =usuarioRepository.findById(id);
        if (opt.isPresent()){
            usuarioRepository.deleteById(id);
            attr.addFlashAttribute("msg", "Gestor eliminado correctamete");
        }
        return "redirect:/admin/listaGestores";
    }

    //--------------------------Usuarios Sede
    @GetMapping("listaUsuarios")
    public String listaUsuarios(Model model){
        model.addAttribute("listUsuariosSede", usuarioRepository.findByTipo("sede"));
        return "Administrador/A-ListaUsuariosSede";
    }


    //--------------------------Sistema
    @GetMapping("perfil")
    public String perfil(){ return "Administrador/A-Perfil"; }
    @GetMapping("generarCuenta")
    public String generarCuenta(){
        return "Administrador/A-GenerarCuenta";
    }

}
