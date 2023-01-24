package data.retrofit;

import data.retrofit.common.ConstantesAPI;
import io.reactivex.rxjava3.core.Single;
import modelo.Reader;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

import java.util.List;

public interface ReadersApi {
    @GET(ConstantesAPI.READERS)
    Single<List<Reader>> getReaders();

    @POST(ConstantesAPI.READERS)
    Single<Reader> addReader(@Body Reader reader);
}
