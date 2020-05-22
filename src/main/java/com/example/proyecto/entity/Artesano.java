package com.example.proyecto.entity;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity
@Table(name = "artesano")
public class Artesano {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idartesano;
    @Column(nullable = false)
    @NotBlank(message = "Este Campo no puede ser vacío")
    private String nombre;
    @Column(nullable = false)
    @NotBlank(message = "Este Campo no puede ser vacío")
    private String apellidopaterno;
    @NotBlank(message = "Este Campo no puede ser vacío")
    private String apellidomaterno;
    @ManyToOne
    @JoinColumn(name = "idcomunidad")
    private Comunidad comunidad;



    public Comunidad getComunidad() {
        return comunidad;
    }

    public void setComunidad(Comunidad comunidad) {
        this.comunidad = comunidad;
    }

    public Integer getIdArtesano() {
        return idartesano;
    }

    public void setIdArtesano(Integer idArtesano) {
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
