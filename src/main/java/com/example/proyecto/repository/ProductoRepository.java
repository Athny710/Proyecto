package com.example.proyecto.repository;

import com.example.proyecto.dto.ProductosEstadoRechazado;
import com.example.proyecto.dto.ProductosEstados;
import com.example.proyecto.dto.ProductosEstadosSede;
import com.example.proyecto.entity.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductoRepository extends JpaRepository<Producto, Integer> {

    @Query(value = "SELECT p.codigoGenerado as codigogenerado,s.nombre as nombre_sede,d.nombre,est.cantidad,i.precioMosqoy,est.fecha,est.estado FROM producto p \n" +
            "inner join denominacion d on p.idDenominacion = d.idDenominacion\n" +
            "inner join inventario i on p.idProducto = i.idProducto\n" +
            "inner join inventariosede invs on i.idInventario = invs.idInventario\n" +
            "inner join estadoenviosede est on invs.idInventarioSede = est.idInventarioSede\n" +
            "inner join sede s on invs.idSede = s.idSede and est.estado = 'en camino'",nativeQuery = true)
    List<ProductosEstados> listaProductosEnviados();

    @Query(value = "SELECT p.codigoGenerado as codigogenerado,s.nombre as nombre_sede,d.nombre,est.cantidad,i.precioMosqoy,est.fecha,est.estado FROM producto p \n" +
            "inner join denominacion d on p.idDenominacion = d.idDenominacion\n" +
            "inner join inventario i on p.idProducto = i.idProducto\n" +
            "inner join inventariosede invs on i.idInventario = invs.idInventario\n" +
            "inner join estadoenviosede est on invs.idInventarioSede = est.idInventarioSede\n" +
            "inner join sede s on invs.idSede = s.idSede and est.estado = 'recibido'",nativeQuery = true)
    List<ProductosEstados> listaProductosRecibidos();

    @Query(value = "SELECT est.idEnvioSede as idestado, est.idInventarioSede as idinventariosede, p.codigoGenerado as codigogenerado,s.nombre as nombre_sede, d.nombre,est.cantidad,i.precioMosqoy,est.fecha,est.estado,est.comentario FROM sw2_proyecto.producto p \n" +
            "inner join sw2_proyecto.denominacion d on p.idDenominacion = d.idDenominacion\n" +
            "inner join sw2_proyecto.inventario i on p.idProducto = i.idProducto\n" +
            "inner join sw2_proyecto.inventariosede invs on i.idInventario = invs.idInventario\n" +
            "inner join sw2_proyecto.estadoenviosede est on invs.idInventarioSede = est.idInventarioSede\n" +
            "inner join sw2_proyecto.sede s on invs.idSede = s.idSede and est.estado = 'rechazado'",nativeQuery = true)
    List<ProductosEstadoRechazado> listaProductosRechazados();

    @Query(value = "SELECT p.codigoGenerado,est.cantidad,i.color,i.precioMosqoy,est.fecha,est.estado,est.idEnvioSede\n" +
            "             FROM producto p \n" +
            "                        inner join denominacion d on p.idDenominacion = d.idDenominacion\n" +
            "                        inner join inventario i on p.idProducto = i.idProducto\n" +
            "                        inner join inventariosede invs on i.idInventario = invs.idInventario\n" +
            "                        inner join estadoenviosede est on invs.idInventarioSede = est.idInventarioSede\n" +
            "                        inner join sede s on invs.idSede = s.idSede and est.estado = 'en camino'\n" +
            "                        where s.idSede=?1",nativeQuery = true)
    List<ProductosEstadosSede> listaProductosEnviadosSede(int idSede);

}
