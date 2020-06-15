package com.example.proyecto.repository;

import com.example.proyecto.dto.AñosVenta;
import com.example.proyecto.dto.ReporteConCamposOriginales;
import com.example.proyecto.dto.VentaPorCodigo;
import com.example.proyecto.dto.VentasXNombreDeProducto;
import com.example.proyecto.entity.Comunidad;
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

    @Query(value = "select p.codigoGenerado as codgen, d.nombre as nombreproduct, v.Cantidad as cantidad, v.precioUnitarioVenta as preciounit, monthname(v.fecha) as fech, v.Cantidad*v.precioUnitarioVenta as totalxproduct from venta v\n" +
            "inner join inventario inv on v.idInventario = inv.idInventario\n" +
            "inner join producto p on inv.idProducto = p.idProducto\n" +
            "inner join denominacion d on p.idDenominacion = d.idDenominacion and month(v.fecha) = ?1 and year(v.fecha) = ?2 and v.nombreCliente = ?3\n" +
            "order by v.fecha", nativeQuery = true)
    List<ReporteConCamposOriginales> reporteMensualCliente(String mes, String año, String cliente);

    @Query(value = "select p.codigoGenerado as codgen, d.nombre as nombreproduct, v.Cantidad as cantidad, v.precioUnitarioVenta as preciounit, monthname(v.fecha) as fech, v.Cantidad*v.precioUnitarioVenta as totalxproduct from venta v\n" +
            "inner join inventario inv on v.idInventario = inv.idInventario\n" +
            "inner join producto p on inv.idProducto = p.idProducto\n" +
            "inner join denominacion d on p.idDenominacion = d.idDenominacion and year(v.fecha) = ?1 and v.nombreCliente = ?2\n" +
            "order by v.fecha", nativeQuery = true)
    List<ReporteConCamposOriginales> reporteAnualCliente(String año, String cliente);

    @Query(value = "select p.codigoGenerado as codgen, d.nombre as nombreproduct, v.Cantidad as cantidad, v.precioUnitarioVenta as preciounit, \n" +
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

    @Query(value = "select p.codigoGenerado as codgen, d.nombre as nombreproduct, v.Cantidad as cantidad, v.precioUnitarioVenta as preciounit, monthname(v.fecha) as fech, v.Cantidad*v.precioUnitarioVenta as totalxproduct from venta v\n" +
            "inner join inventario inv on v.idInventario = inv.idInventario\n" +
            "inner join producto p on inv.idProducto = p.idProducto\n" +
            "inner join denominacion d on p.idDenominacion = d.idDenominacion\n" +
            "inner join usuarios u on v.idUsuarios = u.idUsuarios\n" +
            "inner join sede s on u.idSede = s.idSede and month(v.fecha) = ?1 and year(v.fecha) = ?2 and s.nombre = ?3\n" +
            "order by v.fecha", nativeQuery = true)
    List<ReporteConCamposOriginales> reporteMensualSede(String mes, String año, String idsede);

    @Query(value = "select p.codigoGenerado as codgen, d.nombre as nombreproduct, v.Cantidad as cantidad, v.precioUnitarioVenta as preciounit, monthname(v.fecha) as fech, v.Cantidad*v.precioUnitarioVenta as totalxproduct from venta v\n" +
            "inner join inventario inv on v.idInventario = inv.idInventario\n" +
            "inner join producto p on inv.idProducto = p.idProducto\n" +
            "inner join denominacion d on p.idDenominacion = d.idDenominacion\n" +
            "inner join usuarios u on v.idUsuarios = u.idUsuarios\n" +
            "inner join sede s on u.idSede = s.idSede and year(v.fecha) = ?1 and s.nombre = ?2\n" +
            "order by v.fecha", nativeQuery = true)
    List<ReporteConCamposOriginales> reporteAnualSede(String año, String idsede);

    @Query(value = "select p.codigoGenerado as codgen, d.nombre as nombreproduct, v.Cantidad as cantidad, v.precioUnitarioVenta as preciounit, \n" +
            "CASE \n" +
            "    WHEN month(v.fecha) BETWEEN 1 AND 3 THEN '1er trimestre (enero - febrero - marzo)' \n" +
            "    WHEN month(v.fecha) BETWEEN 4 AND 6 THEN '2do trimestre (abril - mayo - junio)'\n" +
            "    WHEN month(v.fecha) BETWEEN 7 AND 9 THEN '3er trimestre (julio - agosto - setiembre)'\n" +
            "    WHEN month(v.fecha) BETWEEN 10 AND 12 THEN '4to trimestre (octubre - noviembre - diciembre)'\n" +
            "END as fech , v.Cantidad*v.precioUnitarioVenta as totalxproduct from venta v\n" +
            "inner join inventario inv on v.idInventario = inv.idInventario\n" +
            "inner join producto p on inv.idProducto = p.idProducto\n" +
            "inner join denominacion d on p.idDenominacion = d.idDenominacion\n" +
            "inner join usuarios u on v.idUsuarios = u.idUsuarios\n" +
            "inner join sede s on u.idSede = s.idSede and year(v.fecha) = ?1 and s.nombre = ?2\n" +
            "order by v.fecha", nativeQuery = true)
    List<ReporteConCamposOriginales> reporteTrimestralSede(String año, String idsede);

    @Query(value = "select p.codigoGenerado as codgen, d.nombre as nombreproduct, v.Cantidad as cantidad, v.precioUnitarioVenta as preciounit, monthname(v.fecha) as fech, v.Cantidad*v.precioUnitarioVenta as totalxproduct from venta v\n" +
            "inner join inventario inv on v.idInventario = inv.idInventario\n" +
            "inner join producto p on inv.idProducto = p.idProducto\n" +
            "inner join denominacion d on p.idDenominacion = d.idDenominacion and month(v.fecha) = ?1 and year(v.fecha) = ?2 and d.nombre = ?3\n" +
            "order by v.fecha", nativeQuery = true)
    List<ReporteConCamposOriginales> reporteMensualArticulo(String mes, String año, String articulo);

    @Query(value = "select p.codigoGenerado as codgen, d.nombre as nombreproduct, v.Cantidad as cantidad, v.precioUnitarioVenta as preciounit, monthname(v.fecha) as fech, v.Cantidad*v.precioUnitarioVenta as totalxproduct from venta v\n" +
            "inner join inventario inv on v.idInventario = inv.idInventario\n" +
            "inner join producto p on inv.idProducto = p.idProducto\n" +
            "inner join denominacion d on p.idDenominacion = d.idDenominacion and year(v.fecha) = ?1 and d.nombre = ?2\n" +
            "order by v.fecha", nativeQuery = true)
    List<ReporteConCamposOriginales> reporteAnualArticulo(String año, String articulo);

    @Query(value = "select p.codigoGenerado as codgen, d.nombre as nombreproduct, v.Cantidad as cantidad, v.precioUnitarioVenta as preciounit, \n" +
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

    @Query(value = "select p.codigoGenerado as codgen, d.nombre as nombreproduct, v.Cantidad as cantidad, v.precioUnitarioVenta as preciounit, monthname(v.fecha) as fech, v.Cantidad*v.precioUnitarioVenta as totalxproduct from venta v\n" +
            "inner join inventario inv on v.idInventario = inv.idInventario\n" +
            "inner join producto p on inv.idProducto = p.idProducto\n" +
            "inner join denominacion d on p.idDenominacion = d.idDenominacion \n" +
            "inner join comunidad c on c.idComunidad = p.idComunidad and month(v.fecha) = ?1 and year(v.fecha) = ?2 and c.nombre = ?3\n" +
            "order by v.fecha", nativeQuery = true)
    List<ReporteConCamposOriginales> reporteMensualComunidad(String mes, String año, String articulo);

    @Query(value = "select p.codigoGenerado as codgen, d.nombre as nombreproduct, v.Cantidad as cantidad, v.precioUnitarioVenta as preciounit, monthname(v.fecha) as fech, v.Cantidad*v.precioUnitarioVenta as totalxproduct from venta v\n" +
            "inner join inventario inv on v.idInventario = inv.idInventario\n" +
            "inner join producto p on inv.idProducto = p.idProducto\n" +
            "inner join denominacion d on p.idDenominacion = d.idDenominacion \n" +
            "inner join comunidad c on c.idComunidad = p.idComunidad and year(v.fecha) = ?1 and c.nombre = ?2\n" +
            "order by v.fecha", nativeQuery = true)
    List<ReporteConCamposOriginales> reporteAnualComunidad(String año, String articulo);

    @Query(value = "select p.codigoGenerado as codgen, d.nombre as nombreproduct, v.Cantidad as cantidad, v.precioUnitarioVenta as preciounit, \n" +
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

    @Query(value = "select p.codigoGenerado as codgen, d.nombre as nombreproduct, v.Cantidad as cantidad, v.precioUnitarioVenta as preciounit, monthname(v.fecha) as fech, v.Cantidad*v.precioUnitarioVenta as totalxproduct from venta v\n" +
            "inner join inventario inv on v.idInventario = inv.idInventario\n" +
            "inner join producto p on inv.idProducto = p.idProducto\n" +
            "inner join denominacion d on p.idDenominacion = d.idDenominacion and month(v.fecha) = ?1 and year(v.fecha) = ?2\n" +
            "order by v.fecha", nativeQuery = true)
    List<ReporteConCamposOriginales> reporteMensualTotal(String mes, String año);

    @Query(value = "select p.codigoGenerado as codgen, d.nombre as nombreproduct, v.Cantidad as cantidad, v.precioUnitarioVenta as preciounit, monthname(v.fecha) as fech, v.Cantidad*v.precioUnitarioVenta as totalxproduct from venta v\n" +
            "inner join inventario inv on v.idInventario = inv.idInventario\n" +
            "inner join producto p on inv.idProducto = p.idProducto\n" +
            "inner join denominacion d on p.idDenominacion = d.idDenominacion and year(v.fecha) = ?1\n" +
            "order by v.fecha", nativeQuery = true)
    List<ReporteConCamposOriginales> reporteAnualTotal(String año);

    @Query(value = "select p.codigoGenerado as codgen, d.nombre as nombreproduct, v.Cantidad as cantidad, v.precioUnitarioVenta as preciounit, \n" +
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
    List<AñosVenta> obtenerAñosDeVenta();

}
