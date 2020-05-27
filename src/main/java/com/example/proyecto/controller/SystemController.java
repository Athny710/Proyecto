package com.example.proyecto.controller;


import com.example.proyecto.entity.Usuarios;
import com.example.proyecto.repository.UsuarioRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@Controller
@RequestMapping("/system")
public class SystemController {

    @GetMapping("cambiarCont")
    public String cambiarContraseña(){
        return "Sistema/S-NuevContra2";
    }

    @PostMapping("guardarCont")
    public String guardarCont(@RequestParam("psw1") String psw1,
                              @RequestParam("psw2") String psw2,
                              Model model, RedirectAttributes attr,
                              HttpSession session){
        if(!"".equals(psw1) && !"".equals(psw2)){
            if(psw1.equals(psw2)){
                attr.addFlashAttribute("msg", "Contraseña actualizada.");
                Usuarios usuarioLog=(Usuarios) session.getAttribute("user");
                usuarioLog.setPassword(psw1);
                session.setAttribute("user", usuarioLog);
                return "redirect:/admin";
            }else{
                attr.addFlashAttribute("msg", "Las contraseñas no coinciden");
                return "redirect:/system/cambiarCont";
            }
        }else{
            attr.addFlashAttribute("msg", "No puede haber campos vacíos.");
            return "redirect:/system/cambiarCont";
        }
    }
}
