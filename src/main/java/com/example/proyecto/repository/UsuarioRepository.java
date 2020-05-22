package com.example.proyecto.repository;

import com.example.proyecto.entity.Usuarios;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuarios,Integer> {

    public Usuarios findByCorreo(String email);
    public Usuarios findByTipo(String tipo);
}
