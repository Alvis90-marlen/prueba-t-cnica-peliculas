package com.vivetu.peliculas.controller;

import com.vivetu.peliculas.entity.Pelicula;
import com.vivetu.peliculas.repository.PeliculaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort; // Importar Sort
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/peliculas")
@CrossOrigin(origins = "http://localhost:4200")
public class PeliculaController {

    @Autowired
    private PeliculaRepository peliculaRepository;

    // READ ALL (GET /api/peliculas) - Modificado para incluir filtro, búsqueda y ordenamiento
    @GetMapping
    public List<Pelicula> listarPeliculas(
            @RequestParam(required = false) String estado,
            @RequestParam(required = false) String titulo, // Nuevo parámetro para búsqueda por título
            @RequestParam(required = false) String sortBy, // Nuevo parámetro para ordenar por campo (ej. "puntaje", "fechaCreacion")
            @RequestParam(required = false, defaultValue = "desc") String sortDirection) { // Nuevo parámetro para la dirección del ordenamiento ("asc", "desc")

        // Construir el objeto Sort
        Sort sort = Sort.unsorted(); // Por defecto, sin ordenamiento
        if (sortBy != null && !sortBy.isEmpty()) {
            // Asegurarse de que el campo de ordenamiento sea válido para evitar errores
            if (sortBy.equalsIgnoreCase("puntaje") || sortBy.equalsIgnoreCase("fechaCreacion") || sortBy.equalsIgnoreCase("titulo")) {
                if (sortDirection.equalsIgnoreCase("asc")) {
                    sort = Sort.by(Sort.Direction.ASC, sortBy);
                } else {
                    sort = Sort.by(Sort.Direction.DESC, sortBy);
                }
            } else {
                // Si sortBy no es un campo permitido, podemos ignorarlo o lanzar una excepción
                // Por ahora, lo ignoramos y no aplicamos ordenamiento
                System.err.println("Campo de ordenamiento no válido: " + sortBy);
            }
        }

        // Aplicar filtros y búsqueda
        if (estado != null && !estado.isEmpty()) {
            if (titulo != null && !titulo.isEmpty()) {
                // Filtrar por estado Y buscar por título
                // Usamos findAll con Sort para aplicar el ordenamiento
                return peliculaRepository.findByEstadoAndTituloContainingIgnoreCase(estado, titulo, sort);
            } else {
                // Solo filtrar por estado
                // Usamos findAll con Sort para aplicar el ordenamiento
                return peliculaRepository.findByEstado(estado, sort);
            }
        } else if (titulo != null && !titulo.isEmpty()) {
            // Solo buscar por título (sin filtro de estado)
            // Usamos findAll con Sort para aplicar el ordenamiento
            return peliculaRepository.findByTituloContainingIgnoreCase(titulo, sort);
        } else {
            // Devolver todas las películas (para administración), aplicando ordenamiento si existe
            return peliculaRepository.findAll(sort);
        }
    }

    // READ BY ID (GET /api/peliculas/{id})
    @GetMapping("/{id}")
    public ResponseEntity<Pelicula> obtenerPeliculaPorId(@PathVariable Long id) {
        Optional<Pelicula> pelicula = peliculaRepository.findById(id);
        return pelicula.map(ResponseEntity::ok)
                       .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // CREATE (POST /api/peliculas)
    @PostMapping
    public ResponseEntity<Pelicula> crearPelicula(@RequestBody Pelicula pelicula) {
        pelicula.setFechaCreacion(LocalDateTime.now());
        pelicula.setFechaModificacion(LocalDateTime.now());
        Pelicula nuevaPelicula = peliculaRepository.save(pelicula);
        return new ResponseEntity<>(nuevaPelicula, HttpStatus.CREATED);
    }

    // UPDATE (PUT /api/peliculas/{id})
    @PutMapping("/{id}")
    public ResponseEntity<Pelicula> actualizarPelicula(@PathVariable Long id, @RequestBody Pelicula peliculaDetalles) {
        Optional<Pelicula> peliculaExistente = peliculaRepository.findById(id);

        if (peliculaExistente.isPresent()) {
            Pelicula pelicula = peliculaExistente.get();
            pelicula.setTitulo(peliculaDetalles.getTitulo());
            pelicula.setDirector(peliculaDetalles.getDirector());
            pelicula.setAnio(peliculaDetalles.getAnio());
            pelicula.setGenero(peliculaDetalles.getGenero());
            pelicula.setImagen(peliculaDetalles.getImagen());
            pelicula.setDescripcion(peliculaDetalles.getDescripcion());
            pelicula.setPuntaje(peliculaDetalles.getPuntaje());
            pelicula.setEstado(peliculaDetalles.getEstado());
            pelicula.setFechaModificacion(LocalDateTime.now());

            Pelicula peliculaActualizada = peliculaRepository.save(pelicula);
            return ResponseEntity.ok(peliculaActualizada);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // DELETE (DELETE /api/peliculas/{id})
    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> eliminarPelicula(@PathVariable Long id) {
        if (peliculaRepository.existsById(id)) {
            peliculaRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}