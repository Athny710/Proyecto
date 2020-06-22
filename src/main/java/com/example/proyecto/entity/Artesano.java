package com.example.proyecto.entity;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Entity
@Table(name = "artesano")
public class Artesano implements Serializable {
    @Id
    @Column(name = "idartesano")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idartesano;
    @Column(nullable = false)
    @NotBlank(message = "Este Campo no puede ser vacío")
    @Size(max = 45, message = "No puede tener más de 45 caracteres")
    @Pattern(regexp = "^[a-zA-Z\\\\s]*$",message = "solo se debe ingresar letras")
    private String nombre;
    @Column(nullable = false)
    @NotBlank(message = "Este Campo no puede ser vacío")
    @Size(max = 45, message = "No puede tener más de 45 caracteres")
    @Pattern(regexp = "^[a-zA-Z\\s]*$",message = "solo se debe ingresar letras")
    private String apellidopaterno;
    //@NotBlank(message = "Este Campo no puede ser vacío")
    private String apellidomaterno;
    @Column(nullable = false, name = "codigo")
    @NotBlank(message = "Este Campo no puede ser vacío")
    @Pattern(regexp = "^[a-zA-Z\\\\s]*$",message = "solo se debe ingresar letras")
    private String codigo;

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

    public String getApellidopaterno() {
        return apellidopaterno;
    }

    public void setApellidoPaterno(String apellidoPaterno) {
        this.apellidopaterno = apellidoPaterno;
    }

    public String getApellidomaterno() {
        return apellidomaterno;
    }

    public void setApellidoMaterno(String apellidoMaterno) {
        this.apellidomaterno = apellidoMaterno;
    }

    public String getCodigo() { return codigo; }

    public void setCodigo(String codigo) { this.codigo = codigo; }
}
