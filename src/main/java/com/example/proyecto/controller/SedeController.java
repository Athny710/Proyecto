package com.example.proyecto.controller;

import com.example.proyecto.entity.Inventario;
import com.example.proyecto.repository.InventarioRepository;
import org.apache.catalina.LifecycleState;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/sede")
public class SedeController {

    @Autowired
    InventarioRepository inventarioRepository;

    @GetMapping("detalleProducto")
    public String detalleProducto(){
        return "UsuarioSede/DetallesProducto";
    }
    @GetMapping(value = {"principal", "listar"})
    public String principal(Model model){
        List<Inventario> lista = inventarioRepository.findAll();
        model.addAttribute("listaProductos", lista);
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
