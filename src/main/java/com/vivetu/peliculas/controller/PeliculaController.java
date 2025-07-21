package com.vivetu.peliculas.controller;

import com.vivetu.peliculas.entity.Pelicula;
import com.vivetu.peliculas.repository.PeliculaRepository;
import org.springframework.beans.factory.annotation.Autowired;
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

    // READ ALL (GET /api/peliculas) - Modificado para incluir filtro por estado
    @GetMapping
    public List<Pelicula> listarPeliculas(@RequestParam(required = false) String estado) {
        if (estado != null && !estado.isEmpty()) {
            // Si se proporciona un estado, filtra por ese estado
            // Necesitamos añadir un método de búsqueda en el repositorio para esto
            return peliculaRepository.findByEstado(estado);
        } else {
            // Si no se proporciona estado, devuelve todas las películas (para administración)
            return peliculaRepository.findAll();
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
