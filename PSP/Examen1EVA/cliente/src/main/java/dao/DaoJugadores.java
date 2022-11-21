package dao;

import com.google.gson.Gson;
import dao.retrofit.JugadoresApi;
import io.reactivex.rxjava3.core.Single;
import io.vavr.control.Either;
import jakarta.inject.Inject;
import modelo.Equipo;
import modelo.Jugador;
import retrofit2.Response;

import java.io.IOException;
import java.util.List;

public class DaoJugadores extends DaoGenerics {

    private final JugadoresApi api;

    @Inject
    public DaoJugadores(JugadoresApi api, Gson gson) {
        super(gson);
        this.api = api;
    }

    public Either<String, List<Equipo>> verTodosLosEquiposSinJugadores() {
        try {
            Response<List<Equipo>> response = api.getEquiposSinJugadores().execute();
            if (response.isSuccessful()) {
                return Either.right(response.body()).mapLeft(Object::toString);
            } else {
                return Either.left("No se ha encontrado ningún equipo");
            }
        } catch (IOException e) {
            return Either.left("Error al buscar los equipos");
        }
    }

    public Single<Either<String, List<Equipo>>> verTodosLosEquipos() {
        return createSafeSingleApiCall(api.getEquipos());
    }

    public Single<Either<String, List<Jugador>>> verJugadoresDeUnEquipo(String nombreEquipo) {
        return createSafeSingleApiCall(api.verJugadoresDeUnEquipo(nombreEquipo));
    }

    public Single<Either<String, Equipo>> verUnEquipo(String nombreEquipo) {
        return createSafeSingleApiCall(api.verUnEquipo(nombreEquipo));
    }

    public Single<Either<String, Jugador>> addJugador(Jugador jugador) {
        return createSafeSingleApiCall(api.addJugador(jugador));
    }

    public Single<Either<String, Equipo>> addEquipo(Equipo equipo) {
        return createSafeSingleApiCall(api.addEquipo(equipo));
    }

    public Single<Either<String, Jugador>> updateJugador(Jugador jugador) {
        return createSafeSingleApiCall(api.updateJugador(jugador));
    }

    public Single<Either<String, Equipo>> borrarEquipo(String nombreEquipo) {
        return createSafeSingleApiCall(api.borrarEquipo(nombreEquipo));
    }

    public Single<Either<String, Jugador>> borrarJugador(String nombreJugador) {
        return createSafeSingleApiCall(api.borrarJugador(nombreJugador));
    }
    //TODO: arreglar deletes devolviendo el objeto haciendo get del objeto primero
}