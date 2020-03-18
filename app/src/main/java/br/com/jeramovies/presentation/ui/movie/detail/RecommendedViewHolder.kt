package br.com.jeramovies.presentation.ui.movie.detail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import br.com.jeramovies.R
import br.com.jeramovies.databinding.ItemRecommendedMovieBinding
import br.com.jeramovies.domain.entity.Movie
import coil.api.load

class RecommendedViewHolder(
    private val binding: ItemRecommendedMovieBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(movie: Movie?, onClick: (Movie) -> Unit) {
        with(binding) {
            imageView.load(movie?.getBackdropUrl())
            textViewMovieTitle.text = movie?.title
            textViewRating.text = movie?.voteAverage?.toString() ?: "0"
            movie?.let { root.setOnClickListener { onClick(movie) } }
        }
    }

    companion object {
        fun inflate(parent: ViewGroup) = RecommendedViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.item_recommended_movie,
                parent,
                false
            )
        )
    }
}