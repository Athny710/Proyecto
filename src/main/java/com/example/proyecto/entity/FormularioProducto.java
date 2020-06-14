package com.example.proyecto.entity;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class FormularioProducto {
    private int crearActualizar;
    @NotBlank(message = "Este campo no puede estar vacío")
    @Size(max = 45, message = "No puede tener más de 45 caracteres")
    private
    String nombreProducto;
    @NotBlank(message = "Este campo no puede estar vacío")
    @Size(min = 3 ,max = 3, message = "Solo 3 caracteres")
    private
    String codigoProducto;
    @NotBlank(message = "Este campo no puede estar vacío")
    @Size(max = 45, message = "No puede tener más de 45 caracteres")
    private
    String descripcion;
    @NotBlank(message = "Este campo no puede estar vacío")
    @Size(min = 3 ,max = 3, message = "Solo 3 caracteres")
    private
    String codDescripcion;
    private String nombreCategoria;
    private String nombreLinea;
    private String nombreTama;
    private String nombreComun;

    public String getNombreProducto() {
        return nombreProducto;
    }

    public void setNombreProducto(String nombreProducto) {
        this.nombreProducto = nombreProducto;
    }

    public String getCodigoProducto() {
        return codigoProducto;
    }

    public void setCodigoProducto(String codigoProducto) {
        this.codigoProducto = codigoProducto;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getCodDescripcion() {
        return codDescripcion;
    }

    public void setCodDescripcion(String codDescripcion) {
        this.codDescripcion = codDescripcion;
    }

    public String getNombreCategoria() {
        return nombreCategoria;
    }

    public void setNombreCategoria(String nombreCategoria) {
        this.nombreCategoria = nombreCategoria;
    }

    public String getNombreLinea() {
        return nombreLinea;
    }

    public void setNombreLinea(String nombreLinea) {
        this.nombreLinea = nombreLinea;
    }

    public String getNombreTama() {
        return nombreTama;
    }

    public void setNombreTama(String nombreTama) {
        this.nombreTama = nombreTama;
    }

    public String getNombreComun() {
        return nombreComun;
    }

    public void setNombreComun(String nombreComun) {
        this.nombreComun = nombreComun;
    }

    public int getCrearActualizar() {
        return crearActualizar;
    }

    public void setCrearActualizar(int crearActualizar) {
        this.crearActualizar = crearActualizar;
    }
}
