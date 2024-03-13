package com.test.android.booksearch.ui.main.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.test.android.booksearch.data.Book
import com.test.android.booksearch.databinding.*
import com.test.android.booksearch.ui.main.adapter.viewholder.BookNormalViewHolder
import com.test.android.booksearch.ui.main.adapter.viewholder.BookLeftViewHolder
import com.test.android.booksearch.ui.main.adapter.viewholder.BookRightViewHolder
import com.test.android.booksearch.ui.main.viewmodel.BookViewModel


class BookAdapter(
    private val bookViewModel: BookViewModel,
) : ListAdapter<Book, RecyclerView.ViewHolder>(object : DiffUtil.ItemCallback<Book>() {
    override fun areItemsTheSame(oldItem: Book, newItem: Book): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: Book, newItem: Book): Boolean {
        return oldItem == newItem
    }
}) {
    companion object {
        const val ITEM1 = 1
        const val ITEM2 = 2
        const val ITEM3 = 3
    }

    private val onItemClickListener = { position: Int ->
        // bookViewModel.openMovieLink(currentList[position].link)
    }

    override fun getItemViewType(position: Int): Int {
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
            ITEM1 -> {
                BookNormalViewHolder(
                    ItemBookNormalBinding.inflate(
                        LayoutInflater.from(viewGroup.context), viewGroup, false
                    ), onItemClickListener
                )
            }

            ITEM2 -> {
                BookLeftViewHolder(
                    ItemBookLeftBinding.inflate(
                        LayoutInflater.from(viewGroup.context), viewGroup, false
                    ), onItemClickListener
                )
            }

            else -> {
                BookRightViewHolder(
                    ItemBookRightBinding.inflate(
                        LayoutInflater.from(viewGroup.context), viewGroup, false
                    ), onItemClickListener
                )
            }
        }
    }

    override fun onBindViewHolder(viewHolder: RecyclerView.ViewHolder, position: Int) {
        when (viewHolder) {
            is BookNormalViewHolder -> viewHolder.bind(currentList[position])
            is BookLeftViewHolder -> viewHolder.bind(currentList[position])
            is BookRightViewHolder -> viewHolder.bind(currentList[position])
            else -> {}
        }
    }

}
