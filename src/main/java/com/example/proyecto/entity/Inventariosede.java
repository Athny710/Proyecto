package com.example.proyecto.entity;

import javax.persistence.*;

@Entity
@Table(name = "inventariosede")
public class Inventariosede {

    @Id
    @Column(name = "idInventarioSede")
    private int idinventariosede;
    @Column
    private int stock;
    @ManyToOne
    @JoinColumn(name = "idSede")
    private Sede sede;
    @ManyToOne
    @JoinColumn(name = "idInventario")
    private Inventario inventario;



    public int getIdinventariosede() {
        return idinventariosede;
    }

    public void setIdinventariosede(int idInventarioSede) {
        this.idinventariosede = idInventarioSede;
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
