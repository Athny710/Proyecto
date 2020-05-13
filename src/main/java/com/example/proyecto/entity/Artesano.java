package com.example.proyecto.entity;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity
@Table(name = "artesano")
public class Artesano {
    @Id
    @NotBlank
    private int idartesano;
    @Column(nullable = false)
    @NotBlank
    private String nombre;
    @Column(nullable = false)
    @NotBlank
    private String apellidopaterno;
    @NotBlank
    private String apellidomaterno;
    @ManyToOne
    @JoinColumn(name = "idcomunidad")
    @NotBlank
    private Comunidad comunidad;



    public Comunidad getComunidad() {
        return comunidad;
    }

    public void setComunidad(Comunidad comunidad) {
        this.comunidad = comunidad;
    }

    public int getIdArtesano() {
        return idartesano;
    }

    public void setIdArtesano(int idArtesano) {
        this.idartesano = idArtesano;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidoPaterno() {
        return apellidopaterno;
    }

    public void setApellidoPaterno(String apellidoPaterno) {
        this.apellidopaterno = apellidoPaterno;
    }

    public String getApellidoMaterno() {
        return apellidomaterno;
    }

    public void setApellidoMaterno(String apellidoMaterno) {
        this.apellidomaterno = apellidoMaterno;
    }


}
