package ru.blackmirrror.movies.app.presentation.fragments

import androidx.recyclerview.widget.DiffUtil.ItemCallback
import ru.blackmirrror.movies.domain.models.MovieCollectionItem

class MovieItemCallback: ItemCallback<MovieCollectionItem>() {
    override fun areItemsTheSame(
        oldItem: MovieCollectionItem,
        newItem: MovieCollectionItem
    ): Boolean {
        return oldItem.filmId == newItem.filmId
    }

    override fun areContentsTheSame(
        oldItem: MovieCollectionItem,
        newItem: MovieCollectionItem
    ): Boolean {
        return oldItem == newItem
    }

}