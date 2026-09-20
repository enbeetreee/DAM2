package com.aspencarsi.apiservicedemo.api

import com.aspencarsi.apiservicedemo.API_TOKEN
import com.aspencarsi.apiservicedemo.model.MoviesResponse
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers

interface MovieService{

    /*@Headers(//pueden quitarse al añadir el interceptor
        "Accept: application/json",
        "Authorization: Bearer $API_TOKEN"
    )*/
    @GET("movie/now_playing")
    fun getMovies(): Call<MoviesResponse>

    @GET("movie/now_playing")
    suspend fun getMoviesAsyncResponse(): Response<MoviesResponse>

    @GET("movie/now_playing")
    suspend fun getMoviesAsync(): MoviesResponse
}