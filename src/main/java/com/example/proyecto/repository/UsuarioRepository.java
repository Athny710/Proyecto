package com.example.proyecto.repository;

import com.example.proyecto.entity.Usuarios;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.validation.constraints.Max;
import java.util.List;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuarios,Integer> {

    public Usuarios findByCorreo(String email);
    public List<Usuarios> findByTipo(String tipo);
}
