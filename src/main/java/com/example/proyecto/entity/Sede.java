package com.example.proyecto.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "sede")
public class Sede implements Serializable    {
    @Id
    private int idsede;
    private String nombre;

    public int getIdsede() {
        return idsede;
    }

    public void setIdsede(int idsede) {
        this.idsede = idsede;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}