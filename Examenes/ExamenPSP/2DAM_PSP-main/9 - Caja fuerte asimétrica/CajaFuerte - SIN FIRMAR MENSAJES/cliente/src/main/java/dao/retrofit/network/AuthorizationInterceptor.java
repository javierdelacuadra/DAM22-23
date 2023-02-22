package dao.retrofit.network;

import common.CachedCredentials;
import jakarta.inject.Inject;
import okhttp3.*;
import org.utils.common.AplicationConstants;

import java.io.IOException;

public class AuthorizationInterceptor implements Interceptor {


    private final CachedCredentials ca;

    @Inject
    public AuthorizationInterceptor(CachedCredentials ca) {
        this.ca = ca;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request original = chain.request();
        Request request = original;

        request = addTokenToRequest(original, request);

        // se ejecuta la peticion
        Response response = chain.proceed(request);

        if (ca.getJwt() == null && response.header(AplicationConstants.JWT) != null) {
            setToken(response);
        }

        if (!response.isSuccessful())
        {
            Response responseLogin = null;
            if(response.code() == 408)
            {
                //reintentar
                response.close();
                // token expirado, se vuelve a hacer login
                Request requestLogin = doLogin(original);
                 responseLogin = chain.proceed(requestLogin);
            }
            if (responseLogin != null && responseLogin.header(AplicationConstants.JWT) != null) {
                // si la respuesta contiene un header con el token, lo guardamos y volvemos a hacer la llamada original
                setToken(responseLogin);
                responseLogin.close();
                // se ejecuta la peticion original
                request = addTokenToRequest(original, request);
                response = chain.proceed(request);
            }
        }

        return response;
    }

    private Request addTokenToRequest(Request original, Request request) {
        if (ca.getJwt() != null)
        {
            // ya tiene token, lo pasamos en el header como JWT
            request = original.newBuilder()
                    .header(AplicationConstants.AUTHORIZATION, AplicationConstants.BEARER + AplicationConstants.SPACE + ca.getJwt()).build();
        }
        return request;
    }

    private void setToken(Response response) {
        // si la respuesta contiene un header con el token, lo guardamos
        ca.setJwt(response.header(AplicationConstants.JWT));
    }

    private Request doLogin(Request original) {
        Request request;
        String url = original.url().toString().substring(0, original.url().toString().indexOf("caja/") + 4) + AplicationConstants.PATH_USERS + AplicationConstants.PATH_LOGIN;
        request =
        original.newBuilder()
                .url(url)
                .post(RequestBody.create(MediaType.get("application/json"), ca.getUserLogged().toString()))
                .build();
        return request;
    }
}
