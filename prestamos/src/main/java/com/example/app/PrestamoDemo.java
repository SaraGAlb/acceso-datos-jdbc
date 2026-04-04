package com.example.app;

import com.example.model.Prestamo;
import com.example.repository.jdbc.JdbcLibroRepository;
import com.example.repository.jdbc.JdbcPrestamoRepository;
import com.example.service.PrestamoService;

import java.time.LocalDate;

public class PrestamoDemo {

    public static void main(String[] args) {

        var libroRepo = new JdbcLibroRepository();
        var prestamoRepo = new JdbcPrestamoRepository();
        var service = new PrestamoService(libroRepo, prestamoRepo);

        System.out.println("=== PRESTAR (OK) ===");
        Prestamo p = service.prestarLibro(
                1L,
                1,
                LocalDate.now(),
                LocalDate.now().plusDays(14)
        );
        System.out.println("Creado: " + p);

        System.out.println("\n=== PRESTAR MISMO LIBRO (DEBE FALLAR) ===");
        try {
            service.prestarLibro(
                    2L,
                    1,
                    LocalDate.now(),
                    LocalDate.now().plusDays(7)
            );
        } catch (Exception e) {
            System.out.println("Esperado: " + e.getMessage());
        }

        System.out.println("\n=== DEVOLVER (OK) ===");
        long id = service.devolverLibro(1);
        System.out.println("Devuelto préstamo id=" + id);
    }
}
