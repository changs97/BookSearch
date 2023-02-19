package com.test.android.moviesearch.ui.main.adapter.viewholder

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide.init
import com.test.android.moviesearch.data.Movie
import com.test.android.moviesearch.databinding.ItemMovieBinding
import com.test.android.moviesearch.ui.main.viewmodel.MovieViewModel

class MovieViewHolder(private val binding: ItemMovieBinding, onClickListener: (Int) -> Unit) :
    RecyclerView.ViewHolder(binding.root) {

    init {
        // 기존에 bind 가 호출될 때마다 리스너를 set 하던 코드 개선
        binding.itemContainer.setOnClickListener {
            onClickListener(adapterPosition)
        }
    }

    fun bind(item: Movie) {
        binding.item = item
        binding.executePendingBindings() // 데이터 바인딩, 해당 메서드 사용 이유 공부
    }
}