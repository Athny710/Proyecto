package com.example.proyecto.repository;

import com.example.proyecto.dto.ProductosEstadoRechazado;
import com.example.proyecto.dto.ProductosEstados;
import com.example.proyecto.dto.ProductosEstadosSede;
import com.example.proyecto.entity.Inventario;
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

    @Query(value = "SELECT p.codigoGenerado,est.cantidad,i.color,i.precioMosqoy,est.fecha,est.estado,est.idEnvioSede,est.comentario\n" +
            "             FROM producto p \n" +
            "                        inner join denominacion d on p.idDenominacion = d.idDenominacion\n" +
            "                        inner join inventario i on p.idProducto = i.idProducto\n" +
            "                        inner join inventariosede invs on i.idInventario = invs.idInventario\n" +
            "                        inner join estadoenviosede est on invs.idInventarioSede = est.idInventarioSede\n" +
            "                        inner join sede s on invs.idSede = s.idSede and est.estado = 'en camino'\n" +
            "                        where s.idSede=?1",nativeQuery = true)
    List<ProductosEstadosSede> listaProductosEnviadosSede(int idSede);


    @Query(value = "SELECT est.idEnvioSede as idestado, est.idInventarioSede as idinventariosede, p.codigoGenerado as codigogenerado,s.nombre as nombre_sede, d.nombre,est.cantidad,i.precioMosqoy,est.fecha,est.estado,est.comentario FROM sw2_proyecto.producto p \n" +
            "inner join sw2_proyecto.denominacion d on p.idDenominacion = d.idDenominacion\n" +
            "inner join sw2_proyecto.inventario i on p.idProducto = i.idProducto\n" +
            "inner join sw2_proyecto.inventariosede invs on i.idInventario = invs.idInventario\n" +
            "inner join sw2_proyecto.estadoenviosede est on invs.idInventarioSede = est.idInventarioSede\n" +
            "inner join sw2_proyecto.sede s on invs.idSede = s.idSede and est.estado = 'En camino a central'",nativeQuery = true)
    List<ProductosEstadoRechazado> listaProductosDevueltos();


    //NOS SERVIRÁ PARA REALIZAR VALIDACION CON RESPECTO AL SCHEDULER Y PARA EL MODAL DE ALERTA
    @Query(value = "SELECT p.codigoGenerado FROM adquisicion a INNER JOIN producto p ON a.idAdquisicion=p.idAdquisicion  INNER JOIN inventario i ON i.idProducto=p.idProducto WHERE i.estado=?1",
            nativeQuery = true)
    List<String> productoPorEstado(String estado);



    // ESTE QUERY NOS DA LA LISTA DE PRODUCTOS QUE ESTAN A UNA SEMANA DE VENCER, ES INDIFERENTE DEL ESTADO QUE TENGAN EN INVENTARIO
    @Query(value = "SELECT p.codigoGenerado FROM adquisicion a INNER JOIN producto p ON a.idAdquisicion=p.idAdquisicion  INNER JOIN inventario i ON i.idProducto=p.idProducto WHERE DATEDIFF(a.fechaFin,CURDATE())<=7;",
            nativeQuery = true)
    List<String> productoAunaSemanaDeVencer();



}
