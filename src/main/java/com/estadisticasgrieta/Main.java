package com.estadisticasgrieta;

import com.estadisticasgrieta.dao.EquipoDAO;
import com.estadisticasgrieta.dao.RegionDAO;
import com.estadisticasgrieta.model.Equipo;
import com.estadisticasgrieta.model.Region;
import com.estadisticasgrieta.util.IniciarBaseDatos;
import com.estadisticasgrieta.util.jugadoresReader;
import com.estadisticasgrieta.dao.JugadorDAO;
import com.estadisticasgrieta.model.Jugador;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        IniciarBaseDatos.inicializarBaseDeDatos();
        //Si ejecutamos el main varias veces acabaremos con estos jugadores importados muchas veces
        List<Jugador> agentesLibres = jugadoresReader.leerJugadoresTXT("agentes_libres.txt");
        System.out.println("Datos leidos del archivo:");
        for (Jugador jugador : agentesLibres) {
            System.out.println(jugador);
        }
        JugadorDAO jugadorDAO = new JugadorDAO();
        jugadorDAO.importarAgentesLibres(agentesLibres);


        // Ejemplo para rellenar Regiones y Equipos para la demo
        RegionDAO regionDAO = new RegionDAO();
        regionDAO.crear(new Region("KR", "Corea del Sur"));
        regionDAO.crear(new Region("EUW", "Europa Oeste"));

        EquipoDAO equipoDAO = new EquipoDAO();
        equipoDAO.crear(new Equipo("T1", 1)); // Asumiendo que KR es ID 1
        equipoDAO.crear(new Equipo("G2 Esports", 2));

        System.out.println("Demo: Regiones y Equipos creados.");
    }
}