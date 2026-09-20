package com.aspencarsi.movieapp.ui;

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.aspencarsi.movieapp.R
import com.aspencarsi.movieapp.databinding.ViewMovieItemBinding
import com.aspencarsi.movieapp.domain.model.Movie
import com.google.android.material.snackbar.Snackbar


class MovieListAdapter(val onItemClick : (Movie)-> Unit) : ListAdapter<Movie, MovieListAdapter.MovieViewHolder>(DIFF_CALLBACK) {

    private val movieResponses = mutableListOf<Movie>()

    class MovieViewHolder(
        private val binding: ViewMovieItemBinding, val onItemClick: (movieResponse: Movie)->Unit) :
        RecyclerView.ViewHolder(binding.root) {

        private val imageUrl = "https://image.tmdb.org/t/p/w185/"

        /*private val titleText: TextView by lazy {
            //itemView.findViewById(R.id.movie_title)
            binding.movieTitle
        }*/

        fun bind(movieResponse: Movie) {
            binding.movieTitle.text = movieResponse.title

            Glide.with(binding.moviePoster)
                .load("$imageUrl${movieResponse.posterPath}")
                .placeholder(R.mipmap.ic_launcher)
                .fitCenter()
                .into(binding.moviePoster)
            itemView.setOnClickListener{onItemClick(movieResponse)}
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ViewMovieItemBinding.inflate(layoutInflater)
        val vh = MovieViewHolder(binding, onItemClick)

        vh.itemView.setOnClickListener{
            Snackbar.make(parent, "Has hecho click ", Snackbar.LENGTH_SHORT).show()
        }

        return vh
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        holder.bind(currentList[position])
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Movie>() {
            override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
                return oldItem == newItem
            }

            override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
                return oldItem.id == newItem.id
            }

        }
    }



}