package com.example.service;

import com.example.db.Db;
import com.example.model.Prestamo;
import com.example.repository.jdbc.JdbcLibroRepository;
import com.example.repository.jdbc.JdbcPrestamoRepository;

import java.sql.Connection;
import java.time.LocalDate;

public class PrestamoService {

    private final JdbcLibroRepository libroRepo;
    private final JdbcPrestamoRepository prestamoRepo;

    public PrestamoService(JdbcLibroRepository libroRepo, JdbcPrestamoRepository prestamoRepo) {
        this.libroRepo = libroRepo;
        this.prestamoRepo = prestamoRepo;
    }

    public Prestamo prestarLibro(long usuarioId, int libroId, LocalDate inicio, LocalDate fin) {
        try (Connection con = Db.getConnection()) {

            // 1. EMPEZAR TRANSACCIÓN
            con.setAutoCommit(false);

            try {
                // 2. COMPROBAR SI ESTÁ DISPONIBLE
                if (!libroRepo.isDisponible(con, libroId)) {
                    throw new IllegalStateException("El libro no está disponible (o no existe). id=" + libroId);
                }

                // 3. CREAR PRÉSTAMO
                Prestamo p = new Prestamo(usuarioId, libroId, inicio, fin);
                Prestamo guardado = prestamoRepo.insert(con, p);

                // 4. MARCAR LIBRO COMO NO DISPONIBLE
                boolean ok = libroRepo.updateDisponible(con, libroId, false);
                if (!ok) {
                    throw new IllegalStateException("No se pudo marcar como no disponible id=" + libroId);
                }

                // 5. COMMIT
                con.commit();

                return guardado;

            } catch (Exception e) {
                // 6. ROLLBACK SI FALLA ALGO
                con.rollback();
                throw e;

            } finally {
                // 7. VOLVER A AUTO-COMMIT
                con.setAutoCommit(true);
            }

        } catch (Exception e) {
            throw new RuntimeException("Error al prestar (rollback realizado): " + e.getMessage(), e);
        }
    }

    public long devolverLibro(int libroId) {
        try (Connection con = Db.getConnection()) {

            con.setAutoCommit(false);

            try {
                // 1. MARCAR PRÉSTAMO COMO DEVUELTO
                long prestamoId = prestamoRepo.marcarDevuelto(con, libroId);

                // 2. MARCAR LIBRO COMO DISPONIBLE
                boolean ok = libroRepo.updateDisponible(con, libroId, true);
                if (!ok) {
                    throw new IllegalStateException("No se pudo marcar disponible id=" + libroId);
                }

                // 3. COMMIT
                con.commit();

                return prestamoId;

            } catch (Exception e) {
                con.rollback();
                throw e;

            } finally {
                con.setAutoCommit(true);
            }

        } catch (Exception e) {
            throw new RuntimeException("Error al devolver (rollback realizado): " + e.getMessage(), e);
        }
    }
}