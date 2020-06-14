package com.example.proyecto.repository;

import com.example.proyecto.entity.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoriaRepository extends JpaRepository<Categoria, Integer> {

    @Query(value="select * from categoria where nombre= ?1 or codigo=?2",nativeQuery = true)
    List<Categoria> buscarCategoria(String nombre,String codigo);

    @Query(value="select * from categoria where codigo=?1",nativeQuery = true)
    List<Categoria> buscarCategoriaPorCodigo(String codigo);

    @Query(value="select * from categoria where nombre= ?1",nativeQuery = true)
    List<Categoria> buscarCategoriaPorNombre(String nombre);

    List<Categoria> findByNombre(String name);

}
