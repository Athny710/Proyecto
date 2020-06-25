package com.example.proyecto.services;

import com.example.proyecto.dto.CamposReporteSede;
import com.example.proyecto.dto.ReporteConCamposOriginales;
import com.example.proyecto.dto.VentaPorCodigo;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public interface VentasService {
    List<CamposReporteSede> getVentas(int id);
    List<VentaPorCodigo> getVentasPorCodigo(String codigo);
    List<ReporteConCamposOriginales> getVentasPorCliente(String mes, String año, String cliente);
    List<ReporteConCamposOriginales> getVentasPorClienteAnual(String año, String cliente);
    List<ReporteConCamposOriginales> getVentasPorClienteTrimestral(String año, String cliente);
    List<ReporteConCamposOriginales> getVentasPorSede(String mes, String año, String idsede);
    List<ReporteConCamposOriginales> getVentasPorSedeTrimestral(String año, String idsede);
    List<ReporteConCamposOriginales> getVentasPorSedeAnual(String año, String idsede);
    List<ReporteConCamposOriginales> getVentasPorArticuloAnual(String año, String articulo);
    List<ReporteConCamposOriginales> getVentasPorArticuloTrimestral(String año, String articulo);
    List<ReporteConCamposOriginales> getVentasPorArticuloMensual(String mes, String año, String articulo);
    List<ReporteConCamposOriginales> getVentasPorComunidadAnual(String año, String comunidad);
    List<ReporteConCamposOriginales> getVentasPorComunidadTrimestral(String año, String comunidad);
    List<ReporteConCamposOriginales> getVentasPorComunidadMensual(String mes, String año, String comunidad);
    List<ReporteConCamposOriginales> getVentaAnual(String año);
    List<ReporteConCamposOriginales> getVentaTrimestral(String año);
    List<ReporteConCamposOriginales> getVentaMensual(String mes, String año);

    boolean createPDF(List<CamposReporteSede> venta, ServletContext context, HttpServletRequest request, HttpServletResponse response);
    boolean createExcel(List<CamposReporteSede> venta, ServletContext context, HttpServletRequest request, HttpServletResponse response);
    boolean createExcelXCodigo(List<VentaPorCodigo> venta, ServletContext context, HttpServletRequest request, HttpServletResponse response);
    boolean createExcelXCliente(List<ReporteConCamposOriginales> ventaXCliente,String cliente, ServletContext context, HttpServletRequest request, HttpServletResponse response);
    boolean createExcelXSede(List<ReporteConCamposOriginales> ventaXSedeAnual, ServletContext context, HttpServletRequest request, HttpServletResponse response);



}

