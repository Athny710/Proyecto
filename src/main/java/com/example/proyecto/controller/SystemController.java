package com.example.proyecto.controller;


import com.example.proyecto.entity.Usuarios;
import com.example.proyecto.repository.UsuarioRepository;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@Controller
@RequestMapping("/system")
public class SystemController {

    @GetMapping("")
    public String cambiarContraseña(){
        return "Sistema/S-NuevContra2";
    }

}
