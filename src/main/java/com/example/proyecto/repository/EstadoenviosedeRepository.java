package com.example.proyecto.repository;

import com.example.proyecto.entity.Estadoenviosede;
import com.example.proyecto.entity.Inventariosede;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EstadoenviosedeRepository extends JpaRepository<Estadoenviosede, Integer> {

    List<Estadoenviosede>  findByInventariosede(Inventariosede inventariosede);


}
