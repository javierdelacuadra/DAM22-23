package data.retrofit;

import data.retrofit.common.ConstantesAPI;
import io.reactivex.rxjava3.core.Single;
import model.Reader;
import retrofit2.Response;
import retrofit2.http.*;

import java.util.List;

public interface ReadersApi {
    @GET(ConstantesAPI.READERS)
    Single<List<Reader>> getReaders();

    @POST(ConstantesAPI.READERS)
    Single<Reader> addReader(@Body Reader reader);

    @PUT(ConstantesAPI.READERS)
    Single<Reader> updateReader(@Body Reader reader);

    @DELETE(ConstantesAPI.READERS_ID)
    Single<Response<Object>> deleteReader(@Path(ConstantesAPI.ID) String id);
}
