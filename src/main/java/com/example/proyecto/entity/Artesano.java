package com.example.proyecto.entity;

import javax.persistence.*;

@Entity
@Table(name = "artesano")
public class Artesano {
    @Id
    private int idArtesano;
    @Column(nullable = false)
    private String nombre;
    @Column(nullable = false)
    private String apellidoPaterno;
    private String apellidoMaterno;
    @ManyToOne
    @JoinColumn(name = "idComunidad")
    private Comunidad comunidad;



    public Comunidad getComunidad() {
        return comunidad;
    }

    public void setComunidad(Comunidad comunidad) {
        this.comunidad = comunidad;
    }

    public int getIdArtesano() {
        return idArtesano;
    }

    public void setIdArtesano(int idArtesano) {
        this.idArtesano = idArtesano;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidoPaterno() {
        return apellidoPaterno;
    }

    public void setApellidoPaterno(String apellidoPaterno) {
        this.apellidoPaterno = apellidoPaterno;
    }

    public String getApellidoMaterno() {
        return apellidoMaterno;
    }

    public void setApellidoMaterno(String apellidoMaterno) {
        this.apellidoMaterno = apellidoMaterno;
    }


}
