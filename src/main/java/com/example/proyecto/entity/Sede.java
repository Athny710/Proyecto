package com.example.proyecto.entity;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Entity
@Table(name = "sede")
public class Sede implements Serializable    {
    @Id
    @Column(name = "idsede")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idsede;
    @NotBlank(message = "* Debe ingresar el nombre de la sede que desea registrar")
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
