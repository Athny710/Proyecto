package com.example.proyecto.repository;

import com.example.proyecto.entity.Adquisicion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdquisicionRepository extends JpaRepository <Adquisicion,Integer> {
}
