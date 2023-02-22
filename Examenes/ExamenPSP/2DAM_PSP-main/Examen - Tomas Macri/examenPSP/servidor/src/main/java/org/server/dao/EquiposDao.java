package org.server.dao;

import org.utils.domain.modelo.Equipo;
import org.utils.domain.modelo.Jugador;

import java.util.List;

public interface EquiposDao {
    List<Equipo> getAllSinJugadores();

    List<Equipo> getAll();

    List<Jugador> getJugadoresPorEquipo(String nombre);

    Equipo getEquipo(String nombre);

    Jugador addPlayer(String nEquipo, Jugador jugador);

    Equipo addTeam(Equipo equipo);

    Jugador editPlayer(String nEquipo, Jugador jugador);

    Equipo deleteTeam(String nombre);

    Jugador deletePlayer(String id);
}
