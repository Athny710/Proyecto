package com.example.proyecto.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Entity
@Table(name = "comunidad")
public class Comunidad {

    @Id
    private int idcomunidad;
    @Column(nullable = false)
    @NotBlank(message = "Este campo no puede estar vacío")
    @Size(max = 30, message = "No puede tener más de 30 caracteres")
   // @Pattern(regexp = "^[a-zA-Z\\\\s]*$",message = "solo se debe ingresar letras")
    @Pattern(regexp = "^[a-zA-Z\\s]*$",message = "solo se debe ingresar letras")
    private String nombre;
    @Column(nullable = false)
    @NotBlank(message = "Este campo no puede estar vacío")
    @Size(max = 2, message = "No puede tener más de 2 caracteres")
    @Pattern(regexp = "^[a-zA-Z\\\\s]*$",message = "solo se debe ingresar letras")
    private String codigo;


    public int getIdComunidad() {
        return idcomunidad;
    }

    public void setIdComunidad(int idComunidad) {
        this.idcomunidad = idComunidad;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }
}
