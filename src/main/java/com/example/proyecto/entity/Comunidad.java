package com.example.proyecto.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "comunidad")
public class Comunidad {

    @Id
    private int idcomunidad;
    @Column(nullable = false)
    private String nombre;
    @Column(nullable = false)
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