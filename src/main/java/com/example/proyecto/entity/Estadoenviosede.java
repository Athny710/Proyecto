package com.example.proyecto.entity;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "estadoenviosede")
public class Estadoenviosede {

    @Id
    @Column(name="idEnvioSede")
    private int idenviosede;
    private String estado;
    private int cantidad;
    private Date fecha;
    private String comentario;
    @ManyToOne
    @JoinColumn(name = "idInventarioSede")
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

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
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
}
