package com.example.proyecto.entity;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Positive;

@Entity
@Table(name = "inventario")
public class Inventario {

    @Id
    @JoinColumn(name = "idinventario")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idinventario;
    private int stock;
    private String comentario;
    private String foto;
    private String color;
    @Positive
    private Double preciomosqoy;
    @ManyToOne
    @JoinColumn(name = "idproducto")
    private Producto producto;


    public int getIdInventario() {
        return idinventario;
    }

    public void setIdInventario(int idInventario) {
        this.idinventario = idInventario;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }



    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    public Double getPreciomosqoy() {
        return preciomosqoy;
    }

    public void setPreciomosqoy(Double preciomosqoy) {
        this.preciomosqoy = preciomosqoy;
    }
}
