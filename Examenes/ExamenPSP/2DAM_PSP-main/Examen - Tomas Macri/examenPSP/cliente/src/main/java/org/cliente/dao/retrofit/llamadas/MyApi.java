package org.cliente.dao.retrofit.llamadas;

import io.reactivex.rxjava3.core.Single;
import org.utils.domain.modelo.Equipo;
import org.utils.domain.modelo.Jugador;
import retrofit2.Call;
import retrofit2.http.*;

import java.util.List;

public interface MyApi {

    // GET ALL
    @GET("teams/simple")
    Call<List<Equipo>> getAllSinPlayers();


    @GET("teams/{nombre}/jugs")
    Single<List<Jugador>> getPlayersPorEquipo(@Path("nombre") String nombre);

    @GET("teams/{nombre}")
    Single<Equipo> getEquipoDetalle(@Path("nombre") String nombre);

    @GET("teams")
    Single<List<Equipo>> getAllEquipos();

    @PUT("teams/{equipo}")
    Single<Jugador> updateJugador(@Path("equipo") String equipo, @Body Jugador jugador);

    @POST("teams/{equipo}")
    Single<Jugador> addPlayer(@Path("equipo") String equipo, @Body Jugador j);

    @POST("teams")
    Single<Equipo> createTeam(@Body Equipo e);

    @DELETE("teams/{equipo}")
    Single<Equipo> deleteTeam(@Path("equipo") String equipo);


    @DELETE("teams/jugs/{id}")
    Single<Jugador> deletePlayer(@Path("id") String id);
}
