package com.aspencarsi.movieapp

import android.app.Application
import com.aspencarsi.movieapp.data.MovieRepositoryImpl
import com.aspencarsi.movieapp.data.network.RequestTokenInterceptor
import com.aspencarsi.movieapp.data.network.api.MovieService
import com.aspencarsi.movieapp.domain.MovieRepository
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

class MovieApplication : Application() {
    lateinit var movieRepository: MovieRepository
    private val API_TOKEN =
        "eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiIzYzU0YjcyODJjNzk4OGVjMGFmOGE3NjE4YTA0ODNlOCIsIm5iZiI6MTczNzk3NTk5Mi43MzYsInN1YiI6IjY3OTc2OGI4M2Y3ZmNmNjdkMDhmNWFhOCIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.bfpRsANeCItTcG5vPZbBqhfVb4JXFdtOir8r5Mz3a_0"


    private fun getMovieService(token: String): MovieService {
        val client = OkHttpClient.Builder()//añadido despues de hacer interceptor
            .addInterceptor(RequestTokenInterceptor(token))
            .build()


        val moshi = Moshi.Builder()
            .addLast(KotlinJsonAdapterFactory())
            .build()

        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.themoviedb.org/3/")
            .client(client)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()

        return retrofit.create(MovieService::class.java)
    }

    override fun onCreate() {
        super.onCreate()
        val service = getMovieService(API_TOKEN)

        movieRepository = MovieRepositoryImpl(service)
    }
}