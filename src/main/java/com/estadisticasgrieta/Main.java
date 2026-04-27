package com.estadisticasgrieta;

import com.estadisticasgrieta.dao.EquipoDAO;
import com.estadisticasgrieta.dao.JugadorDAO;
import com.estadisticasgrieta.dao.RegionDAO;
import com.estadisticasgrieta.model.Equipo;
import com.estadisticasgrieta.model.Jugador;
import com.estadisticasgrieta.model.Region;
import com.estadisticasgrieta.util.IniciarBaseDatos;

import java.util.LinkedHashMap;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        IniciarBaseDatos.inicializarBaseDeDatos();

        RegionDAO regionDAO = new RegionDAO();
        EquipoDAO equipoDAO = new EquipoDAO();
        JugadorDAO jugadorDAO = new JugadorDAO();

        Map<String, String> regionesBase = new LinkedHashMap<>();
        regionesBase.put("KR", "Corea del Sur");
        regionesBase.put("EUW", "Europa Oeste");
        regionesBase.put("NA", "Norteamerica");
        regionesBase.put("CN", "China");
        regionesBase.put("LAN", "Latinoamerica Norte");

        for (Map.Entry<String, String> entry : regionesBase.entrySet()) {
            if (regionDAO.obtenerPorNombreServidor(entry.getKey()) == null) {
                regionDAO.crear(new Region(entry.getKey(), entry.getValue()));
            }
        }

        Map<String, Integer> idRegionPorNombre = new LinkedHashMap<>();
        for (Region region : regionDAO.obtenerTodas()) {
            idRegionPorNombre.put(region.getNombreServidor(), region.getIdRegion());
        }

        Map<String, String> equiposBase = new LinkedHashMap<>();
        equiposBase.put("T1", "KR");
        equiposBase.put("Gen.G", "KR");
        equiposBase.put("G2 Esports", "EUW");
        equiposBase.put("Fnatic", "EUW");
        equiposBase.put("Cloud9", "NA");
        equiposBase.put("Team Liquid", "NA");
        equiposBase.put("JD Gaming", "CN");
        equiposBase.put("Top Esports", "CN");
        equiposBase.put("Rainbow7", "LAN");

        for (Map.Entry<String, String> entry : equiposBase.entrySet()) {
            String nombreEquipo = entry.getKey();
            String nombreRegion = entry.getValue();

            if (equipoDAO.obtenerPorNombreEquipo(nombreEquipo) == null && idRegionPorNombre.containsKey(nombreRegion)) {
                equipoDAO.crear(new Equipo(nombreEquipo, idRegionPorNombre.get(nombreRegion)));
            }
        }

        Map<String, Integer> idEquipoPorNombre = new LinkedHashMap<>();
        for (Equipo equipo : equipoDAO.obtenerTodos()) {
            idEquipoPorNombre.put(equipo.getNombreEquipo(), equipo.getIdEquipo());
        }

        Map<String, String> asignacionesJugadorEquipo = new LinkedHashMap<>();
        asignacionesJugadorEquipo.put("Faker", "T1");
        asignacionesJugadorEquipo.put("Oner", "T1");
        asignacionesJugadorEquipo.put("Zeus", "T1");
        asignacionesJugadorEquipo.put("Gumayusi", "T1");
        asignacionesJugadorEquipo.put("Keria", "T1");
        asignacionesJugadorEquipo.put("Chovy", "Gen.G");
        asignacionesJugadorEquipo.put("Ruler", "Gen.G");
        asignacionesJugadorEquipo.put("Caps", "G2 Esports");
        asignacionesJugadorEquipo.put("Flakked", "G2 Esports");
        asignacionesJugadorEquipo.put("Jankos", "Fnatic");
        asignacionesJugadorEquipo.put("Bwipo", "Team Liquid");
        asignacionesJugadorEquipo.put("CoreJJ", "Team Liquid");
        asignacionesJugadorEquipo.put("Blaber", "Cloud9");
        asignacionesJugadorEquipo.put("Berserker", "Cloud9");
        asignacionesJugadorEquipo.put("Knight", "JD Gaming");
        asignacionesJugadorEquipo.put("JackeyLove", "Top Esports");
        asignacionesJugadorEquipo.put("Josedeodo", "Rainbow7");

        int jugadoresAsignados = 0;
        for (Jugador jugador : jugadorDAO.obtenerTodos()) {
            String equipoObjetivo = asignacionesJugadorEquipo.get(jugador.getNickname());
            Integer idEquipo = idEquipoPorNombre.get(equipoObjetivo);
            if (idEquipo != null) {
                jugador.setIdEquipo(idEquipo.longValue());
                jugadorDAO.actualizar(jugador);
                jugadoresAsignados++;
            }
        }

        System.out.println("Regiones registradas: " + regionDAO.obtenerTodas().size());
        System.out.println("Equipos registrados: " + equipoDAO.obtenerTodos().size());
        System.out.println("Jugadores asignados a equipos en esta ejecucion: " + jugadoresAsignados);

        Menu menu = new Menu(regionDAO, equipoDAO, jugadorDAO);
        menu.imprimirJugadoresConEquipoYRegion();
        menu.iniciar();
    }

}