package org.cliente.dao.impl;

import io.reactivex.rxjava3.core.Single;
import io.vavr.control.Either;
import jakarta.inject.Inject;
import org.cliente.dao.DaoEquipos;
import org.cliente.dao.retrofit.llamadas.MyApi;
import org.utils.domain.modelo.Equipo;
import org.utils.domain.modelo.Jugador;
import retrofit2.Response;

import java.util.List;

public class DaoEquiposImpl extends DaoGenericsImpl implements DaoEquipos {

    public static final String ERROR_AL_CARGAR_EQUIPOS = "Error al cargar equipos: ";
    private final MyApi myApi;

    @Inject
    public DaoEquiposImpl(MyApi myApi) {
        this.myApi = myApi;
    }


    @Override
    public Either<String, List<Equipo>> getAllCall() {
        //CODIGO LLAMADA A LA API PASANDO LOS ISO DE PAIS Y ESTADO
        Either<String, List<Equipo>> either;
        try {

            Response<List<Equipo>> r = myApi.getAllSinPlayers().execute();
            if (r.isSuccessful() && r.body() != null) {
                List<Equipo> equipos = r.body();
                if (equipos.isEmpty()) {
                    either = Either.left("No hay equipos");
                } else {
                    either = Either.right(equipos);
                }
            } else {
                if (r.errorBody() != null) {
                    either = Either.left(ERROR_AL_CARGAR_EQUIPOS + r.errorBody().string());
                } else {
                    either = Either.left(ERROR_AL_CARGAR_EQUIPOS + "UNKNOWN ERROR");
                }
            }
        } catch (Exception e) {
            either = Either.left(ERROR_AL_CARGAR_EQUIPOS + e.getMessage());
        }
        return either;
    }

    @Override
    public Single<Either<String, List<Jugador>>> getJugadoresPorEquipo(String nombre){
        return safeSingleApicall(myApi.getPlayersPorEquipo(nombre));
    }

    @Override
    public Single<Either<String, Equipo>> getEquipoDetalle(String nombre){
        return safeSingleApicall(myApi.getEquipoDetalle(nombre));
    }

    @Override
    public Single<Either<String, List<Equipo>>> getAllEquipos(){
        return safeSingleApicall(myApi.getAllEquipos());
    }


    @Override
    public Single<Either<String, Jugador>> updatePlater(Jugador jugador) {
        return safeSingleApicall(myApi.updateJugador(jugador.getEquipo(), jugador));
    }

    @Override
    public Single<Either<String, Jugador>> createPlayer(Jugador j) {
        return safeSingleApicall(myApi.addPlayer(j.getEquipo(), j));
    }

    @Override
    public Single<Either<String, Equipo>> createTeam(Equipo e) {
        return safeSingleApicall(myApi.createTeam(e));
    }
    @Override
    public Single<Either<String, Equipo>> deleteTeam(String nombre){
        return safeSingleApicall(myApi.deleteTeam(nombre));
    }

    @Override
    public Single<Either<String, Jugador>> deletePlayer(int id){
        return safeSingleApicall(myApi.deletePlayer(id+""));
    }
}
