package com.aspencarsi.movieapp.data.network.model

import com.squareup.moshi.Json

data class MovieResponse(

    val id: Int = 0,
    val title: String = "",
    @Json(name = "poster_path") val posterPath: String = "",


)