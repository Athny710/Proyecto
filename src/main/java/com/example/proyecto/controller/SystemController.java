package com.example.proyecto.controller;


import com.example.proyecto.entity.Usuarios;
import com.example.proyecto.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.mail.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;


@Controller
@RequestMapping("/system")
public class SystemController {

    @Autowired
    UsuarioRepository usuarioRepository;


    @GetMapping("CambiarContSess")
    public String cambiarCont(){
        return "Sistema/S-CambiarContSes";
    }



    @PostMapping("guardarContSession")
    public String guardarCont(@RequestParam("psw1") String psw1,
                              @RequestParam("psw2") String psw2,
                              Model model, RedirectAttributes attr,
                              HttpSession session){
        if(!"".equals(psw1) && !"".equals(psw2)){
            if(psw1.equals(psw2)){
                if(psw1.length()<8){
                    attr.addFlashAttribute("msg", "Mínimo 8 caracteres");
                    return "redirect:/system/CambiarContSess";
                }else if(psw1.length()>40){
                    attr.addFlashAttribute("msg", "Demasiados caracteres");
                    return "redirect:/system/CambiarContSess";
                }else {
                    attr.addFlashAttribute("msg", "Contraseña actualizada.");
                    Usuarios usuarioLog=(Usuarios) session.getAttribute("user");
                    usuarioLog.setPassword(new BCryptPasswordEncoder().encode(psw1));
                    session.setAttribute("user", usuarioLog);
                    usuarioRepository.save(usuarioLog);
                    if (usuarioLog.getTipo().equalsIgnoreCase("administrador")){
                        return "redirect:/admin";
                    }else if (usuarioLog.getTipo().equalsIgnoreCase("gestor")){
                        return "redirect:/gestor";
                    }else if (usuarioLog.getTipo().equalsIgnoreCase("sede")){
                        return "redirect:/sede";
                    }else {
                        return "redirect:/";
                    }
                }
            }else{
                attr.addFlashAttribute("msg", "Las contraseñas no coinciden");
                return "redirect:/system/CambiarContSess";
            }
        }else{
            attr.addFlashAttribute("msg", "No puede haber campos vacíos.");
            return "redirect:/system/CambiarContSess";
        }
    }



    @GetMapping("cambiarCont")
    public String cambiarContraseña(Model model,
                                    @RequestParam("hasheado") String hasheado){
        model.addAttribute("hasheado",hasheado);
        return "Sistema/S-NuevContra2";
    }

    @GetMapping("recuperarCont")
    public String recuperarCuenta(){ return "Sistema/S-RecupContra"; }

    @GetMapping("nuevaCont")
    public String nuevaContraseña(){ return "Sistema/S-NuevContra"; }

    @PostMapping("enviarCorreoRecuperarCont")
    public String enviarCorreoRecupCuenta(RedirectAttributes attr,
                                          @RequestParam("correo")String correo,
                                          Model model,HttpServletRequest request) throws MessagingException, IOException {

            Usuarios usuarioBuscado = usuarioRepository.findByCorreo(correo);

            if (usuarioBuscado != null) {
                //Se obtiene el usuario, se le crea su hash con ID y luego se le setea
                Integer usuarioID = usuarioBuscado.getIdusuarios();
                System.out.println(usuarioBuscado.getPassword());
                String hasheado = usuarioRepository.seleccionarHash(usuarioID);
                if (hasheado == null) {
                    BCryptPasswordEncoder hasheadoId = new BCryptPasswordEncoder();
                    usuarioBuscado.setHasheado(hasheadoId.encode(usuarioBuscado.getPassword()));
                    usuarioRepository.save(usuarioBuscado);
                    System.out.println("Validacion de hash");
                }
                //Prepara para usar el método del email
                String context = request.getContextPath();
                int localPort = request.getLocalPort();
                //String ipAddr = InetAddress.getLocalHost().getHostAddress();

                //Envia email para recuperar la cuenta (se envia email con CambiarContra.html)
                Email email = new Email();
                System.out.println("Ya se va a enviar");
                email.emailRecuperarCuenta(correo, usuarioBuscado.getHasheado(), localPort, context);
                model.addAttribute("msg", "Se ha enviado el correo exitosamente");
                System.out.println("Ya se envio");
                return "index";

            } else {
                attr.addFlashAttribute("msg", "No se ha encontrado el correo ingresado");
                return "redirect:/system/recuperarCont";
            }

    }

    @PostMapping("guardarCont")
    public String guardarCont(@RequestParam("psw1") String psw1,
                              @RequestParam("psw2") String psw2,
                              @RequestParam("hasheado") String hasheado,
                              Model model, RedirectAttributes attr){

            if(!"".equals(psw1) && !"".equals(psw2)){
                if(psw1.equals(psw2)){
                    if(psw1.length()<8){
                        model.addAttribute("msg", "Mínimo 8 caracteres");
                        model.addAttribute("hasheado",hasheado);
                        return "Sistema/S-NuevContra2";
                    }else if(psw1.length()>40){
                        model.addAttribute("msg", "Demasiados caracteres");
                        model.addAttribute("hasheado",hasheado);
                        return "Sistema/S-NuevContra2";
                    }else {
                        Usuarios usuBuscado = usuarioRepository.obtenerUsuarioPorHash(hasheado);
                        if (usuBuscado!= null){
                            usuBuscado.setPassword(new BCryptPasswordEncoder().encode(psw1));
                            usuBuscado.setHasheado(null);
                            usuarioRepository.save(usuBuscado);
                            model.addAttribute("msg", "Contraseña actualizada.");
                            model.addAttribute("hasheado",hasheado);
                            return "index";
                        }else {
                            model.addAttribute("msg", "No funciono el cambio. Debe tener el hasheado correcto.");
                            model.addAttribute("hasheado",hasheado);
                            return "index";
                        }
                    }
                }else{
                    model.addAttribute("msg", "Las contraseñas no coinciden");
                    model.addAttribute("hasheado",hasheado);
                    return "Sistema/S-NuevContra2";
                }
            }else{
                model.addAttribute("msg", "No puede haber campos vacíos.");
                model.addAttribute("hasheado",hasheado);
                return "Sistema/S-NuevContra2";
            }
        }

}
