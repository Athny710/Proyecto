package com.example.proyecto.controller;


import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {
    @GetMapping("/loginForm")
    public String loginForm(){
        return"index";
    }

    @GetMapping("/redirectByRole")
    public String redirigir(Authentication auth){
        String rol = "";

        for(GrantedAuthority role : auth.getAuthorities()){
            rol = role.getAuthority();
        }
        if(rol.equalsIgnoreCase("administrador")){
            return "redirect:/admin";
        }else if (rol.equalsIgnoreCase("sede")){
            return "redirect:/sede";
        }else if (rol.equalsIgnoreCase("gestor")){
            return "redirect:/gestor";
        }else {
            return "index";
        }
    }
}
