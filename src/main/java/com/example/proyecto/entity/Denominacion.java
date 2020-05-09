package com.example.proyecto.entity;

import javax.persistence.*;

@Entity
@Table(name = "denominacion")
public class Denominacion {

    @Id
    private int iddenominacion;
    @Column(nullable = false)
    private String nombre;
    @Column(nullable = false)
    private String codigonombre;
    @Column(nullable = false)
    private String descripcion;
    @Column(nullable = false)
    private String codigodescripcion;
    @ManyToOne
    @JoinColumn(name="idlinea")
    private Linea linea;


    public int getIdDenominacion() {
        return iddenominacion;
    }

    public void setIdDenominacion(int idDenominacion) {
        this.iddenominacion = idDenominacion;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCodigoNombre() {
        return codigonombre;
    }

    public void setCodigoNombre(String codigoNombre) {
        this.codigonombre = codigoNombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getCodigoDescripcion() {
        return codigodescripcion;
    }

    public void setCodigoDescripcion(String codigoDescripcion) {
        this.codigodescripcion = codigoDescripcion;
    }

    public Linea getLinea() {
        return linea;
    }

    public void setLinea(Linea linea) {
        this.linea = linea;
    }
}
