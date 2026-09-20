package com.aspencarsi.movieapp.data

import com.aspencarsi.movieapp.data.network.api.MovieService
import com.aspencarsi.movieapp.domain.MovieRepository
import com.aspencarsi.movieapp.domain.model.Movie

class MovieRepositoryImpl(val movieService: MovieService) : MovieRepository {
    override suspend fun fetchMovies():List<Movie>{
        val moviesResponse = movieService.getMovies()
        return moviesResponse.results.map{ movie -> movie.toDomain()
        }

    }
}