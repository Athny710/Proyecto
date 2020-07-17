package com.example.proyecto.entity;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Entity
@Table(name = "sede")
public class Sede implements Serializable    {
    @Id
    @Column(name = "idsede")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idsede;
    @NotBlank(message = "Debe ingresar el nombre de la sede que desea registrar")
    @Size(max = 30, message = "No puede tener más de 30 caracteres")
   // @Pattern(regexp = "^[a-zA-Z\\\\s]*$",message = "solo se debe ingresar letras")
    @Pattern(regexp = "^[a-zA-Z\\s]*$",message = "Solo se debe ingresar letras")
    private String nombre;

    public Integer getIdsede() {
        return idsede;
    }

    public void setIdsede(Integer idsede) {
        this.idsede = idsede;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
