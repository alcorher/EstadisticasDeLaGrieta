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
}