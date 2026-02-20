package com.example.service;

import com.example.model.Libro;
import com.example.repository.LibroRepository;

import java.util.List;

public class LibroService {

    private final LibroRepository repo; // Dependencia inyectada

    // Inyección por constructor
    public LibroService(LibroRepository repo) {
        this.repo = repo;
    }

    // Listar todos los libros
    public List<Libro> listar() {
        return repo.findAll();
    }

    // Ver detalle de un libro por id
    public Libro verDetalle(int id) {
        return repo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("No existe libro con id=" + id));
    }

    // Dar de alta un libro nuevo
    public int alta(Libro libro) {
        if (libro.getTitulo() == null || libro.getTitulo().isBlank())
            throw new IllegalArgumentException("Título obligatorio");
        if (libro.getIsbn() == null || libro.getIsbn().isBlank())
            throw new IllegalArgumentException("ISBN obligatorio");

        return repo.insert(libro);
    }

    // Marcar un libro como no disponible
    public void marcarNoDisponible(int id) {
        Libro l = verDetalle(id);
        l.setDisponible(false);
        boolean ok = repo.update(l);
        if (!ok) throw new IllegalStateException("No se pudo actualizar id=" + id);
    }

    // Borrar un libro por id
    public void borrar(int id) {
        boolean ok = repo.deleteById(id);
        if (!ok) throw new IllegalArgumentException("No existe id=" + id);
    }
}
