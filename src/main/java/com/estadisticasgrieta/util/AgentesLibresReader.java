package com.estadisticasgrieta.util;

import com.estadisticasgrieta.model.Jugador;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class AgentesLibresReader {

    public static List<Jugador> leerAgentesLibres(String rutaArchivo) {
        List<Jugador> agentesLibres = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(rutaArchivo))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                if (linea.trim().isEmpty()) continue;

                // Separar datos del txt
                String lineaLimpia = linea.replace(";", "").trim();
                String[] datos = lineaLimpia.split(",");

                if (datos.length == 4) {
                    try {
                        String nombre = datos[0].trim();
                        String rol = datos[1].trim();
                        int nivel = Integer.parseInt(datos[2].trim());
                        String region = datos[3].trim();

                        agentesLibres.add(new Jugador(nombre, rol, nivel, region));
                    } catch (NumberFormatException e) {
                        System.err.println("Error de formato en nivel numérico en la línea: " + linea);
                    }
                } else {
                    System.err.println("Formato incorrecto (faltan o sobran datos) en la línea: " + linea);
                }
            }
        } catch (IOException e) {
            System.err.println("Error al leer el archivo: " + e.getMessage());
        }

        return agentesLibres;
    }
}