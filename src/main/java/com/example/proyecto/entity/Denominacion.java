package com.example.proyecto.entity;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Entity
@Table(name = "denominacion")
public class Denominacion {

    @Id
    private int iddenominacion;
    @Column(nullable = false)
    @NotBlank(message = "Este campo no puede estar vacío")
    @Size(max = 45, message = "No puede tener más de 45 caracteres")
    private String nombre;
    @Column(nullable = false)
    @NotBlank(message = "Este campo no puede estar vacío")
    private String codigonombre;
    @NotBlank(message = "Este campo no puede estar vacío")
    @Size(max = 45, message = "No puede tener más de 45 caracteres")
    private String descripcion;
    @Column(nullable = false)
    @NotBlank(message = "Este campo no puede estar vacío")
    private String codigodescripcion;
    @ManyToOne
    @JoinColumn(name="idlinea")
    private Linea linea;


    public int getIddenominacion() {
        return iddenominacion;
    }

    public void setIddenominacion(int iddenominacion) {
        this.iddenominacion = iddenominacion;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCodigonombre() {
        return codigonombre;
    }

    public void setCodigonombre(String codigonombre) {
        this.codigonombre = codigonombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getCodigodescripcion() {
        return codigodescripcion;
    }

    public void setCodigodescripcion(String codigodescripcion) {
        this.codigodescripcion = codigodescripcion;
    }

    public Linea getLinea() {
        return linea;
    }

    public void setLinea(Linea linea) {
        this.linea = linea;
    }
}
