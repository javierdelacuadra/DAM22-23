package servicios;

import dao.DaoJugadores;
import io.reactivex.rxjava3.core.Single;
import io.vavr.control.Either;
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

    public Either<String, List<Equipo>> verTodosLosEquiposSinJugadores() {
        return dao.verTodosLosEquiposSinJugadores();
    }

    public Single<Either<String, List<Equipo>>> verTodosLosEquipos() {
        return dao.verTodosLosEquipos();
    }

    public Single<Either<String, List<Jugador>>> verJugadoresDeUnEquipo(String nombreEquipo) {
        return dao.verJugadoresDeUnEquipo(nombreEquipo);
    }

    public Single<Either<String, Equipo>> verUnEquipo(String nombreEquipo) {
        return dao.verUnEquipo(nombreEquipo);
    }

    public Single<Either<String, Jugador>> addJugador(Jugador jugador) {
        return dao.addJugador(jugador);
    }

    public Single<Either<String, Equipo>> addEquipo(Equipo equipo) {
        return dao.addEquipo(equipo);
    }

    public Single<Either<String, Jugador>> updateJugador(Jugador jugador) {
        return dao.updateJugador(jugador);
    }

    public Single<Either<String, Equipo>> borrarEquipo(String nombreEquipo) {
        return dao.borrarEquipo(nombreEquipo);
    }

    public Single<Either<String, Jugador>> borrarJugador(String nombreJugador) {
        return dao.borrarJugador(nombreJugador);
    }
}