package com.example.app;

import com.example.repository.jdbc.JdbcLibroRepository;
import com.example.service.TxLibroService;

public class TxDemo {
    public static void main(String[] args) {

        var repo = new JdbcLibroRepository();
        var tx = new TxLibroService(repo);

        System.out.println("=== DEMO TRANSACCION (rollback) ===");

        try {
            tx.marcarNoDisponiblePeroFallar(1);
        } catch (Exception e) {
            System.out.println("Esperado: " + e.getMessage());
        }

        System.out.println("Comprueba en la BD que el libro id=1 NO se quedó como disponible=false.");
    }
}
