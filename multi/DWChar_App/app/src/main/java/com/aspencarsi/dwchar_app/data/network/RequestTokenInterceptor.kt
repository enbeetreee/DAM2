package com.aspencarsi.dwchar_app.data.network

import okhttp3.Interceptor
import okhttp3.Response

class RequestTokenInterceptor(): Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val newRequest = request.newBuilder()//repite patron de request anterior, vease la url
            .addHeader("Accept", "application/json")
            .build()
        return chain.proceed(newRequest)
    }

}