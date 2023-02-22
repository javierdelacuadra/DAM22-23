package org.cliente.dao.retrofit.llamadas;

import io.reactivex.rxjava3.core.Single;
import org.cliente.dao.common.APIConstants;
import org.utils.domain.modelo.Persona;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.*;

import java.util.List;

public interface MyApi {

    // GET ALL
    @GET(APIConstants.PATH_PERS)
    Call<List<Persona>> getAll();

    // GET ONE
    @GET(APIConstants.PATH_PERS + APIConstants.PATH_PARAM_ID)
    Single<Persona> getOne(@Path(APIConstants.ID) String id);

    // GET ALL BY TYPE
    @GET(APIConstants.PATH_PERS + APIConstants.PATH_MENORES)
    Single<List<Persona>> getAllMenores();

    // POST
    @POST(APIConstants.PATH_PERS)
    Single<Persona> add(@Body Persona persona);

    // PUT
    @PUT(APIConstants.PATH_PERS)
    Call<Persona> update(@Body Persona persona);

    // DELETE
    @DELETE(APIConstants.PATH_PERS + APIConstants.PATH_PARAM_ID)
    Single<Response<Object>> delete(@Path(APIConstants.ID) String id);



}
