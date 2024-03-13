package com.test.android.booksearch.ui.main.adapter.viewholder

import androidx.recyclerview.widget.RecyclerView
import com.test.android.booksearch.data.Book
import com.test.android.booksearch.databinding.ItemBookRightBinding

class BookRightViewHolder(
    private val binding: ItemBookRightBinding,
    onClickListener: (Int) -> Unit,
) :
    RecyclerView.ViewHolder(binding.root) {

    init {
        binding.itemContainer.setOnClickListener {
            onClickListener(adapterPosition)
        }
    }

    fun bind(item: Book) {
        binding.item = item
        binding.executePendingBindings()
    }
}