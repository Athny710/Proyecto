package com.example.proyecto.entity;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;


@Entity
@Table(name = "tienda")
public class Tienda {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idtienda;
    @ManyToOne
    @JoinColumn(name = "idsede")
    private Sede sede;
    @NotBlank(message = "* Debe ingresar el nombre de la tienda")
    @Size(max=45,message = "* El nombre de la tienda no debe exceder los 45 caracteres")
    private String nombre;


    public int getIdtienda() {
        return idtienda;
    }

    public void setIdtienda(int idtienda) {
        this.idtienda = idtienda;
    }

    public Sede getSede() {
        return sede;
    }

    public void setSede(Sede sede) {
        this.sede = sede;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
