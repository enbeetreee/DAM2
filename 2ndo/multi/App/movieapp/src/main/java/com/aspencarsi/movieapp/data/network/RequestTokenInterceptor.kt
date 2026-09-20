package com.aspencarsi.movieapp.data.network

import okhttp3.Interceptor
import okhttp3.Response

class RequestTokenInterceptor(val token: String) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val newRequest = request.newBuilder()//repetir patron de request anterior, vease la url
            .addHeader("Accept", "application/json")
            .addHeader("Authorization", "Bearer $token")
            .build()
        return chain.proceed(newRequest)
    }

}