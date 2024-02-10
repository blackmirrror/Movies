package ru.blackmirrror.movies.app.presentation.fragments.popular

import android.graphics.Bitmap
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ru.blackmirrror.movies.R
import ru.blackmirrror.movies.app.presentation.utils.TextFormatter
import ru.blackmirrror.movies.domain.models.MovieCollectionItem

class MoviesAdapter :
    ListAdapter<MovieCollectionItem, MoviesAdapter.MoviesViewHolder>(MovieItemCallback()) {

    var onMovieItemLongClickListener: ((MovieCollectionItem) -> Unit)? = null
    var onMovieItemClickListener: ((MovieCollectionItem) -> Unit)? = null

    class MoviesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val item = itemView
        val image = itemView.findViewById<ImageView>(R.id.iv_item_image)
        val title = itemView.findViewById<TextView>(R.id.tv_item_title)
        val genreAndYear = itemView.findViewById<TextView>(R.id.tv_item_genre_year)
        val isFavorite = itemView.findViewById<ImageView>(R.id.iv_item_favorite)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MoviesViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_movie, parent, false)
        return MoviesViewHolder(view)
    }

    @OptIn(DelicateCoroutinesApi::class)
    override fun onBindViewHolder(holder: MoviesViewHolder, position: Int) {
        val movie = getItem(position)
        with(holder) {
            title.text = movie.nameRu
            genreAndYear.text = TextFormatter.formatGenreAndYear(movie.genres.first().genre, movie.year)

            item.setOnLongClickListener {
                onMovieItemLongClickListener?.invoke(movie)
                true
            }
            item.setOnClickListener {
                onMovieItemClickListener?.invoke(movie)
            }
        }
        GlobalScope.launch(Dispatchers.Main) {
            val bitmap = try {
                withContext(Dispatchers.IO) {
                    Glide.with(holder.image)
                        .asBitmap()
                        .load(movie.posterUrlPreview)
                        .apply(RequestOptions.centerCropTransform())
                        .submit()
                        .get()
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
            (holder.image as ImageView).setImageBitmap(bitmap as? Bitmap?)
        }
    }
}