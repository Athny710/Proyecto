package com.example.proyecto.repository;

import com.example.proyecto.entity.Tienda;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TiendaRepository extends JpaRepository<Tienda,Integer> {

    @Query(value = "select * from tienda where nombre = ?1",
            nativeQuery = true)
    List<Tienda> buscarPorNombreDeTienda(String name);

    List<Tienda> findByNombre(String nombre);
}
