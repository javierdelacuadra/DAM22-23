package data.retrofit;

import data.retrofit.common.ConstantesAPI;
import io.reactivex.rxjava3.core.Single;
import modelo.Newspaper;
import retrofit2.Response;
import retrofit2.http.*;

import java.util.List;

public interface NewspapersApi {

    @GET(ConstantesAPI.NEWSPAPERS)
    Single<List<Newspaper>> getNewspapers();

    @POST(ConstantesAPI.NEWSPAPERS)
    Single<Newspaper> addNewspaper(@Body Newspaper newspaper);

    @PUT(ConstantesAPI.NEWSPAPERS)
    Single<Newspaper> updateNewspaper(@Body Newspaper newspaper);

    @DELETE(ConstantesAPI.NEWSPAPERS_ID)
    Single<Response<Object>> deleteNewspaper(@Path(ConstantesAPI.ID) String id);
}