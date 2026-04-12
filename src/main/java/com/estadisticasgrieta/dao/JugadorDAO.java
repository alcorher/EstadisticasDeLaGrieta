package com.estadisticasgrieta.dao;

import com.estadisticasgrieta.model.Jugador;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class JugadorDAO {

    //Importar desde el txt
    public void importarAgentesLibres(List<Jugador> jugadores) {
        String sql = "INSERT INTO Jugadores (nickname, rol_principal, id_equipo) VALUES (?, ?, ?)";

        try (Connection conn = DButil.getInstance();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            conn.setAutoCommit(false);

            for (Jugador jugador : jugadores) {
                pstmt.setString(1, jugador.getNickname());
                pstmt.setString(2, jugador.getRolPrincipal());
                pstmt.setNull(3, java.sql.Types.INTEGER); // Agente libre = NULL
                pstmt.addBatch();
            }

            pstmt.executeBatch();
            conn.commit();
            System.out.println("Importación de " + jugadores.size() + " agentes libres completada con éxito.");

        } catch (SQLException e) {
            System.err.println("Error al importar agentes libres: " + e.getMessage());
        }
    }


    public void crear(Jugador jugador) {
        String sql = "INSERT INTO Jugadores (nickname, rol_principal, id_equipo) VALUES (?, ?, ?)";
        try (Connection conn = DButil.getInstance();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            pstmt.setString(1, jugador.getNickname());
            pstmt.setString(2, jugador.getRolPrincipal());

            if (jugador.getIdEquipo() != null) {
                pstmt.setLong(3, jugador.getIdEquipo());
            } else {
                pstmt.setNull(3, java.sql.Types.INTEGER);
            }

            pstmt.executeUpdate();

            try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    jugador.setIdJugador(generatedKeys.getLong(1));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Jugador obtenerPorId(Long id) {
        String sql = "SELECT * FROM Jugadores WHERE id_jugador = ?";
        try (Connection conn = DButil.getInstance();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setLong(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    Jugador jugador = new Jugador();
                    jugador.setIdJugador(rs.getLong("id_jugador"));
                    jugador.setNickname(rs.getString("nickname"));
                    jugador.setRolPrincipal(rs.getString("rol_principal"));

                    long idEquipo = rs.getLong("id_equipo");
                    if (!rs.wasNull()) {
                        jugador.setIdEquipo(idEquipo);
                    }
                    return jugador;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Jugador> obtenerTodos() {
        List<Jugador> jugadores = new ArrayList<>();
        String sql = "SELECT * FROM Jugadores";

        try (Connection conn = DButil.getInstance();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Jugador jugador = new Jugador();
                jugador.setIdJugador(rs.getLong("id_jugador"));
                jugador.setNickname(rs.getString("nickname"));
                jugador.setRolPrincipal(rs.getString("rol_principal"));

                long idEquipo = rs.getLong("id_equipo");
                if (!rs.wasNull()) {
                    jugador.setIdEquipo(idEquipo);
                }
                jugadores.add(jugador);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return jugadores;
    }

    public void actualizar(Jugador jugador) {
        String sql = "UPDATE Jugadores SET nickname = ?, rol_principal = ?, id_equipo = ? WHERE id_jugador = ?";
        try (Connection conn = DButil.getInstance();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, jugador.getNickname());
            pstmt.setString(2, jugador.getRolPrincipal());

            if (jugador.getIdEquipo() != null) {
                pstmt.setLong(3, jugador.getIdEquipo());
            } else {
                pstmt.setNull(3, Types.INTEGER);
            }

            pstmt.setLong(4, jugador.getIdJugador());
            pstmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void eliminar(Long id) {
        String sql = "DELETE FROM Jugadores WHERE id_jugador = ?";
        try (Connection conn = DButil.getInstance();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setLong(1, id);
            pstmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}