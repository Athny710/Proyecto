package com.example.proyecto.entity;

import javax.persistence.*;
import java.util.Optional;

@Entity
@Table(name = "inventariosede")
public class Inventariosede {

    @Id
    @Column(name = "idinventariosede")
    private int idinventariosede;
    @Column
    private int stock;
    @ManyToOne
    @JoinColumn(name = "idsede")
    private Sede sede;
    @ManyToOne
    @JoinColumn(name = "idinventario")
    private Inventario inventario;


    public int getIdinventariosede() {
        return idinventariosede;
    }

    public void setIdinventariosede(int idinventariosede) {
        this.idinventariosede = idinventariosede;
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
