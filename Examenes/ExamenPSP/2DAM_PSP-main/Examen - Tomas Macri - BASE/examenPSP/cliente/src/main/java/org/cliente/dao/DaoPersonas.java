package org.cliente.dao;

import com.squareup.moshi.Moshi;
import io.reactivex.rxjava3.core.Single;
import io.vavr.control.Either;
import jakarta.inject.Inject;
import org.cliente.dao.impl.DaoGenericsImpl;
import org.cliente.dao.retrofit.llamadas.MyApi;
import org.cliente.dao.retrofit.produces.LocalDateMoshiAdapter;
import org.cliente.dao.retrofit.produces.LocalDateTimeMoshiAdapter;
import org.cliente.domain.modelo.PersonaCliente;
import org.utils.domain.modelo.ApiError;
import org.utils.domain.modelo.Persona;
import retrofit2.Call;
import retrofit2.Response;

import java.util.List;
import java.util.Objects;

public class DaoPersonas extends DaoGenericsImpl {

    public static final String ERROR_AL_CARGAR_LAS_PERSONAS = "Error al cargar las personas: ";
    public static final String ERROR_AL_ACTUALIZAR_LA_PERSONA = "Error al actualizar la persona: ";
    private final MyApi myApi;

    @Inject
    public DaoPersonas(MyApi myApi) {
        this.myApi = myApi;
    }

    public Either<String, List<PersonaCliente>> getAllCall() {
        //CODIGO LLAMADA A LA API PASANDO LOS ISO DE PAIS Y ESTADO
        Either<String, List<PersonaCliente>> either;
        try {

            Response<List<Persona>> r = myApi.getAll().execute();
            if (r.isSuccessful() && r.body() != null) {
                List<Persona> personasServer = r.body();
                List<PersonaCliente> misPersonas = personasServer.stream()
                        .map(perServer ->
                                new PersonaCliente(perServer.getId(), perServer.getNombre(), perServer.getFechaNacimiento(), perServer.getPassword(), null))
                        .toList();
                if (misPersonas.isEmpty()) {
                    either = Either.left("No hay personas");
                } else {
                    either = Either.right(misPersonas);
                }
            } else {
                if (r.errorBody() != null) {
                    either = Either.left(ERROR_AL_CARGAR_LAS_PERSONAS + r.errorBody().string());
                } else {
                    either = Either.left(ERROR_AL_CARGAR_LAS_PERSONAS + "UNKNOWN ERROR");
                }
            }
        } catch (Exception e) {
            either = Either.left(ERROR_AL_CARGAR_LAS_PERSONAS + e.getMessage());
        }
        return either;
    }

    public Single<Either<String, PersonaCliente>> getUnaPersona(int id) {
        return safeSingleApicall(myApi.getOne(id + "")
                .map(persona ->
                        new PersonaCliente(persona.getId(), persona.getNombre(), persona.getFechaNacimiento(), persona.getPassword(), null)
                )
        );
    }

    public Either<String, PersonaCliente> updatePersonaCall(PersonaCliente personaCliente) {
        Either<String, PersonaCliente> either;
        try {
            Response<Persona> r = myApi.update(new Persona(personaCliente.getId(), personaCliente.getNombre(), personaCliente.getFechaNacimiento(), personaCliente.getPassword())).execute();
            if (r.isSuccessful() && r.body() != null) {
                either = Either.right(personaCliente);
            } else {
                if (r.errorBody() != null) {
                    Moshi m = new Moshi.Builder().add(new LocalDateMoshiAdapter()).add(new LocalDateTimeMoshiAdapter()).build();
                    ApiError apierror = m.adapter(ApiError.class).fromJson(Objects.requireNonNull(Objects.requireNonNull(r.errorBody()).string()));
                    if (apierror != null && apierror.getMessage() != null){
                        either = Either.left(ERROR_AL_ACTUALIZAR_LA_PERSONA + apierror.getMessage());
                    } else {
                        either = Either.left(ERROR_AL_ACTUALIZAR_LA_PERSONA + r.errorBody().string());
                    }
                } else {
                    either = Either.left(ERROR_AL_ACTUALIZAR_LA_PERSONA + "UNKNOWN ERROR");
                }
            }
        } catch (Exception e) {
            either = Either.left(ERROR_AL_ACTUALIZAR_LA_PERSONA + e.getMessage());
        }
        return either;
    }

    public Single<Either<String, Response<Object>>> deletePersona(int id) {
        return safeSingleDeleteApicall(myApi.delete(id + ""));
    }

}

