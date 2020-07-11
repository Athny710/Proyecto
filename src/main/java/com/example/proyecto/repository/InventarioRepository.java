package com.example.proyecto.repository;

import com.example.proyecto.entity.Inventario;
import com.example.proyecto.entity.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InventarioRepository extends JpaRepository<Inventario, Integer> {

    List<Inventario> findByStock(int stock);
    List<Inventario> findByProducto(Producto producto);
    List<Inventario> findByEstado(String estado);

    @Query(value = "SELECT * FROM sw2_proyecto.inventario\n" +
            "where stock > 0",
            nativeQuery = true)
    List<Inventario> listarStockMayor0();

    @Query(value = "SELECT inventario.* FROM inventario, producto\n" +
            "where inventario.idProducto = producto.idProducto\n" +
            "and producto.idComunidad = ?1",
            nativeQuery = true)
    List<Inventario> listarPorComunidad(int id);

    @Query(value = "SELECT inventario.* FROM inventario, producto, adquisicion\n" +
            "where inventario.idProducto = producto.idProducto\n" +
            "and producto.idAdquisicion = adquisicion.idAdquisicion \n" +
            "and adquisicion.modalidad =?1",
            nativeQuery = true)
    List<Inventario> listarPorAdquisicion(String tipo);

    @Query(value = "SELECT inventario.* FROM inventario, producto\n" +
            "where inventario.idProducto = producto.idProducto\n" +
            "and producto.idCategoria = ?1 ;",
            nativeQuery = true)
    List<Inventario> listarPorCategoria(int id);

    @Query(value = "SELECT inventario.* FROM inventario, producto, adquisicion\n" +
            "where inventario.idProducto = producto.idProducto\n" +
            "and producto.idAdquisicion =adquisicion.idAdquisicion\n" +
            "and adquisicion.modalidad = ?1 and adquisicion.idArtesano= ?2",
            nativeQuery = true)
    List<Inventario> listarPorArtesanoConConsigna(String tipo, int id);

    @Query(value = "SELECT inventario.* FROM inventario, producto\n" +
            "where inventario.idProducto = producto.idProducto\n" +
            "and producto.idCategoria =?1 and producto.idComunidad =?2",
            nativeQuery = true)
    List<Inventario> listarPorCategoriaYComunidad(int idCate, int idComu);


}
