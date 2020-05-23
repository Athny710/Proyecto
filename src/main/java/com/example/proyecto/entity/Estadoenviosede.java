package com.example.proyecto.entity;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Date;

@Entity
@Table(name = "estadoenviosede")
public class Estadoenviosede {

    @Id
    @Column(name="idenviosede")
    private int idenviosede;
    private String estado;
    private int cantidad;
    @Column(nullable = false)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate fecha;
    private String comentario;
    @ManyToOne
    @JoinColumn(name = "idinventariosede")
    private Inventariosede inventariosede;

    public int getIdenviosede() {
        return idenviosede;
    }

    public void setIdenviosede(int idEnvioSede) {
        this.idenviosede = idEnvioSede;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }



    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public Inventariosede getInventariosede() {
        return inventariosede;
    }

    public void setInventariosede(Inventariosede inventarioSede) {
        this.inventariosede = inventarioSede;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }
}
