package com.aspencarsi.movieapp.data

import com.aspencarsi.movieapp.data.network.model.MovieResponse
import com.aspencarsi.movieapp.domain.model.Movie

fun MovieResponse.toDomain() = Movie(this.id, this.title, this.posterPath)
