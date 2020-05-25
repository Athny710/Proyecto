package com.example.proyecto.entity;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity
@Table(name = "adquisicion")
public class Adquisicion {

    @Id
    private int idadquisicion;
    @Column(nullable = false)
    private String modalidad;
    @Column(nullable = false)
    @NotBlank(message = "Este campo no puede ser vacío")
    private String fecha;
    private String fechafin;
    @ManyToOne
    @JoinColumn(name = "idartesano")
    private Artesano artesano;


    public int getIdAdquisicion() {
        return idadquisicion;
    }

    public void setIdAdquisicion(int idAdquisicion) {
        this.idadquisicion = idAdquisicion;
    }

    public String getModalidad() {
        return modalidad;
    }

    public void setModalidad(String modalidad) {
        this.modalidad = modalidad;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getFechaFin() {
        return fechafin;
    }

    public void setFechaFin(String fechaFin) {
        this.fechafin = fechaFin;
    }

    public Artesano getArtesano() {
        return artesano;
    }

    public void setArtesano(Artesano artesano) {
        this.artesano = artesano;
    }
}
