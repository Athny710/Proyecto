package com.example.proyecto.controller;
import com.example.proyecto.dto.AñoDeCompraXCliente;
import com.example.proyecto.dto.FechaMesVenta;
import com.example.proyecto.repository.VentaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@RestController
public class Rest{

    @Autowired
    VentaRepository ventaRepository;

    //localhost:8080/proyecto/reporte/(cliente)
    @GetMapping(value = "/reporte/{cliente}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity obtenerAño(@PathVariable("cliente") String client) {

        HashMap<String, Object> responseMap = new HashMap<>();

            List<AñoDeCompraXCliente> listaAños = ventaRepository.obtenerAñosXCliente(client);
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

    //localhost:8080/proyecto/reporte/(año)/(cliente)
    @GetMapping(value = "/reporte/{anho}/{cliente}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity obtenerAño(@PathVariable("anho") String anho, @PathVariable("cliente") String cliente) {

        HashMap<String, Object> responseMap = new HashMap<>();

        List<FechaMesVenta> listaMes = ventaRepository.obtenerPeriodoXAño(cliente, anho);
        if (listaMes != null) {
            responseMap.put("estado", "ok");
            responseMap.put("meses", listaMes);
            return new ResponseEntity(responseMap, HttpStatus.OK);
        } else {
            responseMap.put("estado", "error");
            responseMap.put("msg", "no se encontró un registro con la busqueda cliente-año igual a: " + cliente + "-" + anho);
            return new ResponseEntity(responseMap, HttpStatus.BAD_REQUEST);
        }
    }

}
