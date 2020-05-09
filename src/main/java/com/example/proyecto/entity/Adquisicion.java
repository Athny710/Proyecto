package com.example.proyecto.entity;

import javax.persistence.*;

@Entity
@Table(name = "adquisicion")
public class Adquisicion {

    @Id
    private int idAdquisicion;
    @Column(nullable = false)
    private String modalidad;
    @Column(nullable = false)
    private String fecha;
    private String fechaFin;
    @ManyToOne
    @JoinColumn(name = "idArtesano")
    private Artesano artesano;


    public int getIdAdquisicion() {
        return idAdquisicion;
    }

    public void setIdAdquisicion(int idAdquisicion) {
        this.idAdquisicion = idAdquisicion;
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
        return fechaFin;
    }

    public void setFechaFin(String fechaFin) {
        this.fechaFin = fechaFin;
    }

    public Artesano getArtesano() {
        return artesano;
    }

    public void setArtesano(Artesano artesano) {
        this.artesano = artesano;
    }
}
