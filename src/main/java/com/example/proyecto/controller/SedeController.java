package com.example.proyecto.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("sede")
public class SedeController {
    @GetMapping("detalleProducto")
    public String detalleProducto(){
        return "UsuarioSede/DetallesProducto";
    }
    @GetMapping("principal")
    public String principal(){
        return "UsuarioSede/Principal";
    }
    @GetMapping("nuevoProducto")
    public String nuevoProducto(){
        return "UsuarioSede/NuevoProducto";
    }
    @GetMapping("editarProducto")
    public String editar(){
        return "UsuarioSede/NuevoProducto";
    }
    @GetMapping("borrarProducto")
    public String borrarProducto(){
        return "UsuarioSede/Principal";
    }
}
