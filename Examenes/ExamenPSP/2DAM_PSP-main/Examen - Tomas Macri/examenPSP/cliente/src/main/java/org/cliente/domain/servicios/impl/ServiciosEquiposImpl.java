package org.cliente.domain.servicios.impl;

import io.reactivex.rxjava3.core.Single;
import io.vavr.control.Either;
import jakarta.inject.Inject;
import org.cliente.dao.impl.DaoEquiposImpl;
import org.cliente.domain.servicios.ServiciosEquipos;
import org.utils.domain.modelo.Equipo;
import org.utils.domain.modelo.Jugador;

import java.util.List;

public class ServiciosEquiposImpl implements ServiciosEquipos {

    private final DaoEquiposImpl daoEquipos;

    @Inject
    public ServiciosEquiposImpl(DaoEquiposImpl daoEquipos) {
        this.daoEquipos = daoEquipos;
    }

    @Override
    public Either<String, List<Equipo>> getAllAsynConTask() {
        return daoEquipos.getAllCall();
    }

    @Override
    public Single<Either<String, List<Equipo>>> getAllEquipos(){
        return daoEquipos.getAllEquipos();
    }

    @Override
    public Single<Either<String, List<Jugador>>> getJugadoresPorEquipo(String nombre) {
        return daoEquipos.getJugadoresPorEquipo(nombre);
    }

    @Override
    public Single<Either<String, Equipo>> getEquipoDetalle(String nombre) {
        return daoEquipos.getEquipoDetalle(nombre);
    }

    @Override
    public Single<Either<String, Jugador>> updatePlayer(Jugador jugador) {
            return daoEquipos.updatePlater(jugador);
    }



    @Override
    public Single<Either<String, Jugador>> createPlayer(Jugador j) {
        return daoEquipos.createPlayer(j);
    }

    @Override
    public Single<Either<String, Equipo>> createTeam(Equipo e) {
        return daoEquipos.createTeam(e);
    }

    @Override
    public Single<Either<String, Equipo>> deleteTeam(String nombre){
        return daoEquipos.deleteTeam(nombre);
    }

    @Override
    public Single<Either<String, Jugador>> deletePlayer(int id){ return daoEquipos.deletePlayer(id);}
}
