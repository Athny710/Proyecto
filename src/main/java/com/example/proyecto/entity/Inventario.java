package com.example.proyecto.entity;

import javax.persistence.*;

@Entity
@Table(name = "inventario")
public class Inventario {

    @Id
    private int idinventario;
    private int stock;
    private String comentario;
    private String foto;
    private String color;
    private String preciomosqoy;
    private String costotejedor;
    private String numeropedido;
    private String facilitador;
    private String fecha;
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

    public String getPrecioMosqoy() {
        return preciomosqoy;
    }

    public void setPrecioMosqoy(String precioMosqoy) {
        this.preciomosqoy = precioMosqoy;
    }

    public String getCostoTejedor() {
        return costotejedor;
    }

    public void setCostoTejedor(String costoTejedor) {
        this.costotejedor = costoTejedor;
    }

    public String getNumeroPedido() {
        return numeropedido;
    }

    public void setNumeroPedido(String numeroPedido) {
        this.numeropedido = numeroPedido;
    }

    public String getFacilitador() {
        return facilitador;
    }

    public void setFacilitador(String facilitador) {
        this.facilitador = facilitador;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }
}
