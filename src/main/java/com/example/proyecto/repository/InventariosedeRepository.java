package com.example.proyecto.repository;

import com.example.proyecto.entity.Artesano;
import com.example.proyecto.entity.Inventario;
import com.example.proyecto.entity.Inventariosede;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InventariosedeRepository extends JpaRepository<Inventariosede, Integer> {

    @Query(value="SELECT * FROM sw2_proyecto.inventariosede" +
            " where inventariosede.idSede = ?1;", nativeQuery= true)
    List<Inventariosede> obtenerInventarioSede(int idSede);



}