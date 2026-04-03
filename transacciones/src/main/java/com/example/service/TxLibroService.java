package com.example.service;

import com.example.db.Db;
import com.example.model.Libro;
import com.example.repository.jdbc.JdbcLibroRepository;

import java.sql.Connection;

public class TxLibroService {

    private final JdbcLibroRepository jdbcRepo;

    public TxLibroService(JdbcLibroRepository jdbcRepo) {
        this.jdbcRepo = jdbcRepo;
    }

    public void marcarNoDisponiblePeroFallar(int idLibro) {

        try (Connection con = Db.getConnection()) {

            // Empezar transacción
            con.setAutoCommit(false);

            try {
                // Leer libro
                Libro l = jdbcRepo.findByIdOrNull(con, idLibro);

                if (l == null) {
                    throw new IllegalArgumentException("No existe libro id=" + idLibro);
                }

                // Cambiar estado
                l.setDisponible(false);

                // Guardar en BD
                boolean ok = jdbcRepo.update(con, l);

                if (!ok) {
                    throw new IllegalStateException("No se pudo actualizar id=" + idLibro);
                }

                //  Forzar error
                //throw new RuntimeException("Fallo forzado para demostrar rollback");

                // (no se ejecuta)
                 con.commit();

            } catch (Exception e) {

                //  rollback si algo falla
                con.rollback();
                throw e;

            } finally {
                //  restaurar autocommit
                con.setAutoCommit(true);
            }

        } catch (Exception e) {
            throw new RuntimeException(
                "TxLibroService: se hizo rollback. Motivo: " + e.getMessage(), e
            );
        }
    }
}
