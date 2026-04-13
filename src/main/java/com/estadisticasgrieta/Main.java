package com.estadisticasgrieta;

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
    }
}