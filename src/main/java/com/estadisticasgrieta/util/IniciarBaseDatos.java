package com.estadisticasgrieta.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class IniciarBaseDatos {

    private static final String URL_BASE = "jdbc:mysql://localhost:3306/?serverTimezone=UTC";
    private static final String USER = "root";
    private static final String PASSWORD = ""; // Asegúrate de que esta sea tu clave real

    public static void inicializarBaseDeDatos() {
        String dbName = "grieta";

        // --- SOLUCIÓN AL ERROR DEL DRIVER ---
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.err.println("CRÍTICO: Driver MySQL no encontrado. Revisa tu pom.xml y recarga Maven.");
            return; // Detenemos la ejecución si no hay driver
        }
        // ------------------------------------

        try (Connection con = DriverManager.getConnection(URL_BASE, USER, PASSWORD);
             Statement st = con.createStatement()) {

            String createDb = "CREATE DATABASE IF NOT EXISTS " + dbName;
            st.executeUpdate(createDb);
            System.out.println("Base de datos '" + dbName + "' lista.");

            st.execute("USE " + dbName);

            String createRegiones = "CREATE TABLE IF NOT EXISTS Regiones (" +
                    "id_region INT AUTO_INCREMENT PRIMARY KEY, " +
                    "nombre_servidor VARCHAR(50) NOT NULL, " +
                    "ubicacion VARCHAR(100) NOT NULL" +
                    ")";
            st.executeUpdate(createRegiones);
            System.out.println("Tabla 'Regiones' lista.");

            String createEquipos = "CREATE TABLE IF NOT EXISTS Equipos (" +
                    "id_equipo INT AUTO_INCREMENT PRIMARY KEY, " +
                    "nombre_equipo VARCHAR(100) NOT NULL, " +
                    "id_region INT NOT NULL, " +
                    "FOREIGN KEY (id_region) REFERENCES Regiones(id_region) ON DELETE CASCADE" +
                    ")";
            st.executeUpdate(createEquipos);
            System.out.println("Tabla 'Equipos' Creada.");

            String createJugadores = "CREATE TABLE IF NOT EXISTS Jugadores (" +
                    "id_jugador INT AUTO_INCREMENT PRIMARY KEY, " +
                    "nickname VARCHAR(50) NOT NULL, " +
                    "rol_principal VARCHAR(50) NOT NULL, " +
                    "id_equipo INT NULL, " +
                    "FOREIGN KEY (id_equipo) REFERENCES Equipos(id_equipo) ON DELETE SET NULL" +
                    ")";
            st.executeUpdate(createJugadores);
            System.out.println("Tabla 'Jugadores' Creada.");

            System.out.println("BBDD Creada");

        } catch (SQLException e) {
            System.err.println("Error al inicializar la base de datos: " + e.getMessage());
            e.printStackTrace();
        }
    }
}