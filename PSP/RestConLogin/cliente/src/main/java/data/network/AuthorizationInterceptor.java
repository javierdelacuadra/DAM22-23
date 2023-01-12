package data.network;

import data.common.ConstantesDao;
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
                    .header(ConstantesDao.AUTHORIZATION, Credentials.basic(ca.getUser(), ca.getPass())).build();
        } else {
            request = original.newBuilder()
                    .header(ConstantesDao.AUTHORIZATION, ConstantesDao.BEARER_HEADER + ca.getJwt()).build();
        }

        Response response = chain.proceed(request);
        saveTokenOnCache(response);

        int code = response.code();

        if (!(code >= 200 && code < 300) && code != 401 && code != 403) {
            response.close();
            request = original.newBuilder()
                    .header(ConstantesDao.AUTHORIZATION, Credentials.basic(ca.getUser(), ca.getPass())).build();
            response = chain.proceed(request);
            saveTokenOnCache(response);
        }

        return response;
    }

    private void saveTokenOnCache(Response response) {
        if (response.header(ConstantesDao.AUTHORIZATION) != null) {
            String header = response.header(ConstantesDao.AUTHORIZATION);
            String[] values = new String[0];
            if (header != null) {
                values = header.split(" ");
            }
            if (values[0].equals(ConstantesDao.BEARER) && values.length == 2) {
                ca.setJwt(values[1]);
            }
        }
    }
}
