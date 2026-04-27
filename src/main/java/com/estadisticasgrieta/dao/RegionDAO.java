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

    public Region obtenerPorId(Integer id) {
        String sql = "SELECT * FROM Regiones WHERE id_region = ?";
        try (Connection conn = DButil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    Region r = new Region();
                    r.setIdRegion(rs.getInt("id_region"));
                    r.setNombreServidor(rs.getString("nombre_servidor"));
                    r.setUbicacion(rs.getString("ubicacion"));
                    return r;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Region obtenerPorNombreServidor(String nombreServidor) {
        String sql = "SELECT * FROM Regiones WHERE LOWER(nombre_servidor) = LOWER(?) LIMIT 1";
        try (Connection conn = DButil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, nombreServidor);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    Region region = new Region();
                    region.setIdRegion(rs.getInt("id_region"));
                    region.setNombreServidor(rs.getString("nombre_servidor"));
                    region.setUbicacion(rs.getString("ubicacion"));
                    return region;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void actualizar(Region region) {
        String sql = "UPDATE Regiones SET nombre_servidor = ?, ubicacion = ? WHERE id_region = ?";
        try (Connection conn = DButil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, region.getNombreServidor());
            pstmt.setString(2, region.getUbicacion());
            pstmt.setInt(3, region.getIdRegion());
            pstmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void eliminar(Integer id) {
        String sql = "DELETE FROM Regiones WHERE id_region = ?";
        try (Connection conn = DButil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            pstmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


}