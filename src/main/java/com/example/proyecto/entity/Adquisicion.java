package com.example.proyecto.entity;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.time.LocalDate;
import java.util.Date;

@Entity
@Table(name = "adquisicion")
public class Adquisicion {

    @Id
    private int idadquisicion;
    @Column(nullable = false)
    private String modalidad;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate fecha;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate fechafin;
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



    public Artesano getArtesano() {
        return artesano;
    }

    public void setArtesano(Artesano artesano) {
        this.artesano = artesano;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public LocalDate getFechafin() {
        return fechafin;
    }

    public void setFechafin(LocalDate fechafin) {
        this.fechafin = fechafin;
    }
}
