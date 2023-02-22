package org.cliente.domain.servicios;

import io.reactivex.rxjava3.core.Single;
import io.vavr.control.Either;
import org.utils.domain.modelo.Equipo;
import org.utils.domain.modelo.Jugador;

import java.util.List;

public interface ServiciosEquipos {
    Either<String, List<Equipo>> getAllAsynConTask();

    Single<Either<String, List<Equipo>>> getAllEquipos();

    Single<Either<String, List<Jugador>>> getJugadoresPorEquipo(String nombre);

    Single<Either<String, Equipo>> getEquipoDetalle(String nombre);

    Single<Either<String, Jugador>> updatePlayer(Jugador jugador);

    Single<Either<String, Jugador>> createPlayer(Jugador j);

    Single<Either<String, Equipo>> createTeam(Equipo e);

    Single<Either<String, Equipo>> deleteTeam(String nombre);

    Single<Either<String, Jugador>> deletePlayer(int id);
}
