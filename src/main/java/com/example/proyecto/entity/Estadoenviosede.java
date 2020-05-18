package com.example.proyecto.entity;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.util.Date;

public class Estadoenviosede {

    @Id
    private int idEnvioSede;
    @Column
    private String estado;
    @Column
    private int cantidad;
    @Column
    private Date fecha;
    @Column
    private String comentario;
    @ManyToOne
    @JoinColumn(name = "idInventariosede")
    private Inventariosede inventarioSede;

    public int getIdEnvioSede() {
        return idEnvioSede;
    }

    public void setIdEnvioSede(int idEnvioSede) {
        this.idEnvioSede = idEnvioSede;
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

    public Inventariosede getInventarioSede() {
        return inventarioSede;
    }

    public void setInventarioSede(Inventariosede inventarioSede) {
        this.inventarioSede = inventarioSede;
    }
}
