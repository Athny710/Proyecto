package com.example.proyecto.controller;

import com.example.proyecto.entity.Inventario;
import com.example.proyecto.repository.ComunidadRepository;
import com.example.proyecto.repository.InventarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/sede")
public class SedeController {

    @Autowired
    InventarioRepository inventarioRepository;
    @Autowired
    ComunidadRepository comunidadRepository;

    @GetMapping("detalleProducto")
    public String detalleProducto(@RequestParam("id") int id, Model model){
        Optional<Inventario> opt = inventarioRepository.findById(id);
        if(opt.isPresent()){
            Inventario inv =opt.get();
            model.addAttribute("inventario", inv);
            return "UsuarioSede/DetallesProducto";
        }else{
            return "UsuarioSede/Principal";
        }
    }

    @GetMapping("principal")
    public String principal(Model model){
        List<Inventario> lista = inventarioRepository.findAll();
        model.addAttribute("listaProductos", lista);
        return "UsuarioSede/Principal";
    }
    @GetMapping("nuevoProducto")
    public String nuevoProducto(Model model){
        model.addAttribute("listaComunidad", comunidadRepository.findAll());
        return "UsuarioSede/NuevoProducto";
    }


    @GetMapping("editarProducto")
    public String editar(){
        return "UsuarioSede/NuevoProducto";
    }

    @GetMapping("borrarProducto")
    public String borrarProducto(Model model,
                                 @RequestParam("id") int idinventario,
                                 RedirectAttributes atrr){
        return "UsuarioSede/Principal";
    }
}
