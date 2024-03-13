package com.test.android.booksearch.binding

import android.graphics.drawable.Drawable
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.core.text.HtmlCompat
import androidx.core.text.HtmlCompat.FROM_HTML_MODE_LEGACY
import androidx.core.view.isVisible
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.test.android.booksearch.data.Book
import com.test.android.booksearch.ui.main.adapter.BookAdapter


@BindingAdapter("items")
fun RecyclerView.setItems(list: List<Book>?) {
    list ?: return
    with(adapter as BookAdapter) {
        submitList(list)
    }
}

@BindingAdapter("imageUrl", "error")
fun ImageView.setImage(imageUrl: String, error: Drawable) {
    Glide.with(context)
        .load(imageUrl)
        .error(error)
        .centerCrop()
        .into(this)
}

@BindingAdapter("htmlText")
fun TextView.removeHtmlTag(htmlText: String) {
    text = HtmlCompat.fromHtml(htmlText, FROM_HTML_MODE_LEGACY).toString()
}

@BindingAdapter("isVisible")
fun ProgressBar.setIsVisible(isVisible: Boolean) {
    this.isVisible = isVisible
}



