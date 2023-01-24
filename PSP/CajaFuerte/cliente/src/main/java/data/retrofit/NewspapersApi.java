package data.retrofit;

import data.retrofit.common.ConstantesAPI;
import io.reactivex.rxjava3.core.Single;
import modelo.Newspaper;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface NewspapersApi {

    @POST(ConstantesAPI.NEWSPAPERS)
    Single<Newspaper> addNewspaper(@Body Newspaper newspaper);
}