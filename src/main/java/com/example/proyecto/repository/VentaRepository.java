package com.example.proyecto.repository;

import com.example.proyecto.entity.Comunidad;
import com.example.proyecto.entity.Tienda;
import com.example.proyecto.entity.Venta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VentaRepository extends JpaRepository<Venta,Integer> {

    @Query(value = "select * from venta where nombreCliente = ?1 ",
            nativeQuery = true)
    List<Venta> buscarPorNombre (String nombre);

    @Query(value = "select * from venta where nombreCliente = ?1 ",
            nativeQuery = true)
    List<Venta> buscarPorSede(int sedeid);

    List<Venta> findByTienda(Tienda tienda);

}
