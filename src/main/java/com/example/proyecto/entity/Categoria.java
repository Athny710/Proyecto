package com.example.proyecto.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Entity
@Table(name = "categoria")
public class Categoria {
    @Id
    private int idcategoria;
    @Column(nullable = false)
    @NotBlank(message = "Este Campo no puede ser vacío")
    @Size(max = 45, message = "No puede tener mas de 45 caracter")
    private String nombre;
    @Column(nullable = false)
    @NotBlank(message = "Este Campo no puede ser vacío")
    @Size(max = 1, message = "No puede tener mas de 1 caracter")
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
