package com.example.proyecto.repository;

import com.example.proyecto.entity.Denominacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface DenominacionRepository extends JpaRepository<Denominacion,Integer> {
    List<Denominacion> findByNombre(String name);

    @Query(value = "SELECT * FROM sw2_proyecto.denominacion\n" +
            "where nombre = ?1 or codigoNombre = ?2 or descripcion = ?3 or codigoDescripcion = ?4",
            nativeQuery = true)
    List<Denominacion> buscarRepetido(String nombre, String codNombre, String descripcion, String codDescripcion);
}
