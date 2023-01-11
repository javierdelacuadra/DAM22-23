package data.network;

import okhttp3.Credentials;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import servicios.modelo.CacheAuthorization;

import java.io.IOException;

public class AuthorizationInterceptor implements Interceptor {

    private CacheAuthorization ca;

    public AuthorizationInterceptor(CacheAuthorization ca) {
        this.ca = ca;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request original = chain.request();
        Request request;

        if (ca.getJwt() == null) {
            request = original.newBuilder()
                    .header("Authorization", Credentials.basic(ca.getUser(), ca.getPass())).build();
        } else {
            request = original.newBuilder()
                    .header("Authorization", "Bearer " + ca.getJwt()).build();
        }

        Response response = chain.proceed(request);
        if (response.header("Authorization") != null) {
            String header = response.header("Authorization");
            String[] values = new String[0];
            if (header != null) {
                values = header.split(" ");
            }
            if (values[0].equals("Bearer") && values.length == 2) {
                ca.setJwt(values[1]);
            }
        }

        int code = response.code();

        if (!(code >= 200 && code < 300) && code != 401 && code != 403) {
            response.close();
            request = original.newBuilder()
                    .header("Authorization", Credentials.basic(ca.getUser(), ca.getPass())).build();
            response = chain.proceed(request);
            if (response.header("Authorization") != null) {
                String header = response.header("Authorization");
                String[] values = new String[0];
                if (header != null) {
                    values = header.split(" ");
                }
                if (values[0].equals("Bearer") && values.length == 2) {
                    ca.setJwt(values[1]);
                }
            }
        }

        return response;
    }
}
