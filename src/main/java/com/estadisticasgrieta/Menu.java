package com.estadisticasgrieta;

import com.estadisticasgrieta.dao.EquipoDAO;
import com.estadisticasgrieta.dao.JugadorDAO;
import com.estadisticasgrieta.dao.MaestriaDAO;
import com.estadisticasgrieta.dao.RegionDAO;
import com.estadisticasgrieta.model.Equipo;
import com.estadisticasgrieta.model.Jugador;
import com.estadisticasgrieta.model.MaestriaCampeon; // NUEVO
import com.estadisticasgrieta.model.Region;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Menu {
    private final RegionDAO regionDAO;
    private final EquipoDAO equipoDAO;
    private final JugadorDAO jugadorDAO;
    private final MaestriaDAO maestriaDAO; // NUEVO

    // CONSTRUCTOR ACTUALIZADO
    public Menu(RegionDAO regionDAO, EquipoDAO equipoDAO, JugadorDAO jugadorDAO, MaestriaDAO maestriaDAO) {
        this.regionDAO = regionDAO;
        this.equipoDAO = equipoDAO;
        this.jugadorDAO = jugadorDAO;
        this.maestriaDAO = maestriaDAO;
    }

    public void iniciar() {
        Scanner scanner = new Scanner(System.in);
        boolean salir = false;

        while (!salir) {
            System.out.println("\n====== MENU DE ESTADÍSTICAS DE LA GRIETA ======");
            System.out.println("1. Ver jugadores con equipo y region");
            System.out.println("2. Ver regiones");
            System.out.println("3. Ver equipos");
            System.out.println("4. Crear region");
            System.out.println("5. Crear equipo");
            System.out.println("6. Crear jugador");
            System.out.println("7. Asignar jugador a equipo");
            System.out.println("----------------------------------------------");
            System.out.println("8. Ver maestrías de un jugador (Hibernate)"); // NUEVO
            System.out.println("9. Añadir maestría a un jugador (Hibernate)"); // NUEVO
            System.out.println("----------------------------------------------");
            System.out.println("0. Salir");

            int opcion = leerEntero(scanner, "Elige una opcion: ");

            switch (opcion) {
                case 1:
                    imprimirJugadoresConEquipoYRegion();
                    break;
                case 2:
                    imprimirRegiones();
                    break;
                case 3:
                    imprimirEquipos();
                    break;
                case 4:
                    crearRegionDesdeMenu(scanner);
                    break;
                case 5:
                    crearEquipoDesdeMenu(scanner);
                    break;
                case 6:
                    crearJugadorDesdeMenu(scanner);
                    break;
                case 7:
                    asignarJugadorAEquipoDesdeMenu(scanner);
                    break;
                case 8: // NUEVO
                    verMaestriasDeJugador(scanner);
                    break;
                case 9: // NUEVO
                    anadirMaestriaAJugador(scanner);
                    break;
                case 0:
                    salir = true;
                    System.out.println("Saliendo del menu...");
                    // Cerramos la factoría de Hibernate al salir
                    com.estadisticasgrieta.util.HibernateUtil.shutdown();
                    break;
                default:
                    System.out.println("Opcion no valida.");
                    break;
            }
        }
    }

    // --- NUEVOS MÉTODOS PARA HIBERNATE ---

    private void verMaestriasDeJugador(Scanner scanner) {
        System.out.print("Introduce el Nickname del jugador para ver sus maestrías: ");
        String nickname = scanner.nextLine().trim();

        Jugador jugador = buscarJugadorPorNickname(nickname);
        if (jugador == null) {
            System.out.println("No existe un jugador con ese nickname.");
            return;
        }

        // Usamos Hibernate para obtener las maestrías
        List<MaestriaCampeon> maestrias = maestriaDAO.obtenerPorJugador(jugador.getIdJugador());

        if (maestrias.isEmpty()) {
            System.out.println("El jugador " + jugador.getNickname() + " no tiene maestrías registradas.");
        } else {
            System.out.println("\n--- Maestrías de " + jugador.getNickname() + " ---");
            for (MaestriaCampeon maestria : maestrias) {
                System.out.println("- Campeón: " + maestria.getNombreCampeon() + " | Puntos: " + maestria.getPuntosTotales());
            }
        }
    }

    private void anadirMaestriaAJugador(Scanner scanner) {
        System.out.print("Introduce el Nickname del jugador: ");
        String nickname = scanner.nextLine().trim();

        Jugador jugador = buscarJugadorPorNickname(nickname);
        if (jugador == null) {
            System.out.println("No existe un jugador con ese nickname.");
            return;
        }

        System.out.print("Nombre del Campeón: ");
        String nombreCampeon = scanner.nextLine().trim();

        System.out.print("Puntos totales de maestría: ");
        int puntosTotales = leerEntero(scanner, "");

        if (nombreCampeon.isEmpty() || puntosTotales < 0) {
            System.out.println("Datos inválidos. El campeón no puede estar vacío y los puntos no pueden ser negativos.");
            return;
        }

        // Creamos y guardamos la maestría usando Hibernate
        MaestriaCampeon nuevaMaestria = new MaestriaCampeon(nombreCampeon, puntosTotales, jugador);
        maestriaDAO.crear(nuevaMaestria);
        System.out.println("¡Maestría añadida correctamente a " + jugador.getNickname() + " mediante Hibernate!");
    }

    // --- FIN NUEVOS MÉTODOS ---

    public void imprimirJugadoresConEquipoYRegion() {
        Map<Integer, Equipo> equiposPorId = new LinkedHashMap<>();
        for (Equipo equipo : equipoDAO.obtenerTodos()) {
            equiposPorId.put(equipo.getIdEquipo(), equipo);
        }

        Map<Integer, String> regionPorId = new LinkedHashMap<>();
        for (Region region : regionDAO.obtenerTodas()) {
            regionPorId.put(region.getIdRegion(), region.getNombreServidor());
        }

        System.out.println("\nListado de jugadores con equipo y region:");
        for (Jugador jugador : jugadorDAO.obtenerTodos()) {
            String nombreEquipo = "Sin equipo";
            String nombreRegion = "Sin region";

            if (jugador.getIdEquipo() != null) {
                Equipo equipo = equiposPorId.get(jugador.getIdEquipo().intValue());
                if (equipo != null) {
                    nombreEquipo = equipo.getNombreEquipo();
                    nombreRegion = regionPorId.getOrDefault(equipo.getIdRegion(), "Region desconocida");
                } else {
                    nombreEquipo = "Equipo desconocido";
                    nombreRegion = "Region desconocida";
                }
            }

            System.out.println("- " + jugador.getNickname()
                    + " (" + jugador.getRolPrincipal() + ")"
                    + " | Equipo: " + nombreEquipo
                    + " | Region del equipo: " + nombreRegion);
        }
    }

    private int leerEntero(Scanner scanner, String mensaje) {
        while (true) {
            System.out.print(mensaje);
            String input = scanner.nextLine().trim();
            if (input.isEmpty()) { // Si el usuario solo da Enter, no hacemos nada y el scanner pide otra vez
                continue;
            }
            try {
                return Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println("Introduce un numero valido.");
            }
        }
    }

    private void imprimirRegiones() {
        System.out.println("\nRegiones registradas:");
        for (Region region : regionDAO.obtenerTodas()) {
            System.out.println("- " + region.getIdRegion() + " | " + region.getNombreServidor() + " | " + region.getUbicacion());
        }
    }

    private void imprimirEquipos() {
        Map<Integer, String> regionPorId = new LinkedHashMap<>();
        for (Region region : regionDAO.obtenerTodas()) {
            regionPorId.put(region.getIdRegion(), region.getNombreServidor());
        }

        System.out.println("\nEquipos registrados:");
        for (Equipo equipo : equipoDAO.obtenerTodos()) {
            String nombreRegion = regionPorId.getOrDefault(equipo.getIdRegion(), "Region desconocida");
            System.out.println("- " + equipo.getIdEquipo() + " | " + equipo.getNombreEquipo() + " | Region: " + nombreRegion);
        }
    }

    private void crearRegionDesdeMenu(Scanner scanner) {
        System.out.print("Nombre del servidor (ej: BR): ");
        String nombreServidor = scanner.nextLine().trim();
        System.out.print("Ubicacion: ");
        String ubicacion = scanner.nextLine().trim();

        if (nombreServidor.isEmpty() || ubicacion.isEmpty()) {
            System.out.println("No se puede crear la region con datos vacios.");
            return;
        }

        if (regionDAO.obtenerPorNombreServidor(nombreServidor) != null) {
            System.out.println("Ya existe una region con ese nombre.");
            return;
        }

        regionDAO.crear(new Region(nombreServidor, ubicacion));
        System.out.println("Region creada correctamente.");
    }

    private void crearEquipoDesdeMenu(Scanner scanner) {
        System.out.print("Nombre del equipo: ");
        String nombreEquipo = scanner.nextLine().trim();
        System.out.print("Region del equipo (ej: KR): ");
        String nombreRegion = scanner.nextLine().trim();

        if (nombreEquipo.isEmpty() || nombreRegion.isEmpty()) {
            System.out.println("No se puede crear el equipo con datos vacios.");
            return;
        }

        if (equipoDAO.obtenerPorNombreEquipo(nombreEquipo) != null) {
            System.out.println("Ya existe un equipo con ese nombre.");
            return;
        }

        Region region = regionDAO.obtenerPorNombreServidor(nombreRegion);
        if (region == null) {
            System.out.println("No existe esa region. Crea la region antes de crear el equipo.");
            return;
        }

        equipoDAO.crear(new Equipo(nombreEquipo, region.getIdRegion()));
        System.out.println("Equipo creado correctamente.");
    }

    private void crearJugadorDesdeMenu(Scanner scanner) {
        System.out.print("Nickname del jugador: ");
        String nickname = scanner.nextLine().trim();
        System.out.print("Rol principal: ");
        String rol = scanner.nextLine().trim();
        System.out.print("Equipo (deja vacio para agente libre): ");
        String nombreEquipo = scanner.nextLine().trim();

        if (nickname.isEmpty() || rol.isEmpty()) {
            System.out.println("Nickname y rol son obligatorios.");
            return;
        }

        if (buscarJugadorPorNickname(nickname) != null) {
            System.out.println("Ya existe un jugador con ese nickname.");
            return;
        }

        Jugador jugador = new Jugador();
        jugador.setNickname(nickname);
        jugador.setRolPrincipal(rol);

        if (!nombreEquipo.isEmpty()) {
            Equipo equipo = equipoDAO.obtenerPorNombreEquipo(nombreEquipo);
            if (equipo == null) {
                System.out.println("No existe ese equipo. Se creara como agente libre.");
            } else {
                jugador.setIdEquipo(equipo.getIdEquipo().longValue());
            }
        }

        jugadorDAO.crear(jugador);
        System.out.println("Jugador creado correctamente.");
    }

    private void asignarJugadorAEquipoDesdeMenu(Scanner scanner) {
        System.out.print("Nickname del jugador: ");
        String nickname = scanner.nextLine().trim();
        System.out.print("Nombre del equipo: ");
        String nombreEquipo = scanner.nextLine().trim();

        Jugador jugador = buscarJugadorPorNickname(nickname);
        if (jugador == null) {
            System.out.println("No existe un jugador con ese nickname.");
            return;
        }

        Equipo equipo = equipoDAO.obtenerPorNombreEquipo(nombreEquipo);
        if (equipo == null) {
            System.out.println("No existe un equipo con ese nombre.");
            return;
        }

        jugador.setIdEquipo(equipo.getIdEquipo().longValue());
        jugadorDAO.actualizar(jugador);
        System.out.println("Jugador asignado correctamente.");
    }

    private Jugador buscarJugadorPorNickname(String nickname) {
        for (Jugador jugador : jugadorDAO.obtenerTodos()) {
            if (jugador.getNickname().equalsIgnoreCase(nickname)) {
                return jugador;
            }
        }
        return null;
    }
}