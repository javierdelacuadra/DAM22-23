package retrofit;

import retrofit2.Call;
import retrofit2.http.GET;

public interface GDApi {

    @GET("getGJLevels21.php")
    Call<String> getLevels();

    // diff 1-2-3-4-5 (easy-normal-hard-harder-insane) -2 = demon -3 = auto
    // &featured&diff=-2 ex.
    // * no name
    // ? start of query
    // & add query
}