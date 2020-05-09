package com.example.proyecto.entity;

import javax.persistence.*;

@Entity
@Table(name = "producto")
public class Producto {

    @Id
    private int idProducto;
    @ManyToOne
    @JoinColumn(name = "idCategoria")
    private Categoria categoria;
    @ManyToOne
    @JoinColumn(name = "idTamanho")
    private Tamaño tamanho;
    @ManyToOne
    @JoinColumn(name = "idComunidad")
    private Comunidad comunidad;
    @ManyToOne
    @JoinColumn(name = "idDenominacion")
    private Denominacion denominacion;
    @ManyToOne
    @JoinColumn(name = "idAdquisicion")
    private Adquisicion adquisicion;

    public int getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(int idProducto) {
        this.idProducto = idProducto;
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
        return codigoGenerado;
    }

    public void setCodigoGenerado(String codigoGenerado) {
        this.codigoGenerado = codigoGenerado;
    }

    @Column(nullable = false)
    private String codigoGenerado;




}
