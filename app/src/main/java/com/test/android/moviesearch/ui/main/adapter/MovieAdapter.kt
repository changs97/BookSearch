package com.test.android.moviesearch.ui.main.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.test.android.moviesearch.data.Movie
import com.test.android.moviesearch.databinding.ItemMovieBinding
import com.test.android.moviesearch.ui.main.viewmodel.MovieViewModel

// Recyclerview 사용 이유, 동작 원리, 내부 로직 공부
// DiffUtil 실습, 사용 이유, 내부 로직 공부
// ListAdapter 사용 이유, 내부 로직 공부
// 재사용 가능한 어답터, 뷰홀더? 고민
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
            } // 콜백, 고차함수 공부
        }

        fun bind(item: Movie) {
            binding.item = item
            binding.listener = userActionsListener // 리스너 재생성하지 않고 사용하도록 변경
            binding.executePendingBindings() // 데이터 바인딩, 해당 메서드 사용 이유 공부
        }
    }
}
