package com.example.proyecto.entity;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "venta")
public class Venta {

    @Id
    private int idventa;
    private String nombrecliente;
    private String tipodocumentoidentidad;
    private String numerodocumentoidentidad;
    private String tipodocumentoventa;
    private int numerodocumentoventa;
    private String lugardeventa;
    private float preciounitarioventa;
    private int cantidad;
    private Date fecha;

    @ManyToOne
    @JoinColumn(name = "idtienda")
    private Tienda tienda;

    @ManyToOne
    @JoinColumn(name = "idinventario")
    private Inventario inventario;

    @ManyToOne
    @JoinColumn(name = "idusuario")
    private Usuarios usuarios;


    public int getIdventa() {
        return idventa;
    }

    public void setIdventa(int idventa) {
        this.idventa = idventa;
    }

    public String getNombrecliente() {
        return nombrecliente;
    }

    public void setNombrecliente(String nombrecliente) {
        this.nombrecliente = nombrecliente;
    }

    public String getTipodocumentoidentidad() {
        return tipodocumentoidentidad;
    }

    public void setTipodocumentoidentidad(String tipodocumentoidentidad) {
        this.tipodocumentoidentidad = tipodocumentoidentidad;
    }

    public String getNumerodocumentoidentidad() {
        return numerodocumentoidentidad;
    }

    public void setNumerodocumentoidentidad(String numerodocumentoidentidad) {
        this.numerodocumentoidentidad = numerodocumentoidentidad;
    }

    public String getTipodocumentoventa() {
        return tipodocumentoventa;
    }

    public void setTipodocumentoventa(String tipodocumentoventa) {
        this.tipodocumentoventa = tipodocumentoventa;
    }

    public int getNumerodocumentoventa() {
        return numerodocumentoventa;
    }

    public void setNumerodocumentoventa(int numerodocumentoventa) {
        this.numerodocumentoventa = numerodocumentoventa;
    }

    public String getLugardeventa() {
        return lugardeventa;
    }

    public void setLugardeventa(String lugardeventa) {
        this.lugardeventa = lugardeventa;
    }

    public float getPreciounitarioventa() {
        return preciounitarioventa;
    }

    public void setPreciounitarioventa(float preciounitarioventa) {
        this.preciounitarioventa = preciounitarioventa;
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

    public Tienda getTienda() {
        return tienda;
    }

    public void setTienda(Tienda tienda) {
        this.tienda = tienda;
    }

    public Inventario getInventario() {
        return inventario;
    }

    public void setInventario(Inventario inventario) {
        this.inventario = inventario;
    }
}
