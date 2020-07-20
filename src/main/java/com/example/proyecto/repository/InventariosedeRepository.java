package com.example.proyecto.repository;

import com.example.proyecto.entity.Artesano;
import com.example.proyecto.entity.Inventario;
import com.example.proyecto.entity.Inventariosede;
import com.example.proyecto.entity.Sede;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InventariosedeRepository extends JpaRepository<Inventariosede, Integer> {

    @Query(value="SELECT inventariosede.* FROM sw2_proyecto.inventariosede, sw2_proyecto.inventario\n" +
            "where inventariosede.idInventario = inventario.idInventario\n" +
            "and inventario.stock > 0 and (inventario.estado != 'Devuelto' and inventario.estado != 'Vencido') \n" +
            "and inventariosede.idSede = ?1", nativeQuery= true)
    List<Inventariosede> obtenerInventarioSede(Sede sede);

    List<Inventariosede> findByInventarioAndSede(Inventario xd, Sede xd1);

    List<Inventariosede> findBySede(Sede sede);

    List<Inventariosede> findByInventario(Inventario inventario);

    /*
    @Query(value="SELECT (stock + (SELECT SUM(stock) FROM sw2_proyecto.inventariosede" +
            " where IdInventario = ?1)) as suma" +
            " FROM inventario " +
            "where inventario.idInventario = ?1"
            , nativeQuery = true)
    Integer obtenerStockTotal(int idInventario);
    */
}