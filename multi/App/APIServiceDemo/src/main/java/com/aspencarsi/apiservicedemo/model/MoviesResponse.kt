package com.aspencarsi.apiservicedemo.model

data class MoviesResponse (
    val page: Int,
    val results: List<Movie>
    )