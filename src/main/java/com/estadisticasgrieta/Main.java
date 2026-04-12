package com.estadisticasgrieta;

import com.estadisticasgrieta.dao.EquipoDAO;
import com.estadisticasgrieta.dao.RegionDAO;
import com.estadisticasgrieta.model.Equipo;
import com.estadisticasgrieta.model.Region;
import com.estadisticasgrieta.util.IniciarBaseDatos;
import com.estadisticasgrieta.util.AgentesLibresReader;
import com.estadisticasgrieta.dao.JugadorDAO;
import com.estadisticasgrieta.model.Jugador;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        // 1. Inicializar la base de datos y las tablas automáticamente
        System.out.println("--- Iniciando configuración de base de datos ---");
        IniciarBaseDatos.inicializarBaseDeDatos();
        System.out.println("------------------------------------------------\n");

        // 2. Fase 1: Lectura del TXT
        List<Jugador> agentesLibres = AgentesLibresReader.leerAgentesLibres("agentes_libres.txt");

        // 3. Fase 2: Persistencia de los datos en MySQL
        JugadorDAO jugadorDAO = new JugadorDAO();
        jugadorDAO.importarAgentesLibres(agentesLibres);


        // Ejemplo para rellenar Regiones y Equipos para la demo
        RegionDAO regionDAO = new RegionDAO();
        regionDAO.crear(new Region("KR", "Corea del Sur"));
        regionDAO.crear(new Region("EUW", "Europa Oeste"));

        EquipoDAO equipoDAO = new EquipoDAO();
        equipoDAO.crear(new Equipo("T1", 1)); // Asumiendo que KR es ID 1
        equipoDAO.crear(new Equipo("G2 Esports", 2));

        System.out.println("Demo: Regiones y Equipos creados para la entrega.");
    }
}