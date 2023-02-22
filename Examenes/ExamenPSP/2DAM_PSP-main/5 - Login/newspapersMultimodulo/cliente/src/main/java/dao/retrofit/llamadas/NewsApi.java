package dao.retrofit.llamadas;

import dao.common.APIConstants;
import io.reactivex.rxjava3.core.Single;
import org.miutils.domain.modelo.Newspaper;
import org.miutils.domain.modelo.Reader;
import org.miutils.domain.modelo.TypeArt;
import retrofit2.Response;
import retrofit2.http.*;

import java.util.List;

public interface    NewsApi {


    //NEWS SCREENS
    @GET(APIConstants.PATH_NEWSPAPERS)
    Single<List<Newspaper>> getAllNewsAsync();

    @GET(APIConstants.PATH_READERS + APIConstants.PATH_NEWS + APIConstants.PATH_PARAM_ID + APIConstants.PATH_OLDEST)
    Single<List<String>> getOldestSuscriptorsNews(@Path(APIConstants.ID) String id);

    @DELETE(APIConstants.PATH_NEWSPAPERS + APIConstants.PATH_PARAM_ID)
    Single<Response<Object>> deleteNewsAsync(@Path(APIConstants.ID) String id);

    @POST(APIConstants.PATH_NEWSPAPERS)
    Single<Newspaper> addNewsAsync(@Body Newspaper newspaper);

    @PUT(APIConstants.PATH_NEWSPAPERS)
    Single<Newspaper> updateNewsAsync(@Body Newspaper news);


    // READERS SCREENS

    @GET(APIConstants.PATH_READERS)
    Single<List<Reader>> getAllReadersAsync();

    @GET(APIConstants.PATH_READERS + APIConstants.PATH_PARAM_ID)
    Single<Reader> getOneReader(@Path(APIConstants.ID) String id);


    @DELETE(APIConstants.PATH_READERS + APIConstants.PATH_PARAM_ID)
    Single<Response<Object>> deleteReaderAsync(@Path(APIConstants.ID) String id);

    @POST(APIConstants.PATH_READERS)
    Single<Reader> addReaderAsync(@Body Reader reader);

    @PUT(APIConstants.PATH_READERS + APIConstants.PATH_UPDATE)
    Single<Reader> updateReaderAsync(@Body Reader reader);


    @GET(APIConstants.PATH_READERS + APIConstants.PATH_TIPO + APIConstants.PATH_PARAM_TIPO)
    Single<List<Reader>> getReadersByType(@Path(APIConstants.TIPO) String tipo);

    @GET(APIConstants.PATH_READERS + APIConstants.PATH_NEWS + APIConstants.PATH_PARAM_ID)
    Single<List<Reader>> getReadersByNews(@Path(APIConstants.ID) String id);


    @GET(APIConstants.PATH_TYPES)
    Single<List<TypeArt>> getAllTypesAsync();

    @GET(APIConstants.PATH_LOGIN)
    Single<Reader> login(@Header(APIConstants.EMAIL) String user, @Header(APIConstants.PASS) String password);

    @GET(APIConstants.PATH_LOGIN + APIConstants.PATH_LOGOUT)
    Single<Response<Object>> logout();

    @GET(APIConstants.PATH_LOGIN + APIConstants.PATH_FORGOT)
    Single<Response<Object>> forgot(@Header(APIConstants.EMAIL) String mail);

    @POST(APIConstants.PATH_LOGIN)
    Single<Reader> register(@Body Reader reader);
}


