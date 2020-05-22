package com.example.proyecto.entity;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.time.LocalDate;
import java.util.Date;

@Entity
@Table(name = "venta")
public class Venta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idventa;

    @NotBlank(message = "No puede ser vacío")
    @Size(max = 45, message = "No puede tener mas de 45 caracteres")
    @Column(nullable = false)
    private String nombrecliente;

    private String tipodocumentoidentidad;

    @Positive(message="No puede ser negativo")
    @Digits(integer = 8, fraction = 0, message="No puede ser decimal y debe tener 8 digitos")
    private String numerodocumentoidentidad;

    @Column(nullable = false)
    @NotBlank(message = "No puede ser vacío")
    private String tipodocumentoventa;

    @Positive(message="No puede ser negativo")
    @Digits(integer = 8, fraction = 0, message="No puede ser decimal y debe tener 8 digitos")
    @Column(nullable = false)
    @NotBlank(message = "No puede ser vacío")
    private int numerodocumentoventa;

    private String lugardeventa;

    @Column(nullable = false)
    @Positive(message="No puede ser negativo")
    @Digits(integer = 8, fraction = 0, message="No puede ser decimal y debe tener 8 digitos")
    @NotBlank(message = "No puede ser vacío")
    private float preciounitarioventa;

    @Column(nullable = false)
    @Positive(message="No puede ser negativo")
    @Digits(integer = 8, fraction = 0, message="No puede ser decimal y debe tener 8 digitos")
    @NotBlank(message = "No puede ser vacío")
    private int cantidad;

    @Column(nullable = false)
    @NotBlank(message = "No puede ser vacío")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate fecha;

    @ManyToOne
    @JoinColumn(name = "idtienda")
    private Tienda tienda;

    @ManyToOne
    @JoinColumn(name = "idinventario")
    private Inventario inventario;

    @ManyToOne
    @JoinColumn(name = "idusuarios")
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

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
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

    public Usuarios getUsuarios() {
        return usuarios;
    }

    public void setUsuarios(Usuarios usuarios) {
        this.usuarios = usuarios;
    }
}