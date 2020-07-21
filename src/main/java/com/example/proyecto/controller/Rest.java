package com.example.proyecto.controller;
import com.example.proyecto.dto.AñoDeCompraXFiltro;
import com.example.proyecto.dto.FechaMesVenta;
import com.example.proyecto.repository.VentaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;

@RestController
@CrossOrigin
public class Rest{

    @Autowired
    VentaRepository ventaRepository;

    //localhost:8080/proyecto/reporte/(cliente)
    @GetMapping(value = "/reporte/{cliente}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity obtenerAño(@PathVariable("cliente") String client) {

        HashMap<String, Object> responseMap = new HashMap<>();

            List<AñoDeCompraXFiltro> listaAños = ventaRepository.obtenerAñosXCliente(client);
            if (listaAños.size() >= 1) {
                responseMap.put("estado", "ok");
                responseMap.put("anho", listaAños);
                return new ResponseEntity(responseMap, HttpStatus.OK);
            } else {
                responseMap.put("estado", "error");
                responseMap.put("msg", "no se encontró el cliente con nombre: " + client);
                return new ResponseEntity(responseMap, HttpStatus.BAD_REQUEST);
            }
    }

    //localhost:8080/proyecto/reporte/sede/(sede)
    @GetMapping(value = "/reporte/sede/{sede}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity obtenerAñoSede(@PathVariable("sede") String sede) {

        HashMap<String, Object> responseMap = new HashMap<>();

        List<AñoDeCompraXFiltro> listaAños = ventaRepository.obtenerAñosXSede(sede);
        if (listaAños.size() >= 1) {
            responseMap.put("estado", "ok");
            responseMap.put("anho", listaAños);
            return new ResponseEntity(responseMap, HttpStatus.OK);
        } else {
            responseMap.put("estado", "error");
            responseMap.put("msg", "no se encontró la sede con nombre: " + sede);
            return new ResponseEntity(responseMap, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(value = "/reporte/producto/{producto}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity obtenerAñoProducto(@PathVariable("producto") String producto) {

        HashMap<String, Object> responseMap = new HashMap<>();

        List<AñoDeCompraXFiltro> listaAños = ventaRepository.obtenerAñosXProducto(producto);
        if (listaAños.size() >= 1) {
            responseMap.put("estado", "ok");
            responseMap.put("anho", listaAños);
            return new ResponseEntity(responseMap, HttpStatus.OK);
        } else {
            responseMap.put("estado", "error");
            responseMap.put("msg", "no se encontró el producto con nombre: " + producto);
            return new ResponseEntity(responseMap, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(value = "/reporte/comunidad/{comunidad}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity obtenerAñoComunidad(@PathVariable("comunidad") String comunidad) {

        HashMap<String, Object> responseMap = new HashMap<>();

        List<AñoDeCompraXFiltro> listaAños = ventaRepository.obtenerAñosXComunidad(comunidad);
        System.out.println(listaAños.get(0));
        if (listaAños.size() >= 1) {
            responseMap.put("estado", "ok");
            responseMap.put("anho", listaAños);
            return new ResponseEntity(responseMap, HttpStatus.OK);
        } else {
            responseMap.put("estado", "error");
            responseMap.put("msg", "no se encontró la comunidad con nombre: " + comunidad);
            return new ResponseEntity(responseMap, HttpStatus.BAD_REQUEST);
        }
    }



    /////////////////////// 3er FILTRO !! (año + 2do filtro) ///////////////////////////////////


    //localhost:8080/proyecto/reporte/(año)/(cliente)
    @GetMapping(value = "/reporte/{anho}/{cliente}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity obtenerPeriodo(@PathVariable("anho") String anho, @PathVariable("cliente") String cliente) {

        HashMap<String, Object> responseMap = new HashMap<>();
        List<FechaMesVenta> listaMes = ventaRepository.obtenerPeriodoXAño(cliente, anho);
        if (listaMes != null) {
            responseMap.put("estado", "ok");
            responseMap.put("periodo", listaMes);
            return new ResponseEntity(responseMap, HttpStatus.OK);
        } else {
            responseMap.put("estado", "error");
            responseMap.put("msg", "no se encontró un registro con la busqueda cliente-año igual a: " + cliente + "-" + anho);
            return new ResponseEntity(responseMap, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(value = "/reporte/sede/{anho}/{sede}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity obtenerPeriodoSede(@PathVariable("anho") String anho, @PathVariable("sede") String sede) {

        HashMap<String, Object> responseMap = new HashMap<>();
        List<FechaMesVenta> listaMes = ventaRepository.obtenerPeriodoXAñoSede(sede, anho);
        if (listaMes != null) {
            responseMap.put("estado", "ok");
            responseMap.put("periodo", listaMes);
            return new ResponseEntity(responseMap, HttpStatus.OK);
        } else {
            responseMap.put("estado", "error");
            responseMap.put("msg", "no se encontró un registro con la busqueda sede-año igual a: " + sede + "-" + anho);
            return new ResponseEntity(responseMap, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(value = "/reporte/producto/{anho}/{producto}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity obtenerPeriodoProducto(@PathVariable("anho") String anho, @PathVariable("producto") String producto) {

        HashMap<String, Object> responseMap = new HashMap<>();
        List<FechaMesVenta> listaMes = ventaRepository.obtenerPeriodoXAñoProducto(producto, anho);
        if (listaMes != null) {
            responseMap.put("estado", "ok");
            responseMap.put("periodo", listaMes);
            return new ResponseEntity(responseMap, HttpStatus.OK);
        } else {
            responseMap.put("estado", "error");
            responseMap.put("msg", "no se encontró un registro con la busqueda sede-año igual a: " + producto + "-" + anho);
            return new ResponseEntity(responseMap, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(value = "/reporte/comunidad/{anho}/{comunidad}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity obtenerPeriodoComunidad(@PathVariable("anho") String anho, @PathVariable("comunidad") String comunidad) {

        HashMap<String, Object> responseMap = new HashMap<>();
        List<FechaMesVenta> listaMes = ventaRepository.obtenerPeriodoXAñoComunidad(comunidad, anho);
        if (listaMes != null) {
            responseMap.put("estado", "ok");
            responseMap.put("periodo", listaMes);
            return new ResponseEntity(responseMap, HttpStatus.OK);
        } else {
            responseMap.put("estado", "error");
            responseMap.put("msg", "no se encontró un registro con la busqueda sede-año igual a: " + comunidad + "-" + anho);
            return new ResponseEntity(responseMap, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(value = "/reporte/total/{anho}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity obtenerPeriodoParaReporteTotal(@PathVariable("anho") String anho) {

        HashMap<String, Object> responseMap = new HashMap<>();
        List<FechaMesVenta> listaMes = ventaRepository.obtenerPeriodoParaReporteTotal(anho);
        if (listaMes != null) {
            responseMap.put("estado", "ok");
            responseMap.put("periodo", listaMes);
            return new ResponseEntity(responseMap, HttpStatus.OK);
        } else {
            responseMap.put("estado", "error");
            responseMap.put("msg", "no se encontró un registro en el año: " + anho);
            return new ResponseEntity(responseMap, HttpStatus.BAD_REQUEST);
        }
    }

}











