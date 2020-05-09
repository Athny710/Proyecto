package com.example.proyecto.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "linea")
public class Linea {

    @Id
    private int idlinea;
    @Column(nullable = false)
    private String nombre;
    @Column(nullable = false)
    private String codigo;


    public int getIdLinea() {
        return idlinea;
    }

    public void setIdLinea(int idLinea) {
        this.idlinea = idLinea;
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
