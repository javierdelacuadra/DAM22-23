package dao.retrofit.llamadas;

import dao.common.APIConstants;
import io.reactivex.rxjava3.core.Single;
import org.miutils.common.AplicationConstants;
import org.miutils.domain.modelo.Newspaper;
import org.miutils.domain.modelo.TypeArt;
import retrofit2.Response;
import retrofit2.http.*;

import java.util.List;

public interface NewspapersApi {


    //NEWS SCREENS
    @GET(APIConstants.PATH_NEWSPAPERS)
    Single<List<Newspaper>> getAllNewsAsync();

    @DELETE(APIConstants.PATH_NEWSPAPERS + AplicationConstants.PATH_PARAM_ID)
    Single<Response<Object>> deleteNewsAsync(@Path(AplicationConstants.ID) String id);

    @POST(APIConstants.PATH_NEWSPAPERS)
    Single<Newspaper> addNewsAsync(@Body Newspaper newspaper);

    @PUT(APIConstants.PATH_NEWSPAPERS)
    Single<Newspaper> updateNewsAsync(@Body Newspaper news);

    @GET(APIConstants.PATH_TYPES)
    Single<List<TypeArt>> getAllTypesAsync();

}


