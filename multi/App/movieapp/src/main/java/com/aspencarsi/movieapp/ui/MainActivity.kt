package com.aspencarsi.movieapp.ui;

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.isVisible
import com.aspencarsi.movieapp.databinding.ActivityMainBinding
import com.aspencarsi.movieapp.domain.model.Movie
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {

    //private lateinit var binding: ActivityMainBinding

    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    val viewModel : MovieViewModel by viewModels { MovieViewModel.Factory }

    private val movieAdapter by lazy { MovieListAdapter{movie -> onMovieClick(movie)} }

    fun onMovieClick(movie: Movie){
        Snackbar.
        make(binding.root, "Has hecho click en ${movie.title}", Snackbar.LENGTH_SHORT)
            .show()
        //TODO cambiar a otra activity con datos sobre la pelicula
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        //binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
                val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.movieList.adapter = movieAdapter

        viewModel.fetchMovies()

        viewModel.movies.observe(this) { movies->
                movieAdapter.submitList(movies)
        }

        viewModel.loading.observe(this) { loading ->
                binding.progressBar.isVisible = loading
        }
    }
}