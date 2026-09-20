package com.aspencarsi.movieapp.data.network.model

data class MoviesResponse (
    val page: Int,
    val results: List<MovieResponse>
    )