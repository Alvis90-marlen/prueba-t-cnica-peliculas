package com.vivetu.peliculas.repository;

import com.vivetu.peliculas.entity.Pelicula;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List; // Importar List

public interface PeliculaRepository extends JpaRepository<Pelicula, Long> {
    // Nuevo método: Spring Data JPA creará la consulta automáticamente
    List<Pelicula> findByEstado(String estado);
}