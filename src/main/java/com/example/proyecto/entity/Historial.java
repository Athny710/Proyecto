package com.example.proyecto.entity;

import javax.persistence.*;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.util.Date;

@Entity
public class Historial {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JoinColumn(name = "idHistorial")
    private
    int idhistorial;
    @Size(max = 45,message = "No puede sobrepasar los 45 caracteres")
    private String facilitador;
    @Digits(integer = 5,fraction = 2,message = "Solo puede tener 4 enteros y 2 decimales")
    @Positive(message = "no puede ser negativo")
    private Double costotejedor;
    @NotBlank(message = "No puede ser vacío")
    @Positive(message = "No puede ser negativo")
    private Integer numeropedido;
    private Date fecha;
    @NotBlank(message = "No puede ser vacío")
    @Positive(message = "No puede ser negativo")
    private Integer cantidad;
    @ManyToOne
    @JoinColumn(name = "idInventario")
    private Inventario inventario;

    public int getIdhistorial() {
        return idhistorial;
    }

    public void setIdhistorial(int idhistorial) {
        this.idhistorial = idhistorial;
    }

    public String getFacilitador() {
        return facilitador;
    }

    public void setFacilitador(String facilitador) {
        this.facilitador = facilitador;
    }

    public Double getCostotejedor() {
        return costotejedor;
    }

    public void setCostotejedor(Double costotejedor) {
        this.costotejedor = costotejedor;
    }

    public Integer getNumeropedido() {
        return numeropedido;
    }

    public void setNumeropedido(Integer numeropedido) {
        this.numeropedido = numeropedido;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    public Inventario getInventario() {
        return inventario;
    }

    public void setInventario(Inventario inventario) {
        this.inventario = inventario;
    }
}
