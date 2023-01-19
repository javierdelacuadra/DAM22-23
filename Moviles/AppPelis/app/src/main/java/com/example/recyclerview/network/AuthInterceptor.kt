package com.example.recyclerview.network

import com.example.recyclerview.network.common.ConstantesNetwork
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

/**
 * Intercepts and adds Token or API Key
 */
class AuthInterceptor(private val apiKey: String) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val original = chain.request()
        val httpUrl = original.url.newBuilder()
            .addQueryParameter(ConstantesNetwork.API_KEY, apiKey)
            .build()

        val requestBuilder: Request.Builder = original.newBuilder()
            .url(httpUrl)

        return chain.proceed(requestBuilder.build())
    }
}