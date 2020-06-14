package com.example.proyecto.repository;

import com.example.proyecto.entity.Inventario;
import com.example.proyecto.entity.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InventarioRepository extends JpaRepository<Inventario, Integer> {

    List<Inventario> findByStock(int stock);
    List<Inventario> findByProducto(Producto producto);
}
