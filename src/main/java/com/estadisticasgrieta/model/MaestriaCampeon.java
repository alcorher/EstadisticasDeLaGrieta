package com.estadisticasgrieta.model;

import jakarta.persistence.*;

@Entity
@Table(name = "Maestrias_Campeon")
public class MaestriaCampeon {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_maestria")
    private Long idMaestria;

    @Column(name = "nombre_campeon", nullable = false)
    private String nombreCampeon;

    @Column(name = "puntos_totales", nullable = false)
    private Integer puntosTotales;

    // Relación ManyToOne con la entidad Jugador[cite: 10]
    @ManyToOne
    @JoinColumn(name = "id_jugador", nullable = false)
    private Jugador jugador;

    public MaestriaCampeon() {}

    public MaestriaCampeon(String nombreCampeon, Integer puntosTotales, Jugador jugador) {
        this.nombreCampeon = nombreCampeon;
        this.puntosTotales = puntosTotales;
        this.jugador = jugador;
    }

    // Getters y Setters
    public Long getIdMaestria() { return idMaestria; }
    public void setIdMaestria(Long idMaestria) { this.idMaestria = idMaestria; }

    public String getNombreCampeon() { return nombreCampeon; }
    public void setNombreCampeon(String nombreCampeon) { this.nombreCampeon = nombreCampeon; }

    public Integer getPuntosTotales() { return puntosTotales; }
    public void setPuntosTotales(Integer puntosTotales) { this.puntosTotales = puntosTotales; }

    public Jugador getJugador() { return jugador; }
    public void setJugador(Jugador jugador) { this.jugador = jugador; }
}