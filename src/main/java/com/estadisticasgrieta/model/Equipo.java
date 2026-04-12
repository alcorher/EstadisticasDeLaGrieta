package com.estadisticasgrieta.model;

public class Equipo {
    private Integer idEquipo;
    private String nombreEquipo;
    private Integer idRegion;

    public Equipo() {}

    public Equipo(String nombreEquipo, Integer idRegion) {
        this.nombreEquipo = nombreEquipo;
        this.idRegion = idRegion;
    }

    // Getters y Setters
    public Integer getIdEquipo() { return idEquipo; }
    public void setIdEquipo(Integer idEquipo) { this.idEquipo = idEquipo; }
    public String getNombreEquipo() { return nombreEquipo; }
    public void setNombreEquipo(String nombreEquipo) { this.nombreEquipo = nombreEquipo; }
    public Integer getIdRegion() { return idRegion; }
    public void setIdRegion(Integer idRegion) { this.idRegion = idRegion; }
}