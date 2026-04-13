package com.estadisticasgrieta.model;

public class Jugador {
    private Long idJugador;
    private String nickname;
    private String rolPrincipal;
    private Long idEquipo; // Puede ser null
    private Integer nivel;
    private String regionOrigen;

    public Jugador() {}

    public Jugador(String nickname, String rolPrincipal, Integer nivel, String regionOrigen) {
        this.nickname = nickname;
        this.rolPrincipal = rolPrincipal;
        this.nivel = nivel;
        this.regionOrigen = regionOrigen;
        this.idEquipo = null; // Los jugadores cargados incialmente no tienen equipo
    }

    // Getters y Setters
    public Long getIdJugador() { return idJugador; }
    public void setIdJugador(Long idJugador) { this.idJugador = idJugador; }

    public String getNickname() { return nickname; }
    public void setNickname(String nickname) { this.nickname = nickname; }

    public String getRolPrincipal() { return rolPrincipal; }
    public void setRolPrincipal(String rolPrincipal) { this.rolPrincipal = rolPrincipal; }

    public Long getIdEquipo() { return idEquipo; }
    public void setIdEquipo(Long idEquipo) { this.idEquipo = idEquipo; }

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

