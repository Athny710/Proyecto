package com.example.proyecto.entity;

import javax.persistence.Entity;
import javax.validation.constraints.*;

public class Perfil {
    @NotBlank(message = "  Este campo no puedeo estar vacío")
    @Size(max = 45, message = "Máximo 45 carateres")
    @Email
    private String correo;
    @Digits(integer = 9, fraction = 0, message = "Máximo 9 carateres conchadetumadre")
    @Positive(message = "Numero ingreso inválido")
    @Max(value = 999999999)
    @Min(value = 1000000)
    private Integer telefono;

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public Integer getTelefono() {
        return telefono;
    }

    public void setTelefono(Integer telefono) {
        this.telefono = telefono;
    }
}
