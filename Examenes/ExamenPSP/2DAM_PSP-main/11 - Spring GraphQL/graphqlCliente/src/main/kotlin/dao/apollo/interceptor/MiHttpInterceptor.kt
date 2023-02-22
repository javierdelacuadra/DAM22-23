package dao.apollo.interceptor

import com.apollographql.apollo3.annotations.ApolloExperimental
import com.apollographql.apollo3.api.http.HttpRequest
import com.apollographql.apollo3.api.http.HttpResponse
import com.apollographql.apollo3.api.http.get
import com.apollographql.apollo3.network.http.HttpInterceptor
import com.apollographql.apollo3.network.http.HttpInterceptorChain
import credentials.CachedCredentials
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import okhttp3.Credentials

class MiHttpInterceptor (
    private val ca: CachedCredentials
) : HttpInterceptor {
    private val mutex = Mutex()

    @OptIn(ApolloExperimental::class)
    override suspend fun intercept(request: HttpRequest, chain: HttpInterceptorChain): HttpResponse {

        var token = mutex.withLock {
            // el mutex se utiliza para realizar las llamadas de forma sincrona
            ca.token
        // get current token

        }
        var original = request
        token?.let {
            original = addTokenToRequest(request, token)
        }
        var response = chain.proceed(original)
        if (ca.token == null) {
            response.headers.get("JWT")?.let {
                token = response.headers.get("JWT")
                mutex.withLock {
                    ca.token = token
                }
            }
        }

        if (response.statusCode > 300){
            // ERROR
            var responseLogin: HttpResponse? = null
            if (response.statusCode == 408) {
                //reintentar
                // token expirado, se vuelve a hacer login
                // se ejecuta la peticion original solo que la autenticación se hace con el usuario y contraseña en vez de JWT
                val requestLogin = doLogin(request)
                responseLogin = chain.proceed(requestLogin)
            }
            if (responseLogin != null && responseLogin.headers.get("JWT") != null) {
                // si la respuesta contiene un header con el token, lo guardamos y volvemos a hacer la llamada original
                token = responseLogin.headers.get("JWT")
                mutex.withLock {
                    ca.token = token
                }
                response = responseLogin
            }
        }
        return response
    }

    private fun addTokenToRequest(
        request: HttpRequest,
        token: String?
    ): HttpRequest {
        return request.newBuilder().addHeader("Authorization", "Bearer $token").build()
    }

    private fun doLogin(original: HttpRequest): HttpRequest {
        return original.newBuilder()
            .addHeader("Authorization", Credentials.basic(ca.username!!, ca.password!!))
            .build()

    }
}