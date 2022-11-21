package dao.retrofit;

import io.reactivex.rxjava3.core.Single;
import modelo.Equipo;
import modelo.Jugador;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.*;

import java.util.List;

public interface JugadoresApi {
    @GET("equipos")
    Single<List<Equipo>> getEquipos();

    @GET("equipos/noPlayers")
    Call<List<Equipo>> getEquiposSinJugadores();

    @GET("jugadores/{nombre}")
    Single<List<Jugador>> verJugadoresDeUnEquipo(@Path("nombre") String nombre);

    @GET("equipos/{nombre}")
    Single<Equipo> verUnEquipo(@Path("nombre") String nombre);

    @POST("jugadores")
    Single<Jugador> addJugador(@Body Jugador jugador);

    @POST("equipos")
    Single<Equipo> addEquipo(@Body Equipo equipo);

    @PUT("jugadores")
    Single<Jugador> updateJugador(@Body Jugador jugador);

    @DELETE("equipos/{nombre}")
    Single<Equipo> borrarEquipo(@Path("nombre") String nombre);

    @DELETE("jugadores/{nombre}")
    Single<Jugador> borrarJugador(@Path("nombre") String nombre);
}
