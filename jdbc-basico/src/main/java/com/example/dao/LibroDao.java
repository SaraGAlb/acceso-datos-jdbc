package com.example.dao;

import com.example.db.Db;
import com.example.model.Libro;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class LibroDao {

    // Método privado que convierte una fila del ResultSet en un objeto Libro
    private Libro mapRow(ResultSet rs) throws SQLException {
    int id = rs.getInt("id");               // Obtiene el id
    String titulo = rs.getString("titulo");// Obtiene el título
    String isbn = rs.getString("isbn");     // Obtiene el ISBN
    Integer anio = (Integer) rs.getObject("anio"); // getObject permite que sea NULL
    boolean disponible = rs.getBoolean("disponible");   // Obtiene si está disponible
    return new Libro(id, titulo, isbn, anio, disponible); // Crea y devuelve un objeto Libro con los datos obtenidos
 }

     // Devuelve todos los libros de la tabla libro
    public List<Libro> findAll() {String sql = "SELECT id, titulo, isbn, anio, disponible FROM libro ORDER BY id";
        // try-with-resources: cierra automáticamente conexión, statement y resultset
        try (Connection con = Db.getConnection();
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery()) {

            List<Libro> libros = new ArrayList<>();
            while (rs.next()) { // Recorre cada fila del resultado
                libros.add(mapRow(rs)); // Convierte la fila en objeto Libro
            }
            return libros;

        } catch (SQLException e) {
            throw new RuntimeException("Error leyendo libros", e);
        }
    }

    // Busca libros cuyo título contenga el texto indicado
     public List<Libro> searchByTitulo(String texto) {
        if(texto == null || texto.isBlank()){
            return List.of();  // Si viene vacío o nulo, devuelve lista vacía
        }
        // Consulta SQL que selecciona los campos de la tabla libro
        // LOWER(titulo) convierte el título a minúsculas para hacer la búsqueda sin distinguir mayúsculas/minúsculas
        // LIKE ? permite usar un patrón (comodín) que se asignará después con PreparedStatement
        // ORDER BY id ordena los resultados por id
        String sql = "SELECT id, titulo, isbn, anio, disponible FROM libro WHERE LOWER(titulo) LIKE ? ORDER BY id";
        
        // Se construye el patrón de búsqueda: % significa "cualquier cosa antes o después"
        // texto.toLowerCase() pasa el texto a minúsculas para que coincida con LOWER(titulo)
        String patron = "%" + texto.toLowerCase() + "%";

        try (Connection con = Db.getConnection();
            PreparedStatement ps = con.prepareStatement(sql)) {
            // Se asigna el valor al parámetro ? del LIKE
            ps.setString(1, patron);
        // Se ejecuta la consulta y se obtiene el ResultSet
        try (ResultSet rs = ps.executeQuery()) {
            List<Libro> resultados = new ArrayList<>();
            while (rs.next()) {
                resultados.add(mapRow(rs));
            }

        return resultados;
        }

        }catch (SQLException e) {
            throw new RuntimeException("Error buscando libros", e);
        }
    }
    

    // Busca un libro por su id
    public Optional<Libro> findById(int id) {
        String sql = "SELECT id, titulo, isbn, anio, disponible FROM libro WHERE id = ?";

        try (Connection con = Db.getConnection();
            PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);    // Asigna el valor del parámetro ?

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(mapRow(rs));
                }
                return Optional.empty(); // Si no existe, devuelve vacío
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error buscando libro por id=" + id, e);
        }
    }

    // Inserta un libro en la base de datos
    public int insert(Libro libro) {
        String sql = "INSERT INTO libro (titulo, isbn, anio, disponible) VALUES (?,?,?,?)";
        try (Connection con = Db.getConnection();
            PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

                ps.setString(1, libro.getTitulo());
                ps.setString(2, libro.getIsbn());
                ps.setObject(3, libro.getAnio()); // admite NULL
                ps.setBoolean(4, libro.isDisponible());

                int rows = ps.executeUpdate();  // Ejecuta el INSERT
                if (rows != 1) {
                throw new SQLException("INSERT inesperado. Filas afectadas: " + rows);
                }
 
                try (ResultSet keys = ps.getGeneratedKeys()) {
                    if (keys.next()) {
                        int id = keys.getInt(1);
                        libro.setId(id);
                        return id;
                    }
                    throw new SQLException("No se devolvió id generado (getGeneratedKeys vacío)");
                }
            } catch (SQLException e) {
                throw new RuntimeException("Error insertando libro", e);
            }
 
        }

        // Actualiza un libro existente
        public boolean update(Libro libro) {
 
            if (libro.getId() == null) {
                throw new IllegalArgumentException("No se puede UPDATE sin id. ¿Hiciste insert primero?");
            }

            String sql = "UPDATE libro SET titulo=?, isbn=?, anio=?, disponible=? WHERE id=?";
 
            try (Connection con = Db.getConnection();
            PreparedStatement ps = con.prepareStatement(sql)) {
                ps.setString(1, libro.getTitulo());
                ps.setString(2, libro.getIsbn());
                ps.setObject(3, libro.getAnio());
                ps.setBoolean(4, libro.isDisponible());
                ps.setInt(5, libro.getId());
 
                return ps.executeUpdate() == 1; // true si actualiza 1 fila
 
            } catch (SQLException e) {
                throw new RuntimeException("Error actualizando libro id=" + libro.getId(), e);
            }
 
        }

         // Borra un libro por su id
        public boolean deleteById(int id) {
            String sql = "DELETE FROM libro WHERE id=?";
 
            try (Connection con = Db.getConnection();
            PreparedStatement ps = con.prepareStatement(sql)) { 
                ps.setInt(1, id); 
                return ps.executeUpdate() == 1;     // true si borra 1 fila
            } catch (SQLException e) {
                throw new RuntimeException("Error borrando libro id=" + id, e);
            }

        }

}