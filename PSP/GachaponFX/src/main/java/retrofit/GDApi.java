package retrofit;

import modelo.ResponseLevels;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface GDApi {

    @GET("getGJLevels21.php")
    Call<String> getLevels();


    @GET("/search/{searchTerm}")
    Call<ResponseLevels> getNiveles(@Path("searchTerm") String searchTerm, @Query("diff") String difficulty, @Query("rated") boolean rated, @Query("featured") boolean featured, @Query("epic") boolean epic);
}