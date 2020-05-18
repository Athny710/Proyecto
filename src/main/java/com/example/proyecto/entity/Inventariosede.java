package com.example.proyecto.entity;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

public class Inventariosede {

    @Id
    private int idInventarioSede;
    @Column
    private int stock;
    @ManyToOne
    @JoinColumn(name = "idSede")
    private Sede sede;
    @ManyToOne
    @JoinColumn(name = "idInventario")
    private Inventario inventario;

    public int getIdInventarioSede() {
        return idInventarioSede;
    }

    public void setIdInventarioSede(int idInventarioSede) {
        this.idInventarioSede = idInventarioSede;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public Sede getSede() {
        return sede;
    }

    public void setSede(Sede sede) {
        this.sede = sede;
    }

    public Inventario getInventario() {
        return inventario;
    }

    public void setInventario(Inventario inventario) {
        this.inventario = inventario;
    }
}
