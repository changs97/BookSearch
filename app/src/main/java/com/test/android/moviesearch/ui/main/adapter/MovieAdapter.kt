package com.test.android.moviesearch.ui.main.adapter

import android.text.method.TextKeyListener.clear
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.test.android.moviesearch.data.Movie
import com.test.android.moviesearch.databinding.ItemMovie2Binding
import com.test.android.moviesearch.databinding.ItemMovie3Binding
import com.test.android.moviesearch.databinding.ItemMovieBinding
import com.test.android.moviesearch.ui.main.adapter.viewholder.MovieViewHolder
import com.test.android.moviesearch.ui.main.adapter.viewholder.MovieViewHolder2
import com.test.android.moviesearch.ui.main.adapter.viewholder.MovieViewHolder3
import com.test.android.moviesearch.ui.main.viewmodel.MovieViewModel
import kotlinx.coroutines.NonDisposableHandle.parent
import java.util.Collections.addAll

// Recyclerview 사용 이유, 동작 원리, 내부 로직 공부
// DiffUtil 실습, 사용 이유, 내부 로직 공부
// ListAdapter 사용 이유, 내부 로직 공부
// 재사용 가능한 어답터, 뷰홀더? 고민
class MovieAdapter(
    private val movieViewModel: MovieViewModel,
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    companion object { // enum이나 sealed 클래스로 하면 더 좋을지?
        const val ITEM1 = 1
        const val ITEM2 = 2
        const val ITEM3 = 3
    }

    // 재생성되지 않는 형태로 만들어봄. 개선할 점 고민해보기
    private val onItemClickListener = { position: Int ->
        movieViewModel.openMovieLink(movies[position].link)
    }

    override fun getItemViewType(position: Int): Int {
        // 코드 좀 더 효율적으로 짤 순 없는지 고민
        val i = (position + 1)
        return if (i % 3 == 0) {
            val n = i / 3
            if (n % 2 != 0) {
                ITEM2
            } else {
                ITEM3
            }
        } else {
            ITEM1
        }
    }

    private val movies = ArrayList<Movie>()

    fun setMovieList(movies: List<Movie>) {
        this.movies.apply { // scope 함수 공부, 이게 가독성이 좋은지 고민
            clear()
            addAll(movies)
        }
        notifyDataSetChanged() // 개선을 위해 DiffUtil 사용과 ListAdapter 까지 사용해보기
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            // 반복되는 코드 어떻게 처리하면 좋을지 고민
            ITEM1 -> {
                MovieViewHolder(ItemMovieBinding.inflate(
                    LayoutInflater.from(viewGroup.context), viewGroup, false), onItemClickListener)
            }
            ITEM2 -> {
                MovieViewHolder2(ItemMovie2Binding.inflate(
                    LayoutInflater.from(viewGroup.context), viewGroup, false), onItemClickListener)
            }
            else -> {
                MovieViewHolder3(ItemMovie3Binding.inflate(
                    LayoutInflater.from(viewGroup.context), viewGroup, false), onItemClickListener)
            }
        }
    }

    override fun onBindViewHolder(viewHolder: RecyclerView.ViewHolder, position: Int) {
        // 반복되는 코드 어떻게 처리하면 좋을지 고민
        when (viewHolder) {
            is MovieViewHolder -> viewHolder.bind(movies[position])
            is MovieViewHolder2 -> viewHolder.bind(movies[position])
            is MovieViewHolder3 -> viewHolder.bind(movies[position])
            else -> {}

        }
    }

    override fun getItemCount() = movies.size

}
