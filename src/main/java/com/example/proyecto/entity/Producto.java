package com.example.proyecto.entity;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity
@Table(name = "producto")
public class Producto {

    @Id
    private int idproducto;
    @ManyToOne
    @JoinColumn(name = "idcategoria")
    private Categoria categoria;
    @ManyToOne
    @JoinColumn(name = "idtamaño")
    private Tamaño tamanho;
    @ManyToOne
    @JoinColumn(name = "idcomunidad")
    private Comunidad comunidad;
    @ManyToOne
    @JoinColumn(name = "iddenominacion")
    private Denominacion denominacion;
    @ManyToOne
    @JoinColumn(name = "idadquisicion")
    private Adquisicion adquisicion;
    @Column(nullable = false)
    private String codigogenerado;

    public int getIdProducto() {
        return idproducto;
    }

    public void setIdProducto(int idProducto) {
        this.idproducto = idProducto;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    public Tamaño getTamanho() {
        return tamanho;
    }

    public void setTamanho(Tamaño tamanho) {
        this.tamanho = tamanho;
    }

    public Comunidad getComunidad() {
        return comunidad;
    }

    public void setComunidad(Comunidad comunidad) {
        this.comunidad = comunidad;
    }

    public Denominacion getDenominacion() {
        return denominacion;
    }

    public void setDenominacion(Denominacion denominacion) {
        this.denominacion = denominacion;
    }

    public Adquisicion getAdquisicion() {
        return adquisicion;
    }

    public void setAdquisicion(Adquisicion adquisicion) {
        this.adquisicion = adquisicion;
    }

    public String getCodigoGenerado() {
        return codigogenerado;
    }

    public void setCodigoGenerado(String codigoGenerado) {
        this.codigogenerado = codigoGenerado;
    }


}
