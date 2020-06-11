package com.example.proyecto.repository;

import com.example.proyecto.entity.Denominacion;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DenominacionRepository extends JpaRepository<Denominacion,Integer> {
}
