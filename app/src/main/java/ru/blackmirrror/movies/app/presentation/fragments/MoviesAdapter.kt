package ru.blackmirrror.movies.app.presentation.fragments

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ru.blackmirrror.movies.R
import ru.blackmirrror.movies.app.presentation.utils.TextFormatter
import ru.blackmirrror.movies.domain.models.MovieCollectionItem

class MoviesAdapter :
    ListAdapter<MovieCollectionItem, MoviesAdapter.MoviesViewHolder>(MovieItemCallback()) {

    class MoviesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val image = itemView.findViewById<ImageView>(R.id.iv_item_image)
        val title = itemView.findViewById<TextView>(R.id.tv_item_title)
        val genreAndYear = itemView.findViewById<TextView>(R.id.tv_item_genre_year)
        val isFavorite = itemView.findViewById<ImageView>(R.id.iv_item_favorite)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MoviesViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_movie, parent, false)
        return MoviesViewHolder(view)
    }

    override fun onBindViewHolder(holder: MoviesViewHolder, position: Int) {
        val movie = getItem(position)
        with(holder) {
            title.text = movie.nameRu
            genreAndYear.text = TextFormatter.formatGenreAndYear(movie.genres.first().genre, movie.year)
        }
    }
}