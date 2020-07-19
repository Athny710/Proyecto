package com.example.proyecto.repository;

import com.example.proyecto.dto.*;
import com.example.proyecto.entity.Tienda;
import com.example.proyecto.entity.Venta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VentaRepository extends JpaRepository<Venta, Integer> {

    @Query(value = "select * from venta where nombreCliente = ?1 ",
            nativeQuery = true)
    List<Venta> buscarPorNombre(String nombre);


    @Query(value = "select * from venta where nombreCliente = ?1 ",
            nativeQuery = true)
    List<Venta> buscarPorSede(int sedeid);

    @Query(value = "select v.nombreCliente as nombre from venta v\n" +
            "inner join tienda t on v.idTienda = t.idTienda\n" +
            "inner join sede s on t.idSede = s.idSede and s.idSede = ?1 and t.idTienda = ?2\n" +
            "order by v.fecha", nativeQuery = true)
    List<ClientesQueCompraron> obtenerVentaPorTiendaYSede(int idsede, int idtienda);

    List<Venta> findByTienda(Tienda tienda);

    @Query(value = "select v.numeroDocumentoIdentidad as dnioruc, v.nombreCliente as cliente, v.numeroDocumentoVenta as numerodoc, v.lugarDeVenta as lugar, p.codigoGenerado as codgen,\n" +
            "v.precioUnitarioVenta as preciounit, v.fecha as fecha, v.Cantidad as cantidad from venta v\n" +
            "inner join inventario inv on v.idInventario = inv.idInventario\n" +
            "inner join producto p on inv.idProducto = p.idProducto\n" +
            "inner join denominacion d on p.idDenominacion = d.idDenominacion and p.codigoGenerado = ?1", nativeQuery = true)
    List<VentaPorCodigo> listarVentasXCodigo(String codigo);

    @Query(value = "select v.numeroDocumentoIdentidad as dnioruc, v.nombreCliente as cliente, v.numeroDocumentoVenta as numerodoc, v.lugarDeVenta as lugar, p.codigoGenerado as codgen,\n" +
            "v.precioUnitarioVenta as preciounit, v.fecha as fecha, v.Cantidad as cantidad from venta v\n" +
            "inner join inventario inv on v.idInventario = inv.idInventario\n" +
            "inner join producto p on inv.idProducto = p.idProducto\n" +
            "inner join denominacion d on p.idDenominacion = d.idDenominacion and d.nombre = ?1", nativeQuery = true)
    List<VentasXNombreDeProducto> listarVentasXNombre(String nombreProduct);

    @Query(value = "select v.fecha as fecha, v.tipoDocumentoVenta as tipodocventa, v.numeroDocumentoVenta as numerodocventa, v.numeroDocumentoIdentidad as numerodocid, v.nombreCliente as nombrecliente, d.codigoNombre as codprod, inv.color as colorprod, p.codigoGenerado as codgen, d.nombre as nombreproduct, v.Cantidad as cantidad, v.precioUnitarioVenta as preciounit, monthname(v.fecha) as fech, v.Cantidad*v.precioUnitarioVenta as totalxproduct from venta v\n" +
            "inner join inventario inv on v.idInventario = inv.idInventario\n" +
            "inner join producto p on inv.idProducto = p.idProducto\n" +
            "inner join denominacion d on p.idDenominacion = d.idDenominacion and month(v.fecha) = ?1 and year(v.fecha) = ?2 and v.nombreCliente = ?3\n" +
            "order by v.fecha asc", nativeQuery = true)
    List<ReporteConCamposOriginales> reporteMensualCliente(String mes, String año, String cliente);

    @Query(value = "select v.fecha as fecha, v.tipoDocumentoVenta as tipodocventa, v.numeroDocumentoVenta as numerodocventa, v.numeroDocumentoIdentidad as numerodocid, v.nombreCliente as nombrecliente, d.codigoNombre as codprod, inv.color as colorprod,p.codigoGenerado as codgen, d.nombre as nombreproduct, v.Cantidad as cantidad, v.precioUnitarioVenta as preciounit, monthname(v.fecha) as fech, v.Cantidad*v.precioUnitarioVenta as totalxproduct from venta v\n" +
            "inner join inventario inv on v.idInventario = inv.idInventario\n" +
            "inner join producto p on inv.idProducto = p.idProducto\n" +
            "inner join denominacion d on p.idDenominacion = d.idDenominacion and year(v.fecha) = ?1 and v.nombreCliente = ?2\n" +
            "order by v.fecha asc", nativeQuery = true)
    List<ReporteConCamposOriginales> reporteAnualCliente(String año, String cliente);

    @Query(value = "select v.fecha as fecha, v.tipoDocumentoVenta as tipodocventa, v.numeroDocumentoVenta as numerodocventa, v.numeroDocumentoIdentidad as numerodocid, v.nombreCliente as nombrecliente, d.codigoNombre as codprod, inv.color as colorprod,p.codigoGenerado as codgen, d.nombre as nombreproduct, v.Cantidad as cantidad, v.precioUnitarioVenta as preciounit, \n" +
            "CASE \n" +
            "    WHEN month(v.fecha) BETWEEN 1 AND 3 THEN '1er trimestre (enero - febrero - marzo)' \n" +
            "    WHEN month(v.fecha) BETWEEN 4 AND 6 THEN '2do trimestre (abril - mayo - junio)'\n" +
            "    WHEN month(v.fecha) BETWEEN 7 AND 9 THEN '3er trimestre (julio - agosto - setiembre)'\n" +
            "    WHEN month(v.fecha) BETWEEN 10 AND 12 THEN '4to trimestre (octubre - noviembre - diciembre)'\n" +
            "END as fech , v.Cantidad*v.precioUnitarioVenta as totalxproduct\n" +
            "from venta v\n" +
            "inner join inventario inv on v.idInventario = inv.idInventario\n" +
            "inner join producto p on inv.idProducto = p.idProducto\n" +
            "inner join denominacion d on p.idDenominacion = d.idDenominacion and year(v.fecha) = ?1 and v.nombreCliente = ?2 \n" +
            "order by fech asc", nativeQuery = true)
    List<ReporteConCamposOriginales> reporteTrimestralCliente(String año, String cliente);

    @Query(value = "select t.nombre as tienda, v.fecha as fecha, v.tipoDocumentoVenta as tipodocventa, v.numeroDocumentoVenta as numerodocventa, v.numeroDocumentoIdentidad as numerodocid, v.nombreCliente as nombrecliente, d.codigoNombre as codprod, inv.color as colorprod,p.codigoGenerado as codgen, d.nombre as nombreproduct, v.Cantidad as cantidad, v.precioUnitarioVenta as preciounit, monthname(v.fecha) as fech, v.Cantidad*v.precioUnitarioVenta as totalxproduct from venta v\n" +
            "inner join inventario inv on v.idInventario = inv.idInventario\n" +
            "inner join producto p on inv.idProducto = p.idProducto\n" +
            "inner join denominacion d on p.idDenominacion = d.idDenominacion\n" +
            "inner join tienda t on v.idTienda = t.idTienda\n" +
            "inner join sede s on t.idSede = s.idSede and month(v.fecha) = ?1 and year(v.fecha) = ?2 and s.nombre = ?3\n" +
            "order by v.fecha asc", nativeQuery = true)
    List<ReporteConCamposOriginales> reporteMensualSede(String mes, String año, String idsede);

    @Query(value = "select t.nombre as tienda, v.fecha as fecha, v.tipoDocumentoVenta as tipodocventa, v.numeroDocumentoVenta as numerodocventa, v.numeroDocumentoIdentidad as numerodocid, v.nombreCliente as nombrecliente, d.codigoNombre as codprod, inv.color as colorprod,p.codigoGenerado as codgen, d.nombre as nombreproduct, v.Cantidad as cantidad, v.precioUnitarioVenta as preciounit, monthname(v.fecha) as fech, v.Cantidad*v.precioUnitarioVenta as totalxproduct from venta v\n" +
            "inner join inventario inv on v.idInventario = inv.idInventario\n" +
            "inner join producto p on inv.idProducto = p.idProducto\n" +
            "inner join denominacion d on p.idDenominacion = d.idDenominacion\n" +
            "inner join tienda t on v.idTienda = t.idTienda\n" +
            "inner join sede s on t.idSede = s.idSede and year(v.fecha) = ?1 and s.nombre = ?2\n" +
            "order by v.fecha asc", nativeQuery = true)
    List<ReporteConCamposOriginales> reporteAnualSede(String año, String idsede);

    @Query(value = "select t.nombre as tienda, v.fecha as fecha, v.tipoDocumentoVenta as tipodocventa, v.numeroDocumentoVenta as numerodocventa, v.numeroDocumentoIdentidad as numerodocid, v.nombreCliente as nombrecliente, d.codigoNombre as codprod, inv.color as colorprod,p.codigoGenerado as codgen, d.nombre as nombreproduct, v.Cantidad as cantidad, v.precioUnitarioVenta as preciounit, \n" +
            "CASE \n" +
            "    WHEN month(v.fecha) BETWEEN 1 AND 3 THEN '1er trimestre (enero - febrero - marzo)' \n" +
            "    WHEN month(v.fecha) BETWEEN 4 AND 6 THEN '2do trimestre (abril - mayo - junio)'\n" +
            "    WHEN month(v.fecha) BETWEEN 7 AND 9 THEN '3er trimestre (julio - agosto - setiembre)'\n" +
            "    WHEN month(v.fecha) BETWEEN 10 AND 12 THEN '4to trimestre (octubre - noviembre - diciembre)'\n" +
            "END as fech , v.Cantidad*v.precioUnitarioVenta as totalxproduct from venta v\n" +
            "inner join inventario inv on v.idInventario = inv.idInventario\n" +
            "inner join producto p on inv.idProducto = p.idProducto\n" +
            "inner join denominacion d on p.idDenominacion = d.idDenominacion\n" +
            "inner join tienda t on v.idTienda = t.idTienda\n" +
            "inner join sede s on t.idSede = s.idSede and year(v.fecha) = ?1 and s.nombre = ?2\n" +
            "order by v.fecha asc", nativeQuery = true)
    List<ReporteConCamposOriginales> reporteTrimestralSede(String año, String idsede);

    @Query(value = "select v.fecha as fecha, v.tipoDocumentoVenta as tipodocventa, v.numeroDocumentoVenta as numerodocventa, v.numeroDocumentoIdentidad as numerodocid, v.nombreCliente as nombrecliente, d.codigoNombre as codprod, inv.color as colorprod,p.codigoGenerado as codgen, d.nombre as nombreproduct, v.Cantidad as cantidad, v.precioUnitarioVenta as preciounit, monthname(v.fecha) as fech, v.Cantidad*v.precioUnitarioVenta as totalxproduct from venta v\n" +
            "inner join inventario inv on v.idInventario = inv.idInventario\n" +
            "inner join producto p on inv.idProducto = p.idProducto\n" +
            "inner join denominacion d on p.idDenominacion = d.idDenominacion and month(v.fecha) = ?1 and year(v.fecha) = ?2 and d.nombre = ?3\n" +
            "order by v.fecha asc", nativeQuery = true)
    List<ReporteConCamposOriginales> reporteMensualArticulo(String mes, String año, String articulo);

    @Query(value = "select v.fecha as fecha, v.tipoDocumentoVenta as tipodocventa, v.numeroDocumentoVenta as numerodocventa, v.numeroDocumentoIdentidad as numerodocid, v.nombreCliente as nombrecliente, d.codigoNombre as codprod, inv.color as colorprod,p.codigoGenerado as codgen, d.nombre as nombreproduct, v.Cantidad as cantidad, v.precioUnitarioVenta as preciounit, monthname(v.fecha) as fech, v.Cantidad*v.precioUnitarioVenta as totalxproduct from venta v\n" +
            "inner join inventario inv on v.idInventario = inv.idInventario\n" +
            "inner join producto p on inv.idProducto = p.idProducto\n" +
            "inner join denominacion d on p.idDenominacion = d.idDenominacion and year(v.fecha) = ?1 and d.nombre = ?2\n" +
            "order by v.fecha asc", nativeQuery = true)
    List<ReporteConCamposOriginales> reporteAnualArticulo(String año, String articulo);

    @Query(value = "select v.fecha as fecha, v.tipoDocumentoVenta as tipodocventa, v.numeroDocumentoVenta as numerodocventa, v.numeroDocumentoIdentidad as numerodocid, v.nombreCliente as nombrecliente, d.codigoNombre as codprod, inv.color as colorprod,p.codigoGenerado as codgen, d.nombre as nombreproduct, v.Cantidad as cantidad, v.precioUnitarioVenta as preciounit, \n" +
            "CASE \n" +
            "    WHEN month(v.fecha) BETWEEN 1 AND 3 THEN '1er trimestre (enero - febrero - marzo)' \n" +
            "    WHEN month(v.fecha) BETWEEN 4 AND 6 THEN '2do trimestre (abril - mayo - junio)'\n" +
            "    WHEN month(v.fecha) BETWEEN 7 AND 9 THEN '3er trimestre (julio - agosto - setiembre)'\n" +
            "    WHEN month(v.fecha) BETWEEN 10 AND 12 THEN '4to trimestre (octubre - noviembre - diciembre)'\n" +
            "END as fech , v.Cantidad*v.precioUnitarioVenta as totalxproduct\n" +
            "from venta v\n" +
            "inner join inventario inv on v.idInventario = inv.idInventario\n" +
            "inner join producto p on inv.idProducto = p.idProducto\n" +
            "inner join denominacion d on p.idDenominacion = d.idDenominacion and year(v.fecha) = ?1 and d.nombre = ?2 \n" +
            "order by fech asc", nativeQuery = true)
    List<ReporteConCamposOriginales> reporteTrimestralArticulo(String año, String articulo);

    @Query(value = "select v.fecha as fecha, v.tipoDocumentoVenta as tipodocventa, v.numeroDocumentoVenta as numerodocventa, v.numeroDocumentoIdentidad as numerodocid, v.nombreCliente as nombrecliente, d.codigoNombre as codprod, inv.color as colorprod,p.codigoGenerado as codgen, d.nombre as nombreproduct, v.Cantidad as cantidad, v.precioUnitarioVenta as preciounit, monthname(v.fecha) as fech, v.Cantidad*v.precioUnitarioVenta as totalxproduct from venta v\n" +
            "inner join inventario inv on v.idInventario = inv.idInventario\n" +
            "inner join producto p on inv.idProducto = p.idProducto\n" +
            "inner join denominacion d on p.idDenominacion = d.idDenominacion \n" +
            "inner join comunidad c on c.idComunidad = p.idComunidad and month(v.fecha) = ?1 and year(v.fecha) = ?2 and c.nombre = ?3\n" +
            "order by v.fecha asc", nativeQuery = true)
    List<ReporteConCamposOriginales> reporteMensualComunidad(String mes, String año, String articulo);

    @Query(value = "select v.fecha as fecha, v.tipoDocumentoVenta as tipodocventa, v.numeroDocumentoVenta as numerodocventa, v.numeroDocumentoIdentidad as numerodocid, v.nombreCliente as nombrecliente, d.codigoNombre as codprod, inv.color as colorprod,p.codigoGenerado as codgen, d.nombre as nombreproduct, v.Cantidad as cantidad, v.precioUnitarioVenta as preciounit, monthname(v.fecha) as fech, v.Cantidad*v.precioUnitarioVenta as totalxproduct from venta v\n" +
            "inner join inventario inv on v.idInventario = inv.idInventario\n" +
            "inner join producto p on inv.idProducto = p.idProducto\n" +
            "inner join denominacion d on p.idDenominacion = d.idDenominacion \n" +
            "inner join comunidad c on c.idComunidad = p.idComunidad and year(v.fecha) = ?1 and c.nombre = ?2\n" +
            "order by v.fecha asc", nativeQuery = true)
    List<ReporteConCamposOriginales> reporteAnualComunidad(String año, String articulo);

    @Query(value = "select v.fecha as fecha, v.tipoDocumentoVenta as tipodocventa, v.numeroDocumentoVenta as numerodocventa, v.numeroDocumentoIdentidad as numerodocid, v.nombreCliente as nombrecliente, d.codigoNombre as codprod, inv.color as colorprod,p.codigoGenerado as codgen, d.nombre as nombreproduct, v.Cantidad as cantidad, v.precioUnitarioVenta as preciounit, \n" +
            "CASE \n" +
            "    WHEN month(v.fecha) BETWEEN 1 AND 3 THEN '1er trimestre (enero - febrero - marzo)' \n" +
            "    WHEN month(v.fecha) BETWEEN 4 AND 6 THEN '2do trimestre (abril - mayo - junio)'\n" +
            "    WHEN month(v.fecha) BETWEEN 7 AND 9 THEN '3er trimestre (julio - agosto - setiembre)'\n" +
            "    WHEN month(v.fecha) BETWEEN 10 AND 12 THEN '4to trimestre (octubre - noviembre - diciembre)'\n" +
            "END as fech , v.Cantidad*v.precioUnitarioVenta as totalxproduct\n" +
            "from venta v\n" +
            "inner join inventario inv on v.idInventario = inv.idInventario\n" +
            "inner join producto p on inv.idProducto = p.idProducto\n" +
            "inner join denominacion d on p.idDenominacion = d.idDenominacion\n" +
            "inner join comunidad c on c.idComunidad = p.idComunidad and year(v.fecha) = ?1 and c.nombre = ?2\n" +
            "order by fech asc", nativeQuery = true)
    List<ReporteConCamposOriginales> reporteTrimestralComunidad(String año, String articulo);

    @Query(value = "select v.fecha as fecha, v.tipoDocumentoVenta as tipodocventa, v.numeroDocumentoVenta as numerodocventa, v.numeroDocumentoIdentidad as numerodocid, v.nombreCliente as nombrecliente, d.codigoNombre as codprod, inv.color as colorprod,p.codigoGenerado as codgen, d.nombre as nombreproduct, v.Cantidad as cantidad, v.precioUnitarioVenta as preciounit, monthname(v.fecha) as fech, v.Cantidad*v.precioUnitarioVenta as totalxproduct from venta v\n" +
            "inner join inventario inv on v.idInventario = inv.idInventario\n" +
            "inner join producto p on inv.idProducto = p.idProducto\n" +
            "inner join denominacion d on p.idDenominacion = d.idDenominacion and month(v.fecha) = ?1 and year(v.fecha) = ?2\n" +
            "order by v.fecha asc", nativeQuery = true)
    List<ReporteConCamposOriginales> reporteMensualTotal(String mes, String año);

    @Query(value = "select v.fecha as fecha, v.tipoDocumentoVenta as tipodocventa, v.numeroDocumentoVenta as numerodocventa, v.numeroDocumentoIdentidad as numerodocid, v.nombreCliente as nombrecliente, d.codigoNombre as codprod, inv.color as colorprod,p.codigoGenerado as codgen, d.nombre as nombreproduct, v.Cantidad as cantidad, v.precioUnitarioVenta as preciounit, monthname(v.fecha) as fech, v.Cantidad*v.precioUnitarioVenta as totalxproduct from venta v\n" +
            "inner join inventario inv on v.idInventario = inv.idInventario\n" +
            "inner join producto p on inv.idProducto = p.idProducto\n" +
            "inner join denominacion d on p.idDenominacion = d.idDenominacion and year(v.fecha) = ?1\n" +
            "order by v.fecha asc", nativeQuery = true)
    List<ReporteConCamposOriginales> reporteAnualTotal(String año);

    @Query(value = "select v.fecha as fecha, v.tipoDocumentoVenta as tipodocventa, v.numeroDocumentoVenta as numerodocventa, v.numeroDocumentoIdentidad as numerodocid, v.nombreCliente as nombrecliente, d.codigoNombre as codprod, inv.color as colorprod,p.codigoGenerado as codgen, d.nombre as nombreproduct, v.Cantidad as cantidad, v.precioUnitarioVenta as preciounit, \n" +
            "CASE \n" +
            "    WHEN month(v.fecha) BETWEEN 1 AND 3 THEN '1er trimestre (enero - febrero - marzo)' \n" +
            "    WHEN month(v.fecha) BETWEEN 4 AND 6 THEN '2do trimestre (abril - mayo - junio)'\n" +
            "    WHEN month(v.fecha) BETWEEN 7 AND 9 THEN '3er trimestre (julio - agosto - setiembre)'\n" +
            "    WHEN month(v.fecha) BETWEEN 10 AND 12 THEN '4to trimestre (octubre - noviembre - diciembre)'\n" +
            "END as fech , v.Cantidad*v.precioUnitarioVenta as totalxproduct\n" +
            "from venta v\n" +
            "inner join inventario inv on v.idInventario = inv.idInventario\n" +
            "inner join producto p on inv.idProducto = p.idProducto\n" +
            "inner join denominacion d on p.idDenominacion = d.idDenominacion and year(v.fecha) = ?1\n" +
            "order by fech asc", nativeQuery = true)
    List<ReporteConCamposOriginales> reporteTrimestralTotal(String año);

    @Query(value = "SELECT DISTINCT(year(v.fecha)) as fecha FROM venta v", nativeQuery = true)
    List<FechaVenta> obtenerAñosDeVenta();

    @Query(value = "SELECT distinct(month(v.fecha)) as fecha, CASE WHEN MONTH(v.fecha) = 1 THEN \"enero\"\n" +
            "WHEN MONTH(v.fecha) = 2 THEN \"febrero\"\n" +
            "WHEN MONTH(v.fecha) = 3 THEN \"marzo\"\n" +
            "WHEN MONTH(v.fecha) = 4 THEN \"abril\"\n" +
            "WHEN MONTH(v.fecha) = 5 THEN \"mayo\"\n" +
            "WHEN MONTH(v.fecha) = 6 THEN \"junio\"\n" +
            "WHEN MONTH(v.fecha) = 7 THEN \"julio\"\n" +
            "WHEN MONTH(v.fecha) = 8 THEN \"agosto\"\n" +
            "WHEN MONTH(v.fecha) = 9 THEN \"septiembre\"\n" +
            "WHEN MONTH(v.fecha) = 10 THEN \"octubre\"\n" +
            "WHEN MONTH(v.fecha) = 11 THEN \"noviembre\"\n" +
            "WHEN MONTH(v.fecha) = 12 THEN \"diciembre\" END AS fechaname FROM venta v\n" +
            "group by fecha", nativeQuery = true)
    List<FechaMesVenta> obtenerMesesDeVenta();

    @Query(value = "select DISTINCT(d.nombre) from venta v\n" +
            "inner join inventario inv on v.idInventario = inv.idInventario\n" +
            "inner join producto p on inv.idProducto = p.idProducto\n" +
            "inner join denominacion d on p.idDenominacion = d.idDenominacion ", nativeQuery = true)
    List<ProductosQueSeVendieron> obtenerProductosVendidos();

    @Query(value = "select distinct(c.nombre) as comunidad from venta v\n" +
            "inner join inventario inv on v.idInventario = inv.idInventario\n" +
            "inner join producto p on inv.idProducto = p.idProducto\n" +
            "inner join denominacion d on p.idDenominacion = d.idDenominacion \n" +
            "inner join comunidad c on c.idComunidad = p.idComunidad\n" +
            "order by v.fecha",nativeQuery = true)
    List<ProdComunidades> obtenerPComunidad();

    @Query(value = "select distinct(s.nombre) as nombresede from venta v\n" +
            "inner join tienda t on v.idTienda = t.idTienda\n" +
            "inner join sede s on t.idSede = s.idSede\n" +
            "order by v.fecha", nativeQuery = true)
    List<ListaSedesQueVendieron> obtenerSedes();

    @Query(value = "SELECT distinct(v.nombreCliente) as nombre FROM venta v", nativeQuery = true)
    List<ClientesQueCompraron> obtenerClientes();

    @Query(value = "SELECT distinct(year(v.fecha)) as anio FROM venta v where v.nombreCliente = ?1", nativeQuery = true)
    List<AñoDeCompraXFiltro> obtenerAñosXCliente(String cliente);

    @Query(value = "select distinct(year(v.fecha)) as anio from venta v\n" +
            "inner join tienda t on v.idTienda = t.idTienda\n" +
            "inner join sede s on t.idSede = s.idSede and s.nombre = ?1\n" +
            "order by v.fecha", nativeQuery = true)
    List<AñoDeCompraXFiltro> obtenerAñosXSede(String sede);

    @Query(value = "select distinct(year(v.fecha)) as anio from venta v\n" +
            "inner join inventario inv on v.idInventario = inv.idInventario\n" +
            "inner join producto p on inv.idProducto = p.idProducto\n" +
            "inner join denominacion d on p.idDenominacion = d.idDenominacion and d.nombre = ?1\n" +
            "order by v.fecha", nativeQuery = true)
    List<AñoDeCompraXFiltro> obtenerAñosXProducto(String producto);

    @Query(value = "select distinct(year(v.fecha)) as anio from venta v\n" +
            "inner join inventario inv on v.idInventario = inv.idInventario\n" +
            "inner join producto p on inv.idProducto = p.idProducto\n" +
            "inner join comunidad c on c.idComunidad = p.idComunidad and c.nombre = ?1\n" +
            "order by v.fecha", nativeQuery = true)
    List<AñoDeCompraXFiltro> obtenerAñosXComunidad(String comunidad);

    @Query(value = "SELECT distinct(month(v.fecha)) as fecha, \n" +
            "CASE \n" +
            "\tWHEN MONTH(v.fecha) = 1 THEN \"enero\" \n" +
            "    WHEN MONTH(v.fecha) = 2 THEN \"febrero\"\n" +
            "\tWHEN MONTH(v.fecha) = 3 THEN \"marzo\" \n" +
            "\tWHEN MONTH(v.fecha) = 4 THEN \"abril\" \n" +
            "\tWHEN MONTH(v.fecha) = 5 THEN \"mayo\" \n" +
            "\tWHEN MONTH(v.fecha) = 6 THEN \"junio\" \n" +
            "\tWHEN MONTH(v.fecha) = 7 THEN \"julio\" \n" +
            "\tWHEN MONTH(v.fecha) = 8 THEN \"agosto\" \n" +
            "\tWHEN MONTH(v.fecha) = 9 THEN \"septiembre\" \n" +
            "\tWHEN MONTH(v.fecha) = 10 THEN \"octubre\" \n" +
            "\tWHEN MONTH(v.fecha) = 11 THEN \"noviembre\" \n" +
            "\tWHEN MONTH(v.fecha) = 12 THEN \"diciembre\" END AS fechaname FROM venta v where v.nombreCliente = ?1 and year(v.fecha) = ?2\n" +
            "\tgroup by fecha", nativeQuery = true)
    List<FechaMesVenta> obtenerPeriodoXAño(String cliente, String año);

    @Query(value = "SELECT t.nombre as nombretienda, v.nombreCliente as cliente, v.tipoDocumentoVenta as doc, v.precioUnitarioVenta as preciounit, v.Cantidad as cantidad, v.precioUnitarioVenta*v.Cantidad as preciotot FROM venta v\n" +
            "inner join tienda t on t.idTienda = v.idTienda\n" +
            "inner join sede s on s.idSede = t.idSede and s.idSede = ?1", nativeQuery = true)
    List<CamposReporteSede> obtenerDatosParaReporteSede(int id);

    //SEDEEE
    @Query(value = "select t.nombre as tienda,v.fecha as fecha, v.tipoDocumentoVenta as tipodocventa, v.numeroDocumentoVenta as numerodocventa, v.numeroDocumentoIdentidad as numerodocid, v.nombreCliente as nombrecliente, d.codigoNombre as codprod, inv.color as colorprod,p.codigoGenerado as codgen, d.nombre as nombreproduct, v.Cantidad as cantidad, v.precioUnitarioVenta as preciounit, monthname(v.fecha) as fech, v.Cantidad*v.precioUnitarioVenta as totalxproduct from venta v\n" +
            "inner join inventario inv on v.idInventario = inv.idInventario\n" +
            "inner join producto p on inv.idProducto = p.idProducto\n" +
            "inner join denominacion d on p.idDenominacion = d.idDenominacion\n" +
            "inner join tienda t on v.idTienda = t.idTienda\n" +
            "inner join sede s on t.idSede = s.idSede and s.idSede = ?1\n" +
            "order by v.fecha asc", nativeQuery = true)
    List<ReporteConCamposOriginales> reporteSede(int idsede);

}
