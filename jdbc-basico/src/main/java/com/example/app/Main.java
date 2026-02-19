package com.example.app;

import com.example.dao.LibroDao;

public class Main {
    public static void main(String[] args) {
        var dao = new LibroDao();
        System.out.println("=== LIBROS ===");
        dao.findAll().forEach(System.out::println);
    }
}
