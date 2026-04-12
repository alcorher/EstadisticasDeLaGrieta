package com.estadisticasgrieta.model;

public class Region {
    private Integer idRegion;
    private String nombreServidor;
    private String ubicacion;

    public Region() {}

    public Region(String nombreServidor, String ubicacion) {
        this.nombreServidor = nombreServidor;
        this.ubicacion = ubicacion;
    }

    // Getters y Setters
    public Integer getIdRegion() { return idRegion; }
    public void setIdRegion(Integer idRegion) { this.idRegion = idRegion; }
    public String getNombreServidor() { return nombreServidor; }
    public void setNombreServidor(String nombreServidor) { this.nombreServidor = nombreServidor; }
    public String getUbicacion() { return ubicacion; }
    public void setUbicacion(String ubicacion) { this.ubicacion = ubicacion; }
}