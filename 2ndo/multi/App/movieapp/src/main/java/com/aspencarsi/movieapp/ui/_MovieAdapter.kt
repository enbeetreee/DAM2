package com.aspencarsi.movieapp.ui;

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.aspencarsi.movieapp.R
import com.aspencarsi.movieapp.data.network.model.MovieResponse
import com.aspencarsi.movieapp.databinding.ViewMovieItemBinding

class _MovieAdapter : RecyclerView.Adapter<_MovieAdapter.MovieViewHolder>() {

private val movieResponses = mutableListOf<MovieResponse>()

override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
    val layoutInflater = LayoutInflater.from(parent.context)
    val binding = ViewMovieItemBinding.inflate(layoutInflater)
    return MovieViewHolder(binding)
}

override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
    val movie = movieResponses[position]
    holder.bind(movie)
}


override fun getItemCount(): Int = movieResponses.size


fun addMovies(movieResponseList: List<MovieResponse>) {
    movieResponses.addAll(movieResponseList)
    notifyItemRangeInserted(0, movieResponseList.size)
}


class MovieViewHolder(private val binding: ViewMovieItemBinding) : RecyclerView.ViewHolder(binding.root) {

    private val imageUrl = "https://image.tmdb.org/t/p/w185/"

        /*private val titleText: TextView by lazy {
            //itemView.findViewById(R.id.movie_title)
            binding.movieTitle
        }*/

    private val poster: ImageView by lazy {
        itemView.findViewById(R.id.movie_poster)
    }


    fun bind(movieResponse: MovieResponse) {
        binding.movieTitle.text = movieResponse.title

        Glide.with(itemView.context)
                .load("$imageUrl${movieResponse.posterPath}")
                .placeholder(R.mipmap.ic_launcher)
                .fitCenter()
                .into(poster)
    }
}
}