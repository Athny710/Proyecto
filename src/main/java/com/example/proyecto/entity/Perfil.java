package com.example.proyecto.entity;

import javax.persistence.Entity;
import javax.validation.constraints.*;

public class Perfil {
    @NotBlank(message = "  Este campo no puedeo estar vacío")
    @Size(max = 45, message = "Máximo 45 carateres")
    @Email
    private String correo;
    @NotNull(message = "  Este campo no puedeo estar vacío")
    @Digits(integer = 9, fraction = 0, message = "Máximo 9 carateres")
    @Positive(message = "Numero ingreso inválido")
    @Min(value = 1000000, message = "número no válido")
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
