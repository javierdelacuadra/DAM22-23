package domain.servicios;

import data.DaoJugadores;
import jakarta.inject.Inject;
import modelo.Equipo;
import modelo.Jugador;

import java.util.List;

public class ServiciosJugadores {

    private final DaoJugadores dao;

    @Inject
    public ServiciosJugadores(DaoJugadores dao) {
        this.dao = dao;
    }

    public List<Equipo> verTodosLosEquiposSinJugadores() {
        return dao.verTodosLosEquiposSinJugadores();
    }

    public List<Equipo> verTodosLosEquipos() {
        return dao.verTodosLosEquipos();
    }

    public List<Jugador> verJugadoresDeUnEquipo(String nombreEquipo) {
        return dao.verJugadoresDeUnEquipo(nombreEquipo);
    }

    public Equipo verUnEquipo(String nombreEquipo) {
        return dao.verUnEquipo(nombreEquipo);
    }

    public Jugador addJugador(Jugador jugador) {
        return dao.addJugador(jugador);
    }

    public Equipo addEquipo(Equipo equipo) {
        return dao.addEquipo(equipo);
    }

    public Jugador updateJugador(Jugador jugador) {
        return dao.updateJugador(jugador);
    }

    public boolean borrarEquipo(String nombreEquipo) {
        return dao.borrarEquipo(nombreEquipo);
    }

    public Jugador borrarJugador(String nombreJugador) {
        return dao.borrarJugador(nombreJugador);
    }
}
