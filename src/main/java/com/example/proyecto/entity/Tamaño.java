package com.example.proyecto.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "tamanho")
public class Tamaño {

    @Id
    private int idtamanho;
    @Column(nullable = false)
    private String nombre;
    @Column(nullable = false)
    private String codigo;

    public int getIdTamanho() {
        return idtamanho;
    }

    public void setIdTamanho(int idTamanho) {
        this.idtamanho = idTamanho;
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
