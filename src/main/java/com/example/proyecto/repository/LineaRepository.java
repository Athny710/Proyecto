package com.example.proyecto.repository;

import com.example.proyecto.entity.Linea;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.sound.sampled.Line;
import java.util.List;

@Repository
public interface LineaRepository extends JpaRepository<Linea, Integer > {

    List<Linea> findByNombre(String name);

}
