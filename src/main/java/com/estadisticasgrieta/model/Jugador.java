package com.estadisticasgrieta.model;

import jakarta.persistence.*;

@Entity
@Table(name = "Jugadores")
public class Jugador {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_jugador")
    private Long idJugador;

    @Column(name = "nickname")
    private String nickname;

    @Column(name = "rol_principal")
    private String rolPrincipal;

    @Column(name = "id_equipo")
    private Integer idEquipo; // CAMBIO: De Long a Integer para coincidir con el INT de MySQL

    @Transient
    private Integer nivel;

    @Transient
    private String regionOrigen;

    public Jugador() {}

    public Jugador(String nickname, String rolPrincipal, Integer nivel, String regionOrigen) {
        this.nickname = nickname;
        this.rolPrincipal = rolPrincipal;
        this.nivel = nivel;
        this.regionOrigen = regionOrigen;
        this.idEquipo = null;
    }

    // Getters y Setters
    public Long getIdJugador() { return idJugador; }
    public void setIdJugador(Long idJugador) { this.idJugador = idJugador; }

    public String getNickname() { return nickname; }
    public void setNickname(String nickname) { this.nickname = nickname; }

    public String getRolPrincipal() { return rolPrincipal; }
    public void setRolPrincipal(String rolPrincipal) { this.rolPrincipal = rolPrincipal; }

    public Integer getIdEquipo() { return idEquipo; } // CAMBIO
    public void setIdEquipo(Integer idEquipo) { this.idEquipo = idEquipo; } // CAMBIO

    public Integer getNivel() { return nivel; }
    public void setNivel(Integer nivel) { this.nivel = nivel; }

    public String getRegionOrigen() { return regionOrigen; }
    public void setRegionOrigen(String regionOrigen) { this.regionOrigen = regionOrigen; }

    @Override
    public String toString() {
        return "Jugador{" +
                "idJugador=" + idJugador +
                ", nickname='" + nickname + '\'' +
                ", rolPrincipal='" + rolPrincipal + '\'' +
                ", idEquipo=" + idEquipo +
                ", nivel=" + nivel +
                ", regionOrigen='" + regionOrigen + '\'' +
                '}';
    }
}