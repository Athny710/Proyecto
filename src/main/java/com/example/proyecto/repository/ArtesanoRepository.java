package com.example.proyecto.repository;

import com.example.proyecto.entity.Artesano;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ArtesanoRepository extends JpaRepository<Artesano, Integer> {

    @Query(value="SELECT a.*" +
            "FROM sw2_proyecto.artesano a" +
            "WHERE a.nombre =?1 OR a.apellidoPaterno=?1 OR"+
            "a.apellidoMaterno=?1 OR a.idComunidad (SELECT c.idComunidad"+
            "FROM sw2_proyecto.comunidad WHERE c.nombre=?1)", nativeQuery= true)
    List<Artesano> obtenerArtesanoBusqueda(String busqueda);

    @Query(value="SELECT * FROM artesano WHERE idartesano =?1 OR nombre=?2", nativeQuery = true)
    List<Artesano> obtenerIdArtesano(Integer idbuscado, String nombre);




}
