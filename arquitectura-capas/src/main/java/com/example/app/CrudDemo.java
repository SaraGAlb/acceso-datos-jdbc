package com.example.app;

import com.example.model.Libro;
import com.example.repository.LibroRepository;
import com.example.repository.jdbc.JdbcLibroRepository;
import com.example.service.LibroService;

public class CrudDemo {
    public static void main(String[] args) {

        // Crear repositorio y servicio
        LibroRepository repo = new JdbcLibroRepository();
        LibroService service = new LibroService(repo);

        // LISTADO
        System.out.println("=== LISTADO ===");
        service.listar().forEach(System.out::println);

        // ALTA
        System.out.println("\n=== ALTA ===");
        Libro nuevo = new Libro("Clean Code", "9780132350884-X", 2008, true);
        int id = service.alta(nuevo);
        System.out.println("Creado: " + nuevo);

        // MARCAR NO DISPONIBLE
        System.out.println("\n=== MARCAR NO DISPONIBLE ===");
        service.marcarNoDisponible(id);
        System.out.println(service.verDetalle(id));

        // BORRAR
        System.out.println("\n=== BORRAR ===");
        service.borrar(id);
        System.out.println("Borrado OK");
    }
}


