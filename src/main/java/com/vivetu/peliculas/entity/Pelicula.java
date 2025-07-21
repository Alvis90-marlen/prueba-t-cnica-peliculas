package com.vivetu.peliculas.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Table;
import jakarta.persistence.Column; // Importar para @Column si es necesario
import java.time.LocalDateTime; // Importar para fechas

@Entity
@Table(name = "peliculas")
public class Pelicula {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String titulo; // Ya lo tienes
    private String director; // Ya lo tienes
    private Integer anio; // Ya lo tienes
    private String genero; // Ya lo tienes

    // --- NUEVOS CAMPOS ---
    private String imagen; // URL de la imagen de la cubierta
    private String descripcion;
    private Double puntaje; // Usamos Double para permitir decimales
    private String estado; // Por ejemplo: "publicada", "edicion"

    @Column(name = "fecha_creacion") // Mapea a la columna fecha_creacion en la DB
    private LocalDateTime fechaCreacion;

    @Column(name = "fecha_modificacion") // Mapea a la columna fecha_modificacion en la DB
    private LocalDateTime fechaModificacion;

    // Constructor sin argumentos (requerido por JPA)
    public Pelicula() {
    }

    // Constructor con todos los argumentos (útil para crear objetos Pelicula)
    public Pelicula(Long id, String titulo, String director, Integer anio, String genero,
                    String imagen, String descripcion, Double puntaje, String estado,
                    LocalDateTime fechaCreacion, LocalDateTime fechaModificacion) {
        this.id = id;
        this.titulo = titulo;
        this.director = director;
        this.anio = anio;
        this.genero = genero;
        this.imagen = imagen;
        this.descripcion = descripcion;
        this.puntaje = puntaje;
        this.estado = estado;
        this.fechaCreacion = fechaCreacion;
        this.fechaModificacion = fechaModificacion;
    }

    // --- Getters y Setters ---
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public Integer getAnio() {
        return anio;
    }

    public void setAnio(Integer anio) {
        this.anio = anio;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    // --- Getters y Setters para los NUEVOS CAMPOS ---
    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Double getPuntaje() {
        return puntaje;
    }

    public void setPuntaje(Double puntaje) {
        this.puntaje = puntaje;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public LocalDateTime getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(LocalDateTime fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public LocalDateTime getFechaModificacion() {
        return fechaModificacion;
    }

    public void setFechaModificacion(LocalDateTime fechaModificacion) {
        this.fechaModificacion = fechaModificacion;
    }
}