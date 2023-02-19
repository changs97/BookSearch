package com.test.android.moviesearch.ui.main.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.test.android.moviesearch.data.Movie
import com.test.android.moviesearch.databinding.*
import com.test.android.moviesearch.ui.main.adapter.viewholder.MovieNormalViewHolder
import com.test.android.moviesearch.ui.main.adapter.viewholder.MovieLeftViewHolder
import com.test.android.moviesearch.ui.main.adapter.viewholder.MovieRightViewHolder
import com.test.android.moviesearch.ui.main.viewmodel.MovieViewModel

// Recyclerview 사용 이유, 동작 원리, 내부 로직 공부
// DiffUtil 실습, 사용 이유, 내부 로직 공부
// ListAdapter 사용 이유, 내부 로직 공부
// 재사용 가능한 어답터, 뷰홀더? 고민
class MovieAdapter(
    private val movieViewModel: MovieViewModel,
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    // enum 이나 sealed 클래스로 하면 더 좋을지?
    companion object {
        const val ITEM1 = 1
        const val ITEM2 = 2
        const val ITEM3 = 3
    }

    // 개선할 점 고민해보기
    private val onItemClickListener = { position: Int ->
        movieViewModel.openMovieLink(movies[position].link)
    }

    private val movies = ArrayList<Movie>()

    fun setMovieList(newMovies: List<Movie>) {
        movies.run {
            clear()
            addAll(newMovies)
        }
        notifyDataSetChanged() // 개선을 위해 DiffUtil 사용과 ListAdapter 까지 사용해보기
    }

    override fun getItemViewType(position: Int): Int {
        // 코드 좀 더 효율적으로 짤 순 없는지 고민
        val i = (position + 1)
        return if (i % 5 == 0) {
            val n = i / 5
            if (n % 2 != 0) {
                ITEM2
            } else {
                ITEM3
            }
        } else {
            ITEM1
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            // 반복되는 코드 어떻게 처리하면 좋을지 고민
            // 뷰홀더의 차이는 Binding 객체 차이 이를 추상화한다면?
            ITEM1 -> {
                MovieNormalViewHolder(ItemMovieNormalBinding.inflate(
                    LayoutInflater.from(viewGroup.context), viewGroup, false), onItemClickListener)
            }
            ITEM2 -> {
                MovieLeftViewHolder(ItemMovieLeftBinding.inflate(
                    LayoutInflater.from(viewGroup.context), viewGroup, false), onItemClickListener)
            }
            else -> {
                MovieRightViewHolder(ItemMovieRightBinding.inflate(
                    LayoutInflater.from(viewGroup.context), viewGroup, false), onItemClickListener)
            }
        }
    }

    override fun onBindViewHolder(viewHolder: RecyclerView.ViewHolder, position: Int) {
        // 반복되는 코드 어떻게 처리하면 좋을지 고민
        when (viewHolder) {
            is MovieNormalViewHolder -> viewHolder.bind(movies[position])
            is MovieLeftViewHolder -> viewHolder.bind(movies[position])
            is MovieRightViewHolder -> viewHolder.bind(movies[position])
            else -> {}

        }
    }

    override fun getItemCount() = movies.size

}
