package com.vivetu.peliculas.repository;

import com.vivetu.peliculas.entity.Pelicula;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Sort; // Importar Sort
import java.util.List;

public interface PeliculaRepository extends JpaRepository<Pelicula, Long> {
    // Método para buscar películas por estado
    List<Pelicula> findByEstado(String estado);
    // Versión con ordenamiento
    List<Pelicula> findByEstado(String estado, Sort sort);

    // Buscar películas por título que contenga el texto (ignorando mayúsculas/minúsculas)
    List<Pelicula> findByTituloContainingIgnoreCase(String titulo);
    // Versión con ordenamiento
    List<Pelicula> findByTituloContainingIgnoreCase(String titulo, Sort sort);

    // Método para buscar por estado Y por título (opcional, pero útil)
    List<Pelicula> findByEstadoAndTituloContainingIgnoreCase(String estado, String titulo);
    // Versión con ordenamiento
    List<Pelicula> findByEstadoAndTituloContainingIgnoreCase(String estado, String titulo, Sort sort);
}
