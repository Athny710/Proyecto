package com.example.proyecto.repository;

import com.example.proyecto.entity.Sede;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SedeRepository extends JpaRepository<Sede,Integer> {

    Sede findByNombre(String nombre);
}
