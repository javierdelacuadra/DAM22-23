package retrofit;

import modelo.ResponseLevelsItem;
import modelo.ResponseUser;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

import java.util.List;

public interface GDApi {

    @GET("search/{text}")
    Call<List<ResponseLevelsItem>> getNiveles(@Path("text") String text,
                                              @Query("diff") String difficulty);

    @GET("search/{text}")
    Call<List<ResponseLevelsItem>> getNiveles(@Path("text") String text);

    @GET("profile/{username}")
    Call<ResponseUser> getUser(@Path("username") String username);
}