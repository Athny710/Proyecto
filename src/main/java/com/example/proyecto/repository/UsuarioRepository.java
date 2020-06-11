package com.example.proyecto.repository;

import com.example.proyecto.entity.Usuarios;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuarios,Integer> {

    public Usuarios findByCorreo(String email);
    public List<Usuarios> findByTipo(String tipo);

    @Query(value="select hasheado from usuarios WHERE idUsuarios=?1", nativeQuery=true)
    String seleccionarHash(Optional<Usuarios> idusuarios);

}
