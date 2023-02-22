package dao.retrofit.llamadas;

import dao.common.APIConstants;
import io.reactivex.rxjava3.core.Single;
import org.miutils.common.AplicationConstants;
import org.miutils.domain.modelo.Newspaper;
import org.miutils.domain.modelo.Reader;
import org.miutils.domain.modelo.TypeArt;
import retrofit2.Response;
import retrofit2.http.*;

import java.util.List;

public interface NewspapersApi {


    //NEWS SCREENS
    @GET(APIConstants.PATH_NEWSPAPERS)
    Single<List<Newspaper>> getAllNewsAsync();

    @GET(APIConstants.PATH_READERS + AplicationConstants.PATH_NEWS + AplicationConstants.PATH_PARAM_ID + AplicationConstants.PATH_OLDEST)
    Single<List<String>> getOldestSuscriptorsNews(@Path(AplicationConstants.ID) String id);

    @DELETE(APIConstants.PATH_NEWSPAPERS + AplicationConstants.PATH_PARAM_ID)
    Single<Response<Object>> deleteNewsAsync(@Path(AplicationConstants.ID) String id);

    @POST(APIConstants.PATH_NEWSPAPERS)
    Single<Newspaper> addNewsAsync(@Body Newspaper newspaper);

    @PUT(APIConstants.PATH_NEWSPAPERS)
    Single<Newspaper> updateNewsAsync(@Body Newspaper news);


    // READERS SCREENS

    @GET(APIConstants.PATH_READERS)
    Single<List<Reader>> getAllReadersAsync();

    @GET(APIConstants.PATH_READERS + AplicationConstants.PATH_PARAM_ID)
    Single<Reader> getOneReader(@Path(AplicationConstants.ID) String id);


    @DELETE(APIConstants.PATH_READERS + AplicationConstants.PATH_PARAM_ID)
    Single<Response<Object>> deleteReaderAsync(@Path(AplicationConstants.ID) String id);

    @POST(APIConstants.PATH_READERS)
    Single<Reader> addReaderAsync(@Body Reader reader);

    @PUT(APIConstants.PATH_READERS + AplicationConstants.PATH_UPDATE)
    Single<Reader> updateReaderAsync(@Body Reader reader);


    @GET(APIConstants.PATH_READERS + AplicationConstants.PATH_TIPO + AplicationConstants.PATH_PARAM_TIPO)
    Single<List<Reader>> getReadersByType(@Path(AplicationConstants.TIPO) String tipo);

    @GET(APIConstants.PATH_READERS + AplicationConstants.PATH_NEWS + AplicationConstants.PATH_PARAM_ID)
    Single<List<Reader>> getReadersByNews(@Path(AplicationConstants.ID) String id);


    @GET(APIConstants.PATH_TYPES)
    Single<List<TypeArt>> getAllTypesAsync();

}


