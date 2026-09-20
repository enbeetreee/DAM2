package com.aspencarsi.apiservicedemo


import com.aspencarsi.apiservicedemo.api.MovieService
import com.aspencarsi.apiservicedemo.model.MoviesResponse
import com.squareup.moshi.Moshi
import com.squareup.moshi.adapter
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory


const val API_TOKEN =
    "eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiIzYzU0YjcyODJjNzk4OGVjMGFmOGE3NjE4YTA0ODNlOCIsIm5iZiI6MTczNzk3NTk5Mi43MzYsInN1YiI6IjY3OTc2OGI4M2Y3ZmNmNjdkMDhmNWFhOCIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.bfpRsANeCItTcG5vPZbBqhfVb4JXFdtOir8r5Mz3a_0"

fun main() {
    ejemplo3()
}

fun ejemplo1(): String {
    val client = OkHttpClient()

    val request = Request.Builder()
        .url("https://api.themoviedb.org/3/movie/now_playing?language=es-ES")
        .get()
        .addHeader("Accept", "application/json")
        .addHeader("Authorization", "Bearer $API_TOKEN")
        .build()

    val call = client.newCall(request)
    val response = call.execute()
    val json = response.body?.string() ?: ""

    return json
}

@OptIn(ExperimentalStdlibApi::class)
fun ejemplo2() {

    val json = ejemplo1()

    val moshi = Moshi.Builder()
        .addLast(KotlinJsonAdapterFactory())
        .build()

    val jsonAdapter = moshi.adapter<MoviesResponse>()//alt: adapter(MovieResponse::class.java)

    val moviesResponse = jsonAdapter.fromJson(json)

    moviesResponse?.results?.forEach {
        println(it.title)
    }
}

fun ejemplo3() {

    val movieService = getMovieService()

    val call = movieService.getMovies()
    val response = call.execute()

    val moviesResponse = response.body()

    moviesResponse?.results?.forEach { println(it.title) }
}

private fun getMovieService(): MovieService {
    val client = OkHttpClient.Builder()//añadido despues de hacer interceptor
        .addInterceptor(RequestTokenInterceptor())
        .build()


    val moshi = Moshi.Builder()
        .addLast(KotlinJsonAdapterFactory())
        .build()

    val retrofit = Retrofit.Builder()
        .baseUrl("https://api.themoviedb.org/3/")
        .client(client)
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .build()

    val movieService = retrofit.create(MovieService::class.java)
    return movieService
}

class RequestTokenInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val newRequest = request.newBuilder()//repetir patron de request anterior, vease la url
            .addHeader("Accept", "application/json")
            .addHeader("Authorization", "Bearer $API_TOKEN")
            .build()
        return chain.proceed(newRequest)
    }

}

fun ejemplo5() {
    val service = getMovieService()

    runBlocking {
        val moviesResponse = service.getMoviesAsyncResponse()

        if (moviesResponse.isSuccessful) {
            val movies = moviesResponse.body()
            movies?.results?.forEach { println(it.title) }
        }
    }
}