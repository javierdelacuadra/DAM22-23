package org.server.domain.servicios;

import org.utils.domain.modelo.Equipo;
import org.utils.domain.modelo.Jugador;

import java.util.List;

public interface ServiciosEquipos {
    List<Equipo> getAllSinJugadores();

    List<Equipo> getAll();

    List<Jugador> getJugadoresPorEquipo(String nombre);

    Equipo getEquipo(String nombre);

    Jugador addPlayer(String equipo, Jugador jugador);

    Equipo addTeam(Equipo equipo);

    Jugador editPlayer(String nEquipo, Jugador newPlayer);

    Equipo deleteTeam(String nombre);

    Jugador deletePlayer(String id);
}
