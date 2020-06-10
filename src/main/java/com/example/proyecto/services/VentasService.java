package com.example.proyecto.services;

import com.example.proyecto.entity.Venta;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public interface VentasService {
    List<Venta> getVentas();

    boolean createPDF(List<Venta> venta, ServletContext context, HttpServletRequest request, HttpServletResponse response);

    boolean createExcel(List<Venta> venta, ServletContext context, HttpServletRequest request, HttpServletResponse response);
}
