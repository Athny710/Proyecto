package com.example.proyecto.entity;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;

@Entity
@Table(name = "usuarios")
public class Usuarios  implements Serializable {

    @Column(nullable = false)
    @NotBlank(message = "Este campo no puede ser vacío")
    @Size(max=45, message = "Demasiados caracteres")
    private String nombre;
    @NotBlank(message = "Este campo no puede ser vacío")
    @Size(max = 45, message = "Demsiados caracteres")
    private String apellido;
    @NotBlank(message = "Este campo no puede ser vacío")
    @Size(max = 45, message = "Demasiados caracteres")
    private String correo;
    private String password;
    @NotBlank(message = "Este campo no puede ser vacío")
    @Size(max = 25, message = "Este campo no puede tener mas de 9 dígitos")
    private String telefono;
    @Column(nullable = false)
    private int activo;
    @Column(nullable = false)
    private String tipo;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idusuarios;
    @ManyToOne
    @JoinColumn(name = "idsede")
    private Sede sede;
    private String hasheado;

    public String getHasheado() { return hasheado; }

    public void setHasheado(String hasheado) { this.hasheado = hasheado; }

    public int getIdusuarios() {
        return idusuarios;
    }

    public void setIdusuarios(int idusuarios) {
        this.idusuarios = idusuarios;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public int getActivo() {
        return activo;
    }

    public void setActivo(int activo) {
        this.activo = activo;
    }

    public Sede getSede() {
        return sede;
    }

    public void setSede(Sede sede) {
        this.sede = sede;
    }
}
