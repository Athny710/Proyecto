package com.example.proyecto.entity;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;

@Entity
@Table(name = "historial")
public class Historial {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JoinColumn(name = "idhistorial")
    private
    int idhistorial;

    @Size(max = 45,message = "No puede sobrepasar los 45 caracteres")
    private String facilitador;

    @Digits(integer = 5,fraction = 2,message = "Solo puede tener 4 enteros y 2 decimales")
    @Positive(message = "no puede ser negativo")
    @NotNull(message = "por favor ingrese el costo pagado al tejedor")
    private Double costotejedor;

    //@Positive(message = "No puede ser negativo")
    //@NotNull(message = "por favor ingrese un numero de pedido")
    private Integer numeropedido;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @NotNull(message = "por favor ingrese una fecha")
    private LocalDate fecha;

    @Positive(message = "No puede ser negativo")
    @NotNull(message = "por favor ingrese la cantidad comprada")
    private Integer cantidad;

    @ManyToOne
    @JoinColumn(name = "idinventario")
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

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }
}
