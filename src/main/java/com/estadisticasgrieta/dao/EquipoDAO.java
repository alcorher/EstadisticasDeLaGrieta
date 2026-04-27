package com.estadisticasgrieta.dao;

import com.estadisticasgrieta.model.Equipo;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EquipoDAO {

    public void crear(Equipo equipo) {
        String sql = "INSERT INTO Equipos (nombre_equipo, id_region) VALUES (?, ?)";
        try (Connection conn = DButil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, equipo.getNombreEquipo());
            pstmt.setInt(2, equipo.getIdRegion());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Equipo> obtenerTodos() {
        List<Equipo> equipos = new ArrayList<>();
        String sql = "SELECT * FROM Equipos";
        try (Connection conn = DButil.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Equipo e = new Equipo();
                e.setIdEquipo(rs.getInt("id_equipo"));
                e.setNombreEquipo(rs.getString("nombre_equipo"));
                e.setIdRegion(rs.getInt("id_region"));
                equipos.add(e);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return equipos;
    }

    public Equipo obtenerPorId(Integer id) {
        String sql = "SELECT * FROM Equipos WHERE id_equipo = ?";
        try (Connection conn = DButil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    Equipo e = new Equipo();
                    e.setIdEquipo(rs.getInt("id_equipo"));
                    e.setNombreEquipo(rs.getString("nombre_equipo"));
                    e.setIdRegion(rs.getInt("id_region"));
                    return e;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Equipo obtenerPorNombreEquipo(String nombreEquipo) {
        String sql = "SELECT * FROM Equipos WHERE LOWER(nombre_equipo) = LOWER(?) LIMIT 1";
        try (Connection conn = DButil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, nombreEquipo);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    Equipo equipo = new Equipo();
                    equipo.setIdEquipo(rs.getInt("id_equipo"));
                    equipo.setNombreEquipo(rs.getString("nombre_equipo"));
                    equipo.setIdRegion(rs.getInt("id_region"));
                    return equipo;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void actualizar(Equipo equipo) {
        String sql = "UPDATE Equipos SET nombre_equipo = ?, id_region = ? WHERE id_equipo = ?";
        try (Connection conn = DButil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, equipo.getNombreEquipo());
            pstmt.setInt(2, equipo.getIdRegion());
            pstmt.setInt(3, equipo.getIdEquipo());
            pstmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void eliminar(Integer id) {
        String sql = "DELETE FROM Equipos WHERE id_equipo = ?";
        try (Connection conn = DButil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            pstmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}