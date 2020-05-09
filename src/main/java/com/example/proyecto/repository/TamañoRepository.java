package com.example.proyecto.repository;

import com.example.proyecto.entity.Tamaño;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TamañoRepository extends JpaRepository<Tamaño,Integer> {
}
