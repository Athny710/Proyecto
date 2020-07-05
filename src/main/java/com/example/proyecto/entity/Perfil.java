package com.example.proyecto.entity;

import javax.persistence.Entity;
import javax.validation.constraints.*;

public class Perfil {
    @NotBlank(message = "Este campo no puedeo estar vacío")
    @Size(max = 45, message = "Máximo 45 carateres")
    @Email
    private String correo;
    @NotBlank(message = "Este campo no puedeo estar vacío")
    @Size(min = 7, max = 25, message =  "Teléfono no válido")
    private String telefono;

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }
}
