package com.example.proyecto.controller;


import com.example.proyecto.entity.Usuarios;
import com.example.proyecto.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.mail.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/system")
public class SystemController {

    @Autowired
    UsuarioRepository usuarioRepository;

    @GetMapping("cambiarCont")
    public String cambiarContraseña(Model model, @RequestParam("hasheado") String hasheado){
        model.addAttribute("hasheado",hasheado);
        return "Sistema/S-CambiarContra";
    }

    @GetMapping("recuperarCont")
    public String recuperarCuenta(){ return "Sistema/S-RecupContra"; }

    @GetMapping("nuevaCont")
    public String nuevaContraseña(){ return "Sistema/S-NuevContra"; }

    @PostMapping("enviarCorreoRecuperarCont")
    public String enviarCorreoRecupCuenta(RedirectAttributes attr,
                                          @RequestParam("correo")String correo, HttpServletRequest request) throws MessagingException, UnknownHostException {

        Usuarios usuarioBuscado = usuarioRepository.findByCorreo(correo);

        if(usuarioBuscado!= null){
            //Se obtiene el usuario, se le crea su hash con ID y luego se le setea
            Integer usuarioID= usuarioBuscado.getIdusuarios();
            BCryptPasswordEncoder hasheadoId = new BCryptPasswordEncoder();
            System.out.println(usuarioBuscado.getPassword());
            usuarioBuscado.setHasheado(hasheadoId.encode(usuarioBuscado.getPassword()));
            String hasheado = usuarioRepository.seleccionarHash(usuarioID);

            //Prepara para usar el método del email
            String context = request.getContextPath();
            int localPort= request.getLocalPort();
            String ipAddr = InetAddress.getLocalHost().getHostAddress();

            //Envia email para recuperar la cuenta (se envia email con CambiarContra.html)
            Email email = new Email();
            email.emailRecuperarCuenta(correo,hasheado,ipAddr,localPort,context);
            attr.addFlashAttribute("msg", "Se ha enviado el correo exitosamente");
            return "redirect:/system/recuperarCont";

        }else{
            attr.addFlashAttribute("msg", "No se ha encontrado el correo ingresado");
            return "redirect:/system/recuperarCont";
        }

    }

    @PostMapping("guardarCont")
    public String guardarCont(@RequestParam("psw1") String psw1,
                              @RequestParam("psw2") String psw2,
                              @RequestParam("hasheado") String hasheado,
                              Model model, RedirectAttributes attr,
                              HttpSession session){
        if(!"".equals(psw1) && !"".equals(psw2)){
            if(psw1.equals(psw2)){
                if(psw1.length()<8){
                    attr.addFlashAttribute("msg", "Mínimo 8 caracteres");
                    return "redirect:/system/cambiarCont";
                }else if(psw1.length()>40){
                    attr.addFlashAttribute("msg", "Demasiados caracteres");
                    return "redirect:/system/cambiarCont";
                }else {
                    attr.addFlashAttribute("msg", "Contraseña actualizada.");
                    Usuarios usuarioLog=(Usuarios) session.getAttribute("user");
                    usuarioLog.setPassword(new BCryptPasswordEncoder().encode(psw1));
                    session.setAttribute("user", usuarioLog);
                    usuarioRepository.save(usuarioLog);
                    return "redirect:/system/cambiarCont";
                }
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
