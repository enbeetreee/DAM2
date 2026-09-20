package com.aspencarsi.movieapp.domain

import com.aspencarsi.movieapp.domain.model.Movie

interface MovieRepository {
    suspend fun fetchMovies(): List<Movie>
}