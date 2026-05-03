package com.estadisticasgrieta.dao;

import com.estadisticasgrieta.model.Jugador;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class JugadorDAO {

    public void importarJugadores(List<Jugador> jugadores) {
        String sql = "INSERT INTO Jugadores (nickname, rol_principal, id_equipo) VALUES (?, ?, ?)";
        try (Connection conn = DButil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            for (Jugador jugador : jugadores) {
                pstmt.setString(1, jugador.getNickname());
                pstmt.setString(2, jugador.getRolPrincipal());
                pstmt.setNull(3, java.sql.Types.INTEGER);
                pstmt.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void crear(Jugador jugador) {
        String sql = "INSERT INTO Jugadores (nickname, rol_principal, id_equipo) VALUES (?, ?, ?)";
        try (Connection conn = DButil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, jugador.getNickname());
            pstmt.setString(2, jugador.getRolPrincipal());
            if (jugador.getIdEquipo() != null) {
                pstmt.setInt(3, jugador.getIdEquipo()); // CAMBIO
            } else {
                pstmt.setNull(3, java.sql.Types.INTEGER);
            }
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Jugador obtenerPorId(Long id) {
        String sql = "SELECT * FROM Jugadores WHERE id_jugador = ?";
        try (Connection conn = DButil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setLong(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    Jugador jugador = new Jugador();
                    jugador.setIdJugador(rs.getLong("id_jugador"));
                    jugador.setNickname(rs.getString("nickname"));
                    jugador.setRolPrincipal(rs.getString("rol_principal"));

                    int idEquipo = rs.getInt("id_equipo"); // CAMBIO
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
        try (Connection conn = DButil.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Jugador jugador = new Jugador();
                jugador.setIdJugador(rs.getLong("id_jugador"));
                jugador.setNickname(rs.getString("nickname"));
                jugador.setRolPrincipal(rs.getString("rol_principal"));

                int idEquipo = rs.getInt("id_equipo"); // CAMBIO
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
        try (Connection conn = DButil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, jugador.getNickname());
            pstmt.setString(2, jugador.getRolPrincipal());
            if (jugador.getIdEquipo() != null) {
                pstmt.setInt(3, jugador.getIdEquipo()); // CAMBIO
            } else {
                pstmt.setNull(3, java.sql.Types.INTEGER);
            }
            pstmt.setLong(4, jugador.getIdJugador());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void eliminar(Long id) {
        String sql = "DELETE FROM Jugadores WHERE id_jugador = ?";
        try (Connection conn = DButil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setLong(1, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}