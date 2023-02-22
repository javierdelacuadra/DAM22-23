package org.server.domain.servicios.impl;

import jakarta.inject.Inject;
import org.server.dao.EquiposDao;
import org.server.domain.modelo.errores.InvalidFieldsException;
import org.server.domain.servicios.ServiciosEquipos;
import org.utils.domain.modelo.Equipo;
import org.utils.domain.modelo.Jugador;

import java.util.List;

public class ServiciosEquiposImpl implements ServiciosEquipos {

    private final EquiposDao equiposDao;

    @Inject
    public ServiciosEquiposImpl(EquiposDao equiposDao) {
        this.equiposDao = equiposDao;
    }


    @Override
    public List<Equipo> getAllSinJugadores() {
        return equiposDao.getAllSinJugadores();
    }

    @Override
    public List<Equipo> getAll() {
        return equiposDao.getAll();

    }

    @Override
    public List<Jugador> getJugadoresPorEquipo(String nombre) {
        return equiposDao.getJugadoresPorEquipo(nombre);
    }

    @Override
    public Equipo getEquipo(String nombre) {
        return equiposDao.getEquipo(nombre);
    }


    @Override
    public Jugador addPlayer(String equipo, Jugador jugador) {
        if (validationPlayer(jugador)) {
            return equiposDao.addPlayer(equipo, jugador);
        }
            throw new InvalidFieldsException("Faltan completar campos del jugador (nombre, id > 0 y equipo)");
    }

    private boolean validationPlayer(Jugador jugador) {
        return !(jugador.getNombre().equalsIgnoreCase("") || jugador.getId() < 0 || jugador.getEquipo().equalsIgnoreCase(""));
    }

    @Override
    public Equipo addTeam(Equipo equipo) {
        if (validationsTeam(equipo)){
            return equiposDao.addTeam(equipo);
        }
        throw new InvalidFieldsException("Faltan completar campos del equipo");
    }

    @Override
    public Jugador editPlayer(String nEquipo, Jugador newPlayer){
        if (validationPlayer(newPlayer) && !nEquipo.equalsIgnoreCase("")){
            return equiposDao.editPlayer(nEquipo, newPlayer);
        } else {
            throw new InvalidFieldsException("Complete todos los campos");
        }
    }

    private boolean validationsTeam(Equipo equipo) {
        return !(equipo.getEntrenador().equalsIgnoreCase("") || equipo.getJugadores() == null || equipo.getNombre().equalsIgnoreCase(""));
    }


    @Override
    public Equipo deleteTeam(String nombre) {
        return equiposDao.deleteTeam(nombre);
    }

    @Override
    public Jugador deletePlayer(String id) {
        return equiposDao.deletePlayer(id);
    }
}
