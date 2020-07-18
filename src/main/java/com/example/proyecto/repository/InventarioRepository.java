package com.example.proyecto.repository;

import com.example.proyecto.dto.inventarioStockTotal;
import com.example.proyecto.entity.Inventario;
import com.example.proyecto.entity.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InventarioRepository extends JpaRepository<Inventario, Integer> {

    @Query(value = "SELECT * FROM sw2_proyecto.inventario \n" +
            "where stock = 0 and (estado != 'Devuelto' and estado != 'Vencido')",
            nativeQuery = true)
    List<Inventario> SinStock();

    List<Inventario> findByProducto(Producto producto);
    List<Inventario> findByEstado(String estado);


    @Query(value = "SELECT (inventario.stock + coalesce((SELECT sum(inventariosede.stock) as stock FROM sw2_proyecto.inventariosede where inventariosede.idInventario = inventario.idInventario ), 0))  as 'stockTotal', inventario.idInventario as 'idInvent' FROM sw2_proyecto.inventario where stock > 0 and (estado != 'Devuelto' and estado != 'Vencido') ", nativeQuery = true)
    List<inventarioStockTotal> listaInventarioStockTotal();

    @Query(value = "SELECT * FROM sw2_proyecto.inventario \n" +
            "where stock > 0 and (estado != 'Devuelto' and estado != 'Vencido')",
            nativeQuery = true)
    List<Inventario> listarStockMayor0();

    @Query(value = "SELECT inventario.* FROM inventario, producto\n" +
            "where inventario.idProducto = producto.idProducto\n" +
            "and producto.idComunidad = ?1 and inventario.stock > 0 and (inventario.estado != 'Devuelto' and inventario.estado != 'Vencido')",
            nativeQuery = true)
    List<Inventario> listarPorComunidad(int id);

    @Query(value = "SELECT inventario.* FROM inventario, producto, adquisicion\n" +
            "where inventario.idProducto = producto.idProducto\n" +
            "and producto.idAdquisicion = adquisicion.idAdquisicion \n" +
            "and adquisicion.modalidad =?1 and inventario.stock > 0 and (inventario.estado != 'Devuelto' and inventario.estado != 'Vencido')",
            nativeQuery = true)
    List<Inventario> listarPorAdquisicion(String tipo);

    @Query(value = "SELECT inventario.* FROM inventario, producto\n" +
            "where inventario.idProducto = producto.idProducto\n" +
            "and producto.idCategoria = ?1 and inventario.stock > 0 and (inventario.estado != 'Devuelto' and inventario.estado != 'Vencido')",
            nativeQuery = true)
    List<Inventario> listarPorCategoria(int id);

    @Query(value = "SELECT inventario.* FROM inventario, producto, adquisicion\n" +
            "where inventario.idProducto = producto.idProducto\n" +
            "and producto.idAdquisicion =adquisicion.idAdquisicion\n" +
            "and adquisicion.modalidad = ?1 and adquisicion.idArtesano= ?2 and inventario.stock > 0 and (inventario.estado != 'Devuelto' and inventario.estado != 'Vencido')",
            nativeQuery = true)
    List<Inventario> listarPorArtesanoConConsigna(String tipo, int id);

    @Query(value = "SELECT inventario.* FROM inventario, producto\n" +
            "where inventario.idProducto = producto.idProducto\n" +
            "and producto.idCategoria =?1 and producto.idComunidad =?2 and inventario.stock > 0 and (inventario.estado != 'Devuelto' and inventario.estado != 'Vencido')",
            nativeQuery = true)
    List<Inventario> listarPorCategoriaYComunidad(int idCate, int idComu);


    @Query(value = "SELECT inventario.* FROM sw2_proyecto.inventario, producto, adquisicion\n" +
            "where inventario.idProducto = producto.idProducto\n" +
            "and producto.idAdquisicion = adquisicion.idAdquisicion\n" +
            "and producto.idComunidad =?1 and adquisicion.modalidad = ?2\n" +
            "and inventario.stock > 0 and (inventario.estado != 'Devuelto' and inventario.estado != 'Vencido')",
            nativeQuery = true)
    List<Inventario> listarPorComunidadYModalidad( int idComu, String tipo);

    @Query(value = "SELECT inventario.* FROM sw2_proyecto.inventario, producto, adquisicion\n" +
            "where inventario.idProducto = producto.idProducto\n" +
            "and producto.idAdquisicion = adquisicion.idAdquisicion\n" +
            "and producto.idComunidad =?1 and adquisicion.modalidad = 'consignado' and adquisicion.idArtesano = ?2 \n" +
            "and inventario.stock > 0 and (inventario.estado != 'Devuelto' and inventario.estado != 'Vencido')",
            nativeQuery = true)
    List<Inventario> listarPorComunidadConsignadoYArtesano( int idComu, int idArt);

    @Query(value = "SELECT inventario.* FROM sw2_proyecto.inventario, producto, adquisicion\n" +
            "where inventario.idProducto = producto.idProducto\n" +
            "and producto.idAdquisicion = adquisicion.idAdquisicion\n" +
            "and producto.idCategoria =1 and adquisicion.modalidad = 'consignado' and adquisicion.idArtesano = 9 \n" +
            "and inventario.stock > 0 and (inventario.estado != 'Devuelto' and inventario.estado != 'Vencido')",
            nativeQuery = true)
    List<Inventario> listarPorCategoriaConsignadoYArtesano( int idCat, int idArt);

    @Query(value = "SELECT inventario.* FROM sw2_proyecto.inventario, producto, adquisicion\n" +
            "where inventario.idProducto = producto.idProducto\n" +
            "and producto.idAdquisicion = adquisicion.idAdquisicion\n" +
            "and producto.idCategoria =?1 and adquisicion.modalidad = ?2 \n" +
            "and inventario.stock > 0 and (inventario.estado != 'Devuelto' and inventario.estado != 'Vencido')",
            nativeQuery = true)
    List<Inventario> listarPorCategoriaYModalidad( int idCate, String tipo);

    @Query(value = "SELECT inventario.* FROM sw2_proyecto.inventario, producto, adquisicion\n" +
            "where inventario.idProducto = producto.idProducto\n" +
            "and producto.idAdquisicion = adquisicion.idAdquisicion\n" +
            "and producto.idCategoria =?1 and adquisicion.modalidad = ?2 and producto.idComunidad =?3 \n" +
            "and inventario.stock > 0 and (inventario.estado != 'Devuelto' and inventario.estado != 'Vencido')",
            nativeQuery = true)
    List<Inventario> listarPorCategoriaYComunidadYModalidad( int idCate, String tipo,int idComu);

    @Query(value = "SELECT inventario.* FROM sw2_proyecto.inventario, producto, adquisicion\n" +
            "where inventario.idProducto = producto.idProducto\n" +
            "and producto.idAdquisicion = adquisicion.idAdquisicion\n" +
            "and producto.idCategoria =?1 and adquisicion.modalidad = 'consignado' and producto.idComunidad = ?2 and adquisicion.idArtesano = ?3\n" +
            "and inventario.stock > 0 and (inventario.estado != 'Devuelto' and inventario.estado != 'Vencido')",
            nativeQuery = true)
    List<Inventario> listarPorCategoriaComunidadConsignadoYArtesano( int idCat, int idComu, int idArt);


}
