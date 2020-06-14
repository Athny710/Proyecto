package com.example.proyecto.repository;

import com.example.proyecto.entity.Comunidad;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface ComunidadRepository extends JpaRepository<Comunidad, Integer> {


    @Query(value = "select * from comunidad where nombre = ?1 or codigo =?2",
            nativeQuery = true)
    List<Comunidad> buscarPorNombre (String nombre, String codigo);

    List<Comunidad> findByNombre(String name);


}
