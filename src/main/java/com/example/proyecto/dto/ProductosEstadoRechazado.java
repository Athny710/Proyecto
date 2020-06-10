package com.example.proyecto.dto;

import java.time.LocalDate;

public interface ProductosEstadoRechazado {
    int getIdestado();
    int getIdinventariosede();
    String getCodigogenerado();
    String getNombre_sede();
    String getNombre();
    int getCantidad();
    float getPreciomosqoy();
    LocalDate getFecha();
    String getEstado();
    String getComentario();
}
