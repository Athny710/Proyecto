package com.example.proyecto.repository;

import com.example.proyecto.entity.Historial;
import com.example.proyecto.entity.Inventario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HistorialRepository extends JpaRepository<Historial,Integer> {

    @Query(value = "SELECT * FROM sw2_proyecto.historial where idInventario = ?1;",
            nativeQuery = true)
    List<Historial> listarHistorialDeUnPro(int idPro);
}
