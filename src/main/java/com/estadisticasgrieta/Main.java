package com.estadisticasgrieta;

import com.estadisticasgrieta.util.IniciarBaseDatos;
import com.estadisticasgrieta.util.AgentesLibresReader;
import com.estadisticasgrieta.dao.JugadorDAO;
import com.estadisticasgrieta.model.Jugador;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        IniciarBaseDatos.inicializarBaseDeDatos();
        List<Jugador> agentesLibres = AgentesLibresReader.leerAgentesLibres("agentes_libres.txt");
        JugadorDAO jugadorDAO = new JugadorDAO();
        jugadorDAO.importarAgentesLibres(agentesLibres);
    }
}