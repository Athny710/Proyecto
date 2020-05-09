package com.example.proyecto.repository;

import com.example.proyecto.entity.Artesano;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ArtesanoRepository extends JpaRepository<Artesano, Integer> {

}
