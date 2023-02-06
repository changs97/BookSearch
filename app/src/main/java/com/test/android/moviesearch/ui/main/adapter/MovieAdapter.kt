package com.test.android.moviesearch.ui.main.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.test.android.moviesearch.data.Movie
import com.test.android.moviesearch.databinding.ItemMovieBinding
import com.test.android.moviesearch.ui.main.viewmodel.MovieViewModel

class MovieAdapter(
    private val movieViewModel: MovieViewModel,
) : RecyclerView.Adapter<MovieAdapter.MovieViewHolder>() {
    private val movies = ArrayList<Movie>()

    fun setMovieList(movies: List<Movie>) {
        this.movies.apply {
            clear()
            addAll(movies)
        }
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): MovieViewHolder {
        val binding =
            ItemMovieBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)
        return MovieViewHolder(binding)
    }

    override fun onBindViewHolder(viewHolder: MovieViewHolder, position: Int) {
        viewHolder.bind(movies[position])
    }

    override fun getItemCount() = movies.size

    inner class MovieViewHolder(private val binding: ItemMovieBinding) :
        RecyclerView.ViewHolder(binding.root) {
        private val userActionsListener = object : MovieItemUserActionsListener {
            override fun onItemClicked(item: Movie) {
                movieViewModel.openMovieLink(item.link)
            }
        }

        fun bind(item: Movie) {
            binding.item = item
            binding.listener = userActionsListener
            binding.executePendingBindings()
        }
    }
}
