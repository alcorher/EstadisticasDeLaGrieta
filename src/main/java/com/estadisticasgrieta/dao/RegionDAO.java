package com.estadisticasgrieta.dao;

import com.estadisticasgrieta.model.Region;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RegionDAO {

    public void crear(Region region) {
        String sql = "INSERT INTO Regiones (nombre_servidor, ubicacion) VALUES (?, ?)";
        try (Connection conn = DButil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, region.getNombreServidor());
            pstmt.setString(2, region.getUbicacion());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Region> obtenerTodas() {
        List<Region> regiones = new ArrayList<>();
        String sql = "SELECT * FROM Regiones";
        try (Connection conn = DButil.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Region r = new Region();
                r.setIdRegion(rs.getInt("id_region"));
                r.setNombreServidor(rs.getString("nombre_servidor"));
                r.setUbicacion(rs.getString("ubicacion"));
                regiones.add(r);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return regiones;
    }


}