package com.test.android.moviesearch.binding

import android.graphics.drawable.Drawable
import android.widget.ImageView
import android.widget.TextView
import androidx.core.text.HtmlCompat
import androidx.core.text.HtmlCompat.FROM_HTML_MODE_LEGACY
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.test.android.moviesearch.data.Movie
import com.test.android.moviesearch.ui.main.adapter.MovieAdapter


@BindingAdapter("items")
fun RecyclerView.setItems(list: List<Movie>?) {
    list ?: return
    with(adapter as MovieAdapter) {
        setMovieList(list)
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



