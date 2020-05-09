package com.example.proyecto.entity;

import javax.persistence.*;

@Entity
@Table(name = "denominacion")
public class Denominacion {

    @Id
    private int idDenominacion;
    @Column(nullable = false)
    private String nombre;
    @Column(nullable = false)
    private String codigoNombre;
    @Column(nullable = false)
    private String descripcion;
    @Column(nullable = false)
    private String codigoDescripcion;
    @ManyToOne
    @JoinColumn(name="idLinea")
    private Linea linea;


    public int getIdDenominacion() {
        return idDenominacion;
    }

    public void setIdDenominacion(int idDenominacion) {
        this.idDenominacion = idDenominacion;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCodigoNombre() {
        return codigoNombre;
    }

    public void setCodigoNombre(String codigoNombre) {
        this.codigoNombre = codigoNombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getCodigoDescripcion() {
        return codigoDescripcion;
    }

    public void setCodigoDescripcion(String codigoDescripcion) {
        this.codigoDescripcion = codigoDescripcion;
    }

    public Linea getLinea() {
        return linea;
    }

    public void setLinea(Linea linea) {
        this.linea = linea;
    }
}
