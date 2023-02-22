package dao.retrofit.network;

import okhttp3.Credentials;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import org.miutils.common.AplicationConstants;

import java.io.IOException;

public class AuthorizationInterceptor implements Interceptor {


    private final CacheAuthorization ca;


    public AuthorizationInterceptor(CacheAuthorization ca) {
        this.ca = ca;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request original = chain.request();
        Request request = original;

        if (ca.getJwt() != null)
        {
            // ya tiene token, lo pasamos en el header como JWT
            request = original.newBuilder()
                    .header(AplicationConstants.AUTHORIZATION, AplicationConstants.BEARER + AplicationConstants.SPACE + ca.getJwt()).build();
        }

        // se ejecuta la peticion
        Response response = chain.proceed(request);

        if (ca.getJwt() == null)
            setToken(response);


        if (!response.isSuccessful())
        {

            if(response.code() == 408)
            {
                //reintentar
                response.close();
                // token expirado, se vuelve a hacer login
                request = doLogin(original);
                response = chain.proceed(request);
            }
            setToken(response);
        }

        return response;
    }

    private void setToken(Response response) {
        // si la respuesta contiene un header con el token, lo guardamos
        if (response.header(AplicationConstants.JWT) != null)
            ca.setJwt(response.header(AplicationConstants.JWT));
    }

    private Request doLogin(Request original) {
        Request request;
        request = original.newBuilder()
                .header(AplicationConstants.AUTHORIZATION, Credentials.basic(ca.getUser(), ca.getPass())).build();
        return request;
    }
}
