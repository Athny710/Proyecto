package com.example.proyecto.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "categoria")
public class Categoria {
    @Id
    private int idcategoria;
    @Column(nullable = false)
    private String nombre;
    @Column(nullable = false)
    private String codigo;


    public int getIdCategoria() {
        return idcategoria;
    }

    public void setIdCategoria(int idCategoria) {
        this.idcategoria = idCategoria;
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
