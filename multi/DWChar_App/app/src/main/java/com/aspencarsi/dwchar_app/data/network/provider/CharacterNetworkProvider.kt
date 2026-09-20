package com.aspen.nasaapod_app.data.local.provider

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import com.aspencarsi.dwchar_app.data.network.api.CharacterService

import com.aspencarsi.dwchar_app.data.network.RequestTokenInterceptor

object CharacterNetworkProvider {
    private  val DB_FILENAME = "characters.db"

    private val API_SERVICE_BASE_URL = "https://doctor-who-api.onrender.com/api/"

    fun getCharacterApiService() : CharacterService {
        return getCharacterService()
    }


    private fun getCharacterService(): CharacterService {

        val client = OkHttpClient.Builder()
            .addInterceptor(RequestTokenInterceptor())
            .build()

        val moshi = Moshi.Builder()
            .addLast(KotlinJsonAdapterFactory())
            .build()

        val retrofit = Retrofit.Builder()
            .baseUrl(API_SERVICE_BASE_URL)
            .client(client)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()

        return retrofit.create(CharacterService::class.java)
    }
}